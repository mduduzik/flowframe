Ext.namespace('Ext.ux', 'Ext.ux.dynagrid');
Ext.ux.dynagrid.DynamicJsonReader = function(config){
    Ext.ux.dynagrid.DynamicJsonReader.superclass.constructor.call(this, config, []);
};
Ext.extend(Ext.ux.dynagrid.DynamicJsonReader, Ext.data.JsonReader, {
    readRecords : function(o){
        this.jsonData = o;
        if(o.metaData){
            delete this.ef;
            this.meta = o.metaData;
            this.recordType = Ext.data.Record.create(o.metaData.fields);
            this.onMetaChange(this.meta, this.recordType, o);
        } else {
            var data = this.meta.root ? this.getJsonAccessor(this.meta.root)(o) : o;
            if (Ext.isArray(data) && data[0]) {
                delete this.ef;
                var fields = [];
                for (var fieldName in data[0]) {
                    fields.push(fieldName);
                }
                this.meta.fields = fields;
                this.recordType = Ext.data.Record.create(fields);
                this.onMetaChange(this.meta, this.recordType, o);
            }
        }
        var s = this.meta, Record = this.recordType,
            f = Record.prototype.fields, fi = f.items, fl = f.length;
        if (!this.ef) {
            if(s.totalProperty) {
                this.getTotal = this.getJsonAccessor(s.totalProperty);
            }
            if(s.successProperty) {
                this.getSuccess = this.getJsonAccessor(s.successProperty);
            }
            this.getRoot = s.root ? this.getJsonAccessor(s.root) : function(p){return p;};
            if (s.id) {
                var g = this.getJsonAccessor(s.id);
                this.getId = function(rec) {
                    var r = g(rec);
                    return (r === undefined || r === "") ? null : r;
                };
            } else {
                this.getId = function(){return null;};
            }
            this.ef = [];
            for(var i = 0; i < fl; i++){
                f = fi[i];
                var map = (f.mapping !== undefined && f.mapping !== null) ? f.mapping : f.name;
                this.ef[i] = this.getJsonAccessor(map);
            }
        }
        var root = this.getRoot(o), c = root.length, totalRecords = c, success = true;
        if(s.totalProperty){
            var v = parseInt(this.getTotal(o), 10);
            if(!isNaN(v)){
                totalRecords = v;
            }
        }
        if(s.successProperty){
            var v = this.getSuccess(o);
            if(v === false || v === 'false'){
                success = false;
            }
        }
        var records = [];
        for(var i = 0; i < c; i++){
            var n = root[i];
            var values = {};
            var id = this.getId(n);
            for(var j = 0; j < fl; j++){
                f = fi[j];
                var v = this.ef[j](n);
                values[f.name] = f.convert((v !== undefined) ? v : f.defaultValue, n);
            }
            var record = new Record(values, id);
            record.json = n;
            records[i] = record;
        }
        return {
            success : success,
            records : records,
            totalRecords : totalRecords
        };
    }
});
Ext.ux.dynagrid.DynamicColumnModel = function(store, config){
    Ext.ux.dynagrid.DynamicColumnModel.superclass.constructor.call(this, Ext.apply({store: store, columns: []}, config));
    if (store.fields) {
        this.reconfigure();
    }
    store.on('load', this.reconfigure, this);
};
Ext.extend(Ext.ux.dynagrid.DynamicColumnModel, Ext.grid.ColumnModel, {
    reconfigure: function(){
        var cols = [];
        this.store.fields.each(function(field){
            cols.push({header: field.name, dataIndex: field.name});
        });
        this.setConfig(cols);
    }
});