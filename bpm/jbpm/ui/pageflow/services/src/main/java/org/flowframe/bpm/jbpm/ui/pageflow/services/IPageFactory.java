package org.flowframe.bpm.jbpm.ui.pageflow.services;

import java.util.Map;

import org.flowframe.bpm.jbpm.ui.pageflow.services.IPageComponent;
import org.flowframe.bpm.jbpm.ui.pageflow.services.IPageFlowPage;
import org.flowframe.ui.component.domain.AbstractComponent;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.presenter.IPresenter;
import org.vaadin.mvp.presenter.PresenterFactory;

import com.vaadin.ui.Component;

public interface IPageFactory {

	public Component createComponent(AbstractComponent componentModel);

	public Component create(AbstractComponent componentModel);

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

	public IPageComponent createPage(final IPageFlowPage page,
			Map<String, Object> initParams);
	
	public boolean isInstanceOf(IPageFlowPage page, Class<?> type);

	public boolean correspondsToPresenter(
			AbstractComponent componentModel);

	public PresenterFactory getPresenterFactory();
	
	public IPageDataBuilder getDataBuilder();

	public Map<String, Object> getConfig();
	
	public void init(Map<String, Object> config);
}