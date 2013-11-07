package org.flowframe.etl.pentaho.server.plugins.core.model.json.jackson.mixin.steps;

public abstract class TextFileInputMetaPropertyFilterMixIn  {
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
}
