Ext.onReady(function(){

    Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

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
                },{
                    fieldLabel: 'Mobile',
                    name: 'mobile'
                },{
                    fieldLabel: 'Fax',
                    name: 'fax'
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
                },{
                    fieldLabel: 'Mobile',
                    name: 'mobile'
                },{
                    fieldLabel: 'Fax',
                    name: 'fax'
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
                },{
                    fieldLabel: 'Mobile',
                    name: 'mobile'
                },{
                    fieldLabel: 'Fax',
                    name: 'fax'
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

    // tab panel
    var jobEditorTabPanel = new Ext.TabPanel({
        resizeTabs:true, // turn on tab resizing
        minTabWidth: 115,
        tabWidth:135,
        enableTabScroll:true,
        autoWidth:true,
        minHeight: 250,
        height: 250,
        defaults: {autoScroll:true},
        plugins: new Ext.ux.TabCloseMenu(),
        items: [selectedComponentForm,problemsGrid]
    });

    var centerNorth_ = new Ext.Panel({
        region: 'center',
        minHeight: 300,
        autoScroll: true
    });
    var centerSouth_ = new Ext.Panel({
        region: 'south',
        layout	: 'fit',
        border	:false,
        split	: true,
        items   :[jobEditorTabPanel]
    });
    var center_ = new Ext.Panel({
        region	: 'center',
        layout: 'border',
        items: [
            centerNorth_,
            centerSouth_
        ],
        autoScroll: true
    });

    var viewport = new Ext.Viewport({
        layout:'border',
        items:[
            new Ext.BoxComponent({ // raw
                region:'north',
                el: 'north',
                height:32
            }),{
                region:'east',
                title: 'East Side',
                collapsible: true,
                split:true,
                width: 225,
                minSize: 175,
                maxSize: 400,
                layout:'fit',
                margins:'0 5 0 0',
                items:
                    new Ext.TabPanel({
                        border:false,
                        activeTab:1,
                        tabPosition:'bottom',
                        items:[{
                            html:'<p>A TabPanel component can be a region.</p>',
                            title: 'A Tab',
                            autoScroll:true
                        },
                            new Ext.grid.PropertyGrid({
                                title: 'Property Grid',
                                closable: true,
                                source: {
                                    "(name)": "Properties Grid",
                                    "grouping": false,
                                    "autoFitColumns": true,
                                    "productionQuality": false,
                                    "created": new Date(Date.parse('10/15/2006')),
                                    "tested": false,
                                    "version": .01,
                                    "borderWidth": 1
                                }
                            })]
                    })
            },{
                region:'west',
                id:'west-panel',
                title:'West',
                split:true,
                width: 200,
                minSize: 175,
                maxSize: 400,
                collapsible: true,
                margins:'0 0 0 5',
                layout:'accordion',
                layoutConfig:{
                    animate:true
                },
                items: [{
                    contentEl: 'west',
                    title:'Navigation',
                    border:false,
                    iconCls:'nav'
                },{
                    title:'Settings',
                    html:'<p>Some settings in here.</p>',
                    border:false,
                    iconCls:'settings'
                }]
            },
            center_
        ]
    });
});
