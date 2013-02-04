package org.flowframe.bpm.jbpm.ui.pageflow.vaadin.builder;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.flowframe.bpm.jbpm.ui.pageflow.services.ICustomDrivenPageFlowPage;
import org.flowframe.bpm.jbpm.ui.pageflow.services.IModelDrivenPageFlowPage;
import org.flowframe.bpm.jbpm.ui.pageflow.services.IPageComponent;
import org.flowframe.bpm.jbpm.ui.pageflow.services.IPageFlowPage;
import org.flowframe.bpm.jbpm.ui.pageflow.vaadin.ext.form.VaadinCollapsibleConfirmActualsForm;
import org.flowframe.bpm.jbpm.ui.pageflow.vaadin.ext.form.VaadinCollapsiblePhysicalAttributeConfirmActualsForm;
import org.flowframe.bpm.jbpm.ui.pageflow.vaadin.ext.grid.VaadinMatchGrid;
import org.flowframe.bpm.jbpm.ui.pageflow.vaadin.ext.mvp.IConfigurablePresenter;
import org.flowframe.bpm.jbpm.ui.pageflow.vaadin.mvp.IPageDataHandler;
import org.flowframe.bpm.jbpm.ui.pageflow.vaadin.mvp.PagePresenter;
import org.flowframe.bpm.jbpm.ui.pageflow.vaadin.mvp.editor.MasterSectionPresenter;
import org.flowframe.bpm.jbpm.ui.pageflow.vaadin.mvp.editor.form.EditorFormPresenter;
import org.flowframe.bpm.jbpm.ui.pageflow.vaadin.mvp.editor.form.header.EditorFormHeaderPresenter;
import org.flowframe.bpm.jbpm.ui.pageflow.vaadin.mvp.editor.multilevel.MultiLevelEditorPresenter;
import org.flowframe.bpm.jbpm.ui.pageflow.vaadin.mvp.lineeditor.EntityLineEditorPresenter;
import org.flowframe.bpm.jbpm.ui.pageflow.vaadin.mvp.lineeditor.section.EntityLineEditorSectionPresenter;
import org.flowframe.bpm.jbpm.ui.pageflow.vaadin.mvp.lineeditor.section.attachment.AttachmentEditorPresenter;
import org.flowframe.bpm.jbpm.ui.pageflow.vaadin.mvp.lineeditor.section.form.EntityLineEditorFormPresenter;
import org.flowframe.bpm.jbpm.ui.pageflow.vaadin.mvp.lineeditor.section.form.header.EntityLineEditorFormHeaderPresenter;
import org.flowframe.bpm.jbpm.ui.pageflow.vaadin.mvp.lineeditor.section.grid.EntityLineEditorGridPresenter;
import org.flowframe.bpm.jbpm.ui.pageflow.vaadin.mvp.lineeditor.section.grid.header.EntityLineEditorGridHeaderPresenter;
import org.flowframe.bpm.jbpm.ui.pageflow.vaadin.mvp.lineeditor.section.notes.NotesEditorPresenter;
import org.flowframe.bpm.jbpm.ui.pageflow.vaadin.mvp.lineeditor.section.preferences.PreferencesEditorPresenter;
import org.flowframe.bpm.jbpm.ui.pageflow.vaadin.mvp.lineeditor.section.refnum.ReferenceNumberEditorPresenter;
import org.flowframe.ui.component.domain.AbstractComponent;
import org.flowframe.ui.component.domain.attachment.AttachmentEditorComponent;
import org.flowframe.ui.component.domain.editor.MultiLevelEntityEditorComponent;
import org.flowframe.ui.component.domain.form.CollapseableSectionFormComponent;
import org.flowframe.ui.component.domain.form.CollapsibleConfirmActualsFormComponent;
import org.flowframe.ui.component.domain.form.CollapsiblePhysicalAttributeConfirmActualsFormComponent;
import org.flowframe.ui.component.domain.form.FormComponent;
import org.flowframe.ui.component.domain.form.SimpleFormComponent;
import org.flowframe.ui.component.domain.masterdetail.LineEditorComponent;
import org.flowframe.ui.component.domain.masterdetail.LineEditorContainerComponent;
import org.flowframe.ui.component.domain.masterdetail.MasterDetailComponent;
import org.flowframe.ui.component.domain.note.NoteEditorComponent;
import org.flowframe.ui.component.domain.page.TaskPageComponent;
import org.flowframe.ui.component.domain.preferences.PreferencesEditorComponent;
import org.flowframe.ui.component.domain.referencenumber.ReferenceNumberEditorComponent;
import org.flowframe.ui.component.domain.search.SearchGridComponent;
import org.flowframe.ui.component.domain.table.GridComponent;
import org.flowframe.ui.component.domain.table.MatchGridComponent;
import org.flowframe.ui.services.factory.IComponentFactory;
import org.flowframe.ui.vaadin.addons.common.FlowFrameAbstractSplitPanel.ISplitPositionChangeListener;
import org.flowframe.ui.vaadin.addons.common.FlowFrameVerticalSplitPanel;
import org.flowframe.ui.vaadin.editors.entity.vaadin.ext.search.EntitySearchGrid;
import org.flowframe.ui.vaadin.forms.impl.VaadinCollapsibleSectionForm;
import org.flowframe.ui.vaadin.forms.impl.VaadinSimpleForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.EventBusManager;
import org.vaadin.mvp.presenter.IPresenter;
import org.vaadin.mvp.presenter.PresenterFactory;

import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

public class VaadinPageFactoryImpl {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private PresenterFactory presenterFactory;
	private Map<String, Object> config;

	public VaadinPageFactoryImpl(Map<String, Object> config) {
		this.config = config;
		this.presenterFactory = new PresenterFactory(new EventBusManager(), Locale.getDefault());
	}

	public VaadinPageFactoryImpl(Map<String, Object> config, PresenterFactory presenterFactory) {
		this.config = config;
		this.presenterFactory = presenterFactory;
	}

	public Component createComponent(AbstractComponent componentModel) {
		assert componentModel != null: "componentModel must be not null";
		if (componentModel instanceof MasterDetailComponent) {
			FlowFrameVerticalSplitPanel splitPanel = new FlowFrameVerticalSplitPanel();
			splitPanel.setSizeFull();
			splitPanel.setSplitPosition(50);

			final Component firstComponent = create(((MasterDetailComponent) componentModel).getMasterComponent());
			final Component secondComponent = create(((MasterDetailComponent) componentModel).getLineEditorPanel());

			splitPanel.setFirstComponent(firstComponent);
			splitPanel.setSecondComponent(secondComponent);

			splitPanel.addSplitPositionChangeListener(new ISplitPositionChangeListener() {

				@Override
				public void onSplitPositionChanged(Integer newPos, int posUnit, boolean posReversed) {
				}

				@Override
				public void onSecondComponentHeightChanged(int height) {
				}

				@Override
				public void onFirstComponentHeightChanged(int height) {
					if (height > 0) {
						firstComponent.setHeight(height, com.vaadin.terminal.Sizeable.UNITS_PIXELS);
					}
				}
			});

			return splitPanel;
		} else if (componentModel instanceof SearchGridComponent) {
			EntitySearchGrid component = new EntitySearchGrid((SearchGridComponent) componentModel);
			component.setStatusEnabled(true);
			return component;
		} else if (componentModel instanceof CollapsibleConfirmActualsFormComponent) {
			return new VaadinCollapsibleConfirmActualsForm((CollapsibleConfirmActualsFormComponent) componentModel);
		} else if (componentModel instanceof CollapseableSectionFormComponent) {
			return new VaadinCollapsibleSectionForm((CollapseableSectionFormComponent) componentModel);
		} else if (componentModel instanceof MatchGridComponent) {
			return new VaadinMatchGrid((MatchGridComponent) componentModel);
		} else if (componentModel instanceof CollapsiblePhysicalAttributeConfirmActualsFormComponent) {
			return new VaadinCollapsiblePhysicalAttributeConfirmActualsForm((CollapsiblePhysicalAttributeConfirmActualsFormComponent) componentModel);
		} else if (componentModel instanceof SimpleFormComponent) {
			return new VaadinSimpleForm((SimpleFormComponent) componentModel);
		}
		else
			throw new IllegalArgumentException(componentModel.getCaption() + "is not support");
		// FIXME this should return null
		//return new VerticalLayout();
	}

	public Component create(AbstractComponent componentModel) {
		try {
			if (correspondsToPresenter(componentModel)) {
				IPresenter<?, ? extends EventBus> presenter = createPresenter(componentModel, new HashMap<String, Object>(config));
				return (Component) presenter.getView();
			} else {
				return createComponent(componentModel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new VerticalLayout();
	}

	public IPresenter<?, ? extends EventBus> createPresenter(AbstractComponent componentModel, Map<String, Object> params) throws Exception {
		params.put(IComponentFactory.COMPONENT_MODEL, componentModel);
		params.put(IComponentFactory.VAADIN_COMPONENT_FACTORY, this);

		IPresenter<?, ? extends EventBus> presenter = null;
		if (componentModel instanceof LineEditorContainerComponent) {
			presenter = presenterFactory.createPresenter(EntityLineEditorPresenter.class);
		} else if (componentModel instanceof LineEditorComponent) {
			presenter = presenterFactory.createPresenter(EntityLineEditorSectionPresenter.class);
		} else if (componentModel instanceof MultiLevelEntityEditorComponent) {
			presenter = presenterFactory.createPresenter(MultiLevelEditorPresenter.class);
		} else if (FormComponent.class.isAssignableFrom(componentModel.getClass()) || GridComponent.class.isAssignableFrom(componentModel.getClass())) {
			presenter = presenterFactory.createPresenter(MasterSectionPresenter.class);
		}

		if (presenter instanceof IConfigurablePresenter) {
			((IConfigurablePresenter) presenter).onConfigure(params);
		}

		return presenter;
	}

	public IPresenter<?, ? extends EventBus> createMasterSectionHeaderPresenter(AbstractComponent componentModel) throws Exception {
		IPresenter<?, ? extends EventBus> presenter = null;
		if (FormComponent.class.isAssignableFrom(componentModel.getClass())) {
			presenter = this.presenterFactory.createPresenter(EditorFormHeaderPresenter.class);
		} else if (GridComponent.class.isAssignableFrom(componentModel.getClass())) {
			return null;
		}

		return presenter;
	}

	public IPresenter<?, ? extends EventBus> createMasterSectionContentPresenter(AbstractComponent componentModel, Map<String, Object> params) throws Exception {
		params.put(IComponentFactory.COMPONENT_MODEL, componentModel);
		params.put(IComponentFactory.VAADIN_COMPONENT_FACTORY, this);

		IPresenter<?, ? extends EventBus> presenter = null;
		if (FormComponent.class.isAssignableFrom(componentModel.getClass())) {
			presenter = presenterFactory.createPresenter(EditorFormPresenter.class);
		}

		if (presenter instanceof IConfigurablePresenter) {
			((IConfigurablePresenter) presenter).onConfigure(params);
		}

		return presenter;
	}

	public IPresenter<?, ? extends EventBus> createLineEditorSectionContentPresenter(AbstractComponent componentModel, Map<String, Object> params)
			throws Exception {
		params.put(IComponentFactory.COMPONENT_MODEL, componentModel);
		params.put(IComponentFactory.VAADIN_COMPONENT_FACTORY, this);

		IPresenter<?, ? extends EventBus> presenter = null;
		if (componentModel instanceof AttachmentEditorComponent) {
			presenter = presenterFactory.createPresenter(AttachmentEditorPresenter.class);
		} else if (componentModel instanceof ReferenceNumberEditorComponent) {
			presenter = presenterFactory.createPresenter(ReferenceNumberEditorPresenter.class);
		} else if (componentModel instanceof PreferencesEditorComponent) {
			presenter = presenterFactory.createPresenter(PreferencesEditorPresenter.class);
		} else if (componentModel instanceof NoteEditorComponent) {
			presenter = presenterFactory.createPresenter(NotesEditorPresenter.class);
		} else if (GridComponent.class.isAssignableFrom(componentModel.getClass())) {
			presenter = presenterFactory.createPresenter(EntityLineEditorGridPresenter.class);
		} else if (FormComponent.class.isAssignableFrom(componentModel.getClass())) {
			presenter = presenterFactory.createPresenter(EntityLineEditorFormPresenter.class);
		}

		if (presenter instanceof IConfigurablePresenter) {
			((IConfigurablePresenter) presenter).onConfigure(params);
		}

		return presenter;
	}

	public IPresenter<?, ? extends EventBus> createLineEditorSectionHeaderPresenter(AbstractComponent componentModel) throws Exception {
		IPresenter<?, ? extends EventBus> presenter = null;
		if (FormComponent.class.isAssignableFrom(componentModel.getClass())) {
			presenter = this.presenterFactory.createPresenter(EntityLineEditorFormHeaderPresenter.class);
		} else if (GridComponent.class.isAssignableFrom(componentModel.getClass())) {
			presenter = this.presenterFactory.createPresenter(EntityLineEditorGridHeaderPresenter.class);
		}

		return presenter;
	}

	public IPageComponent createPage(final IPageFlowPage page, Map<String, Object> initParams) {
		if (isInstanceOf(page, IModelDrivenPageFlowPage.class)) {
			PagePresenter pagePresenter = (PagePresenter) presenterFactory.createPresenter(PagePresenter.class);
			pagePresenter.setPage(page);
			this.config.putAll(initParams);

			AbstractComponent componentModel = ((TaskPageComponent) ((IModelDrivenPageFlowPage) page).getComponentModel()).getContent();
			pagePresenter.setPageContent(create(componentModel));
			pagePresenter.setDataHandler(new PagePresenterDataHandler());
			pagePresenter.init(this.config);

			return pagePresenter;
		} else if (isInstanceOf(page, ICustomDrivenPageFlowPage.class)) {
			IPageComponent pageComponent = ((ICustomDrivenPageFlowPage) page).getPageComponent();
			return pageComponent;
		} else {
			return null;
		}
	}

	public boolean isInstanceOf(IPageFlowPage page, Class<?> type) {
		return page.getType().isAssignableFrom(type);
	}

	public boolean correspondsToPresenter(AbstractComponent componentModel) {
		if (componentModel != null
				&& (componentModel instanceof LineEditorContainerComponent || componentModel instanceof LineEditorComponent
						|| componentModel instanceof MultiLevelEntityEditorComponent || FormComponent.class.isAssignableFrom(componentModel.getClass()) || GridComponent.class
							.isAssignableFrom(componentModel.getClass()))) {
			return true;
		}
		return false;
	}

	public PresenterFactory getPresenterFactory() {
		return this.presenterFactory;
	}

	public Map<String, Object> getConfig() {
		return this.config;
	}

	private class PagePresenterDataHandler implements IPageDataHandler {

		@Override
		public void setParameterData(PagePresenter source, Map<String, Object> params) {
			try {
				VaadinPageDataBuilder
						.applyParamData(source.getConfig(), source.getPageContent(), params, VaadinPageFactoryImpl.this.presenterFactory);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public Object getResultData(PagePresenter source) {
			Map<String, Object> resultDataMap = new HashMap<String, Object>(VaadinPageDataBuilder.buildResultDataMap(source.getParameterData(),
					VaadinPageDataBuilder.buildResultData(source.getPageContent()), source.getPage().getResultKeyMap()));
			return resultDataMap;
		}

	}
}
