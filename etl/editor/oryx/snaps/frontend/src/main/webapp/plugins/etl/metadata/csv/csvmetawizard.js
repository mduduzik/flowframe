if (!ORYX.Plugins) {
    ORYX.Plugins = new Object();
}

if (!ORYX.Plugins.ETL) {
    ORYX.Plugins.ETL = new Object();
}

if (!ORYX.Plugins.ETL.Metadata) {
    ORYX.Plugins.ETL.Metadata = new Object();
}

ORYX.Plugins.ETL.Metadata.CSVMetaWizard = {

    facade: undefined,
    folderId: undefined,
    metaId: undefined,
    parentNavNodeId: undefined,
    wizMode: undefined,  //EDITING, CREATE

    newWizDialog: undefined,
    newCsvMetaWiz: undefined,

    construct: function (facade) {
        // Reference to the Editor-Interface
        this.facade = facade;

        this.facade.registerOnEvent(ORYX.CONFIG.EVENT_ETL_METADATA_CREATE_PREFIX + 'CSVMeta', this.onCreate.bind(this));
        this.facade.registerOnEvent(ORYX.CONFIG.EVENT_ETL_METADATA_EDIT_PREFIX + 'CSVMeta', this.onEdit.bind(this));
        this.facade.registerOnEvent(ORYX.CONFIG.EVENT_ETL_METADATA_DELETE_PREFIX + 'CSVMeta', this.onDelete.bind(this));
    },


    initWiz: function () {
        var getDisabledFieldValue = function () {
            var me = this,
                data = null;
            data = {};
            data[me.name] = '' + me.getValue();
            return data;
        };
        var getCheckboxFieldValue = function () {
            var me = this,
                data = null;
            var val = me.getValue();
            data = {};
            if (val === 'on')
                data[me.name] = true;
            else
                data[me.name] = false;

            return data;
        };
        var uploaderPanel = new Ext.ux.UploadPanel({
            layout: 'fit',
            xtype: 'uploadpanel',
            addText: 'Add sample CSV file',
            buttonsAt: 'tbar',
            id: 'uppanel',
            url: '/etlrepo/csvmeta/uploadsample',
            path: 'root',
            maxFileSize: 1048576,
            enableProgress: false,
            singleUpload: true
        });
        /**
         * Cards
         */
        var uploadSampleFileCard = new Ext.ux.Wiz.Card({
            id: "uploadsamplefile",
            title: 'Upload Sample CSV file',
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
                            id: 'uploadsamplefile.fileEntryId',
                            fieldLabel: 'File Repository ID',
                            name: 'fileEntryId',
                            emptyText: '<Upload sample file below>',
                            disabled: true,
                            allowBlank: false,
                            required: true,
                            getSubmitData: getDisabledFieldValue
                        },
                        {
                            id: 'uploadsamplefile.filename',
                            xtype: 'fileuploadfield',
                            fieldLabel: 'Filename',
                            name: 'filename',
                            emptyText: '<Browse and select file first>',
                            disabled: true,
                            allowBlank: false,
                            getSubmitData: getDisabledFieldValue,
                            buttonCfg: {
                                text: '',
                                iconCls: 'up-icon',
                                disabled : true,
                                type: 'button',
                                buttonOnly: true,
                                handler: function(){
                                    var options = {
                                        url: '/etlrepo/csvmeta/uploadsample',
                                        enctype: 'multipart/form-data',
                                        isUpload: true,
                                        success: function(fp, o){
                                            var jsonResp = fp.responseText.substring(fp.responseText.indexOf("{"), fp.responseText.lastIndexOf("}") + 1);
                                            var rec = Ext.decode(jsonResp);

                                            var formPanel = Ext.getCmp('uploadsamplefile');

                                            //Update fileName field
                                            var field = formPanel.form.findField('uploadsamplefile.filename');
                                            field.setValue(rec.fileName);
                                            field.button.disable();

                                            //Update uploadsamplefile.fileEntryId
                                            field = formPanel.form.findField('uploadsamplefile.fileEntryId');
                                            field.setValue(rec.fileentryid);

                                            //Clear fileuploadfield.form-file
                                            field = formPanel.form.findField('fileuploadfield.form-file');
                                            field.reset();
                                        }.bind(this),
                                        failure: function(fp, o){
                                            Oryx.Log.error('Error uploading file...')
                                        }.bind(this)
                                    };
                                    options.form = uploadSampleFileCard.form.el;
                                    // request upload
                                    Ext.Ajax.request(options);
                                }
                            }

                        },
                        {
                            xtype: 'fileuploadfield',
                            id: 'fileuploadfield.form-file',
                            emptyText: 'Select a CSV file',
                            fieldLabel: 'Local File to Upload',
                            name: 'file',
                            buttonCfg: {
                                text: '',
                                iconCls: 'find-job-icon',
                                type: 'file',
                                buttonOnly: true,
                                handler: function(){
                                    this.button.enable();
                                }
                            },
                            listeners:{
                                fileselected:function(field,value){
                                    var field = Ext.getCmp('uploadsamplefile.filename');
                                    field.setValue(value);
                                    field.button.enable();
                                }
                            }
                        }
                    ]
                }
            ],
            isValid: function () {
                var formPanel = Ext.getCmp('uploadsamplefile');
                if (!formPanel.form.findField('uploadsamplefile.filename') || !formPanel.form.findField('uploadsamplefile.fileEntryId'))
                    return false;

                var valid = Ext.ux.Wiz.Card.prototype.isValid.apply(this, arguments);
                valid = valid || formPanel.form.findField('uploadsamplefile.filename').validate();
                valid = valid || formPanel.form.findField('uploadsamplefile.fileEntryId').validate();

                return valid;
            }
        });
        var enterMetadataSettingsCard = new Ext.ux.Wiz.Card({
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
                            fieldLabel: 'Row Number Field',
                            name: 'rowNumField'
                        },
                        {
                            xtype: 'checkbox',
                            fieldLabel: 'Header Present',
                            name: 'headerPresent',
                            width: 15
                        },
                        {
                            fieldLabel: 'Delimiter',
                            name: 'delimiter'
                        },
                        {
                            fieldLabel: 'Enclosure',
                            name: 'enclosure'
                        },
                        {
                            xtype: 'numberfield',
                            fieldLabel: 'Buffer Size',
                            name: 'bufferSize',
                            style: 'text-align: left'
                        },
                        {
                            xtype: 'checkbox',
                            fieldLabel: 'Lazy Conversion',
                            name: 'lazyConversionActive',
                            width: 15
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
                                    url: '/etlrepo/encoding/getall'
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
                            xtype: 'checkbox',
                            fieldLabel: 'Newline Possible In Fields',
                            name: 'newlinePossibleInFields',
                            width: 15
                        }
                    ]
                }
            ]
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
            ]),
            height: 400,
            //title:'Company Data',
            border: true
        });

        var getMetadataCard = new Ext.ux.Wiz.Card({
            name: 'getmetadata',
            title: 'Get metadata fields',
            monitorValid: true,
            layout: 'fit',
            defaults: {
                labelStyle: 'font-size:11px'
            },
            items: [
                {
                    layout: 'fit',
                    items: [getmetadataGrid]
                }
            ],
            onCardShow: function (card) {
                Ext.ux.Wiz.Card.prototype.onCardShow.apply(this, arguments);
                this.newCsvMetaWiz.getMetadata();
            }.bind(this)
        });

        //Preview data
        var previewDataDS = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: '/etlrepo/csvmeta/previewdata',
                method: 'POST',
                headers: {
                    'userid': 'test'
                }}),
            reader: new Ext.ux.dynagrid.DynamicJsonReader({root: 'rows'}),
            remoteSort: true,
            root: 'rows'
        });
        var previewDataGrid = new Ext.grid.EditorGridPanel({
            //title: 'test',
            //id: 'test',
            height: 350,
            ds: previewDataDS,
            cm: new Ext.ux.dynagrid.DynamicColumnModel(previewDataDS),
            selModel: new Ext.grid.RowSelectionModel({singleSelect: true}),
            tbar: [
                {
                    text: 'Preview', tooltip: 'Refresh data', iconCls: 'icon-refresh', id: 'btn-refresh', listeners: {
                    //click:{scope:this, fn:wiz.previewData,buffer:200}
                }
                }
            ]/*,
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

        var previewDataCard = new Ext.ux.Wiz.Card({
            name: 'previewdata',
            title: 'Preview data',
            monitorValid: true,
            layout: 'fit',
            defaults: {
                labelStyle: 'font-size:11px'
            },
            items: [previewDataGrid],
            onCardShow: function (card) {
                Ext.ux.Wiz.Card.prototype.onCardShow.apply(this, arguments);
                this.newCsvMetaWiz.previewData();
            }.bind(this)
        });

        //-- Create New Wizard
        this.newCsvMetaWiz = new Ext.ux.Wiz({
            region: 'center',
            buttonsAt: 'bbar',
            headerConfig: {
                title: 'CSVInput Metadata'
            },
            cardPanelConfig: {
                defaults: {
                    baseCls: 'x-small-editor',
                    bodyStyle: 'padding: 20px 15px 5px 5px; background-color:#F6F6F6;',
                    border: false
                }
            },
            cards: [
                uploadSampleFileCard,
                enterMetadataSettingsCard,
                getMetadataCard,
                previewDataCard
            ],
            onBackToFirstStep : function() {
                this.cardPanel.getLayout().setActiveItem(0);
            },
            //@Override
            onFinish : function()
            {
                if (this.wizMode === 'CREATE')
                    this.addMetadata();
                else if (this.wizMode === 'EDIT')
                    this.saveMetadata();
            },
            saveMetadata: function () {
                //-- Form data
                if (this.newCsvMetaWiz.isDirty()) {
                    var data = this.newCsvMetaWiz.getSelectWizardData([]);//Form
                    var formPanel = Ext.getCmp('uploadsamplefile');
                    var filenameValue = formPanel.form.findField('uploadsamplefile.filename').getSubmitData();
                    Ext.apply(data, filenameValue);
                    var feValue = formPanel.form.findField('uploadsamplefile.fileEntryId').getSubmitData();
                    Ext.apply(data, feValue);

                    //--Fields
                    var fields = {inputFields: getmetadataGridStore.reader.jsonData.rows};//fields
                    Ext.apply(data, fields);

                    //-- Normalize data
                    //a hack: checkboxes are returning 'on' for true
                    if (data.headerPresent === 'on')
                        data.headerPresent = true;
                    else
                        data.headerPresent = false;
                    if (data.includingFilename === 'on')
                        data.includingFilename = true;
                    else
                        data.includingFilename = false;
                    if (data.isaddresult === 'on')
                        data.isaddresult = true;
                    else
                        data.isaddresult = false;
                    if (data.lazyConversionActive === 'on')
                        data.lazyConversionActive = true;
                    else
                        data.lazyConversionActive = false;
                    if (data.newlinePossibleInFields === 'on')
                        data.newlinePossibleInFields = true;
                    else
                        data.newlinePossibleInFields = false;


                    //--Submit
                    Ext.apply(data,{pathId:this.metaId,subDirObjId:this.folderId})
                    var dataJson = Ext.encode(data);
                    Ext.lib.Ajax.request = Ext.lib.Ajax.request.createInterceptor(function (method, uri, cb, data, options) {
                        // here you can put whatever you need as header. For instance:
                        this.defaultPostHeader = "application/json; charset=utf-8;";
                        this.defaultHeaders = {userid: 'test'};
                    });
                    Ext.Ajax.request({
                        url: '/etlrepo/csvmeta/save',
                        method: 'POST',
                        params: dataJson,
                        success: function (response, opts) {
                            var meta = Ext.decode(response.responseText);
                            this.facade.raiseEvent({type:ORYX.CONFIG.EVENT_ETL_METADATA_CREATED,forceExecution:true,name:meta.name,treeNodeParentId:this.parentNavNodeId});
                            this.newWizDialog.close();
                        }.bind(this),
                        failure: function (response, opts) {
                        }.bind(this)
                    });
                }
            }.bind(this),
            addMetadata: function () {
                //-- Form data
                var data = this.newCsvMetaWiz.getSelectWizardData([]);//Form
                var formPanel = Ext.getCmp('uploadsamplefile');
                var filenameValue = formPanel.form.findField('uploadsamplefile.filename').getSubmitData();
                Ext.apply(data, filenameValue);
                var feValue = formPanel.form.findField('uploadsamplefile.fileEntryId').getSubmitData();
                Ext.apply(data, feValue);

                //--Fields
                var fields = {inputFields: getmetadataGridStore.reader.jsonData.rows};//fields
                Ext.apply(data, fields);

                //-- Normalize data
                //a hack: checkboxes are returning 'on' for true
                if (data.headerPresent === 'on')
                    data.headerPresent = true;
                else
                    data.headerPresent = false;
                if (data.includingFilename === 'on')
                    data.includingFilename = true;
                else
                    data.includingFilename = false;
                if (data.isaddresult === 'on')
                    data.isaddresult = true;
                else
                    data.isaddresult = false;
                if (data.lazyConversionActive === 'on')
                    data.lazyConversionActive = true;
                else
                    data.lazyConversionActive = false;
                if (data.newlinePossibleInFields === 'on')
                    data.newlinePossibleInFields = true;
                else
                    data.newlinePossibleInFields = false;


                //--Submit
                Ext.apply(data,{subDirObjId:this.folderId})
                var dataJson = Ext.encode(data);
                Ext.lib.Ajax.request = Ext.lib.Ajax.request.createInterceptor(function (method, uri, cb, data, options) {
                    // here you can put whatever you need as header. For instance:
                    this.defaultPostHeader = "application/json; charset=utf-8;";
                    this.defaultHeaders = {userid: 'test'};
                });
                Ext.Ajax.request({
                    url: '/etlrepo/csvmeta/add',
                    method: 'POST',
                    params: dataJson,
                    success: function (response, opts) {
                        var meta = Ext.decode(response.responseText);
                        this.facade.raiseEvent({type:ORYX.CONFIG.EVENT_ETL_METADATA_CREATED,forceExecution:true,name:meta.name,treeNodeParentId:this.parentNavNodeId});
                        this.newWizDialog.close();
                    }.bind(this),
                    failure: function (response, opts) {
                    }.bind(this)
                });
            }.bind(this),
            //Called by 'preview' data
            previewData: function () {

                //-- Form data
                var data = this.getSelectWizardData([]);//Form
                var formPanel = Ext.getCmp('uploadsamplefile');
                var filenameValue = formPanel.form.findField('uploadsamplefile.filename').getSubmitData();
                Ext.apply(data, filenameValue);
                var feValue = formPanel.form.findField('uploadsamplefile.fileEntryId').getSubmitData();
                Ext.apply(data, feValue);

                //--Fields
                var fields = {inputFields: getmetadataGridStore.reader.jsonData.rows};//fields
                Ext.apply(data, fields);

                //-- Normalize data
                //a hack: checkboxes are returning 'on' for true
                if (data.headerPresent === 'on')
                    data.headerPresent = true;
                else
                    data.headerPresent = false;
                if (data.includingFilename === 'on')
                    data.includingFilename = true;
                else
                    data.includingFilename = false;
                if (data.isaddresult === 'on')
                    data.isaddresult = true;
                else
                    data.isaddresult = false;
                if (data.lazyConversionActive === 'on')
                    data.lazyConversionActive = true;
                else
                    data.lazyConversionActive = false;
                if (data.newlinePossibleInFields === 'on')
                    data.newlinePossibleInFields = true;
                else
                    data.newlinePossibleInFields = false;


                //--Submit
                var dataJson = Ext.encode(data);

                previewDataDS.load({
                    params: dataJson
                });
            },
            // Called by 'getmetadata' card
            getMetadata: function () {
                var data = this.getSelectWizardData([]);

                //-- Normalize data
                var data = this.getSelectWizardData([]);//Form
                var formPanel = Ext.getCmp('uploadsamplefile');
                var filenameValue = formPanel.form.findField('uploadsamplefile.filename').getSubmitData();
                Ext.apply(data, filenameValue);
                var feValue = formPanel.form.findField('uploadsamplefile.fileEntryId').getSubmitData();
                Ext.apply(data, feValue);



                //a hack: checkboxes are returning 'on' for true
                if (data.headerPresent === 'on')
                    data.headerPresent = true;
                else
                    data.headerPresent = false;
                if (data.includingFilename === 'on')
                    data.includingFilename = true;
                else
                    data.includingFilename = false;
                if (data.isaddresult === 'on')
                    data.isaddresult = true;
                else
                    data.isaddresult = false;
                if (data.lazyConversionActive === 'on')
                    data.lazyConversionActive = true;
                else
                    data.lazyConversionActive = false;
                if (data.newlinePossibleInFields === 'on')
                    data.newlinePossibleInFields = true;
                else
                    data.newlinePossibleInFields = false;


                //--Submit
                var dataJson = Ext.encode(data);
                Ext.lib.Ajax.request = Ext.lib.Ajax.request.createInterceptor(function (method, uri, cb, data, options) {
                    // here you can put whatever you need as header. For instance:
                    this.defaultPostHeader = "application/json; charset=utf-8;";
                    this.defaultHeaders = {userid: 'test'};
                });
                Ext.Ajax.request({
                    url: '/etlrepo/csvmeta/ongetmetadata',
                    method: 'POST',
                    params: dataJson,
                    success: function (response, opts) {
                        var recs = Ext.decode(response.responseText);
                        //Update 'getMetadata' card
                        getmetadataGridStore.loadData(recs, false);
                    },
                    failure: function (response, opts) {
                    }
                });
            },
            //@Override
            getSelectWizardData: function (cardids) {
                var formValues = {};
                var cards = this.cards;
                for (var i = 0, len = cards.length; i < len; i++) {

                    var cardform = cards[i].form;
                    if (cardform) {
                        var values = cardform.getValues(false);
                        var applyProps = true;
                        for (p in values) {
                            var propname = p+'';
                            if (propname.indexOf('ext-comp-') == 0)
                                applyProps = false;
                        }
                        if (applyProps)
                            Ext.apply(formValues, values);
                    }
                }
                return formValues;
            }
        });
        this.newCsvMetaWiz.addEvents(
            /**
             * @event ORYX.CONFIG.EVENT_ETL_METADATA_CREATED
             * Fires after new metadata artifact (e.g. DbConnection) has been created
             * @param {name:'<artifact name>',treeNodeParentId:<>}
             */
            ORYX.CONFIG.EVENT_ETL_METADATA_CREATED
        );
        this.newCsvMetaWiz.on('cancel', this.onBeforeCancel, this);
    },

    /**
     *
     */
    onBeforeCancel: function() {
        if (this.newCsvMetaWiz.isDirty()) {
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

        // Basic Dialog
        this.initWiz();

        this.newWizDialog = new Ext.Window({
            autoScroll: false,
            autoCreate: true,
            closeAction:'destroy',
            title: 'New CSV Metadata',
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
            items: [this.newCsvMetaWiz],
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
        this.newCsvMetaWiz.on('render',function() {
            // Get data
            Ext.lib.Ajax.request = Ext.lib.Ajax.request.createInterceptor(function (method, uri, cb, data, options) {
                // here you can put whatever you need as header. For instance:
                this.defaultPostHeader = "application/json; charset=utf-8;";
                this.defaultHeaders = {userid: 'test'};
            });
            Ext.Ajax.request({
                url: '/etlrepo/csvmeta/onedit',
                method: 'GET',
                params: {pathId:this.metaId},
                success: function (response, opts) {
                    var rec = Ext.decode(response.responseText);
                    this.newCsvMetaWiz.loadRecord({data:rec});
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
            items: [this.newCsvMetaWiz],
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
                        url: '/etlrepo/csvmeta/delete',
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
                            this.facade.raiseEvent({type:ORYX.CONFIG.EVENT_ETL_METADATA_DELETED,forceExecution:true,treeNodeParentId:this.folderId});
                        }.bind(this)
                    });
                }
            }.bind(this),
            icon: Ext.MessageBox.QUESTION
        });
    }
}
ORYX.Plugins.ETL.Metadata.CSVMetaWizard = Clazz.extend(ORYX.Plugins.ETL.Metadata.CSVMetaWizard);