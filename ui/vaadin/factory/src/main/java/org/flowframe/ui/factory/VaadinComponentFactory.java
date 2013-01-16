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
import org.flowframe.ui.component.domain.referencenumber.ReferenceNumberEditorComponent;
import org.flowframe.ui.component.domain.table.DetailGridComponent;
import org.flowframe.ui.component.domain.table.GridComponent;
import org.flowframe.ui.services.factory.IComponentFactory;
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
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.refnum.ReferenceNumberEditorPresenter;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.search.grid.EntityGridPresenter;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.EventBusManager;
import org.vaadin.mvp.presenter.IPresenter;

public class VaadinComponentFactory implements IComponentFactory {

	private ConfigurablePresenterFactory factory;

	@Override
	public Map<IPresenter<?, ? extends EventBus>, EventBus> create(AbstractComponent componentModel, Map<String, Object> params) {
		Map<IPresenter<?, ? extends EventBus>, EventBus> res = null;

		if (this.factory == null) {
			if (params.get(IComponentFactory.FACTORY_PARAM_MVP_LOCALE) instanceof Locale) {
				this.factory = new ConfigurablePresenterFactory(new EventBusManager(),
						(Locale) params.get(IComponentFactory.FACTORY_PARAM_MVP_LOCALE));
			} else {
				throw new RuntimeException(
						"Cannot create a new ConfigurablePresenterFactory without a parameter IComponentFactory.FACTORY_PARAM_MVP_LOCALE.");
			}
		}

		if (componentModel instanceof AttachmentEditorComponent) {
			params.put(IComponentFactory.FACTORY_PARAM_MVP_COMPONENT_MODEL, componentModel);
			final IPresenter<?, ? extends EventBus> presenter = factory.createPresenter(AttachmentEditorPresenter.class);
			final EventBus eventBus = presenter.getEventBus();

			res = new HashMap<IPresenter<?, ? extends EventBus>, EventBus>();
			res.put(presenter, eventBus);
		} else if (componentModel instanceof NoteEditorComponent) {
			params.put(IComponentFactory.FACTORY_PARAM_MVP_COMPONENT_MODEL, componentModel);
			IPresenter<?, ? extends EventBus> presenter = factory.createPresenter(NotesEditorPresenter.class);
			EventBus eventBus = presenter.getEventBus();

			res = new HashMap<IPresenter<?, ? extends EventBus>, EventBus>();
			res.put(presenter, eventBus);
		} else if (componentModel instanceof ReferenceNumberEditorComponent) {
			params.put(IComponentFactory.FACTORY_PARAM_MVP_COMPONENT_MODEL, componentModel);
			IPresenter<?, ? extends EventBus> presenter = factory.createPresenter(ReferenceNumberEditorPresenter.class);
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
			IPresenter<?, ? extends EventBus> presenter = factory.createPresenter(EntityFormPresenter.class);
			EventBus eventBus = presenter.getEventBus();

			res = new HashMap<IPresenter<?, ? extends EventBus>, EventBus>();
			res.put(presenter, eventBus);
		} else if (componentModel instanceof CollapseableSectionFormComponent) {
			params.put(IComponentFactory.FACTORY_PARAM_MVP_COMPONENT_MODEL, componentModel);
			IPresenter<?, ? extends EventBus> presenter = factory.createPresenter(EntityLineEditorCollapsibleFormPresenter.class);
			EventBus eventBus = presenter.getEventBus();

			res = new HashMap<IPresenter<?, ? extends EventBus>, EventBus>();
			res.put(presenter, eventBus);
		} else if (componentModel instanceof SimpleFormComponent) {
			params.put(IComponentFactory.FACTORY_PARAM_MVP_COMPONENT_MODEL, componentModel);
			IPresenter<?, ? extends EventBus> presenter = factory.createPresenter(EntityLineEditorSimpleFormPresenter.class);
			EventBus eventBus = presenter.getEventBus();

			res = new HashMap<IPresenter<?, ? extends EventBus>, EventBus>();
			res.put(presenter, eventBus);
		} else if (componentModel instanceof DetailGridComponent) {
			params.put(IComponentFactory.FACTORY_PARAM_MVP_COMPONENT_MODEL, componentModel);
			IPresenter<?, ? extends EventBus> presenter = factory.createPresenter(EntityLineEditorGridPresenter.class);
			EventBus eventBus = presenter.getEventBus();

			res = new HashMap<IPresenter<?, ? extends EventBus>, EventBus>();
			res.put(presenter, eventBus);
		} else if (componentModel instanceof GridComponent) {
			params.put(IComponentFactory.FACTORY_PARAM_MVP_COMPONENT_MODEL, componentModel);
			IPresenter<?, ? extends EventBus> presenter = factory.createPresenter(EntityGridPresenter.class);
			EventBus eventBus = presenter.getEventBus();

			res = new HashMap<IPresenter<?, ? extends EventBus>, EventBus>();
			res.put(presenter, eventBus);
		} else if (componentModel instanceof LineEditorComponent) {
			params.put(IComponentFactory.FACTORY_PARAM_MVP_COMPONENT_MODEL, componentModel);
			IPresenter<?, ? extends EventBus> presenter = factory.createPresenter(EntityLineEditorSectionPresenter.class);
			EventBus eventBus = presenter.getEventBus();

			res = new HashMap<IPresenter<?, ? extends EventBus>, EventBus>();
			res.put(presenter, eventBus);
		}
		return res;
	}

}
