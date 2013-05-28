package org.flowframe.ui.component.domain.custom.provider.presenter;

import java.util.Map;

public interface ICustomContentPresenter {
	/**
	 * Get a renderable component specific to the UI framework which corresponds to this presenter.
	 * @return
	 * 			A renderable UI framework specific component
	 */
	public Object getView();
	/**
	 * Sets the data for the presenter to display in its view. The String-typed keys put in this map should correspond to the map returned by <code>getParamKeyMap();</code>.
	 * @param data
	 * 			The data map
	 * @throws Exception 
	 */
	public void setData(Map<String, Object> data) throws Exception;
	/**
	 * Returns the parameter names and types that this presenter requires.
	 * @return
	 * 			A map with the parameter names and types
	 */
	public Map<Class<?>, String> getParamKeyMap();
}
