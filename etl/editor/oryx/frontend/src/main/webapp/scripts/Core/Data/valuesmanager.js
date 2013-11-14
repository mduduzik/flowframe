if(!ORYX) {var ORYX = {};}

if (!ORYX.Data) {
    ORYX.Data = new Object();
}

ORYX.Data.ValuesManager = {
    onNewURL: undefined,//e.g. /etl/core/textfileinputmeta/onnew,
    onGetMetadataURL: undefined,//e.g. /etl/core/textfileinputmeta/ongetmetadata,
    onPreviewURL: undefined,//e.g. /etl/core/textfileinputmeta/previewdata
    onSaveURL: undefined,//e.g. /etl/core/textfileinputmeta/save
    onAddURL: undefined,//e.g. /etl/core/textfileinputmeta/add
    onDeleteURL: undefined,//e.g. '/etl/core/textfileinputmeta/delete'

    eventManager: undefined,
    record: undefined,
    formPanels: new Hash(),//type FormPanel
    construct: function (config) {
        this.eventManager = config.eventManager;
        this.onNewURL = config.onNewURL;
        this.onGetMetadataURL = config.onGetMetadataURL;
        this.onPreviewURL = config.onPreviewURL;
        this.onSaveURL = config.onSaveURL;
        this.onAddURL = config.onAddURL;
        this.onDeleteURL = config.onDeleteURL;
        this.eventManager.registerOnEvent(ORYX.CONFIG.EVENT_ETL_MODEL_SAVED, this.onModelSaved.bind(this));
    },
    onModelSaved: function (event,args) {
    },
    /**
     * Returns facade.
     */
    getFacade: function() {

        // if there is no pluginfacade already created:
        if(!(this._getFacade))
            // create it.
            this._getFacade = {
                getFormPanels: function() {return this.formPanels.values();}.bind(this),
                getFormPanelById: function(id) {return this.formPanels[id];}.bind(this),
                setFormPanels: function(fps) {this.formPanels = fps;}.bind(this),
                registerFormPanels: this._registerFormPanels.bind(this),
                init: this._initManager.bind(this),
                getValuesForSubmission: this._getValuesForSubmission.bind(this),
                isDirty: this._isDirty.bind(this),

                //-- Record methods
                getRecord: function(record) {return this.record;}.bind(this),
                setRecord: function(record) {this.record = record;}.bind(this),
                loadRecord: this._loadRecord.bind(this),
                updateRecordProperty: this._updateRecordProperty.bind(this),

                //-- AJAX requests
                executeOnNewRequest: this._executeOnNewRequest.bind(this),
                executeOnGetMetadataRequest: this._executeOnGetMetadataRequest.bind(this),
                executeOnAddDataRequest: this._executeOnAddDataRequest.bind(this)
            };

        // return it.
        return this._getFacade;
    }
    //{{
    /**
     * Init manager
     * @param configParams
     */
    //}
    ,_initManager: function(record,formPanels) {
        this.record = record;
        this.formPanels = formPanels;
    }
    //{{
    /**
     * _loadRecord
     * @param formPanel
     */
    //}
    ,_loadRecord: function(record) {
        this.record = record;
        var cards           = this.cards;
        this.formPanels.values().each(function(_formPanel) {
            _formPanel.form.loadRecord({data:record});
        }.bind(this));
    }
    //{{
    /**
     * _loadRecord
     * @param formPanel
     */
    //}
    ,_updateRecordProperty: function(propName, propValue) {
        this.record[propName] = propValue;
    }

    //{{
    /**
     * _getValuesForSubmission
     * @param
     */
    //}
    ,_getValuesForSubmission: function() {
        this.formPanels.values().each(function(_formPanel) {
            var basicForm = _formPanel.form;
            if (basicForm) {
                var values = basicForm.getObjectValues();
                for (p in values) {
                    if (p in this.record)
                        this.record[p] = values[p];
                }
            }
        }.bind(this));
        return this.record;
    }
    //{{
    /**
     * _isDirty
     * @param
     */
    //}
    ,_isDirty: function() {
        var isDirty = false;
        this.formPanels.values().each(function(_formPanel) {
            if (_formPanel.form.isDirty())
                isDirty = true;
        }.bind(this));
        return isDirty;
    }
    //{{
    /**
     * _onAfterModelLoad
     * @param
     */
    //}
    ,_onAfterModelLoad: function() {
        this.formPanels.values().each(function(_formPanel) {
            if (_formPanel.onAfterModelLoad)
                _formPanel.onAfterModelLoad();
        }.bind(this));
    }
    //{{
    /**
     * _onBeforeModelSubmission
     * @param
     */
    //}
    ,_onBeforeModelSubmission: function() {
        this._getValuesForSubmission();
        this.formPanels.values().each(function(_formPanel) {
            if (_formPanel.onBeforeModelSubmission)
                _formPanel.onBeforeModelSubmission();
        }.bind(this));
    }
    //{{
    /**
     * _executeOnNewRequest
     * @param
     */
    //}
    ,_executeOnNewRequest: function() {
        this.eventManager.raiseEvent({
            type: ORYX.CONFIG.EVENT_LOADING_ENABLE,
            text: 'Loading new record'
        });
        var options = {
            url: this.onNewURL,
            method: 'GET',
            asynchronous: false,
            success: function (response, opts) {
                var record = Ext.decode(response.responseText);
                this._loadRecord(record);

                this._onAfterModelLoad();

                this.eventManager.raiseEvent({
                    type: ORYX.CONFIG.EVENT_LOADING_DISABLE
                });
            }.bind(this),
            failure: function (response, opts) {
                this.eventManager.raiseEvent({
                    type: ORYX.CONFIG.EVENT_LOADING_DISABLE
                });
            }.bind(this)
        };
        return this._executeRequest(options);
    }
    //{{
    /**
     * _executeOnGetMetadataRequest
     * @param
     */
        //}
     ,_executeOnGetMetadataRequest: function(successHandler) {
        this._onBeforeModelSubmission();
        var dataJson = Ext.encode(this.record);
        var options = {
            url: this.onGetMetadataURL,
            method: 'POST',
            asynchronous: false,
            params: dataJson,
            success: successHandler,
            failure: function (response, opts) {
            }.bind(this)
        };
        return this._executeRequest(options);
     }
    //{{
    /**
     * _executeOnAddDataRequest
     * @param
     */
    //}
    ,_executeOnAddDataRequest: function(subDirObjId,successHandler) {
        this._onBeforeModelSubmission();


        var dataJson = Ext.encode(this.record);


        var options = {
            url: this.onAddURL+'/'+subDirObjId,
            method: 'POST',
            asynchronous: false,
            params: dataJson,
            success: successHandler,
            failure: function (response, opts) {
            }.bind(this)
        };
        return this._executeRequest(options);
    }


    //{{
    /**
     * _executeRequest
     * @param
     */
        //}
     ,_executeRequest: function(options) {
        var headers =  {userid: 'test'};

        Ext.lib.Ajax.request = Ext.lib.Ajax.request.createInterceptor(function (method, uri, cb, data, options) {
            // here you can put whatever you need as header. For instance:
            this.defaultPostHeader = "application/json; charset=utf-8;";
            this.defaultHeaders = headers;
        });

        Ext.Ajax.request(options);
        return true;
    }
    //{{
    /**
     * _registerFormPanels
     * @param formPanels
     */
    //}
    ,_registerFormPanels: function(formPanels) {
        formPanels.each(function(_formPanel) {
            this.formPanels[_formPanel.id] = _formPanel;
        }.bind(this));
    }
}
ORYX.Data.ValuesManager = Clazz.extend(ORYX.Data.ValuesManager);