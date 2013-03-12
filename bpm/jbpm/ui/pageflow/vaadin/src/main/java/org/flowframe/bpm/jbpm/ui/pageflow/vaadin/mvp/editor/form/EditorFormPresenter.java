package org.flowframe.bpm.jbpm.ui.pageflow.vaadin.mvp.editor.form;

import java.util.Map;

import org.flowframe.bpm.jbpm.ui.pageflow.services.IPageComponent;
import org.flowframe.bpm.jbpm.ui.pageflow.services.IPageFactory;
import org.flowframe.bpm.jbpm.ui.pageflow.vaadin.builder.VaadinPageDataBuilder;
import org.flowframe.bpm.jbpm.ui.pageflow.vaadin.ext.mvp.IConfigurablePresenter;
import org.flowframe.bpm.jbpm.ui.pageflow.vaadin.ext.mvp.IContainerItemPresenter;
import org.flowframe.bpm.jbpm.ui.pageflow.vaadin.ext.mvp.ILocalizedEventSubscriber;
import org.flowframe.bpm.jbpm.ui.pageflow.vaadin.mvp.editor.form.view.EditorFormView;
import org.flowframe.bpm.jbpm.ui.pageflow.vaadin.mvp.editor.form.view.IEditorFormView;
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

@Presenter(view = EditorFormView.class)
public class EditorFormPresenter extends
		BasePresenter<IEditorFormView, EditorFormEventBus> implements
		IContainerItemPresenter, IConfigurablePresenter, IFormChangeListener, ILocalizedEventSubscriber {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private FormComponent formComponent;
	private IPageFactory factory;
	private EventBusManager sectionEventBusManager;
	private IDAOProvider daoProvider;
	private Map<String, Object> config;

	@Override
	public void onSetItemDataSource(Item item, Container... container)
			throws Exception {
		if (container.length == 1) {
			this.factory.getDataBuilder().applyItemDataSource(false, this.getView()
					.getForm(), container[0], item, this.factory
					.getPresenterFactory(), this.config);
		} else {
			throw new Exception(
					"Could not set item datasource. Expected one container, but got "
							+ container.length);
		}
	}

	@Override
	public void onConfigure(Map<String, Object> params) throws Exception {
		this.config = params;
		this.formComponent = (FormComponent) params
				.get(IComponentFactory.FACTORY_PARAM_MVP_COMPONENT_MODEL);
		this.factory = (IPageFactory) params
				.get(IComponentFactory.VAADIN_COMPONENT_FACTORY);
		this.daoProvider = (IDAOProvider) params
				.get(IPageComponent.DAO_PROVIDER);

		this.getView().setForm(
				(VaadinForm) this.factory.createComponent(this.formComponent));
		this.getView().addListener(this);
	}

	@Override
	public void onFormChanged() {
		fireEvent("enableValidate");
		fireEvent("disableSave");
		fireEvent("enableReset");
	}

	public void onValidate() throws Exception {
		if (this.getView().getForm().validateForm()) {
			fireEvent("disableValidate");
			fireEvent("enableSave");
			fireEvent("enableReset");
		} else {
			fireEvent("disableValidate");
			fireEvent("disableSave");
			fireEvent("enableReset");
		}
	}

	public void onSave() throws Exception {
		if (this.getView().getForm().saveForm()) {
			if (this.getView().getForm().getItemDataSource() instanceof BeanItem<?>) {
				Object bean = ((BeanItem<?>) this.getView().getForm()
						.getItemDataSource()).getBean();
				throw new UnsupportedOperationException("saveInstance is no longer supported in Flowframe. It used to use DAOs to create beans.");
//				VaadinPageDataBuilder.saveInstance(bean, this.daoProvider);
			}

			fireEvent("disableValidate");
			fireEvent("disableSave");
			fireEvent("disableReset");
		} else {
			fireEvent("disableValidate");
			fireEvent("disableSave");
			fireEvent("enableReset");
		}
	}

	public void onReset() throws Exception {
		this.getView().getForm().resetForm();
		fireEvent("disableValidate");
		fireEvent("disableSave");
		fireEvent("disableReset");
	}

	public void subscribe(EventBusManager eventBusManager) {
		this.sectionEventBusManager = eventBusManager;
		this.sectionEventBusManager.register(EditorFormEventBus.class,
				this);
	}

	private void fireEvent(String eventName) {
		if (this.sectionEventBusManager != null) {
			this.sectionEventBusManager.fireAnonymousEvent(eventName);
		}
	}
}
