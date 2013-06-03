package org.flowframe.ui.factory;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.flowframe.ui.component.domain.AbstractComponent;
import org.flowframe.ui.component.domain.attachment.AttachmentEditorComponent;
import org.flowframe.ui.component.domain.form.CollapseableSectionFormComponent;
import org.flowframe.ui.component.domain.form.DetailFormComponent;
import org.flowframe.ui.component.domain.form.SimpleFormComponent;
import org.flowframe.ui.component.domain.masterdetail.LineEditorComponent;
import org.flowframe.ui.component.domain.masterdetail.MasterDetailComponent;
import org.flowframe.ui.component.domain.note.NoteEditorComponent;
import org.flowframe.ui.component.domain.preferences.PreferencesEditorComponent;
import org.flowframe.ui.component.domain.referencenumber.ReferenceNumberEditorComponent;
import org.flowframe.ui.component.domain.table.DetailGridComponent;
import org.flowframe.ui.component.domain.table.GridComponent;
import org.flowframe.ui.services.contribution.IComponentFactoryContributionManager;
import org.flowframe.ui.services.factory.IComponentFactory;
import org.flowframe.ui.vaadin.editors.builder.vaadin.VaadinEntityEditorFactoryImpl;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.ConfigurablePresenterFactory;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.MultiLevelEntityEditorEventBus;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.MultiLevelEntityEditorPresenter;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.attachment.AttachmentEditorPresenter;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.customizer.ConfigurablePresenterFactoryCustomizer;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.detail.form.EntityFormPresenter;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.lineeditor.section.EntityLineEditorSectionPresenter;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.lineeditor.section.form.collapsible.EntityLineEditorCollapsibleFormPresenter;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.lineeditor.section.form.simple.EntityLineEditorSimpleFormPresenter;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.lineeditor.section.grid.EntityLineEditorGridPresenter;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.notes.NotesEditorPresenter;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.preferences.PreferencesEditorPresenter;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.refnum.ReferenceNumberEditorPresenter;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.search.grid.EntityGridPresenter;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.EventBusManager;
import org.vaadin.mvp.presenter.IPresenter;


public class VaadinComponentFactory implements IComponentFactory {

	protected ConfigurablePresenterFactory presenterFactory;
	
	
	protected IComponentFactoryContributionManager componentFactoryContributionManager;
	
	public IComponentFactoryContributionManager getComponentFactoryContributionManagerManager() {
		return componentFactoryContributionManager;
	}

	public void setComponentFactoryContributionManager(IComponentFactoryContributionManager componentFactoryContributionManager) {
		this.componentFactoryContributionManager = componentFactoryContributionManager;
	}



	protected IComponentFactory createEntityEditorFactory(ConfigurablePresenterFactory factory)
	{
		return new VaadinEntityEditorFactoryImpl(componentFactoryContributionManager,factory);
	}
	

	@Override
	public Map<IPresenter<?, ? extends EventBus>, EventBus> create(AbstractComponent componentModel, Map<String, Object> params) {
		Map<IPresenter<?, ? extends EventBus>, EventBus> res = null;

		ensureConfigurablePresenterFactory(params);
		
		params.put(IComponentFactory.FACTORY_PARAM_MVP_ENTITY_FACTORY,createEntityEditorFactory(this.presenterFactory));

		if (componentModel instanceof AttachmentEditorComponent) {
			params.put(IComponentFactory.FACTORY_PARAM_MVP_COMPONENT_MODEL, componentModel);
			final IPresenter<?, ? extends EventBus> presenter = presenterFactory.createPresenter(AttachmentEditorPresenter.class);
			final EventBus eventBus = presenter.getEventBus();

			res = new HashMap<IPresenter<?, ? extends EventBus>, EventBus>();
			res.put(presenter, eventBus);
		} else if (componentModel instanceof NoteEditorComponent) {
			params.put(IComponentFactory.FACTORY_PARAM_MVP_COMPONENT_MODEL, componentModel);
			IPresenter<?, ? extends EventBus> presenter = presenterFactory.createPresenter(NotesEditorPresenter.class);
			EventBus eventBus = presenter.getEventBus();

			res = new HashMap<IPresenter<?, ? extends EventBus>, EventBus>();
			res.put(presenter, eventBus);
		} else if (componentModel instanceof PreferencesEditorComponent) {
			params.put(IComponentFactory.FACTORY_PARAM_MVP_COMPONENT_MODEL, componentModel);
			IPresenter<?, ? extends EventBus> presenter = presenterFactory.createPresenter(PreferencesEditorPresenter.class);
			EventBus eventBus = presenter.getEventBus();

			res = new HashMap<IPresenter<?, ? extends EventBus>, EventBus>();
			res.put(presenter, eventBus);
		} else if (componentModel instanceof ReferenceNumberEditorComponent) {
			params.put(IComponentFactory.FACTORY_PARAM_MVP_COMPONENT_MODEL, componentModel);
			IPresenter<?, ? extends EventBus> presenter = presenterFactory.createPresenter(ReferenceNumberEditorPresenter.class);
			EventBus eventBus = presenter.getEventBus();

			res = new HashMap<IPresenter<?, ? extends EventBus>, EventBus>();
			res.put(presenter, eventBus);
		} else if (componentModel instanceof MasterDetailComponent) {
			params.put(IComponentFactory.FACTORY_PARAM_MVP_COMPONENT_MODEL, componentModel);
			ConfigurablePresenterFactory presenterFactory = null;
			EventBusManager ebm = (EventBusManager) params.get(IComponentFactory.FACTORY_PARAM_MVP_EVENTBUS_MANAGER);

			if (params.containsKey(IComponentFactory.FACTORY_PARAM_MVP_PRESENTER_FACTORY)) {
				presenterFactory = (ConfigurablePresenterFactory) params.get(IComponentFactory.FACTORY_PARAM_MVP_PRESENTER_FACTORY);
			} else {
				ebm = (EventBusManager) params.get(IComponentFactory.FACTORY_PARAM_MVP_EVENTBUS_MANAGER);
				Locale locale = (Locale) params.get(IComponentFactory.FACTORY_PARAM_MVP_LOCALE);
				presenterFactory = new ConfigurablePresenterFactory(ebm, locale);
				presenterFactory.setCustomizer(new ConfigurablePresenterFactoryCustomizer(params));
				params.put(IComponentFactory.FACTORY_PARAM_MVP_PRESENTER_FACTORY, presenterFactory);
			}

			final IPresenter<?, ? extends EventBus> mainPresenter = presenterFactory.createPresenter(MultiLevelEntityEditorPresenter.class);
			final MultiLevelEntityEditorEventBus mainEventBus = (MultiLevelEntityEditorEventBus) mainPresenter.getEventBus();

			res = new HashMap<IPresenter<?, ? extends EventBus>, EventBus>();
			res.put(mainPresenter, mainEventBus);
		} else if (componentModel instanceof DetailFormComponent) {
			params.put(IComponentFactory.FACTORY_PARAM_MVP_COMPONENT_MODEL, componentModel);
			IPresenter<?, ? extends EventBus> presenter = presenterFactory.createPresenter(EntityFormPresenter.class);
			EventBus eventBus = presenter.getEventBus();

			res = new HashMap<IPresenter<?, ? extends EventBus>, EventBus>();
			res.put(presenter, eventBus);
		} else if (componentModel instanceof CollapseableSectionFormComponent) {
			params.put(IComponentFactory.FACTORY_PARAM_MVP_COMPONENT_MODEL, componentModel);
			IPresenter<?, ? extends EventBus> presenter = presenterFactory.createPresenter(EntityLineEditorCollapsibleFormPresenter.class);
			EventBus eventBus = presenter.getEventBus();

			res = new HashMap<IPresenter<?, ? extends EventBus>, EventBus>();
			res.put(presenter, eventBus);
		} else if (componentModel instanceof SimpleFormComponent) {
			params.put(IComponentFactory.FACTORY_PARAM_MVP_COMPONENT_MODEL, componentModel);
			IPresenter<?, ? extends EventBus> presenter = presenterFactory.createPresenter(EntityLineEditorSimpleFormPresenter.class);
			EventBus eventBus = presenter.getEventBus();

			res = new HashMap<IPresenter<?, ? extends EventBus>, EventBus>();
			res.put(presenter, eventBus);
		} else if (componentModel instanceof DetailGridComponent) {
			params.put(IComponentFactory.FACTORY_PARAM_MVP_COMPONENT_MODEL, componentModel);
			IPresenter<?, ? extends EventBus> presenter = presenterFactory.createPresenter(EntityLineEditorGridPresenter.class);
			EventBus eventBus = presenter.getEventBus();

			res = new HashMap<IPresenter<?, ? extends EventBus>, EventBus>();
			res.put(presenter, eventBus);
		} else if (componentModel instanceof GridComponent) {
			params.put(IComponentFactory.FACTORY_PARAM_MVP_COMPONENT_MODEL, componentModel);
			IPresenter<?, ? extends EventBus> presenter = presenterFactory.createPresenter(EntityGridPresenter.class);
			EventBus eventBus = presenter.getEventBus();

			res = new HashMap<IPresenter<?, ? extends EventBus>, EventBus>();
			res.put(presenter, eventBus);
		} else if (componentModel instanceof LineEditorComponent) {
			params.put(IComponentFactory.FACTORY_PARAM_MVP_COMPONENT_MODEL, componentModel);
			IPresenter<?, ? extends EventBus> presenter = presenterFactory.createPresenter(EntityLineEditorSectionPresenter.class);
			EventBus eventBus = presenter.getEventBus();

			res = new HashMap<IPresenter<?, ? extends EventBus>, EventBus>();
			res.put(presenter, eventBus);
		}
		return res;
	}

	protected void ensureConfigurablePresenterFactory(Map<String, Object> params) {	
		if (this.presenterFactory == null) {
			if (params.get(IComponentFactory.FACTORY_PARAM_MVP_LOCALE) instanceof Locale) {
				this.presenterFactory = new ConfigurablePresenterFactory(new EventBusManager(),
						(Locale) params.get(IComponentFactory.FACTORY_PARAM_MVP_LOCALE));
			} else {
				throw new RuntimeException(
						"Cannot create a new ConfigurablePresenterFactory without a parameter IComponentFactory.FACTORY_PARAM_MVP_LOCALE.");
			}
		}
	}

	protected ConfigurablePresenterFactory getFactory() {
		return presenterFactory;
	}


	@Override
	public void setConfigurablePresenterFactory(Object factory) {
		assert (factory != null) : "Factory parameter was null.";
		assert (!(factory instanceof ConfigurablePresenterFactory)) : "Factory parameter was not of type ConfigurablePresenterFactory.";
		this.presenterFactory = (ConfigurablePresenterFactory) factory;
	}
}
