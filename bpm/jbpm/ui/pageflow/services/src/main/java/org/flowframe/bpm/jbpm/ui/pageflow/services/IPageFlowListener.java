package org.flowframe.bpm.jbpm.ui.pageflow.services;

import java.util.Map;

public interface IPageFlowListener {
	public void onNext(BasePageFlowPage currentPage, Map<String, Object> state);
	public void onPrevious(BasePageFlowPage currentPage, Map<String, Object> state);
}
