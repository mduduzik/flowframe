if (!ORYX.Plugins)
    ORYX.Plugins = new Object();

ORYX.Plugins.ETLRepoNavigation = Clazz.extend({

    // Defines the facade
    facade                : undefined,

    // Defines the undo/redo Stack
    navigationPanel     : undefined,

    // Constructor
    construct: function(facade,ownPluginData){

        this.facade = facade;

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
                dataUrl:'http://localhost:8080/etlrepo/explorer/getnode',
                preloadChildren: false,
                baseParams: {
                    userid: 'userid',
                    itemtype: 'undefined',
                    folderObjectId: 'undefined'
                },
                listeners: {
                    load: function(loader,node,response) {
                        console.log('data loaded');
                        node.expandChildNodes();
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
        }
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
         * Init the rest
         */
        //-- Event listening
        this.loader.on("beforeload", function(treeLoader, node) {
            //this.baseParams.category = node.attributes.category;
            //this.baseParams.userid = 'test';
            if (node.attributes.itemtype)
                this.loader.baseParams.itemtype = node.attributes.itemtype;
            if (node.attributes.folderObjectId)
                this.loader.baseParams.folderObjectId = node.attributes.folderObjectId;
        }, this);

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
                icon: '/oryx/images/conxbi/etl/connection-new.png',
                text:'Add Database',
                scope: this,
                handler:function(){
                    this.ctxNode.select();
                    //this.mainEditorPanel.removeAll();
                    Ext.apply(this.new_repoitem_database_wizard,{ctxNode:this.ctxNode,mainEditorPanel:this.mainEditorPanel});
                    this.mainEditorPanel.setTitle("New Database");
                    this.mainEditorPanel.add(this.new_repoitem_database_wizard);
                    this.mainTabPanel.add(this.mainEditorPanel);
                    this.mainTabPanel.setActiveTab(this.mainEditorPanel);
                    //this.new_repoitem_database_wizard.show();
                }
            },{
                text:'Create Folder',
                icon: '/oryx/images/conxbi/etl/folder_close.png',
                scope: this,
                handler:function(){
                    this.ctxNode.select();
                    //this.mainEditorPanel.removeAll();
                    Ext.apply(this.new_repoitem_folder_wizard,{ctxNode:this.ctxNode,mainEditorPanel:this.mainEditorPanel});
                    this.mainEditorPanel.setTitle("New Folder");
                    this.mainEditorPanel.add(this.new_repoitem_folder_wizard);
                    this.mainTabPanel.add(this.mainEditorPanel);
                    this.mainTabPanel.setActiveTab(this.mainEditorPanel);
                    //this.new_repoitem_database_wizard.show();
                }
            },{
                text:'Delete Folder',
                icon: '/oryx/images/conxbi/etl/folder_delete.png',
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
                }
            },{
                text:'Refresh Folder',
                icon: '/oryx/images/conxbi/etl/refresh.gif',
                scope: this,
                handler:function(){
                    this.ctxNode.attributes.children = false;
                    this.ctxNode.reload();
                }
            }
            ]
        });
        this.repofolder_database_contextmenu.on('hide', this.onContextHide, this);

        this.repoitem_database_contextmenu = new Ext.menu.Menu({
            id:'feeds-ctx',
            items: [{
                id: 'load',
                icon: '/oryx/images/conxbi/etl/connection-new.png',
                text:'Add Database',
                scope: this,
                handler:function(){
                    this.ctxNode.select();
                    //this.mainEditorPanel.removeAll();
                    Ext.apply(this.new_repoitem_database_wizard,{ctxNode:this.ctxNode,mainEditorPanel:this.mainEditorPanel});
                    this.mainEditorPanel.setTitle("New Database");
                    this.mainEditorPanel.add(this.new_repoitem_database_wizard);
                    this.mainTabPanel.setActiveTab(this.mainEditorPanel);
                    //this.new_repoitem_database_wizard.show();
                }
            },{
                text:'Edit Database',
                icon: '/oryx/images/conxbi/etl/modify.gif',
                scope: this,
                handler:function(){
                    this.ctxNode.ui.removeClass('x-node-ctx');
                    this.removeFeed(this.ctxNode.attributes.url);
                    this.ctxNode = null;
                }
            },{
                text:'Delete Database',
                icon: '/oryx/images/conxbi/etl/connection-delete.png',
                scope: this,
                handler:function(){
                    this.ctxNode.ui.removeClass('x-node-ctx');
                    this.removeFeed(this.ctxNode.attributes.url);
                    this.ctxNode = null;
                }
            },{
                text:'Create Folder',
                icon: '/oryx/images/conxbi/etl/folder_close.png',
                scope: this,
                handler:function(){
                    this.ctxNode.select();
                    //this.mainEditorPanel.removeAll();
                    Ext.apply(this.new_repoitem_folder_wizard,{ctxNode:this.ctxNode,mainEditorPanel:this.mainEditorPanel});
                    this.mainEditorPanel.setTitle("New Folder");
                    this.mainEditorPanel.add(this.new_repoitem_folder_wizard);
                    this.mainTabPanel.add(this.mainEditorPanel);
                    this.mainTabPanel.setActiveTab(this.mainEditorPanel);
                    //this.new_repoitem_database_wizard.show();
                }
            }]
        });
        this.repoitem_database_contextmenu.on('hide', this.onContextHide, this);

        //Wizards
        try {
            this.new_repoitem_folder_wizard = new Ext.ux.Wiz({
                headerConfig: {
                    title: ''
                },
                cardPanelConfig: {
                    defaults: {
                        baseCls: 'x-small-editor',
                        bodyStyle: 'padding: 20px 15px 5px 5px; background-color:#F6F6F6;',
                        border: false
                    }
                },
                getSelectWizardData : function(cardids)
                {
                    var formValues = {};
                    var cards = this.cards;
                    for (var i = 0, len = cards.length; i < len; i++) {

                        var cardform = cards[i].form;
                        if (cardform) {
                            var values = cardform.getValues(false);
                            Object.extend(formValues,values);
                        }
                    }
                    return formValues;
                },

                onFinish: function() {
                    var requestWiz = this;
                    var requestCtxNode =  requestWiz.ctxNode;
                    var requestMainEditorPanel = requestWiz.mainEditorPanel;
                    var requestMainTabPanel = requestWiz.mainTabPanel;
                    if (requestWiz.wizmode && requestWiz.wizmode === 'EDITING') {

                    }
                    else {
                        var dirId = requestCtxNode.attributes.folderObjectId;
                        var data = this.getSelectWizardData([]);
                        Object.extend(data,{dirObjectId:dirId,itemtype:requestCtxNode.attributes.itemtype});
                        var dataJson = Ext.encode(data);
                        Ext.lib.Ajax.request = Ext.lib.Ajax.request.createInterceptor(function(method, uri, cb, data, options){
                            // here you can put whatever you need as header. For instance:
                            this.defaultPostHeader = "application/json; charset=utf-8;";
                            this.defaultHeaders = {userid:'test'};
                        });
                        Ext.Ajax.request({
                            url: '/etlrepo/explorer/adddir',
                            method: 'POST',
                            params: dataJson,
                            success: function(response, opts) {
                                //Refresh this.ctxNode
                                if (requestCtxNode.attributes)
                                    requestCtxNode.attributes.children = false;
                                requestCtxNode.select();
                                requestCtxNode.ui.addClass('x-node-ctx');
                                requestCtxNode.reload();


                                //Update tab title
                                //requestMainEditorPanel.hide();

                                var dir = Ext.decode(response.responseText);
                                requestMainEditorPanel.setTitle("Editing "+dir.name);
                                requestWiz.finishButtonText = 'Save';
                                Object.extend(requestWiz,{wizmode:'EDITING'});
                            },
                            failure: function(response, opts) {}
                        });
                    }
                },
                cards: [
                    // second card with input fields last/firstname
                    new Ext.ux.Wiz.Card({
                        id: "enterfolderproperties",
                        title: 'Enter subfolder name',
                        monitorValid: true,
                        defaults: {
                            labelStyle: 'font-size:11px'
                        },
                        items: [
                            new Ext.form.TextField({
                                name: 'name',
                                fieldLabel: 'Name',
                                allowBlank: false
                            })
                        ]
                    })
                ]
            });


            this.new_repoitem_database_wizard = new Ext.ux.Wiz({
                headerConfig: {
                    title: 'Simple Wizard Example'
                },
                cardPanelConfig: {
                    defaults: {
                        baseCls: 'x-small-editor',
                        bodyStyle: 'padding: 20px 15px 5px 5px; background-color:#F6F6F6;',
                        border: false
                    }
                },
                getSelectWizardData : function(cardids)
                {
                    var formValues = {};
                    var cards = this.cards;
                    for (var i = 0, len = cards.length; i < len; i++) {

                        var cardform = cards[i].form;
                        if (cardform) {
                            var values = cardform.getValues(false);
                            Object.extend(formValues,values);
                        }
                    }
                    return formValues;
                },

                onFinish: function() {
                    var requestWiz = this;
                    var requestCtxNode =  requestWiz.ctxNode;
                    var requestMainEditorPanel = requestWiz.mainEditorPanel;
                    var requestMainTabPanel = requestWiz.mainTabPanel;
                    if (requestWiz.wizmode && requestWiz.wizmode === 'EDITING') {

                    }
                    else {
                        var idArray = requestCtxNode.id.split('/');
                        var dirId = idArray[1];
                        var data = this.getSelectWizardData([
                            'selectdatabasetype',
                            'jdbcsettings',
                            'auth']);
                        Object.extend(data,{dirObjectId:dirId});
                        var dataJson = Ext.encode(data);
                        Ext.lib.Ajax.request = Ext.lib.Ajax.request.createInterceptor(function(method, uri, cb, data, options){
                            // here you can put whatever you need as header. For instance:
                            this.defaultPostHeader = "application/json; charset=utf-8;";
                            this.defaultHeaders = {userid:'test'};
                        });
                        Ext.Ajax.request({
                            url: '/etlrepo/databasemeta/add',
                            method: 'POST',
                            params: dataJson,
                            success: function(response, opts) {
                                //Refresh this.ctxNode
                                if (requestCtxNode.attributes)
                                    requestCtxNode.attributes.children = false;
                                requestCtxNode.select();
                                requestCtxNode.ui.addClass('x-node-ctx');
                                requestCtxNode.reload();


                                //Update tab title
                                //requestMainEditorPanel.hide();

                                var db = Ext.decode(response.responseText);
                                requestMainEditorPanel.setTitle("Editing "+db.name);
                                requestWiz.finishButtonText = 'Save';
                                Object.extend(requestWiz,{wizmode:'EDITING'});
                            },
                            failure: function(response, opts) {}
                        });
                    }
                },
                cards: [
                    // second card with input fields last/firstname
                    new Ext.ux.Wiz.Card({
                        id: "selectdatabasetype",
                        title: 'Enter name and select database type',
                        monitorValid: true,
                        defaults: {
                            labelStyle: 'font-size:11px'
                        },
                        items: [
                            new Ext.form.TextField({
                                name: 'name',
                                fieldLabel: 'Name',
                                allowBlank: false
                            }),
                            new Ext.form.ComboBox({
                                name: 'databaseType',
                                fieldLabel: 'Database Type',
                                hiddenName:'databaseType',
                                store: new Ext.data.Store({
                                    id: "store",
                                    remoteSort: true,
                                    autoLoad: {params:{start:1, limit:2}},
                                    proxy: new Ext.data.ScriptTagProxy({
                                        url: 'http://localhost:8082/etlrepo/databasetype/search'
                                    }),
                                    reader: new Ext.data.JsonReader({
                                        root : 'data',
                                        totalProperty: 'totalCount'
                                    }, [
                                        {name: 'id', mapping: 'id'},
                                        {name: 'code', mapping: 'code'},
                                        {name: 'description', mapping: 'description'}
                                    ])
                                }),

                                valueField:'code',
                                displayField:'description',
                                typeAhead: true,
                                mode: 'local',
                                triggerAction: 'all',
                                emptyText:'Select a database type...',
                                selectOnFocus:true,
                                width:190
                            })
                        ]
                    }),
                    // JDBC Settings
                    new Ext.ux.Wiz.Card({
                        name: 'jdbcsettings',
                        title: 'Set the JDBC Settings',
                        monitorValid: true,
                        defaults: {
                            labelStyle: 'font-size:11px'
                        },
                        items: [
                            new Ext.form.TextField({
                                name: 'hostname',
                                fieldLabel: 'Hostname of database server',
                                allowBlank: false
                            }),
                            new Ext.form.NumberField({
                                name: 'databasePort',
                                fieldLabel: 'The TCP/IP Port',
                                allowBlank: true
                            }),
                            new Ext.form.TextField({
                                name: 'databaseName',
                                fieldLabel: 'The name of the database',
                                allowBlank: false
                            })
                        ]
                    }),
                    //Auth
                    new Ext.ux.Wiz.Card({
                        name: 'auth',
                        title: 'Enter username and password',
                        monitorValid: true,
                        defaults: {
                            labelStyle: 'font-size:11px'
                        },
                        items: [
                            new Ext.form.TextField({
                                name: 'username',
                                fieldLabel: 'Username',
                                allowBlank: false
                            }),
                            new Ext.form.TextField({
                                name: 'password',
                                fieldLabel: 'Password',
                                allowBlank: false,
                                inputType:"password"
                            })
                        ]
                    })

                ]
            });

            //-- Register events
            this.addEvents({feedselect:true});

            this.on('contextmenu', this.onContextMenu, this);
        }
        catch (e) {
            ORYX.Log.error(e);
        }
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

        if(this.ctxNode){
            this.ctxNode.ui.removeClass('x-node-ctx');
            this.ctxNode = null;
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

    showWindow : function(btn){
        if(!this.win){
            this.win = new FeedWindow();
            this.win.on('validfeed', this.addFeed, this);
        }
        this.win.show(btn);
    },

    selectFeed: function(url){
        this.getNodeById(url).select();
    },

    removeFeed: function(url){
        var node = this.getNodeById(url);
        if(node){
            node.unselect();
            Ext.fly(node.ui.elNode).ghost('l', {
                callback: node.remove, scope: node, duration: .4
            });
        }
    },

    addFeed : function(attrs, inactive, preventAnim){
        var exists = this.getNodeById(attrs.url);
        if(exists){
            if(!inactive){
                exists.select();
                exists.ui.highlight();
            }
            return;
        }
        Ext.apply(attrs, {
            iconCls: 'feed-icon',
            leaf:true,
            cls:'feed',
            id: attrs.url
        });
        var node = new Ext.tree.TreeNode(attrs);
        this.feeds.appendChild(node);
        if(!inactive){
            if(!preventAnim){
                Ext.fly(node.ui.elNode).slideIn('l', {
                    callback: node.select, scope: node, duration: .4
                });
            }else{
                node.select();
            }
        }
        return node;
    },
/*    onRender:function() {
        // call parent
        Ext.ux.ETLRepoNavigationTreePanel.superclass.onRender.apply(this, arguments);

        this.root.expand();
    },*/

    // prevent the default context menu when you miss the node
    afterRender : function(){
        Ext.ux.ETLRepoNavigationTreePanel.superclass.afterRender.call(this);
        this.el.on('contextmenu', function(e){
            e.preventDefault();
        });
    }
});
