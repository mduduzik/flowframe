package org.flowframe.etl.pentaho.server.plugins.core.utils.transformation;

import org.flowframe.etl.pentaho.server.plugins.core.exception.TransConversionException;
import org.flowframe.etl.pentaho.server.plugins.core.model.json.CustomObjectMapper;
import org.flowframe.etl.pentaho.server.plugins.core.utils.transformation.resourcenormalizer.ExcelInputMetaResourceNormalizer;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.pentaho.di.trans.steps.excelinput.ExcelInputMeta;
import org.pentaho.di.trans.steps.textfileinput.TextFileInputMeta;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mduduzi on 11/22/13.
 */
public class StepMetaResourceConversionFactory {
    public static final CustomObjectMapper mapper = new CustomObjectMapper();

    public final static Map<String,Class> StepPluginIDMetaClassMap = new HashMap<String, Class>(){
            {
                 put("TextFileInput", TextFileInputMeta.class);
                 put("ExcelInput", ExcelInputMeta.class);
            }
    };

    public final static Map<Class,IStepMetaResourceNormalizer> StepPluginIDResourceNormalizerMap = new HashMap<Class, IStepMetaResourceNormalizer>(){
        {
            put(ExcelInputMeta.class, new ExcelInputMetaResourceNormalizer());
        }
    };

    public StepMetaInterface create(String stencilID, String metaJson) throws IOException {
       return (StepMetaInterface)mapper.readValue(metaJson,StepPluginIDMetaClassMap.get(stencilID));
    }

    public StepMetaInterface externalize(StepMetaInterface stepMeta, Map<String,Object> options) throws TransConversionException {
        IStepMetaResourceNormalizer externalizer = StepPluginIDResourceNormalizerMap.get(stepMeta.getClass());
        if (externalizer != null)
            stepMeta = externalizer.normalize(stepMeta, options);

        return stepMeta;
    }

    public TransMeta externalize(TransMeta transMeta, Map<String,Object> options) throws TransConversionException {
        int steps = transMeta.getSteps().size();
        for (int stepIndex = 0; stepIndex < steps; stepIndex++) {
            StepMeta step = transMeta.getStep(stepIndex);
            StepMetaInterface stepMI = externalize(step.getStepMetaInterface(),options);
            step.setStepMetaInterface(stepMI);
        }
        return transMeta;
    }
}