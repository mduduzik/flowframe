package org.flowframe.ui.vaadin.common.editors.entity.mvp.lineeditor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;

import org.flowframe.ui.component.domain.AbstractComponent;
import org.flowframe.ui.component.domain.attachment.AttachmentEditorComponent;
import org.flowframe.ui.component.domain.masterdetail.LineEditorComponent;
import org.flowframe.ui.component.domain.masterdetail.LineEditorContainerComponent;
import org.flowframe.ui.component.domain.note.NoteEditorComponent;
import org.flowframe.ui.component.domain.preferences.PreferencesEditorComponent;
import org.flowframe.ui.component.domain.referencenumber.ReferenceNumberEditorComponent;
import org.flowframe.ui.services.factory.IComponentFactory;
import org.flowframe.ui.services.factory.IComponentFactoryManager;
import org.flowframe.ui.services.factory.IComponentModelFactory;
import org.flowframe.ui.vaadin.common.mvp.AbstractMainApplication;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.AbstractEntityEditorEventBus;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.ConfigurableBasePresenter;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.ConfigurablePresenterFactory;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.MultiLevelEntityEditorPresenter;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.lineeditor.view.EntityLineEditorView;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.lineeditor.view.IEntityLineEditorView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.presenter.IPresenter;
import org.vaadin.mvp.presenter.annotation.Presenter;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Component;

@Presenter(view = EntityLineEditorView.class)
public class EntityLineEditorPresenter extends ConfigurableBasePresenter<IEntityLineEditorView, EntityLineEditorEventBus> implements Property.ValueChangeListener {
	private static final long serialVersionUID = 1L;

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	private boolean initialized = false;
	private Set<IPresenter<?, ? extends EventBus>> mvpCache = new HashSet<IPresenter<?, ? extends EventBus>>();
	private Map<IPresenter<?, ? extends EventBus>, String> linePresenter2CaptionCache = new HashMap<IPresenter<?, ? extends EventBus>, String>();

	private IPresenter<?, ? extends EventBus> attachmentsPresenter;
	private IPresenter<?, ? extends EventBus> refNumPresenter;
	private IPresenter<?, ? extends EventBus> notesPresenter;
	private IPresenter<?, ? extends EventBus> preferencesPresenter;

	private Item itemDataSource;

	private AbstractMainApplication mainApplication;

	private IComponentFactoryManager componentFactoryManager;

	public EntityLineEditorPresenter() {
		super();
	}

	/**
	 * EventBus callbacks
	 */
	public void onStart(MultiLevelEntityEditorPresenter parentPresenter, AbstractEntityEditorEventBus entityEditorEventListener, AbstractComponent aec, EntityManager em,
			HashMap<String, Object> extraParams) {
		this.getView().init();
	}

	// MultiLevelEntityEditorEventBus implementation
	@SuppressWarnings("rawtypes")
	public void onEntityItemEdit(EntityItem item) {
		for (IPresenter<?, ? extends EventBus> presenter : mvpCache) {
			((AbstractEntityEditorEventBus) presenter.getEventBus()).entityItemEdit(item);
			break;
		}

		// - Attachments
		if (attachmentsPresenter != null)
			((AbstractEntityEditorEventBus) attachmentsPresenter.getEventBus()).entityItemEdit(item);
		// - Reference Numbers
		if (refNumPresenter != null)
			((AbstractEntityEditorEventBus) refNumPresenter.getEventBus()).entityItemEdit(item);
		// - Notes
		if (notesPresenter != null)
			((AbstractEntityEditorEventBus) notesPresenter.getEventBus()).entityItemEdit(item);
		// - Preferences
		if (preferencesPresenter != null)
			((AbstractEntityEditorEventBus) preferencesPresenter.getEventBus()).entityItemEdit(item);
	}

	@SuppressWarnings("rawtypes")
	public void onEntityItemAdded(EntityItem item) {
		for (IPresenter<?, ? extends EventBus> presenter : mvpCache) {
			((AbstractEntityEditorEventBus) presenter.getEventBus()).entityItemAdded(item);
		}

		// - Attachments
		if (attachmentsPresenter != null)
			((AbstractEntityEditorEventBus) attachmentsPresenter.getEventBus()).entityItemAdded(item);
		// - Reference Numbers
		if (refNumPresenter != null)
			((AbstractEntityEditorEventBus) refNumPresenter.getEventBus()).entityItemAdded(item);
		// - Notes
		if (notesPresenter != null)
			((AbstractEntityEditorEventBus) notesPresenter.getEventBus()).entityItemAdded(item);
		// - Preferences
		if (preferencesPresenter != null)
			((AbstractEntityEditorEventBus) preferencesPresenter.getEventBus()).entityItemAdded(item);
	}

	@Override
	public void bind() {
		String caption = null;
		for (IPresenter<?, ? extends EventBus> presenter : mvpCache) {
			caption = linePresenter2CaptionCache.get(presenter);
			((IEntityLineEditorView) getView()).getMainLayout().addTab((Component) presenter.getView(), caption);
		}

		// - Attachments
		if (attachmentsPresenter != null)
			((IEntityLineEditorView) getView()).getMainLayout().addTab((Component) attachmentsPresenter.getView(), "Attachments");
		// - Reference Numbers
		if (refNumPresenter != null)
			((IEntityLineEditorView) getView()).getMainLayout().addTab((Component) refNumPresenter.getView(), "Reference Numbers");
		// - Notes
		if (notesPresenter != null)
			((IEntityLineEditorView) getView()).getMainLayout().addTab((Component) notesPresenter.getView(), "Notes");
		// - Preferences
		if (preferencesPresenter != null)
			((IEntityLineEditorView) getView()).getMainLayout().addTab((Component) preferencesPresenter.getView(), "Preferences");

		if (!initialized) {
			if (this.itemDataSource != null) {
				onEntityItemEdit((EntityItem<?>) this.itemDataSource);
			}
		}
		this.setInitialized(true);
	}

	@Override
	public void valueChange(ValueChangeEvent event) {

	}

	public boolean isInitialized() {
		return initialized;
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onConfigure(Map<String, Object> config) {
		super.setConfig(config);
		getConfig().put(IComponentFactory.FACTORY_PARAM_MVP_LINE_EDITOR_SECTION_PRESENTER, this);

		LineEditorContainerComponent componentModel = (LineEditorContainerComponent) config.get(IComponentFactory.FACTORY_PARAM_MVP_COMPONENT_MODEL);
		ConfigurablePresenterFactory presenterFactory = (ConfigurablePresenterFactory) config.get(IComponentFactory.FACTORY_PARAM_MVP_PRESENTER_FACTORY);

		this.mainApplication = (AbstractMainApplication)config.get(IComponentFactory.FACTORY_PARAM_MAIN_APP);
		this.componentFactoryManager = (IComponentFactoryManager)this.mainApplication.getComponentFactoryManager();
		
		// 1. Get LineEditor models
		Set<LineEditorComponent> lecs = componentModel.getLineEditors();

		// 2. For each, create LineEditorSection presenters
		IComponentModelFactory entityFactory = componentFactoryManager.create(new HashMap<String,Object>(),presenterFactory);
		Map<IPresenter<?, ? extends EventBus>, EventBus> entityMVP = null;
		for (LineEditorComponent lec : lecs) {
			entityMVP = (Map<IPresenter<?, ? extends EventBus>, EventBus>) entityFactory.create(lec, getConfig());
			if (entityMVP != null) {
				IPresenter<?, ? extends EventBus> presenter = entityMVP.keySet().iterator().next();

				if (lec.getContent() instanceof AttachmentEditorComponent) {
					this.attachmentsPresenter = presenter;
				} else if (lec.getContent() instanceof NoteEditorComponent) {
					this.notesPresenter = presenter;
				} else if (lec.getContent() instanceof ReferenceNumberEditorComponent) {
					this.refNumPresenter = presenter;
				} else if (lec.getContent() instanceof PreferencesEditorComponent) {
					this.preferencesPresenter = presenter;
				} else {
					linePresenter2CaptionCache.put(presenter, lec.getCaption());
					mvpCache.add(presenter);
				}
			}
		}

		this.itemDataSource = (Item) getConfig().get(IComponentFactory.FACTORY_PARAM_MVP_ITEM_DATASOURCE);
	}

	public void onSetItemDataSource(Item item) {
		if (item != null) {
			onEntityItemEdit((EntityItem<?>) item);
		}
	}
}
