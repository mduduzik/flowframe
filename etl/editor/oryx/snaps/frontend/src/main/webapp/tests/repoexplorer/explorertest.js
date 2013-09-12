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
    var navigationTree = new Ext.ux.FileTreePanel({
        height:400
        ,id:'ftp'
        ,rootPath:'root'
        ,autoScroll:true
        ,enableProgress:false
//		,baseParams:{additional:'haha'}
//		,singleUpload:true
    });
    var navigationTreePanel = new Ext.Panel({
        region:'west',
        title: 'Repo',
        collapsible: true,
        split:true,
        width: 225,
        minSize: 175,
        maxSize: 400,
        layout:'fit',
        margins:'0 5 0 0',
        tbar: [
            'Search: ', ' ',
            new Ext.app.SearchField({
                width:'auto'
            })
        ],
        items: [navigationTree]
    });

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
            navigationTreePanel,
            mainCenter_
        ]
    });
});



