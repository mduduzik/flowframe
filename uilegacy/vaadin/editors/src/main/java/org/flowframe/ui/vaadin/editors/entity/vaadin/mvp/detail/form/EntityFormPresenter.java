package org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.detail.form;

import org.flowframe.kernel.common.mdm.domain.BaseEntity;
import org.flowframe.ui.component.domain.form.DetailFormComponent;
import org.flowframe.ui.services.factory.IComponentFactory;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.ConfigurableBasePresenter;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.MultiLevelEntityEditorEventBus;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.MultiLevelEntityEditorPresenter;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.detail.form.view.EntityFormView;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.detail.form.view.IEntityFormView;
import org.flowframe.ui.vaadin.forms.listeners.IFormChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.presenter.annotation.Presenter;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.data.Item;

@Presenter(view = EntityFormView.class)
public class EntityFormPresenter extends ConfigurableBasePresenter<IEntityFormView, EntityFormEventBus> implements IFormChangeListener {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private boolean initialized = false;
	private MultiLevelEntityEditorPresenter multiLevelEntityEditorPresenter;
	private DetailFormComponent formComponent;
	private EntityItem<?> itemDataSource;
	private BaseEntity entity;
	private MultiLevelEntityEditorEventBus entityEditorEventListener;

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
		onSetItemDataSource(item);
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
		this.multiLevelEntityEditorPresenter = (MultiLevelEntityEditorPresenter) getConfig().get(IComponentFactory.FACTORY_PARAM_MVP_CURRENT_MLENTITY_EDITOR_PRESENTER);
		this.formComponent = (DetailFormComponent) getConfig().get(IComponentFactory.FACTORY_PARAM_MVP_COMPONENT_MODEL);
		this.entityEditorEventListener = multiLevelEntityEditorPresenter.getEventBus();
	}

	public BaseEntity getEntity() {
		return entity;
	}

	public void setEntity(BaseEntity entity) {
		this.entity = entity;
	}

	public void onSetItemDataSource(Item item) {
		if (item != null) {
			this.itemDataSource = (EntityItem<?>) item;
			this.setEntity((BaseEntity) this.itemDataSource.getEntity());
			if (!isInitialized()) {
				try {
					initialize();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			this.getView().setItemDataSource(item);
			this.getView().setFormTitle(formComponent.getCaption() + " (" + this.itemDataSource.getItemProperty("name").getValue().toString() + ")");
		}
	}

	@Override
	public void onFormChanged() {
		entityEditorEventListener.formChanged();
	}

	public void onFormValidated() {
		entityEditorEventListener.formValidated();
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
