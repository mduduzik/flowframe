package org.flowframe.etl.pentaho.server.plugins.core.utils.job;

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
import org.pentaho.di.job.JobHopMeta;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.job.entry.JobEntryCopy;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.csvinput.CsvInputMeta;
import org.pentaho.di.trans.steps.tableoutput.TableOutputMeta;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mduduzi on 9/30/13.
 */
public class JSONStencilSet2JobConverter {
    private static PluginRegistry registry = PluginRegistry.getInstance();
    public static final JobMeta toJobMeta(ICustomRepository repository, String jsonModel) throws JSONException, KettleException {
        JSONObject jsonObject = new JSONObject(jsonModel);

        Map<String,JobEntryCopy> name2StepMeta = new HashMap<String,JobEntryCopy>();

        JobMeta jobMeta = new JobMeta();
        jobMeta.setName(jsonObject.getString("resourceId"));

        JSONArray childShapes = (JSONArray)jsonObject.get("childShapes");
        JSONObject childShape = null;
        String type = null;
        JobEntryCopy stepMeta = null;

        //-- Create steps
        for (int index = 0; index < childShapes.length(); index++ ){
            childShape = (JSONObject)childShapes.get(index);

            type = ((JSONObject)childShape.get("stencil")).getString("id");

            //================== Inputs
            if ("CsvInput".equals(type)) {
                //Lookup meta
                stepMeta = RepositoryUtil.getJobEntry(repository, getStringProperty(childShape, "metadataobjid"));
                stepMeta.setName(getStringProperty(childShape,"name"));
                ((CsvInputMeta)stepMeta.getEntry()).setFilename(getStringProperty(childShape, "filename"));
                jobMeta.addJobEntry(stepMeta);
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
                jobMeta.addDatabase(db);

                //Create meta
                TableOutputMeta tom = new TableOutputMeta();
                tom.setDatabaseMeta(db);
                tom.setTableName(targettable);

                String fromid = registry.getPluginId(StepPluginType.class, tom);
                //stepMeta = new JobEntryCopy(fromid, getStringProperty(childShape,"name"), (JobEntry)tom);

                jobMeta.addJobEntry(stepMeta);
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

                JobHopMeta hop = new JobHopMeta(name2StepMeta.get(fromStepName), name2StepMeta.get(toStepName));
                jobMeta.addJobHop(hop);
            }

        }

        return jobMeta;
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
