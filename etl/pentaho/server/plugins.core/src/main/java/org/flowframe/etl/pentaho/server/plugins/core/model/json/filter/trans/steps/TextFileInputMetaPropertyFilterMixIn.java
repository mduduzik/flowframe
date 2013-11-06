package org.flowframe.etl.pentaho.server.plugins.core.model.json.filter.trans.steps;

import org.codehaus.jackson.map.annotate.JsonFilter;

@JsonFilter("TextFileInputMeta")
public class TextFileInputMetaPropertyFilterMixIn {
    public static String[] ignorableFieldNames = {"xml","fileFormatTypeNr","fileTypeNr","log","stepData","stepIOMeta","requiredFields"};
}
