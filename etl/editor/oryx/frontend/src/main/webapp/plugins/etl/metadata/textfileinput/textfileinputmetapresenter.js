if (!ORYX.Plugins) {
    ORYX.Plugins = new Object();
}

if (!ORYX.Plugins.ETL) {
    ORYX.Plugins.ETL = new Object();
}

if (!ORYX.Plugins.ETL.Metadata) {
    ORYX.Plugins.ETL.Metadata = new Object();
}

ORYX.Plugins.ETL.Metadata.TextFileInputMetaPresenter = ORYX.Plugins.ETL.Metadata.StepMetaBasePresenter.extend({
    construct: function (eventManager) {
        this.itemType = ORYX.CONFIG.ETL_METADATA_TYPE_DELIMTEDMETA;
        this.eventManager = eventManager;
        this.dataConfig = {
            onNewURL: '/etl/core/textfileinputmeta/onnew',
            onGetMetadataURL: '/etl/core/textfileinputmeta/ongetmetadata',
            onPreviewURL: '/etl/core/textfileinputmeta/previewdata',
            onSaveURL: '/etl/core/textfileinputmeta/save',
            onAddURL: '/etl/core/textfileinputmeta/add',
            onEditURL: '/etl/core/textfileinputmeta/onedit',
            onDeleteURL: '/etl/core/textfileinputmeta/delete'
        };


        // Call super class constructor
        arguments.callee.$.construct.apply(this, arguments);
    },

    //@Override
    initWizard: function () {
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
        var enterMetadataSettingsPage = new Ext.ux.etl.BaseWizardCardView({
            id: "entermetadatasettings",
            title: 'Enter/change settings',
            items: [
                {
                    fieldLabel: 'Metadata Name',
                    name: 'name',
                    emptyText: '<Enter name>',
                    allowBlank: false
                },
                {
                    fieldLabel: 'File Title',
                    name: 'fileTitle',
                    disabled: true,
                    allowBlank: false,
                    getSubmitData: this.getDisabledFieldValue//read-only hack
                },
                {
                    xtype: 'docrepotreecombo',
                    fieldLabel: 'File URI',
                    name: 'fileName',
                    hiddenFieldName: 'fileTitle',
                    //disabled: true,
                    allowBlank: false,
                    getSubmitData: this.getDisabledFieldValue//read-only hack
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
                    value: true
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
                    selectOnFocus: true
                }
                ,
                {
                    xtype: 'xcheckbox',
                    fieldLabel: 'Newline Possible In Fields',
                    name: 'newlinePossibleInFields',
                    inputValue: false,
                    value: false
                }
            ]
            ,onBeforeModelSubmission: function () {//Sync form-to-model
                //--name
                var parentStepMeta = {
                    stepname: this.getForm().getObjectValues().name
                };
                this.parentEditor.getDataPresenter().updateRecordProperty("parentStepMeta",parentStepMeta);

                if (this.parentEditor.wizMode === 'CREATE') {
                    //fileName
                    var fileNames = [];
                    fileNames.push('ff://repo/internal?fileentry#'+this.parentEditor.fileEntryId);
                    this.parentEditor.getDataPresenter().updateRecordProperty("fileName",fileNames);
                }
                else if (this.parentEditor.wizMode === 'EDIT') {
                    //fileName
                    var fileNames = [];
                    fileNames.push(this.parentEditor.getDataPresenter().getRecord().fileName);
                    this.parentEditor.getDataPresenter().updateRecordProperty("fileName",fileNames);
                }
            }
            ,onAfterModelLoad: function () {//Sync model-to-form
                var name =  this.parentEditor.getDataPresenter().getRecord().parentStepMeta.stepname;
                if (this.parentEditor.wizMode === 'CREATE') {
                    //Update record
                    this.onBeforeModelSubmission();

                    var fileTitle = this.parentEditor.initParams.fileEntryTitle;
                    var fileName = 'ff://repo/internal?fileentry#'+this.parentEditor.initParams.sampleFileNode.id;
                    this.form.setValues({
                        name: name,
                        fileTitle: fileTitle,
                        fileName: fileName
                    })
                }
                else if (this.parentEditor.wizMode === 'EDIT') {
                    var fileURI =  this.parentEditor.getDataPresenter().getRecord().fileName[0];//ff:// format
                    this.parentEditor.getDataPresenter().getFileEntryInfo(fileURI, function(fileinfo) {
                        this.form.setValues({
                            name: name,
                            fileTitle: fileinfo.title,
                            fileName: fileURI
                        })
                    }.bind(this));
                }
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

        var getMetadataPage = new Ext.ux.etl.BaseWizardCardView({
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
                Ext.ux.etl.BaseWizardCardView.prototype.onCardShow.apply(this, arguments);


                this.parentEditor.switchDialogState(false,'fetchingMetaData');
                this.parentEditor.getDataPresenter().executeOnGetMetadataRequest(function(response, opts) {
                    var data = Ext.decode(response.responseText);
                    getmetadataGridStore.loadData(data, false);
                    this.parentEditor.getDataPresenter().updateRecordProperty("inputFields",data.rows);
                    this.parentEditor.switchDialogState(true);
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
        });

        var previewDataPage = new Ext.ux.etl.BaseWizardCardView({
            name: 'previewdata',
            title: 'Preview data',
            monitorValid: true,
            layout: 'fit',
            items: [
                previewDataGrid
            ],
            onCardShow: function (card) {
                Ext.ux.etl.BaseWizardCardView.prototype.onCardShow.apply(this, arguments);

                //Setup BBAR - update record
                previewDataGrid.getBottomToolbar().setJsonEntity(this.parentEditor.getDataPresenter().getRecord());
                previewDataGrid.getBottomToolbar().setEditor(this.parentEditor);

                //Init paged load
                this.parentEditor.switchDialogState(false,'fetchingPreviewData');
                previewDataGrid.getBottomToolbar().doLoad(0);
                this.parentEditor.switchDialogState(true);
            }
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
                enterMetadataSettingsPage,
                getMetadataPage,
                previewDataPage
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
            onFinish : function()
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
                            var title = this.getDataPresenter().getRecord().parentStepMeta.stepname;
                            this.eventManager.raiseEvent({type:ORYX.CONFIG.EVENT_ETL_METADATA_CREATED,forceExecution:true,name:title,treeNodeParentId:this.parentEditor.parentNavNodeId});
                        }.bind(this));
                    }
                    else if (this.wizMode === 'EDIT')
                        this.getDataPresenter().executeOnAddDataRequest(this.metaId,function(response, opts) {
                            var title = this.getDataPresenter().getRecord();
    /*                        this.eventManager.raiseEvent({type:ORYX.CONFIG.EVENT_ETL_METADATA_CREATED,forceExecution:true,name:title,treeNodeParentId:this.parentNavNodeId});
                            this.parentEditor.editorDialog.close();*/
                        }.bind(this));
                }
            }
        });
    }
})