package org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.lineeditor.section;

import java.util.Map;

import org.flowframe.ui.component.domain.form.CollapseableSectionFormComponent;
import org.flowframe.ui.component.domain.form.SimpleFormComponent;
import org.flowframe.ui.component.domain.masterdetail.LineEditorComponent;
import org.flowframe.ui.component.domain.table.DetailGridComponent;
import org.flowframe.ui.services.factory.IComponentFactory;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.AbstractEntityEditorEventBus;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.ConfigurableBasePresenter;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.ConfigurablePresenterFactory;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.lineeditor.section.form.collapsible.EntityLineEditorCollapsibleFormPresenter;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.lineeditor.section.form.header.EntityLineEditorFormHeaderPresenter;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.lineeditor.section.form.simple.EntityLineEditorSimpleFormPresenter;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.lineeditor.section.grid.header.EntityLineEditorGridHeaderPresenter;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.lineeditor.section.view.EntityLineEditorSectionView;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.lineeditor.section.view.IEntityLineEditorSectionView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.presenter.IPresenter;
import org.vaadin.mvp.presenter.annotation.Presenter;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.ui.Component;

@Presenter(view = EntityLineEditorSectionView.class)
public class EntityLineEditorSectionPresenter extends ConfigurableBasePresenter<IEntityLineEditorSectionView, EntityLineEditorSectionEventBus> {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	private IPresenter<?, ? extends EventBus> headerPresenter;
	private IPresenter<?, ? extends EventBus> contentPresenter;
	private LineEditorComponent componentModel;

	@SuppressWarnings("rawtypes")
	public void onEntityItemEdit(EntityItem item) {
		if (this.contentPresenter == null) {
			getConfig().put(IComponentFactory.FACTORY_PARAM_MVP_CURRENT_DATA_ITEM, item);
			IComponentFactory entityFactory = (IComponentFactory)getConfig().get(IComponentFactory.FACTORY_PARAM_MVP_ENTITY_FACTORY);
			
			//VaadinEntityEditorFactoryImpl entityFactory = new VaadinEntityEditorFactoryImpl(presenterFactory);
			Map<IPresenter<?, ? extends EventBus>, EventBus> resultMap = (Map<IPresenter<?, ? extends EventBus>, EventBus>)entityFactory.create(this.componentModel.getContent(), getConfig());
			if (resultMap != null) {
				this.contentPresenter = resultMap.keySet().iterator().next();
			}			
		}
		else {
			((AbstractEntityEditorEventBus) this.contentPresenter.getEventBus()).entityItemEdit(item);
		}
	}

	@SuppressWarnings("rawtypes")
	public void onEntityItemAdded(EntityItem item) {
		if (this.contentPresenter != null) {
			((AbstractEntityEditorEventBus) this.contentPresenter.getEventBus()).entityItemAdded(item);
		}
	}

	@Override
	public void bind() {
		if (this.headerPresenter != null) {
			this.getView().setHeader((Component) this.headerPresenter.getView());
		}
		if (this.contentPresenter != null) {
			this.getView().setContent((Component) this.contentPresenter.getView());
		}
	}

	@Override
	public void configure() {
		this.componentModel = (LineEditorComponent) getConfig().get(IComponentFactory.FACTORY_PARAM_MVP_COMPONENT_MODEL);

		if (componentModel != null) {
			ConfigurablePresenterFactory presenterFactory = (ConfigurablePresenterFactory) getConfig().get(IComponentFactory.FACTORY_PARAM_MVP_PRESENTER_FACTORY);
			
			getConfig().put(IComponentFactory.FACTORY_PARAM_MVP_LINE_EDITOR_SECTION_PRESENTER, this);
			if (componentModel.getContent() instanceof CollapseableSectionFormComponent ||
					componentModel.getContent() instanceof SimpleFormComponent) {
				IPresenter<?, ? extends EventBus> presenter = presenterFactory.createPresenter(EntityLineEditorFormHeaderPresenter.class);
				if (presenter != null) {
					this.headerPresenter = presenter;
				}
			} else if (componentModel.getContent() instanceof DetailGridComponent) {
				IPresenter<?, ? extends EventBus> presenter = presenterFactory.createPresenter(EntityLineEditorGridHeaderPresenter.class);
				if (presenter != null) {
					this.headerPresenter = presenter;
				}
			}
		}
	}
	
	public void onSaveForm() {
		if (componentModel.getContent() instanceof CollapseableSectionFormComponent) {
			((EntityLineEditorCollapsibleFormPresenter) this.contentPresenter).getEventBus().saveForm();
		} else if (componentModel.getContent() instanceof SimpleFormComponent) {
			((EntityLineEditorSimpleFormPresenter) this.contentPresenter).getEventBus().saveForm();
		}
	}

	public void onValidateForm() {
		if (componentModel.getContent() instanceof CollapseableSectionFormComponent) {
			((EntityLineEditorCollapsibleFormPresenter) this.contentPresenter).getEventBus().validateForm();
		} else if (componentModel.getContent() instanceof SimpleFormComponent) {
			((EntityLineEditorSimpleFormPresenter) this.contentPresenter).getEventBus().validateForm();
		}
	}

	public void onResetForm() {
		if (componentModel.getContent() instanceof CollapseableSectionFormComponent) {
			((EntityLineEditorCollapsibleFormPresenter) this.contentPresenter).getEventBus().resetForm();
		} else if (componentModel.getContent() instanceof SimpleFormComponent) {
			((EntityLineEditorSimpleFormPresenter) this.contentPresenter).getEventBus().resetForm();
		}
	}

	public void onResizeForm(int newHeight) {
		if (componentModel.getContent() instanceof CollapseableSectionFormComponent) {
			((EntityLineEditorCollapsibleFormPresenter) this.contentPresenter).getEventBus().resizeForm(newHeight);
		} else if (componentModel.getContent() instanceof SimpleFormComponent) {
			((EntityLineEditorSimpleFormPresenter) this.contentPresenter).getEventBus().resizeForm(newHeight);
		}
	}
}