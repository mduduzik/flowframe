package org.flowframe.ui.vaadin.common.mvp.view.feature;

import java.util.HashMap;
import java.util.Map;

import org.flowframe.bpm.jbpm.ui.pageflow.services.ITaskWizard;
import org.flowframe.kernel.common.mdm.domain.application.Feature;
import org.flowframe.ui.component.domain.AbstractComponent;
import org.flowframe.ui.services.contribution.IActionContribution;
import org.flowframe.ui.services.contribution.IComponentModelViewContribution;
import org.flowframe.ui.services.contribution.IMVPViewContribution;
import org.flowframe.ui.services.contribution.ITaskViewContribution;
import org.flowframe.ui.services.contribution.IViewContribution;
import org.flowframe.ui.services.factory.IComponentFactory;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.presenter.IPresenter;

import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

public abstract class FeatureView extends VerticalLayout implements IFeatureView {
	private static final long serialVersionUID = 47832919823L;

	private Feature currentFeature;
	private Component currentComponent;

	private Map<String, Object> config;

	public FeatureView(Map<String, Object> config) {
		assert (config != null) : "The config map was null.";

		setSizeFull();
		setStyleName("ff-feature-view");

		this.config = config;
	}

	public abstract IActionContribution findActionContributionByCode(String code);

	public abstract IViewContribution findViewContributionByCode(String code);

	public abstract IPresenter<?, ? extends EventBus> createPresenter(Class<?> presenterClass);

	public abstract Component createComponent(AbstractComponent component, Map<String, Object> config);

	public abstract ITaskWizard createTaskWizard(String processId, Map<String, Object> config);
	
	public void setFeature(Feature feature) {
		if (this.currentFeature != feature) {
			assert (feature != null) : "Feature was null.";
			Component featureComponent = null;
			try {
				featureComponent = buildFeatureComponent(feature);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("There was a " + e.getClass().getName() + " while building the feature component.");
			}

			assert (featureComponent != null) : "Could not create a component for feature. Feature component was null.";
			featureComponent.setSizeFull();

			this.currentFeature = feature;
			this.currentComponent = featureComponent;

			this.removeAllComponents();
			this.addComponent(featureComponent);
		}
	}

	public Feature getCurrentFeature() {
		return this.currentFeature;
	}

	public Component getCurrentFeatureComponent() {
		return this.currentComponent;
	}

	public void clearFeatureComponent() {
		this.currentFeature = null;
		this.currentComponent = null;
		this.removeAllComponents();
	}

	private Component buildFeatureComponent(Feature feature) throws Exception {
		assert (feature != null) : "Feature was null.";

		Component featureComponent = null;
		String viewCode = feature.getCode();
		assert (viewCode != null) : "This feature's view code was null.";
		IViewContribution viewContribution = findViewContributionByCode(viewCode);
		assert (viewContribution != null) : "Could not find the view contribution for this feature.";

		Map<String, Object> props = new HashMap<String, Object>(this.config);
		props.put("feature", feature);
		props.put("onCompletionFeature", feature.getOnCompletionFeature());

		VerticalLayout viewContainer = new VerticalLayout();
		viewContainer.setSizeFull();

		props.put(IComponentFactory.FACTORY_PARAM_MVP_EDITOR_CONTAINER, viewContainer);
		if (viewContribution instanceof IMVPViewContribution) {
			Class<?> presenterClass = ((IMVPViewContribution) viewContribution).getPresenterClass();
			assert (presenterClass != null) : "The presenter class of view contribution for this feature was null.";
			IPresenter<?, ? extends EventBus> featurePresenter = createPresenter(presenterClass);
			assert (featurePresenter != null) : "Could not create a presenter for this feature's view contribution. The presenter wass null.";
			assert (featurePresenter.getView() instanceof Component) : "The view for the presenter of this feature's view contribution was not of type Component.";
			featureComponent = (Component) featurePresenter.getView();
		} else if (viewContribution instanceof IComponentModelViewContribution) {
			AbstractComponent componentModel = ((IComponentModelViewContribution) viewContribution).getComponentModel(props);
			assert (componentModel != null) : "The component model of the view contribution for this feature was null.";
			featureComponent = createComponent(componentModel, props);
		} else if (viewContribution instanceof ITaskViewContribution) {
			String processId = ((ITaskViewContribution) viewContribution).getProcessId();
			assert (processId != null) : "The process id of the task view contribution for this feature was null.";
			ITaskWizard taskWizard = createTaskWizard(processId, props);
			assert (taskWizard != null) : "Could not create task wizard for process id: " + processId;
			featureComponent = taskWizard.getComponent();
		} else {
			throw new RuntimeException("The view contribution for this feature was naked (is not a sub-implementation of IViewContribution).");
		}

		return featureComponent;
	}
}
