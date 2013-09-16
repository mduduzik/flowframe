if (!ORYX.Plugins) {
    ORYX.Plugins = new Object();
}

if (!ORYX.Plugins.ETL) {
    ORYX.Plugins.ETL = new Object();
}

if (!ORYX.Plugins.ETL.Metadata) {
    ORYX.Plugins.ETL.Metadata = new Object();
}

ORYX.Plugins.ETL.Metadata.DBConnectionWizard = {

    facade: undefined,
    dbFolderId: undefined,
    parentNavNodeId: undefined,
    wizMode: undefined,  //EDITING, CREATE

    selectDBTypeCard: undefined,
    enterJDBCSettingsCard: undefined,
    enterAuthCard: undefined,
    newWizDialog: undefined,

    construct: function (facade) {
        // Reference to the Editor-Interface
        this.facade = facade;

        this.facade.registerOnEvent(ORYX.CONFIG.EVENT_ETL_METADATA_CREATE_PREFIX + 'DBConnection', this.onCreate.bind(this));
        //this.facade.registerOnEvent(ORYX.CONFIG.EVENT_LOADED, this.selectDiagram.bind(this));
        //this.init();
    },


    initWiz: function () {
        /**
         * Cards
         */
            //-1. Select DB Type
        this.selectDBTypeCard = new Ext.ux.Wiz.Card({
            id: "selectDBTypeCard",
            title: 'Enter name and select database type',
            monitorValid: true,
            defaults: {
                labelStyle: 'font-size:11px'
            },
            items: [
                {
                    xtype: 'fieldset',
                    labelWidth: 200,
                    layoutConfig: {
                        labelAlign: 'right'
                    },
                    //title:'Company details',
                    defaults: {width: 350},	// Default config options for child items
                    defaultType: 'textfield',
                    autoHeight: true,
                    bodyStyle: Ext.isIE ? 'padding:0 0 5px 15px;' : 'padding:10px 15px;',
                    border: false,
                    style: {
                        "margin-left": "10px", // when you add custom margin in IE 6...
                        "margin-right": Ext.isIE6 ? (Ext.isStrict ? "-10px" : "-13px") : "0"  // you have to adjust for it somewhere else
                    },
                    items: [
                        {
                            fieldLabel: 'Name',
                            name: 'name',
                            emptyText: '<Enter name>',
                            allowBlank: false
                        },
                        new Ext.form.ComboBox({
                            name: 'databaseType',
                            fieldLabel: 'Database Type',
                            hiddenName: 'databaseType',
                            store: new Ext.data.Store({
                                id: "store",
                                remoteSort: true,
                                autoLoad: {params: {start: 1, limit: 2}},
                                proxy: new Ext.data.ScriptTagProxy({
                                    url: '/etlrepo/databasetype/search'
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
                }
            ]

        });

        //2. Enter JDBC Settings
        this.enterJDBCSettingsCard = new Ext.ux.Wiz.Card({
            name: 'enterJDBCSettingsCard',
            title: 'Set the JDBC Settings',
            monitorValid: true,
            defaults: {
                labelStyle: 'font-size:11px'
            },
            items: [
                {
                    xtype: 'fieldset',
                    labelWidth: 200,
                    layoutConfig: {
                        labelAlign: 'right'
                    },
                    //title:'Company details',
                    defaults: {width: 350},	// Default config options for child items
                    defaultType: 'textfield',
                    autoHeight: true,
                    bodyStyle: Ext.isIE ? 'padding:0 0 5px 15px;' : 'padding:10px 15px;',
                    border: false,
                    style: {
                        "margin-left": "10px", // when you add custom margin in IE 6...
                        "margin-right": Ext.isIE6 ? (Ext.isStrict ? "-10px" : "-13px") : "0"  // you have to adjust for it somewhere else
                    },
                    items: [
                        {
                            fieldLabel: 'Hostname of database server',
                            name: 'hostname',
                            emptyText: '<Enter hostname>',
                            allowBlank: false
                        },
                        {
                            fieldLabel: 'The TCP/IP Port',
                            name: 'databasePort',
                            emptyText: '<Enter TCP/IP Port>',
                            allowBlank: false
                        },
                        {
                            fieldLabel: 'The name of the database',
                            name: 'databaseName',
                            emptyText: '<Enter name of the database>',
                            allowBlank: false
                        }
                    ]
                }
            ]
        });

        this.enterAuthCard = new Ext.ux.Wiz.Card({
            name: 'auth',
            title: 'Enter username and password',
            monitorValid: true,
            defaults: {
                labelStyle: 'font-size:11px'
            },
            items: [
                {
                    xtype: 'fieldset',
                    labelWidth: 200,
                    layoutConfig: {
                        labelAlign: 'right'
                    },
                    //title:'Company details',
                    defaults: {width: 350},	// Default config options for child items
                    defaultType: 'textfield',
                    autoHeight: true,
                    bodyStyle: Ext.isIE ? 'padding:0 0 5px 15px;' : 'padding:10px 15px;',
                    border: false,
                    style: {
                        "margin-left": "10px", // when you add custom margin in IE 6...
                        "margin-right": Ext.isIE6 ? (Ext.isStrict ? "-10px" : "-13px") : "0"  // you have to adjust for it somewhere else
                    },
                    items: [
                        {
                            fieldLabel: 'Username',
                            name: 'username',
                            emptyText: '<Enter usename>',
                            allowBlank: false
                        },
                        {
                            name: 'password',
                            fieldLabel: 'Password',
                            emptyText: '<Enter password>',
                            allowBlank: false,
                            inputType: "password"
                        }
                    ]
                }
            ]
        });

        /**
         * Create New DB Wizard
         * @type {Ext.ux.Wiz}
         */
        this.newDBWiz = new Ext.ux.Wiz({
            headerConfig: {
                title: 'New DB Metadata Wiz'
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
                if (this.wizMode === 'CREATE') {;
                    var data = this.newDBWiz.getSelectWizardData();
                    Object.extend(data, {dirObjectId: this.dbFolderId});
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
                            var db = Ext.decode(response.responseText);
                            this.facade.raiseEvent({type:ORYX.CONFIG.EVENT_ETL_METADATA_CREATED,forceExecution:true,name:db.name,treeNodeParentId:this.parentNavNodeId});
                            this.newWizDialog.close();
                        }.bind(this),
                        failure: function (response, opts) {
                            Ext.MessageBox.show({
                                title:'Add failed',
                                msg: 'Error saving new db connection',
                                buttons: Ext.MessageBox.OK,
                                icon: Ext.MessageBox.ERROR
                            });
                        }.bind(this)
                    });
                }
            }.bind(this),
            cards: [
                //1. Select DB type
                this.selectDBTypeCard,
                //2. Enter JDBC Settings
                this.enterJDBCSettingsCard,
                //3. Auth
                this.enterAuthCard
            ]
        });
        this.newDBWiz.addEvents(
            /**
             * @event ORYX.CONFIG.EVENT_ETL_METADATA_CREATED
             * Fires after new metadata artifact (e.g. DbConnection) has been created
             * @param {name:'<artifact name>',treeNodeParentId:<>}
             */
            ORYX.CONFIG.EVENT_ETL_METADATA_CREATED
        );
        this.newDBWiz.on('cancel', this.onBeforeCancel, this);
    },

    /**
     *
     */
    onBeforeCancel: function() {
        Ext.MessageBox.show({
            title:'Save Changes?',
            msg: 'There might be unsaved changes. <br />Do you still want to cancel?',
            buttons: Ext.MessageBox.YESNO,
            fn: function(btn){
                if (btn === 'yes'){
                    this.newWizDialog.close();
                }
            }.bind(this),
            icon: Ext.MessageBox.QUESTION
        });
        return false;//Don't close wizard - close dialog
    },

    /**
     * Handle ORYX.CONFIG.EVENT_ETL_METADATA_CREATE_PREFIX+'DBConnection' Event
     * @param event
     * @param arg - tree node
     */
    onCreate: function (event, arg) {
        this.wizMode = 'CREATE';
        this.dbFolderId = arg.dbFolderId;
        this.parentNavNodeId = arg.sourceNavNodeId;

        // Basic Dialog
        this.initWiz();

        this.newWizDialog = new Ext.Window({
            autoScroll: false,
            autoCreate: true,
            closeAction:'destroy',
            title: 'New Db Connection',
            height: 450,
            width: 800,
            modal: true,
            collapsible: false,
            fixedcenter: true,
            shadow: true,
            proxyDrag: true,
            layout: 'fit',
            keys: [
                {
                    key: 27,
                    fn: function () {
                        this.newWizDialog.hide
                    }.bind(this)
                }
            ],
            items: [this.newDBWiz],
            bodyStyle: "background-color:#FFFFFF"
        });

        this.newWizDialog.show();
    }
}
ORYX.Plugins.ETL.Metadata.DBConnectionWizard = Clazz.extend(ORYX.Plugins.ETL.Metadata.DBConnectionWizard);