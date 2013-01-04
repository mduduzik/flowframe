package org.flowframe.bpm.jbpm.pageflow.services;

import java.util.Map;

public interface IPageFlowListener {
	public void onNext(Object currentPage, Map<String, Object> state);
	public void onPrevious(Object currentPage, Map<String, Object> state);
}
