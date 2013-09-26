Ext.state.Manager.setProvider(new Ext.state.CookieProvider);

Ext.onReady(function () {
    var csvWiz = new Ext.ux.etl.metadata.CsvMetadataWiz({mode:'CREATE'});

    var main = new Ext.Panel({
        region: 'center',
        title: 'CSVInput Metadata',
        labelAlign: 'top',
        bodyStyle: 'padding:0px',
        layout: 'fit',
        items: [
            csvWiz
        ],
        autoScroll: true
    });

    var viewport = new Ext.Viewport({
        layout: 'fit',
        items: [
            csvWiz
        ]
        ,
        renderTo: Ext.getBody()
    });
});
