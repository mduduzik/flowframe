if(!ORYX.Plugins) {
    ORYX.Plugins = new Object();
}

if(!ORYX.Plugins.ETL) {
    ORYX.Plugins.ETL = new Object();

if(!ORYX.Plugins.ETL.Metadata) {
    ORYX.Plugins.ETL.Metadata = new Object();
}

ORYX.Plugins.ETL.Metadata.DBConnectionWizard = {

    facade: undefined,

    construct: function(facade) {
        // Reference to the Editor-Interface
        this.facade = facade;

        //this.facade.registerOnEvent(ORYX.CONFIG.EVENT_SHOW_PROPERTYWINDOW, this.init.bind(this));
        //this.facade.registerOnEvent(ORYX.CONFIG.EVENT_LOADED, this.selectDiagram.bind(this));
        this.init();
    },

    init: function(){
        var wizard = new Ext.ux.Wiz({

            title : 'A simple example for a wizard',

            headerConfig : {
                title : 'Simple Wizard Example'
            },

            cardPanelConfig : {
                defaults : {
                    baseCls    : 'x-small-editor',
                    bodyStyle  : 'padding:40px 15px 5px 120px;background-color:#F6F6F6;',
                    border     : false
                }
            },

            cards : [

                // first card with welcome message
                new Ext.ux.Wiz.Card({
                    title : 'Welcome',
                    items : [{
                        border    : false,
                        bodyStyle : 'background:none;',
                        html      : 'Welcome to the example for <strong>Ext.ux.Wiz</string>, '+
                            'a Ext JS user extension for creating wizards.<br/><br/>'+
                            'Please click the "next"-button and fill out all form values.'
                    }]
                }),

                // second card with input fields last/firstname
                new Ext.ux.Wiz.Card({
                    title        : 'Your name',
                    monitorValid : true,
                    defaults     : {
                        labelStyle : 'font-size:11px'
                    },
                    items : [{
                        border    : false,
                        bodyStyle : 'background:none;padding-bottom:30px;',
                        html      : 'Please enter your first- and your lastname. Only letters, underscores and hyphens are allowed.'
                    },
                        new Ext.form.TextField({
                            name       : 'firstname',
                            fieldLabel : 'Firstname',
                            allowBlank : false,
                            validator  : function(v){
                                var t = /^[a-zA-Z_\- ]+$/;
                                return t.test(v);
                            }
                        }),
                        new Ext.form.TextField({
                            name       : 'lastname',
                            fieldLabel : 'Lastname',
                            allowBlank : false,
                            validator  : function(v){
                                var t = /^[a-zA-Z_\- ]+$/
                                return t.test(v);
                            }
                        })

                    ]
                }),

                // third card with input field email-address
                new Ext.ux.Wiz.Card({
                    title        : 'Your email-address',
                    monitorValid : true,
                    defaults     : {
                        labelStyle : 'font-size:11px'
                    },
                    items : [{
                        border    : false,
                        bodyStyle : 'background:none;padding-bottom:30px;',
                        html      : ' Please enter your email-address.'
                    },
                        new Ext.form.TextField({
                            name       : 'email',
                            fieldLabel : 'Email-Address',
                            allowBlank : false,
                            vtype      : 'email'
                        })
                    ]
                }),

                // fourth card with finish-message
                new Ext.ux.Wiz.Card({
                    title        : 'Finished!',
                    monitorValid : true,
                    items : [{
                        border    : false,
                        bodyStyle : 'background:none;',
                        html      : 'Thank you for testing this wizard. Your data has been collected '+
                            'and can be accessed via a call to <pre><code>this.getWizardData</code></pre>'+
                            'When you click on the "finish"-button, the "finish"-event will be fired.<br/>'+
                            'If no attached listener for this event returns "false", this dialog will be '+
                            'closed. <br />(In this case, our listener will return false after a popup shows the data you just entered)'
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