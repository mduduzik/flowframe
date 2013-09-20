Ext.namespace('Ext.ux.etl', 'Ext.ux.etl.metadata');
Ext.QuickTips.init();



Ext.ux.etl.metadata.CsvMetadataWiz = function (config) {
    var editWiz = undefined;
    var wizMode = config.mode;
    var pathId = config.pathId;
    /**
     *
     *   Create cards
     */
    //Enter sample file info
    var getDisabledFieldValue = function () {
        var me = this,
            data = null;
        data = {};
        data[me.getName()] = '' + me.getValue();
        return data;
    };
    var getCheckboxFieldValue = function () {
        var me = this,
            data = null;
        var val = me.getValue();
        data = {};
        if (val === 'on')
            data[me.getName()] = true;
        else
            data[me.getName()] = false;

        return data;
    };
    var uploaderPanel = new Ext.ux.UploadPanel({
        layout: 'fit',
        xtype: 'uploadpanel',
        addText: 'Add sample CSV file',
        buttonsAt: 'tbar',
        id: 'uppanel',
        url: '/etlrepo/csvinput/uploadsample',
        path: 'root',
        maxFileSize: 1048576,
        enableProgress: false,
        singleUpload: true
    });

    var enterSampleFileInfoCard = new Ext.ux.Wiz.Card({
        id: "getfields",
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
                    /*                        {
                     xtype: 'uploadpanel',
                     id: 'form-file',
                     emptyText: 'Select a sample CSV file',
                     fieldLabel: 'Filename',
                     name: 'filename',
                     buttonCfg: {
                     text: '',
                     iconCls: 'upload-icon'
                     },
                     allowBlank: false
                     },*/
                    {
                        fieldLabel: 'Metadata Name',
                        name: 'name',
                        emptyText: '<Enter name>',
                        allowBlank: false
                    },
                    {
                        fieldLabel: 'Filename',
                        name: 'filename',
                        emptyText: '<Upload sample file below>',
                        disabled: true,
                        allowBlank: false,
                        getSubmitData: getDisabledFieldValue
                    },
                    {
                        fieldLabel: 'File Repository ID',
                        name: 'fileEntryId',
                        disabled: true,
                        allowBlank: false,
                        getSubmitData: getDisabledFieldValue
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
            },
            uploaderPanel
        ],
        isValid: function () {
            var formPanel = Ext.getCmp('getfields');
            if (!formPanel.form.findField('filename') || !formPanel.form.findField('fileEntryId'))
                return false;

            var valid = Ext.ux.Wiz.Card.prototype.isValid.apply(this, arguments);
            valid = valid || formPanel.form.findField('filename').validate();
            valid = valid || formPanel.form.findField('fileEntryId').validate();

            return valid;
        }
    });
    // relay uploader events
    /*    uploaderPanel.relayEvents(uploaderPanel.uploader, [
     'allfinished'
     ]);*/
    uploaderPanel.on({allfinished: {scope: this, fn: function () {
        var formPanel = Ext.getCmp('getfields');
        var rec = uploaderPanel.store.getAt(0);//Only first response
        formPanel.form.findField('filename').setValue(rec.data.fileName, true);
        formPanel.form.findField('fileEntryId').setValue(rec.data.fileentryid, true);

        enterSampleFileInfoCard.fireEvent('clientvalidation', enterSampleFileInfoCard, true);

        uploaderPanel.removeAll();
    }}
    });


    if (wizMode && wizMode === 'CREATE') {
        enterSampleFileInfoCard.getForm().load({
            method: 'GET',
            headerConfig: {userid: 'test'},
            url: '/etlrepo/csvinput/onnew',
            waitMsg: 'Getting new record...',
            success: function (fp, o) {
                //msg('Success', 'Processed file "'+o.result.file+'" on the server');
            }
        });
    }
    else if (wizMode && wizMode === 'EDIT') {
        enterSampleFileInfoCard.getForm().load({
            method: 'POST',
            headerConfig: {userid: 'test'},
            params: {pathId:pathId},
            url: '/etlrepo/csvinput/onedit',
            waitMsg: 'Getting record '+pathId+'...',
            success: function (fp, o) {
                //msg('Success', 'Processed file "'+o.result.file+'" on the server');
            }
        });
    }

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
            newCsvMetaWiz.getMetadata();
        }
    });

    //Preview data
    var previewDataDS = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: '/etlrepo/csvinput/previewdata',
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
            newCsvMetaWiz.previewData();
        }
    });

    //-- Create New Wizard
    newCsvMetaWiz = new Ext.ux.Wiz({
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
            enterSampleFileInfoCard,
            getMetadataCard,
            previewDataCard
        ],
        onBackToFirstStep : function() {
            this.cardPanel.getLayout().setActiveItem(0);
        },
        //@Override
        onFinish : function()
        {
            newCsvMetaWiz.addMetadata();
        },
        addMetadata: function () {

            //-- Form data
            var data = this.getSelectWizardData([]);//Form
            var formPanel = Ext.getCmp('getfields');
            var filenameValue = formPanel.form.findField('filename').getSubmitData();
            Ext.apply(data, filenameValue);
            var feValue = formPanel.form.findField('fileEntryId').getSubmitData();
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
            Ext.apply(data,{subDirObjId:5})
            var dataJson = Ext.encode(data);
            Ext.lib.Ajax.request = Ext.lib.Ajax.request.createInterceptor(function (method, uri, cb, data, options) {
                // here you can put whatever you need as header. For instance:
                this.defaultPostHeader = "application/json; charset=utf-8;";
                this.defaultHeaders = {userid: 'test'};
            });
            Ext.Ajax.request({
                url: '/etlrepo/csvinput/add',
                method: 'POST',
                params: dataJson,
                success: function (response, opts) {
                    newCsvMetaWiz.mode = 'EDITING';
                    newCsvMetaWiz.onBackToFirstStep();
                },
                failure: function (response, opts) {
                }
            });
        },
        //Called by 'preview' data
        previewData: function () {

            //-- Form data
            var data = this.getSelectWizardData([]);//Form
            var formPanel = Ext.getCmp('getfields');
            var filenameValue = formPanel.form.findField('filename').getSubmitData();
            Ext.apply(data, filenameValue);
            var feValue = formPanel.form.findField('fileEntryId').getSubmitData();
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
            var formPanel = Ext.getCmp('getfields');
            var filenameValue = formPanel.form.findField('filename').getSubmitData();
            Ext.apply(data, filenameValue);
            var feValue = formPanel.form.findField('fileEntryId').getSubmitData();
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
                url: '/etlrepo/csvinput/ongetmetadata',
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

    /**
     * access methods
     */
    var setMode = function(mode) {
        this.wizmode = mode;
    };
    var setPathId = function(pathId) {
        this.pathId = pathId;
    };

    return newCsvMetaWiz;
};
