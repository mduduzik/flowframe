if (!ORYX.ETL)
    ORYX.ETL = new Object();


ORYX.ETL.ETLRepoNavigation = Clazz.extend({

    // Defines the application
    application: undefined,

    // Defines the undo/redo Stack
    navigationPanel: undefined,

    // Currently selected context menu node
    ctxNode: undefined,

    modalDialogShown: undefined,
    selectOnEdit:true,

    // Constructor
    construct: function (application) {

        this.application = application;
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
/*
                        else if (node.attributes['ddenabled'] && node.attributes['ddenabled'] === 'true') {
                            node.render();
                            thisEditor.registerDD(node, node.attributes['itemtype']);
                        }
*/
                        node.cascade( function() {
                            if (this.attributes['ddenabled'] && this.attributes['ddenabled'] === 'true') {
                                this.render();
                                thisEditor.registerDD(this, this.attributes['itemtype']);
                            }
                        });
                    },
                    beforeload: function(treeLoader, node) {
                        this.baseParams.itemtype = node.attributes.itemtype;
                        this.baseParams.folderObjectId = node.attributes.folderObjectId;
                    }
                }
            });

            this.navigationPanel = new Ext.ux.ETLRepoNavigationTreePanel({
                application: this.application,
                header: true,
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
                        width: 'auto'
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
                DragZone.afterDragDrop = this.drop.bind(this, DragZone);
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
            namespace: 'http://etl.flowframe.org/stencilset/etl/trans#'
            // Set Namespace of stencil
        });
    },
    drop: function (dragZone, target, event) {
        var pr = dragZone.getProxy();
        pr.hide();

        //-- Raise DD event
        var eventData = {
            type: ORYX.CONFIG.EVENT_ETL_METADATA_DDROP,
            forceExecution: true //async call
        };
        var sourceData = {
            dragZone: dragZone,
            target: target,
            event: event
        };

        this.modalDialogShown = true;
        this.application.handleEvents(eventData, sourceData);
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
Ext.ux.ETLRepoNavigationTreePanel = Ext.extend(Ext.tree.TreePanel, {
    application: undefined,
    //Etc
    mainTabPanel: undefined,
    mainEditorPanel_: undefined,
    repofolder_database_contextmenu: undefined,
    repoitem_database_contextmenu: undefined,
    new_repoitem_database_wizard: undefined,
    newdirText: 'New Folder',
    /*    onRender: function(){
     Ext.ux.ETLRepoNavigationTreePanel.superclass.onRender.apply(this, arguments);
     },

     onResize: function(){
     Ext.ux.ETLRepoNavigationTreePanel.superclass.onResize.apply(this, arguments);
     },*/

    initComponent: function () {
        Ext.apply(this, {
        });
        Ext.ux.ETLRepoNavigationTreePanel.superclass.initComponent.apply(this, arguments);


        /**
         * Tree Loader
         */
        this.loader.on("beforeload", function (treeLoader, node) {
            //this.baseParams.category = node.attributes.category;
            //this.baseParams.userid = 'test';
            this.loader.baseParams.itemtype = undefined;
            this.loader.baseParams.folderObjectId = undefined;


            if (node.attributes.itemtype)
                this.loader.baseParams.itemtype = node.attributes.itemtype;
            if (node.attributes.folderObjectId)
                this.loader.baseParams.folderObjectId = node.attributes.folderObjectId;
        }, this);

        // {{{
        // install treeEditor event handlers
        this.treeEditor = new Ext.tree.TreeEditor(this, {
            allowBlank:false
            ,cancelOnEsc:true
            ,completeOnEnter:true
            ,ignoreNoChange:true
            ,selectOnFocus:this.selectOnEdit
        })
        //}}

        // {{{
        // install treeEditor event handlers
        if(this.treeEditor) {
            // do not enter edit mode on selected node click
            this.treeEditor.beforeNodeClick = function(node,e){return true;};

            // treeEditor event handlers
            this.treeEditor.on({
                complete:{scope:this, fn:this.onEditComplete}
                ,beforecomplete:{scope:this, fn:this.onBeforeEditComplete}
            });
        }
        // }}}
        // {{{
        // install event handlers
        this.on({dblclick:{scope:this, fn:this.onDblClick}
            ,beforenodedrop:{scope:this, fn:this.onBeforeNodeDrop}
            ,nodedrop:{scope:this, fn:this.onNodeDrop}
            ,nodedragover:{scope:this, fn:this.onNodeDragOver}
            //,beforenewdir:{scope:this, fn:this.onBeforeNewDir}
        });

        // }}}
        // {{{
        // add events
        this.addEvents(
            /**
             * @event beforerename
             * Fires after the user completes file name editing
             * but before the file is renamed. Return false to cancel the event
             * @param {Ext.ux.FileTreePanel} this
             * @param {Ext.tree.AsyncTreeNode} node being renamed
             * @param {String} newPath including file name
             * @param {String} oldPath including file name
             */
            'beforerename'
            /**
             * @event rename
             * Fires after the file has been successfully renamed
             * @param {Ext.ux.FileTreePanel} this
             * @param {Ext.tree.AsyncTreeNode} node that has been renamed
             * @param {String} newPath including file name
             * @param {String} oldPath including file name
             */
            ,'rename'
            /**
             * @event renamefailure
             * Fires after a failure when renaming file
             * @param {Ext.ux.FileTreePanel} this
             * @param {Ext.tree.AsyncTreeNode} node rename of which failed
             * @param {String} newPath including file name
             * @param {String} oldPath including file name
             */
            ,'renamefailure'
            /**
             * @event beforenewdir
             * Fires before new directory is created. Return false to cancel the event
             * @param {Ext.ux.FileTreePanel} this
             * @param {Ext.tree.AsyncTreeNode} node under which the new directory is being created
             */
            ,'beforenewdir'
            /**
             * @event newdir
             * Fires after the new directory has been successfully created
             * @param {Ext.ux.FileTreePanel} this
             * @param {Ext.tree.AsyncTreeNode} new node/directory that has been created
             */
            ,'newdir'
            /**
             * @event newdirfailure
             * Fires if creation of new directory failed
             * @param {Ext.ux.FileTreePanel} this
             * @param {String} path creation of which failed
             */
            ,'newdirfailure'
        ); // eo addEvents
        // }}}


        /**
         * Event registrations
         */
            //-- Editing
        this.application.registerOnEvent(ORYX.CONFIG.EVENT_ETL_METADATA_CREATED, this.onCreated.bind(this));
        this.application.registerOnEvent(ORYX.CONFIG.EVENT_ETL_METADATA_DELETED, this.onDeleted.bind(this));

        this.application.registerOnEvent(ORYX.CONFIG.EVENT_ETL_MODEL_CREATED, this.onModelCreated.bind(this));


        //-- Drag and Drop
        this.application.registerOnEvent(ORYX.CONFIG.EVENT_ETL_METADATA_DDROP, this.onMetadataDrop.bind(this));

        this.getSelectionModel().on({
            'beforeselect': function (sm, node) {
                return node.isLeaf();
            },
            'selectionchange': function (sm, node) {
                if (node) {
                    this.fireEvent('feedselect', node.attributes);
                }
                this.getTopToolbar().items.get('delete').setDisabled(!node);
            },
            scope: this
        });


        //-- Create context menu's
        /**
         * Database and Folders
         */
        this.repofolder_database_contextmenu = new Ext.menu.Menu({
            id: 'database.folder',
            items: [
                {
                    id: 'addDatabaseInFolder',
                    icon: '/etl/images/conxbi/etl/connection-new.png',
                    text: 'Add Database',
                    scope: this,
                    handler: function () {
                        this.ctxNode.select();
                        var eventData = {
                            type: ORYX.CONFIG.EVENT_ETL_METADATA_CREATE_PREFIX + 'DBConnection',
                            forceExecution: true
                        };
                        this.application.handleEvents(eventData, {
                                folderId: this.ctxNode.attributes['folderObjectId'],
                                sourceNavNodeId: this.ctxNode.id}
                        );
                    }.bind(this)
                },
                new Ext.menu.Separator({cmd:'sep-open'}),
                {
                    id: 'createFolder',
                    text: 'Create Folder',
                    icon: '/etl/images/conxbi/etl/folder_close.png',
                    scope: this,
                    handler: function () {
                        this.ctxNode.select();
                        //this.mainEditorPanel.removeAll();
                        Ext.apply(this.new_repoitem_folder_wizard, {ctxNode: this.ctxNode, mainEditorPanel: this.mainEditorPanel});
                        this.mainEditorPanel.setTitle("New Folder");
                        this.mainEditorPanel.add(this.new_repoitem_folder_wizard);
                        this.mainTabPanel.add(this.mainEditorPanel);
                        this.mainTabPanel.setActiveTab(this.mainEditorPanel);
                        //this.newDBWiz.show();
                    }.bind(this)
                },
                {
                    id: 'deleteFolder',
                    text: 'Delete Folder',
                    icon: '/etl/images/conxbi/etl/folder_delete.png',
                    scope: this,
                    handler: function () {
                        var requestWiz = this;
                        var requestCtxNode = requestWiz.ctxNode;

                        var idArray = requestCtxNode.id.split('/');
                        var dirId = idArray[1];
                        Ext.lib.Ajax.request = Ext.lib.Ajax.request.createInterceptor(function (method, uri, cb, data, options) {
                            // here you can put whatever you need as header. For instance:
                            //this.defaultPostHeader = "application/json; charset=utf-8;";
                            this.defaultHeaders = {
                                userid: 'test',
                                itemtype: 'database',
                                folderObjectId: dirId
                            };
                        });
                        Ext.Ajax.request({
                            url: '/etl/core/explorer/deletedir',
                            method: 'DELETE',
                            success: function (response, opts) {
                                //Refresh this.ctxNode
                                if (requestCtxNode.attributes)
                                    requestCtxNode.attributes.children = false;
                                requestCtxNode.reload();
                            },
                            failure: function (response, opts) {
                            }
                        });
                    }.bind(this)
                },
                {
                    id: 'refreshFolder',
                    text: 'Refresh Folder',
                    icon: '/etl/images/conxbi/etl/refresh.gif',
                    scope: this,
                    handler: function () {
                        this.ctxNode.attributes.children = false;
                        this.ctxNode.reload();
                    }
                }

            ]
        });
        this.repofolder_database_contextmenu.on('hide', this.onContextHide, this);

        this.repoitem_database_contextmenu = new Ext.menu.Menu({
            id: 'database',
            items: [
                {
                    id: 'addDatabase',
                    icon: '/etl/images/conxbi/etl/connection-new.png',
                    text: 'Add Database',
                    scope: this,
                    handler: function () {
                        this.ctxNode.select();
                        var eventData = {
                            type: ORYX.CONFIG.EVENT_ETL_METADATA_CREATE_PREFIX + 'DBConnection',
                            forceExecution: true
                        };
                        this.application.handleEvents(eventData, {
                                folderId: this.ctxNode.attributes['folderObjectId'],
                                sourceNavNodeId: this.ctxNode.id}
                        );
                    }.bind(this)
                },
                {
                    id: 'editDatabase',
                    text: 'Edit Database',
                    icon: '/etl/images/conxbi/etl/modify.gif',
                    scope: this,
                    handler: function () {
                        this.ctxNode.select();
                        var eventData = {
                            type: ORYX.CONFIG.EVENT_ETL_METADATA_EDIT_PREFIX + 'DBConnection',
                            forceExecution: true
                        };
                        this.application.handleEvents(eventData, {
                                title: 'DB Connection ' + this.ctxNode.attributes['title'],
                                sourceNavNodeId: this.ctxNode.id
                            }
                        );
                    }.bind(this)
                },
                {
                    text: 'deleteDatabase',
                    text: 'Delete Database',
                    icon: '/etl/images/conxbi/etl/connection-delete.png',
                    scope: this,
                    handler: function () {
                        this.ctxNode.select();
                        var eventData = {
                            type: ORYX.CONFIG.EVENT_ETL_METADATA_DELETE_PREFIX + 'DBConnection',
                            forceExecution: true
                        };
                        this.application.handleEvents(eventData, {
                                title: 'DB Connection ' + this.ctxNode.attributes['title'],
                                sourceNavNodeId: this.ctxNode.id,
                                parentSourceNavNodeId: this.ctxNode.parentNode.id
                            }
                        );
                        this.ctxNode = null;
                    }.bind(this)
                },
                new Ext.menu.Separator({cmd:'sep-open'}),
                {
                    id: 'createFolder',
                    text: 'Create Folder',
                    icon: '/etl/images/conxbi/etl/folder_close.png',
                    scope: this,
                    handler: function () {
                        this.ctxNode.select();
                        //this.mainEditorPanel.removeAll();
                        Ext.apply(this.new_repoitem_folder_wizard, {ctxNode: this.ctxNode, mainEditorPanel: this.mainEditorPanel});
                        this.mainEditorPanel.setTitle("New Folder");
                        this.mainEditorPanel.add(this.new_repoitem_folder_wizard);
                        this.mainTabPanel.add(this.mainEditorPanel);
                        this.mainTabPanel.setActiveTab(this.mainEditorPanel);
                        //this.newDBWiz.show();
                    }.bind(this)
                }
            ]
        });
        this.repoitem_database_contextmenu.on('hide', this.onContextHide, this);

        /**
         * CSVMeta and Folders
         */
        this.repofolder_csvmeta_contextmenu = new Ext.menu.Menu({
            id: 'csvmeta.folder',
            items: [
                {
                    id: 'addCsvMeta',
                    icon: '/etl/images/conxbi/etl/icon_delimited.gif',
                    text: 'Add CSV Metadata',
                    scope: this,
                    handler: function () {
                        this.ctxNode.select();
                        var eventData = {
                            type: ORYX.CONFIG.EVENT_ETL_METADATA_CREATE_PREFIX + 'CSVMeta',
                            forceExecution: true
                        };
                        this.application.handleEvents(eventData, {
                                folderId: this.ctxNode.attributes['folderObjectId'],
                                sourceNavNodeId: this.ctxNode.id}
                        );
                    }.bind(this)
                },
                new Ext.menu.Separator({cmd:'sep-open'}),
                {
                    id: 'createFolder',
                    text: 'Create Folder',
                    icon: '/etl/images/conxbi/etl/folder_close.png',
                    scope: this,
                    handler: function () {
                        this.ctxNode.select();
                        //this.mainEditorPanel.removeAll();
                        Ext.apply(this.new_repoitem_folder_wizard, {ctxNode: this.ctxNode, mainEditorPanel: this.mainEditorPanel});
                        this.mainEditorPanel.setTitle("New Folder");
                        this.mainEditorPanel.add(this.new_repoitem_folder_wizard);
                        this.mainTabPanel.add(this.mainEditorPanel);
                        this.mainTabPanel.setActiveTab(this.mainEditorPanel);
                        //this.newDBWiz.show();
                    }.bind(this)
                },
                {
                    id: 'deleteFolder',
                    text: 'Delete Folder',
                    icon: '/etl/images/conxbi/etl/folder_delete.png',
                    scope: this,
                    handler: function () {
                        var requestWiz = this;
                        var requestCtxNode = requestWiz.ctxNode;

                        var idArray = requestCtxNode.id.split('/');
                        var dirId = idArray[1];
                        Ext.lib.Ajax.request = Ext.lib.Ajax.request.createInterceptor(function (method, uri, cb, data, options) {
                            // here you can put whatever you need as header. For instance:
                            //this.defaultPostHeader = "application/json; charset=utf-8;";
                            this.defaultHeaders = {
                                userid: 'test',
                                itemtype: 'csvmeta',
                                folderObjectId: dirId
                            };
                        });
                        Ext.Ajax.request({
                            url: '/etl/core/explorer/deletedir',
                            method: 'DELETE',
                            success: function (response, opts) {
                                //Refresh this.ctxNode
                                if (requestCtxNode.attributes)
                                    requestCtxNode.attributes.children = false;
                                requestCtxNode.reload();
                            },
                            failure: function (response, opts) {
                            }
                        });
                    }.bind(this)
                },
                {
                    id: 'refreshFolder',
                    text: 'Refresh Folder',
                    icon: '/etl/images/conxbi/etl/refresh.gif',
                    scope: this,
                    handler: function () {
                        this.ctxNode.attributes.children = false;
                        this.ctxNode.reload();
                    }.bind(this)
                }
            ]
        })
        this.repofolder_csvmeta_contextmenu.on('hide', this.onContextHide, this);

        //{{
        //  Transformations and Folders
        //}}
        this.repoitem_transformation_contextmenu = new Ext.menu.Menu({
            id: 'transformation',
            items: [
                {
                    id: 'addTransformation',
                    icon: '/etl/images/conxbi/etl/icon_delimited.gif',
                    text: 'Add Transformation',
                    scope: this,
                    handler: function () {
                        this.application.newTransformation();
/*                        this.ctxNode.select();
                        var eventData = {
                            type: ORYX.CONFIG.EVENT_ETL_MODEL_CREATE_PREFIX + 'Transformation',
                            forceExecution: true
                        };
                        this.application.handleEvents(eventData, {
                                folderId: this.ctxNode.attributes['folderObjectId'],
                                sourceNavNodeId: this.ctxNode.id}
                        );*/
                    }.bind(this)
                },
                {
                    id: 'editTransformation',
                    text: 'Edit Transformation',
                    icon: '/etl/images/conxbi/etl/modify.gif',
                    scope: this,
                    handler: function () {
                        Ext.lib.Ajax.request = Ext.lib.Ajax.request.createInterceptor(function (method, uri, cb, data, options) {
                            // here you can put whatever you need as header. For instance:
                            //this.defaultPostHeader = "application/json; charset=utf-8;";
                            this.defaultHeaders = {
                                userid: 'test'
                            };
                        });
                        var node_ = this.ctxNode;
                        var application_ = this.application;
                        Ext.Ajax.request({
                            url: '/etl/core/transmeta/get',
                            method: 'GET',
                            params: {
                                objectId: this.ctxNode.id
                            },
                            success: function (response, opts) {
                               var data = Ext.decode(response.responseText);;
                                application_.editTransformation(data.name,data.pathId,data.subDirPathId,data.jsonModel);
                            },
                            failure: function (response, opts) {
                            }
                        });
                    }.bind(this)
                },
                {
                    id: 'deleteTransformation',
                    text: 'Delete Transformation',
                    icon: '/etl/images/conxbi/etl/connection-delete.png',
                    scope: this,
                    handler: function () {
                        Ext.lib.Ajax.request = Ext.lib.Ajax.request.createInterceptor(function (method, uri, cb, data, options) {
                            // here you can put whatever you need as header. For instance:
                            //this.defaultPostHeader = "application/json; charset=utf-8;";
                            this.defaultHeaders = {
                                userid: 'test'
                            };
                        });
                        var parentNode_ = this.ctxNode.parentNode;
                        Ext.Ajax.request({
                            url: '/etl/core/transmeta/delete',
                            method: 'POST',
                            params: {
                                objectId: this.ctxNode.id
                            },
                            success: function (response, opts) {
                                //Refresh this.ctxNode
                                if (parentNode_.attributes)
                                    parentNode_.attributes.children = false;
                                parentNode_.reload();
                            },
                            failure: function (response, opts) {
                            }
                        });
                    }.bind(this)
                },
                new Ext.menu.Separator({cmd:'sep-open'}),
                {
                    text: 'Create Folder',
                    icon: '/etl/images/conxbi/etl/folder_close.png',
                    scope: this,
                    handler: function () {
                        this.ctxNode.select();
                        //this.mainEditorPanel.removeAll();
                        Ext.apply(this.new_repoitem_folder_wizard, {ctxNode: this.ctxNode, mainEditorPanel: this.mainEditorPanel});
                        this.mainEditorPanel.setTitle("New Folder");
                        this.mainEditorPanel.add(this.new_repoitem_folder_wizard);
                        this.mainTabPanel.add(this.mainEditorPanel);
                        this.mainTabPanel.setActiveTab(this.mainEditorPanel);
                        //this.newDBWiz.show();
                    }.bind(this)
                }
            ]
        });
        this.repoitem_transformation_contextmenu.on('hide', this.onContextHide, this);

        this.repofolder_transformation_contextmenu = new Ext.menu.Menu({
            id: 'transformation.folder',
            items: [
                {
                    id: 'addTransformationToFolder',
                    icon: '/etl/images/conxbi/etl/icon_delimited.gif',
                    text: 'Add Transformation',
                    scope: this,
                    handler: function () {
                        this.application.newTransformation(this.ctxNode.id);
                    }.bind(this)
                },
                new Ext.menu.Separator({cmd:'sep-open'}),
                {
                    id: 'createFolder',
                    text: 'Create Folder',
                    icon: '/etl/images/conxbi/etl/folder_close.png',
                    scope: this,
                    handler: function () {
                        //this.ctxNode.select();
                        this.createNewDir(this.ctxNode,{
                            url:'/etl/core/explorer/adddir',
                            itemtype: 'transformation'
                        });
                    }.bind(this)
                },
                {
                    id: 'deleteFolder',
                    text: 'Delete Folder',
                    icon: '/etl/images/conxbi/etl/folder_delete.png',
                    scope: this,
                    handler: function () {
                        //this.ctxNode.select();
                        this.deleteDir(this.ctxNode,{
                            url:'/etl/core/explorer/deldir',
                            itemtype: 'transformation'
                        });
                    }.bind(this)
                },
                {
                    id: 'refreshFolder',
                    text: 'Refresh Folder',
                    icon: '/etl/images/conxbi/etl/refresh.gif',
                    scope: this,
                    handler: function () {
                        this.ctxNode.attributes.children = false;
                        this.ctxNode.reload();
                    }.bind(this)
                }
            ]
        })
        this.repofolder_transformation_contextmenu.on('hide', this.onContextHide, this);

/*        this.repoitem_csvmeta_contextmenu = new Ext.menu.Menu({
            id: 'csvmeta',
            items: [
                {
                    id: 'addCsvMetadata',
                    icon: '/etl/images/conxbi/etl/icon_delimited.gif',
                    text: 'Add CSV Metadata',
                    scope: this,
                    handler: function () {
                        this.ctxNode.select();
                        var eventData = {
                            type: ORYX.CONFIG.EVENT_ETL_METADATA_CREATE_PREFIX + 'CSVMeta',
                            forceExecution: true
                        };
                        this.application.handleEvents(eventData, {
                                folderId: this.ctxNode.attributes['folderObjectId'],
                                sourceNavNodeId: this.ctxNode.id}
                        );
                    }.bind(this)
                },
                {
                    id: 'editCsvMetadata',
                    text: 'Edit CSV Metadata',
                    icon: '/etl/images/conxbi/etl/modify.gif',
                    scope: this,
                    handler: function () {
                        this.ctxNode.select();
                        var eventData = {
                            type: ORYX.CONFIG.EVENT_ETL_METADATA_EDIT_PREFIX + 'CSVMeta',
                            forceExecution: true
                        };
                        this.application.handleEvents(eventData, {
                                title: 'CSV Metadata ' + this.ctxNode.attributes['title'],
                                sourceNavNodeId: this.ctxNode.id
                            }
                        );
                    }.bind(this)
                },
                {
                    id: 'deleteCsvMetadata',
                    text: 'Delete CSV Metadata',
                    icon: '/etl/images/conxbi/etl/connection-delete.png',
                    scope: this,
                    handler: function () {
                        this.ctxNode.select();
                        var eventData = {
                            type: ORYX.CONFIG.EVENT_ETL_METADATA_DELETE_PREFIX + 'CSVMeta',
                            forceExecution: true
                        };
                        this.application.handleEvents(eventData, {
                                title: 'CSV Metadata ' + this.ctxNode.attributes['title'],
                                sourceNavNodeId: this.ctxNode.id,
                                parentSourceNavNodeId: this.ctxNode.parentNode.id
                            }
                        );
                        this.ctxNode = null;
                    }.bind(this)
                },
                new Ext.menu.Separator({cmd:'sep-open'}),
                {
                    text: 'Create Folder',
                    icon: '/etl/images/conxbi/etl/folder_close.png',
                    scope: this,
                    handler: function () {
                        this.ctxNode.select();
                        //this.mainEditorPanel.removeAll();
                        Ext.apply(this.new_repoitem_folder_wizard, {ctxNode: this.ctxNode, mainEditorPanel: this.mainEditorPanel});
                        this.mainEditorPanel.setTitle("New Folder");
                        this.mainEditorPanel.add(this.new_repoitem_folder_wizard);
                        this.mainTabPanel.add(this.mainEditorPanel);
                        this.mainTabPanel.setActiveTab(this.mainEditorPanel);
                        //this.newDBWiz.show();
                    }.bind(this)
                }
            ]
        });
        this.repoitem_csvmeta_contextmenu.on('hide', this.onContextHide, this);*/


        //Wizards
        try {
            //-- Register events
            this.addEvents({feedselect: true});

            this.on('contextmenu', this.onContextMenu, this);
        }
        catch (e) {
            ORYX.Log.error(e);
        }
    }
    // {{{
    /**
     * called before editing is completed - allows edit cancellation
     * @private
     * @param {TreeEditor} editor
     * @param {String} newName
     * @param {String} oldName
     */
    ,onBeforeEditComplete:function(editor, newName, oldName) {
        if(editor.cancellingEdit) {
            editor.cancellingEdit = false;
            return;
        }

        var oldPath = this.getPath(editor.editNode);
        var newPath = oldPath.replace(/\/[^\\]+$/, '/' + newName);

        if(false === this.fireEvent('beforerename', this, editor.editNode, newPath, oldPath)) {
            editor.cancellingEdit = true;
            editor.cancelEdit();
            return false;
        }
    }
    // }}}
// {{{
    /**
     * runs when editing of a node (rename) is completed
     * @private
     * @param {Ext.Editor} editor
     * @param {String} newName
     * @param {String} oldName
     */
    ,onEditComplete:function(editor, newName, oldName) {

        var node = editor.editNode;

        if(newName === oldName || editor.creatingNewDir) {
            editor.creatingNewDir = false;
            return;
        }
        var path = this.getPath(node.parentNode);
        var options = {
            url:this.renameUrl || this.url
            ,method:this.method
            ,scope:this
            ,callback:this.cmdCallback
            ,node:node
            ,oldName:oldName
            ,params:{
                cmd:'rename'
                ,oldname:path + '/' + oldName
                ,newname:path + '/' + newName
            }
        };
        Ext.Ajax.request(options);
    }
    // }}}
    // {{{
    /**
     * deletes the passed node
     * @private
     * @param {Ext.tree.AsyncTreeNode} node
     */
    ,deleteDir:function(node,params) {
        // fire beforedelete event
        if(true !== this.eventsSuspended && false === this.fireEvent('beforedelete', this, node)) {
            return;
        }

        Ext.Msg.show({
            title:'Confirm'
            ,msg:'Do you really want to delete <b>' + node.text + '</b>?'
            ,icon:Ext.Msg.WARNING
            ,buttons:Ext.Msg.YESNO
            ,scope:this
            ,fn:function(response) {
                // do nothing if answer is not yes
                if('yes' !== response) {
                    this.getEl().dom.focus();
                    return;
                }
                var url =  params.url;
                var itemtype =   params.itemtype;

                Ext.lib.Ajax.request = Ext.lib.Ajax.request.createInterceptor(function (method, uri, cb, data, options) {
                    // here you can put whatever you need as header. For instance:
                    this.defaultPostHeader = "application/json; charset=utf-8;";
                    this.defaultHeaders = {
                        userid: 'test'
                    };
                });
                var idArray = node.id.split('/');   //'/dir/##
                var dirId = idArray[2];
                Ext.Ajax.request({
                    url: url,
                    method: 'POST',
                    params: Ext.encode({
                        name: node.text,
                        dirObjectId: dirId,
                        itemtype: itemtype
                    }),
                    success: function (response, opts) {
                        if (node.parentNode.attributes)
                            node.parentNode.attributes.children = false;
                        node.parentNode.reload();

                        Ext.ux.Toast.msg('Deletion success.', 'Folder '+node.text+' deleted.');
                    },
                    failure: function (response, opts) {
                    }.bind(this)
                });
            }
        });
    } // eo function deleteNode
    // {{{
    /**
     * creates new directory (node)
     * @private
     * @param {Ext.tree.AsyncTreeNode} node
     */
    ,createNewDir:function(node,params) {

        // fire beforenewdir event
        if(true !== this.eventsSuspended && false === this.fireEvent('beforenewdir', this, node)) {
            return;
        }

        var treeEditor = this.treeEditor;
        var newNode;

        // get node to append the new directory to
        var appendNode = node.isLeaf() ? node.parentNode : node;

        // create new folder after the appendNode is expanded
        appendNode.expand(false, false, function(n) {
            // create new node
            newNode = n.appendChild(new Ext.tree.AsyncTreeNode({text:this.newdirText, iconCls:'folder'}));
            newNode.attributes['params'] = params;

            // setup one-shot event handler for editing completed
            treeEditor.on({
                    complete:{
                        scope:this
                        ,single:true
                        ,fn:this.onNewDir
                    }}
            );

            // creating new directory flag
            treeEditor.creatingNewDir = true;

            // start editing after short delay
            (function(){treeEditor.triggerEdit(newNode);}.defer(10));
            // expand callback needs to run in this context
        }.createDelegate(this));

    } // eo function creatingNewDir
    // }}}
    // {{{
    /**
     * create new directory handler
     * @private
     * runs after editing of new directory name is completed
     * @param {Ext.Editor} editor
     */
    ,onNewDir:function(editor) {
        var path = this.getPath(editor.editNode);
        var parentPathId =  editor.editNode.parentNode.id;
        if (parentPathId.indexOf('/') >= 0) {
            var idArray = parentPathId.split('/');   //'/dir/##
            parentPathId = idArray[2];
        }
        else {
            parentPathId = !isNaN(parseFloat(parentPathId)) && isFinite(parentPathId) ? parentPathId : -1;
        }
        var url = editor.editNode.attributes.params.url;
        var itemtype =  editor.editNode.attributes.params.itemtype;

        Ext.lib.Ajax.request = Ext.lib.Ajax.request.createInterceptor(function (method, uri, cb, data, options) {
            // here you can put whatever you need as header. For instance:
            this.defaultPostHeader = "application/json; charset=utf-8;";
            this.defaultHeaders = {
                userid: 'test'
            };
        });
        Ext.Ajax.request({
            url: url,
            method: 'POST',
            params: Ext.encode({
                name: editor.editNode.text,
                dirObjectId: parentPathId,
                itemtype: itemtype
            }),
            success: function (response, opts) {
                //Refresh this.ctxNode
                var record = Ext.decode(response.responseText);
                if (editor.editNode.parentNode.attributes)
                    editor.editNode.parentNode.attributes.children = false;

                if (record.requestMsg)
                    Ext.ux.Toast.msg('Folder creation failed.', 'Folder '+editor.editNode.text+' already exists');

                editor.editNode.parentNode.reload();
/*
                editor.editNode.id = record.dirObjectId;
                editor.editNode.icon = record.icon;
                editor.editNode.text = record.name;
                editor.editNode.hasChildren = false;
                editor.editNode.allowDrag = false;
                editor.editNode.allowDrop = false;
                editor.editNode.attributes.ddenabled = true;

                editor.editNode.children = [];

                editor.editNode.setText(record.name);*/
            },
            failure: function (response, opts) {
                //var msg = response.responseText;
                var record = Ext.decode(response.responseText);
                if (record.requestMsg)
                    Ext.ux.Toast.msg('Folder creation failed.', 'Folder '+editor.editNode.text+' already exists');
            }.bind(this)
        });
    }
    // }}}
    // {{{
    /**
     * returns path of node (file/directory)
     * @private
     */
    ,getPath:function(node) {
        var path, p, a;

        // get path for non-root node
        if(node !== this.root) {
            p = node.parentNode;
            a = [node.text];
            while(p && p !== this.root) {
                a.unshift(p.text);
                p = p.parentNode;
            }
            a.unshift(this.root.attributes.path || '');
            path = a.join(this.pathSeparator);
        }

        // path for root node is it's path attribute
        else {
            path = node.attributes.path || '';
        }

        // a little bit of security: strip leading / or .
        // full path security checking has to be implemented on server
        path = path.replace(/^[\/\.]*/, '');
        return path;
    } // eo function getPath
    // }}}
    //{{{
    /**
     * On Created
     * @param event
     */
    ,onCreated: function (event) {
        var ctxNodeId = event.treeNodeParentId;
        var name = event.name;

        var ctxNode_ = this.getNodeById(ctxNodeId);
        ctxNode_.select();

        Ext.MessageBox.show({
            title: 'Saved',
            msg: name + ' was created successfully.',
            buttons: Ext.MessageBox.OK,
            icon: Ext.MessageBox.INFO
        });

        ctxNode_.reload();
    },
    /**
     * On Model Created
     * @param event
     */
    onModelCreated: function (event) {
        var name = event.title;
        var ctxNodeId = event.dirPathId;


        var ctxNode_ = this.getNodeById(ctxNodeId);
        ctxNode_.select();

        ctxNode_.attributes.children = false;

        Ext.MessageBox.show({
            title: 'Saved',
            msg: name + ' was created successfully.',
            buttons: Ext.MessageBox.OK,
            icon: Ext.MessageBox.INFO
        });

        ctxNode_.reload();
    },
    /**
     * On Deleted
     * @param event
     */
    onDeleted: function (event) {
        var ctxNodeId = event.treeNodeParentId;
        var name = event.name;

        var ctxNode_ = this.getNodeById(ctxNodeId);
        ctxNode_.select();

        ctxNode_.reload();
    },
    /**
     * On MetadataDrop
     * @param event
     */
    onMetadataDrop: function (event, source) {
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
                            // Instanciated the class
                            var metadataType = dragZone.dragData.mainNode.attributes['itemtype'];
                            var metadataName = dragZone.dragData.mainNode.text;
                            var metadataObjId = dragZone.dragData.mainNode.id;
                            command = new commandClass(currentCanvasFacade, metadataType, metadataName, metadataObjId);
                            currentCanvasFacade.executeCommands([ command ]);


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
    },
    /**
     *
     * @param node
     * @param e
     */
    onContextMenu: function (node, e) {
        if (node.attributes.menugroup && node.attributes.menugroup === 'database') {
            this.menu = this.repoitem_database_contextmenu;
        }
        else if (node.attributes.menugroup && node.attributes.menugroup === 'database.folder') {
            this.menu = this.repofolder_database_contextmenu;
            if (node.id === 'metadata.dbconnections')
                this.getItemById(this.menu,'deleteFolder').setDisabled(true);
        }
        else if (node.attributes.menugroup && node.attributes.menugroup === 'csvmeta') {
            this.menu = this.repoitem_csvmeta_contextmenu;
        }
        else if (node.attributes.menugroup && node.attributes.menugroup === 'csvmeta.folder') {
            this.menu = this.repofolder_csvmeta_contextmenu;
            if (node.id === 'metadata.csvmeta')
                this.getItemById(this.menu,'deleteFolder').setDisabled(true);
        }
        else if (node.attributes.menugroup && node.attributes.menugroup === 'excelmeta') {
            this.menu = this.repoitem_excelmeta_contextmenu;
        }
        else if (node.attributes.menugroup && node.attributes.menugroup === 'excelmeta.folder') {
            this.menu = this.repofolder_csvmeta_contextmenu;
            if (node.id === 'metadata.excelmeta')
                this.getItemById(this.menu,'deleteFolder').setDisabled(true);
        }
        else if (node.attributes.menugroup && node.attributes.menugroup === 'transformation') {
            this.menu = this.repoitem_transformation_contextmenu;
        }
        else if (node.attributes.menugroup && node.attributes.menugroup === 'transformation.folder') {
            this.menu = this.repofolder_transformation_contextmenu;
        }
        else
            return;


        //if(node.isLeaf()){
        this.ctxNode = node;
        this.ctxNode.ui.addClass('x-node-ctx');
        //if (this.menu.items)
        //    this.menu.items.get('load').setDisabled(node.isSelected());
        this.menu.showAt(e.getXY());
        //}
    },
    getItemById:function(menu,id) {
        var open;
        var item = menu.items.find(function(i) {
            return id === i.id;
        });

        return item;
    },
    // eo function getItemByCmd
    onContextHide: function () {
        if (this.ctxNode) {
            this.ctxNode.ui.removeClass('x-node-ctx');
            this.ctxNode = null;
        }
    },

    // prevent the default context menu when you miss the node
    afterRender: function () {
        Ext.ux.ETLRepoNavigationTreePanel.superclass.afterRender.call(this);
        this.el.on('contextmenu', function (e) {
            e.preventDefault();
        });
    },
    menuGroups : {
        database: [
            'addDatabase',
            'editDatabase',
            'deleteDatabase'
        ],
        csvmeta: [
            'addCsvMeta',
            'editCsvMeta',
            'deleteCsvMeta'
        ],
        excelmeta: [
            'addExcelMeta',
            'editExcelMeta',
            'deleteExcelMeta'
        ],
        folder: [
            'addFolder',
            'editFolder',
            'deleteFolder',
            'refreshFolder'
        ]
    }
});

