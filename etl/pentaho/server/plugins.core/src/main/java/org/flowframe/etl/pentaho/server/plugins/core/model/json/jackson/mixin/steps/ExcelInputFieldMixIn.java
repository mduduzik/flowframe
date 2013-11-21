package org.flowframe.etl.pentaho.server.plugins.core.model.json.jackson.mixin.steps;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by Mduduzi on 11/7/13.
 */
@JsonIgnoreProperties({
        "typeDesc",
        "trimTypeDesc"})
public abstract class ExcelInputFieldMixIn {
}
