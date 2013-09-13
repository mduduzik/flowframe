if (!ORYX.Plugins) {
    ORYX.Plugins = new Object();
}

if (!ORYX.Plugins.ETL) {
    ORYX.Plugins.ETL = new Object();
}

if (!ORYX.Plugins.ETL.Metadata) {
    ORYX.Plugins.ETL.Metadata = new Object();
}

ORYX.Plugins.ETL.Metadata.EditorRepository = {

    facade: undefined,
    editorData: undefined,

    construct: function (facade) {
        // Reference to the Editor-Interface
        this.facade = facade;

        //this.facade.registerOnEvent(ORYX.CONFIG.EVENT_SHOW_PROPERTYWINDOW, this.init.bind(this));
        //this.facade.registerOnEvent(ORYX.CONFIG.EVENT_LOADED, this.selectDiagram.bind(this));
        this.loadEditors();
    },

    /**
     * Load editors
     */
    loadEditors: function() {
        // init the editor instances.
        this._loadEditors();
    },

    _loadEditors: function() {

        // load plugin configuration file.
        var source = ORYX.CONFIG.ETL_METADATA_EDITORS_CONFIG;

        ORYX.Log.debug("Loading metadata editor plugin configuration from '%0'.", source);

        var mainFacade = this.facade;

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

                ORYX.Log.info("Metadata editor plugin configuration file loaded.");

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

                    ORYX.Log.debug("Loading Metadata Editor Plugin '%0' from '%1'", url,pluginData['name']);

                    //Make is available so mainFacade.activatePluginByName() won't fail - maybe a hack
                    ORYX.availablePlugins.push(pluginData);
                    mainFacade.activatePluginByName(pluginData['name'],
                        function(sucess,err){
                            if(!!sucess){
                                ORYX.Log.info("ETL Metadata Editor Plugin '%0' registered successfully.", pluginData['name']);
                            }else{
                                Ext.Msg.show({
                                    title: ORYX.I18N.PluginLoad.loadErrorTitle,
                                    msg: ORYX.I18N.PluginLoad.loadErrorDesc + ORYX.I18N.PluginLoad[err],
                                    buttons: Ext.MessageBox.OK
                                });
                            }}.bind(this));

                    // Add the Script-Tag to the Site
                    //Kickstart.require(url);
/*
                    $LAB.script(url).wait(function() {

                    });
*/

                    ORYX.Log.info("Plugin '%0' successfully loaded.", pluginData['name']);


                    // Add the Plugin-Data to all available Plugins
                    //ORYX.availablePlugins.push(pluginData);

                });

            },
            onFailure:this._loadPluginsOnFails
        });

    },

    _loadPluginsOnFails: function(result) {

        ORYX.Log.error("Plugin configuration file not available.");
    }
}
ORYX.Plugins.ETL.Metadata.EditorRepository = Clazz.extend(ORYX.Plugins.ETL.Metadata.EditorRepository);