package org.flowframe.ui.vaadin.common.editors.ext.mvp;

import java.util.Map;

public interface IConfigurablePresenter {
	public void onConfigure(Map<String, Object> params) throws Exception;
}
