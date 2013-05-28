package org.flowframe.ui.vaadin.common.editors.pageflow.mvp.lineeditor.section;

import java.util.HashMap;
import java.util.Map;

import org.flowframe.bpm.jbpm.ui.pageflow.services.IPageFactory;
import org.flowframe.ui.vaadin.common.editors.pageflow.ext.mvp.IConfigurablePresenter;
import org.flowframe.ui.vaadin.common.editors.pageflow.ext.mvp.IContainerItemPresenter;
import org.flowframe.ui.vaadin.common.editors.pageflow.ext.mvp.ILocalizedEventSubscriber;
import org.flowframe.ui.vaadin.common.editors.pageflow.mvp.editor.multilevel.MultiLevelEditorEventBus;
import org.flowframe.ui.vaadin.common.editors.pageflow.mvp.lineeditor.section.form.EntityLineEditorFormPresenter;
import org.flowframe.ui.vaadin.common.editors.pageflow.mvp.lineeditor.section.view.EntityLineEditorSectionView;
import org.flowframe.ui.vaadin.common.editors.pageflow.mvp.lineeditor.section.view.IEntityLineEditorSectionView;
import org.flowframe.ui.component.domain.masterdetail.CreateNewLineEditorComponent;
import org.flowframe.ui.component.domain.masterdetail.LineEditorComponent;
import org.flowframe.ui.services.factory.IComponentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.EventBusManager;
import org.vaadin.mvp.presenter.BasePresenter;
import org.vaadin.mvp.presenter.IPresenter;
import org.vaadin.mvp.presenter.annotation.Presenter;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.ui.Component;

@Presenter(view = EntityLineEditorSectionView.class)
public class EntityLineEditorSectionPresenter extends BasePresenter<IEntityLineEditorSectionView, EntityLineEditorSectionEventBus> implements IConfigurablePresenter, IContainerItemPresenter {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	private IPresenter<?, ? extends EventBus> headerPresenter;
	private IPresenter<?, ? extends EventBus> contentPresenter;
	private LineEditorComponent componentModel;
	private IPageFactory factory;

	@Override
	public void onConfigure(Map<String, Object> params) throws Exception {
		this.componentModel = (LineEditorComponent) params.get(IComponentFactory.COMPONENT_MODEL);
		this.factory = (IPageFactory) params.get(IComponentFactory.VAADIN_COMPONENT_FACTORY);

		HashMap<String, Object> childParams = null;

		if (this.componentModel != null) {
			EventBusManager sectionEventBusManager = new EventBusManager();
//			sectionEventBusManager.register(EntityLineEditorSectionEventBus.class, this);
			// Add the MLE Event Bus if it exists
			EventBus mleEventBus = this.factory.getPresenterFactory().getEventBusManager().getEventBus(MultiLevelEditorEventBus.class);
			if (mleEventBus != null) {
				sectionEventBusManager.register(MultiLevelEditorEventBus.class, mleEventBus);
			}

			childParams = new HashMap<String, Object>(params);
			this.headerPresenter = this.factory.createLineEditorSectionHeaderPresenter(this.componentModel.getContent());
			this.contentPresenter = this.factory.createLineEditorSectionContentPresenter(this.componentModel.getContent(), childParams);

			if (this.headerPresenter instanceof ILocalizedEventSubscriber) {
				((ILocalizedEventSubscriber) this.headerPresenter).subscribe(sectionEventBusManager);
			}
			if (this.contentPresenter instanceof ILocalizedEventSubscriber) {
				((ILocalizedEventSubscriber) this.contentPresenter).subscribe(sectionEventBusManager);
			}

			if (this.headerPresenter != null) {
				this.getView().setHeader((Component) this.headerPresenter.getView());
			}
			if (this.contentPresenter != null) {
				this.getView().setContent((Component) this.contentPresenter.getView());
			}
			
			if (this.componentModel instanceof CreateNewLineEditorComponent) {
				if (this.contentPresenter instanceof EntityLineEditorFormPresenter) {
					((EntityLineEditorFormPresenter) this.contentPresenter).setNewLineEditor(true);
				}
			}
			
		}
	}

	@Override
	public void onSetItemDataSource(Item item, Container... containers) throws Exception {
		if (this.contentPresenter != null) {
			if (IContainerItemPresenter.class.isAssignableFrom(this.contentPresenter.getClass())) {
				((IContainerItemPresenter) this.contentPresenter).onSetItemDataSource(item, containers);
			}
		} else {
			throw new Exception("Could not set the item datasource. The content presenter was null.");
		}
	}
}
