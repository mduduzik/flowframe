Ext.onReady(function () {

    Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

    /*
     //Job Artifact Wizard/Main Editor
     */
    var mainEditorTabPanel_ = new Ext.Panel({
        title: 'New CSVInput',
        iconCls: 'tabs',
        closable: true,
        split: true,
        bodyStyle: 'padding:0px',
        layout: 'fit',
        collapsible: false,
        margins: '3 0 3 3',
        cmargins: '3 3 3 3',
        autoScroll: false
    });


    var form = new Ext.form.FormPanel({
        labelAlign: 'top',
        title: 'test',
        bodyStyle: 'padding:5px',
        layout: 'table',
        layoutConfig: {columns: 3},
        defaults: { layout: 'form', border: false },
        items: [
            new Ext.form.Label({
                layout: 'form',
                text: 'Row 1',
                forId: 'row1'
            })
            , new Ext.form.TextField({
                layout: 'form',
                name: 'r1c1',
                id: 'row1',
                allowBlank: false
            }),
            new Ext.form.TextField({
                layout: 'form',
                name: 'r1c2',
                allowBlank: false
            }),
            new Ext.form.Label({
                layout: 'form',
                text: 'Row 2',
                forId: 'row2'
            }), new Ext.form.TextField({
                layout: 'form',
                name: 'r2c1',
                id: 'row2',
                allowBlank: false
            }),
            new Ext.form.TextField({
                layout: 'form',
                name: 'r2c2',
                allowBlank: false
            })]
    });


    var new_repoitem_database_wizard = new Ext.ux.Wiz({
        region: 'center',
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
        getSelectWizardData: function (cardids) {
            var formValues = {};
            var cards = this.cards;
            for (var i = 0, len = cards.length; i < len; i++) {

                var cardform = cards[i].form;
                if (cardform) {
                    var values = cardform.getValues(false);
                    Object.extend(formValues, values);
                }
            }
            return formValues;
        },

        onFinish: function () {
            var requestWiz = this;
            var requestCtxNode = requestWiz.ctxNode;
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
                Object.extend(data, {dirObjectId: dirId});
                var dataJson = Ext.encode(data);
                Ext.lib.Ajax.request = Ext.lib.Ajax.request.createInterceptor(function (method, uri, cb, data, options) {
                    // here you can put whatever you need as header. For instance:
                    this.defaultPostHeader = "application/json; charset=utf-8;";
                    this.defaultHeaders = {userid: 'test'};
                });
                Ext.Ajax.request({
                    url: '/etlrepo/databasemeta/add',
                    method: 'POST',
                    params: dataJson,
                    success: function (response, opts) {
                        //Refresh this.ctxNode
                        if (requestCtxNode.attributes)
                            requestCtxNode.attributes.children = false;
                        requestCtxNode.select();
                        requestCtxNode.ui.addClass('x-node-ctx');
                        requestCtxNode.reload();


                        //Update tab title
                        //requestMainEditorPanel.hide();

                        var db = Ext.decode(response.responseText);
                        requestMainEditorPanel.setTitle("Editing " + db.name);
                        requestWiz.finishButtonText = 'Save';
                        Object.extend(requestWiz, {wizmode: 'EDITING'});
                    },
                    failure: function (response, opts) {
                    }
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
                        hiddenName: 'databaseType',
                        store: new Ext.data.Store({
                            id: "store",
                            remoteSort: true,
                            autoLoad: {params: {start: 1, limit: 2}},
                            proxy: new Ext.data.ScriptTagProxy({
                                url: 'http://localhost:8082/etlrepo/databasetype/search'
                            }),
                            reader: new Ext.data.JsonReader({
                                root: 'data',
                                totalProperty: 'totalCount'
                            }, [
                                {name: 'id', mapping: 'id'},
                                {name: 'code', mapping: 'code'},
                                {name: 'description', mapping: 'description'}
                            ])
                        }),

                        valueField: 'code',
                        displayField: 'description',
                        typeAhead: true,
                        mode: 'local',
                        triggerAction: 'all',
                        emptyText: 'Select a database type...',
                        selectOnFocus: true,
                        width: 190
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
                        inputType: "password"
                    })
                ]
            })

        ]
    });

    /*
     //Job Editor Tab
     */
    //Center Panel
    var problemsGrid = new Ext.grid.PropertyGrid({
        title: 'Fields',
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


    var jobPropertiesForm = new Ext.FormPanel({
        title: 'Job (Job #1) Properties',
        iconCls: 'tabs',
        closable: true,
        labelAlign: 'top',
        bodyStyle: 'padding:5px',
        minHeight: 300,
        items: [
            {
                layout: 'column',
                border: false,
                items: [
                    {
                        columnWidth: .5,
                        layout: 'form',
                        border: false,
                        items: [
                            {
                                xtype: 'textfield',
                                fieldLabel: 'First Name',
                                name: 'first',
                                anchor: '95%'
                            },
                            {
                                xtype: 'textfield',
                                fieldLabel: 'Company',
                                name: 'company',
                                anchor: '95%'
                            }
                        ]
                    },
                    {
                        columnWidth: .5,
                        layout: 'form',
                        border: false,
                        items: [
                            {
                                xtype: 'textfield',
                                fieldLabel: 'Last Name',
                                name: 'last',
                                anchor: '95%'
                            },
                            {
                                xtype: 'textfield',
                                fieldLabel: 'Email',
                                name: 'email',
                                vtype: 'email',
                                anchor: '95%'
                            }
                        ]
                    }
                ]
            },
            {
                xtype: 'tabpanel',
                plain: true,
                activeTab: 0,
                height: 235,
                defaults: {bodyStyle: 'padding:10px'},
                items: [
                    {
                        title: 'Personal Details',
                        layout: 'form',
                        defaults: {width: 230},
                        defaultType: 'textfield',

                        items: [
                            {
                                fieldLabel: 'First Name',
                                name: 'first',
                                allowBlank: false,
                                value: 'Jack'
                            },
                            {
                                fieldLabel: 'Last Name',
                                name: 'last',
                                value: 'Slocum'
                            },
                            {
                                fieldLabel: 'Company',
                                name: 'company',
                                value: 'Ext JS'
                            },
                            {
                                fieldLabel: 'Email',
                                name: 'email',
                                vtype: 'email'
                            }
                        ]
                    },
                    {
                        title: 'Phone Numbers',
                        layout: 'form',
                        defaults: {width: 230},
                        defaultType: 'textfield',

                        items: [
                            {
                                fieldLabel: 'Home',
                                name: 'home',
                                value: '(888) 555-1212'
                            },
                            {
                                fieldLabel: 'Business',
                                name: 'business'
                            }
                        ]
                    },
                    {
                        cls: 'x-plain',
                        title: 'Biography',
                        layout: 'fit',
                        items: {
                            xtype: 'htmleditor',
                            id: 'bio2',
                            fieldLabel: 'Biography'
                        }
                    }
                ]
            }
        ],

        buttons: [
            {
                text: 'Save'
            },
            {
                text: 'Cancel'
            }
        ]
    });

    var jobEditorDetailEditorsTabPanel = new Ext.TabPanel({
        region: 'center',
        minTabWidth: 115,
        tabWidth: 135,
        enableTabScroll: true,
        activeTab: 0,
        defaults: {autoScroll: true},
        plugins: new Ext.ux.TabCloseMenu(),
        items: [problemsGrid]
    });
    var mainCenterCenterNorth_ = new Ext.Panel({
        region: 'center',
        minHeight: 320,
        autoScroll: true
    });
    var mainCenterCenterSouth_ = new Ext.Panel({
        region: 'south',
        layout: 'fit',
        border: false,
        split: true,
        bodyStyle: 'padding:0px',
        height: 250,
        items: [jobEditorDetailEditorsTabPanel]
    });
    var mainJobEditorPanel_ = new Ext.Panel({
        title: 'CSVInput',
        iconCls: 'tabs',
        closable: true,
        labelAlign: 'top',
        bodyStyle: 'padding:0px',
        layout: 'border',
        items: [
            new_repoitem_database_wizard
        ],
        autoScroll: true
    });

    var mainCenter_ = new Ext.TabPanel({
        region: 'center',
        minTabWidth: 115,
        tabWidth: 135,
        enableTabScroll: true,
        activeTab: 0,
        plugins: new Ext.ux.TabCloseMenu(),
        items: [mainJobEditorPanel_]
    });

    var viewport = new Ext.Viewport({
        layout: 'border',
        items: [
            new Ext.BoxComponent({ // raw
                region: 'north',
                el: 'north',
                height: 32
            }),
            mainCenter_
        ]
    });
});
