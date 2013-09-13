if(!ORYX.Plugins) {
    ORYX.Plugins = new Object();
}

if(!ORYX.Plugins.ETL) {
    ORYX.Plugins.ETL = new Object();
}

if(!ORYX.Plugins.ETL.Metadata) {
    ORYX.Plugins.ETL.Metadata = new Object();
}

ORYX.Plugins.ETL.Metadata.DBConnectionWizard = {

    facade: undefined,

    selectDBTypeCard : undefined,
    enterJDBCSettingsCard : undefined,
    enterAuthCard : undefined,

    construct: function(facade) {
        // Reference to the Editor-Interface
        this.facade = facade;

        this.facade.registerOnEvent(ORYX.CONFIG.EVENT_ETL_METADATA_CREATE_PREFIX+'DBConnection', this.onCreate.bind(this));
        //this.facade.registerOnEvent(ORYX.CONFIG.EVENT_LOADED, this.selectDiagram.bind(this));
        this.init();
    },


    init: function(){
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
        });

        //2. Enter JDBC Settings
        this.enterJDBCSettingsCard =  new Ext.ux.Wiz.Card({
            name: 'enterJDBCSettingsCard',
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
        });

        this.enterAuthCard = new Ext.ux.Wiz.Card({
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
                        'selectDBTypeCard',
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
                //1. Select DB type
                this.selectDBTypeCard               ,
                //2. Enter JDBC Settings
                this.enterJDBCSettingsCard,
                //Auth
                this.enterAuthCard
            ]
        });
    },

    /**
     * Handle ORYX.CONFIG.EVENT_ETL_METADATA_CREATE_PREFIX+'DBConnection' Event
     * @param event
     * @param arg
     */
    onCreate: function(event, arg) {
        // Basic Dialog
        if (!this.newWizDialog) {
            this.newWizDialog = new Ext.Window({
                autoScroll: true,
                autoCreate: true,
                title: 'New Db Connection',
                height: 350,
                width: dialogWidth,
                modal:true,
                collapsible:false,
                fixedcenter: true,
                shadow:true,
                proxyDrag: true,
                keys:[{
                    key: 27,
                    fn: function(){
                        this.dialog.hide
                    }.bind(this)
                }],
                items:[this.newDBWiz],
                bodyStyle:"background-color:#FFFFFF",
                buttons: [{
                    text: ORYX.I18N.PropertyWindow.ok,
                    handler: function(){
                        this.grid.stopEditing();
                        // store dialog input
                        this.data = this.buildValue();
                        this.dialog.hide()
                    }.bind(this)
                }, {
                    text: ORYX.I18N.PropertyWindow.cancel,
                    handler: function(){
                        this.dialog.hide()
                    }.bind(this)
                }]
            });
        }
        this.newWizDialog.show();
    }
}
ORYX.Plugins.ETL.Metadata.DBConnectionWizard = Clazz.extend(ORYX.Plugins.ETL.Metadata.DBConnectionWizard);