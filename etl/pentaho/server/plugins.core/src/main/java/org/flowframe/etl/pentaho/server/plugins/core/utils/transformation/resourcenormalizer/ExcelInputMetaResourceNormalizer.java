package org.flowframe.etl.pentaho.server.plugins.core.utils.transformation.resourcenormalizer;

import org.flowframe.documentlibrary.remote.services.IRemoteDocumentRepository;
import org.flowframe.etl.pentaho.server.plugins.core.exception.TransConversionException;
import org.flowframe.etl.pentaho.server.plugins.core.utils.repository.doclib.DocLibUtil;
import org.flowframe.etl.pentaho.server.plugins.core.utils.transformation.IStepMetaResourceNormalizer;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.pentaho.di.trans.steps.excelinput.ExcelInputMeta;

import java.net.URI;
import java.util.Map;

/**
 * Created by Mduduzi on 11/22/13.
 */
public class ExcelInputMetaResourceNormalizer implements IStepMetaResourceNormalizer {

    @Override
    public StepMetaInterface normalize(StepMetaInterface stepMeta, Map<String,Object> options) throws TransConversionException {
        try {
            String internalFileURI = ((ExcelInputMeta) stepMeta).getFileName()[0];
            ((ExcelInputMeta) stepMeta).getFileName()[0] = DocLibUtil.getFileEntryWebDavURI((IRemoteDocumentRepository)options.get("docRepositoryService"),new URI(internalFileURI)).toString();
            return stepMeta;
        } catch (Exception e) {
            throw new TransConversionException("Error externalizing stepMeta["+((ExcelInputMeta) stepMeta).getName()+"]",e);
        }
    }
}
