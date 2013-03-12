package org.flowframe.bpm.jbpm.ui.pageflow.vaadin.mvp.lineeditor.section.form;

import java.util.Map;

import org.flowframe.bpm.jbpm.ui.pageflow.services.IPageComponent;
import org.flowframe.bpm.jbpm.ui.pageflow.services.IPageFactory;
import org.flowframe.bpm.jbpm.ui.pageflow.vaadin.builder.VaadinPageDataBuilder;
import org.flowframe.bpm.jbpm.ui.pageflow.vaadin.ext.mvp.IConfigurablePresenter;
import org.flowframe.bpm.jbpm.ui.pageflow.vaadin.ext.mvp.lineeditor.section.ILineEditorSectionContentPresenter;
import org.flowframe.bpm.jbpm.ui.pageflow.vaadin.mvp.lineeditor.section.form.header.EntityLineEditorFormHeaderEventBus;
import org.flowframe.bpm.jbpm.ui.pageflow.vaadin.mvp.lineeditor.section.form.view.EntityLineEditorFormView;
import org.flowframe.bpm.jbpm.ui.pageflow.vaadin.mvp.lineeditor.section.form.view.IEntityLineEditorFormView;
import org.flowframe.kernel.jpa.container.services.IDAOProvider;
import org.flowframe.ui.component.domain.form.FormComponent;
import org.flowframe.ui.services.factory.IComponentFactory;
import org.flowframe.ui.vaadin.forms.impl.VaadinForm;
import org.flowframe.ui.vaadin.forms.listeners.IFormChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.eventbus.EventBusManager;
import org.vaadin.mvp.presenter.BasePresenter;
import org.vaadin.mvp.presenter.annotation.Presenter;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;

@Presenter(view = EntityLineEditorFormView.class)
public class EntityLineEditorFormPresenter extends BasePresenter<IEntityLineEditorFormView, EntityLineEditorFormEventBus> implements
		ILineEditorSectionContentPresenter, IConfigurablePresenter, IFormChangeListener {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private FormComponent formComponent;
	private IPageFactory factory;
	private EventBusManager sectionEventBusManager;
	private IDAOProvider daoProvider;
	private Map<String, Object> config;
	private boolean isNewLineEditor;
	private VaadinPageDataBuilder pageDataBuilder;

	@Override
	public void onSetItemDataSource(Item item, Container... container) throws Exception {
		if (container.length == 1) {
			this.pageDataBuilder.applyItemDataSource(false, this.getView().getForm(), container[0], item,
					this.factory.getPresenterFactory(), this.config);
		} else {
			throw new Exception("Could not set item datasource. Expected one container, but got " + container.length);
		}
	}

	@Override
	public void onConfigure(Map<String, Object> params) throws Exception {
		this.config = params;
		this.formComponent = (FormComponent) params.get(IComponentFactory.FACTORY_PARAM_MVP_COMPONENT_MODEL);
		this.factory = (IPageFactory) params.get(IComponentFactory.VAADIN_COMPONENT_FACTORY);
		this.daoProvider = (IDAOProvider) params.get(IPageComponent.DAO_PROVIDER);

		if (this.sectionEventBusManager != null) {
			this.sectionEventBusManager.register(EntityLineEditorFormHeaderEventBus.class, this);
		}

		this.getView().setForm((VaadinForm) this.factory.createComponent(this.formComponent));
		this.getView().addListener(this);
		
		this.pageDataBuilder = new VaadinPageDataBuilder();
	}

	@Override
	public void onFormChanged() {
		this.sectionEventBusManager.fireAnonymousEvent("enableValidate");
		this.sectionEventBusManager.fireAnonymousEvent("disableSave");
		this.sectionEventBusManager.fireAnonymousEvent("enableReset");
	}

	public void onValidate() throws Exception {
		if (this.getView().getForm().validateForm()) {
			this.sectionEventBusManager.fireAnonymousEvent("disableValidate");
			this.sectionEventBusManager.fireAnonymousEvent("enableSave");
			this.sectionEventBusManager.fireAnonymousEvent("enableReset");
		} else {
			this.sectionEventBusManager.fireAnonymousEvent("disableValidate");
			this.sectionEventBusManager.fireAnonymousEvent("disableSave");
			this.sectionEventBusManager.fireAnonymousEvent("enableReset");
		}
	}

	public void onSave() throws Exception {
		if (this.getView().getForm().saveForm()) {
			if (this.getView().getForm().getItemDataSource() instanceof BeanItem<?>) {
				Object bean = ((BeanItem<?>) this.getView().getForm().getItemDataSource()).getBean();
				if (!isNewLineEditor) {
					this.pageDataBuilder.saveInstance(bean, this.daoProvider);
				} else {
					this.pageDataBuilder.saveNewInstance(bean, this.daoProvider, this.factory.getPresenterFactory().getEventBusManager(), this.config);
				}
			}

			this.sectionEventBusManager.fireAnonymousEvent("disableValidate");
			this.sectionEventBusManager.fireAnonymousEvent("disableSave");
			this.sectionEventBusManager.fireAnonymousEvent("disableReset");
		} else {
			this.sectionEventBusManager.fireAnonymousEvent("disableValidate");
			this.sectionEventBusManager.fireAnonymousEvent("disableSave");
			this.sectionEventBusManager.fireAnonymousEvent("enableReset");
		}
	}

	public void onReset() throws Exception {
		this.getView().getForm().resetForm();
		this.sectionEventBusManager.fireAnonymousEvent("disableValidate");
		this.sectionEventBusManager.fireAnonymousEvent("disableSave");
		this.sectionEventBusManager.fireAnonymousEvent("disableReset");
	}

	@Override
	public void subscribe(EventBusManager eventBusManager) {
		this.sectionEventBusManager = eventBusManager;
		this.sectionEventBusManager.register(EntityLineEditorFormEventBus.class, this);
	}

	public void setNewLineEditor(boolean isNewLineEditor) {
		this.isNewLineEditor = isNewLineEditor;
	}

	public boolean isNewLineEditor() {
		return this.isNewLineEditor;
	}
}
