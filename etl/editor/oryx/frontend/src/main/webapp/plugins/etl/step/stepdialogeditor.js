if (!ORYX.Plugins) {
    ORYX.Plugins = new Object();
}

if (!ORYX.Plugins.ETL) {
    ORYX.Plugins.ETL = new Object();
}

if (!ORYX.Plugins.ETL.Step) {
    ORYX.Plugins.ETL.Step = new Object();
}

ORYX.Plugins.ETL.Step.StepDialogEditor = Clazz.extend({

    facade: undefined,
    
    construct: function(facade){
    
        this.facade = facade;

		this.facade.registerOnEvent(ORYX.CONFIG.EVENT_DBLCLICK, this.actOnDBLClick.bind(this));
        this.facade.offer({
		 keyCodes: [{
				keyCode: 113, // F2-Key
				keyAction: ORYX.CONFIG.KEY_ACTION_DOWN 
			}
		 ],
         functionality: this.renamePerF2.bind(this)
         });
		
		
		//document.documentElement.addEventListener(ORYX.CONFIG.EVENT_MOUSEDOWN, this.hide.bind(this), true );
		
		// Added in 2011 by Matthias Kunze and Tobias Pfeiffer
		// Register on the event for the template plugins (see the method registerTemplate for more information)
		//this.facade.registerOnEvent(ORYX.CONFIG.EVENT_REGISTER_LABEL_TEMPLATE, this.registerTemplate.bind(this));
		// raise the event once so we have the initialized property this.label_templates
/*		this.facade.raiseEvent({
			type: ORYX.CONFIG.EVENT_REGISTER_LABEL_TEMPLATE,
			empty: true // enforces basic template (unity)
		});	*/
    },
    
    /**
     * Handle the registration of a plugin for templatization.
     * This is part of a change made by Matthias Kunze and Tobias Pfeiffer in 2011 
     * The options are 2 functions (edit_template and render_template) that handle the templatization.
     * 
     * edit_template is called with the oldValue of a property to be edited and changes it's appearance a bit
     * so the user sees something slightly different in his editwindow, for details refer to the UMLState plugin.
     * 
     * render_template is called with the result of the editing by the user, the template connected things are removed
     * from it so it gets saved in its pure form. Again for more info please refer to the UMLState plugin.
     * 
     * multiple templating methods are saved and are executed one after another, as one may want to use many of them.
     * 
     * @param options, the options of the template function. It should be the edit_template and the render_template functions.
     */
    registerTemplate: function(options) {

        // initialization
        this.label_templates = this.label_templates || [];
     
        // push the new template onto our list so it gets executed in the next renaming process
        this.label_templates.push({
            edit: "function" == typeof(options.edit_template) ? options.edit_template : function(a){return a;},
            render: "function" == typeof(options.render_template) ? options.render_template : function(a){return a;}
        });
     },

    
	/**
	 * This method handles the "F2" key down event. The selected shape are looked
	 * up and the editing of title/name of it gets started.
	 */
	renamePerF2 : function renamePerF2() {
		var selectedShapes = this.facade.getSelection();
		this.actOnDBLClick(undefined, selectedShapes.first());
	},
	
	getEditableProperties: function getEditableProperties(shape) {
	    // Get all properties which where at least one ref to view is set
		var props = shape.getStencil().properties().findAll(function(item){ 
			return (item.refToView() 
					&&  item.refToView().length > 0
					&&	item.directlyEditable()); 
		});
		
		// from these, get all properties where write access are and the type is String
	    return props.findAll(function(item){ return !item.readonly() &&  item.type() == ORYX.CONFIG.TYPE_STRING });
	},
	
	getPropertyForLabel: function getPropertyForLabel(properties, shape, label) {
	    return properties.find(function(item){ return item.refToView().any(function(toView){ return label.id == shape.id + toView })});
	},
	
	actOnDBLClick: function actOnDBLClick(evt, shape){
		if( !(shape instanceof ORYX.Core.Shape) ){ return }


        var editableLabelProp = this.getLabelProperty(shape);
        var title = editableLabelProp.title();
        var metaPathId = 'metadataObjId';

        var itemType = shape.getStencil().idWithoutNs();



        var eventData = {
            type: ORYX.CONFIG.EVENT_ETL_STEP_EDIT_PREFIX + itemType,
            forceExecution: true
        };


        //This is a global event
        if (this.facade.getEventManager().hasActiveListeners(eventData.type)) {
            this.facade.getEventManager().raiseEvent(eventData, {
                    title: title,
                    metaPathId: metaPathId,
                    shapeObject: shape,
                    shapeObjectLabelProp: editableLabelProp
                }
            );
        }
        else { //Simply edit label
            this.facade.raiseEvent({type:ORYX.CONFIG.EVENT_ETL_STEP_EDIT_LABEL}, shape);
        }
	},
	getLabelProperty: function(shape) {
        var props = this.getEditableProperties(shape);

        // Get all ref ids
        var allRefToViews	= props.collect(function(prop){ return prop.refToView() }).flatten().compact();
        // Get all labels from the shape with the ref ids
        var labels			= shape.getLabels().findAll(function(label){ return allRefToViews.any(function(toView){ return label.id.endsWith(toView) }); })

        // If there are no referenced labels --> return
        if( labels.length == 0 ){ return }

        // Define the nearest label
        var nearestLabel 	= labels.length == 1 ? labels[0] : null;
        if( !nearestLabel ){
            nearestLabel = labels.find(function(label){ return label.node == evt.target || label.node == evt.target.parentNode })
            if( !nearestLabel ){
                var evtCoord 	= this.facade.eventCoordinates(evt);

                var trans		= this.facade.getCanvas().rootNode.lastChild.getScreenCTM();
                evtCoord.x		*= trans.a;
                evtCoord.y		*= trans.d;
                if (!shape instanceof ORYX.Core.Node) {

                    var diff = labels.collect(function(label){

                        var center 	= this.getCenterPosition( label.node );
                        var len 	= Math.sqrt( Math.pow(center.x - evtCoord.x, 2) + Math.pow(center.y - evtCoord.y, 2));
                        return {diff: len, label: label}
                    }.bind(this));

                    diff.sort(function(a, b){ return a.diff > b.diff })

                    nearestLabel = 	diff[0].label;
                } else {

                    var diff = labels.collect(function(label){

                        var center 	= this.getDifferenceCenterForNode( label.node );
                        var len 	= Math.sqrt( Math.pow(center.x - evtCoord.x, 2) + Math.pow(center.y - evtCoord.y, 2));
                        return {diff: len, label: label}
                    }.bind(this));

                    diff.sort(function(a, b){ return a.diff > b.diff })

                    nearestLabel = 	diff[0].label;
                }
            }
        }

        // Get the particular property for the label
        var prop = this.getPropertyForLabel(props, shape, nearestLabel);
        return prop;
    }
});
