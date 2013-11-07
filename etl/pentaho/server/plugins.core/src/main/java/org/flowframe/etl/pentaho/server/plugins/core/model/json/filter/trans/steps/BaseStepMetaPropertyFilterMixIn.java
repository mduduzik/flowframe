package org.flowframe.etl.pentaho.server.plugins.core.model.json.filter.trans.steps;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonFilter;

import java.util.concurrent.atomic.AtomicBoolean;

@JsonFilter("BaseStepMeta")
public abstract class BaseStepMetaPropertyFilterMixIn {
    public static String[] ignorableFieldNames = {
            "stepMetaInterface",
            "stepPartitioningMeta",
            "targetStepPartitioningMeta",
            "clusterSchema",
            "stepErrorMeta",
            "parentTransMeta",
            "remoteInputSteps",
            "remoteOutputSteps",
            "databases",
            "repository",
            "parentStepMeta",
            "ioMeta",
            "tableFields",
            "requiredFields",
            "objectsMap",
            "resourceDependencies",
            "sQLStatements",
            "paused","usedArguments","xml","fileFormatTypeNr","fileTypeNr","log","stepData","stepIOMeta","requiredFields","stopped","running","acceptingStep"};

    @JsonIgnore
    public abstract void setPaused(AtomicBoolean paused);
}
