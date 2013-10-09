if (!ORYX.Plugins)
    ORYX.Plugins = new Object();


ORYX.Plugins.DOCRepoNavigation = Clazz.extend({

    // Defines the facade
    facade: undefined,

    // Defines the undo/redo Stack
    navigationPanel: undefined,

    // Currently selected context menu node
    ctxNode: undefined,

    modalDialogShown: undefined,

    // Constructor
    construct: function (facade, ownPluginData) {

        this.facade = facade;
        this._currentParent;
        this._canContain = undefined;
        this._canAttach = undefined;

        // Offers the functionality of undo
        /*        this.facade.offer({
         name              : ORYX.I18N.Undo.undo,
         description       : ORYX.I18N.Undo.undoDesc,
         icon              : ORYX.PATH + "images/arrow_undo.png",
         functionality     : this.doUndo.bind(this),
         group             : ORYX.I18N.Undo.group,
         isEnabled         : function(){ return this.undoStack.length > 0 }.bind(this),
         index                        : 0
         });*/

        // Offers the functionality of redo
        /*        this.facade.offer({
         name              : ORYX.I18N.Undo.redo,
         description       : ORYX.I18N.Undo.redoDesc,
         icon              : ORYX.PATH + "images/arrow_redo.png",
         functionality     : this.doRedo.bind(this),
         group             : ORYX.I18N.Undo.group,
         isEnabled         : function(){ return this.redoStack.length > 0 }.bind(this),
         index                       : 1
         });*/

        // Register on event for executing commands-->store all commands in a stack
        /*        this.facade.registerOnEvent(ORYX.CONFIG.EVENT_EXECUTE_COMMANDS,
         this.handleExecuteCommands.bind(this) );*/
        var thisEditor = this;
        if (!this.navigationPanel) {
/*
            var treeLoader = new Ext.tree.TreeLoader({
                requestMethod: 'GET',
                clearOnLoad: false,
                dataUrl: '/etl/core/explorer/getnode',
                preloadChildren: false,
                baseParams: {
                    userid: 'userid',
                    itemtype: 'undefined',
                    folderObjectId: 'undefined'
                },
                listeners: {
                    load: function (loader, node, response) {
                        console.log('data loaded');
                        if (node.isRoot && node.isRoot === true)//1st-level nodes e.g. Metadata etc
                            node.expandChildNodes(false);
                        else if (node.id.indexOf('metadata.') >= 0) {//Metadata folder
                            node.expand();
                        }
                        node.cascade( function() {
                            if (this.attributes['ddenabled'] && this.attributes['ddenabled'] === 'true') {
                                this.render();
                                thisEditor.registerDD(this, this.attributes['itemtype']);
                            }
                        });
                    }
                }
            });
*/


            this.navigationPanel = new Ext.ux.DOCRepoNavigationTreePanel({
                facade: this.facade,
                header: true,
                //region: 'west',
                id: 'docNavigation',
                icon: '/etl/images/conxbi/etl/home_nav.gif',
                url: '/etl/core/docexplorer/getnode',
                uploadUrl: '/etl/core/docexplorer/upload',
                collapsible: true,
                title: 'Files',
                autoWidth: true,
                autoHeight: true,
                autoScroll: true,
                rootVisible: false,
                cmargins: '5 0 0 0',
                padding: '0 0 0 0',
                tbar: [
                    'Search: ', ' ',
                    new Ext.ux.SearchField({
                        width: 'auto'
                    })
                ]
                // default tree elements for the navigation
/*                root: new Ext.tree.AsyncTreeNode({
                    text: '',
                    uiProvider: Ext.tree.customNodeUI
                })*/
            });
            try {
                var region = this.facade.addToRegion("west", this.navigationPanel, "Repository");
            }
            catch (e) {
                ORYX.Log.error(e);
            }

            /**
             * DD
             *
             */
            try {
                var rootNode_ = this.navigationPanel.getRootNode();
                //-- Create a Drag-Zone for Drag'n'Drop
                var DragZone = new Ext.dd.DragZone(rootNode_.getUI().getEl(), {
                    shadow: !Ext.isMac
                });
                DragZone.afterDragDrop = this.drop.bind(this, DragZone);
                DragZone.beforeDragOver = this.beforeDragOver.bind(this, DragZone);
                DragZone.beforeDragEnter = function () {
                    this.modalDialogShown = false;
                    return true
                }.bind(this);
            }
            catch (e) {
                ORYX.Log.error(e);
            }
        }
    },
    registerDD: function (node, type) {
        var ui = node.getUI();

        // Set the tooltip
        ui.elNode.setAttributeNS(null, "title", node.title);

        // Register the Node on Drag and Drop
        Ext.dd.Registry.register(ui.elNode, {
            mainNode: node,
            node: ui.node,
            handles: [ ui.elNode, ui.textNode ].concat($A(ui.elNode.childNodes)), // Set
            // the
            // Handles
            isHandle: false,
            type: type, // Set Type of stencil
            namespace: 'http://etl.flowframe.org/stencilset/etlbasic#'
            // Set Namespace of stencil
        });
    },
    drop: function (dragZone, target, event) {
        var pr = dragZone.getProxy();
        pr.hide();

        //-- Raise DD event
        var eventData = {
            type: ORYX.CONFIG.EVENT_ETL_METADATA_DDROP,
            forceExecution: false //async call
        };
        var sourceData = {
            dragZone: dragZone,
            target: target,
            event: event
        };

        this.modalDialogShown = true;
        this.facade.raiseEvent(eventData, sourceData);
    },
    /**
     * On Drag Over
     *
     * @param dragZone
     * @param target
     * @param event
     * @returns {boolean}
     */
    beforeDragOver: function (dragZone, target, event) {
        if (this.modalDialogShown === true)
            return;
        //target.lock();
        //target.unreg();

        //Disallow drop right away before presenting user with choice
        var pr = dragZone.getProxy();
        pr.setStatus(pr.dropAllowed);
        pr.sync();
        //pr.hide();


        //var ui = node.getUI();

        //this.registerDD(ui,node.title,"database");
        //target.unlock();
        //pr.setStatus(pr.dropAllowed);
        //pr.sync();
        //var option = Ext.dd.Registry.getHandle(target.DDM.currentTarget);
        //this.registerDD(option.mainNode,"database");
        //

        return false;
    },
    handleExecuteCommands: function (evt) {

        //...

    },

    doUndo: function () {

        //...

    },

    doRedo: function () {

        //...
    }

});


Ext.ns("Ext.ux");
Ext.ux.DOCRepoNavigationTreePanel = Ext.extend(Ext.ux.FileTreePanel, {
    facade: undefined,

    initComponent: function () {
        try {
            Ext.apply(this, {
            });
            Ext.ux.DOCRepoNavigationTreePanel.superclass.initComponent.apply(this, arguments);
        }
        catch (e) {
            ORYX.Log.error(e);
        }
    }
});
