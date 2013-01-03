package org.flowframe.ui.services.contribution;

import org.vaadin.mvp.uibinder.UiBinderException;

import com.vaadin.Application;
import com.vaadin.ui.Component;

public interface IApplicationViewContribution extends IViewContribution {
	public Component getApplicationComponent(Application application) throws UiBinderException;
}
