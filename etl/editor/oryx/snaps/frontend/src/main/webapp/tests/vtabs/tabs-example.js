/*!
 * Ext JS Library 3.3.0
 * Copyright(c) 2006-2010 Ext JS, Inc.
 * licensing@extjs.com
 * http://www.extjs.com/license
 */
Ext.onReady(function(){
    // basic tabs 1, built from existing content
    var tabs = new Ext.ux.tot2ivn.VrTabPanel({
        renderTo: 'tabs1',
        width:450,
        activeTab: 0,
        frame:true,		
		bodyStyle: 'padding: 10px',
        defaults:{autoHeight: true},
        items:[
            {contentEl:'script', title: 'Short Text'},
            {contentEl:'markup', title: 'Long Text'}
        ]
    });

    // second tabs built from JS
    var tabs2 = new Ext.ux.tot2ivn.VrTabPanel({
        renderTo: 'tabs2',
        activeTab: 0,
        width:600,
        height:250,
        plain:true,
		bodyStyle: 'padding: 10px',
        defaults:{autoScroll: true},
        items:[{
                title: 'Normal Tab',
                html: "My content was added during construction."
            },{
                title: 'Ajax Tab 1',
                autoLoad:'ajax1.htm'
            },{
                title: 'Ajax Tab 2',
                autoLoad: {url: 'ajax2.htm', params: 'foo=bar&wtf=1'}
            },{
                title: 'Event Tab',
                listeners: {activate: handleActivate},
                html: "I am tab 4's content. I also have an event listener attached."
            },{
                title: 'Disabled Tab',
                disabled:true,
                html: "Can't see me cause I'm disabled"
            }
        ]
    });

    function handleActivate(tab){
        alert(tab.title + ' was activated.');
    }
	
	// Vertical Tabs ( Ext.ux.tot2ivn.VrTabPanel ) nested inside Horizontal TabPanel ( Ext.TabPanel ).
    var tabs4 = new Ext.TabPanel({
        renderTo: 'tabs4',
        activeTab: 0,
        width:600,
        height:250,
        plain:true,						
        defaults:{autoScroll: true},
        items:[{
                title: 'Normal Tab',
                xtype: 'vrtabpanel',
				border: false,				
				activeTab: 1,
				plain:true,
				defaults:{autoScroll: true, border: false},
				items:[{
						title: 'Normal Tab',
						html: "My content was added during construction."
					},{
						title: 'Ajax Tab 1',
						autoLoad:'ajax1.htm'
					},{
						title: 'Ajax Tab 2',
						autoLoad: {url: 'ajax2.htm', params: 'foo=bar&wtf=1'}
					},{
						title: 'Event Tab',
						listeners: {activate: handleActivate},
						html: "I am tab 4's content. I also have an event listener attached."
					},{
						title: 'Disabled Tab',
						disabled:true,
						html: "Can't see me cause I'm disabled"
					}
				]
            },{
                title: 'Ajax Tab 1',
                autoLoad:'ajax1.htm'
            },{
                title: 'Ajax Tab 2',
                autoLoad: {url: 'ajax2.htm', params: 'foo=bar&wtf=1'}
            },{
                title: 'Disabled Tab',
                disabled:true,
                html: "Can't see me cause I'm disabled"
            }
        ]
    });
	
	// Horizontal Tabs ( Ext.TabPanel ) nested inside Vertical TabPanel.
    var tabs3 = new Ext.ux.tot2ivn.VrTabPanel({
        renderTo: 'tabs3',
        activeTab: 0,
        width:600,
        height:250,
        plain:true,
		tabMarginTop: 30,	/* Push the tab strip down 30px from top. If not set, defaults to 15.*/
		bodyStyle: 'padding: 10px',
        defaults:{autoScroll: true},
        items:[{
                title: 'Normal Tab',
                xtype: 'tabpanel',
				activeTab: 1,
				plain:true,
				defaults:{autoScroll: true},
				items:[{
						title: 'Normal Tab',
						html: "My content was added during construction."
					},{
						title: 'Ajax Tab 1',
						autoLoad:'ajax1.htm'
					},{
						title: 'Ajax Tab 2',
						autoLoad: {url: 'ajax2.htm', params: 'foo=bar&wtf=1'}
					},{
						title: 'Event Tab',
						listeners: {activate: handleActivate},
						html: "I am tab 4's content. I also have an event listener attached."
					},{
						title: 'Disabled Tab',
						disabled:true,
						html: "Can't see me cause I'm disabled"
					}
				]
            },{
                title: 'Ajax Tab 1',
                autoLoad:'ajax1.htm'
            },{
                title: 'Ajax Tab 2',
                autoLoad: {url: 'ajax2.htm', params: 'foo=bar&wtf=1'}
            },{
                title: 'Disabled Tab',
                disabled:true,
                html: "Can't see me cause I'm disabled"
            }
        ]
    });
});