package org.flowframe.ui.vaadin.common.editors.pageflow.ext.mvp;

import java.util.Map;

public interface IConfigurablePresenter {
	public void onConfigure(Map<String, Object> params) throws Exception;
}
