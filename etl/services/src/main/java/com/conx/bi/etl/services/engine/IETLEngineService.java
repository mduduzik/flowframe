package com.conx.bi.etl.services.engine;

import java.util.List;


public interface IETLEngineService {
	public List<ETLEnginePluginType> getRegisteredStepEntryPlugins() throws Exception;
	public List<ETLEnginePluginType> getRegisteredJobEntryPlugins() throws Exception;
}
