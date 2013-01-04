package org.flowframe.ui.vaadin.editors.builder.vaadin;

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
import org.flowframe.ui.services.factory.IEntityEditorFactory;
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
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.EventBusManager;
import org.vaadin.mvp.presenter.IPresenter;

@Transactional
@Repository
public class VaadinEntityEditorFactoryImpl implements IEntityEditorFactory {

	@SuppressWarnings("unused")
	private IRemoteDocumentRepository remoteDocumentRepository;

	private ConfigurablePresenterFactory factory;

	public VaadinEntityEditorFactoryImpl() {
	}

	public VaadinEntityEditorFactoryImpl(ConfigurablePresenterFactory factory) {
		this.factory = factory;
	}

	@Override
	public Map<IPresenter<?, ? extends EventBus>, EventBus> create(AbstractComponent conXComponent, Map<String, Object> params) {
		Map<IPresenter<?, ? extends EventBus>, EventBus> res = null;

		if (conXComponent instanceof AttachmentEditorComponent) {
			params.put(IEntityEditorFactory.FACTORY_PARAM_MVP_COMPONENT_MODEL, conXComponent);
			final IPresenter<?, ? extends EventBus> presenter = factory.createPresenter(AttachmentEditorPresenter.class);
			final EventBus eventBus = presenter.getEventBus();

			res = new HashMap<IPresenter<?, ? extends EventBus>, EventBus>();
			res.put(presenter, eventBus);
		} else if (conXComponent instanceof NoteEditorComponent) {
			params.put(IEntityEditorFactory.FACTORY_PARAM_MVP_COMPONENT_MODEL, conXComponent);
			IPresenter<?, ? extends EventBus> presenter = factory.createPresenter(NotesEditorPresenter.class);
			EventBus eventBus = presenter.getEventBus();

			res = new HashMap<IPresenter<?, ? extends EventBus>, EventBus>();
			res.put(presenter, eventBus);
		} else if (conXComponent instanceof ReferenceNumberEditorComponent) {
			params.put(IEntityEditorFactory.FACTORY_PARAM_MVP_COMPONENT_MODEL, conXComponent);
			IPresenter<?, ? extends EventBus> presenter = factory.createPresenter(ReferenceNumberEditorPresenter.class);
			EventBus eventBus = presenter.getEventBus();

			res = new HashMap<IPresenter<?, ? extends EventBus>, EventBus>();
			res.put(presenter, eventBus);
		} else if (conXComponent instanceof MasterDetailComponent) {
			params.put(IEntityEditorFactory.FACTORY_PARAM_MVP_COMPONENT_MODEL, conXComponent);
			ConfigurablePresenterFactory presenterFactory = null;
			EventBusManager ebm = (EventBusManager) params.get(IEntityEditorFactory.FACTORY_PARAM_MVP_EVENTBUS_MANAGER);

			if (params.containsKey(IEntityEditorFactory.FACTORY_PARAM_MVP_PRESENTER_FACTORY)) {
				presenterFactory = (ConfigurablePresenterFactory) params.get(IEntityEditorFactory.FACTORY_PARAM_MVP_PRESENTER_FACTORY);
			} else {
				ebm = (EventBusManager) params.get(IEntityEditorFactory.FACTORY_PARAM_MVP_EVENTBUS_MANAGER);
				Locale locale = (Locale) params.get(IEntityEditorFactory.FACTORY_PARAM_MVP_LOCALE);
				presenterFactory = new ConfigurablePresenterFactory(ebm, locale);
				presenterFactory.setCustomizer(new ConfigurablePresenterFactoryCustomizer(params));
				params.put(IEntityEditorFactory.FACTORY_PARAM_MVP_PRESENTER_FACTORY, presenterFactory);
			}

			final IPresenter<?, ? extends EventBus> mainPresenter = presenterFactory.createPresenter(MultiLevelEntityEditorPresenter.class);
			final MultiLevelEntityEditorEventBus mainEventBus = (MultiLevelEntityEditorEventBus) mainPresenter.getEventBus();

			res = new HashMap<IPresenter<?, ? extends EventBus>, EventBus>();
			res.put(mainPresenter, mainEventBus);
		} else if (conXComponent instanceof DetailFormComponent) {
			params.put(IEntityEditorFactory.FACTORY_PARAM_MVP_COMPONENT_MODEL, conXComponent);
			IPresenter<?, ? extends EventBus> presenter = factory.createPresenter(EntityFormPresenter.class);
			EventBus eventBus = presenter.getEventBus();

			res = new HashMap<IPresenter<?, ? extends EventBus>, EventBus>();
			res.put(presenter, eventBus);
		} else if (conXComponent instanceof CollapseableSectionFormComponent) {
			params.put(IEntityEditorFactory.FACTORY_PARAM_MVP_COMPONENT_MODEL, conXComponent);
			IPresenter<?, ? extends EventBus> presenter = factory.createPresenter(EntityLineEditorCollapsibleFormPresenter.class);
			EventBus eventBus = presenter.getEventBus();

			res = new HashMap<IPresenter<?, ? extends EventBus>, EventBus>();
			res.put(presenter, eventBus);
		} else if (conXComponent instanceof SimpleFormComponent) {
			params.put(IEntityEditorFactory.FACTORY_PARAM_MVP_COMPONENT_MODEL, conXComponent);
			IPresenter<?, ? extends EventBus> presenter = factory.createPresenter(EntityLineEditorSimpleFormPresenter.class);
			EventBus eventBus = presenter.getEventBus();

			res = new HashMap<IPresenter<?, ? extends EventBus>, EventBus>();
			res.put(presenter, eventBus);
		} else if (conXComponent instanceof DetailGridComponent) {
			params.put(IEntityEditorFactory.FACTORY_PARAM_MVP_COMPONENT_MODEL, conXComponent);
			IPresenter<?, ? extends EventBus> presenter = factory.createPresenter(EntityLineEditorGridPresenter.class);
			EventBus eventBus = presenter.getEventBus();

			res = new HashMap<IPresenter<?, ? extends EventBus>, EventBus>();
			res.put(presenter, eventBus);
		} else if (conXComponent instanceof GridComponent) {
			params.put(IEntityEditorFactory.FACTORY_PARAM_MVP_COMPONENT_MODEL, conXComponent);
			IPresenter<?, ? extends EventBus> presenter = factory.createPresenter(EntityGridPresenter.class);
			EventBus eventBus = presenter.getEventBus();

			res = new HashMap<IPresenter<?, ? extends EventBus>, EventBus>();
			res.put(presenter, eventBus);
		}  else if (conXComponent instanceof LineEditorComponent) {
			params.put(IEntityEditorFactory.FACTORY_PARAM_MVP_COMPONENT_MODEL, conXComponent);
			IPresenter<?, ? extends EventBus> presenter = factory.createPresenter(EntityLineEditorSectionPresenter.class);
			EventBus eventBus = presenter.getEventBus();

			res = new HashMap<IPresenter<?, ? extends EventBus>, EventBus>();
			res.put(presenter, eventBus);
		}
		return res;
	}

	public void setRemoteDocumentRepository(IRemoteDocumentRepository remoteDocumentRepository) {
		this.remoteDocumentRepository = remoteDocumentRepository;
	}
}
