package org.flowframe.ui.services.contribution;

import org.flowframe.kernel.common.mdm.domain.application.Feature;
import org.flowframe.ui.component.domain.AbstractComponent;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.presenter.BasePresenter;

import com.vaadin.Application;

public interface IViewContribution {
	public AbstractComponent  getComponentModel(Application application, Feature feature);

	public String getIcon();

	public String getName();
	
	public String getCode();

	public Class<? extends BasePresenter<?, ? extends EventBus>> getPresenterClass();
}
