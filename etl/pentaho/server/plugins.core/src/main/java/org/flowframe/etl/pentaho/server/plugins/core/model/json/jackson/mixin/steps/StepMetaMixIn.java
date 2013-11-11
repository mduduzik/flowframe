package org.flowframe.etl.pentaho.server.plugins.core.model.json.jackson.mixin.steps;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by Mduduzi on 11/7/13.
 */
@JsonIgnoreProperties({"xml","copies","changed","partitioned","stepMetaInterface","location","stepPartitioningMeta","targetStepPartitioningMeta","clusterSchema","stepErrorMeta","remoteInputSteps","remoteOutputSteps","parentTransMeta"})
public abstract class StepMetaMixIn {
}