package org.flowframe.etl.pentaho.server.plugins.core.model;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Mduduzi
 * Date: 8/21/13
 * Time: 6:30 PM
 * To change this template use File | Settings | File Templates.
 */
abstract public class BaseDTO implements Serializable {

    private String subDirObjId;
    private String subDirPathId;
    private String pathId;

    public String toJSON() {
        JSONSerializer serializer = new JSONSerializer();
        return serializer.deepSerialize(this);
    }

    public Object fromDTO(Class type) throws JSONException {
        final String thisJson = toJSON();
        final JSONObject obj = new JSONObject(thisJson);
        obj.remove("class");
        JSONDeserializer metaDeserializer = new JSONDeserializer();
        return metaDeserializer.deserialize(obj.toString(),type);
    }

    public String getSubDirObjId() {
        return subDirObjId;
    }

    public void setSubDirObjId(String subDirObjId) {
        this.subDirObjId = subDirObjId;
    }

    public String getPathId() {
        return pathId;
    }

    public void setPathId(String pathId) {
        this.pathId = pathId;
    }

    public String getSubDirPathId() {
        return subDirPathId;
    }

    public void setSubDirPathId(String subDirPathId) {
        this.subDirPathId = subDirPathId;
    }
}
