if (!ORYX.Plugins)
    ORYX.Plugins = new Object();

ORYX.Plugins.New = ORYX.Plugins.AbstractPlugin.extend({
	
    facade: undefined,
	
	processURI: undefined,
	
    construct: function(facade){
		this.facade = facade;
		if (!(ORYX.CONFIG.IS_TEMPLATE)) {
			this.facade.offer({
				'name': 'NewTransformation',
				'functionality': this.newTransformation.bind(this,false),
				'group': 'New ETL Transformation',
				'icon': "/etl/images/conxbi/etl/transformation.png",
				'description': 'New Transformation',
				'index': 1,
				'minShape': 0,
				'maxShape': 0
			});
		}
		
		this.facade.offer({
			'name': 'NewJob',
			'functionality': this.newJob.bind(this,true),
			'group': 'New',
			'icon': "/etl/images/conxbi/etl/process_icon.gif",
			'description': 'New ETL Job',
			'index': 2,
			'minShape': 0,
			'maxShape': 0
		});
	},
    /**
     * New transformation
     */
    newTransformation: function(){
        var config = {
            ssns: ORYX.CONFIG.NAMESPACE_ETL_TRANS
        };
        this.facade.raiseEvent({
            type: ORYX.CONFIG.EVENT_ETL_MODEL_EDIT,
            forceExecution: true
        },config);
    },
    /**
     * New job
     */
    newJob: function(){
        var config = {
            ssns: ORYX.CONFIG.NAMESPACE_ETL_JOB
        };
        this.facade.raiseEvent({
            type: ORYX.CONFIG.EVENT_ETL_MODEL_EDIT,
            forceExecution: true
        },config);
    }
});
