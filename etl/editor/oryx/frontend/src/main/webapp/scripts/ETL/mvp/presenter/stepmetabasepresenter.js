if (!ORYX.Plugins) {
    ORYX.Plugins = new Object();
}

if (!ORYX.Plugins.ETL) {
    ORYX.Plugins.ETL = new Object();
}

if (!ORYX.Plugins.ETL.Metadata) {
    ORYX.Plugins.ETL.Metadata = new Object();
}

ORYX.Plugins.ETL.Metadata.StepMetaBasePresenter = {
    eventManager: undefined,
    dataPresenter: undefined,
    folderId: undefined,
    metaId: undefined,
    parentNavNodeId: undefined,
    wizMode: undefined,  //EDITING, CREATE

    shapeConfig: undefined,
    editorMode: undefined, //STEP, METADATA

    editorDialog: undefined,
    stepMetaWizard: undefined,

    model: undefined,

    dataConfig: undefined,
    itemType: undefined,

    construct: function (eventManager) {
        //Init data manager
        this.dataPresenter = new ORYX.ETL.DataPresenter({
            editorMode: this.editorMode,
            eventManager: this.eventManager,
            onNewURL: this.dataConfig.onNewURL,
            onGetMetadataURL: this.dataConfig.onGetMetadataURL,
            onPreviewURL: this.dataConfig.onPreviewURL,
            onSaveURL: this.dataConfig.onSaveURL,
            onAddURL: this.dataConfig.onAddURL,
            onEditURL: this.dataConfig.onEditURL,
            onDeleteURL: this.dataConfig.onDeleteURL
        });

        //Events
        //-- Global
        this.eventManager.registerOnEvent(ORYX.CONFIG.EVENT_ETL_METADATA_CREATE_PREFIX + this.itemType, this.onCreate.bind(this));
        this.eventManager.registerOnEvent(ORYX.CONFIG.EVENT_ETL_METADATA_EDIT_PREFIX + this.itemType, this.onEdit.bind(this));
        this.eventManager.registerOnEvent(ORYX.CONFIG.EVENT_ETL_METADATA_DELETE_PREFIX + this.itemType, this.onDelete.bind(this));

        this.eventManager.registerOnEvent(ORYX.CONFIG.EVENT_ETL_STEP_EDIT_PREFIX + this.itemType, this.onStepEdit.bind(this));
    },

    /**
     *
     */
    onBeforeCancel: function() {
        if (this.stepMetaWizard.isDirty()) {
            Ext.MessageBox.show({
                title:'Save Changes?',
                msg: 'There might be unsaved changes. <br />Do you still want to cancel?',
                buttons: Ext.MessageBox.YESNO,
                fn: function(btn){
                    if (btn === 'yes'){
                        this.editorDialog.close();
                    }
                }.bind(this),
                icon: Ext.MessageBox.QUESTION
            });
        }
        else {//Close anyways
            this.editorDialog.close();
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
        this.parentNavNodeId = arg.metaPathId;
        this.sampleFileNode = arg.dropData.source;

        this.metaName = 'New TextFileInput Metadata';

        //-- Launch
        this._launchEditor();
    },
    /**
     * Handle ORYX.CONFIG.EVENT_ETL_METADATA_EDIT_PREFIX+'DBConnection' Event
     * @param event
     * @param arg - tree node
     */
    onEdit: function (event, arg) {
        this.wizMode = 'EDIT';
        this.folderId = arg.folderId;
        this.metaId = arg.metaPathId;

        this.metaName = 'Editing '+arg.title;

        //-- Launch
        this._launchEditor();
    },
    /**
     * Handle ORYX.CONFIG.EVENT_ETL_STEP_EDIT_PREFIX+'DBConnection' Event
     * @param event
     * @param arg - tree node
     */
    onStepEdit: function (event, arg) {
        this.editorMode = 'STEP',
        this.wizMode = 'EDIT';
        this.folderId = arg.folderId;
        this.metaId = arg.metaId;
        this.shapeConfig = arg.shapeConfig;

        var shapeNamePropId		= arg.shapeConfig.shapeObjectLabelProp.prefix() + "-" + arg.shapeConfig.shapeObjectLabelProp.id();
        this.metaName = 'Editing Step '+arg.shapeConfig.shapeObject.properties[shapeNamePropId];

        //-- Launch
        this._launchEditor();
    },
    _launchEditor: function() {
        // Editor dialog
        this.initWizard();

        this.stepMetaWizard.on('cancel', this.onBeforeCancel, this);

        this.editorDialog = new Ext.Window({
            autoScroll: false,
            autoCreate: true,
            closeAction:'destroy',
            title: this.metaName,
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
                        this.editorDialog.hide
                    }.bind(this)
                }
            ],
            items: [this.stepMetaWizard],
            bodyStyle: "background-color:#FFFFFF"
        });

        this.editorDialog.show();
    },
    /**
     * Handle ORYX.CONFIG.EVENT_ETL_METADATA_DELETE_PREFIX+'CSVMeta' Event
     * @param event
     * @param arg - tree node
     */
    onDelete: function (event, arg) {
        this.wizMode = 'EDIT';
        this.folderId = arg.parentSourceNavNodeId;
        this.metaId = arg.metaPathId;
        this.metaName = arg.title;

        Ext.MessageBox.show({
            title:'Confirm delete.',
            msg: 'Delete '+arg.title+' ?',
            buttons: Ext.MessageBox.YESNO,
            fn: function(btn){
                if (btn === 'yes'){
                    this.dataPresenter.getFacade().executeOnDeleteDataRequest(this.metaId,function(message) {
                        Ext.MessageBox.show({
                            title:'Success',
                            msg: message,
                            buttons: Ext.MessageBox.OK,
                            icon: Ext.MessageBox.INFO
                        });
                        this.eventManager.raiseEvent({type:ORYX.CONFIG.EVENT_ETL_METADATA_DELETED,forceExecution:true,treeNodeParentId:this.folderId});

                    }.bind(this));
                }
            }.bind(this),
            icon: Ext.MessageBox.QUESTION
        });
    }
}
ORYX.Plugins.ETL.Metadata.StepMetaBasePresenter = Clazz.extend(ORYX.Plugins.ETL.Metadata.StepMetaBasePresenter);