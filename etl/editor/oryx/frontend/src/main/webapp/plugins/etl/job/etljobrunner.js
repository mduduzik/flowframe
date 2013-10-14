if (!ORYX.Plugins) {
    ORYX.Plugins = new Object();
}

if (!ORYX.Plugins.ETL) {
    ORYX.Plugins.ETL = new Object();
}

if (!ORYX.Plugins.ETL.Job) {
    ORYX.Plugins.ETL.Job = new Object();
}

ORYX.Plugins.ETL.Job.ETLJobRunner = {

    facade: undefined,

    construct: function (facade) {
        // Reference to the Editor-Interface
        this.facade = facade;

        //this.facade.registerOnEvent(ORYX.CONFIG.EVENT_SHOW_PROPERTYWINDOW, this.init.bind(this));
        //this.facade.registerOnEvent(ORYX.CONFIG.EVENT_LOADED, this.selectDiagram.bind(this));

        this.facade.registerOnEvent(ORYX.CONFIG.EVENT_STENCIL_SET_LOADED, this.handleSSLoaded.bind(this));
    },

    createStepMetricGrid: function () {
        this.stepMetricColumnModel = new Ext.grid.ColumnModel({
            defaultWidth: 60,
                columns: [
            {
                //id: 'name',
                header: 'Step name',
                dataIndex: 'stepname',
                width: 120,
                sortable: true
            },
            {
                header: 'Read',
                dataIndex: 'linesRead',
                sortable: true
            },
            {
                header: 'Written',
                dataIndex: 'linesWritten',
                sortable: true
            },
            {
                header: 'Input',
                dataIndex: 'linesInput',
                sortable: true
            },
            {
                header: 'Output',
                dataIndex: 'linesOutput',
                sortable: true
            },
            {
                header: 'Updated',
                dataIndex: 'linesUpdated',
                sortable: true
            },
            {
                header: 'Rejected',
                dataIndex: 'linesRejected',
                sortable: true
            },
            {
                header: 'Errors',
                dataIndex: 'errors',
                sortable: true
            },
            {
                header: 'Time',
                dataIndex: 'seconds',
                sortable: true
            },
            {
                header: 'Speed',
                dataIndex: 'speed',
                sortable: true
            },
            {
                header: 'Stopped',
                dataIndex: 'stopped',
                sortable: true
            },
            {
                id: 'stepMetricColumnModel.paused',
                header: 'Paused',
                dataIndex: 'paused',
                sortable: true
            }
        ]});

        // creating the store for the model.
        this.stepMetricDataSource = new Ext.data.Store({
            proxy: new Ext.data.MemoryProxy(this.stepMetricData),
            reader: new Ext.data.ArrayReader({}, [
                {name: 'stepname'},
                {name: 'linesRead'},
                {name: 'linesWritten'},
                {name: 'linesInput'},
                {name: 'linesOutput'},
                {name: 'linesUpdated'},
                {name: 'linesRejected'},
                {name: 'errors'},
                {name: 'seconds'},
                {name: 'speed'},
                {name: 'stopped'},
                {name: 'paused'}
            ])
        });
        this.stepMetricDataSource.load();

        this.stepMetricGrid = new Ext.grid.GridPanel({
            title: 'Step Metrics',
            stripeRows: true,
            autoExpandColumn: 'stepMetricColumnModel.paused',
            width:'auto',
            // the column model
            colModel: this.stepMetricColumnModel,
            //enableHdMenu: true,
            store: this.stepMetricDataSource
        });

        return this.stepMetricGrid;
    },
    createLoggingGrid: function() {
        this.loggingGrid = new Ext.grid.GridPanel({
            title: 'Logging',
            stripeRows: true,
            autoExpandColumn: 'loggingColumnModel.message',
            width:'auto',
            // the column model
            colModel: new Ext.grid.ColumnModel({
                defaultWidth: 120,
                columns: [
                    {
                        header: 'Line',
                        dataIndex: 'linenr',
                        width: 50,
                        sortable: true
                    },
                    {
                        header: 'TS',
                        dataIndex: 'ts',
                        sortable: true
                    },
                    {
                        id: 'loggingColumnModel.message',
                        header: 'Message',
                        dataIndex: 'message',
                        sortable: true
                    }
                ]}),
            //enableHdMenu: true,
            store: new Ext.data.Store({
                proxy: new Ext.data.MemoryProxy(this.stepMetricData),
                reader: new Ext.data.ArrayReader({}, [
                    {name: 'linenr'},
                    {name: 'ts'},
                    {name: 'message'}
                ])
            })
        });
    },
    handleSSLoaded: function (event,config) {
        // the properties array
        this.stepMetricData = [];

        try{
            /* The currently selected shapes whos properties will shown */
            this.shapeSelection = new Hash();
            this.shapeSelection.shapes = new Array();
            this.shapeSelection.commonProperties = new Array();
            this.shapeSelection.commonPropertiesValues = new Hash();

            this.updaterFlag = false;

            //Create Run/Debug toolbar
            this.toolbar = new Ext.ux.SlicedToolbar({
                region: 'north',
                height: 24
            });
            var button = new Ext.Toolbar.Button({
                align: 'left',
                iconCls:        'process_run_icon',         // icons can also be specified inline
                cls:            'x-btn-icon',       // Class who shows only the icon
                itemId:         '1222222',
                tooltip:        'Run Transformation',  // Set the tooltip
                tooltipType:    'title',            // Tooltip will be shown as in the html-title attribute
                handler:        this.runTransGraph.bind(this),  // Handler for mouse click
                enableToggle:   undefined, // Option for enabling toggling
                toggleHandler:  undefined // Handler for toggle (Parameters: button, active)
            });

            //create step metrics grid
            this.createStepMetricGrid();

            //create logging grid
            this.createLoggingGrid();

            // creating the column model of the grid.
    /*        this.loggingPanelTemplate = new Ext.XTemplate(
                '<span style="border: 0pt; background: #ffffff;font-size: 8pt; important;">{logging_string}</span>'
            );
            this.loggingPanel = new Ext.Panel({
                id: 'ORYX.Plugins.ETL.Job.ETLJobRunner.loggingPanel',
                title: 'Logging',
                autoScroll: true,
                defaults: {anchor: '-20'},
                html: '<p>Run transformation</p>'
            });*/

            this.loggingField = new Ext.form.TextArea({
               style: 'border: 0pt; background: #ffffff;font-size: 8pt; important;'
            });
            this.loggingPanel = {
                xtype: 'form',
                layout:'fit',
                items: [
                    this.loggingField
                ]
            }



            var panel = {
                width:'auto',
                iconCls: 'run_exc_icon',
                title: 'Run',
                bodyStyle:'padding:0px',
                layout: 'border',
                items: [
                    this.toolbar,
                    {
                        id: 'ORYX.Plugins.ETL.Job.ETLJobRunner.tabpanel',
                        region: 'center',
                        xtype: 'tabpanel',
                        width:'auto',
                        activeItem: 0,
                        border: false,
                        //tabPosition:'left',  //choose 'left' or 'right' for vertical tabs; 'top' or 'bottom' for horizontal tabs
                        //textAlign:'right'
                        // this line is necessary for anchoring to work at
                        // lower level containers and for full height of tabs
                        //, anchor: '100% 100%'

                        // only fields from an active tab are submitted
                        // if the following line is not persent
                        //, deferredRender: false

                        // tabs
                        defaults: {
                            autoScroll: true,
                            layout:'fit'
                        },
                        items: [
                            this.loggingGrid,
                            this.stepMetricGrid,
                            {
                                title: 'Execution History'
                            }
                            ,
                            {
                                title: 'Performance Graph'
                            }
                        ]

                    }
                ]
            }


            this.region = this.facade.addToRegion("centerSouth", panel, "Run");
            this.region.render();
            this.toolbar.add(button);
            this.toolbar.add('-');
            this.toolbar.add('->');

            // Register on Events
            /*		this.grid.on('beforeedit', this.beforeEdit, this, true);
             this.grid.on('afteredit', this.afterEdit, this, true);
             this.grid.view.on('refresh', this.hideMoreAttrs, this, true);*/

            //this.grid.on(ORYX.CONFIG.EVENT_KEYDOWN, this.keyDown, this, true);

            // Renderer the Grid
            //this.grid.enableColumnMove = false;
            //this.grid.render();

            // Sort as Default the first column
            //this.dataSource.sort('name');
        } catch (e) {
            ORYX.Log.error("Plugin ORYX.Plugins.ETL.Job.RunETLJob init() failed",e);
        }

    },
    runSynchronously: function(){

        // Reset changes
        this.changeDifference = 0;
        var reqURI= "/etl/core/transjobservice/execute";

        // Get the serialized svg image source
        var svgClone 	= this.facade.getCanvas().getSVGRepresentation(true);
        var svgDOM 		= DataManager.serialize(svgClone);
        this.serializedDOM = Ext.encode(this.facade.getJSON());


        // Get the stencilset
        var ss = this.facade.getStencilSets().values()[0]

        // Define Default values
        var defaultData = {title:'Run Transformation', summary:'', type:ss.title(), url: reqURI, namespace: ss.namespace() }

        // Create a Template
        var dialog = new Ext.XTemplate(
            // TODO find some nice words here -- copy from above ;)
            '<form class="oryx_repository_edit_model" action="#" id="edit_model" onsubmit="return false;">',

            '<fieldset>',
            '<p class="description">' + ORYX.I18N.Save.dialogDesciption + '</p>',
            '<input type="hidden" name="namespace" value="{namespace}" />',
            '<p><label for="edit_model_title">' + ORYX.I18N.Save.dialogLabelTitle + '</label><input type="text" class="text" name="title" value="{title}" id="edit_model_title" onfocus="this.className = \'text activated\'" onblur="this.className = \'text\'"/></p>',
            '<p><label for="edit_model_summary">' + ORYX.I18N.Save.dialogLabelDesc + '</label><textarea rows="5" name="summary" id="edit_model_summary" onfocus="this.className = \'activated\'" onblur="this.className = \'\'">{summary}</textarea></p>',
            '<p><label for="edit_model_type">' + ORYX.I18N.Save.dialogLabelType + '</label><input type="text" name="type" class="text disabled" value="{type}" disabled="disabled" id="edit_model_type" /></p>',

            '</fieldset>',

            '</form>')

        // Create the callback for the template
        var callback = function(form){

            var title 		= form.elements["title"].value.strip();
            title 			= title.length == 0 ? defaultData.title : title;

            //added changing title of page after first save
            window.document.title = title + " - Oryx";

            var summary = form.elements["summary"].value.strip();
            summary 	= summary.length == 0 ? defaultData.summary : summary;

            var namespace	= form.elements["namespace"].value.strip();
            namespace		= namespace.length == 0 ? defaultData.namespace : namespace;

            win.destroy();

            // Send the request out
            this.sendRunRequest( reqURI, { data: this.serializedDOM, svg: svgDOM, title: title, summary: summary, type: namespace });

        }.bind(this);

        // Create a new window
        win = new Ext.Window({
            id:		'Job_Properties_Window',
            width:	'auto',
            height:	'auto',
            title:	'Run Transformation Job',
            modal:	true,
            bodyStyle: 'background:#FFFFFF',
            html: 	dialog.apply( defaultData ),
            buttons:[{
                text: 'Run',
                handler: function(){
                    callback( $('edit_model') )
                }
            },{
                text: ORYX.I18N.Save.close,
                handler: function(){
                    this.facade.raiseEvent({
                        type: ORYX.CONFIG.EVENT_LOADING_DISABLE
                    });
                    win.destroy();
                }.bind(this)
            }]
        });

        win.show();
    },
    padDigits: function(number, digits) {
        return Array(Math.max(digits - String(number).length + 1, 0)).join(0) + number;
    },
    unescapeHTML: function (str) {
        str = str.replace(/&quot;/g,'\"');
        str = str.replace(/&apos;/g,"'");
        str = str.replace(/%5B/g,'[');
        str = str.replace(/%5D/g,']');
        str = str.replace(/%7B/g,'{');
        str = str.replace(/%7D/g,'}');
        str = str.replace(/%3A/g,':');
        str = str.replace(/%3B/g,';');
        str = str.replace(/%2C/g,',');
        str = str.replace(/%22/g,'"');
        str = str.replace(/%27/g,'\'');
        str = str.replace(/%5C/g,'\\');
        return str;
    },
    sendRunRequest: function(url, params){

        // Send the request to the server.
        new Ajax.Request(url, {
            method: 'POST',
            asynchronous: false,
            parameters: params,
            onSuccess: (function(response) {
                var data = Ext.decode(response.responseText)[0];
                var status = data.transstatus;

                //Update logEEx
                var trace = status.logging_string;
                trace = trace.replace(/&quot;/g,'\"');
                trace = trace.replace(/&apos;/g,"'");
                var traceData = Ext.decode(trace);

                var logList = traceData.records;
                this.logData = [];
                for (var i = 0; i < logList.length; i++) {
                    var log =  logList[i];
                    this.logData.push([
                        this.padDigits(log([0],5)),
                        this.unescapeHTML(log[1]),
                        this.unescapeHTML(log[2])
                    ]);
                }
                this.loggingGrid.store.loadData(this.logData);

                var tabPanel = Ext.getCmp('ORYX.Plugins.ETL.Job.ETLJobRunner.tabpanel');
                var tabs = tabPanel.find( 'title', 'Logging' );
                tabPanel.setActiveTab(tabs[0]);
                //this.loggingField.setRawValue(trace);
                //var html = this.loggingPanelTemplate.applyTemplate(status);
                //this.loggingPanelTemplate.overwrite(tabs[0].body,trace);

                //Step Metric Grid
                var stepList = status.stepstatuslist.stepstatus;
                for (var i = 0; i < stepList.length; i++) {
                    var step =  stepList[i];
                    this.stepMetricData.push([
                        step.stepname,
                        step.linesRead,
                        step.linesWritten,
                        step.linesInput,
                        step.linesOutput,
                        step.linesUpdated,
                        step.linesRejected,
                        step.errors,
                        step.seconds,
                        step.speed,
                        step.stopped,
                        step.paused
                    ]);
                }
                this.stepMetricDataSource.loadData(this.stepMetricData);

            }).bind(this),
            onFailure: (function(transport) {
                // raise loading disable event.
                this.facade.raiseEvent({
                    type: ORYX.CONFIG.EVENT_LOADING_DISABLE
                });


                Ext.Msg.alert(ORYX.I18N.Oryx.title, ORYX.I18N.Save.failed);

                ORYX.Log.warn("Execution failed: " + transport.responseText);
            }).bind(this),
            on403: (function(transport) {
                // raise loading disable event.
                this.facade.raiseEvent({
                    type: ORYX.CONFIG.EVENT_LOADING_DISABLE
                });


                Ext.Msg.alert(ORYX.I18N.Oryx.title, ORYX.I18N.Save.noRights);

                ORYX.Log.warn("Execution failed: " + transport.responseText);
            }).bind(this)
        });

    },

    /**
     * Saves the current process to the server.
     */
    runTransGraph: function(){

        // raise loading enable event
        this.facade.raiseEvent({
            type: ORYX.CONFIG.EVENT_LOADING_ENABLE,
            text: 'Running Transformation Job...'
        });

        // asynchronously ...
        window.setTimeout((function(){

            // ... save synchronously
            this.runSynchronously();

        }).bind(this), 10);


        return true;
    },
    // Select the Canvas when the editor is ready
    selectDiagram: function () {
        this.shapeSelection.shapes = [this.facade.getCanvas()];

        this.setPropertyWindowTitle();
        this.identifyCommonProperties();
        this.createProperties();
    },

    /**
     * Changes the title of the property window panel according to the selected shapes.
     */
    setPropertyWindowTitle: function () {
        if (this.shapeSelection.shapes.length == 1) {
            // add the name of the stencil of the selected shape to the title
            region.setTitle(ORYX.I18N.PropertyWindow.title + ' (' + this.shapeSelection.shapes.first().getStencil().title() + ')');
        } else {
            region.setTitle(ORYX.I18N.PropertyWindow.title + ' ('
                + this.shapeSelection.shapes.length
                + ' '
                + ORYX.I18N.PropertyWindow.selected
                + ')');
        }
    },


    /**
     * Returns the set of stencils used by the passed shapes.
     */
    getStencilSetOfSelection: function () {
        var stencils = new Hash();

        this.shapeSelection.shapes.each(function (shape) {
            stencils[shape.getStencil().id()] = shape.getStencil();
        })
        return stencils;
    },

    setProperties: function () {
        var props = this.popularProperties.concat(this.stepMetricData);

        this.dataSource.loadData(props);
    }
}
ORYX.Plugins.ETL.Job.ETLJobRunner = Clazz.extend(ORYX.Plugins.ETL.Job.ETLJobRunner);
