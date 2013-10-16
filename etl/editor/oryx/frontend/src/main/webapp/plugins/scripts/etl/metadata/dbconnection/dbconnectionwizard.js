if(!ORYX.Plugins) {
    ORYX.Plugins = new Object();
}

if(!ORYX.Plugins.ETL) {
    ORYX.Plugins.ETL = new Object();
}

if(!ORYX.Plugins.ETL.Metadata) {
    ORYX.Plugins.ETL.Metadata = new Object();
}

ORYX.Plugins.ETL.Metadata.DBConnectionWizard = {

    facade: undefined,

    selectDBTypeFormPanel : undefined,
    setJDBCSettingsFormPanel : undefined,
    setUsernameAndPasswordFormPanel : undefined,

    construct: function(facade) {
        // Reference to the Editor-Interface
        this.facade = facade;

        //this.facade.registerOnEvent(ORYX.CONFIG.EVENT_SHOW_PROPERTYWINDOW, this.init.bind(this));
        //this.facade.registerOnEvent(ORYX.CONFIG.EVENT_LOADED, this.onSelectDiagram.bind(this));
        this.init();
    },

    init: function(){
        var wizard = new Ext.ux.Wiz({

            title : 'New DB Connection Wizard',

            headerConfig : {
                title : 'New DB Connection Wizard'
            },

            cardPanelConfig : {
                defaults : {
                    baseCls    : 'x-small-editor',
                    bodyStyle  : 'padding:40px 15px 5px 120px;background-color:#F6F6F6;',
                    border     : false
                }
            },

            cards : [

                //Select database name and type
                new Ext.ux.Wiz.Card({
                    title : 'Select database name and type',
                    items : [{
                        border    : false,
                        bodyStyle : 'background:none;',
                        items     : this.selectDBTypeFormPanel
                    }]
                }),

                //Set JDBC settings
                new Ext.ux.Wiz.Card({
                    title        : 'Set JDBC settings',
                    items : [{
                        border    : false,
                        bodyStyle : 'background:none;',
                        items     : this.setJDBCSettingsFormPanel
                    }]
                }),

                //Set the username and password
                new Ext.ux.Wiz.Card({
                    title        : 'Set the username and password',
                    items : [{
                        border    : false,
                        bodyStyle : 'background:none;',
                        items     : this.setUsernameAndPasswordFormPanel
                    }]
                })
            ]
        });

        // show the wizard
        wizard.show();

        region = this.facade.addToRegion("centerSouth", new Ext.Panel({
            autoHeight: true,
            layout: "fit",
            border: false,
            title: 'Properties',
            items: [
                this.grid
            ]
        }), ORYX.I18N.PropertyWindow.title)  ;

        // Register on Events
        this.grid.on('beforeedit', this.beforeEdit, this, true);
        this.grid.on('afteredit', this.afterEdit, this, true);
        this.grid.view.on('refresh', this.hideMoreAttrs, this, true);

        //this.grid.on(ORYX.CONFIG.EVENT_KEYDOWN, this.keyDown, this, true);

        // Renderer the Grid
        this.grid.enableColumnMove = false;
        //this.grid.render();

        // Sort as Default the first column
        //this.dataSource.sort('name');

    }
}
ORYX.Plugins.ETL.Metadata.DBConnectionWizard = Clazz.extend(ORYX.Plugins.ETL.Metadata.DBConnectionWizard);