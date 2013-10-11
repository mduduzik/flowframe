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
				'functionality': this.save.bind(this,false),
				'group': 'New',
				'icon': "/etl/images/conxbi/etl/transformation.png",
				'description': 'New Transformation',
				'index': 1,
				'minShape': 0,
				'maxShape': 0
			});
		}
		
		this.facade.offer({
			'name': 'NewJob',
			'functionality': this.save.bind(this,true),
			'group': 'New',
			'icon': "/etl/images/conxbi/etl/process_icon.gif",
			'description': 'New ETL Job',
			'index': 2,
			'minShape': 0,
			'maxShape': 0
		});	
		
		window.onbeforeunload = this.onUnLoad.bind(this);
	
		this.changeDifference = 0;
		
		// Register on event for executing commands --> store all commands in a stack		 
		// --> Execute
		this.facade.registerOnEvent(ORYX.CONFIG.EVENT_UNDO_EXECUTE, function(){ this.changeDifference++ }.bind(this) );
		this.facade.registerOnEvent(ORYX.CONFIG.EVENT_EXECUTE_COMMANDS, function(){ this.changeDifference++ }.bind(this) );
		// --> Rollback
		this.facade.registerOnEvent(ORYX.CONFIG.EVENT_UNDO_ROLLBACK, function(){ this.changeDifference-- }.bind(this) );
		
		//TODO very critical for load time performance!!!
		//this.serializedDOM = DataManager.__persistDOM(this.facade);
	},
	
	onUnLoad: function(){

		
		if( this.changeDifference !== 0 ){
		
			return ORYX.I18N.Save.unsavedData;
			
		}			
		
	},
		
	
    saveSynchronously: function(forceNew){
            
		// Reset changes
		this.changeDifference = 0;
		var reqURI ='';
		
		if (this.processURI) {
			reqURI = this.processURI;
		}
		else {
			if(!location.hash.slice(1)){
				reqURI= "/backend/poem/new";
			}
			else{
				reqURI = '/backend/poem/'+(location.hash.slice(1).replace(/^\/?/,"").replace(/\/?$/,""))+"/self";
			}
		}
		// If the  current url is the API-URL, try to find out the needed one.
		/* if( reqURI.endsWith("/api") || reqURI.include("/api?") ){
			// Parse params
			var params = {};
			window.location.search.slice(1).split("&").each(function(param){ params[param.split("=")[0]]=param.split("=")[1]})
			
			// If there is model in param, take this
			if(  params.model ){
				reqURI = window.location.href.split("/api")[0] + params.model + "/self";
			// If not, force to get a new one
			} else {
				forceNew = true;
			}
		}*/
		
		if(forceNew){
			var ss 		= this.facade.getStencilSets();
			var source 	= ss[ss.keys()[0]].source().split('stencilsets')[1];
	
			reqURI = '/backend/poem' + ORYX.CONFIG.ORYX_NEW_URL + "?stencilset=/stencilsets" + source ;		
		}


		// Get the serialized svg image source
        var svgClone 	= this.facade.getCanvas().getSVGRepresentation(true);
        var svgDOM 		= DataManager.serialize(svgClone);
		this.serializedDOM = Ext.encode(this.facade.getJSON());
		
		// Check if this is the NEW URL
		if( reqURI.include( ORYX.CONFIG.ORYX_NEW_URL ) ){
			
			// Get the stencilset
			var ss = this.facade.getStencilSets().values()[0]
		
			// Define Default values
			var defaultData = {title:ORYX.I18N.Save.newProcess, summary:'', type:ss.title(), url: reqURI, namespace: ss.namespace() }
			
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
			callback = function(form){
						
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
				this.sendSaveRequest( reqURI, { data: this.serializedDOM, svg: svgDOM, title: title, summary: summary, type: namespace }, forceNew);
				
			}.bind(this);
			
			// Create a new window				
			win = new Ext.Window({
				id:		'Propertie_Window',
		        width:	'auto',
		        height:	'auto',
		        title:	forceNew ? ORYX.I18N.Save.saveAsTitle : ORYX.I18N.Save.save,
		        modal:	true,
				bodyStyle: 'background:#FFFFFF',
		        html: 	dialog.apply( defaultData ),
				buttons:[{
					text: ORYX.I18N.Save.saveBtn,
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
			
		} else {
			
			// Send the request out
			this.sendSaveRequest( reqURI, { data: this.serializedDOM, svg: svgDOM } );
			
		}
    },
	
	sendSaveRequest: function(url, params, forceNew){

		// Send the request to the server.
		new Ajax.Request(url, {
                method: 'POST',
                asynchronous: false,
                parameters: params,
			onSuccess: (function(transport) {
				var loc = transport.getResponseHeader("location");
				if (loc) {
					this.processURI = loc;
				}
				else {
					this.processURI = url;
				}
				
				var modelUri="/model"+this.processURI.split("model")[1].replace(/self\/?$/i,"");
				location.hash="#"+modelUri;
				
				if( forceNew ){
					var newURLWin = new Ext.Window({
						title:		ORYX.I18N.Save.savedAs, 
						bodyStyle:	"background:white;padding:10px", 
						width:		'auto', 
						height:		'auto',
						html:"<div style='font-weight:bold;margin-bottom:10px'>"+ORYX.I18N.Save.saveAsHint+"</div><span><a href='" + loc +"' target='_blank'>" + loc + "</a></span>",
						buttons:[{text:'Ok',handler:function(){newURLWin.destroy()}}]
					});
					newURLWin.show();
				}
				//raise saved event
				this.facade.raiseEvent({
					type:ORYX.CONFIG.EVENT_MODEL_SAVED
				});
				//show saved status
				this.facade.raiseEvent({
						type:ORYX.CONFIG.EVENT_LOADING_STATUS,
						text:ORYX.I18N.Save.saved
					});
			}).bind(this),
			onFailure: (function(transport) {
				// raise loading disable event.
                this.facade.raiseEvent({
                    type: ORYX.CONFIG.EVENT_LOADING_DISABLE
                });


				Ext.Msg.alert(ORYX.I18N.Oryx.title, ORYX.I18N.Save.failed);
				
				ORYX.Log.warn("Saving failed: " + transport.responseText);
			}).bind(this),
			on403: (function(transport) {
				// raise loading disable event.
                this.facade.raiseEvent({
                    type: ORYX.CONFIG.EVENT_LOADING_DISABLE
                });


				Ext.Msg.alert(ORYX.I18N.Oryx.title, ORYX.I18N.Save.noRights);
				
				ORYX.Log.warn("Saving failed: " + transport.responseText);
			}).bind(this)
		});
				
	},
    
    /**
     * Saves the current process to the server.
     */
    save: function(forceNew, event){
    
        // raise loading enable event
        this.facade.raiseEvent({
            type: ORYX.CONFIG.EVENT_LOADING_ENABLE,
			text: ORYX.I18N.Save.saving
        });
        
        // asynchronously ...
        window.setTimeout((function(){
        
            // ... save synchronously
            this.saveSynchronously(forceNew);
            
        }).bind(this), 10);

        
        return true;
    }	
});
