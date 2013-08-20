Ext.app.SearchField = Ext.extend(Ext.form.TwinTriggerField, {
    initComponent : function(){
        Ext.app.SearchField.superclass.initComponent.call(this);
        this.on('specialkey', function(f, e){
            if(e.getKey() == e.ENTER){
                this.onTrigger2Click();
            }
        }, this);
    },

    validationEvent:false,
    validateOnBlur:false,
    trigger1Class:'x-form-clear-trigger',
    trigger2Class:'x-form-search-trigger',
    hideTrigger1:true,
    width:180,
    hasSearch : false,
    paramName : 'query',

    onTrigger1Click : function(){
        if(this.hasSearch){
            this.el.dom.value = '';
            var o = {start: 0};
            this.store.baseParams = this.store.baseParams || {};
            this.store.baseParams[this.paramName] = '';
            this.store.reload({params:o});
            this.triggers[0].hide();
            this.hasSearch = false;
        }
    },

    onTrigger2Click : function(){
        var v = this.getRawValue();
        if(v.length < 1){
            this.onTrigger1Click();
            return;
        }
        var o = {start: 0};
        this.store.baseParams = this.store.baseParams || {};
        this.store.baseParams[this.paramName] = v;
        this.store.reload({params:o});
        this.hasSearch = true;
        this.triggers[0].show();
    }
});

Ext.onReady(function(){

    Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

    /*
     //Job Artifact Wizard/Main Editor
     */
    var mainEditorTabPanel_ = new Ext.Panel({
        title: 'Editing Metadata (Excel FEDEX)',
        iconCls: 'tabs',
        closable:true,
        split       : true,
        bodyStyle:'padding:0px',
        layout: 'fit',
        collapsible : false,
        margins     : '3 0 3 3',
        cmargins    : '3 3 3 3',
        autoScroll  : false
    });

    /*
     //Job Editor Tab
     */
    //Center Panel
    var problemsGrid = new Ext.grid.PropertyGrid({
        title: 'Problems',
        closable: true,
        source: {
            "grouping": false,
            "autoFitColumns": true,
            "productionQuality": false,
            "created": new Date(Date.parse('10/15/2006')),
            "tested": false,
            "version": .01,
            "borderWidth": 1
        }
    });

    var selectedComponentForm = new Ext.FormPanel({
        title: 'Component',
        iconCls: 'tabs',
        closable:true,
        labelAlign: 'top',
        bodyStyle:'padding:5px',
        height: 320,
        items: {
            xtype:'tabpanel',
            activeTab: 0,
            defaults:{autoHeight:true, bodyStyle:'padding:10px'},
            items:[{
                title:'Personal Details',
                layout:'form',
                defaults: {width: 230},
                defaultType: 'textfield',

                items: [{
                    fieldLabel: 'First Name',
                    name: 'first',
                    allowBlank:false,
                    value: 'Jack'
                },{
                    fieldLabel: 'Last Name',
                    name: 'last',
                    value: 'Slocum'
                }]
            },{
                title:'Phone Numbers',
                layout:'form',
                defaults: {width: 230},
                defaultType: 'textfield',

                items: [{
                    fieldLabel: 'Home',
                    name: 'home',
                    value: '(888) 555-1212'
                },{
                    fieldLabel: 'Business',
                    name: 'business'
                },{
                    fieldLabel: 'Mobile',
                    name: 'mobile'
                },{
                    fieldLabel: 'Fax',
                    name: 'fax'
                }]
            }]
        },

        buttons: [{
            text: 'Save'
        },{
            text: 'Cancel'
        }]
    });

    var runDebugPanel = new Ext.FormPanel({
        title: 'Run/Debug (Job #1)',
        iconCls: 'tabs',
        closable:true,
        labelAlign: 'top',
        bodyStyle:'padding:5px',
        minHeight: 300,
        items: [{
            layout:'column',
            border:false,
            items:[{
                columnWidth:.5,
                layout: 'form',
                border:false,
                items: [{
                    xtype:'textfield',
                    fieldLabel: 'First Name',
                    name: 'first',
                    anchor:'95%'
                }, {
                    xtype:'textfield',
                    fieldLabel: 'Company',
                    name: 'company',
                    anchor:'95%'
                }]
            },{
                columnWidth:.5,
                layout: 'form',
                border:false,
                items: [{
                    xtype:'textfield',
                    fieldLabel: 'Last Name',
                    name: 'last',
                    anchor:'95%'
                },{
                    xtype:'textfield',
                    fieldLabel: 'Email',
                    name: 'email',
                    vtype:'email',
                    anchor:'95%'
                }]
            }]
        },{
            xtype:'tabpanel',
            plain:true,
            activeTab: 0,
            height:235,
            defaults:{bodyStyle:'padding:10px'},
            items:[{
                title:'Personal Details',
                layout:'form',
                defaults: {width: 230},
                defaultType: 'textfield',

                items: [{
                    fieldLabel: 'First Name',
                    name: 'first',
                    allowBlank:false,
                    value: 'Jack'
                },{
                    fieldLabel: 'Last Name',
                    name: 'last',
                    value: 'Slocum'
                },{
                    fieldLabel: 'Company',
                    name: 'company',
                    value: 'Ext JS'
                }, {
                    fieldLabel: 'Email',
                    name: 'email',
                    vtype:'email'
                }]
            },{
                title:'Phone Numbers',
                layout:'form',
                defaults: {width: 230},
                defaultType: 'textfield',

                items: [{
                    fieldLabel: 'Home',
                    name: 'home',
                    value: '(888) 555-1212'
                },{
                    fieldLabel: 'Business',
                    name: 'business'
                }]
            },{
                cls:'x-plain',
                title:'Biography',
                layout:'fit',
                items: {
                    xtype:'htmleditor',
                    id:'bio2',
                    fieldLabel:'Biography'
                }
            }]
        }],

        buttons: [{
            text: 'Save'
        },{
            text: 'Cancel'
        }]
    });

    var jobPropertiesForm = new Ext.FormPanel({
        title: 'Job (Job #1) Properties',
        iconCls: 'tabs',
        closable:true,
        labelAlign: 'top',
        bodyStyle:'padding:5px',
        minHeight: 300,
        items: [{
            layout:'column',
            border:false,
            items:[{
                columnWidth:.5,
                layout: 'form',
                border:false,
                items: [{
                    xtype:'textfield',
                    fieldLabel: 'First Name',
                    name: 'first',
                    anchor:'95%'
                }, {
                    xtype:'textfield',
                    fieldLabel: 'Company',
                    name: 'company',
                    anchor:'95%'
                }]
            },{
                columnWidth:.5,
                layout: 'form',
                border:false,
                items: [{
                    xtype:'textfield',
                    fieldLabel: 'Last Name',
                    name: 'last',
                    anchor:'95%'
                },{
                    xtype:'textfield',
                    fieldLabel: 'Email',
                    name: 'email',
                    vtype:'email',
                    anchor:'95%'
                }]
            }]
        },{
            xtype:'tabpanel',
            plain:true,
            activeTab: 0,
            height:235,
            defaults:{bodyStyle:'padding:10px'},
            items:[{
                title:'Personal Details',
                layout:'form',
                defaults: {width: 230},
                defaultType: 'textfield',

                items: [{
                    fieldLabel: 'First Name',
                    name: 'first',
                    allowBlank:false,
                    value: 'Jack'
                },{
                    fieldLabel: 'Last Name',
                    name: 'last',
                    value: 'Slocum'
                },{
                    fieldLabel: 'Company',
                    name: 'company',
                    value: 'Ext JS'
                }, {
                    fieldLabel: 'Email',
                    name: 'email',
                    vtype:'email'
                }]
            },{
                title:'Phone Numbers',
                layout:'form',
                defaults: {width: 230},
                defaultType: 'textfield',

                items: [{
                    fieldLabel: 'Home',
                    name: 'home',
                    value: '(888) 555-1212'
                },{
                    fieldLabel: 'Business',
                    name: 'business'
                }]
            },{
                cls:'x-plain',
                title:'Biography',
                layout:'fit',
                items: {
                    xtype:'htmleditor',
                    id:'bio2',
                    fieldLabel:'Biography'
                }
            }]
        }],

        buttons: [{
            text: 'Save'
        },{
            text: 'Cancel'
        }]
    });

    var jobEditorDetailEditorsTabPanel = new Ext.TabPanel({
        region: 'center',
        minTabWidth: 115,
        tabWidth:135,
        enableTabScroll:true,
        activeTab: 0,
        defaults: {autoScroll:true},
        plugins: new Ext.ux.TabCloseMenu(),
        items: [selectedComponentForm,problemsGrid]
    });
    var mainCenterCenterNorth_ = new Ext.Panel({
        region: 'center',
        minHeight: 320,
        autoScroll: true
    });
    var mainCenterCenterSouth_ = new Ext.Panel({
        region: 'south',
        layout	: 'fit',
        border	:false,
        split	: true,
        bodyStyle:'padding:0px',
        height: 250,
        items   :[jobEditorDetailEditorsTabPanel]
    });
    var mainJobEditorPanel_ = new Ext.Panel({
        title: 'Job (Test #1)',
        iconCls: 'tabs',
        closable:true,
        labelAlign: 'top',
        bodyStyle:'padding:0px',
        layout: 'border',
        items: [
            mainCenterCenterNorth_,
            mainCenterCenterSouth_
        ],
        autoScroll: true
    });

    var mainCenter_ = new Ext.TabPanel({
        region: 'center',
        minTabWidth: 115,
        tabWidth:135,
        enableTabScroll:true,
        activeTab: 0,
        plugins: new Ext.ux.TabCloseMenu(),
        items: [mainJobEditorPanel_]
    });


    //Repository tree
    var navigationTree = new NavigationTreePanel(mainCenter_,mainEditorTabPanel_);

    var item1 = new Ext.Panel({
        title: 'Databases'
    });

    var item2 = new Ext.Panel({
        title: 'File'
    });

    var item3 = new Ext.Panel({
        title: 'Internet'
    });

    var item4 = new Ext.Panel({
        title: 'XML'
    });

    var item5 = new Ext.Panel({
        title: 'Logs & Errors'
    });

    var accordion = new Ext.Panel({
        region:'east',
        title: 'Palette',
        collapsible: true,
        split:true,
        width: 225,
        minSize: 175,
        maxSize: 400,
        layout:'fit',
        margins:'0 5 0 0',
        layout:'accordion',
        tbar: [
            'Search: ', ' ',
            new Ext.app.SearchField({
                width:'auto'
            })
        ],
        defaults: {html: '&lt;empty panel&gt;', cls:'empty'},
        items: [item1, item2, item3, item4, item5]
    });

    var viewport = new Ext.Viewport({
        layout:'border',
        items:[
            new Ext.BoxComponent({ // raw
                region:'north',
                el: 'north',
                height:32
            }),
            accordion,
            navigationTree,
            mainCenter_
        ]
    });
});

NavigationTreePanel = function(mainCenterTabPanel_,mainEditorTab_) {
    NavigationTreePanel.superclass.constructor.call(this, {
        region: 'west',
        id: 'navigation',
        icon: '/oryx/images/conxbi/etl/home_nav.gif',
        collapsible: true,
        title: 'Repository',
        width: 214,
        autoScroll: true,
        rootVisible: false,
        cmargins: '5 0 0 0',
        padding: '0 0 0 0',
        tbar: [
            'Search: ', ' ',
            new Ext.app.SearchField({
                width:'auto'
            })
        ],
        loader: new Ext.tree.TreeLoader({
            requestMethod: 'GET',
            clearOnLoad: false,
            dataUrl:'http://localhost:8082/etlrepo/explorer/getnode',
            preloadChildren: false,
            baseParams: {
                userid: 'userid',
                itemtype: 'undefined',
                folderObjectId: 'undefined'
            }
        }),
        // default tree elements for the navigation
        root: new Ext.tree.AsyncTreeNode({
            text: '',
            uiProvider: Ext.tree.customNodeUI
        })
    });

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

    //-- Init params
    this.mainTabPanel =  mainCenterTabPanel_;
    this.mainEditorPanel =  mainEditorTab_;

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
        }]
    });
    this.repoitem_database_contextmenu.on('hide', this.onContextHide, this);

    //Wizards
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

Ext.extend(NavigationTreePanel, Ext.tree.TreePanel, {
    mainTabPanel: undefined,
    mainEditorPanel_ : undefined,
    repofolder_database_contextmenu : undefined,
    repoitem_database_contextmenu : undefined,
    new_repoitem_database_wizard : undefined,
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

    // prevent the default context menu when you miss the node
    afterRender : function(){
        NavigationTreePanel.superclass.afterRender.call(this);
        this.el.on('contextmenu', function(e){
            e.preventDefault();
        });
    }
});



