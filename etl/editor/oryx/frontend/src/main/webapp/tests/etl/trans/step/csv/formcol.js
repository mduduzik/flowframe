// vim: sw=4:ts=4:nu:nospell:fdc=4
/*global Ext */
/**
 * Column Layout in Form
 *
 * @author    Ing. Jozef Sakáloš
 * @copyright (c) 2008, by Ing. Jozef Sakáloš
 * @date      10. April 2008
 * @version   $Id: formcol.js 137 2009-03-13 23:00:01Z jozo $
 *
 * @license formcol.js is licensed under the terms of the Open Source
 * LGPL 3.0 license. Commercial use is permitted to the extent that the 
 * code/component(s) do NOT become part of another Open Source or Commercially
 * licensed development library or toolkit without explicit permission.
 * 
 * License details: http://www.gnu.org/licenses/lgpl.html
 */
 
Ext.BLANK_IMAGE_URL = './ext/resources/images/default/s.gif';
 
// application main entry point
Ext.onReady(function() {
	var win = new Ext.Window({
         title:Ext.get('page-title').dom.innerHTML
		,renderTo:Ext.getBody()
		,iconCls:'icon-bulb'
		,width:420
		,height:240
		,border:false
		,layout:'fit'
		,items:[{
			// form as the only item in window
			 xtype:'form'	
			,labelWidth:60
			,frame:true
			,items:[{
				// textfield
				 fieldLabel:'Text'
				,xtype:'textfield'
				,anchor:'-18'
			},{
				// column layout with 2 columns
				 layout:'column'

				// defaults for columns
				,defaults:{
					 columnWidth:0.5
					,layout:'form'
					,border:false
					,xtype:'panel'
					,bodyStyle:'padding:0 18px 0 0'
				}
				,items:[{
					// left column
					// defaults for fields
					 defaults:{anchor:'100%'}
					,items:[{
						 xtype:'combo'
						,fieldLabel:'Combo 1'
						,store:['Item 1', 'Item 2']
					},{
						 xtype:'datefield'
						,fieldLabel:'Date'
					}]
				},{
					// right column
					// defaults for fields
					 defaults:{anchor:'100%'}
					,items:[{
						 xtype:'combo'
						,fieldLabel:'Combo 2'
						,store:['Item 1', 'Item 2']
					},{
						 xtype:'timefield'
						,fieldLabel:'Time'
					}]
				}]
			},{
				// bottom textarea
				 fieldLabel:'Text Area'
				,xtype:'textarea'
				,anchor:'-18 -80'
			}]
		}]
	});
	win.show();

}); // eo function onReady
 
// eof
