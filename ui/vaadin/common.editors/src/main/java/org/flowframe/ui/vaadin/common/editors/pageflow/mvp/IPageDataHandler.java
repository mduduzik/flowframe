package org.flowframe.ui.vaadin.common.editors.pageflow.mvp;

import java.util.Map;

public interface IPageDataHandler {
	public void setParameterData(PagePresenter source, Map<String, Object> params);
	public Object getResultData(PagePresenter source);
}
