package org.flowframe.ui.pageflow.vaadin.mvp;

import java.util.Map;

public interface IPageDataHandler {
	public void setParameterData(PagePresenter source, Map<String, Object> params);
	public Object getResultData(PagePresenter source);
}
