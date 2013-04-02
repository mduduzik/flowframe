package org.flowframe.ui.vaadin.mvp.editors;

import org.flowframe.ui.component.domain.AbstractComponent;
import org.flowframe.ui.component.domain.masterdetail.MasterDetailComponent;
import org.flowframe.ui.vaadin.mvp.core.presenter.SpringBasePresenter;
import org.flowframe.ui.vaadin.mvp.editors.event.IEntityEditorEventBus;
import org.flowframe.ui.vaadin.mvp.editors.view.EntityEditorView;
import org.flowframe.ui.vaadin.mvp.editors.view.IEntityEditorView;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.presenter.IPresenter;
import org.vaadin.mvp.presenter.annotation.Presenter;

@Presenter(view = EntityEditorView.class)
public class EntityEditorPresenter extends SpringBasePresenter<IEntityEditorView, IEntityEditorEventBus>{
	@SuppressWarnings("unused")
	private MasterDetailComponent componentModel;
	
	public void setMasterPresenter(IPresenter<?, ? extends EventBus> masterPresenter) {
		// TODO: Implement this
	}
	
	public void setDetailPresenter(IPresenter<?, ? extends EventBus> masterPresenter) {
		// TODO: Implement this
	}

	public void setComponentModel(AbstractComponent componentModel) {
		this.componentModel = (MasterDetailComponent) componentModel;
	}
}
