if (!ORYX.Plugins) {
    ORYX.Plugins = new Object();
}

if (!ORYX.Plugins.ETL) {
    ORYX.Plugins.ETL = new Object();
}


ORYX.Plugins.ETL.Core = {

    facade: undefined,

    construct: function (facade) {
        // Reference to the Editor-Interface
        this.facade = facade;

        //this.facade.registerOnEvent(ORYX.CONFIG.EVENT_SHOW_PROPERTYWINDOW, this.init.bind(this));
        //this.facade.registerOnEvent(ORYX.CONFIG.EVENT_LOADED, this.selectDiagram.bind(this));
        this.init();
    },

    createCard: function (title, items) {
        var card = new Ext.ux.Wiz.Card({
            title: title,
            monitorValid: true,
            defaults: {
                labelStyle: 'font-size:11px'
            },
            items: items
        });
    }
}
ORYX.Plugins.ETL.Core = Clazz.extend(ORYX.Plugins.ETL.Core);