package org.flowframe.etl.pentaho.server.plugins.core.utils.transformation;

import org.pentaho.di.trans.step.StepMetaInterface;

/**
 * Created by Mduduzi on 11/22/13.
 */
public interface IStepMetaResourceNormalizer {
    public StepMetaInterface normalize(StepMetaInterface stepMeta) throws Exception;
}
