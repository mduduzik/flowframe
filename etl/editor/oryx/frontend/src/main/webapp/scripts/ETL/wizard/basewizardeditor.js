Ext.namespace('Ext.ux', 'Ext.ux.etl');

Ext.ux.etl.BaseWizardEditor = Ext.extend(Ext.ux.Wiz, {
    initParams: undefined,

    eventManager : undefined,

    valuesManager: undefined,

    onNewURL: undefined,//e.g. /etl/core/textfileinputmeta/onnew,
    onGetMetadataURL: undefined,//e.g. /etl/core/textfileinputmeta/ongetmetadata,
    onPreviewURL: undefined,//e.g. /etl/core/textfileinputmeta/previewdata
    onSaveURL: undefined,//e.g. /etl/core/textfileinputmeta/save
    onDeleteURL: undefined,//e.g. '/etl/core/textfileinputmeta/delete',
    /**
     * Inits this component with the specified config-properties and automatically
     * creates its components.
     */
    initComponent : function()
    {
        //Init cards/pages
        this.cards.each(function(_card) {
            _card.setParentEditor(this);
        }.bind(this));

        //Init data manager
        this.valuesManager = new ORYX.Data.ValuesManager({eventManager: this.eventManager,
            onNewURL: this.onNewURL,
            onGetMetadataURL: this.onGetMetadataURL,
            onPreviewURL: this.onPreviewURL,
            onSaveURL: this.onSaveURL,
            onDeleteURL: this.onDeleteURL
        });
        this.valuesManager.getFacade().registerFormPanels(this.cards);
        this.addEvents(
            /**
             * @event aftermodelupdate
             * Fires after the cancel-button has been clicked.
             * @param {Ext.ux.Wiz} this
             */
            //'aftermodelupdate'
            /**
             * @event finish
             * Fires after the last card was reached in the wizard and the
             * next/finish-button has been clicked.
             * @param {Ext.ux.Wiz} this
             * @param {Object} data The collected data of the cards, whereas
             * the index is the id of the card and the specific values
             * are objects with key/value pairs in the form formElementName : value
             */
            //'finish'
        );

        //--Events
        this.on('render',function() {
            this.valuesManager.getFacade().executeOnNewRequest();
        },this);

        Ext.ux.etl.BaseWizardEditor.superclass.initComponent.call(this);
    },

// -------- helper
    /**
     * Returns the form-data of all cards in this wizard. The first index is the
     * id of the card in this wizard,
     * and the values are objects containing key/value pairs in the form of
     * fieldName : fieldValue.
     *
     * @return {Array}
     */
    getValuesManager : function()
    {
        return this.valuesManager.getFacade();
    }
    /**
     * Returns the form-data of all cards in this wizard. The first index is the
     * id of the card in this wizard,
     * and the values are objects containing key/value pairs in the form of
     * fieldName : fieldValue.
     *
     * @return {Array}
     */
    ,getValuesForSubmission : function()
    {
        return this.valuesManager.getFacade().getValuesForSubmission();
    },
    /**
     * Loads data to cards/forms
     */
    loadRecord : function(record)
    {
        this.valuesManager.getFacade().loadRecord(record);
    },
    /**
     * Check if dirty
     */
    isDirty : function()
    {
        this.valuesManager.getFacade().isDirty()
    }
    ,isDirty : function()
    {
        this.valuesManager.getFacade().isDirty()
    }

});

Ext.ux.etl.BaseWizardEditorPage = Ext.extend(Ext.ux.Wiz.Card , {
    parentEditor : undefined,
    initialized : false,
    onAfterModelLoad: undefined,
    onBeforeModelSubmission: undefined,
    initComponent : function()
    {

        //var itemsConfig = {items: this.items};

        Ext.ux.etl.BaseWizardEditorPage.superclass.initComponent.call(this);

        //Ext.apply(this.form,itemsConfig);
    },
    setParentEditor: function(editor) {
        this.parentEditor = editor;
    },
    getDisabledFieldValue: function () {
        var me = this,
            data = null;
        data = {};
        data[me.name] = '' + me.getValue();
        return data;
    },
    getCheckboxFieldValue: function () {
        var me = this,
            data = null;
        var val = me.getValue();
        data = {};
        if (val === 'on')
            data[me.name] = true;
        else
            data[me.name] = false;

        return data;
    }
});


Ext.ux.etl.EditorGridPagingToolbar = Ext.extend(Ext.PagingToolbar,{
    onLoadFn: undefined,
    jsonEntity: undefined,
    baseURL: null,
    paramNames: {
        start: 'start',
        limit: 'pageSize'
    },
    currentStart: 0,
    setOnLoadCallback: function(fn) {
        this.onLoadFn = fn;
    },
    setJsonEntity: function(jsonEntity) {
        this.jsonEntity  = Ext.encode(jsonEntity);
    },
    //@Override
    doLoad : function(start){
        //var o = {}, pn = this.paramNames;
        //o[pn.start] = start;
        //o[pn.limit] = this.pageSize;
        //this.onLoadFn(o);
        this.currentStart = start;

        if (this.baseURL === null)
            this.baseURL = this.store.proxy.conn.url;

        this.store.proxy.conn.url = this.baseURL+'/'+start+'/'+this.pageSize;
        this.store.load({params:this.jsonEntity});
    },
    //@Override
    onLoad : function(store, r, o){
        if(!this.rendered){
            this.dsLoaded = [store, r, o];
            return;
        }
        this.cursor = this.currentStart;
        var d = this.getPageData(), ap = d.activePage, ps = d.pages;

        this.afterTextEl.el.innerHTML = String.format(this.afterPageText, d.pages);
        this.field.dom.value = ap;
        this.first.setDisabled(ap == 1);
        this.prev.setDisabled(ap == 1);
        this.next.setDisabled(ap == ps);
        this.last.setDisabled(ap == ps);
        this.loading.enable();
        this.updateInfo();
    }
});