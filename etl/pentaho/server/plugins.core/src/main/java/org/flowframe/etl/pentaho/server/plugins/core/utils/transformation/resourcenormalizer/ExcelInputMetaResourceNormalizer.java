package org.flowframe.etl.pentaho.server.plugins.core.utils.transformation.resourcenormalizer;

import org.flowframe.etl.pentaho.server.plugins.core.resource.BaseDelegateResource;
import org.flowframe.etl.pentaho.server.plugins.core.utils.transformation.IStepMetaResourceNormalizer;
import org.pentaho.di.trans.step.BaseStepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.pentaho.di.trans.steps.excelinput.ExcelInputMeta;

/**
 * Created by Mduduzi on 11/22/13.
 */
public class ExcelInputMetaResourceNormalizer implements IStepMetaResourceNormalizer {

    @Override
    public StepMetaInterface normalize(StepMetaInterface stepMeta) throws Exception {
        String internalFileURI = ((ExcelInputMeta) stepMeta).getFileName()[0];
        ((ExcelInputMeta) stepMeta).getFileName()[0] = BaseDelegateResource.getFileEntryWebDavURI(internalFileURI).toString();
        return stepMeta;
    }
}
