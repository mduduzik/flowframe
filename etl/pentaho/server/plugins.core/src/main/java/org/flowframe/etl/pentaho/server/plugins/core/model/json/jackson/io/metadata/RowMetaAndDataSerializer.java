package org.flowframe.etl.pentaho.server.plugins.core.model.json.jackson.io.metadata;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.flowframe.kernel.common.utils.HTMLUtil;
import org.flowframe.kernel.common.utils.StringUtil;
import org.pentaho.di.core.RowMetaAndData;
import org.pentaho.di.core.exception.KettleValueException;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.row.ValueMetaInterface;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Created by Mduduzi on 11/7/13.
 */

public class RowMetaAndDataSerializer extends JsonSerializer<RowMetaAndData>
{

    @Override
    public void serialize(RowMetaAndData value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        try {
            jgen.writeStartObject();
            Object[] row = value.getData();
            RowMetaInterface rowMI = value.getRowMeta();
            for (int i = 0; i < rowMI.getFieldNames().length; i++) {
                Object elm = row[i];
                ValueMetaInterface vm = rowMI.getValueMeta(i);
                String obj = rowMI.getString(row, i);
                switch(vm.getType()) {
                    case 5:/*TYPE_INTEGER*/
                        jgen.writeNumberField(vm.getName(), (obj == null)?null:Long.valueOf(StringUtil.trim(obj.toString())));
                        break;
                    case 2:/*TYPE_STRING*/
                        jgen.writeStringField(vm.getName(), HTMLUtil.escape(obj));
                        break;
                    case 3:/*TYPE_DATE*/
                        jgen.writeStringField(vm.getName(), obj);
                        break;
                    case 4:/*TYPE_BOOLEAN*/
                        jgen.writeBooleanField(vm.getName(),  (obj == null)?null:Boolean.valueOf(StringUtil.trim(obj.toString())));
                        break;
                    case 1:/*TYPE_NUMBER*/
                    case 6:/*TYPE_BIGNUMBER*/
                        jgen.writeNumberField(vm.getName(), (obj == null)?null:new BigDecimal(StringUtil.trim(obj.toString())));
                        break;
                    default:
                        jgen.writeStringField(vm.getName(), obj);
                        break;
                }
            }
            jgen.writeEndObject();

        } catch (KettleValueException e) {
            throw new IOException(e);
        }
    }
}