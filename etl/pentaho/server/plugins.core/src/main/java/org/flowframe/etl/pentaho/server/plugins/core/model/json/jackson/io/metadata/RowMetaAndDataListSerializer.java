package org.flowframe.etl.pentaho.server.plugins.core.model.json.jackson.io.metadata;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.pentaho.di.core.RowMetaAndData;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.row.ValueMetaInterface;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mduduzi on 11/7/13.
 */

public class RowMetaAndDataListSerializer extends JsonSerializer<List<RowMetaAndData>>
{

    @Override
    public void serialize(List<RowMetaAndData> list, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        try {
            jgen.writeStartObject();
            /**
             *
             * generate rows and metadata
             *
             */
            HashMap jsonRow;
            Object elm;
            ValueMetaInterface vm;
            Object[] row;
            RowMetaInterface rowMI;
            String obj;

            //-- metadata
            RowMetaAndData rowMeta = list.get(0);
            jgen.writeArrayFieldStart("metaData");
            for (ValueMetaInterface metaItem : rowMeta.getRowMeta().getValueMetaList()) {
                jgen.writeStartObject();
                jgen.writeStringField("name",metaItem.getName());
                jgen.writeStringField("typeDesc",metaItem.getTypeDesc());
                jgen.writeNumberField("type",metaItem.getType());
                jgen.writeEndObject();
            }
            jgen.writeEndArray();

            //-- data
            jgen.writeNumberField("results", list.size());
            jgen.writeStringField("totalProperty", "results");
            jgen.writeStringField("root", "rows");

            jgen.writeArrayFieldStart("rows");
            List<Map> rows = new ArrayList<Map>();
            for (RowMetaAndData prevrow : list) {

                jgen.writeStartObject();
                row = prevrow.getData();
                rowMI = prevrow.getRowMeta();
                for (int i = 0; i < rowMI.getFieldNames().length; i++) {
                    elm = row[i];
                    vm = rowMI.getValueMeta(i);
                    obj = rowMI.getString(row, i);
                    switch(vm.getType()) {
                        case 5:/*TYPE_INTEGER*/
                            jgen.writeNumberField(vm.getName(), Long.valueOf(obj));
                            break;
                        case 2:/*TYPE_STRING*/
                            jgen.writeStringField(vm.getName(), obj);
                            break;
                        case 3:/*TYPE_DATE*/
                            jgen.writeStringField(vm.getName(), obj);
                            break;
                        case 4:/*TYPE_BOOLEAN*/
                            jgen.writeBooleanField(vm.getName(), Boolean.valueOf(obj));
                            break;
                        case 1:/*TYPE_NUMBER*/
                        case 6:/*TYPE_BIGNUMBER*/
                            jgen.writeNumberField(vm.getName(), new BigDecimal(obj.toString()));
                            break;
                        default:
                            jgen.writeStringField(vm.getName(), obj);
                            break;
                    }
                }
                jgen.writeEndObject();
            }
            jgen.writeEndArray();


            jgen.writeEndObject();
        } catch (Exception e) {
            throw new IOException(e);
        }
    }
}