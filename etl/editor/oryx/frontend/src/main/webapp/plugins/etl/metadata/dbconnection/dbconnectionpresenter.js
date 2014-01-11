if (!ORYX.Plugins) {
    ORYX.Plugins = new Object();
}

if (!ORYX.Plugins.ETL) {
    ORYX.Plugins.ETL = new Object();
}

if (!ORYX.Plugins.ETL.Metadata) {
    ORYX.Plugins.ETL.Metadata = new Object();
}

if (!ORYX.Plugins.ETL.Metadata) {
    ORYX.Plugins.ETL.Metadata = new Object();
}

ORYX.Plugins.ETL.Metadata.DBConnectionEditor = ORYX.Plugins.ETL.Metadata.StepMetaBasePresenter.extend({

    eventManager: undefined,
    dbFolderId: undefined,
    dbId: undefined,
    parentNavNodeId: undefined,
    wizMode: undefined,  //EDITING, CREATE

    selectDBTypeCard: undefined,
    enterJDBCSettingsCard: undefined,
    enterAuthCard: undefined,
    newWizDialog: undefined,

    construct: function (eventManager) {
        this.itemType = ORYX.CONFIG.ETL_METADATA_TYPE_DATABASE;
        this.eventManager = eventManager;
        this.dataConfig = {
            onNewURL: '/etl/core/databasemeta/onnew',
            onGetMetadataURL: '/etl/core/databasemeta/ongetmetadata',
            onPreviewURL: '/etl/core/databasemeta/previewdata',
            onSaveURL: '/etl/core/databasemeta/update',
            onAddURL: '/etl/core/databasemeta/add',
            onEditURL: '/etl/core/databasemeta/onedit',
            onDeleteURL: '/etl/core/databasemeta/delete'
        };

        // Call super class constructor
        arguments.callee.$.construct.apply(this, arguments);
    },


    //@Override
    initWizard: function () {
        /**
         * Cards
         */
            //-1. Select DB Type
        this.selectDBTypeCard = new Ext.ux.etl.BaseWizardCardView({
            id: "selectDBTypeCard",
            title: 'Enter name and select database type',
            monitorValid: true,
            defaults: {
                labelStyle: 'font-size:11px'
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
                            url: '/etl/core/databasetype/search'
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

        });

        //2. Enter JDBC Settings
        this.enterJDBCSettingsCard = new Ext.ux.etl.BaseWizardCardView({
            name: 'enterJDBCSettingsCard',
            title: 'Set the JDBC Settings',
            monitorValid: true,
            defaults: {
                labelStyle: 'font-size:11px'
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
        });

        this.enterAuthCard = new Ext.ux.etl.BaseWizardCardView({
            name: 'auth',
            title: 'Enter username and password',
            monitorValid: true,
            defaults: {
                labelStyle: 'font-size:11px'
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
        });


        //-- Create New Wizard
        this.stepMetaWizard = new Ext.ux.etl.BaseCardView({
            parentEditor: this,
            eventManager: this.eventManager,
            region: 'center',
            wizMode: this.wizMode,
            metaId: this.metaId,
            shapeConfig: this.shapeConfig,
            buttonsAt: 'bbar',
            headerConfig: {
                title: 'Excel Input Metadata'
            },
            cardPanelConfig: {
                defaults: {
                    baseCls: 'x-small-editor',
                    bodyStyle: 'padding: 20px 15px 5px 5px; background-color:#F6F6F6;',
                    border: false
                }
            },
            cards: [
                this.selectDBTypeCard,
                this.enterJDBCSettingsCard,
                this.enterAuthCard
            ],
            initParams: {
                fileEntryId: (this.sampleFileNode === undefined)?'<select file>':this.sampleFileNode.id,
                fileEntryTitle: (this.sampleFileNode === undefined)?'<select file>':this.sampleFileNode.text
            },
            dataPresenter: this.dataPresenter,
            onBackToFirstStep : function() {
                this.cardPanel.getLayout().setActiveItem(0);
            },
            //@Ovveriide
            onLoadModel: function() {//usually called on render event
                if (this.parentEditor.editorMode === 'STEP') {
                    this.getDataPresenter().executeOnEditStepDataRequest(this.shapeConfig);
                }
                else { //''METADATA'
                    this.switchDialogState(false);
                    if (this.wizMode === 'CREATE')
                        this.getDataPresenter().executeOnNewRequest();
                    else if (this.wizMode === 'EDIT')
                        this.getDataPresenter().executeOnEditDataRequest(this.metaId,function(response, opts) {
                        }.bind(this));
                    this.switchDialogState(true);
                }
            },
            //@Override
            onFinish : function()//Save or Add
            {
                if (this.parentEditor.editorMode === 'STEP') {
                    this.getDataPresenter().executeOnSaveStepDataRequest(function() {
                        this.switchDialogState(true);
                        try {
                            this.parentEditor.editorDialog.destroy();
                        } catch (e) {
                            ORYX.Log.warn("Error closing editor dialog: this.parentEditor.editorDialog.destroy() failed");
                        }
                        //var title = this.getDataPresenter().getRecord().parentStepMeta.stepname;
                        //TBD: Update shape here
                        //this.eventManager.raiseEvent({type:ORYX.CONFIG.EVENT_ETL_METADATA_CREATED,forceExecution:true,name:title,treeNodeParentId:this.parentEditor.parentNavNodeId});
                    }.bind(this));
                }
                else { //''METADATA'
                    if (this.wizMode === 'CREATE') {
                        this.switchDialogState(false,'creatingMetaData');
                        this.getDataPresenter().executeOnAddDataRequest(this.parentEditor.folderId,function(response, opts) {
                            this.switchDialogState(true);
                            this.parentEditor.editorDialog.destroy();
                            var title = this.getDataPresenter().getRecord().name;
                            this.eventManager.raiseEvent({type:ORYX.CONFIG.EVENT_ETL_METADATA_CREATED,forceExecution:true,name:title,treeNodeParentId:this.parentEditor.parentNavNodeId});
                        }.bind(this));
                    }
                    else if (this.wizMode === 'EDIT')
                        if (this.getDataPresenter().isDirty()) {
                            this.getDataPresenter().executeOnSaveDataRequest(this.metaId,function(response, opts) {
                                var title = this.getDataPresenter().getRecord().name;
                                this.eventManager.raiseEvent({type:ORYX.CONFIG.EVENT_ETL_METADATA_SAVED,forceExecution:true,name:title,treeNodeParentId:this.parentEditor.metaId});
                            }.bind(this));
                        }
                    try {
                        this.parentEditor.editorDialog.destroy();
                    } catch (e) {
                        ORYX.Log.warn("Error closing editor dialog: this.parentEditor.editorDialog.destroy() failed");
                    }
                }
            }
        });
    }
})