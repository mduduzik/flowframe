package org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.lineeditor.section.form.simple;

import org.flowframe.kernel.common.mdm.domain.BaseEntity;
import org.flowframe.ui.component.domain.form.SimpleFormComponent;
import org.flowframe.ui.services.factory.IEntityEditorFactory;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.ConfigurableBasePresenter;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.lineeditor.section.EntityLineEditorSectionEventBus;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.lineeditor.section.EntityLineEditorSectionPresenter;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.lineeditor.section.form.simple.view.EntityLineEditorSimpleFormView;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.lineeditor.section.form.simple.view.IEntityLineEditorSimpleFormView;
import org.flowframe.ui.vaadin.forms.listeners.IFormChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.presenter.annotation.Presenter;

import com.vaadin.addon.jpacontainer.EntityItem;

@Presenter(view = EntityLineEditorSimpleFormView.class)
public class EntityLineEditorSimpleFormPresenter extends ConfigurableBasePresenter<IEntityLineEditorSimpleFormView, EntityLineEditorSimpleFormEventBus> implements IFormChangeListener {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private boolean initialized = false;
	private SimpleFormComponent formComponent;
	private BaseEntity entity;

	private EntityLineEditorSectionPresenter lineEditorSectionPresenter;
	private EntityLineEditorSectionEventBus lineEditorSectionEventBus;

	public EntityLineEditorSimpleFormPresenter() {
	}

	private void initialize() {
		this.getView().init();
		this.getView().setForm(formComponent);
		this.getView().addFormChangeListener(this);
		this.setInitialized(true);
	}

	@Override
	public void bind() {
	}

	@SuppressWarnings("rawtypes")
	public void onEntityItemEdit(EntityItem item) {
		this.setEntity((BaseEntity) item.getEntity());
		if (!isInitialized()) {
			initialize();
		}
		this.getView().setItemDataSource(item);
	}

	public void onEntityItemAdded(EntityItem<?> item) {
		// this.entityContainer.refresh();
	}

	public boolean isInitialized() {
		return initialized;
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

	@Override
	public void configure() {
		this.lineEditorSectionPresenter = (EntityLineEditorSectionPresenter) getConfig().get(IEntityEditorFactory.FACTORY_PARAM_MVP_LINE_EDITOR_SECTION_PRESENTER);
		this.formComponent = (SimpleFormComponent) getConfig().get(IEntityEditorFactory.FACTORY_PARAM_MVP_COMPONENT_MODEL);
		this.lineEditorSectionEventBus = lineEditorSectionPresenter.getEventBus();
	}

	public BaseEntity getEntity() {
		return entity;
	}

	public void setEntity(BaseEntity entity) {
		this.entity = entity;
	}

	@Override
	public void onFormChanged() {
		lineEditorSectionEventBus.formChanged();
	}

	public void onFormValidated() {
		lineEditorSectionEventBus.formValidated();
	}

	public void onSaveForm() {
		this.getView().saveForm();
	}

	public void onValidateForm() {
		if (this.getView().validateForm()) {
			this.onFormValidated();
		}
	}

	public void onResetForm() {
		this.getView().resetForm();
	}

	public void onResizeForm(int newHeight) {
		this.getView().resizeForm(newHeight);
	}
}
