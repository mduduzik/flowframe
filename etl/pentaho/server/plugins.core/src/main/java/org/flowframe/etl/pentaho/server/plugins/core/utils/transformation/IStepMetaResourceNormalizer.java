package org.flowframe.etl.pentaho.server.plugins.core.utils.transformation;

import org.flowframe.etl.pentaho.server.plugins.core.exception.TransConversionException;
import org.pentaho.di.trans.step.StepMetaInterface;

import java.util.Map;

/**
 * Created by Mduduzi on 11/22/13.
 */
public interface IStepMetaResourceNormalizer {
    public StepMetaInterface normalize(StepMetaInterface stepMeta,  Map<String,Object> options) throws TransConversionException;
}
