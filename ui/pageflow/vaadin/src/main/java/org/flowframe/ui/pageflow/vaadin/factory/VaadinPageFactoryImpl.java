package org.flowframe.ui.pageflow.vaadin.factory;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.flowframe.ui.component.domain.AbstractComponent;
import org.flowframe.ui.component.domain.attachment.AttachmentEditorComponent;
import org.flowframe.ui.component.domain.editor.MultiLevelEntityEditorComponent;
import org.flowframe.ui.component.domain.form.CollapseableSectionFormComponent;
import org.flowframe.ui.component.domain.form.CollapsibleConfirmActualsFormComponent;
import org.flowframe.ui.component.domain.form.CollapsiblePhysicalAttributeConfirmActualsFormComponent;
import org.flowframe.ui.component.domain.form.EntityPreferenceFormComponent;
import org.flowframe.ui.component.domain.form.FormComponent;
import org.flowframe.ui.component.domain.form.SimpleFormComponent;
import org.flowframe.ui.component.domain.masterdetail.LineEditorComponent;
import org.flowframe.ui.component.domain.masterdetail.LineEditorContainerComponent;
import org.flowframe.ui.component.domain.masterdetail.MasterDetailComponent;
import org.flowframe.ui.component.domain.note.NoteEditorComponent;
import org.flowframe.ui.component.domain.page.TaskPageComponent;
import org.flowframe.ui.component.domain.preferences.PreferencesEditorComponent;
import org.flowframe.ui.component.domain.referencenumber.ReferenceNumberEditorComponent;
import org.flowframe.ui.component.domain.search.SearchGridComponent;
import org.flowframe.ui.component.domain.table.GridComponent;
import org.flowframe.ui.component.domain.table.MatchGridComponent;
import org.flowframe.ui.pageflow.services.ICustomDrivenPageFlowPage;
import org.flowframe.ui.pageflow.services.IModelDrivenPageFlowPage;
import org.flowframe.ui.pageflow.services.IPageComponent;
import org.flowframe.ui.pageflow.services.IPageDataBuilder;
import org.flowframe.ui.pageflow.services.IPageFactory;
import org.flowframe.ui.pageflow.services.IPageFlowPage;
import org.flowframe.ui.pageflow.vaadin.builder.VaadinPageDataBuilder;
import org.flowframe.ui.pageflow.vaadin.contribution.PageFactoryContributionManager;
import org.flowframe.ui.pageflow.vaadin.ext.form.VaadinCollapsibleConfirmActualsForm;
import org.flowframe.ui.pageflow.vaadin.ext.form.VaadinCollapsiblePhysicalAttributeConfirmActualsForm;
import org.flowframe.ui.pageflow.vaadin.ext.grid.VaadinMatchGrid;
import org.flowframe.ui.pageflow.vaadin.ext.mvp.IConfigurablePresenter;
import org.flowframe.ui.pageflow.vaadin.mvp.IPageDataHandler;
import org.flowframe.ui.pageflow.vaadin.mvp.PageContentEventBus;
import org.flowframe.ui.pageflow.vaadin.mvp.PagePresenter;
import org.flowframe.ui.pageflow.vaadin.mvp.editor.MasterSectionPresenter;
import org.flowframe.ui.pageflow.vaadin.mvp.editor.form.EditorFormPresenter;
import org.flowframe.ui.pageflow.vaadin.mvp.editor.form.header.EditorFormHeaderPresenter;
import org.flowframe.ui.pageflow.vaadin.mvp.editor.grid.EditorGridPresenter;
import org.flowframe.ui.pageflow.vaadin.mvp.editor.multilevel.MultiLevelEditorPresenter;
import org.flowframe.ui.pageflow.vaadin.mvp.lineeditor.EntityLineEditorPresenter;
import org.flowframe.ui.pageflow.vaadin.mvp.lineeditor.section.EntityLineEditorSectionPresenter;
import org.flowframe.ui.pageflow.vaadin.mvp.lineeditor.section.attachment.AttachmentEditorPresenter;
import org.flowframe.ui.pageflow.vaadin.mvp.lineeditor.section.form.EntityLineEditorFormPresenter;
import org.flowframe.ui.pageflow.vaadin.mvp.lineeditor.section.form.header.EntityLineEditorFormHeaderPresenter;
import org.flowframe.ui.pageflow.vaadin.mvp.lineeditor.section.grid.EntityLineEditorGridPresenter;
import org.flowframe.ui.pageflow.vaadin.mvp.lineeditor.section.grid.header.EntityLineEditorGridHeaderPresenter;
import org.flowframe.ui.pageflow.vaadin.mvp.lineeditor.section.notes.NotesEditorPresenter;
import org.flowframe.ui.pageflow.vaadin.mvp.lineeditor.section.preferences.PreferencesEditorPresenter;
import org.flowframe.ui.pageflow.vaadin.mvp.lineeditor.section.refnum.ReferenceNumberEditorPresenter;
import org.flowframe.ui.services.factory.IComponentFactory;
import org.flowframe.ui.vaadin.addons.common.FlowFrameAbstractSplitPanel.ISplitPositionChangeListener;
import org.flowframe.ui.vaadin.addons.common.FlowFrameVerticalSplitPanel;
import org.flowframe.ui.vaadin.editors.entity.vaadin.ext.search.EntityGrid;
import org.flowframe.ui.vaadin.editors.entity.vaadin.ext.search.EntitySearchGrid;
import org.flowframe.ui.vaadin.forms.impl.VaadinCollapsibleSectionForm;
import org.flowframe.ui.vaadin.forms.impl.VaadinEntityPreferenceForm;
import org.flowframe.ui.vaadin.forms.impl.VaadinSimpleForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.EventBusManager;
import org.vaadin.mvp.presenter.IPresenter;
import org.vaadin.mvp.presenter.PresenterFactory;

import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

public class VaadinPageFactoryImpl implements IPageFactory {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	protected PresenterFactory presenterFactory;
	protected Map<String, Object> config;
	protected IPageDataBuilder dataBuilder;
	protected boolean initialized = false;

	@Autowired
	protected PageFactoryContributionManager pageFactoryContributionManager;

	public VaadinPageFactoryImpl(Map<String, Object> config) {
		this.config = config;
		this.dataBuilder = createPageDataBuilder();
		this.presenterFactory = new PresenterFactory(new EventBusManager(), Locale.getDefault());
		this.initialized = true;
	}

	public VaadinPageFactoryImpl(Map<String, Object> config, PresenterFactory presenterFactory, PageFactoryContributionManager pageFactoryContributionManager) {
		this.pageFactoryContributionManager = pageFactoryContributionManager;
		this.config = config;
		this.dataBuilder = createPageDataBuilder();
		this.presenterFactory = presenterFactory;
		if (this.presenterFactory == null)
			this.presenterFactory = new PresenterFactory(new EventBusManager(), Locale.getDefault());

		this.initialized = true;
	}

	@Override
	public void init(Map<String, Object> config) {
		if (!this.initialized) {
			this.config = config;
			this.presenterFactory = new PresenterFactory(new EventBusManager(), Locale.getDefault());
			this.initialized = true;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.flowframe.ui.pageflow.services.IPageFactory#createComponent
	 * (org.flowframe.ui.component.domain.AbstractComponent)
	 */
	@Override
	public Component createComponent(AbstractComponent componentModel) {
		assert componentModel != null : "componentModel must be not null";
		IPresenter<?, ? extends EventBus> presenter = null;
		if (componentModel instanceof MasterDetailComponent) {
			FlowFrameVerticalSplitPanel splitPanel = new FlowFrameVerticalSplitPanel();
			splitPanel.setSizeFull();
			splitPanel.setSplitPosition(50);

			final Component firstComponent = create(((MasterDetailComponent) componentModel).getMasterComponent());
			final Component secondComponent = create(((MasterDetailComponent) componentModel).getLineEditorPanel());

			splitPanel.setFirstComponent(firstComponent);
			splitPanel.setSecondComponent(secondComponent);

			splitPanel.addSplitPositionChangeListener(new ISplitPositionChangeListener() {

				@Override
				public void onSplitPositionChanged(Integer newPos, int posUnit, boolean posReversed) {
				}

				@Override
				public void onSecondComponentHeightChanged(int height) {
				}

				@Override
				public void onFirstComponentHeightChanged(int height) {
					if (height > 0) {
						firstComponent.setHeight(height, com.vaadin.terminal.Sizeable.UNITS_PIXELS);
					}
				}
			});

			return splitPanel;
		} else if (componentModel instanceof SearchGridComponent) {
			EntitySearchGrid component = new EntitySearchGrid((SearchGridComponent) componentModel);
			component.setStatusEnabled(true);
			return component;
		} else if (componentModel instanceof GridComponent) {
			EntityGrid component = new EntityGrid((GridComponent) componentModel);
			component.setStatusEnabled(true);
			return component;
		} 
		else if (componentModel instanceof CollapsibleConfirmActualsFormComponent) {
			return new VaadinCollapsibleConfirmActualsForm((CollapsibleConfirmActualsFormComponent) componentModel);
		} else if (componentModel instanceof CollapseableSectionFormComponent) {
			return new VaadinCollapsibleSectionForm((CollapseableSectionFormComponent) componentModel);
		} else if (componentModel instanceof MatchGridComponent) {
			return new VaadinMatchGrid((MatchGridComponent) componentModel, pageFactoryContributionManager.getVaadinPageDataBuilder());
		} else if (componentModel instanceof CollapsiblePhysicalAttributeConfirmActualsFormComponent) {
			return new VaadinCollapsiblePhysicalAttributeConfirmActualsForm((CollapsiblePhysicalAttributeConfirmActualsFormComponent) componentModel);
		} else if (componentModel instanceof EntityPreferenceFormComponent) {
			return new VaadinEntityPreferenceForm();
		} else if (componentModel instanceof SimpleFormComponent) {
			return new VaadinSimpleForm((SimpleFormComponent) componentModel);
		} else
			throw new IllegalArgumentException(componentModel == null ? "componentModel is null" : componentModel.getCaption() + "is not supported");
		// FIXME this should return null
		// return new VerticalLayout();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.flowframe.ui.pageflow.services.IPageFactory#create(org.flowframe
	 * .ui.component.domain.AbstractComponent)
	 */
	@Override
	public Component create(AbstractComponent componentModel) {
		try {
			if (correspondsToPresenter(componentModel)) {
				IPresenter<?, ? extends EventBus> presenter = createPresenter(componentModel, new HashMap<String, Object>(config));
				return (Component) presenter.getView();
			} else {
				return createComponent(componentModel);
			}
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new VerticalLayout();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.flowframe.ui.pageflow.services.IPageFactory#createPresenter
	 * (org.flowframe.ui.component.domain.AbstractComponent, java.util.Map)
	 */
	@Override
	public IPresenter<?, ? extends EventBus> createPresenter(AbstractComponent componentModel, Map<String, Object> params) throws Exception {
		params.put(IComponentFactory.COMPONENT_MODEL, componentModel);
		params.put(IComponentFactory.VAADIN_COMPONENT_FACTORY, this);

		IPresenter<?, ? extends EventBus> presenter = null;
		if (this.pageFactoryContributionManager.getPageFactoryContributionsMap().containsKey(componentModel.getClass())) {
			presenter = presenterFactory.createPresenter(this.pageFactoryContributionManager.getPageFactoryContributionsMap().get(componentModel.getClass()));
		} else if (componentModel instanceof LineEditorContainerComponent) {
			presenter = presenterFactory.createPresenter(EntityLineEditorPresenter.class);
		} else if (componentModel instanceof LineEditorComponent) {
			presenter = presenterFactory.createPresenter(EntityLineEditorSectionPresenter.class);
		} else if (componentModel instanceof MultiLevelEntityEditorComponent) {
			presenter = presenterFactory.createPresenter(MultiLevelEditorPresenter.class);
		} else if (FormComponent.class.isAssignableFrom(componentModel.getClass()) || GridComponent.class.isAssignableFrom(componentModel.getClass())) {
			presenter = presenterFactory.createPresenter(MasterSectionPresenter.class);
		} else
			throw new IllegalArgumentException(componentModel == null ? "componentModel is null" : componentModel.getCaption() + "is not supported");

		if (presenter instanceof IConfigurablePresenter) {
			((IConfigurablePresenter) presenter).onConfigure(params);
		}

		return presenter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.flowframe.ui.pageflow.services.IPageFactory#
	 * createMasterSectionHeaderPresenter
	 * (org.flowframe.ui.component.domain.AbstractComponent)
	 */
	@Override
	public IPresenter<?, ? extends EventBus> createMasterSectionHeaderPresenter(AbstractComponent componentModel) throws Exception {
		IPresenter<?, ? extends EventBus> presenter = null;
		if (FormComponent.class.isAssignableFrom(componentModel.getClass())) {
			presenter = this.presenterFactory.createPresenter(EditorFormHeaderPresenter.class);
		} else if (GridComponent.class.isAssignableFrom(componentModel.getClass())) {
			return null;
		}

		return presenter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.flowframe.ui.pageflow.services.IPageFactory#
	 * createMasterSectionContentPresenter
	 * (org.flowframe.ui.component.domain.AbstractComponent, java.util.Map)
	 */
	@Override
	public IPresenter<?, ? extends EventBus> createMasterSectionContentPresenter(AbstractComponent componentModel, Map<String, Object> params) throws Exception {
		params.put(IComponentFactory.COMPONENT_MODEL, componentModel);
		params.put(IComponentFactory.VAADIN_COMPONENT_FACTORY, this);

		IPresenter<?, ? extends EventBus> presenter = null;
		if (FormComponent.class.isAssignableFrom(componentModel.getClass())) {
			presenter = presenterFactory.createPresenter(EditorFormPresenter.class);
		} else if (GridComponent.class.isAssignableFrom(componentModel.getClass())) {
			presenter = presenterFactory.createPresenter(EditorGridPresenter.class);
		}

		if (presenter instanceof IConfigurablePresenter) {
			((IConfigurablePresenter) presenter).onConfigure(params);
		}

		return presenter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.flowframe.ui.pageflow.services.IPageFactory#
	 * createLineEditorSectionContentPresenter
	 * (org.flowframe.ui.component.domain.AbstractComponent, java.util.Map)
	 */
	@Override
	public IPresenter<?, ? extends EventBus> createLineEditorSectionContentPresenter(AbstractComponent componentModel, Map<String, Object> params) throws Exception {
		params.put(IComponentFactory.COMPONENT_MODEL, componentModel);
		params.put(IComponentFactory.VAADIN_COMPONENT_FACTORY, this);

		IPresenter<?, ? extends EventBus> presenter = null;
		if (this.pageFactoryContributionManager.getPageFactoryContributionsMap().containsKey(componentModel.getClass())) {
			presenter = presenterFactory.createPresenter(this.pageFactoryContributionManager.getPageFactoryContributionsMap().get(componentModel.getClass()));
		} else if (componentModel instanceof AttachmentEditorComponent) {
			presenter = presenterFactory.createPresenter(AttachmentEditorPresenter.class);
		} else if (componentModel instanceof ReferenceNumberEditorComponent) {
			presenter = presenterFactory.createPresenter(ReferenceNumberEditorPresenter.class);
		} else if (componentModel instanceof PreferencesEditorComponent) {
			presenter = presenterFactory.createPresenter(PreferencesEditorPresenter.class);
		} else if (componentModel instanceof NoteEditorComponent) {
			presenter = presenterFactory.createPresenter(NotesEditorPresenter.class);
		} else if (GridComponent.class.isAssignableFrom(componentModel.getClass())) {
			presenter = presenterFactory.createPresenter(EntityLineEditorGridPresenter.class);
		} else if (FormComponent.class.isAssignableFrom(componentModel.getClass())) {
			presenter = presenterFactory.createPresenter(EntityLineEditorFormPresenter.class);
		} else
			throw new IllegalArgumentException(componentModel == null ? "createLineEditorSectionContentPresenter:componentModel is null"
					: "createLineEditorSectionContentPresenter:" + componentModel.getCaption() + "is not supported");

		if (presenter instanceof IConfigurablePresenter) {
			((IConfigurablePresenter) presenter).onConfigure(params);
		}

		return presenter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.flowframe.ui.pageflow.services.IPageFactory#
	 * createLineEditorSectionHeaderPresenter
	 * (org.flowframe.ui.component.domain.AbstractComponent)
	 */
	@Override
	public IPresenter<?, ? extends EventBus> createLineEditorSectionHeaderPresenter(AbstractComponent componentModel) throws Exception {
		IPresenter<?, ? extends EventBus> presenter = null;
		if (FormComponent.class.isAssignableFrom(componentModel.getClass())) {
			presenter = this.presenterFactory.createPresenter(EntityLineEditorFormHeaderPresenter.class);
		} else if (GridComponent.class.isAssignableFrom(componentModel.getClass())) {
			presenter = this.presenterFactory.createPresenter(EntityLineEditorGridHeaderPresenter.class);
		}

		return presenter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.flowframe.ui.pageflow.services.IPageFactory#createPage(org
	 * .flowframe.bpm.jbpm.ui.pageflow.services.IPageFlowPage, java.util.Map)
	 */
	@Override
	public IPageComponent createPage(final IPageFlowPage page, Map<String, Object> initParams) {
		if (isInstanceOf(page, IModelDrivenPageFlowPage.class)) {
			PagePresenter pagePresenter = (PagePresenter) presenterFactory.createPresenter(PagePresenter.class);
			pagePresenter.setPage(page);
			this.config.putAll(initParams);

			AbstractComponent componentModel = ((TaskPageComponent) ((IModelDrivenPageFlowPage) page).getComponentModel()).getContent();
			Component contentComponent = null;
			try {
				contentComponent = create(componentModel);
			} catch (IllegalArgumentException ie) {// Must be a custom page
													// component
				if (this.pageFactoryContributionManager.getPageFactoryContributionsMap().containsKey(componentModel.getClass())) {
					IPresenter<?, ? extends EventBus> presenter = presenterFactory.createPresenter(this.pageFactoryContributionManager.getPageFactoryContributionsMap().get(
							componentModel.getClass()));
					pagePresenter.setContentPresenter(presenter);
					if (presenter instanceof IConfigurablePresenter) {
						try {
							((IConfigurablePresenter) presenter).onConfigure(config);
						} catch (Exception e) {
							throw new RuntimeException("Error creating presenter for (" + componentModel + ")", e);
						}
					}
					contentComponent = (Component) presenter.getView();
				} else {
					throw new IllegalArgumentException("Component Model(" + componentModel + ") couldn't be translate into a Vaadin Component");
				}
			}
			pagePresenter.setPageContent(contentComponent);
			pagePresenter.setDataHandler(createPageDataHandler(getDataBuilder()));
			pagePresenter.init(this.config);

			return pagePresenter;
		} else if (isInstanceOf(page, ICustomDrivenPageFlowPage.class)) {
			IPageComponent pageComponent = ((ICustomDrivenPageFlowPage) page).getPageComponent();
			return pageComponent;
		} else {
			return null;
		}
	}

	protected VaadinPageDataBuilder createPageDataBuilder() {
		return new VaadinPageDataBuilder();
	}

	protected IPageDataHandler createPageDataHandler(IPageDataBuilder dataBuilder) {
		return new PagePresenterDataHandler(dataBuilder);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.flowframe.ui.pageflow.services.IPageFactory#isInstanceOf
	 * (org.flowframe.ui.pageflow.services.IPageFlowPage,
	 * java.lang.Class)
	 */
	@Override
	public boolean isInstanceOf(IPageFlowPage page, Class<?> type) {
		return page.getType().isAssignableFrom(type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.flowframe.ui.pageflow.services.IPageFactory#
	 * correspondsToPresenter
	 * (org.flowframe.ui.component.domain.AbstractComponent)
	 */
	@Override
	public boolean correspondsToPresenter(AbstractComponent componentModel) {
		// TODO make GridComponent correspond to a presenter again - temporarily disabling it as a presenter
		if (componentModel != null
				&& (componentModel instanceof LineEditorContainerComponent 
						|| componentModel instanceof LineEditorComponent
						|| componentModel instanceof MultiLevelEntityEditorComponent 
						|| FormComponent.class.isAssignableFrom(componentModel.getClass()) 
						|| GridComponent.class.isAssignableFrom(componentModel.getClass()))) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.flowframe.ui.pageflow.services.IPageFactory#getPresenterFactory
	 * ()
	 */
	@Override
	public PresenterFactory getPresenterFactory() {
		return this.presenterFactory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.flowframe.ui.pageflow.services.IPageFactory#getConfig()
	 */
	@Override
	public Map<String, Object> getConfig() {
		return this.config;
	}

	@Override
	public IPageDataBuilder getDataBuilder() {
		return this.dataBuilder;
	}

	protected class PagePresenterDataHandler implements IPageDataHandler {
		private IPageDataBuilder dataBuilder;

		public PagePresenterDataHandler(IPageDataBuilder dataBuilder) {
			this.dataBuilder = dataBuilder;
		}

		@Override
		public void setParameterData(PagePresenter source, Map<String, Object> params) {
			try {
				IPresenter<?, ? extends EventBus> contentPresenter = source.getContentPresenter();
				EventBus contentPresenterBus = null;
				if (contentPresenter != null && (contentPresenterBus = contentPresenter.getEventBus()) != null) {
					((PageContentEventBus) contentPresenterBus).applyPageParams(params);
				} else {
					this.dataBuilder.applyParamData(source.getConfig(), source.getPageContent(), params, VaadinPageFactoryImpl.this.presenterFactory);
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@SuppressWarnings("unchecked")
		@Override
		public Object getResultData(PagePresenter source) {
			Map<String, Object> resultDataMap = null;
			IPresenter<?, ? extends EventBus> contentPresenter = source.getContentPresenter();
			EventBus contentPresenterBus = null;
			if (contentPresenter != null && (contentPresenterBus = contentPresenter.getEventBus()) != null) {
				resultDataMap = (Map<String, Object>) ((PageContentEventBus) contentPresenterBus).getPageResults();
			} else {
				resultDataMap = new HashMap<String, Object>(this.dataBuilder.buildResultDataMap(source.getParameterData(),
						this.dataBuilder.buildResultData(source.getPageContent()), source.getPage().getResultKeyMap()));
			}
			return resultDataMap;
		}

	}
}
