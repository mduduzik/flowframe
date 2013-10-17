if (!ORYX.Plugins) {
    ORYX.Plugins = new Object();
}

if (!ORYX.Plugins.ETL) {
    ORYX.Plugins.ETL = new Object();
}

if (!ORYX.Plugins.ETL.Trans) {
    ORYX.Plugins.ETL.Trans = new Object();
}


ORYX.Plugins.ETL.Trans.TransPropertyWindow = ORYX.Plugins.ETL.BasePropertyWindow.extend({
    ns: 'http://etl.flowframe.org/stencilset/etl/trans#',

	construct: function() {
        this.iconCls = 'transformation-icon';
        this.title = 'Transformation';

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
           arguments.callee.$.onSelectDiagram.apply(this);
           return;
        }
        else {
            arguments.callee.$.onSelectionChanged.apply(this);
        }
    },
    onStencilSetLoaded: function() {
        // Call super method
        arguments.callee.$.onStencilSetLoaded.apply(this, arguments);
        arguments.callee.$.onSelectDiagram.apply(this);
    }
});