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
    var registrationWizard = function() {

        var cardsTotal = 4;

        var navHandler = function( direction ) {
            var lay = cardForm.getLayout();
            var i = lay.activeItem.id.split('card-')[1];
            var next = parseInt(i) + direction;
            lay.setActiveItem(next);
            Ext.getCmp('move-prev').setDisabled(next==0);
            Ext.getCmp('move-next').setDisabled(next==cardsTotal);
            if(next >= cardsTotal) {
                Ext.getCmp('move-next').hide();
                Ext.getCmp('move-finish').show();
            } else {
                Ext.getCmp('move-next').show();
                Ext.getCmp('move-finish').hide();
            }
        };



        var cardForm = new Ext.FormPanel({
            id: 'regFormPanel',
            url: 'user.register.php',
            method: 'POST',
            bodyStyle: 'padding:0px',
            layout:'card',
            height: 200,
            activeItem: 0,
            defaults: {
                border: false
            },
            items: [{
                id: 'card-0',
                items: [{
                    html: '<p>Step 1 of 5</p>'
                },{
                    xtype: 'panel',
                    layout: 'fit',
                    autoScroll: true,
                    contentEl: 'north'
                }]
            },{
                id: 'card-1',
                items: [{
                    html: '<p>Step 2 of 5</p>'
                },{
                    xtype: 'fieldset',
                    title: 'User',
                    autoHeight: true,
                    defaultType: 'textfield',
                    collapsible: true,
                    items: [{
                        fieldLabel: 'username',
                        name: 'username',
                        allowBlank: false
                    }, {
                        fieldLabel: 'password',
                        name: 'password',
                        inputType: 'password',
                        allowBlank: false
                    }, {
                        fieldLabel: 'confirm',
                        name: 'confirm',
                        inputType: 'password',
                        allowBlank: false
                    }, {
                        fieldLabel: 'e-mail',
                        name: 'email',
                        anchor: '100%',
                        allowBlank: false
                    }]
                }]
            },{
                id: 'card-2',
                items: [{
                    html: '<p>Step 3 of 5</p>'
                }, {
                    xtype: 'fieldset',
                    title: 'personals',
                    collapsible: true,
                    autoHeight: true,
                    defaultType: 'textfield',
                    items: [{
                        fieldLabel: 'gender',
                        name: 'gender',
                        anchor: '100%',
                        allowBlank: true
                    }, {
                        fieldLabel: 'firstname',
                        name: 'firstname',
                        anchor: '100%',
                        allowBlank: false
                    }, {
                        fieldLabel: 'familyname',
                        name: 'lastname',
                        anchor: '100%',
                        allowBlank: false
                    }, new Ext.form.DateField({
                        fieldLabel: 'birthday',
                        name: 'birthday',
                        allowBlank: false
                    })]
                }]
            },{
                id: 'card-3',
                items: [{
                    html: '<p>Step 4 of 5</p>'
                },{
                    xtype: 'panel',
                    layout: 'fit',
                    contentEl: 'north',
                    autoScroll: true,
                    height: 200,
                    frame: true
                },{
                    xtype: 'radio',
                    name: 'disc_accept',
                    value: "true",
                    boxLabel: 'I accept the license aggrement',
                    allowBlank: false
                },{
                    xtype: 'radio',
                    name: 'disc_accept',
                    value: "false",
                    checked: true,
                    boxLabel: 'I do not accept the license aggrement'
                }]
            },{
                id: 'card-4',
                items: [{
                    html: '<p>Step 5 of 5</p>'
                },{
                    contentEl: 'north'
                }]
            }]
        });

        var submitRegistration = function() {
            if ( cardForm.form.isValid() ) {
                Ext.MessageBox.alert('Status', 'Passed');
                cardForm.form.submit({
                    waitMsg:'Registring new user...',
                    reset: false,
                    failure: function(result, action) {
                        Ext.MessageBox.alert('Error', action.result.message);
                    },
                    success: function(result, action) {
                        Ext.Msg.show({
                            title:'registration complete',
                            msg: result.responseText,
                            buttons: Ext.Msg.OK,
                            icon: Ext.MessageBox.INFO
                        });
                    }
                });
            } else {
                Ext.MessageBox.alert('Status', 'Not valid');
            }
        };

        var registerWin = new Ext.Panel({
            layout: 'fit',
            bodyStyle: 'padding:0px',
            items: [cardForm
            ],
            buttons:[{
                text: 'abort',
                handler: function(){
                    registerWin.hide();
                }
            },{
                text: 'previous',
                disabled: true,
                id: 'move-prev',
                handler: navHandler.createDelegate(this, [-1])
            },{
                text: 'next',
                id: 'move-next',
                handler: navHandler.createDelegate(this, [1])
            },{
                text: 'finish',
                id: 'move-finish',
                hidden: true,
                handler: function() {
                    submitRegistration();
                }
            }]
        });

        return registerWin;
    }();


    var cardForm_ = new Ext.FormPanel({
        id: 'regFormPanel',
        url: 'user.register.php',
        method: 'POST',
        bodyStyle: 'padding:0px',
        layout:'card',
        height: 400,
        activeItem: 0,
        defaults: {
            border: false
        },
        items: [{
            id: 'card-0',
            items: [{
                html: '<p>Step 1 of 5</p>'
                },{
                    xtype: 'panel',
                    layout: 'fit',
                    autoScroll: true,
                    contentEl: 'north'
                }]
            },{
            id: 'card-1',
            items: [{
                html: '<p>Step 2 of 5</p>'
            },{
                xtype: 'fieldset',
                title: 'User',
                autoHeight: true,
                defaultType: 'textfield',
                collapsible: true,
                items: [{
                    fieldLabel: 'username',
                    name: 'username',
                    allowBlank: false
                }, {
                    fieldLabel: 'password',
                    name: 'password',
                    inputType: 'password',
                    allowBlank: false
                }, {
                    fieldLabel: 'confirm',
                    name: 'confirm',
                    inputType: 'password',
                    allowBlank: false
                }, {
                    fieldLabel: 'e-mail',
                    name: 'email',
                    anchor: '100%',
                    allowBlank: false
                }]
            }]
        }]
    });


    var mainEditorTabPanel_ = new Ext.Panel({
        title: 'Editing Metadata (Excel FEDEX)',
        iconCls: 'tabs',
        closable:true,
        bodyStyle:'padding:0px',
        layout: 'fit',
        height: 350,
        items: [  cardForm_
        ],
        autoScroll: true
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
        defaults: {autoScroll:true},
        plugins: new Ext.ux.TabCloseMenu(),
        items: [mainJobEditorPanel_,mainEditorTabPanel_]
    });


    //Repository tree
    var navigationTree = new NavigationTreePanel();

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


NavigationTreePanel = function() {
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
        loader: new Ext.tree.TreeLoader({requestMethod: 'GET',dataUrl:'http://localhost:8082/etlmdmrepo/explorer?call=all&userId=test'}),
        // default tree elements for the navigation
        root: new Ext.tree.AsyncTreeNode({
            text: '',
            uiProvider: Ext.tree.customNodeUI
        })
    });

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

    this.addEvents({feedselect:true});

    this.on('contextmenu', this.onContextMenu, this);
}

Ext.extend(NavigationTreePanel, Ext.tree.TreePanel, {
    onContextMenu : function(node, e){
        if(!this.menu){ // create context menu on first right click
            this.menu = new Ext.menu.Menu({
                id:'feeds-ctx',
                items: [{
                    id:'load',
                    icon: '/oryx/images/conxbi/etl/connection-new.png',
                    text:'Create Connection',
                    scope: this,
                    handler:function(){
                        this.ctxNode.select();
                    }
                },{
                    text:'Create Folder',
                    icon: '/oryx/images/conxbi/etl/folder_close.png',
                    scope: this,
                    handler:function(){
                        this.ctxNode.ui.removeClass('x-node-ctx');
                        this.removeFeed(this.ctxNode.attributes.url);
                        this.ctxNode = null;
                    }
                },'-',{
                    iconCls:'add-feed',
                    text:'Add Feed',
                    handler: this.showWindow,
                    scope: this
                }]
            });
            this.menu.on('hide', this.onContextHide, this);
        }
        if(this.ctxNode){
            this.ctxNode.ui.removeClass('x-node-ctx');
            this.ctxNode = null;
        }
        //if(node.isLeaf()){
            this.ctxNode = node;
            this.ctxNode.ui.addClass('x-node-ctx');
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