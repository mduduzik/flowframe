if (!ORYX.ETL)
    ORYX.ETL = new Object();


ORYX.ETL.DOCRepoNavigation = Clazz.extend({

    // Defines the application
    application: undefined,

    // Defines the undo/redo Stack
    navigationPanel: undefined,

    // Currently selected context menu node
    ctxNode: undefined,

    modalDialogShown: undefined,

    // Constructor
    construct: function (application) {

        this.application = application;
        this._currentParent;
        this._canContain = undefined;
        this._canAttach = undefined;

        // Offers the functionality of undo
        /*        this.application.offer({
         name              : ORYX.I18N.Undo.undo,
         description       : ORYX.I18N.Undo.undoDesc,
         icon              : ORYX.PATH + "images/arrow_undo.png",
         functionality     : this.doUndo.bind(this),
         group             : ORYX.I18N.Undo.group,
         isEnabled         : function(){ return this.undoStack.length > 0 }.bind(this),
         index                        : 0
         });*/

        // Offers the functionality of redo
        /*        this.application.offer({
         name              : ORYX.I18N.Undo.redo,
         description       : ORYX.I18N.Undo.redoDesc,
         icon              : ORYX.PATH + "images/arrow_redo.png",
         functionality     : this.doRedo.bind(this),
         group             : ORYX.I18N.Undo.group,
         isEnabled         : function(){ return this.redoStack.length > 0 }.bind(this),
         index                       : 1
         });*/

        // Register on event for executing commands-->store all commands in a stack
        /*        this.application.registerOnEvent(ORYX.CONFIG.EVENT_EXECUTE_COMMANDS,
         this.handleExecuteCommands.bind(this) );*/
        var thisEditor = this;
        if (!this.navigationPanel) {
/*
            var treeLoader = new Ext.tree.TreeLoader({
                requestMethod: 'GET',
                clearOnLoad: false,
                dataUrl: '/etl/core/explorer/getnode',
                preloadChildren: false,
                baseParams: {
                    userid: 'userid',
                    itemtype: 'undefined',
                    folderObjectId: 'undefined'
                },
                listeners: {
                    load: function (loader, node, response) {
                        console.log('data loaded');
                        if (node.isRoot && node.isRoot === true)//1st-level nodes e.g. Metadata etc
                            node.expandChildNodes(false);
                        else if (node.id.indexOf('metadata.') >= 0) {//Metadata folder
                            node.expand();
                        }
                        node.cascade( function() {
                            if (this.attributes['ddenabled'] && this.attributes['ddenabled'] === 'true') {
                                this.render();
                                thisEditor.registerDD(this, this.attributes['itemtype']);
                            }
                        });
                    }
                }
            });
*/


            this.navigationPanel = new Ext.ux.DOCRepoNavigationTreePanel({
                registerDDCallback: this.registerDD.bind(this),
                application: this.application,
                header: true,
                //region: 'west',
                id: 'docNavigation',
                icon: '/etl/images/conxbi/etl/home_nav.gif',
                url: '/etl/core/docexplorer/getnode',
                uploadUrl: '/etl/core/docexplorer/upload',
                newdirUrl: '/etl/core/docexplorer/adddir',
                deleteUrl: '/etl/core/docexplorer/deletedir',
                collapsible: true,
                title: 'Files',
                autoWidth: true,
                autoHeight: true,
                autoScroll: true,
                rootVisible: false,
                cmargins: '5 0 0 0',
                padding: '0 0 0 0',
                tbar: [
                    'Search: ', ' ',
                    new Ext.ux.SearchField({
                        width: 'auto'
                    })
                ]
                // default tree elements for the navigation
/*                root: new Ext.tree.AsyncTreeNode({
                    text: '',
                    uiProvider: Ext.tree.customNodeUI
                })*/
            });
            try {
                var region = this.application.addToRegion("west", this.navigationPanel, "Repository");
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
                    shadow: !Ext.isMac
                });
                DragZone.afterDragDrop = this.afterDragDrop(this, DragZone);
                DragZone.beforeDragOver = this.beforeDragOver.bind(this, DragZone);
                DragZone.beforeDragEnter = function () {
                    this.modalDialogShown = false;
                    return true
                }.bind(this);
            }
            catch (e) {
                ORYX.Log.error(e);
            }
        }
    },
    registerDD: function (node, type) {
        var ui = node.getUI();

        // Set the tooltip
        ui.elNode.setAttributeNS(null, "title", node.title);

        // Register the Node on Drag and Drop
        Ext.dd.Registry.register(ui.elNode, {
            mainNode: node,
            node: ui.node,
            handles: [ ui.elNode, ui.textNode ].concat($A(ui.elNode.childNodes)), // Set
            // the
            // Handles
            isHandle: false,
            type: type, // Set Type of stencil
            namespace: 'http://etl.flowframe.org/stencilset/etl#'
            // Set Namespace of stencil
        });
    },
    afterDragDrop: function (dragZone, target, event) {
        var pr = dragZone.getProxy();
        pr.hide();

/*        var nodeId = event.target.attributes['ext:tree-node-id'].nodeValue;
        var targetNode = this.navigationPanel.getNodeById(nodeId);*/

        //-- Raise DD event
/*        var eventData = {
            type: ORYX.CONFIG.EVENT_ETL_DOCREPOSITORY_DDROP,
            forceExecution: false //async call
        };
        var sourceData = {
            dragZone: dragZone,
            target: target,
            event: event
        };

        this.modalDialogShown = true;
        this.application.handleEvents(eventData, sourceData);*/
    },
    /**
     * On Drag Over
     *
     * @param dragZone
     * @param target
     * @param event
     * @returns {boolean}
     */
    beforeDragOver: function (dragZone, target, event) {
        if (this.modalDialogShown === true)
            return;
        //target.lock();
        //target.unreg();

        //Disallow drop right away before presenting user with choice
        var pr = dragZone.getProxy();
        pr.setStatus(pr.dropAllowed);
        pr.sync();
        //pr.hide();


        //var ui = node.getUI();

        //this.registerDD(ui,node.title,"database");
        //target.unlock();
        //pr.setStatus(pr.dropAllowed);
        //pr.sync();
        //var option = Ext.dd.Registry.getHandle(target.DDM.currentTarget);
        //this.registerDD(option.mainNode,"database");
        //

        return false;
    },
    handleExecuteCommands: function (evt) {

        //...

    },

    doUndo: function () {

        //...

    },

    doRedo: function () {

        //...
    }

});


Ext.ns("Ext.ux");
Ext.ux.DOCRepoNavigationTreePanel = Ext.extend(Ext.ux.FileTreePanel, {
    application: undefined,
    registerDDCallback: undefined,

    initComponent: function () {
        try {
            Ext.apply(this, {
            });
            Ext.ux.DOCRepoNavigationTreePanel.superclass.initComponent.apply(this, arguments);

            //{{
            //  Tree events
            //}}
            this.loader.on('load',function(treeLoader, node) {
                var registerDDCallback_ = this.registerDDCallback;
                node.cascade( function() {
                    if (this.attributes['ddenabled'] && this.attributes['ddenabled'] === 'true') {
                        this.render();
                        registerDDCallback_(this, this.attributes['itemtype']);
                    }
                });
            }, this);

            //{{
            //  Event handling
            //}}
            //-- Drag and Drop
            this.application.registerOnEvent(ORYX.CONFIG.EVENT_ETL_DOCREPOSITORY_DDROP, this.onDocRepoItemDrop.bind(this));
        }
        catch (e) {
            ORYX.Log.error(e);
        }
    },
    /**
     * On DocRepoItemDrop
     * @param event
     */
    onDocRepoItemDrop: function (event, source) {
        if (!this.application.CurrentEditor)
            return;

        var currentCanvasFacade =  this.application.CurrentEditor.canvasFacade;
        var currentCanvas = currentCanvasFacade.getCanvas();

        var dragZone = source.dragZone;
        var target = source.target;
        var event = source.event;

        var coord = currentCanvasFacade.eventCoordinates(event.browserEvent);
        var aShapes = currentCanvas.getAbstractShapesAtPosition(coord);

        if (aShapes.length <= 0) {
            return false;
        }

        var el = aShapes.last();

        if (aShapes.lenght == 1 && aShapes[0] instanceof ORYX.Core.Canvas) {
            return;
        } else {
            // check containment rules
            var option = Ext.dd.Registry.getHandle(target.DDM.currentTarget);

            /**
             * Get stencils supprting this metadata
             */
            var stencilSet = currentCanvasFacade.getStencilSets()[currentCanvasFacade.getNamespace()];

            // Get Stencils from Stencilset
            var stencils = stencilSet.stencils(currentCanvas.getStencil(), currentCanvasFacade.getRules());
            var treeGroups = new Hash();

            // Get stencils that support metadata 'option.type'
            var stencilsByMetadata = stencils.findAll(function (value) {
                if (!value._jsonStencil || !value._jsonStencil.supportedMetadata)
                    return false;

                var index_ = value._jsonStencil.supportedMetadata.indexOf(option.type);
                if (index_ >= 0)
                    return true;
                else
                    return false;
            });

            if (stencilsByMetadata.length < 1)
                return;

            // Sort the stencils according to their position and add them to the repository
            stencilsByMetadata = stencilsByMetadata.sortBy(function (value) {
                return value.title;
            });


            //-- Create records
            var data = [];
            stencilsByMetadata.each(function (stencil) {
                data.push([stencil._jsonStencil.icon, stencil._jsonStencil.title, true, stencil._jsonStencil.id, stencil._namespace]);
            })
            if (data.length == 0) {
                return;
            }

            var reader = new Ext.data.ArrayReader({}, [
                {name: 'icon'},
                {name: 'title'},
                {name: 'engaged'},
                {name: 'type'},
                {name: 'namespace'}
            ]);

            var newURLWin = new Ext.Window({
                title: "Select Step",
                //bodyStyle:	"background:white;padding:0px",
                width: 'auto',
                height: 'auto',
                closeAction: 'destroy',
                modal: true,
                //html:"<div style='font-weight:bold;margin-bottom:10px'></div><span></span>",
                buttons: [
                    {
                        id: 'select',
                        text: 'Select',
                        disabled: true,
                        handler: function () {
                            var selection = sm.getSelected();  //always one element
                            var option_ = {
                                type: selection.data.type,
                                position: coord,
                                connectingType: undefined,
                                connectedShape: undefined,
                                dragging: undefined,
                                namespace: selection.data.namespace,
                                parent: el
                            };

                            //--Add shape
                            var commandClass = ORYX.Core.Command.extend({
                                construct: function (option, currentParent, canAttach, position, facade) {
                                    this.option = option;
                                    this.currentParent = currentParent;
                                    this.canAttach = canAttach;
                                    this.position = position;
                                    this.facade = facade;
                                    this.selection = this.facade.getSelection();
                                    this.shape;
                                    this.parent;
                                },
                                execute: function () {
                                    if (!this.shape) {
                                        this.shape = this.facade.createShape(this.option);
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
                                rollback: function () {
                                    currentCanvasFacade.deleteShape(this.shape);

                                    // this.currentParent.update();

                                    this.facade.setSelection(this.selection.without(this.shape));
                                    this.facade.getCanvas().update();
                                    this.facade.updateSelection();
                                }
                            });
                            var command = new commandClass(option_, this._currentParent, this._canAttach, coord, currentCanvasFacade);
                            currentCanvasFacade.executeCommands([command]);

                            //--Update metadata on shape
                            var newShape = currentCanvasFacade.getSelection()[0];
                            var commandClass = ORYX.Core.Command.extend({
                                construct: function (facade, metadatatype, metadataName, metadataObjId) {
                                    ;
                                    this.facade = facade,
                                    this.metadatatype = metadatatype,
                                    this.metadataName = metadataName;
                                    this.metadataObjId = metadataObjId
                                },
                                execute: function () {
                                    newShape.setProperty('oryx-name', this.metadatatype + '-' + Ext.id());
                                    newShape.setProperty('oryx-author', 'Developer');
                                    newShape.setProperty('oryx-version', '1.0');
                                    newShape.setProperty('oryx-documentation', this.metadatatype + '-' + Ext.id());
                                    newShape.setProperty('oryx-metadatatype', this.metadatatype);
                                    newShape.setProperty('oryx-metadataname', this.metadataName);
                                    newShape.setProperty('oryx-metadataobjid', this.metadataObjId);

                                    this.facade.setSelection([newShape]);
                                    currentCanvas.update();
                                    this.facade.updateSelection();
                                },
                                rollback: function () {
                                    newShape.setProperty('oryx-metadatatype', 'R' + this.metadatatype);
                                    newShape.setProperty('oryx-metadataObjId', 'R' + this.metadataObjId);
                                    this.facade.setSelection([newShape]);
                                    currentCanvas.update();
                                    this.facade.updateSelection();
                                }
                            })
                            var metadataType = dragZone.dragData.mainNode.attributes['itemtype'];
                            var metadataName = dragZone.dragData.mainNode.text;
                            var metadataObjId = dragZone.dragData.mainNode.id;
                            command = new commandClass(currentCanvasFacade, metadataType, metadataName, metadataObjId);
                            currentCanvasFacade.executeCommands([ command ]);


                            //--Generate extra metadata properties - if there are any
                            var itemtype = dragZone.dragData.mainNode.attributes['itemtype'];
                            var itemtypeProps = new Hash();
                            for (attrname in dragZone.dragData.mainNode.attributes) {
                                if (attrname.indexOf(itemtype+'.') >= 0)
                                    itemtypeProps[attrname] = dragZone.dragData.mainNode.attributes[attrname];
                            };
                            if (itemtypeProps.keys().length > 0) {
                                var extraProps =  new Hash();
                                itemtypeProps.keys().each(function (key) {
                                    var tokens = key.split(itemtype+'.')
                                    extraProps[tokens[1]] = itemtypeProps[key];
                                }.bind(this));

                                var commandClass = ORYX.Core.Command.extend({
                                    construct: function (facade, props) {
                                        this.facade = facade,
                                        this.props = props;
                                    },
                                    execute: function () {
                                        this.props.keys().each(function (key) {
                                            newShape.setProperty('oryx-'+key,this.props[key])
                                        }.bind(this));
                                        this.facade.setSelection([newShape]);
                                        currentCanvas.update();
                                        this.facade.updateSelection();
                                    },
                                    rollback: function () {
                                        this.props.keys().each(function (key) {
                                            newShape.setProperty('oryx-'+key,'R'+this.props[key])
                                        }.bind(this));
                                        this.facade.setSelection([newShape]);
                                        currentCanvas.update();
                                        this.facade.updateSelection();
                                    }
                                })
                                command = new commandClass(currentCanvasFacade, extraProps);
                                currentCanvasFacade.executeCommands([ command ]);
                            }

                            newURLWin.close();
                        }.bind(this)
                    },
                    {
                        text: 'Cancel',
                        handler: function () {
                            newURLWin.close();
                        }
                    }
                ]
            });

            var sm = new Ext.grid.CheckboxSelectionModel({
                singleSelect: true,
                listeners: {
                    selectionchange: function (sm, nbr, rec) {
                        var selectBtn = newURLWin.buttons[0];//select
                        if (sm.getCount() > 0)
                            selectBtn.enable();
                        else
                            selectBtn.disable();
                        /*                    sm.suspendEvents();
                         sm.selectRow(nbr, true);
                         sm.resumeEvents();*/
                    }
                }});
            var grid2 = new Ext.grid.GridPanel({
                store: new Ext.data.Store({
                    reader: reader,
                    data: data
                }),
                cm: new Ext.grid.ColumnModel([
                    //{id: 'icon', width: 25,  renderer:function(val) {return '<img src="' + val + '">';}, sortable: false, dataIndex: 'icon'},
                    {id: 'icon', width: 24, renderer: function (val) {
                        return '<img style="max-height: 16px; max-width: 16px" src="' + val + '" />';
                    }, sortable: false, dataIndex: 'icon'},
                    {id: 'title', width: 390, sortable: true, dataIndex: 'title'},
                    sm
                ]),
                sm: sm,
                width: 460,
                height: 250,
                frame: true,
                hideHeaders: true,
                iconCls: 'icon-grid',
                listeners: {
                    render: function () {
                        var recs = [];
                        this.grid.getStore().each(function (rec) {

                            if (rec.data.engaged) {
                                recs.push(rec);
                            }
                        }.bind(this));
                        /*                    this.suspendEvents();
                         this.selectRecords(recs);
                         this.resumeEvents();*/
                    }.bind(sm)
                }
            });


            newURLWin.add(grid2);
            newURLWin.show();
        }
    }
});
