package org.flowframe.etl.pentaho.server.plugins.core.model.json.filter.trans.steps;

import org.codehaus.jackson.map.annotate.JsonFilter;

@JsonFilter("SharedObjects")
public abstract class SharedObjectsPropertyFilterMixIn {
    public static String[] ignorableFieldNames = {"objectsMap"};
}
