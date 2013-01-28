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

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public abstract class MultiFeatureView extends TabSheet implements IFeatureView {
	private static final long serialVersionUID = 321645322135L;

	protected HashMap<Feature, Tab> featureMap;
	protected Map<String, Object> config;

	public MultiFeatureView(Map<String, Object> config) {
		assert (config != null) : "The config map was null.";

		setSizeFull();
		setStyleName("ff-multi-feature-view");

		this.config = config;
		this.featureMap = new HashMap<Feature, TabSheet.Tab>();
	}

	public abstract IActionContribution findActionContributionByCode(String code);

	public abstract IViewContribution findViewContributionByCode(String code);

	public abstract IPresenter<?, ? extends EventBus> createPresenter(Class<?> presenterClass);

	public abstract Component createComponent(AbstractComponent component, Map<String, Object> config);
	
	public abstract ITaskWizard createTaskWizard(String processId, Map<String, Object> config);

	public void openFeature(Feature feature) {
		if (!isFeatureOpen(feature)) {
			assert (feature != null) : "Feature was null.";

			Component featureComponent = null;
			String iconUrl = null;
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
			} else {
				throw new RuntimeException("The view contribution for this feature was naked (is not a sub-implementation of IViewContribution).");
			}

			iconUrl = viewContribution.getIcon();
			assert (featureComponent != null) : "Could not create a component for feature. Feature component was null.";
			featureComponent.setSizeFull();

			if (iconUrl == null) {
				if (feature.getIconUrl() != null) {
					iconUrl = feature.getIconUrl();
				}
			}

			Tab featureTab = null;
			if (iconUrl != null) {
				featureTab = addTab(featureComponent, feature.getName(), new ThemeResource(iconUrl));
			} else {
				featureTab = addTab(featureComponent, feature.getName());
			}
			assert (featureTab != null) : "Could not add a tab for this feature.";

			this.featureMap.put(feature, featureTab);
			this.setSelectedTab(featureTab);
		}
	}

	private boolean isFeatureOpen(Feature feature) {
		return this.featureMap.containsKey(feature);
	}
}
