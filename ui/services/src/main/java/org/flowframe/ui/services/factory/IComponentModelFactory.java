package org.flowframe.ui.services.factory;

import java.util.Map;

import org.flowframe.ui.component.domain.AbstractComponent;
import org.flowframe.ui.services.data.IEditorDataManager;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.presenter.IPresenter;

import com.vaadin.ui.Component;

public interface IComponentModelFactory {
	public IPresenter<?, ? extends EventBus> createPresenter(
			AbstractComponent componentModel, Map<String, Object> params)
			throws Exception;

	public IPresenter<?, ? extends EventBus> createMasterSectionHeaderPresenter(
			AbstractComponent componentModel) throws Exception;

	public IPresenter<?, ? extends EventBus> createMasterSectionContentPresenter(
			AbstractComponent componentModel, Map<String, Object> params)
			throws Exception;

	public IPresenter<?, ? extends EventBus> createLineEditorSectionContentPresenter(
			AbstractComponent componentModel, Map<String, Object> params)
			throws Exception;

	public IPresenter<?, ? extends EventBus> createLineEditorSectionHeaderPresenter(
			AbstractComponent componentModel) throws Exception;

	public boolean correspondsToPresenter(AbstractComponent componentModel);

	public Object getPresenterFactory();
	
	public IEditorDataManager getDataManager();

	public Map<IPresenter<?, ? extends EventBus>, EventBus> create(AbstractComponent componentModel, Map<String, Object> params);
}