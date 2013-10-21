if (!ORYX.Plugins) {
    ORYX.Plugins = new Object();
}

if (!ORYX.Plugins.ETL) {
    ORYX.Plugins.ETL = new Object();
}

if (!ORYX.Plugins.ETL.Job) {
    ORYX.Plugins.ETL.Job = new Object();
}


ORYX.Plugins.ETL.Job.JobPropertyWindow = ORYX.Plugins.ETL.BasePropertyWindow.extend({
    ns: ORYX.CONFIG.NAMESPACE_ETL_JOB,

	construct: function() {
        this.iconCls = 'process-icon';
        this.title = 'Job'

        // Call super class constructor
        arguments.callee.$.construct.apply(this, arguments);
	},
    processOnSelectionChanged: function(event,args) {
        if (!event)
            return false;
        else
            return event.elements && event.elements.length == 0
    },
    onSelectionChanged: function(event,args) {
        if (!this.processOnSelectionChanged(event,args)) {
            //if (!this.disRegardEvent())
            //    arguments.callee.$.onSelectDiagram.apply(this);
            return;
        }
        else {
            arguments.callee.$.onSelectionChanged.apply(this);
        }
    },
    onStencilSetLoaded: function() {
        // Call super method
        arguments.callee.$.onStepPropertyWindow.apply(this, arguments);
        arguments.callee.$.onSelectDiagram.apply(this);
    }
});