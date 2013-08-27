// vim: ts=4:sw=4:nu:fdc=4:nospell
/*global Ext, Example */
/**
 * @class Example.Grid
 * @extends Ext.EditorGridPanel
 *
 * Editor grid for use in examples
 *
 * @author    Ing. Jozef Sakáloš
 * @copyright (c) 2009, by Ing. Jozef Sakáloš
 * @date      1. February 2009
 * @version   0.1
 * @revision  $Id: Example.Grid.js 544 2009-02-06 23:01:30Z jozo $
 *
 * @license Example.Grid.js is licensed under the terms of the Open Source
 * LGPL 3.0 license. Commercial use is permitted to the extent that the 
 * code/component(s) do NOT become part of another Open Source or Commercially
 * licensed development library or toolkit without explicit permission.
 * 
 * <p>License details: <a href="http://www.gnu.org/licenses/lgpl.html"
 * target="_blank">http://www.gnu.org/licenses/lgpl.html</a></p>
 */
 
Ext.ns('Example');
 
/**
 * @constructor
 * Creates new Example.Grid
 * @param {Object} config A config object
 */
Example.Grid = Ext.extend(Ext.grid.EditorGridPanel, {
	 border:false
	,url:'process-request.php'
	,objName:'company'
	,idName:'compID'

	// {{{
	,initComponent:function() {

		// hard coded config (it cannot be changed while instantiating)
		// {{{
		var config = {
			// {{{
			store:new Ext.data.Store({
				reader:new Ext.data.JsonReader({
					 id:'compID'
					,totalProperty:'totalCount'
					,root:'rows'
					,fields:[
						 {name:'compID', type:'int'}
						,{name:'company', type:'string'}
						,{name:'price', type:'float'}
						,{name:'change', type:'float'}
						,{name:'pctChange', type:'float'}
						,{name:'lastChange', type:'date', dateFormat:'n/j/Y'}
						,{name:'industry', type:'string'}
						,{name:'action1', type:'string'}
						,{name:'qtip1', type:'string'}
//						,{name:'action2', type:'string'}
//						,{name:'qtip2', type:'string'}
//						,{name:'action3', type:'string'}
//						,{name:'qtip3', type:'string'}
						,{name:'note', type:'string'}
					]
				})
				,proxy:new Ext.data.HttpProxy({url:this.url})
				,baseParams:{cmd:'getData', objName:this.objName}
				,sortInfo:{field:'company', direction:'ASC'}
				,remoteSort:true
			})
			// }}}
			// {{{
			,columns:[{
				 header:'Company'
				,id:'company'
				,dataIndex:'company'
				,width:160
				,sortable:true
				,editor:new Ext.form.TextField({
					allowBlank:false
				})
			},{
				 header:'Price'
				,dataIndex:'price'
				,width:40
				,sortable:true
				,align:'right'
				,editor:new Ext.form.NumberField({
					 allowBlank:false
					,decimalPrecision:2
					,selectOnFocus:true
				})
			},{
				 header:'Change'
				,dataIndex:'change'
				,width:40
				,sortable:true
				,align:'right'
				,editor:new Ext.form.NumberField({
					 allowBlank:false
					,decimalPrecision:2
					,selectOnFocus:true
				})
			},{
				 header:'Change [%]'
				,dataIndex:'pctChange'
				,width:50
				,sortable:true
				,align:'right'
				,editor:new Ext.form.NumberField({
					 allowBlank:false
					,decimalPrecision:2
					,selectOnFocus:true
				})
			},{
				 header:'Last Updated'
				,dataIndex:'lastChange'
				,width:70
				,sortable:true
				,align:'right'
				,editor:new Ext.form.DateField({
				})
				,renderer:Ext.util.Format.dateRenderer('n/j/Y')
			},{
				 header:'Industry'
				,dataIndex:'industry'
				,width:75
				,sortable:true
				,editor:new Ext.form.ComboBox({
					store:new Ext.data.SimpleStore({
						 id:0
						,fields:['industry']
						,data:[
							 ['Automotive']
							,['Computer']
							,['Finance']
							,['Food']
							,['Manufacturing']
							,['Medical']
							,['Retail']
							,['Services']
						]
					})
					,displayField:'industry'
					,valueField:'industry'
					,triggerAction:'all'
					,mode:'local'
					,editable:false
					,lazyRender:true
					,forceSelection:true
				})
			},{
				 header:'Note'
				,dataIndex:'note'
				,width:75
				,sortable:true
				,editor:new Ext.form.TextArea({
					grow:true
				})
			}]
			// }}}
			,viewConfig:{forceFit:true}
			,buttons:[{
				 text:'Save'
				,iconCls:'icon-disk'
				,scope:this
				,handler:this.commitChanges
			},{
				 text:'Reset'
				,iconCls:'icon-undo'
				,scope:this
				,handler:function() {
					this.store.rejectChanges();
				}
			}]
			,tbar:[{
				 text:'Add Record'
				,iconCls:'icon-plus'
				,listeners:{
					click:{scope:this, fn:this.addRecord,buffer:200}
				}
			}]
		}; // eo config object
		// }}}

		// apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));

		this.bbar = new Ext.PagingToolbar({
			 store:this.store
			,displayInfo:true
			,pageSize:10
		});

		// call parent
		Example.Grid.superclass.initComponent.apply(this, arguments);
	} // eo function initComponent
	// }}}
	// {{{
	,onRender:function() {
		// call parent
		Example.Grid.superclass.onRender.apply(this, arguments);

		// load store
		this.store.load({params:{start:0,limit:10}});

	} // eo function onRender
	// }}}
	// {{{
	,addRecord:function() {
		var store = this.store;
		if(store.recordType) {
			var rec = new store.recordType({newRecord:true});
			rec.fields.each(function(f) {
				rec.data[f.name] = f.defaultValue || null;
			});
			rec.commit();
			store.add(rec);

			// select record
			this.getSelectionModel().select(this.store.indexOf(rec), 0);

			return rec;
		}
		return false;
	} // eo function addRecord
	// }}}
	// {{{
	,commitChanges:function() {
		var records = this.store.getModifiedRecords();
		if(!records.length) {
			return;
		}
		var data = [];
		Ext.each(records, function(r, i) {
			var o = r.getChanges();
			if(r.data.newRecord) {
				o.newRecord = true;
			}
			o[this.idName] = r.get(this.idName);
			data.push(o);
		}, this);
		var o = {
			 url:this.url
			,method:'post'
			,callback:this.requestCallback
			,scope:this
			,params:{
				 cmd:'saveData'
				,objName:this.objName
				,data:Ext.encode(data)
			}
		};
		Ext.Ajax.request(o);
	} // eo function commitChanges
	// }}}
	// {{{
	,requestCallback:function(options, success, response) {
		if(true !== success) {
			this.showError(response.responseText);
			return;
		}
		try {
			var o = Ext.decode(response.responseText);
		}
		catch(e) {
			this.showError(response.responseText, 'Cannot decode JSON object');
			return;
		}
		if(true !== o.success) {
			this.showError(o.error || 'Unknown error');
			return;
		}

		switch(options.params.cmd) {
			case 'saveData':
				var records = this.store.getModifiedRecords();
				Ext.each(records, function(r, i) {
					if(o.insertIds && o.insertIds[i]) {
						r.set(this.idName, o.insertIds[i]);
						delete(r.data.newRecord);
					}
				});
				this.store.commitChanges();
			break;

			case 'deleteData':
			break;
		}
	} // eo function requestCallback
	// }}}
	// {{{
	,showError:function(msg, title) {
		Ext.Msg.show({
			 title:title || 'Error'
			,msg:Ext.util.Format.ellipsis(msg, 2000)
			,icon:Ext.Msg.ERROR
			,buttons:Ext.Msg.OK
			,minWidth:1200 > String(msg).length ? 360 : 600
		});
	} // eo function showError
	// }}}
	// {{{
	,deleteRecord:function(record) {
		Ext.Msg.show({
			 title:'Delete record?'
			,msg:'Do you really want to delete <b>' + record.get('company') + '</b><br/>There is no undo.'
			,icon:Ext.Msg.QUESTION
			,buttons:Ext.Msg.YESNO
			,scope:this
			,fn:function(response) {
				if('yes' !== response) {
					return;
				}
//				console.info('Deleting record');
			}
		});
	} // eo function deleteRecord
	// }}}

}); // eo extend

// register xtype
Ext.reg('examplegrid', Example.Grid);

// eof
