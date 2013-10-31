package org.flowframe.etl.pentaho.server.plugins.core.model;

import org.codehaus.jettison.json.JSONException;
import org.pentaho.di.job.JobMeta;

/**
 * Created with IntelliJ IDEA.
 * User: Mduduzi
 * Date: 8/21/13
 * Time: 6:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class JobMetaDTO extends BaseDTO {
    private String name;

    private String jsonModel;

    private String svgModel;

    public JobMetaDTO() {
    }

    public JobMetaDTO(JobMeta transMeta, String dirPathId, String jsonModel, String svgModel) {
        this(transMeta);
        super.setSubDirPathId(dirPathId);
        this.jsonModel = jsonModel;
        this.svgModel = svgModel;
    }

    public JobMetaDTO(JobMeta transMeta) {
        setName(transMeta.getName());
        setPathId(transMeta.getObjectId().toString());
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSvgModel() {
        return svgModel;
    }

    public void setSvgModel(String svgModel) {
        this.svgModel = svgModel;
    }

    public String getJsonModel() {
        return jsonModel;
    }

    public void setJsonModel(String jsonModel) {
        this.jsonModel = jsonModel;
    }

    @Override
    public Object fromDTO(Class type) throws JSONException {
/*        final String thisJson = toJSON();
        final JSONObject obj = new JSONObject(thisJson);
        obj.remove("class");

        final JSONDeserializer metaDeserializer = new JSONDeserializer();
        JSONArray ifls = null;
        List<TextFileInputField> fieldList = null;
        if (obj.has("inputFields") && obj.get("inputFields") != JSONObject.NULL){
            fieldList = new ArrayList<TextFileInputField>();
            ifls = (JSONArray) obj.get("inputFields");
            for (int i=0;i<ifls.length(); i++){
                ((JSONObject)ifls.get(i)).remove("class");
                fieldList.add((TextFileInputField)metaDeserializer.deserialize(((JSONObject)ifls.get(i)).toString(),TextFileInputField.class));
            }
        }
        obj.remove("inputFields");
        final CsvInputMeta meta = (CsvInputMeta)metaDeserializer.deserialize(obj.toString(),type);
        if (fieldList != null) {
            meta.setInputFields(fieldList.toArray(new TextFileInputField[]{}));
        }
        */
        final JobMeta meta = null;

        return meta;
    }
}
