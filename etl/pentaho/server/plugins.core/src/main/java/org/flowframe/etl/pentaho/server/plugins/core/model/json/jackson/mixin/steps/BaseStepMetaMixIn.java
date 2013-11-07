package org.flowframe.etl.pentaho.server.plugins.core.model.json.jackson.mixin.steps;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.trans.TransMeta;

/**
 * Created by Mduduzi on 11/7/13.
 */
@JsonIgnoreProperties({"changed","databases","repository","parentStepMeta","ioMeta","log"})
public abstract class BaseStepMetaMixIn extends BaseMixIn {
    @JsonIgnore abstract String getXML();
    @JsonIgnore abstract DatabaseMeta[] getUsedDatabaseConnections();
    @JsonIgnore abstract String[] getUsedLibraries();
    @JsonIgnore abstract String getDialogClassName();
    @JsonIgnore abstract public TransMeta.TransformationType[] getSupportedTransformationTypes();
}
