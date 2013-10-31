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


/**
 @namespace Global Oryx name space
 @name ORYX
 */
if(!ORYX) {var ORYX = {};}

/**
 * The EventManager class.
 * @class ORYX.EventManager
 * @extends Clazz
 */
ORYX.EventManager = {
    /** @lends ORYX.EventManager.prototype */
    // Defines application event listener
    EventListeners: new Hash(),

    construct: function(config) {
        // initialization.
        this._eventsQueue 	= [];
        this.getEventManagerFacade = this._getEventManagerFacade.bind(this);
    }
    //{{
    ,deactivateEventListeners: function(){
        this._eventsQueue.clear();
    }
    /**
     * Returns a per-editor singleton plugin facade.
     * To be used in plugin initialization.
     */
    ,_getEventManagerFacade: function() {

        // if there is no pluginfacade already created:
        if(!(this._eventManagerFacade))

        // create it.
            this._eventManagerFacade = {
                registerOnEvent:		this.registerOnEvent.bind(this),
                unregisterOnEvent:		this.unregisterOnEvent.bind(this),
                raiseEvent:				this.handleEvents.bind(this),
                enableEvent:			this.enableEvent.bind(this),
                disableEvent:			this.disableEvent.bind(this)
            };

        // return it.
        return this._eventManagerFacade;
    },
    //{{
    /**
     *
     * @param configParams
     */
    //}
    onUpdateConfiguration: function(configParams) {
        //-- Title
        if (!(this.config.title === configParams.title))
            this.layout.setTitle(configParams.title);

        //-- Sync rest
        Ext.apply(this.config,configParams);
    },
    /**
     * Implementes the command pattern
     * (The real usage of the command pattern
     * is implemented and shown in the Plugins/undo.js)
     *
     * @param <Oryx.Core.Command>[] Array of commands
     */
    executeCommands: function(commands){

        // Check if the argument is an array and the elements are from command-class
        if ( 	commands instanceof Array 	&&
            commands.length > 0 		&&
            commands.all(function(command){ return command instanceof ORYX.Core.Command }) ) {

            // Raise event for executing commands
            this.handleEvents({
                type		: ORYX.CONFIG.EVENT_EXECUTE_COMMANDS,
                commands	: commands
            });

            // Execute every command
            commands.each(function(command){
                command.execute();
            })

        }
    },

    disableEvent: function(eventType){
        if(this.EventListeners.keys().member(eventType)) {
            var value = this.EventListeners.remove(eventType);
            this.EventListeners['disable_' + eventType] = value;
        }
    },

    enableEvent: function(eventType){
        if(this.EventListeners.keys().member("disable_" + eventType)) {
            var value = this.EventListeners.remove("disable_" + eventType);
            this.EventListeners[eventType] = value;
        }
    },

    /**
     *  Methods for the PluginFacade
     */
    registerOnEvent: function(eventType, callback) {
        if(!(this.EventListeners.keys().member(eventType))) {
            this.EventListeners[eventType] = [];
        }

        this.EventListeners[eventType].push(callback);
    },

    unregisterOnEvent: function(eventType, callback) {
        if(this.EventListeners.keys().member(eventType)) {
            this.EventListeners[eventType] = this.EventListeners[eventType].without(callback);
        } else {
            // Event is not supported
            // TODO: Error Handling
        }
    },
    /* Event-Handler Methods */

    /**
     * Helper method to execute an event immediately. The event is not
     * scheduled in the _eventsQueue. Needed to handle Layout-Callbacks.
     */
    _executeEventImmediately: function(eventObj) {
        if(this.EventListeners.keys().member(eventObj.event.type)) {
            this.EventListeners[eventObj.event.type].each((function(value) {
                var isrray_ = value instanceof Array;
                if (isrray_)
                    value[0](eventObj.event, eventObj.arg);
                else
                    value(eventObj.event, eventObj.arg);
            }).bind(this));
        }
    },

    _executeEvents: function() {
        this._queueRunning = true;
        while(this._eventsQueue.length > 0) {
            var val = this._eventsQueue.shift();
            this._executeEventImmediately(val);
        }
        this._queueRunning = false;
    },

    /**
     * Leitet die Events an die Editor-Spezifischen Event-Methoden weiter
     * @param {Object} event Event , welches gefeuert wurde
     * @param {Object} uiObj Target-UiObj
     */
    handleEvents: function(event, uiObj) {

        ORYX.Log.trace("Dispatching event type %0 on %1", event.type, uiObj);

        /* Force execution if necessary. Used while handle Layout-Callbacks. */
        if(event.forceExecution) {
            this._executeEventImmediately({event: event, arg: uiObj});
        } else {
            this._eventsQueue.push({event: event, arg: uiObj});
        }

        if(!this._queueRunning) {
            this._executeEvents();
        }

        // TODO: Make this return whether no listener returned false.
        // So that, when one considers bubbling undesireable, it won't happen.
        return false;
    }
};
ORYX.EventManager = Clazz.extend(ORYX.EventManager);