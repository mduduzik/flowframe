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

if (!ORYX.Plugins) {
    ORYX.Plugins = new Object();
}

if (!ORYX.Plugins.ETL) {
    ORYX.Plugins.ETL = new Object();
}

if (!ORYX.Plugins.ETL.Metadata) {
    ORYX.Plugins.ETL.Metadata = new Object();
}

/**
 * The ETL PluginManager class.
 * @class ORYX.Plugins.ETL.Metadata.PluginManager
 * @extends Clazz
 */
ORYX.Plugins.ETL.Metadata.PluginManager = {
    construct: function(config) {
        // initialization.
        this.config = config;

        // available plugin configurations
        this.availablePluginConfigurations = [];

        // loaded plugins
        this.loadedPlugins 	= [];
        this.pluginsData 	= [];

        // Initializing of a callback to check loading ends
        var loadPluginConfigurationsFinished 	= false;
        var loadPluginsFinished = false;
        var initFinished = function(){
            if( !loadPluginConfigurationsFinished && !loadPluginsFinished){ return }
            this._finishedLoading();
        }.bind(this)

        // LOAD the plugins
        window.setTimeout(function(){
            this.loadPluginConfigurations();
            loadPluginConfigurationsFinished = true;
            initFinished();
        }.bind(this), 100);


        // LOAD the plugins
        window.setTimeout(function(){
            this.loadPlugins();
            loadPluginsFinished = true;
            initFinished();
        }.bind(this), 100);
    }
    ,_finishedLoading: function() {
    }
    ,_initEventListener: function(){
    }
    /**
     * Third bootstrapping layer. This is where first the plugin coniguration
     * file is loaded into oryx, analyzed, and where all plugins are being
     * requested by the server. Afterwards, all editor instances will be
     * initialized.
     */
    ,loadPluginConfigurations: function() {

        // load plugins if enabled.
        if(ORYX.CONFIG.ETL_PLUGINS_ENABLED)
            this._loadPluginConfigurations();
        else
            ORYX.Log.warn("ETL: Ignoring plugins, loading Core only.");
    },

    _loadPluginConfigurations: function() {

        // load plugin configuration file.
        var source = ORYX.CONFIG.ETL_PLUGINS_CONFIG;

        ORYX.Log.debug("ETL: Loading plugin configuration from '%0'.", source);

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

                ORYX.Log.info("ETL: Plugin configuration file loaded.");

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
                        ORYX.Log.error("ETL: A plugin is not providing a name. Ingnoring this plugin.");
                        return;
                    }

                    // ensure there's a source attribute.
                    if(!pluginData['source']) {
                        ORYX.Log.error("ETL: Plugin with name '%0' doesn't provide a source attribute.", pluginData['name']);
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

                    var url = ORYX.PATH + ORYX.CONFIG.PLUGINS_FOLDER + pluginData['source'];

                    ORYX.Log.debug("ETL: Requireing '%0'", url);

                    // Add the Script-Tag to the Site
                    //Kickstart.require(url);

                    ORYX.Log.info("ETL: Plugin '%0' successfully loaded.", pluginData['name']);

                    // Add the Plugin-Data to all available Plugins
                    this.availablePluginConfigurations.push(pluginData);

                    this.activatePluginByName(pluginData['name'],
                        function(sucess,err){
                            if(!!sucess){
                                ORYX.Log.info("ETL: Metadata Editor Plugin '%0' registered successfully.", pluginData['name']);
                            }else{
                                Ext.Msg.show({
                                    title: 'Error loading ETL Plugin',
                                    msg: 'Loading ETL Plugin['+pluginData['name']+'] Errored:\n'+err,
                                    buttons: Ext.MessageBox.OK
                                });
                            }}.bind(this));

                }.bind(this));

            }.bind(this),
            onFailure:this._loadPluginConfigurationsOnFails
        });

    },

    _loadPluginConfigurationsOnFails: function(result) {

        ORYX.Log.error("ETL: Plugin configuration file not available.");
    }
    ,getAvailablePluginConfigurations: function(){
        var curAvailablePlugins=this.availablePluginConfigurations.clone();
        curAvailablePlugins.each(function(plugin){
            if(this.loadedPlugins.find(function(loadedPlugin){
                return loadedPlugin.type==this.name;
            }.bind(plugin))){
                plugin.engaged="true";
            }
/*            else{
                plugin.engaged="false";
            }*/
        }.bind(this));
        return curAvailablePlugins;
    },

    loadPluginConfigurationScript: function (url, callback){
        if (url) {
            var script = document.createElement("script")
            script.type = "text/javascript";
            if (script.readyState){  //IE
                script.onreadystatechange = function(){
                    if (script.readyState == "loaded" || script.readyState == "complete"){
                        script.onreadystatechange = null;
                        callback();
                    }
                };
            } else {  //Others
                script.onload = function(){
                    callback();
                };
            }
            script.src = url;
            document.getElementsByTagName("head")[0].appendChild(script);
        }
        else  {
            ORYX.Log.warn("ETL: Editor.loadPluginConfigurationScript: URL is NULL..load aborted..");
        }

    },
    //{{
    //
    //
    //  Plugin loading
    //
    //
    //}}

    /**
     * activate Plugin
     *
     * @param {String} name
     * @param {Function} callback
     * 		callback(sucess, [errorCode])
     * 			errorCodes:  NOTFOUND, YETACTIVATED
     */
    activatePluginByName: function(name, callback, loadTry){
        var match=this.getAvailablePluginConfigurations().find(function(value){return value.name==name});
        if(match && (!match.engaged || (match.engaged==='false'))){
            var facade = this.config.eventManager.getEventManagerFacade();
            var newPlugin;
            var me=this;
            ORYX.Log.debug("ETL: Initializing plugin '%0'", match.name);

            var cond1 = !match.requires;
            if (!match.requires ){
                try {

                    var className 	= eval(match.name);
                    var newPlugin = new className(facade, match);
                    newPlugin.type = match.name;

                    // If there is an GUI-Plugin, they get all Plugins-Offer-Meta-Data
                    if (newPlugin.registryChanged)
                        newPlugin.registryChanged(me.pluginsData);

                    // If there have an onSelection-Method it will pushed to the Editor Event-Handler
                    if (newPlugin.onSelectionChanged)
                        me.registerOnEvent(ORYX.CONFIG.EVENT_SELECTION_CHANGED, newPlugin.onSelectionChanged.bind(newPlugin));
                    this.loadedPlugins.push(newPlugin);
                    this.loadedPlugins.each(function(loaded){
                        if(loaded.registryChanged)
                            loaded.registryChanged(this.pluginsData);
                    }.bind(me));
                    callback(true);

                } catch(e) {
                    ORYX.Log.warn("ETL: Plugin %0 is not available", match.name);
                    if(!!loadTry){
                        callback(false,"INITFAILED");
                        return;
                    }
                    //this.loadPluginConfigurationScript("plugins/scripts/"+match.source, this.activatePluginByName.bind(this,match.name,callback,true));
                    var url = ORYX.PATH + ORYX.CONFIG.PLUGINS_FOLDER + match.source;
                    this.loadPluginConfigurationScript(url, this.activatePluginByName.bind(this,match.name,callback,true));
                }

            } else {
                callback(false,"REQUIRESTENCILSET");
                ORYX.Log.info("Plugin need a stencilset which is not loaded'", match.name);
            }


        }else{
            callback(false, match?"NOTFOUND":"YETACTIVATED");
            //TODO error handling
        }
    },

    //{{
    //
    //  ETL Plugin Loading
    //
    //}}
    //{{
    /**
     * Loads plugins from plugin configurations
     */
    //}}
    loadPlugins: function() {

        // if there should be plugins but still are none, try again.
        // TODO this should wait for every plugin respectively.
        /*if (!ORYX.Plugins && ORYX.availablePlugins.length > 0) {
         window.setTimeout(this.loadPluginConfigurations.bind(this), 100);
         return;
         }*/

        var me = this;
        var newPlugins = [];

        this.getAvailablePluginConfigurations().each(function(value) {
            ORYX.Log.debug("ETL: Initializing plugin '%0'", value.name);
            if( !value.requires &&
                //We assume if there is no engaged attribute in an XML
                //node of a plugin the plugin is activated by default.
                //If there is an engaged attribute and it is set to true
                //the plugin will not be loaded
                (!value.engaged || value.engaged=="true" )){

                try {
                    var className 	= eval(value.name);
                    if( className ){
                        var plugin		= new className(me.config.eventManager, value);
                        plugin.type		= value.name;
                        newPlugins.push( plugin );
                        plugin.engaged="true";
                    }
                } catch(e) {
                    ORYX.Log.error(e);
                    ORYX.Log.error("ETL: Plugin %0 is not available", value.name);
                }

            } else {
                ORYX.Log.info("ETL: Plugin need a stencilset which is not loaded'", value.name);
            }

        });

        newPlugins.each(function(value) {
            // If there is an GUI-Plugin, they get all Plugins-Offer-Meta-Data
            if(value.registryChanged)
                value.registryChanged(me.pluginsData);
        });

        this.loadedPlugins = newPlugins;
    },

    /**
     * Returns a per-editor singleton plugin facade.
     * To be used in plugin initialization.
     */
    _getPluginFacade: function() {

        // if there is no pluginfacade already created:
        if(!(this._pluginFacade))
            // create it.
            this._pluginFacade = {
                activatePluginByName:		this.activatePluginByName.bind(this)
            };

        // return it.
        return this._pluginFacade;
    }
};
ORYX.Plugins.ETL.Metadata.PluginManager = Clazz.extend(ORYX.Plugins.ETL.Metadata.PluginManager);