package org.flowframe.ui.vaadin.common.mvp.view.feature;

import java.util.HashMap;
import java.util.Map;

import org.flowframe.kernel.common.mdm.domain.application.DocViewFeature;
import org.flowframe.kernel.common.mdm.domain.application.Feature;
import org.flowframe.kernel.common.mdm.domain.application.ReportViewFeature;
import org.flowframe.kernel.common.mdm.domain.documentlibrary.FileEntry;
import org.flowframe.kernel.common.mdm.domain.user.User;
import org.flowframe.kernel.common.utils.Validator;
import org.flowframe.ui.component.domain.AbstractComponent;
import org.flowframe.ui.services.contribution.ITaskActionContribution;
import org.flowframe.ui.services.contribution.IViewContribution;
import org.flowframe.ui.services.factory.IEntityEditorFactory;
import org.flowframe.ui.vaadin.common.mvp.LaunchableViewEventBus;
import org.flowframe.ui.vaadin.common.mvp.MainMVPApplication;
import org.flowframe.ui.vaadin.common.mvp.docviewer.DocViewerPresenter;
import org.flowframe.ui.vaadin.common.mvp.reportviewer.ReportViewerPresenter;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.presenter.IPresenter;
import org.vaadin.mvp.presenter.IPresenterFactory;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class FeatureTabbedView extends TabSheet implements IFeatureView {
	private static final long serialVersionUID = 1987628349762L;

	private HashMap<Feature, Component> viewCache;
	private Feature currentFeature;
	private MainMVPApplication app;
	private IPresenter<?, ? extends EventBus> viewPresenter;

	private HashMap<String, Object> entityFactoryPresenterParams;

	private User currentUser = null;

	public FeatureTabbedView(MainMVPApplication app, IPresenter<?, ? extends EventBus> viewPresenter) {
		this.app = app;
		this.currentUser = this.app.getCurrentUser();
		this.viewPresenter = viewPresenter;
		this.viewCache = new HashMap<Feature, Component>();

		this.entityFactoryPresenterParams = new HashMap<String, Object>();
		this.entityFactoryPresenterParams.putAll(app.provideEntityEditorFactoryParams());

		setSizeFull();
		setStyleName("conx-entity-editor-detail-tabsheet");
	}

	public void clearFeature(Feature feature) {
		Component component = viewCache.get(feature);
		if (component != null) {
			this.removeComponent(component);
			viewCache.remove(feature);
		}
	}

	public void setFeature(Feature feature) {
		if (feature != currentFeature) {
			if (Validator.isNotNull(feature.getCode())) {
				currentFeature = feature;
				Component componentFeature = getComponentFor(feature);
				if (getTab(componentFeature) != null) {
					this.setSelectedTab(componentFeature);
				} else {
					if (feature.getCaption() != null) {
						addTab(componentFeature, feature.getCaption(), new ThemeResource(feature.getIconUrl()));
					} else {
						addTab(componentFeature, feature.getName());
					}
				}
			} else if (feature instanceof DocViewFeature) {
				currentFeature = feature;
				Component view = viewCache.get(feature);
				FileEntry fe = ((DocViewFeature) feature).getFileEntry();
				ThemeResource rc = null;
				if (view == null) {
					IPresenterFactory pf = this.app.getPresenterFactory();
					DocViewerPresenter viewPresenter = (DocViewerPresenter) pf.createPresenter(DocViewerPresenter.class);
					view = (Component) viewPresenter.getView();

					viewPresenter.getEventBus().viewDocument(fe);

					rc = new ThemeResource("icons/mimetype/attachment-generic.png");
					Tab tab = addTab(view, feature.getName(), rc);
					tab.setClosable(true);
					this.setSelectedTab(view);
					viewCache.put(feature, view);
				} else {
					if (getTab(view) != null) {
						this.setSelectedTab(view);
					} else {
						rc = new ThemeResource("icons/mimetype/attachment-generic.png");
						Tab tab = addTab(view, feature.getName(), rc);
						this.setSelectedTab(view);
						tab.setClosable(true);
						viewCache.put(feature, view);
					}
				}
			} else if (feature instanceof ReportViewFeature) {
				currentFeature = feature;
				Component view = viewCache.get(feature);
				ThemeResource rc = null;
				if (view == null) {
					IPresenterFactory pf = this.app.getPresenterFactory();
					ReportViewerPresenter viewPresenter = (ReportViewerPresenter) pf.createPresenter(ReportViewerPresenter.class);
					viewPresenter.getEventBus().viewReport(((ReportViewFeature) feature).getReportUrl());
					view = (Component) viewPresenter.getView();

					rc = new ThemeResource("toolstrip/img/label.png");
					Tab tab = addTab((Component) viewPresenter.getView(), feature.getCaption(), rc);
					tab.setClosable(true);
					this.setSelectedTab(view);
					viewCache.put(feature, view);
				} else {
					if (getTab(view) != null) {
						this.setSelectedTab(view);
					} else {
						rc = new ThemeResource("toolstrip/img/label.png");
						Tab tab = addTab(view, feature.getName(), rc);
						this.setSelectedTab(view);
						tab.setClosable(true);
						viewCache.put(feature, view);
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private Component getComponentFor(Feature feature) {
		Component view = null;
		if (feature.isTaskFeature()) {
			// IUIContributionManager cm = this.app.getUiContributionManager();
			String viewCode = feature.getExternalCode();
			String processId = feature.getCode();

			ITaskActionContribution ac = (ITaskActionContribution) app.getActionContributionByCode(viewCode);

			if (viewCode != null && processId != null && ac != null) {
				Map<String, Object> props = new HashMap<String, Object>();
				props.put("userId", this.currentUser.getScreenName());
				props.put("processId", processId);
				if (this.app != null) {
					props.put("daoProvider", this.app.getDaoProvider());
				}
				props.put("feature", feature);
				props.put("onCompletionFeature", feature.getOnCompletionFeature());
				props.put("onCompletionViewPresenter", this.viewPresenter);
				props.put("appPresenter", this.viewPresenter);

				try {
					view = ac.execute(props);
					viewCache.put(feature, view);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else if (Validator.isNotNull(feature.getCode())) {
			view = viewCache.get(feature);
			if (view == null) {
				// IUIContributionManager cm =
				// this.app.getUiContributionManager();
				String viewCode = feature.getCode();
				IViewContribution vc = app.getViewContributionByCode(viewCode);
				if (vc != null) {
					try {
						// FIXME: getComponentModel needs a real props map
						AbstractComponent componentModel = vc.getComponentModel(null);
						if (Validator.isNotNull(componentModel)) {
							VerticalLayout viewContainer = new VerticalLayout();
							viewContainer.setSizeFull();

							this.entityFactoryPresenterParams.put(IEntityEditorFactory.FACTORY_PARAM_MVP_CURRENT_APP_PRESENTER, this.viewPresenter);
							this.entityFactoryPresenterParams.put(IEntityEditorFactory.FACTORY_PARAM_MVP_EDITOR_CONTAINER, viewContainer);
							Map<IPresenter<?, ? extends EventBus>, EventBus> mvp = (Map<IPresenter<?, ? extends EventBus>, EventBus>) this.app
									.getEntityEditorFactory().create(componentModel, this.entityFactoryPresenterParams);
							IPresenter<?, ? extends EventBus> viewPresenter = mvp.keySet().iterator().next();

							Component newView = (Component) viewPresenter.getView();
							viewContainer.addComponent(newView);
							viewContainer.setExpandRatio(newView, 1.0f);

							view = viewContainer;
						} else {
							IPresenterFactory pf = this.app.getPresenterFactory();
							IPresenter<?, ? extends EventBus> viewPresenter = pf.createPresenter(vc.getPresenterClass());
							((LaunchableViewEventBus) viewPresenter.getEventBus()).launch(this.app);

							view = (Component) viewPresenter.getView();
						}
						view.setSizeFull();
					} catch (Exception e) {
						e.printStackTrace();
					}
					viewCache.put(feature, view);
				}
			}

		}
		return view;
	}
}
