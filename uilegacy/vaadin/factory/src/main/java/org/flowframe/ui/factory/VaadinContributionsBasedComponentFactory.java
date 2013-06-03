package org.flowframe.ui.factory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.flowframe.ui.component.domain.AbstractComponent;
import org.flowframe.ui.component.domain.attachment.AttachmentEditorComponent;
import org.flowframe.ui.component.domain.editor.MultiLevelEntityEditorComponent;
import org.flowframe.ui.component.domain.form.FormComponent;
import org.flowframe.ui.component.domain.masterdetail.LineEditorComponent;
import org.flowframe.ui.component.domain.masterdetail.LineEditorContainerComponent;
import org.flowframe.ui.component.domain.masterdetail.MasterDetailComponent;
import org.flowframe.ui.component.domain.note.NoteEditorComponent;
import org.flowframe.ui.component.domain.preferences.PreferencesEditorComponent;
import org.flowframe.ui.component.domain.referencenumber.ReferenceNumberEditorComponent;
import org.flowframe.ui.component.domain.table.GridComponent;
import org.flowframe.ui.services.contribution.IComponentFactoryContributionManager;
import org.flowframe.ui.services.data.IEditorDataManager;
import org.flowframe.ui.services.factory.IComponentFactory;
import org.flowframe.ui.services.factory.IComponentModelFactory;
import org.flowframe.ui.vaadin.common.editors.data.VaadinEditorDataManager;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.ConfigurablePresenterFactory;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.MultiLevelEntityEditorPresenter;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.customizer.ConfigurablePresenterFactoryCustomizer;
import org.flowframe.ui.vaadin.common.editors.ext.mvp.IConfigurablePresenter;
import org.flowframe.ui.vaadin.common.editors.mvp.EditorContentEventBus;
import org.flowframe.ui.vaadin.common.editors.mvp.IEditorDataHandler;
import org.flowframe.ui.vaadin.common.editors.mvp.editor.MasterSectionPresenter;
import org.flowframe.ui.vaadin.common.editors.mvp.editor.form.EditorFormPresenter;
import org.flowframe.ui.vaadin.common.editors.mvp.editor.form.header.EditorFormHeaderPresenter;
import org.flowframe.ui.vaadin.common.editors.mvp.editor.multilevel.MultiLevelEditorPresenter;
import org.flowframe.ui.vaadin.common.editors.mvp.lineeditor.EntityLineEditorPresenter;
import org.flowframe.ui.vaadin.common.editors.mvp.lineeditor.section.EntityLineEditorSectionPresenter;
import org.flowframe.ui.vaadin.common.editors.mvp.lineeditor.section.attachment.AttachmentEditorPresenter;
import org.flowframe.ui.vaadin.common.editors.mvp.lineeditor.section.form.EntityLineEditorFormPresenter;
import org.flowframe.ui.vaadin.common.editors.mvp.lineeditor.section.form.header.EntityLineEditorFormHeaderPresenter;
import org.flowframe.ui.vaadin.common.editors.mvp.lineeditor.section.grid.EntityLineEditorGridPresenter;
import org.flowframe.ui.vaadin.common.editors.mvp.lineeditor.section.grid.header.EntityLineEditorGridHeaderPresenter;
import org.flowframe.ui.vaadin.common.editors.mvp.lineeditor.section.notes.NotesEditorPresenter;
import org.flowframe.ui.vaadin.common.editors.mvp.lineeditor.section.preferences.PreferencesEditorPresenter;
import org.flowframe.ui.vaadin.common.editors.mvp.lineeditor.section.refnum.ReferenceNumberEditorPresenter;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.EventBusManager;
import org.vaadin.mvp.presenter.IPresenter;


public class VaadinContributionsBasedComponentFactory implements IComponentModelFactory {

	protected ConfigurablePresenterFactory presenterFactory;
	
	protected IComponentFactoryContributionManager componentFactoryContributionManager;
	
	public VaadinContributionsBasedComponentFactory(){
	}
	
	public IComponentFactoryContributionManager getComponentFactoryContributionManagerManager() {
		return componentFactoryContributionManager;
	}

	public void setComponentFactoryContributionManager(IComponentFactoryContributionManager componentFactoryContributionManager) {
		this.componentFactoryContributionManager = componentFactoryContributionManager;
	}

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	protected Map<String, Object> config;
	protected VaadinEditorDataManager dataBuilder;
	protected boolean initialized = false;


	public VaadinContributionsBasedComponentFactory(IComponentFactoryContributionManager componentFactoryContributionManager, ConfigurablePresenterFactory factory) {
		this.dataBuilder = createPageDataBuilder();
		this.presenterFactory = factory;
		this.initialized = true;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.flowframe.ui.pageflow.services.IPageFactory#createComponent
	 * (org.flowframe.ui.component.domain.AbstractComponent)
	 */
	@Override
	public Map<IPresenter<?, ? extends EventBus>, EventBus> create(AbstractComponent componentModel, Map<String, Object> params) {
		assert componentModel != null : "componentModel must be not null";
		Map<IPresenter<?, ? extends EventBus>, EventBus> res = null;
		try {
			res = (Map<IPresenter<?, ? extends EventBus>, EventBus>)this.componentFactoryContributionManager.create(componentModel,params);
		} catch (IllegalArgumentException e) {
		}		
		
		try {
			res = new HashMap<IPresenter<?,? extends EventBus>, EventBus>();
			IPresenter<?, ? extends EventBus> pres = createPresenter(componentModel, params);
			res.put(pres, pres.getEventBus());
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			throw new IllegalArgumentException("Error create(): with model "+componentModel+"Error["+stacktrace+"]");
		}
		
		return res;
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
		//params.put(IComponentFactory.COMPONENT_MODEL, componentModel);
		params.put(IComponentFactory.VAADIN_COMPONENT_FACTORY, this);
		//params.put(IComponentFactory.FACTORY_PARAM_MVP_COMPONENT_MODEL, componentModel);

		Map<IPresenter<?, ? extends EventBus>, EventBus> res = null;
		IPresenter<?, ? extends EventBus> presenter = null;
		try {
			res = (Map<IPresenter<?, ? extends EventBus>, EventBus>)this.componentFactoryContributionManager.create(componentModel,params);
		} catch (IllegalArgumentException e) {
		}	
		if (res != null && !res.isEmpty())
			presenter = res.keySet().iterator().next();
		
		if (presenter == null) {
			if (params.containsKey(IComponentFactory.FACTORY_PARAM_MVP_PRESENTER_FACTORY)) {
				presenterFactory = (ConfigurablePresenterFactory) params.get(IComponentFactory.FACTORY_PARAM_MVP_PRESENTER_FACTORY);
			} else {
				EventBusManager ebm = (EventBusManager) params.get(IComponentFactory.FACTORY_PARAM_MVP_EVENTBUS_MANAGER);				
				Locale locale = (Locale) params.get(IComponentFactory.FACTORY_PARAM_MVP_LOCALE);
				presenterFactory = new ConfigurablePresenterFactory(ebm, locale);
				presenterFactory.setCustomizer(new ConfigurablePresenterFactoryCustomizer(params));
				params.put(IComponentFactory.FACTORY_PARAM_MVP_PRESENTER_FACTORY, presenterFactory);
			}
			presenterFactory.getCustomizer().getConfig().put(IComponentFactory.COMPONENT_MODEL, componentModel);
			presenterFactory.getCustomizer().getConfig().put(IComponentFactory.FACTORY_PARAM_MVP_COMPONENT_MODEL, componentModel);
			
			if (componentModel instanceof MasterDetailComponent) {
				presenter = presenterFactory.createPresenter(MultiLevelEntityEditorPresenter.class);
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

		Map<IPresenter<?, ? extends EventBus>, EventBus> res = null;
		IPresenter<?, ? extends EventBus> presenter = null;
		try {
			res = (Map<IPresenter<?, ? extends EventBus>, EventBus>)this.componentFactoryContributionManager.create(componentModel,params);
		} catch (IllegalArgumentException e) {
		}	
		if (res != null && !res.isEmpty())
			presenter = res.keySet().iterator().next();
		
		if (presenter == null) {
			if (FormComponent.class.isAssignableFrom(componentModel.getClass())) {
				presenter = presenterFactory.createPresenter(EditorFormPresenter.class);
			}
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
		
		Map<IPresenter<?, ? extends EventBus>, EventBus> res = null;
		IPresenter<?, ? extends EventBus> presenter = null;
		try {
			res = (Map<IPresenter<?, ? extends EventBus>, EventBus>)this.componentFactoryContributionManager.create(componentModel,params);
		} catch (IllegalArgumentException e) {
		}	
		if (!res.isEmpty())
			presenter = res.keySet().iterator().next();
		
		if (presenter == null) {
			if (componentModel instanceof AttachmentEditorComponent) {
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



	protected VaadinEditorDataManager createPageDataBuilder() {
		return new VaadinEditorDataManager();
	}

	protected IEditorDataHandler createPageDataHandler(IEditorDataManager dataBuilder) {
		return null;//new PagePresenterDataHandler(dataBuilder);
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
		if (componentModel != null
				&& (componentModel instanceof LineEditorContainerComponent || componentModel instanceof LineEditorComponent
						|| componentModel instanceof MultiLevelEntityEditorComponent || FormComponent.class.isAssignableFrom(componentModel.getClass()) || GridComponent.class
							.isAssignableFrom(componentModel.getClass()))) {
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
	public ConfigurablePresenterFactory getPresenterFactory() {
		return this.presenterFactory;
	}

	@Override
	public IEditorDataManager getDataManager() {
		return this.dataBuilder;
	}

/*	protected class PagePresenterDataHandler implements IEditorDataHandler {
		private IEditorDataManager dataBuilder;

		public PagePresenterDataHandler(IEditorDataManager dataBuilder) {
			this.dataBuilder = dataBuilder;
		}

		@Override
		public void setParameterData(PagePresenter source, Map<String, Object> params) {
			try {
				IPresenter<?, ? extends EventBus> contentPresenter = source.getContentPresenter();
				EventBus contentPresenterBus = null;
				if (contentPresenter != null && (contentPresenterBus = contentPresenter.getEventBus()) != null) {
					((EditorContentEventBus) contentPresenterBus).applyEditorParams(params);
				} else {
					this.dataBuilder.applyParamData(source.getConfig(), source.getPageContent(), params, VaadinContributionsBasedComponentFactory.this.presenterFactory);
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
				resultDataMap = (Map<String, Object>) ((EditorContentEventBus) contentPresenterBus).getEditorResults();
			} else {
				resultDataMap = new HashMap<String, Object>(this.dataBuilder.buildResultDataMap(source.getParameterData(),
						this.dataBuilder.buildResultData(source.getPageContent()), source.getPage().getResultKeyMap()));
			}
			return resultDataMap;
		}

	}*/
}
