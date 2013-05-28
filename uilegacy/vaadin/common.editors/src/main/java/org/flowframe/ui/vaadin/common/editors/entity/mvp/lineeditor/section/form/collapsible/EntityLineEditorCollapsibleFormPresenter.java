package org.flowframe.ui.vaadin.common.editors.entity.mvp.lineeditor.section.form.collapsible;

import java.util.Map;
import org.flowframe.kernel.common.mdm.domain.BaseEntity;
import org.flowframe.ui.component.domain.form.CollapseableSectionFormComponent;
import org.flowframe.ui.services.factory.IComponentFactory;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.ConfigurableBasePresenter;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.lineeditor.section.EntityLineEditorSectionEventBus;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.lineeditor.section.EntityLineEditorSectionPresenter;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.lineeditor.section.form.collapsible.view.EntityLineEditorCollapsibleFormView;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.lineeditor.section.form.collapsible.view.IEntityLineEditorCollapsibleFormView;
import org.flowframe.ui.vaadin.forms.listeners.IFormChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.presenter.annotation.Presenter;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.data.Item;

@Presenter(view = EntityLineEditorCollapsibleFormView.class)
public class EntityLineEditorCollapsibleFormPresenter extends ConfigurableBasePresenter<IEntityLineEditorCollapsibleFormView, EntityLineEditorCollapsibleFormEventBus> implements IFormChangeListener {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private boolean initialized = false;
	private EntityLineEditorSectionPresenter lineEditorSectionPresenter;
	private CollapseableSectionFormComponent formComponent;
	private EntityItem<?> itemDataSource;
	private BaseEntity entity;
	private EntityLineEditorSectionEventBus lineEditorSectionEventBus;

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
	public void onConfigure(Map<String, Object> config) {
		super.setConfig(config);
		this.lineEditorSectionPresenter = (EntityLineEditorSectionPresenter) getConfig().get(IComponentFactory.FACTORY_PARAM_MVP_LINE_EDITOR_SECTION_PRESENTER);
		this.formComponent = (CollapseableSectionFormComponent) getConfig().get(IComponentFactory.FACTORY_PARAM_MVP_COMPONENT_MODEL);
		this.lineEditorSectionEventBus = lineEditorSectionPresenter.getEventBus();
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
			this.getView().setFormTitle(formComponent.getCaption() + " (" + this.itemDataSource.getItemProperty("code").getValue().toString() + ")");
		}
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
