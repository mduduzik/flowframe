// vim: sw=4:ts=4:nu:nospell:fdc=4
/*global Ext */
/**
 * Tabs in Form Example by Saki
 *
 * @author    Ing. Jozef Sakáloš
 * @copyright (c) 2008, by Ing. Jozef Sakáloš
 * @date      29. May 2008
 * @version   $Id: tabsinform.js 141 2009-04-05 00:31:39Z jozo $
 *
 * @license tabsinform.js is licensed under the terms of the Open Source
 * LGPL 3.0 license. Commercial use is permitted to the extent that the
 * code/component(s) do NOT become part of another Open Source or Commercially
 * licensed development library or toolkit without explicit permission.
 *
 * License details: http://www.gnu.org/licenses/lgpl.html
 */

Ext.BLANK_IMAGE_URL = '/etl/lib/ext-2.0.2/resources/images/default/s.gif';

// application main entry point
Ext.onReady(function () {

    Ext.QuickTips.init();

    // invalid markers to sides
    Ext.form.Field.prototype.msgTarget = 'side';

    var win = new Ext.Window({
        id: 'tabsinform-win', width: 300, minWidth: 240, height: 228, minHeight: 160, layout: 'fit', title: Ext.get('page-title').dom.innerHTML, closable: false

        // form
        , items: [
            {
                xtype: 'form', id: 'tabsinform-form', border: false, url: 'tabsinform.php'

                // tabpanel
                , items: [
                {
                    xtype: 'verticaltabpanel',
                    activeItem: 0,
                    border: false,
                    tabPosition:'left',  //choose 'left' or 'right' for vertical tabs; 'top' or 'bottom' for horizontal tabs
                    textAlign:'right'
                    // this line is necessary for anchoring to work at
                    // lower level containers and for full height of tabs
                    , anchor: '100% 100%'

                    // only fields from an active tab are submitted
                    // if the following line is not persent
                    , deferredRender: false

                    // tabs
                    , defaults: {
                                    layout: 'form', labelWidth: 80, defaultType: 'textfield', bodyStyle: 'padding:5px'

                                    // as we use deferredRender:false we mustn't
                                    // render tabs into display:none containers
                                    , hideMode: 'offsets'
                                },
                    items: [
                        {
                            title: 'Metadata', autoScroll: true, defaults: {anchor: '-20'}

                            // fields
                            , items: [
                            {
                                title: 'Schema',
                                xtype: 'fieldset',
                                labelWidth: 200,
                                layoutConfig: {
                                    labelAlign: 'right'
                                },
                                //title:'Company details',
                                defaults: {width: 350},	// Default config options for child items
                                defaultType: 'textfield',
                                autoHeight: true,
                                bodyStyle: Ext.isIE ? 'padding:0 0 5px 15px;' : 'padding:10px 15px;',
                                border: true,
                                style: {
                                    "margin-left": "10px", // when you add custom margin in IE 6...
                                    "margin-right": Ext.isIE6 ? (Ext.isStrict ? "-10px" : "-13px") : "0"  // you have to adjust for it somewhere else
                                },
                                items: [
                                    {
                                        id: 'uploadsamplefile.fileEntryId',
                                        fieldLabel: 'Metadata ID',
                                        name: 'fileEntryId',
                                        emptyText: '<Upload sample file below>',
                                        disabled: true,
                                        allowBlank: false,
                                        required: true
                                        //getSubmitData: getDisabledFieldValue
                                    }
                                ]
                            },
                            {
                                title: 'Sample File',
                                xtype: 'fieldset',
                                labelWidth: 200,
                                layoutConfig: {
                                    labelAlign: 'right'
                                },
                                //title:'Company details',
                                defaults: {width: 350},	// Default config options for child items
                                defaultType: 'textfield',
                                autoHeight: true,
                                bodyStyle: Ext.isIE ? 'padding:0 0 5px 15px;' : 'padding:10px 15px;',
                                border: true,
                                style: {
                                    "margin-left": "10px", // when you add custom margin in IE 6...
                                    "margin-right": Ext.isIE6 ? (Ext.isStrict ? "-10px" : "-13px") : "0"  // you have to adjust for it somewhere else
                                },
                                items: [
                                    {
                                        id: 'uploadsamplefile.fileEntryId',
                                        fieldLabel: 'File Repository ID',
                                        name: 'fileEntryId',
                                        emptyText: '<Upload sample file below>',
                                        disabled: true,
                                        allowBlank: false,
                                        required: true
                                        //getSubmitData: getDisabledFieldValue
                                    },
                                    {
                                        id: 'uploadsamplefile.filename',
                                        xtype: 'textfield',
                                        fieldLabel: 'Filename',
                                        name: 'filename',
                                        emptyText: '<Browse and select file first>',
                                        disabled: true,
                                        allowBlank: false
                                        //getSubmitData: getDisabledFieldValue

                                    },
                                    {
                                        xtype: 'textfield',
                                        id: 'fileuploadfield.form-file',
                                        emptyText: 'Select a CSV file',
                                        fieldLabel: 'Local File to Upload',
                                        name: 'file'
                                    }
                                ]
                            }
                        ]
                    },
                    {
                        title: 'Basic Settings', autoScroll: true, defaults: {anchor: '-20'}

                        // fields
                        , items: [
                        {
                            xtype: 'fieldset',
                            labelWidth: 200,
                            layoutConfig: {
                                labelAlign: 'right'
                            },
                            //title:'Company details',
                            defaults: {width: 350},	// Default config options for child items
                            defaultType: 'textfield',
                            autoHeight: true,
                            bodyStyle: Ext.isIE ? 'padding:0 0 5px 15px;' : 'padding:10px 15px;',
                            border: false,
                            style: {
                                "margin-left": "10px", // when you add custom margin in IE 6...
                                "margin-right": Ext.isIE6 ? (Ext.isStrict ? "-10px" : "-13px") : "0"  // you have to adjust for it somewhere else
                            },
                            items: [
                                {
                                    fieldLabel: 'Metadata Name',
                                    name: 'name',
                                    emptyText: '<Enter name>',
                                    allowBlank: false
                                },
                                {
                                    fieldLabel: 'Row Number Field',
                                    name: 'rowNumField'
                                },
                                {
                                    xtype: 'checkbox',
                                    fieldLabel: 'Header Present',
                                    name: 'headerPresent',
                                    width: 15
                                },
                                {
                                    fieldLabel: 'Delimiter',
                                    name: 'delimiter'
                                },
                                {
                                    fieldLabel: 'Enclosure',
                                    name: 'enclosure'
                                },
                                {
                                    xtype: 'numberfield',
                                    fieldLabel: 'Buffer Size',
                                    name: 'bufferSize',
                                    style: 'text-align: left'
                                },
                                {
                                    xtype: 'checkbox',
                                    fieldLabel: 'Lazy Conversion',
                                    name: 'lazyConversionActive',
                                    width: 15
                                },
                                {
                                    xtype: 'combo',
                                    name: 'encoding',
                                    fieldLabel: 'Encoding',
                                    hiddenName: 'encoding',
                                    store: new Ext.data.Store({
                                        id: "store",
                                        remoteSort: true,
                                        autoLoad: {params: {start: 1, limit: 2}},
                                        proxy: new Ext.data.ScriptTagProxy({
                                            url: '/etlrepo/encoding/getall'
                                        }),
                                        reader: new Ext.data.JsonReader({
                                            root: 'data',
                                            totalProperty: 'totalCount'
                                        }, [
                                            {name: 'id', mapping: 'id'},
                                            {name: 'code', mapping: 'code'},
                                            {name: 'description', mapping: 'description'}
                                        ])
                                    }),

                                    valueField: 'code',
                                    displayField: 'description',
                                    typeAhead: true,
                                    mode: 'local',
                                    triggerAction: 'all',
                                    emptyText: 'Select encoding...',
                                    selectOnFocus: true,
                                    width: 190
                                }
                                ,
                                {
                                    xtype: 'checkbox',
                                    fieldLabel: 'Newline Possible In Fields',
                                    name: 'newlinePossibleInFields',
                                    width: 15
                                }
                            ]
                        }
                        ]
                    },
                    {
                        title: 'Fields'

                        // fields
                        , items: [
                        {
                            xtype: 'fieldset',
                            labelWidth: 200,
                            layoutConfig: {
                                labelAlign: 'right'
                            },
                            //title:'Company details',
                            defaults: {width: 350},	// Default config options for child items
                            defaultType: 'textfield',
                            autoHeight: true,
                            bodyStyle: Ext.isIE ? 'padding:0 0 5px 15px;' : 'padding:10px 15px;',
                            border: false,
                            style: {
                                "margin-left": "10px", // when you add custom margin in IE 6...
                                "margin-right": Ext.isIE6 ? (Ext.isStrict ? "-10px" : "-13px") : "0"  // you have to adjust for it somewhere else
                            },
                            items: [
                                {
                                    fieldLabel: 'Metadata Name',
                                    name: 'name',
                                    emptyText: '<Enter name>',
                                    allowBlank: false
                                },
                                {
                                    fieldLabel: 'Row Number Field',
                                    name: 'rowNumField'
                                },
                                {
                                    xtype: 'checkbox',
                                    fieldLabel: 'Header Present',
                                    name: 'headerPresent',
                                    width: 15
                                },
                                {
                                    fieldLabel: 'Delimiter',
                                    name: 'delimiter'
                                },
                                {
                                    fieldLabel: 'Enclosure',
                                    name: 'enclosure'
                                },
                                {
                                    xtype: 'numberfield',
                                    fieldLabel: 'Buffer Size',
                                    name: 'bufferSize',
                                    style: 'text-align: left'
                                },
                                {
                                    xtype: 'checkbox',
                                    fieldLabel: 'Lazy Conversion',
                                    name: 'lazyConversionActive',
                                    width: 15
                                },
                                {
                                    xtype: 'combo',
                                    name: 'encoding',
                                    fieldLabel: 'Encoding',
                                    hiddenName: 'encoding',
                                    store: new Ext.data.Store({
                                        id: "store",
                                        remoteSort: true,
                                        autoLoad: {params: {start: 1, limit: 2}},
                                        proxy: new Ext.data.ScriptTagProxy({
                                            url: '/etlrepo/encoding/getall'
                                        }),
                                        reader: new Ext.data.JsonReader({
                                            root: 'data',
                                            totalProperty: 'totalCount'
                                        }, [
                                            {name: 'id', mapping: 'id'},
                                            {name: 'code', mapping: 'code'},
                                            {name: 'description', mapping: 'description'}
                                        ])
                                    }),

                                    valueField: 'code',
                                    displayField: 'description',
                                    typeAhead: true,
                                    mode: 'local',
                                    triggerAction: 'all',
                                    emptyText: 'Select encoding...',
                                    selectOnFocus: true,
                                    width: 190
                                }
                                ,
                                {
                                    xtype: 'checkbox',
                                    fieldLabel: 'Newline Possible In Fields',
                                    name: 'newlinePossibleInFields',
                                    width: 15
                                }
                            ]
                        }
                    ]
                    },
                    {
                        title: 'Preview'
                    },
                    {
                        title: 'View'
                    },
                    {
                        title: 'Documentation'
                    }
                ]

                }
            ], buttons: [
                {
                    text: 'Load', iconCls: 'icon-load', handler: function () {
                    Ext.getCmp('tabsinform-form').getForm().load({params: {cmd: 'load'}});
                }
                },
                {
                    text: 'Submit', iconCls: 'icon-disk', handler: function () {
                    var formValues = {};
                    var form = Ext.getCmp('tabsinform-form').getForm();
                    var values = form.getValues(false);
                    var applyProps = true;
                    for (p in values) {
                        var propname = p+'';
                        if (propname.indexOf('ext-comp-') == 0)
                            applyProps = false;
                    }
                    if (applyProps)
                        Ext.apply(formValues, values);
                }
                }
            ]
            }
        ]
    }); // eo new Ext.Window

    // show window
    win.show();

}); // eo function onReady

// eof
