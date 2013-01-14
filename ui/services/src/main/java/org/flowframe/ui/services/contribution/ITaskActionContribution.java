package org.flowframe.ui.services.contribution;

import java.util.Map;

import com.vaadin.Application;
import com.vaadin.ui.Component;

public interface ITaskActionContribution extends IActionContribution {
	public Component execute(Map<String,Object> properties) throws Exception;
}
