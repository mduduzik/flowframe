package com.conx.bi.etl.services.plugins;

import java.util.List;


public interface IETLPluginsManager {
	public List<ETLPluginTypeInfo> getRegisteredStepEntryPlugins() throws Exception;
	public List<ETLPluginTypeInfo> getRegisteredJobEntryPlugins() throws Exception;
}
