package org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.lineeditor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.presenter.IPresenter;
import org.vaadin.mvp.presenter.annotation.Presenter;

import com.conx.logistics.kernel.ui.components.domain.AbstractComponent;
import com.conx.logistics.kernel.ui.components.domain.attachment.AttachmentEditorComponent;
import com.conx.logistics.kernel.ui.components.domain.masterdetail.LineEditorComponent;
import com.conx.logistics.kernel.ui.components.domain.masterdetail.LineEditorContainerComponent;
import com.conx.logistics.kernel.ui.components.domain.note.NoteEditorComponent;
import com.conx.logistics.kernel.ui.components.domain.referencenumber.ReferenceNumberEditorComponent;
import org.flowframe.ui.vaadin.editors.builder.vaadin.VaadinEntityEditorFactoryImpl;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.AbstractEntityEditorEventBus;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.ConfigurableBasePresenter;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.ConfigurablePresenterFactory;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.MultiLevelEntityEditorPresenter;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.lineeditor.view.EntityLineEditorView;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.lineeditor.view.IEntityLineEditorView;
import com.conx.logistics.kernel.ui.factory.services.IEntityEditorFactory;
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

	private Item itemDataSource;

	public EntityLineEditorPresenter() {
		super();
	}

	/**
	 * EventBus callbacks
	 */
	public void onStart(MultiLevelEntityEditorPresenter parentPresenter, AbstractEntityEditorEventBus entityEditorEventListener, AbstractComponent aec, EntityManager em,
			HashMap<String, Object> extraParams) {
		try {
			this.getView().init();

		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
		}
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

	@Override
	public void configure() {
		getConfig().put(IEntityEditorFactory.FACTORY_PARAM_MVP_LINE_EDITOR_SECTION_PRESENTER, this);

		Map<String, Object> config = super.getConfig();
		LineEditorContainerComponent componentModel = (LineEditorContainerComponent) config.get(IEntityEditorFactory.FACTORY_PARAM_MVP_COMPONENT_MODEL);
		ConfigurablePresenterFactory presenterFactory = (ConfigurablePresenterFactory) config.get(IEntityEditorFactory.FACTORY_PARAM_MVP_PRESENTER_FACTORY);

		// 1. Get LineEditor models
		Set<LineEditorComponent> lecs = componentModel.getLineEditors();

		// 2. For each, create LineEditorSection presenters
		VaadinEntityEditorFactoryImpl entityFactory = new VaadinEntityEditorFactoryImpl(presenterFactory);
		Map<IPresenter<?, ? extends EventBus>, EventBus> entityMVP = null;
		for (LineEditorComponent lec : lecs) {
			entityMVP = entityFactory.create(lec, getConfig());
			if (entityMVP != null) {
				IPresenter<?, ? extends EventBus> presenter = entityMVP.keySet().iterator().next();

				if (lec.getContent() instanceof AttachmentEditorComponent) {
					this.attachmentsPresenter = presenter;
				} else if (lec.getContent() instanceof NoteEditorComponent) {
					this.notesPresenter = presenter;
				} else if (lec.getContent() instanceof ReferenceNumberEditorComponent) {
					this.refNumPresenter = presenter;
				} else {
					linePresenter2CaptionCache.put(presenter, lec.getCaption());
					mvpCache.add(presenter);
				}
			}
		}

		this.itemDataSource = (Item) getConfig().get(IEntityEditorFactory.FACTORY_PARAM_MVP_ITEM_DATASOURCE);
	}

	public void onSetItemDataSource(Item item) {
		if (item != null) {
			onEntityItemEdit((EntityItem<?>) item);
		}
	}
}
