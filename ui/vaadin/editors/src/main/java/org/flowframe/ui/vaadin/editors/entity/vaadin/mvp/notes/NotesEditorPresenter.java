package org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.notes;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.flowframe.kernel.common.mdm.domain.BaseEntity;
import org.flowframe.kernel.common.mdm.domain.note.Note;
import org.flowframe.kernel.common.mdm.domain.note.NoteItem;
import org.flowframe.ui.component.domain.AbstractComponent;
import org.flowframe.ui.component.domain.note.NoteEditorComponent;
import org.flowframe.ui.services.contribution.IMainApplication;
import org.flowframe.ui.services.factory.IComponentFactory;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.ConfigurableBasePresenter;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.notes.view.INotesEditorView;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.notes.view.NotesEditorView;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.notes.view.NotesEditorView.ICreateNoteListener;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.notes.view.NotesEditorView.ISaveNoteListener;
import org.flowframe.ui.vaadin.forms.FormMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.presenter.annotation.Presenter;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.util.DefaultQueryModifierDelegate;
import com.vaadin.data.Item;

@Presenter(view = NotesEditorView.class)
public class NotesEditorPresenter extends ConfigurableBasePresenter<INotesEditorView, NotesEditorEventBus> implements ICreateNoteListener, ISaveNoteListener {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	private boolean initialized = false;
	private JPAContainer<NoteItem> entityContainer;
	private Set<String> visibleFieldNames;
	private AbstractComponent noteEditorComponent;
	private Note note;
	// private ConfigurableBasePresenter<IMultiLevelEntityEditorView,
	// MultiLevelEntityEditorEventBus> mainEventBus;
	private List<String> formVisibleFieldNames;
	private Item newEntityItem;
	@SuppressWarnings({ "rawtypes", "unused" })
	private ConfigurableBasePresenter multiLevelEntityEditorPresenter;
	private IMainApplication mainApplication;

	@SuppressWarnings("unchecked")
	private void initialize() {
		this.getView().init();
		this.entityContainer = (JPAContainer<NoteItem>) this.mainApplication.createPersistenceContainer(NoteItem.class);
		this.visibleFieldNames = noteEditorComponent.getDataSource().getVisibleFieldNames();
		this.formVisibleFieldNames = Arrays.asList("noteType", "content");
		Set<String> nestedFieldNames = noteEditorComponent.getDataSource().getNestedFieldNames();
		for (String npp : nestedFieldNames) {
			entityContainer.addNestedContainerProperty(npp);
		}
		this.getView().addCreateNoteListener(this);
		this.getView().addSaveNoteListener(this);
		this.getView().setContainerDataSource(entityContainer, visibleFieldNames, formVisibleFieldNames);
		this.getView().showContent();
		this.setInitialized(true);
	}

	// MultiLevelEntityEditorEventBus implementation
	@SuppressWarnings("rawtypes")
	public void onEntityItemEdit(EntityItem item) {
		BaseEntity entity = (BaseEntity) item.getEntity();
		this.note = entity.getNote();
		if (!isInitialized()) {
			try {
				initialize();
			} catch (Exception e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				String stacktrace = sw.toString();
				logger.error(stacktrace);
			}
		}
		updateQueryFilter();
	}

	@SuppressWarnings("rawtypes")
	public void onEntityItemAdded(EntityItem item) {
		this.entityContainer.refresh();
	}

	public void onNoteItemAdded(NoteItem ni) {
		// this.entityContainer.addEntity(ni);
	}

	@Override
	public void bind() {
	}

	public boolean isInitialized() {
		return initialized;
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void configure() {
		this.multiLevelEntityEditorPresenter = (ConfigurableBasePresenter) getConfig().get(IComponentFactory.FACTORY_PARAM_MVP_CURRENT_MLENTITY_EDITOR_PRESENTER);
		this.noteEditorComponent = (NoteEditorComponent) getConfig().get(IComponentFactory.FACTORY_PARAM_MVP_COMPONENT_MODEL);
		this.mainApplication = (IMainApplication) getConfig().get(IComponentFactory.FACTORY_PARAM_MAIN_APP);
	}

	private void updateQueryFilter() {
		this.entityContainer.getEntityProvider().setQueryModifierDelegate(new DefaultQueryModifierDelegate() {
			@Override
			public void filtersWillBeAdded(CriteriaBuilder criteriaBuilder, CriteriaQuery<?> query, List<Predicate> predicates) {
				Root<?> fromFileEntry = query.getRoots().iterator().next();

				Path<Note> parentNote = fromFileEntry.<Note> get("note");
				Path<Long> pathId = parentNote.get("id");
				predicates.add(criteriaBuilder.equal(pathId, NotesEditorPresenter.this.note.getId()));
			}
		});
		this.entityContainer.applyFilters();
	}

	@Override
	public void onSaveNote(Item item) {
		this.newEntityItem = null;
		this.getView().hideDetail();
	}

	@Override
	public void onCreateNote() {
		this.newEntityItem = entityContainer.createEntityItem(new NoteItem());
		this.getView().setItemDataSource(this.newEntityItem, FormMode.CREATING);
		this.getView().showDetail();
	}
}
