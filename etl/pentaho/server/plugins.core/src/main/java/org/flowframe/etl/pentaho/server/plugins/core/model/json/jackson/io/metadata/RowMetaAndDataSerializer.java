package org.flowframe.etl.pentaho.server.plugins.core.model.json.jackson.io.metadata;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.pentaho.di.core.RowMetaAndData;
import org.pentaho.di.core.exception.KettleValueException;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.row.ValueMetaInterface;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mduduzi on 11/7/13.
 */

public class RowMetaAndDataSerializer extends JsonSerializer<RowMetaAndData>
{

    @Override
    public void serialize(RowMetaAndData value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        try {
            jgen.writeStartObject();

            Map jsonRow = new HashMap();
            Object[] row;
            RowMetaInterface rowMI;
            Object elm;
            ValueMetaInterface vm;
            String obj;

            row = value.getData();
            for (int i = 0; i < value.getRowMeta().getFieldNames().length; i++) {
                elm = row[i];
                vm = value.getRowMeta().getValueMeta(i);
                obj = value.getRowMeta().getString(row, i);
                if (!vm.getName().equals("filename"))
                    jgen.writeObjectField(vm.getName(), obj);
            }


            jgen.writeEndObject();
        } catch (KettleValueException e) {
            throw new IOException(e);
        }
    }
}