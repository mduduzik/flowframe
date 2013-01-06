package org.flowframe.ui.services.contribution;

import com.vaadin.Application;

public interface IActionContribution {
	String getIcon();
	String getText();
	String getCode();
	void execute(Application application);
}
