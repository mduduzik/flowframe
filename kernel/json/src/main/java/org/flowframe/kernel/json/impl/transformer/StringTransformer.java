package org.flowframe.kernel.json.impl.transformer;

import java.util.Collections;
import java.util.List;

import org.flowframe.kernel.json.JSONTransformer;

import flexjson.Path;
import flexjson.TypeContext;
import flexjson.transformer.AbstractTransformer;

public class StringTransformer extends AbstractTransformer implements JSONTransformer {

    public List<String> getJavaScriptAttributes() {
        return _javaScriptAttributes;
    }

    public boolean isEventPath(Path path) {
        String parentPath = path.toString();

        if (path.length() > 1) {
            parentPath = path.getPath().get(path.length() - 2);
        }

        if (parentPath.equals(_ON) || parentPath.equals(_AFTER)) {
            return true;
        }

        return false;
    }

    public boolean isJavaScriptAttribute(String key) {
        return _javaScriptAttributes.contains(key);
    }

    public void setJavaScriptAttributes(List<String> javaScriptAttributes) {
        _javaScriptAttributes = javaScriptAttributes;
    }

    public void transform(Object object) {
        Path path = getContext().getPath();
        TypeContext typeContext = getContext().peekTypeContext();

        if (typeContext != null) {
            String propertyName = typeContext.getPropertyName();

            if (isEventPath(path) ||
                    isJavaScriptAttribute(propertyName)) {

                getContext().write((String) object);
            }
            else {
                getContext().writeQuoted((String) object);
            }
        }
        else {
            getContext().write((String) object);
        }
    }

    private static final String _AFTER = "after";

    private static final String _ON = "on";

    private List<String> _javaScriptAttributes = Collections.EMPTY_LIST;
}
