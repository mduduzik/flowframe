package org.flowframe.etl.pentaho.server.plugins.core;

import org.flowframe.etl.pentaho.server.plugins.core.resource.BaseResource;
import org.pentaho.di.core.plugins.PluginInterface;

/**
 * Created by Mduduzi on 8/31/13.
 */
public interface IPluginInterface extends PluginInterface {
    public java.lang.Class<? extends BaseResource> getRESTResourceType();
}
