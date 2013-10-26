/**

 * Copyright (c) 2006
 * Martin Czuchra, Nicolas Peters, Daniel Polak, Willi Tscheschner
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 **/

var idCounter = 1;

var ID_PREFIX = "resource";

function printf() {
	
	var result = arguments[0];
	for (var i=1; i<arguments.length; i++)
		result = result.replace('%' + (i-1), arguments[i]);
	return result;
}

function uiId() {
    //var id = Ext.id();
    //id = parseInt(id.replace("ext-gen", ""));
    var id = idCounter;
    idCounter++;
    return id;
}

function removeArrayItem(arr, val) {
    for (var i = 0; i < arr.length; i++) if (arr[i] === val) arr.splice(i, 1);
    return arr;
}

// oryx constants.
var ORYX_LOGLEVEL_TRACE = 5;
var ORYX_LOGLEVEL_DEBUG = 4;
var ORYX_LOGLEVEL_INFO = 3;
var ORYX_LOGLEVEL_WARN = 2;
var ORYX_LOGLEVEL_ERROR = 1;
var ORYX_LOGLEVEL_FATAL = 0;
var ORYX_LOGLEVEL = 4;
var ORYX_CONFIGURATION_DELAY = 100;
var ORYX_CONFIGURATION_WAIT_ATTEMPTS = 10;
var DEFAULT_EDITORS = new Hash();

if(!ORYX) var ORYX = {};

ORYX = Object.extend(ORYX, {
    //Toolbar Button Plugins
   pluginsData: [],
   plugs:	[],
   buttons: [],

    //Event routing
    DOMEventListeners: new Hash(),

    Editors: new Hash(),
    CurrentEditor: undefined,

	//set the path in the config.js file!!!!
	PATH: ORYX.CONFIG.ROOT_PATH,
	//CONFIGURATION: "config.js",

	URLS: [
	
		/*
		 * No longer needed, since compiled into one source file that
		 * contains all of this files concatenated in the exact order
		 * as defined in build.xml.
		 */
		
/*
		"scripts/Core/SVG/editpathhandler.js",
		"scripts/Core/SVG/minmaxpathhandler.js",
		"scripts/Core/SVG/pointspathhandler.js",
		"scripts/Core/SVG/svgmarker.js",
		"scripts/Core/SVG/svgshape.js",
		"scripts/Core/SVG/label.js",
		"scripts/Core/Math/math.js",		
		"scripts/Core/StencilSet/stencil.js",
		"scripts/Core/StencilSet/property.js",
		"scripts/Core/StencilSet/propertyitem.js",
		"scripts/Core/StencilSet/rules.js",
		"scripts/Core/StencilSet/stencilset.js",
		"scripts/Core/StencilSet/stencilsets.js",
		"scripts/Core/bounds.js",
		"scripts/Core/uiobject.js",
		"scripts/Core/abstractshape.js",
		"scripts/Core/canvas.js",
		"scripts/Core/main.js",
		"scripts/Core/svgDrag.js",
		"scripts/Core/shape.js",
		"scripts/Core/Controls/control.js",
		"scripts/Core/Controls/docker.js",
		"scripts/Core/Controls/magnet.js",		
		"scripts/Core/node.js",
		"scripts/Core/edge.js"
*/	],

	alreadyLoaded: [],

	configrationRetries: 0,

	Version: '0.0.1',

	availablePlugins: [],

	/**
	 * The ORYX.Log logger.
	 */
	Log: {
		
		__appenders: [
			{
				// Firebug console log appender, 
				// will only react if window.console is present
				append: function(level, args) {
					
					//			messageParts[0] = (new Date()).getTime() + " "
					//				+ prefix + " " + messageParts[0];
					//			var message = printf.apply(null, args);
					
					if (window.console) {
						switch(level) {
							case 'TRACE': 
	                            args.unshift("[TRACE|" + (new Date()).getTime()+"]"); 
								// missing break is intentional
							case 'DEBUG':
								window.console.debug.apply(window.console, args); 
								break;
							case 'INFO':
								window.console.info.apply(window.console, args); 
							break;
							case 'WARN':
								window.console.warn.apply(window.console, args); 
								break;
							case 'FATAL':
								args.unshift("[FATAL]"); 
								// missing break is intentional
							case 'ERROR':
								window.console.error.apply(window.console, args); 
								break;
							default:
								args.unshift("["+level.toUpperCase()+"]");
								window.console.log.apply(window.console, args); 
						}	
					}
				}
			}
		],
	
		trace: function() {	if(ORYX_LOGLEVEL >= ORYX_LOGLEVEL_TRACE)
			ORYX.Log.__log('TRACE', arguments); },
		debug: function() { if(ORYX_LOGLEVEL >= ORYX_LOGLEVEL_DEBUG)
			ORYX.Log.__log('DEBUG', arguments); },
		info: function() { if(ORYX_LOGLEVEL >= ORYX_LOGLEVEL_INFO)
			ORYX.Log.__log('INFO', arguments); },
		warn: function() { if(ORYX_LOGLEVEL >= ORYX_LOGLEVEL_WARN)
			ORYX.Log.__log('WARN', arguments); },
		error: function() { if(ORYX_LOGLEVEL >= ORYX_LOGLEVEL_ERROR)
			ORYX.Log.__log('ERROR', arguments); },
		fatal: function() { if(ORYX_LOGLEVEL >= ORYX_LOGLEVEL_FATAL)
			ORYX.Log.__log('FATAL', arguments); },
		
		__log: function(prefix, args) {			
			ORYX.Log.__appenders.each(function(appender) {
				appender.append(prefix, $A(args));
			});
		},
		
		addAppender: function(appender) {
			ORYX.Log.__appenders.push(appender);
		}
	},

	/**
	 * First bootstrapping layer. The Oryx loading procedure begins. In this
	 * step, all preliminaries that are not in the responsibility of Oryx to be
	 * met have to be checked here, such as the existance of the prototpe
	 * library in the current execution environment. After that, the second
	 * bootstrapping layer is being invoked. Failing to ensure that any
	 * preliminary condition is not met has to fail with an error.
	 */
	load: function() {
		var waitingpanel = new Ext.Window({renderTo:Ext.getBody(),id:'oryx-loading-panel',bodyStyle:'padding: 8px;background:white',title:ORYX.I18N.Oryx.title,width:'auto',height:'auto',modal:true,resizable:false,closable:false,html:'<span style="font-size:11px;">' + ORYX.I18N.Oryx.pleaseWait + '</span>'})
		waitingpanel.show()
				
		ORYX.Log.debug("FF DI begins loading procedure.");
		
		// check for prototype
		if( (typeof Prototype=='undefined') ||
			(typeof Element == 'undefined') ||
			(typeof Element.Methods=='undefined') ||
			parseFloat(Prototype.Version.split(".")[0] + "." +
				Prototype.Version.split(".")[1]) < 1.5)

			throw("Application requires the Prototype JavaScript framework >= 1.5.3");
		
		ORYX.Log.debug("Prototype > 1.5 found.");

		// continue loading.
		ORYX._load();
	},

	/**
	 * Second bootstrapping layer. The oryx configuration is checked. When not
	 * yet loaded, config.js is being requested from the server. A repeated
	 * error in retrieving the configuration will result in an error to be
	 * thrown after a certain time of retries. Once the configuration is there,
	 * all urls that are registered with oryx loading are being requested from
	 * the server. Once everything is loaded, the third layer is being invoked.
	 */
	_load: function() {
        this.loadAppFinished = false;
        var initFinished = function(){
            if( !this.loadAppFinished ){ return }
            this._finishedLoading();
        }.bind(this);

        window.setTimeout(function(){
            ORYX.loadPlugins();
            init();
            ORYX.generateMainUI();
            this.loadAppFinished = true;
            initFinished();
        }.bind(this), 100);
	},

	/**
	 * Third bootstrapping layer. This is where first the plugin coniguration
	 * file is loaded into oryx, analyzed, and where all plugins are being
	 * requested by the server. Afterwards, all editor instances will be
	 * initialized.
	 */
	loadPlugins: function() {
		
		// load plugins if enabled.
		if(ORYX.CONFIG.PLUGINS_ENABLED)
			ORYX._loadPlugins()
		else
			ORYX.Log.warn("Ignoring plugins, loading Core only.");

		// init the editor instances.
		//init();
        //this.generateMainUI();
	},
	
	_loadPlugins: function() {

		// load plugin configuration file.
		var source = ORYX.CONFIG.PLUGINS_CONFIG;

		ORYX.Log.debug("Loading plugin configuration from '%0'.", source);
	
		new Ajax.Request(source, {
			asynchronous: false,
			method: 'get',
			onSuccess: function(result) {

				/*
				 * This is the method that is being called when the plugin
				 * configuration was successfully loaded from the server. The
				 * file has to be processed and the contents need to be
				 * considered for further plugin requireation.
				 */
				
				ORYX.Log.info("Plugin configuration file loaded.");
		
				// get plugins.xml content
				var resultXml = result.responseXML;
				
				// TODO: Describe how properties are handled.
				// Get the globale Properties
				var globalProperties = [];
				var preferences = $A(resultXml.getElementsByTagName("properties"));
				preferences.each( function(p) {

					var props = $A(p.childNodes);
					props.each( function(prop) {
						var property = new Hash(); 
						
						// get all attributes from the node and set to global properties
						var attributes = $A(prop.attributes)
						attributes.each(function(attr){property[attr.nodeName] = attr.nodeValue});				
						if(attributes.length > 0) { globalProperties.push(property) };				
					});
				});

				
				// TODO Why are we using XML if we don't respect structure anyway?
				// for each plugin element in the configuration..
				var plugin = resultXml.getElementsByTagName("plugin");
				$A(plugin).each( function(node) {
					
					// get all element's attributes.
					// TODO: What about: var pluginData = $H(node.attributes) !?
					var pluginData = new Hash();
					$A(node.attributes).each( function(attr){
						pluginData[attr.nodeName] = attr.nodeValue});				
					
					// ensure there's a name attribute.
					if(!pluginData['name']) {
						ORYX.Log.error("A plugin is not providing a name. Ingnoring this plugin.");
						return;
					}

					// ensure there's a source attribute.
					if(!pluginData['source']) {
						ORYX.Log.error("Plugin with name '%0' doesn't provide a source attribute.", pluginData['name']);
						return;
					}
					
					// Get all private Properties
					var propertyNodes = node.getElementsByTagName("property");
					var properties = [];
					$A(propertyNodes).each(function(prop) {
						var property = new Hash(); 
						
						// Get all Attributes from the Node			
						var attributes = $A(prop.attributes)
						attributes.each(function(attr){property[attr.nodeName] = attr.nodeValue});				
						if(attributes.length > 0) { properties.push(property) };	
					
					});
					
					// Set all Global-Properties to the Properties
					properties = properties.concat(globalProperties);
					
					// Set Properties to Plugin-Data
					pluginData['properties'] = properties;
					
					// Get the RequieredNodes
					var requireNodes = node.getElementsByTagName("requires");
					var requires;
					$A(requireNodes).each(function(req) {			
						var namespace = $A(req.attributes).find(function(attr){ return attr.name == "namespace"})
						if( namespace && namespace.nodeValue ){
							if( !requires ){
								requires = {namespaces:[]}
							}
						
							requires.namespaces.push(namespace.nodeValue)
						} 
					});					
					
					// Set Requires to the Plugin-Data, if there is one
					if( requires ){
						pluginData['requires'] = requires;
					}


					// Get the RequieredNodes
					var notUsesInNodes = node.getElementsByTagName("notUsesIn");
					var notUsesIn;
					$A(notUsesInNodes).each(function(not) {			
						var namespace = $A(not.attributes).find(function(attr){ return attr.name == "namespace"})
						if( namespace && namespace.nodeValue ){
							if( !notUsesIn ){
								notUsesIn = {namespaces:[]}
							}
						
							notUsesIn.namespaces.push(namespace.nodeValue)
						} 
					});					
					
					// Set Requires to the Plugin-Data, if there is one
					if( notUsesIn ){
						pluginData['notUsesIn'] = notUsesIn;
					}		
					
								
					var url = ORYX.PATH + ORYX.CONFIG.PLUGINS_FOLDER + pluginData['source'];
		
					ORYX.Log.debug("Requireing '%0'", url);
		
					// Add the Script-Tag to the Site
					//Kickstart.require(url);
		
					ORYX.Log.info("Plugin '%0' successfully loaded.", pluginData['name']);
		
					// Add the Plugin-Data to all available Plugins
					ORYX.availablePlugins.push(pluginData);
		
				});
		
			},
			onFailure:this._loadPluginsOnFails
		});

	},

	_loadPluginsOnFails: function(result) {

		ORYX.Log.error("Plugin configuration file not available.");
	},
    //{{
    //
    //  Default Editors Config & Launcher
    //
    //}}
    launchEditor: function(config) {
        if(Ext.getCmp('oryx-loading-panel')){
            Ext.getCmp('oryx-loading-panel').show();
        }

        //Hack: not allowing multiple editors
        if (this.CurrentEditor) { //Close it
            Ext.MessageBox.show({
                title:'Proceed?',
                msg: 'There might be unsaved changes in '+this.CurrentEditor.title+'. <br />Click Yes to Proceed or No to Save first.?',
                buttons: Ext.MessageBox.YESNO,
                fn: function(btn){
                    if (btn === 'no'){
                        //this.newWizDialog.close();
                    }
                    else {
                        this.mainEditorsPanel.remove(this.CurrentEditor);
                        this._createEditor(config);
                    }
                }.bind(this),
                icon: Ext.MessageBox.QUESTION
            });
        }
        else {
            this._createEditor(config);
        }
    },
    _createEditor: function(config) {
        var ssNameSpace = config.ssns;
        var ssConfig = DEFAULT_EDITORS[ssNameSpace];

        var ssConfig_ = {type:config.type};
        Ext.apply(ssConfig_,ssConfig);

        if (config.title)
            ssConfig_.title = config.title;
        else
            ssConfig_.title = ssConfig.title+' #'+uiId();

        //- Create editor
        var editor = new ORYX.Editor(ssConfig_);
        this.Editors[editor.layout] = editor;

        //- Add to layout
        this.addToRegion('center', editor.layout, ssConfig_.title);

        //- Load model  if editing
        if (config.type === ORYX.CONFIG.EVENT_ETL_MODEL_EDIT) {
            var jsonObject = Ext.decode(config.jsonModel);
            editor._pluginFacade.importJSON(jsonObject,true);
        }
    },
    /**
     * Create configs
     * @private
     */
    _initDefaultEditorsConfig: function() {

       DEFAULT_EDITORS[ORYX.CONFIG.NAMESPACE_ETL_TRANS] = {
               id: 'etlTransCanvas',
               saveNewModelUrl: '/etl/core/transmeta/add',
               updateModelUrl: '/etl/core/transmeta/update',
               removeModelUrl: '/etl/core/transmeta/remove',
               title: 'New Transformation',
               iconCls: 'transformation-icon',
               stencilset: {

                   url: ORYX.PATH + '/stencilsets/etl/etl1.0/etl1.0.json',
                   ns: ORYX.CONFIG.NAMESPACE_ETL_TRANS
               }
           };

       DEFAULT_EDITORS[ORYX.CONFIG.NAMESPACE_ETL_JOB] = {
               id: 'etlJobCanvas',
               saveNewUrl: '/etl/core/jobmeta/add',
               updateModelUrl: '/etl/core/jobmeta/update',
               removeModelUrl: '/etl/core/jobmeta/remove',
               title: 'New Job ',
               iconCls: 'process-icon',
               stencilset: {

                   url: ORYX.PATH + '/stencilsets/etl/etljob1.0/etljob1.0.json',
                   ns: ORYX.CONFIG.NAMESPACE_ETL_JOB
               }
           };
    },
    //{{
    //
    // Event queue's and managers
    //
    //}
    /**
     *  Methods for the PluginFacade
     */
    registerOnEvent: function(eventType, callback) {
        if(!(this.DOMEventListeners.keys().member(eventType))) {
            this.DOMEventListeners[eventType] = [];
        }

        this.DOMEventListeners[eventType].push(callback);
    },

    unregisterOnEvent: function(eventType, callback) {
        if(this.DOMEventListeners.keys().member(eventType)) {
            this.DOMEventListeners[eventType] = this.DOMEventListeners[eventType].without(callback);
        } else {
            // Event is not supported
            // TODO: Error Handling
        }
    },
    handleEvents: function(event, uiObj) {
        this.CurrentEditor.getCanvasFacade().raiseEvent(event, uiObj);
    },
    //{{
    //
    //
    //  Global App Listener callbacks
    //
    //}}
    /**
     *
     * @param component
     * @param state
     */
    onBeforeTabChange: function(component,state) {
        if (!state.ns)//Not SS editor
            return;

        if (!this.CurrentEditor) {//First current editor
            this.CurrentEditor = state;
            //-- Copy listeners over
            this.DOMEventListeners.keys().each((function(eventType) {
                this.CurrentEditor.getCanvasFacade().registerOnEvent(eventType, this.DOMEventListeners[eventType]);
            }).bind(this));

            return;
        }

        if (state.id === this.CurrentEditor.id)//No need to change SS repo
            return;

        //-- Unregister current app listeners
        this.DOMEventListeners.keys().each((function(eventType) {
            //this.CurrentEditor.getCanvasFacade().deactivateEventListeners();
            this.CurrentEditor.getCanvasFacade().unregisterOnEvent(eventType, this.DOMEventListeners[eventType]);
        }).bind(this));

        //- Assign new editor
        this.CurrentEditor = state;

        //-- Register current app listeners
        this.DOMEventListeners.keys().each((function(eventType) {
            //this.CurrentEditor.getCanvasFacade().reactivateEventListeners();
            this.CurrentEditor.getCanvasFacade().registerOnEvent(eventType, this.DOMEventListeners[eventType]);
        }).bind(this));
    },
    onBeforeTabClose: function(editorPanel) {
        var editor = this.Editors[editorPanel];
        if (editor) {
            var facade = editor._pluginFacade;
            if (editor) {
                facade.deactivateEventListeners();
                this.Editors.remove(editorPanel);
                //Ext.destroy(editorPanel);
                Ext.destroy(editor);
            }
            this.CurrentEditor = null;
        }
        else {
            Ext.destroy(editorPanel);
        }
    },
    //{{
    //
    //  Create Main UI
    //
    //}}
    // Main UI
    generateMainUI: function() {
        this._initDefaultEditorsConfig();
        this._generateDefaultUIPanels();
        this._generateLayout();
        this._generateRepositoryUI();
        this._generateToolbarUI();
    },
    // Toolbar
    _generateToolbarUI: function() {
        var groups = new Hash();
        groups['New'] = '1';
        groups['Edit'] = '2';
        groups['Undo'] = '3';
        groups['Help'] = 'ZZZZZZZ';

        //Default Button UI configs
        this.newTransButtonConfig = {
            'name': 'NewTransformation',
            'functionality': this.newTransformation.bind(this,false),
            'group': ORYX.I18N.Save.group,
            'icon': "/etl/images/conxbi/etl/transformation.png",
            'description': 'New Transformation',
            'index': 1,
            'minShape': 0,
            'maxShape': 0
        };
        this.pluginsData.push(this.newTransButtonConfig);

        this.newJobButtonConfig = {
            'name': 'NewJob',
            'functionality': this.newJob.bind(this,true),
            'group': ORYX.I18N.Save.group,
            'icon': "/etl/images/conxbi/etl/process_icon.gif",
            'description': 'New ETL Job',
            'index': 2,
            'minShape': 0,
            'maxShape': 0
        };
        this.pluginsData.push(this.newJobButtonConfig);

        //Group buttons
        this.groupIndex = new Hash();
        groups.each((function(value){
            if(value.group && value.index != undefined) {
                this.groupIndex[value.group] = value.index
            }
        }).bind(this));

        //Toolbar UI
		ORYX.Log.trace("Creating a toolbar.")
        if(!this.toolbar){
            this.toolbar = new Ext.ux.SlicedToolbar({
                height: 24
            });
            var region = this.addToRegion("north", this.toolbar, "Toolbar");
        }

        //Populate toolbar
        var newPlugs =  this.pluginsData.sortBy((function(value) {
            return ((groups[value.group] != undefined ? groups[value.group] : "" ) + value.group + "" + value.index).toLowerCase();
        }).bind(this));
        var plugs = $A(newPlugs).findAll(function(value){
            return !this.plugs.include( value )
        }.bind(this));
        if(plugs.length<1)
            return;

        this.buttons = [];
        var currentGroupsName = this.plugs.last()?this.plugs.last().group:plugs[0].group;

        // Map used to store all drop down buttons of current group
        var currentGroupsDropDownButton = {};


        plugs.each((function(value) {
            if(!value.name) {return}
            this.plugs.push(value);
            // Add seperator if new group begins
            if(currentGroupsName != value.group) {
                this.toolbar.add('-');
                currentGroupsName = value.group;
                currentGroupsDropDownButton = {};
            }
            //add eventtracking
            var tmp = value.functionality;
            value.functionality = function(){
                if ("undefined" != typeof(pageTracker) && "function" == typeof(pageTracker._trackEvent) )
                {
                    pageTracker._trackEvent("ToolbarButton",value.name)
                }
                return tmp.apply(this, arguments);

            }
            // If an drop down group icon is provided, a split button should be used
            if(value.dropDownGroupIcon){
                var splitButton = currentGroupsDropDownButton[value.dropDownGroupIcon];

                // Create a new split button if this is the first plugin using it
                if(splitButton === undefined){
                    splitButton = currentGroupsDropDownButton[value.dropDownGroupIcon] = new Ext.Toolbar.SplitButton({
                        cls: "x-btn-icon", //show icon only
                        icon: value.dropDownGroupIcon,
                        menu: new Ext.menu.Menu({
                            items: [] // items are added later on
                        }),
                        listeners: {
                            click: function(button, event){
                                // The "normal" button should behave like the arrow button
                                if(!button.menu.isVisible() && !button.ignoreNextClick){
                                    button.showMenu();
                                } else {
                                    button.hideMenu();
                                }
                            }
                        }
                    });

                    this.toolbar.add(splitButton);
                }

                // General config button which will be used either to create a normal button
                // or a check button (if toggling is enabled)
                var buttonCfg = {
                    icon: value.icon,
                    text: value.name,
                    itemId: value.id,
                    handler: value.toggle ? undefined : value.functionality,
                    checkHandler: value.toggle ? value.functionality : undefined,
                    listeners: {
                        render: function(item){
                            // After rendering, a tool tip should be added to component
                            if (value.description) {
                                new Ext.ToolTip({
                                    target: item.getEl(),
                                    title: value.description
                                })
                            }
                        }
                    }
                }

                // Create buttons depending on toggle
                if(value.toggle) {
                    var button = new Ext.menu.CheckItem(buttonCfg);
                } else {
                    var button = new Ext.menu.Item(buttonCfg);
                }

                splitButton.menu.add(button);

            } else { // create normal, simple button
                var button = new Ext.Toolbar.Button({
                    icon:           value.icon,         // icons can also be specified inline
                    cls:            'x-btn-icon',       // Class who shows only the icon
                    itemId:         value.id,
                    tooltip:        value.description,  // Set the tooltip
                    tooltipType:    'title',            // Tooltip will be shown as in the html-title attribute
                    handler:        value.toggle ? null : value.functionality,  // Handler for mouse click
                    enableToggle:   value.toggle, // Option for enabling toggling
                    toggleHandler:  value.toggle ? value.functionality : null // Handler for toggle (Parameters: button, active)
                });

                this.toolbar.add(button);

                button.getEl().onclick = function() {this.blur()}
            }

            value['buttonInstance'] = button;
            this.buttons.push(value);

        }).bind(this));

        this.enableButtons([]);
        this.toolbar.calcSlices();
        window.addEventListener("resize", function(event){this.toolbar.calcSlices()}.bind(this), false);
        window.addEventListener("onresize", function(event){this.toolbar.calcSlices()}.bind(this), false);
    },
    //{{
    //  Transformations
    //}}
    /**
     * New transformation
     */
    newTransformation: function(){
        var config = {
            ssns: ORYX.CONFIG.NAMESPACE_ETL_TRANS,
            type: ORYX.CONFIG.EVENT_ETL_MODEL_CREATE
        };
        this.launchEditor(config);
    },
    /**
     * Edit transformation
     */
    editTransformation: function(title,repoPathId,repoParentDirPathId,jsonModel){
        var config = {
            ssns: ORYX.CONFIG.NAMESPACE_ETL_TRANS,
            type: ORYX.CONFIG.EVENT_ETL_MODEL_EDIT,
            title: title,
            repoPathId: repoPathId,
            repoParentDirPathId: repoParentDirPathId,
            jsonModel: jsonModel
        };
        this.launchEditor(config);
    },
    /**
     * New job
     */
    newJob: function(){
        var config = {
            ssns: ORYX.CONFIG.NAMESPACE_ETL_JOB,
            type: ORYX.CONFIG.EVENT_ETL_MODEL_CREATE
        };
        this.launchEditor(config);
/*        this.facade.raiseEvent({
            type: ORYX.CONFIG.EVENT_ETL_MODEL_EDIT,
            forceExecution: true
        },config);*/
    },
    enableButtons: function(elements) {
        // Show the Buttons
        this.buttons.each((function(value){
            value.buttonInstance.enable();

            // If there is less elements than minShapes
            if(value.minShape && value.minShape > elements.length)
                value.buttonInstance.disable();
            // If there is more elements than minShapes
            if(value.maxShape && value.maxShape < elements.length)
                value.buttonInstance.disable();
            // If the plugin is not enabled
            if(value.isEnabled && !value.isEnabled())
                value.buttonInstance.disable();

        }).bind(this));
    },
    // Explorer Navigation
    _generateRepositoryUI: function() {
        this.etlRepoPanel = new ORYX.ETL.ETLRepoNavigation(this);
        this.etlRepoPanel = new ORYX.ETL.DOCRepoNavigation(this);
    },
    // Default UI Panels
    _generateDefaultUIPanels: function() {
        // Workspace/Welcome
        var tools = [{
            handler: function(){
                Ext.Msg.alert('Message', 'The Settings tool was clicked.');
            }
        },{
            handler: function(e, target, panel){
                panel.ownerCt.remove(panel, true);
            }
        }];
        this.workspaceTab = new Ext.Panel({
            title: 'Welcome',
            iconCls: 'process-icon',
            closable:true,
            labelAlign: 'top',
            bodyStyle:'padding:0px',
            layout: 'fit',
            dropAllowed: false,
            items: [
                {
                    xtype:'portal',
                    defaults: {
                        margin:5
                    },
                    region:'center',
                    margins:'35 5 5 0',
                    items:[{
                        columnWidth:.5,
                        style:'padding:10px 10px 10px 10px',
                        items:[{
                            title: 'Getting Started',
                            tools: tools,
                            html: 'Getting started'
                        },{
                            title: 'Samples',
                            tools: tools,
                            html: 'Samples and Tutorials'
                        }]
                    },{
                        columnWidth:.5,
                        style:'padding:10px 10px 10px 10px',
                        items:[{
                            title: 'What\'s new',
                            tools: tools,
                            html: 'What\'s new'
                        },{
                            title: 'Documentation',
                            tools: tools,
                            html: 'Documentation'
                        }]
                    }]

                    /*
                     * Uncomment this block to test handling of the drop event. You could use this
                     * to save portlet position state for example. The event arg e is the custom
                     * event defined in Ext.ux.Portal.DropZone.
                     */
//            ,listeners: {
//                'drop': function(e){
//                    Ext.Msg.alert('Portlet Dropped', e.panel.title + '<br />Column: ' +
//                        e.columnIndex + '<br />Position: ' + e.position);
//                }
//            }
                }
            ],
            autoScroll: true
        });

        //Main TabPanel
        // Main UI
        this.mainEditorsPanel = new Ext.TabPanel({
            region: 'center',
            minTabWidth: 115,
            tabWidth:135,
            enableTabScroll:true,
            activeTab: 0,
            //plugins: new Ext.ux.TabCloseMenu(),
            items: [
                this.workspaceTab
            ]
        });
        this.mainEditorsPanel.on('beforetabchange',this.onBeforeTabChange.bind(this));
        this.mainEditorsPanel.on('beforeremove',this.onBeforeTabClose.bind(this));
    },
    _generateLayout: function() {
        /**
         * Create Layout Config
         */
        this.layout_regions = {

            // DEFINES MAIN TOP-AREA
            north	: new Ext.Panel({ //TOOO make a composite of the oryx header and addable elements (for toolbar), second one should contain margins
                region	: 'north',
                cls		: 'x-panel-editor-north',
                autoEl	: 'div',
                border	: false
            }),
            // DEFINES LEFT REPOSITORY EXPLORER AREA
            west	: new Ext.Panel({
                region	: 'west',
                layout	: 'fit',
                cls		: 'x-panel-editor-east',
                /*layout: 'accordion',
                 layoutConfig: {
                 // layout-specific configs go here
                 titleCollapse: true,
                 animate: true,
                 activeOnTop: true
                 },*/
                autoEl	: 'div',
                border	:false,
                cmargins: {left:0, right:0},
                collapsible	: true,
                width	: ORYX.CONFIG.PANEL_RIGHT_WIDTH || 200,
                split	: true,
                title	: "Explorer"
            }),
            center	: this.mainEditorsPanel
        }
        //Test333
        //layout_regions.centerNew.setAdapter(new Ext.SplitBar.AbsoluteLayoutAdapter("container"));



        // Hide every region except the center
        for (region in this.layout_regions) {
            if ( region != "center" ) {
                //this.layout_regions[ region ].hide();
            }
        }

        // Config for the Ext.Viewport
        var layout_config = {
            layout: 'border',
            items: [
                this.layout_regions.north,
                this.layout_regions.west,
                this.layout_regions.center
            ]
        }
        /**
         * Create Viewport
         */
        this.layout = new Ext.Viewport( layout_config );
    },
    /**
     * adds a component to the specified region
     *
     * @param {String} region
     * @param {Ext.Component} component
     * @param {String} title, optional
     * @return {Ext.Component} dom reference to the current region or null if specified region is unknown
     */
    addToRegion: function(region, component, title) {
        var region_name =  region.toLowerCase();
        var current_region = this.layout_regions[region.toLowerCase()];

        current_region.add(component).show();

        ORYX.Log.debug("original dimensions of region %0: %1 x %2", current_region.region, current_region.width, current_region.height)

        // update dimensions of region if required.
        if  (!current_region.width && component.initialConfig && component.initialConfig.width) {
            ORYX.Log.debug("resizing width of region %0: %1", current_region.region, component.initialConfig.width)
            current_region.setWidth(component.initialConfig.width)
        }
        if  (component.initialConfig && component.initialConfig.height) {
            ORYX.Log.debug("resizing height of region %0: %1", current_region.region, component.initialConfig.height)
            var current_height = current_region.height || 0;
            current_region.height = component.initialConfig.height + current_height;
            current_region.setHeight(component.initialConfig.height + current_height)
        }


        // set title if provided as parameter.
        if (typeof title == "string") {
            switch(region.toLowerCase()) {
                case "west":
                    /*                        if (current_region.title != "West"){
                     title = current_region.title + " and " + title;
                     current_region.setTitle(title);
                     }
                     current_region.setTitle(title);*/
                    break;
                default :
                    current_region.setTitle(title);
            }
        }


        //This renders the layout
        current_region.ownerCt.doLayout();
        current_region.show();

        if(Ext.isMac)
            ORYX.Editor.resizeFix();

        return current_region;
    },
    _finishedLoading: function() {
        if(Ext.getCmp('oryx-loading-panel')){
            Ext.getCmp('oryx-loading-panel').hide()
        }

        // Raise Loaded Event
        this.registerOnEvent( {type:ORYX.CONFIG.EVENT_LOADED} );

    }
});
ORYX.Log.debug('Registering Oryx with Kickstart');
Kickstart.register(ORYX.load);


/**

 * Main initialization method. To be called when loading

 * of the document, including all scripts, is completed.

 */

function init() {


    Ext.QuickTips.interceptTitles = true;
    Ext.QuickTips.init();

    /* When the blank image url is not set programatically to a local

     * representation, a spacer gif on the site of ext is loaded from the

     * internet. This causes problems when internet or the ext site are not

     * available. */

    Ext.BLANK_IMAGE_URL = ORYX.PATH + 'lib/ext-2.0.2/resources/images/default/s.gif';



    ORYX.Log.debug("Querying editor instances");



    // Hack for WebKit to set the SVGElement-Classes

    ORYX.Editor.setMissingClasses();
}

