package org.flowframe.etl.pentaho.server.plugins.core.model.json.introspector;

import org.codehaus.jackson.map.MapperConfig;
import org.codehaus.jackson.map.introspect.AnnotatedClass;
import org.codehaus.jackson.map.introspect.POJOPropertiesCollector;
import org.codehaus.jackson.map.introspect.POJOPropertyBuilder;
import org.codehaus.jackson.type.JavaType;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by Mduduzi on 11/6/13.
 */
public class CustomPOJOPropertiesCollector extends POJOPropertiesCollector {
    public CustomPOJOPropertiesCollector(MapperConfig<?> config, boolean forSerialization, JavaType type, AnnotatedClass classDef) {
        super(config, forSerialization, type, classDef);
    }

    /**
     * Method called to get rid of candidate properties that are marked
     * as ignored, or that are not visible.
     */
    protected void _removeUnwantedProperties()
    {
        Iterator<Map.Entry<String,POJOPropertyBuilder>> it = _properties.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, POJOPropertyBuilder> entry = it.next();
            POJOPropertyBuilder prop = entry.getValue();

            // First: if nothing visible, just remove altogether
            if (!prop.anyVisible()) {
                it.remove();
                continue;
            }
            // Otherwise, check ignorals
            if (prop.anyIgnorals()) {
                _addIgnored(prop);
                // first: if one or more ignorals, and no explicit markers, remove the whole thing
/*                if (!prop.anyExplicitNames()) {
                    it.remove();
                    continue;
                }*/
                // otherwise just remove explicitly ignored (and retain others)
                prop.removeIgnored();
            }
            // and finally, handle removal of individual non-visible elements
            prop.removeNonVisible();
        }
    }

    private void _addIgnored(POJOPropertyBuilder prop)
    {
        // not used in any way for serialization side:
        if (_forSerialization) {
            return;
        }
        /* and with deserialization, two aspects: whether it's ok to see
         * property ('ignore for failure reporting') and whether we forcifully
         * ignore it even if there was "any setter" available.
         */
        // but do not add unless ignoral was for field, setter or ctor param
        String name = prop.getName();
        _ignoredPropertyNames = addToSet(_ignoredPropertyNames, name);
        if (prop.anyDeserializeIgnorals()) {
            _ignoredPropertyNamesForDeser = addToSet(_ignoredPropertyNamesForDeser, name);
        }
    }

    private Set<String> addToSet(Set<String> set, String str)
    {
        if (set == null) {
            set = new HashSet<String>();
        }
        set.add(str);
        return set;
    }
}
