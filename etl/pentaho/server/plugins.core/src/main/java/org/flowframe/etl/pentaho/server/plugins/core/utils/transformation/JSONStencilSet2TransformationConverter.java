package org.flowframe.etl.pentaho.server.plugins.core.utils.transformation;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.flowframe.etl.pentaho.server.plugins.core.utils.DatabaseMetaUtil;
import org.flowframe.etl.pentaho.server.plugins.core.utils.RepositoryUtil;
import org.flowframe.etl.pentaho.server.repository.util.ICustomRepository;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.plugins.PluginRegistry;
import org.pentaho.di.core.plugins.StepPluginType;
import org.pentaho.di.trans.TransHopMeta;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.pentaho.di.trans.steps.tableoutput.TableOutputMeta;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mduduzi on 9/30/13.
 */
public class JSONStencilSet2TransformationConverter {
    private static PluginRegistry registry = PluginRegistry.getInstance();
    public static final TransMeta toTransMeta(ICustomRepository repository, String jsonModel) throws JSONException, KettleException {
        JSONObject jsonObject = new JSONObject(jsonModel);

        Map<String,StepMeta> name2StepMeta = new HashMap<String,StepMeta>();

        TransMeta transMeta = new TransMeta();
        transMeta.setName(jsonObject.getString("resourceId"));

        JSONArray childShapes = (JSONArray)jsonObject.get("childShapes");
        JSONObject childShape = null;
        String type = null;
        StepMeta stepMeta = null;

        //-- Create steps
        for (int index = 0; index < childShapes.length(); index++ ){
            childShape = (JSONObject)childShapes.get(index);

            type = ((JSONObject)childShape.get("stencil")).getString("id");

            //================== Inputs
            if ("CsvInput".equals(type)) {
                //Lookup meta
                stepMeta = RepositoryUtil.getStep(repository, getStringProperty(childShape, "metadataobjid"));
                stepMeta.setName(getStringProperty(childShape,"name"));
                transMeta.addStep(stepMeta);
                name2StepMeta.put(getStringProperty(childShape,"name"),stepMeta);
            }

            //================== Outtputs
            if ("TableOutput".equals(type)) {
                //Lookup db
                String pathId = getStringProperty(childShape, "metadataobjid");
                String targettable = getStringProperty(childShape, "targettable");
                if (targettable == null || targettable.isEmpty()) {//Get from default metadata
                    targettable = RepositoryUtil.getDBTablenameFromPathID(pathId);
                }
                DatabaseMeta db = DatabaseMetaUtil.getDatabaseMetaByPathId(repository, pathId);
                transMeta.addDatabase(db);

                //Create meta
                TableOutputMeta tom = new TableOutputMeta();
                tom.setDatabaseMeta(db);
                tom.setTableName(targettable);

                String fromid = registry.getPluginId(StepPluginType.class, tom);
                stepMeta = new StepMeta(fromid, getStringProperty(childShape,"name"), (StepMetaInterface)tom);

                transMeta.addStep(stepMeta);
                name2StepMeta.put(getStringProperty(childShape,"name"),stepMeta);
            }
        }


        //-- Create hops
        for (int index = 0; index < childShapes.length(); index++ ){
            childShape = (JSONObject)childShapes.get(index);

            type = ((JSONObject)childShape.get("stencil")).getString("id");
            //================== Hops
            if ("connector".equals(type)) {
                //Get from:
                String fromStepName = findFromStepName(childShapes,childShape);
                //Get to:
                String toStepName = findToStepName(childShapes, childShape);

                TransHopMeta hop = new TransHopMeta(name2StepMeta.get(fromStepName), name2StepMeta.get(toStepName));
                transMeta.addTransHop(hop);
            }

        }

        return transMeta;
    }

    private static String findFromStepName(JSONArray childShapes, JSONObject edgeShape) throws JSONException {
        String edgeResourceId = edgeShape.getString("resourceId");
        StepMeta stepMeta = null;
        for (int index = 0; index < childShapes.length(); index++ ){
            JSONObject childShape_ = (JSONObject) childShapes.get(index);
            final JSONArray outgoingArray = (JSONArray) childShape_.get("outgoing");
            if (outgoingArray.length() > 0) {
                JSONObject outgoing = (JSONObject)outgoingArray.get(0);
                String resourceId = outgoing.getString("resourceId");
                if (edgeResourceId.equals(resourceId)) {
                    return getStringProperty(childShape_,"name");
                }
            }
        }

        return null;
    }

    private static String findToStepName(JSONArray childShapes, JSONObject edgeShape) throws JSONException {
        String edgeTargetResourceid = ((JSONObject) edgeShape.get("target")).getString("resourceId");
        StepMeta stepMeta = null;
        for (int index = 0; index < childShapes.length(); index++ ){
            JSONObject childShape_ = (JSONObject) childShapes.get(index);
            String rcId = childShape_.getString("resourceId");
            if (edgeTargetResourceid.equals(rcId)) {
                return getStringProperty(childShape_,"name");
            }
        }

        return null;
    }


    public static final String getStringProperty(JSONObject childShape, String propName) throws JSONException {
        JSONObject props = (JSONObject)childShape.get("properties");
        return props.getString(propName);
    }
}
