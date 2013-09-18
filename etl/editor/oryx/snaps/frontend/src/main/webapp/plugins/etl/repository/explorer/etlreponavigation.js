if (!ORYX.Plugins)
    ORYX.Plugins = new Object();

ORYX.Plugins.ETLRepoNavigation = Clazz.extend({

    // Defines the facade
    facade                : undefined,

    // Defines the undo/redo Stack
    navigationPanel     : undefined,

    // Currently selected context menu node
    ctxNode : undefined,

    // Constructor
    construct: function(facade,ownPluginData){

        this.facade = facade;
        this._currentParent;
        this._canContain = undefined;
        this._canAttach = undefined;

        // Offers the functionality of undo
/*        this.facade.offer({
            name              : ORYX.I18N.Undo.undo,
            description       : ORYX.I18N.Undo.undoDesc,
            icon              : ORYX.PATH + "images/arrow_undo.png",
            functionality     : this.doUndo.bind(this),
            group             : ORYX.I18N.Undo.group,
            isEnabled         : function(){ return this.undoStack.length > 0 }.bind(this),
            index                        : 0
        });*/

        // Offers the functionality of redo
/*        this.facade.offer({
            name              : ORYX.I18N.Undo.redo,
            description       : ORYX.I18N.Undo.redoDesc,
            icon              : ORYX.PATH + "images/arrow_redo.png",
            functionality     : this.doRedo.bind(this),
            group             : ORYX.I18N.Undo.group,
            isEnabled         : function(){ return this.redoStack.length > 0 }.bind(this),
            index                       : 1
        });*/

        // Register on event for executing commands-->store all commands in a stack
/*        this.facade.registerOnEvent(ORYX.CONFIG.EVENT_EXECUTE_COMMANDS,
            this.handleExecuteCommands.bind(this) );*/
        if (!this.navigationPanel)  {
            var treeLoader = new Ext.tree.TreeLoader({
                requestMethod: 'GET',
                clearOnLoad: false,
                dataUrl:'/etlrepo/explorer/getnode',
                preloadChildren: false,
                baseParams: {
                    userid: 'userid',
                    itemtype: 'undefined',
                    folderObjectId: 'undefined'
                },
                listeners: {
                    load: function(loader,node,response) {
                        console.log('data loaded');
                        if (node.isRoot && node.isRoot === true)//1st-level nodes e.g. Metadata etc
                            node.expandChildNodes(false);
                        else  if (node.attributes['itemtype'] && node.attributes['itemtype'] === 'database') {
                            if (node.id === 'metadata.dbconnections') {//DB Connections parent node - simply expand
                                node.expand();
                                node.childNodes.forEach(function(childNode){
                                    var ui = childNode.getUI();

                                    // Set the tooltip
                                    ui.elNode.setAttributeNS(null, "title", childNode.title);

                                    // Register the Node on Drag and Drop
                                    Ext.dd.Registry.register(ui.elNode, {
                                        node : ui.node,
                                        handles : [ ui.elNode, ui.textNode ].concat($A(ui.elNode.childNodes)), // Set
                                        // the
                                        // Handles
                                        isHandle : false,
                                        type : 'database', // Set Type of stencil
                                        namespace : 'http://database'
                                        // Set Namespace of stencil
                                    });
                                });
                            }
                            //Enable DD on 'database' metadata type
                            else {
                                //Ensure rendered to access UI
                                node.render();

                                var ui = node.getUI();

                                // Set the tooltip
                                ui.elNode.setAttributeNS(null, "title", node.title);

                                // Register the Node on Drag and Drop
                                Ext.dd.Registry.register(ui.elNode, {
                                    node : ui.node,
                                    handles : [ ui.elNode, ui.textNode ].concat($A(ui.elNode.childNodes)), // Set
                                    // the
                                    // Handles
                                    isHandle : false,
                                    type : 'database', // Set Type of stencil
                                    namespace : 'http://database'
                                    // Set Namespace of stencil
                                });
                            }
                        }
                    }
                }
            });

            this.navigationPanel = new Ext.ux.ETLRepoNavigationTreePanel({
                facade: this.facade,
                header : false,
                //region: 'west',
                id: 'navigation',
                icon: '/etl/images/conxbi/etl/home_nav.gif',
                collapsible: true,
                title: 'Repository',
                width: 214,
                autoHeight: true,
                autoScroll: true,
                rootVisible: false,
                cmargins: '5 0 0 0',
                padding: '0 0 0 0',
                tbar: [
                    'Search: ', ' ',
                    new Ext.ux.SearchField({
                        width:'auto'
                    })
                ],
                loader: treeLoader,
                // default tree elements for the navigation
                root: new Ext.tree.AsyncTreeNode({
                    text: '',
                    uiProvider: Ext.tree.customNodeUI
                })
            });
            try {
                var region = this.facade.addToRegion("west", this.navigationPanel, "Repository");
            }
            catch (e) {
                ORYX.Log.error(e);
            }

            /**
             * DD
             *
             */
            try {
                var rootNode_ = this.navigationPanel.getRootNode();
                //-- Create a Drag-Zone for Drag'n'Drop
                var DragZone = new Ext.dd.DragZone(rootNode_.getUI().getEl(), {
                    shadow : !Ext.isMac
                });
                DragZone.afterDragDrop = this.drop.bind(this, DragZone);
                DragZone.beforeDragOver = this.beforeDragOver.bind(this, DragZone);
                DragZone.beforeDragEnter = function() {
                    this._lastOverElement = false;
                    return true
                }.bind(this);
            }
            catch (e) {
                ORYX.Log.error(e);
            }
        }
    },
    /**
     * Drop handler
     * @param dragZone
     * @param target
     * @param event
     */
    drop : function(dragZone, target, event) {

        this._lastOverElement = undefined;

        // Hide the highlighting
        /*        this.facade.raiseEvent({
         type : ORYX.CONFIG.EVENT_HIGHLIGHT_HIDE,
         highlightId : 'shapeRepo.added'
         });
         this.facade.raiseEvent({
         type : ORYX.CONFIG.EVENT_HIGHLIGHT_HIDE,
         highlightId : 'shapeRepo.attached'
         });*/

        // Check if drop is allowed
        var proxy = dragZone.getProxy()
        if (proxy.dropStatus == proxy.dropNotAllowed) {
            return
        }

        // Check if there is a current Parent
        if (!this._currentParent) {
            return
        }

        var option = Ext.dd.Registry.getHandle(target.DDM.currentTarget);

        var xy = event.getXY();
        var pos = {
            x : xy[0],
            y : xy[1]
        };

        var a = this.facade.getCanvas().node.getScreenCTM();

        // Correcting the UpperLeft-Offset
        pos.x -= a.e;
        pos.y -= a.f;
        // Correcting the Zoom-Faktor
        pos.x /= a.a;
        pos.y /= a.d;
        // Correting the ScrollOffset
        pos.x -= document.documentElement.scrollLeft;
        pos.y -= document.documentElement.scrollTop;
        // Correct position of parent
        var parentAbs = this._currentParent.absoluteXY();
        pos.x -= parentAbs.x;
        pos.y -= parentAbs.y;

        // Set position
        option['position'] = pos

        // Set parent
        if (this._canAttach && this._currentParent instanceof ORYX.Core.Node) {
            option['parent'] = undefined;
        } else {
            option['parent'] = this._currentParent;
        }
        /*
         var commandClass = ORYX.Core.Command.extend({
         construct : function(option, currentParent, canAttach, position, facade) {
         this.option = option;
         this.currentParent = currentParent;
         this.canAttach = canAttach;
         this.position = position;
         this.facade = facade;
         this.selection = this.facade.getSelection();
         this.shape;
         this.parent;
         },
         execute : function() {
         if (!this.shape) {
         this.shape = this.facade.createShape(option);
         this.parent = this.shape.parent;
         } else {
         this.parent.add(this.shape);
         }

         if (this.canAttach && this.currentParent instanceof ORYX.Core.Node && this.shape.dockers.length > 0) {

         var docker = this.shape.dockers[0];

         if (this.currentParent.parent instanceof ORYX.Core.Node) {
         this.currentParent.parent.add(docker.parent);
         }

         docker.bounds.centerMoveTo(this.position);
         docker.setDockedShape(this.currentParent);
         // docker.update();
         }

         // this.currentParent.update();
         // this.shape.update();

         this.facade.setSelection([ this.shape ]);
         this.facade.getCanvas().update();
         this.facade.updateSelection();

         },
         rollback : function() {
         this.facade.deleteShape(this.shape);

         // this.currentParent.update();

         this.facade.setSelection(this.selection.without(this.shape));
         this.facade.getCanvas().update();
         this.facade.updateSelection();

         }
         });

         var position = this.facade.eventCoordinates(event.browserEvent);

         var command = new commandClass(option, this._currentParent, this._canAttach, position, this.facade);

         this.facade.executeCommands([ command ]);

         this._currentParent = undefined;*/
    },

    /**
     * On Drag Over
     *
     * @param dragZone
     * @param target
     * @param event
     * @returns {boolean}
     */
    beforeDragOver : function(dragZone, target, event) {

        var coord = this.facade.eventCoordinates(event.browserEvent);
        var aShapes = this.facade.getCanvas().getAbstractShapesAtPosition(coord);

        if (aShapes.length <= 0) {

            var pr = dragZone.getProxy();
            pr.setStatus(pr.dropNotAllowed);
            pr.sync();

            return false;
        }

        var el = aShapes.last();

        if (aShapes.lenght == 1 && aShapes[0] instanceof ORYX.Core.Canvas) {

            return false;

        } else {
            // check containment rules
            var option = Ext.dd.Registry.getHandle(target.DDM.currentTarget);

            var stencilSet = this.facade.getStencilSets()[option.namespace];

            var stencil = stencilSet.stencil(option.type);

            if (stencil.type() === "node") {

                var parentCandidate = aShapes.reverse().find(function(candidate) {
                    return (candidate instanceof ORYX.Core.Canvas || candidate instanceof ORYX.Core.Node || candidate instanceof ORYX.Core.Edge);
                });

                if (parentCandidate !== this._lastOverElement) {

                    this._canAttach = undefined;
                    this._canContain = undefined;

                }

                if (parentCandidate) {
                    // check containment rule

                    if (!(parentCandidate instanceof ORYX.Core.Canvas) && parentCandidate.isPointOverOffset(coord.x, coord.y) && this._canAttach == undefined) {

                        this._canAttach = this.facade.getRules().canConnect({
                            sourceShape : parentCandidate,
                            edgeStencil : stencil,
                            targetStencil : stencil
                        });

                        if (this._canAttach) {
                            // Show Highlight
                            this.facade.raiseEvent({
                                type : ORYX.CONFIG.EVENT_HIGHLIGHT_SHOW,
                                highlightId : "shapeRepo.attached",
                                elements : [ parentCandidate ],
                                style : ORYX.CONFIG.SELECTION_HIGHLIGHT_STYLE_RECTANGLE,
                                color : ORYX.CONFIG.SELECTION_VALID_COLOR
                            });

                            this.facade.raiseEvent({
                                type : ORYX.CONFIG.EVENT_HIGHLIGHT_HIDE,
                                highlightId : "shapeRepo.added"
                            });

                            this._canContain = undefined;
                        }

                    }

                    if (!(parentCandidate instanceof ORYX.Core.Canvas) && !parentCandidate.isPointOverOffset(coord.x, coord.y)) {
                        this._canAttach = this._canAttach == false ? this._canAttach : undefined;
                    }

                    if (this._canContain == undefined && !this._canAttach) {

                        this._canContain = this.facade.getRules().canContain({
                            containingShape : parentCandidate,
                            containedStencil : stencil
                        });

                        // Show Highlight
                        this.facade.raiseEvent({
                            type : ORYX.CONFIG.EVENT_HIGHLIGHT_SHOW,
                            highlightId : 'shapeRepo.added',
                            elements : [ parentCandidate ],
                            color : this._canContain ? ORYX.CONFIG.SELECTION_VALID_COLOR : ORYX.CONFIG.SELECTION_INVALID_COLOR
                        });
                        this.facade.raiseEvent({
                            type : ORYX.CONFIG.EVENT_HIGHLIGHT_HIDE,
                            highlightId : "shapeRepo.attached"
                        });
                    }

                    this._currentParent = this._canContain || this._canAttach ? parentCandidate : undefined;
                    this._lastOverElement = parentCandidate;
                    var pr = dragZone.getProxy();
                    pr.setStatus(this._currentParent ? pr.dropAllowed : pr.dropNotAllowed);
                    pr.sync();

                }
            } else { // Edge
                this._currentParent = this.facade.getCanvas();
                var pr = dragZone.getProxy();
                pr.setStatus(pr.dropAllowed);
                pr.sync();
            }
        }

        return false
    },

    handleExecuteCommands: function( evt ){

        //...

    },

    doUndo: function(){

        //...

    },

    doRedo: function(){

        //...
    }

});


Ext.ns("Ext.ux");
Ext.ux.ETLRepoNavigationTreePanel = Ext.extend(Ext.tree.TreePanel, {
    facade: undefined,
    //Etc
    mainTabPanel: undefined,
    mainEditorPanel_ : undefined,
    repofolder_database_contextmenu : undefined,
    repoitem_database_contextmenu : undefined,
    new_repoitem_database_wizard : undefined,

/*    onRender: function(){
        Ext.ux.ETLRepoNavigationTreePanel.superclass.onRender.apply(this, arguments);
    },

    onResize: function(){
        Ext.ux.ETLRepoNavigationTreePanel.superclass.onResize.apply(this, arguments);
    },*/

    initComponent: function(){
        Ext.apply(this, {
        });
        Ext.ux.ETLRepoNavigationTreePanel.superclass.initComponent.apply(this, arguments);


        /**
         * Tree Loader
         */
        this.loader.on("beforeload", function(treeLoader, node) {
            //this.baseParams.category = node.attributes.category;
            //this.baseParams.userid = 'test';
            if (node.attributes.itemtype)
                this.loader.baseParams.itemtype = node.attributes.itemtype;
            if (node.attributes.folderObjectId)
                this.loader.baseParams.folderObjectId = node.attributes.folderObjectId;
        }, this);


        /**
         * Event registrations
         */
        this.facade.registerOnEvent(ORYX.CONFIG.EVENT_ETL_METADATA_CREATED, this.onCreated.bind(this));
        this.facade.registerOnEvent(ORYX.CONFIG.EVENT_ETL_METADATA_DELETED, this.onDeleted.bind(this));

        this.getSelectionModel().on({
            'beforeselect' : function(sm, node){
                return node.isLeaf();
            },
            'selectionchange' : function(sm, node){
                if(node){
                    this.fireEvent('feedselect', node.attributes);
                }
                this.getTopToolbar().items.get('delete').setDisabled(!node);
            },
            scope:this
        });


        //-- Create context menu's
        this.repofolder_database_contextmenu = new Ext.menu.Menu({
            id:'feeds-ctx',
            items: [{
                id: 'load',
                icon: '/etl/images/conxbi/etl/connection-new.png',
                text:'Add Database',
                scope: this,
                handler:function(){
                    this.ctxNode.select();
                    var eventData = {
                        source:this.ctxNode
                    };
                    this.facade.raiseEvent(ORYX.CONFIG.EVENT_ETL_METADATA_CREATE_PREFIX+'DBConnection',eventData);
                }.bind(this)
            },{
                text:'Create Folder',
                icon: '/etl/images/conxbi/etl/folder_close.png',
                scope: this,
                handler:function(){
                    this.ctxNode.select();
                    //this.mainEditorPanel.removeAll();
                    Ext.apply(this.new_repoitem_folder_wizard,{ctxNode:this.ctxNode,mainEditorPanel:this.mainEditorPanel});
                    this.mainEditorPanel.setTitle("New Folder");
                    this.mainEditorPanel.add(this.new_repoitem_folder_wizard);
                    this.mainTabPanel.add(this.mainEditorPanel);
                    this.mainTabPanel.setActiveTab(this.mainEditorPanel);
                    //this.newDBWiz.show();
                }.bind(this)
            },{
                text:'Delete Folder',
                icon: '/etl/images/conxbi/etl/folder_delete.png',
                scope: this,
                handler:function(){
                    var requestWiz = this;
                    var requestCtxNode =  requestWiz.ctxNode;

                    var idArray = requestCtxNode.id.split('/');
                    var dirId = idArray[1];
                    Ext.lib.Ajax.request = Ext.lib.Ajax.request.createInterceptor(function(method, uri, cb, data, options){
                        // here you can put whatever you need as header. For instance:
                        //this.defaultPostHeader = "application/json; charset=utf-8;";
                        this.defaultHeaders = {
                            userid: 'test',
                            itemtype: 'database',
                            folderObjectId: dirId
                        };
                    });
                    Ext.Ajax.request({
                        url: '/etlrepo/explorer/deletedir',
                        method: 'DELETE',
                        success: function(response, opts) {
                            //Refresh this.ctxNode
                            if (requestCtxNode.attributes)
                                requestCtxNode.attributes.children = false;
                            requestCtxNode.reload();
                        },
                        failure: function(response, opts) {}
                    });
                }.bind(this)
            },{
                text:'Refresh Folder',
                icon: '/etl/images/conxbi/etl/refresh.gif',
                scope: this,
                handler:function(){
                    this.ctxNode.attributes.children = false;
                    this.ctxNode.reload();
                }.bind(this)
            }
            ]
        })
        this.repofolder_database_contextmenu.on('hide', this.onContextHide, this);

        this.repoitem_database_contextmenu = new Ext.menu.Menu({
            id:'feeds-ctx',
            items: [{
                id: 'load',
                icon: '/etl/images/conxbi/etl/connection-new.png',
                text:'Add Database',
                scope: this,
                handler:function(){
                    this.ctxNode.select();
                    var eventData = {
                        type: ORYX.CONFIG.EVENT_ETL_METADATA_CREATE_PREFIX+'DBConnection',
                        forceExecution: true
                    };
                    this.facade.raiseEvent(eventData,{
                            dbFolderId: this.ctxNode.attributes['folderObjectId'],
                            sourceNavNodeId: this.ctxNode.id}
                    );
                }.bind(this)
            },{
                text:'Edit Database',
                icon: '/etl/images/conxbi/etl/modify.gif',
                scope: this,
                handler:function(){
                    this.ctxNode.select();
                    var eventData = {
                        type: ORYX.CONFIG.EVENT_ETL_METADATA_EDIT_PREFIX+'DBConnection',
                        forceExecution: true
                    };
                    this.facade.raiseEvent(eventData,{
                            title: 'DB Connection '+this.ctxNode.attributes['title'],
                            sourceNavNodeId: this.ctxNode.id
                        }
                    );
                }.bind(this)
            },{
                text:'Delete Database',
                icon: '/etl/images/conxbi/etl/connection-delete.png',
                scope: this,
                handler:function(){
                    this.ctxNode.select();
                    var eventData = {
                        type: ORYX.CONFIG.EVENT_ETL_METADATA_DELETE_PREFIX+'DBConnection',
                        forceExecution: true
                    };
                    this.facade.raiseEvent(eventData,{
                            title: 'DB Connection '+this.ctxNode.attributes['title'],
                            sourceNavNodeId: this.ctxNode.id,
                            parentSourceNavNodeId: this.ctxNode.parentNode.id
                        }
                    );
                    this.ctxNode = null;
                }.bind(this)
            },{
                text:'Create Folder',
                icon: '/etl/images/conxbi/etl/folder_close.png',
                scope: this,
                handler:function(){
                    this.ctxNode.select();
                    //this.mainEditorPanel.removeAll();
                    Ext.apply(this.new_repoitem_folder_wizard,{ctxNode:this.ctxNode,mainEditorPanel:this.mainEditorPanel});
                    this.mainEditorPanel.setTitle("New Folder");
                    this.mainEditorPanel.add(this.new_repoitem_folder_wizard);
                    this.mainTabPanel.add(this.mainEditorPanel);
                    this.mainTabPanel.setActiveTab(this.mainEditorPanel);
                    //this.newDBWiz.show();
                }.bind(this)
            }]
        });
        this.repoitem_database_contextmenu.on('hide', this.onContextHide, this);

        //Wizards
        try {
            //-- Register events
            this.addEvents({feedselect:true});

            this.on('contextmenu', this.onContextMenu, this);
        }
        catch (e) {
            ORYX.Log.error(e);
        }
    },
    /**
     * On Created
     * @param event
     */
    onCreated : function(event) {
        var ctxNodeId = event.treeNodeParentId;
        var name = event.name;

        var ctxNode_ = this.getNodeById(ctxNodeId);
        ctxNode_.select();

        Ext.MessageBox.show({
            title:'Saved',
            msg: name+' was created successfully.',
            buttons: Ext.MessageBox.OK,
            icon: Ext.MessageBox.INFO
        });

        ctxNode_.reload();
    },
    /**
     * On Deleted
     * @param event
     */
    onDeleted : function(event) {
        var ctxNodeId = event.treeNodeParentId;
        var name = event.name;

        var ctxNode_ = this.getNodeById(ctxNodeId);
        ctxNode_.select();

        ctxNode_.reload();
    },
    /**
     *
     * @param node
     * @param e
     */
    onContextMenu : function(node, e){
        if (node.attributes.itemtype && node.attributes.itemtype === 'database') {
            if (!node.attributes.itemcontainertype) {//database  leaf
                this.menu = this.repoitem_database_contextmenu;
            } else if (node.attributes.itemcontainertype && node.attributes.itemcontainertype === 'repofolder') {
                this.menu = this.repofolder_database_contextmenu;
            }
        }


        //if(node.isLeaf()){
        this.ctxNode = node;
        this.ctxNode.ui.addClass('x-node-ctx');
        if (this.menu.items)
            this.menu.items.get('load').setDisabled(node.isSelected());
        this.menu.showAt(e.getXY());
        //}
    },

    onContextHide : function(){
        if(this.ctxNode){
            this.ctxNode.ui.removeClass('x-node-ctx');
            this.ctxNode = null;
        }
    },

    // prevent the default context menu when you miss the node
    afterRender : function(){
        Ext.ux.ETLRepoNavigationTreePanel.superclass.afterRender.call(this);
        this.el.on('contextmenu', function(e){
            e.preventDefault();
        });
    }
});
