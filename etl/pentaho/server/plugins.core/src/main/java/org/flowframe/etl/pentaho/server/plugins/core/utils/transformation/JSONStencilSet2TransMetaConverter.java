package org.flowframe.etl.pentaho.server.plugins.core.utils.transformation;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.flowframe.etl.pentaho.server.plugins.core.exception.TransConversionException;
import org.flowframe.etl.pentaho.server.repository.util.ICustomRepository;
import org.pentaho.di.core.plugins.PluginRegistry;
import org.pentaho.di.trans.TransHopMeta;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mduduzi on 9/30/13.
 */
public class JSONStencilSet2TransMetaConverter {
    private static PluginRegistry registry = PluginRegistry.getInstance();
    private static StepMetaResourceConversionFactory stepMetaFactory = new StepMetaResourceConversionFactory();

    public static final TransMeta toTransMeta(Map<String,Object> options, String jsonModel, boolean externalize) throws JSONException, IOException, TransConversionException {
        ICustomRepository repository = (ICustomRepository)options.get("etlRepository");
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

            String stepName = getStringProperty(childShape, "name");
            stepMeta = new StepMeta(stepName,stepMetaFactory.create(type,getStringProperty(childShape, "stepmeta")));
            stepMeta.setDraw(true);
            transMeta.addStep(stepMeta);
            name2StepMeta.put(stepName,stepMeta);
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

        if (externalize)
            transMeta = stepMetaFactory.externalize(transMeta,options);

        return transMeta;
    }

    public static TransMeta externalize(TransMeta transMeta, Map<String,Object> options) throws TransConversionException {
        return stepMetaFactory.externalize(transMeta,options);
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
