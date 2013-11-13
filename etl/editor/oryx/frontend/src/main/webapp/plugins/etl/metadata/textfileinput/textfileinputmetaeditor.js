if (!ORYX.Plugins) {
    ORYX.Plugins = new Object();
}

if (!ORYX.Plugins.ETL) {
    ORYX.Plugins.ETL = new Object();
}

if (!ORYX.Plugins.ETL.Metadata) {
    ORYX.Plugins.ETL.Metadata = new Object();
}

ORYX.Plugins.ETL.Metadata.TextFileInputMetaEditor = {

    eventManager: undefined,
    folderId: undefined,
    metaId: undefined,
    parentNavNodeId: undefined,
    wizMode: undefined,  //EDITING, CREATE

    newWizDialog: undefined,
    newTextFileInputMetaWiz: undefined,

    model: undefined,

    construct: function (eventManager) {
        // Reference to the Editor-Interface
        this.eventManager = eventManager;

        this.eventManager.registerOnEvent(ORYX.CONFIG.EVENT_ETL_METADATA_CREATE_PREFIX + ORYX.CONFIG.ETL_METADATA_TYPE_DELIMTEDMETA, this.onCreate.bind(this));
        this.eventManager.registerOnEvent(ORYX.CONFIG.EVENT_ETL_METADATA_EDIT_PREFIX + ORYX.CONFIG.ETL_METADATA_TYPE_DELIMTEDMETA, this.onEdit.bind(this));
        this.eventManager.registerOnEvent(ORYX.CONFIG.EVENT_ETL_METADATA_DELETE_PREFIX + ORYX.CONFIG.ETL_METADATA_TYPE_DELIMTEDMETA, this.onDelete.bind(this));
    },


    initWiz: function () {
        var uploaderPanel = new Ext.ux.UploadPanel({
            layout: 'fit',
            xtype: 'uploadpanel',
            addText: 'Add sample CSV file',
            buttonsAt: 'tbar',
            id: 'uppanel',
            url: '/etl/core/textfileinputmeta/uploadsample',
            path: 'root',
            maxFileSize: 1048576,
            enableProgress: false,
            singleUpload: true
        });
        /**
         * Pages
         */
        var selectSampleFilePage = new Ext.ux.etl.BaseWizardEditorPage({
            id: "uploadsamplefile",
            title: 'Sample File',
            monitorValid: true,
            isUpload: true,
            defaults: {
                labelStyle: 'font-size:11px',
                labelAlign: 'right',
                align: 'left'
            },
            layout: 'column',

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
                            fieldLabel: 'File Title',
                            name: 'fileTitle',
                            disabled: true,
                            allowBlank: false,
                            getSubmitData: this.getDisabledFieldValue//read-only hack
                        },
                        {
                            fieldLabel: 'File URI',
                            name: 'fileName',
                            disabled: true,
                            allowBlank: false,
                            getSubmitData: this.getDisabledFieldValue//read-only hack
                        }
                    ]
                }
            ]
            ,onBeforeModelSubmission: function () {
                //Update record
                var fileNames = [];
                fileNames.push('ff://repo/internal?fileentry#'+this.parentEditor.initParams.sampleFileNode.id);
                this.parentEditor.getValuesManager().updateRecordProperty("fileName",fileNames);
            }
            ,onAfterModelLoad: function () {
                //Update record
                this.onBeforeModelSubmission()

                //Make form fields look right
                this.form.setValues({
                    fileTitle: this.parentEditor.initParams.sampleFileNode.text,
                    fileName: 'ff://repo/internal?fileentry#'+this.parentEditor.initParams.sampleFileNode.id
                })
            }
        });


        var enterMetadataSettingsPage = new Ext.ux.etl.BaseWizardEditorPage({
            id: "entermetadatasettings",
            title: 'Enter/change settings',
            monitorValid: true,
            defaults: {
                labelStyle: 'font-size:11px',
                labelAlign: 'right',
                align: 'left'
            },
            layout: 'column',

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
                            fieldLabel: 'Metadata Name',
                            name: 'name',
                            emptyText: '<Enter name>',
                            allowBlank: false
                        },
                        {
                            xtype:'combo',
                            fieldLabel:'File Type',
                            name:'fileType',
                            displayField:'text',
                            valueField:'value',
                            store : new Ext.data.SimpleStore({
                                fields: ["value","text"],
                                data: [
                                    ["CSV","CSV"],
                                    ["Fixed","Fixed"]
                                ]
                            }),
                            typeAhead: true,
                            triggerAction: 'all',
                            value:"CSV",
                            selectOnFocus:false,
                            editable:false,
                            forceSelection:false,
                            allowBlank:true,
                            mode:'local',
                            emptyText : "FileType is Required"
                        },
                        {
                            fieldLabel: 'Row Number Field',
                            name: 'rowNumberField',
                            value: 'lineNumber'
                        },
                        {
                            xtype: 'xcheckbox',
                            fieldLabel: 'Header?',
                            name: 'header',
                            inputValue: true,
                            value: true,
                            width: 15
                        },
                        {
                            fieldLabel: 'Separator',
                            name: 'separator',
                            emptyText: '<Enter delimiter or separator>',
                            allowBlank: false,
                            value: ','
                        },
                        {
                            fieldLabel: 'Enclosure',
                            name: 'enclosure',
                            value: '"'
                        },
                        {
                            xtype: 'numberfield',
                            fieldLabel: 'Number of header lines',
                            name: 'nrHeaderLines',
                            style: 'text-align: left',
                            value: 1
                        },
                        {
                            xtype: 'xcheckbox',
                            fieldLabel: 'Rownum in output?',
                            name: 'includeRowNumber',
                            width: 15,
                            inputValue: true,
                            value: true
                        },
                        {
                            xtype: 'combo',
                            name: 'encoding',
                            fieldLabel: 'Encoding',
                            hiddenName: 'encoding',
                            store: new Ext.data.Store({
                                id: "store",
                                remoteSort: true,
                                autoLoad: {params: {start: 1, limit: 2}},
                                proxy: new Ext.data.ScriptTagProxy({
                                    url: '/etl/core/encoding/getall'
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
                            emptyText: 'Select encoding...',
                            selectOnFocus: true,
                            width: 190
                        }
                        ,
                        {
                            xtype: 'xcheckbox',
                            fieldLabel: 'Newline Possible In Fields',
                            name: 'newlinePossibleInFields',
                            inputValue: false,
                            value: false,
                            width: 15
                        }
                    ]
                }
            ]
            ,onBeforeModelSubmission: function () {
                var parentStepMeta = {
                    stepname: this.getForm().getObjectValues().name
                };
                this.parentEditor.getValuesManager().updateRecordProperty("parentStepMeta",parentStepMeta);
            }
        });

        //Get Metadata
        var MetadataFieldModel = Ext.data.Record.create([
            {name: 'id'},
            {name: 'name'},
            {name: 'typeDescription'},
            {name: 'format'},
            {name: 'length'},
            {name: 'precision'},
            {name: 'currency'},
            {name: 'decimal'},
            {name: 'group'},
            {name: 'trimtype'}
        ]);

        var getmetadataGridStore = new Ext.data.Store({
            reader: new Ext.data.JsonReader({
                totalProperty: "results",             // The property which contains the total dataset size (optional)
                root: "rows",                         // The property which contains an Array of row objects
                id: "id"                              // The property within each row object that provides an ID for the record (optional)
            }, MetadataFieldModel)
        });


        var controlBar = new Ext.Toolbar({
            items: [
                {
                    text: 'Get fields', tooltip: 'Refresh fields', iconCls: 'icon-refresh', id: 'btn-refresh', toggleHandler: function (btn, pressed) {
                    newCsvMetaWiz.getMetadata();
                }
                }
            ]
        });

        var getmetadataGrid = new Ext.grid.EditorGridPanel({
            autoScroll: true,
            height: 300,
            clicksToEdit: 2,
            tbar: controlBar,
            store: getmetadataGridStore,
            listeners: {
                afteredit: function (e) {
                    e.record.commit();
                }
            },
            cm: new Ext.grid.ColumnModel([
                {
                    id: 'name',
                    header: "Name",
                    width: 150,
                    sortable: true,
                    locked: false,
                    dataIndex: 'name',
                    allowBlank: false,
                    editor: new Ext.form.TextField({
                        allowBlank: false
                    })
                },
                {
                    header: "Type",
                    width: 100,
                    sortable: true,
                    dataIndex: 'typeDescription',
                    editor: new Ext.form.ComboBox({
                        store: new Ext.data.SimpleStore({
                            fields: ['type', 'description'],
                            data: [
                                [0, "NONE"],
                                [1, "Number"],
                                [2, "String"],
                                [3, "Date"],
                                [4, "Boolean"],
                                [5, "Integer"],
                                [6, "BigNumber"],
                                [7, "Serializable"],
                                [8, "Binary"]
                            ]
                        }),
                        displayField: 'description',
                        typeAhead: true,
                        mode: 'local',
                        triggerAction: 'all',
                        emptyText: 'Select a type...',
                        selectOnFocus: true
                    })
                },
                {
                    header: "Format",
                    width: 150,
                    sortable: true,
                    dataIndex: 'format',
                    editor: new Ext.form.ComboBox({
                        store: new Ext.data.SimpleStore({
                            fields: ['format'],
                            data: [
                                [""],
                                ["#"],
                                ["0.00"],
                                ["0000000000000"],
                                ["###,###,###.#######"],
                                ["###############.###############"],
                                ["#####.###############%"],
                                ["yyyy/MM/dd HH:mm:ss.SSS"],
                                ["yyyy/MM/dd HH:mm:ss"],
                                ["dd/MM/yyyy"],
                                ["dd-MM-yyyy"],
                                ["yyyy/MM/dd"],
                                ["yyyy-MM-dd"],
                                ["yyyyMMdd"],
                                ["ddMMyyyy"],
                                ["d-M-yyyy"],
                                ["d/M/yyyy"],
                                ["d-M-yy"],
                                ["d/M/yy"]
                            ]
                        }),
                        displayField: 'format',
                        typeAhead: true,
                        mode: 'local',
                        triggerAction: 'all',
                        emptyText: 'Select a type...',
                        selectOnFocus: true
                    })},
                {
                    header: "Length",
                    width: 75,
                    sortable: true,
                    dataIndex: 'length',
                    editor: new Ext.form.NumberField({
                        allowBlank: false,
                        allowNegative: false,
                        maxValue: 100000
                    })
                },
                {
                    header: "Precision",
                    width: 100,
                    sortable: true,
                    dataIndex: 'precision',
                    editor: new Ext.form.NumberField({
                        allowBlank: false,
                        allowNegative: false,
                        maxValue: 100000
                    })},
                {header: "Currency", width: 75, sortable: true, dataIndex: 'currency'},
                {header: "Decimal", width: 75, sortable: true, dataIndex: 'decimal'},
                {header: "Group", width: 75, sortable: true, dataIndex: 'group'},
                {
                    header: "Trim Type",
                    width: 75,
                    sortable: true,
                    dataIndex: 'trimtype',
                    editor: new Ext.form.ComboBox({
                        store: new Ext.data.SimpleStore({
                            fields: ['index', 'type'],
                            data: [
                                [0, "none"],
                                [1, "left"],
                                [2, "right"],
                                [3, "both"]
                            ]
                        }),
                        displayField: 'type',
                        valueField: 'index',
                        typeAhead: true,
                        mode: 'local',
                        triggerAction: 'all',
                        emptyText: 'Select a type...',
                        selectOnFocus: true
                    })
                }
            ])
        });

        var getMetadataPage = new Ext.ux.etl.BaseWizardEditorPage({
            name: 'getmetadata',
            title: 'Get metadata fields',
            monitorValid: true,
            layout: 'fit',
            defaults: {
                labelStyle: 'font-size:11px'
            },
            items: [getmetadataGrid],
            onCardShow: function (page) {
                //Call super
                Ext.ux.etl.BaseWizardEditorPage.prototype.onCardShow.apply(this, arguments);

                this.parentEditor.getValuesManager().executeOnGetMetadataRequest(function(response, opts) {
                    var data = Ext.decode(response.responseText);
                    getmetadataGridStore.loadData(data, false);
                    this.parentEditor.getValuesManager().updateRecordProperty("inputFields",data.rows);
                }.bind(this));
            }
        });

        //Preview data
        var previewDataDS = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: '/etl/core/textfileinputmeta/previewdata',
                method: 'POST',
                headers: {
                    'userid': 'test'
                }}),
            reader: new Ext.ux.dynagrid.DynamicJsonReader({root: 'rows',totalProperty: 'totalCount'}),
            remoteSort: true,
            root: 'rows'
        });


        var previewDataGrid = new Ext.grid.EditorGridPanel({
            //title: 'test',
            autoScroll: true,
            height: 300,
            ds: previewDataDS,
            cm: new Ext.ux.dynagrid.DynamicColumnModel(previewDataDS),
            selModel: new Ext.grid.RowSelectionModel({singleSelect: true}),
            tbar: [
                {
                    text: 'Preview',
                    tooltip: 'Refresh data',
                    iconCls: 'icon-refresh',
                    id: 'btn-refresh',
                    listeners: {
                        click: function() {
                            previewDataGrid.getBottomToolbar().refresh();
                        }
                    }
                }
            ],
            bbar: new Ext.ux.etl.EditorGridPagingToolbar({
                pageSize: 10,
                store: previewDataDS,
                displayInfo: true,
                displayMsg: 'Displaying sample records {0} - {1} of {2}',
                emptyMsg: "No data to display",

                items:[
                    '-', {
                        pressed: true,
                        enableToggle:true,
                        text: 'Show Preview',
                        cls: 'x-btn-text-icon details',
                        toggleHandler: function(btn, pressed){
                            var view = grid.getView();
                            view.showPreview = pressed;
                            view.refresh();
                        }
                    }]
            })
            /*,
             bbar : new Ext.PagingToolbar({
             store:previewDataDS
             ,displayInfo:true
             ,pageSize:10
             }),
             plugins: [
             new Ext.ux.grid.Search({
             iconCls: 'icon-zoom',
             readonlyIndexes: ['note'],
             disableIndexes: ['pctChange']
             })
             ]*/
        });

        var previewDataPage = new Ext.ux.etl.BaseWizardEditorPage({
            name: 'previewdata',
            title: 'Preview data',
            monitorValid: true,
            layout: 'fit',
            defaults: {
                labelStyle: 'font-size:11px'
            },
            items: [{
                layout: 'fit',
                items: [
                        previewDataGrid
                    ]
            }],
            onCardShow: function (card) {
                Ext.ux.etl.BaseWizardEditorPage.prototype.onCardShow.apply(this, arguments);

                //Setup BBAR - update record
                previewDataGrid.getBottomToolbar().setJsonEntity(this.parentEditor.getValuesManager().getRecord());

                //Init paged load
                previewDataGrid.getBottomToolbar().doLoad(0);
            }
        });

        //-- Create New Wizard
        this.newTextFileInputMetaWiz = new Ext.ux.etl.BaseWizardEditor({
            parentEditor: this,
            eventManager: this.eventManager,
            region: 'center',
            wizMode: this.wizMode,
            buttonsAt: 'bbar',
            headerConfig: {
                title: 'Text File Input Metadata'
            },
            cardPanelConfig: {
                defaults: {
                    baseCls: 'x-small-editor',
                    bodyStyle: 'padding: 20px 15px 5px 5px; background-color:#F6F6F6;',
                    border: false
                }
            },
            cards: [
                selectSampleFilePage,
                enterMetadataSettingsPage,
                getMetadataPage,
                previewDataPage
            ],
            initParams: {
                sampleFileNode: this.sampleFileNode
            },
            onNewURL: '/etl/core/textfileinputmeta/onnew',//e.g. /etl/core/textfileinputmeta/onnew,
            onGetMetadataURL: '/etl/core/textfileinputmeta/ongetmetadata',//e.g. /etl/core/textfileinputmeta/ongetmetadata,
            onPreviewURL: '/etl/core/textfileinputmeta/previewdata',//e.g. /etl/core/textfileinputmeta/previewdata
            onSaveURL: '/etl/core/textfileinputmeta/save',//e.g. /etl/core/textfileinputmeta/save
            onAddURL: '/etl/core/textfileinputmeta/add',//e.g. /etl/core/textfileinputmeta/add
            onDeleteURL: '/etl/core/textfileinputmeta/delete',//e.g. '/etl/core/textfileinputmeta/delete'
            onBackToFirstStep : function() {
                this.cardPanel.getLayout().setActiveItem(0);
            },
            //@Override
            onFinish : function()
            {
                if (this.wizMode === 'CREATE')
                    this.getValuesManager().executeOnAddDataRequest(this.parentEditor.folderId,function(response, opts) {
                        var title = this.getValuesManager().getRecord();
                        this.eventManager.raiseEvent({type:ORYX.CONFIG.EVENT_ETL_METADATA_CREATED,forceExecution:true,name:title,treeNodeParentId:this.parentEditor.parentNavNodeId});
                        this.parentEditor.newWizDialog.close();
                    }.bind(this));
                else if (this.wizMode === 'EDIT')
                    this.getValuesManager().executeOnAddDataRequest(function(response, opts) {
                        var title = this.parentEditor.getValuesManager().getRecord();
                        this.eventManager.raiseEvent({type:ORYX.CONFIG.EVENT_ETL_METADATA_CREATED,forceExecution:true,name:title,treeNodeParentId:this.parentNavNodeId});
                        this.parentEditor.newWizDialog.close();
                    }.bind(this));
            }
        });
        this.newTextFileInputMetaWiz.addEvents(
            /**
             * @event ORYX.CONFIG.EVENT_ETL_METADATA_CREATED
             * Fires after new metadata artifact (e.g. DbConnection) has been created
             * @param {name:'<artifact name>',treeNodeParentId:<>}
             */
            ORYX.CONFIG.EVENT_ETL_METADATA_CREATED
        );
        this.newTextFileInputMetaWiz.on('cancel', this.onBeforeCancel, this);
    },

    /**
     *
     */
    onBeforeCancel: function() {
        if (this.newTextFileInputMetaWiz.isDirty()) {
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
        }
        return false;//Don't close wizard - close dialog
    },

    /**
     * Handle ORYX.CONFIG.EVENT_ETL_METADATA_CREATE_PREFIX+'DBConnection' Event
     * @param event
     * @param arg - tree node
     */
    onCreate: function (event, arg) {
        this.wizMode = 'CREATE';
        this.folderId = arg.folderId;
        this.parentNavNodeId = arg.sourceNavNodeId;
        this.sampleFileNode = arg.dropData.source;

        // Basic Dialog
        this.initWiz();

        this.newWizDialog = new Ext.Window({
            autoScroll: false,
            autoCreate: true,
            closeAction:'destroy',
            title: 'New Text File Input Metadata',
            height: 500,
            width: 850,
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
            items: [this.newTextFileInputMetaWiz],
            bodyStyle: "background-color:#FFFFFF"
        });

        this.newWizDialog.show();
    },
    /**
     * Handle ORYX.CONFIG.EVENT_ETL_METADATA_EDIT_PREFIX+'DBConnection' Event
     * @param event
     * @param arg - tree node
     */
    onEdit: function (event, arg) {
        this.wizMode = 'EDIT';
        this.folderId = arg.folderId;
        this.metaId = arg.sourceNavNodeId;
        this.metaName = arg.title;

        // Basic Dialog
        this.initWiz();
        this.newTextFileInputMetaWiz.on('render',function() {
            // Get data
            Ext.lib.Ajax.request = Ext.lib.Ajax.request.createInterceptor(function (method, uri, cb, data, options) {
                // here you can put whatever you need as header. For instance:
                this.defaultPostHeader = "application/json; charset=utf-8;";
                this.defaultHeaders = {userid: 'test'};
            });
            Ext.Ajax.request({
                url: '/etl/core/textfileinputmeta/onedit',
                method: 'GET',
                params: {pathId:this.metaId},
                success: function (response, opts) {
                    var rec = Ext.decode(response.responseText);
                    this.newTextFileInputMetaWiz.loadRecord({data:rec});
                    //read-only field hack - update 'filename' field
                    var field = Ext.getCmp('uploadsamplefile.filename');
                    field.setValue(rec.filename);
                }.bind(this)
            });
        },this);

        this.newWizDialog = new Ext.Window({
            autoScroll: false,
            autoCreate: true,
            closeAction:'destroy',
            title: 'Editing...',
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
            items: [this.newTextFileInputMetaWiz],
            bodyStyle: "background-color:#FFFFFF"
        });
        this.newWizDialog.setTitle('Editing '+this.metaName);
        this.newWizDialog.show();
    },
    /**
     * Handle ORYX.CONFIG.EVENT_ETL_METADATA_DELETE_PREFIX+'CSVMeta' Event
     * @param event
     * @param arg - tree node
     */
    onDelete: function (event, arg) {
        this.wizMode = 'EDIT';
        this.folderId = arg.parentSourceNavNodeId;
        this.metaId = arg.sourceNavNodeId;
        this.metaName = arg.title;

        Ext.MessageBox.show({
            title:'Confirm delete.',
            msg: 'Delete '+arg.title+' ?',
            buttons: Ext.MessageBox.YESNO,
            fn: function(btn){
                if (btn === 'yes'){
                    Ext.lib.Ajax.request = Ext.lib.Ajax.request.createInterceptor(function (method, uri, cb, data, options) {
                        // here you can put whatever you need as header. For instance:
                        this.defaultPostHeader = "application/json; charset=utf-8;";
                        this.defaultHeaders = {userid: 'test'};
                    });
                    Ext.Ajax.request({
                        url: '/etl/core/textfileinputmeta/delete',
                        method: 'DELETE',
                        params: Ext.encode({name:this.metaName,pathId:this.metaId}),
                        success: function (response, opts) {
                            var text = Ext.encode(response.responseText);
                            Ext.MessageBox.show({
                                title:'Success',
                                msg: text,
                                buttons: Ext.MessageBox.OK,
                                icon: Ext.MessageBox.INFO
                            });
                            this.eventManager.raiseEvent({type:ORYX.CONFIG.EVENT_ETL_METADATA_DELETED,forceExecution:true,treeNodeParentId:this.folderId});
                        }.bind(this)
                    });
                }
            }.bind(this),
            icon: Ext.MessageBox.QUESTION
        });
    }
}
ORYX.Plugins.ETL.Metadata.TextFileInputMetaEditor = Clazz.extend(ORYX.Plugins.ETL.Metadata.TextFileInputMetaEditor);