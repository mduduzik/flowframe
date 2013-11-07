package org.flowframe.etl.pentaho.server.plugins.core.model.json.jackson.mixin.steps;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by Mduduzi on 11/7/13.
 */
@JsonIgnoreProperties({"changed",
        "databases",
        "repository",
        "parentStepMeta",
        "ioMeta",
        "stepData",
        "stepIOMeta",
        "xml",
        /*BaseStepMeta*/
        "changed",
        "databases",
        "repository",
        "parentStepMeta",
        "ioMeta",
        "log",
        "stepIOMeta",
        "optionalStreams",
        "stepMetaInjectionInterface",
        "requiredFields"})
public abstract class TextFileInputMetaMixIn  {
}
