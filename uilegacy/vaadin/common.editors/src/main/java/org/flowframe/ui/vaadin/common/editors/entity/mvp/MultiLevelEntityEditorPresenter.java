package org.flowframe.ui.vaadin.common.editors.entity.mvp;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import org.flowframe.kernel.common.mdm.domain.documentlibrary.FileEntry;
import org.flowframe.kernel.common.mdm.domain.metamodel.AbstractManagedType;
import org.flowframe.ui.component.domain.masterdetail.MasterDetailComponent;
import org.flowframe.ui.component.domain.table.GridComponent;
import org.flowframe.ui.services.factory.IComponentFactory;
import org.flowframe.ui.services.factory.IComponentFactoryManager;
import org.flowframe.ui.services.factory.IComponentModelFactory;
import org.flowframe.ui.vaadin.addons.common.FlowFrameAbstractSplitPanel.ISplitPositionChangeListener;
import org.flowframe.ui.vaadin.common.mvp.AbstractMainApplication;
import org.flowframe.ui.vaadin.common.mvp.ApplicationEventBus;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.breadcrumb.EntityBreadCrumbEventBus;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.breadcrumb.EntityBreadCrumbPresenter;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.detail.form.EntityFormEventBus;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.detail.header.EntityFormHeaderEventBus;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.detail.header.EntityFormHeaderPresenter;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.footer.EntityTableFooterPresenter;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.lineeditor.EntityLineEditorEventBus;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.lineeditor.EntityLineEditorPresenter;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.search.header.EntityGridHeaderPresenter;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.view.IMultiLevelEntityEditorView;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.view.MultiLevelEntityEditorView;
import org.flowframe.ui.vaadin.common.editors.ext.mvp.IConfigurablePresenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.EventBusManager;
import org.vaadin.mvp.presenter.IPresenter;
import org.vaadin.mvp.presenter.PresenterFactory;
import org.vaadin.mvp.presenter.annotation.Presenter;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.data.Item;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

@Presenter(view = MultiLevelEntityEditorView.class)
public class MultiLevelEntityEditorPresenter extends ConfigurableBasePresenter<IMultiLevelEntityEditorView, MultiLevelEntityEditorEventBus>
		implements ISplitPositionChangeListener {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	private boolean initialized = false;
	private EventBusManager ebm = null;
	private ConfigurablePresenterFactory presenterFactory = null;
	private IPresenter breadCrumbPresenter;
	private IPresenter lineEditorPresenter;
	private EntityLineEditorEventBus lineEditorBus;
	private IPresenter footerPresenter;
	private MasterDetailComponent metaData;
	private ApplicationEventBus appEventBus;
	private MultiLevelEntityEditorPresenter parentEditor;
	private Map<MasterDetailComponent, MultiLevelEntityEditorPresenter> childEditorPresenterMap;
	private IComponentModelFactory entityFactory;
	private IConfigurablePresenter masterPresenter;
	private IPresenter headerPresenter;
	private AbstractMainApplication mainApplication;
	private IComponentFactoryManager componentFactoryManager;

	public void onInit(EventBusManager ebm, PresenterFactory presenterFactory, MasterDetailComponent md, EntityManager em,
			HashMap<String, Object> extraParams) {
		this.setInitialized(true);
		this.metaData = md;
		this.setEbm(ebm);
	}

	@SuppressWarnings("rawtypes")
	public void onEntityItemEdit(EntityItem item) {
		this.getView().showDetail();
		((AbstractEntityEditorEventBus) lineEditorBus).entityItemEdit(item);
	}

	@SuppressWarnings("rawtypes")
	public void onEntityItemAdded(EntityItem item) {
		((AbstractEntityEditorEventBus) lineEditorBus).entityItemAdded(item);
	}

	public void onReportItem(Object itemEntity) {
		// FIXME: MLE should not need whse.im
//		Application app = ((Component) this.getView()).getApplication();
//		if (app instanceof MainMVPApplication && itemEntity instanceof StockItem) {
//			String url = ((MainMVPApplication) app).getDaoProvider().provideByDAOClass(IStockItemDAOService.class)
//					.getStockItemLabelUrl((StockItem) itemEntity, ((MainMVPApplication) app).getReportingUrl());
//			if (url != null) {
//				this.appEventBus.openDocument(url, "Label for " + ((StockItem) itemEntity).getCode());
//			}
//		}
	}

	public void onSetItemDataSource(Item item) {
		if (breadCrumbPresenter != null) {
			((EntityBreadCrumbEventBus) breadCrumbPresenter.getEventBus()).setItemDataSource(item);
		}

		((EntityFormHeaderEventBus) headerPresenter.getEventBus()).setItemDataSource(item);
		((EntityFormEventBus) ((IPresenter)masterPresenter).getEventBus()).setItemDataSource(item);
		lineEditorBus.setItemDataSource(item);
	}

	public void onViewDocument(FileEntry fileEntry) {
		this.appEventBus.openDocument(fileEntry);
	}

	public void onShowPresenter(ConfigurableBasePresenter<?, ? extends EventBus> presenter) {
		VerticalLayout editorContainer = (VerticalLayout) getConfig().get(IComponentFactory.FACTORY_PARAM_MVP_EDITOR_CONTAINER);
		Component childEditorView = (Component) presenter.getView();
		editorContainer.removeAllComponents();
		editorContainer.addComponent(childEditorView);
		editorContainer.setExpandRatio(childEditorView, 1.0f);
	}

	public void onEditItem(Item item, MasterDetailComponent componentModel) {
		getConfig().put(IComponentFactory.FACTORY_PARAM_MVP_ITEM_DATASOURCE, item);

		if (childEditorPresenterMap == null) {
			childEditorPresenterMap = new HashMap<MasterDetailComponent, MultiLevelEntityEditorPresenter>();
		}

		MultiLevelEntityEditorPresenter childEditorPresenter = childEditorPresenterMap.get(componentModel);
		if (childEditorPresenter == null) {
			getConfig().put(IComponentFactory.FACTORY_PARAM_MVP_PARENT_EDITOR, this);
			getConfig().put(IComponentFactory.FACTORY_PARAM_MVP_CURRENT_DATA_ITEM, item);
			
			Map<IPresenter<?, ? extends EventBus>, EventBus> res = (Map<IPresenter<?, ? extends EventBus>, EventBus>) this.entityFactory.create(componentModel, getConfig());
			childEditorPresenter = (MultiLevelEntityEditorPresenter) res.keySet().iterator().next();
			childEditorPresenterMap.put(componentModel, childEditorPresenter);
		}

		onShowPresenter(childEditorPresenter);
	}

	@Override
	public void onConfigure(Map<String, Object> config) {
		super.setConfig(config);
		this.parentEditor = (MultiLevelEntityEditorPresenter) config.get(IComponentFactory.FACTORY_PARAM_MVP_PARENT_EDITOR);
		this.metaData = (MasterDetailComponent) config.get(IComponentFactory.FACTORY_PARAM_MVP_COMPONENT_MODEL);
		this.presenterFactory = (ConfigurablePresenterFactory) config.get(IComponentFactory.FACTORY_PARAM_MVP_PRESENTER_FACTORY);
		this.ebm = this.presenterFactory.getEventBusManager();

		this.mainApplication = (AbstractMainApplication)config.get(IComponentFactory.FACTORY_PARAM_MAIN_APP);
		this.componentFactoryManager = (IComponentFactoryManager)this.mainApplication.getComponentFactoryManager();
		this.entityFactory = this.mainApplication.getComponentFactory();
		//this.entityFactory.setConfigurablePresenterFactory(presenterFactory);
		
		this.presenterFactory.getCustomizer().getConfig().put(IComponentFactory.FACTORY_PARAM_MVP_CURRENT_MLENTITY_EDITOR_PRESENTER, this);


		config.put(IComponentFactory.FACTORY_PARAM_MVP_PARENT_EDITOR, this);
		IMultiLevelEntityEditorView localView = this.getView();
		localView.init();
		localView.addSplitPositionChangeListener(this);

		if (isDetailEditor()) {
			breadCrumbPresenter = (ConfigurableBasePresenter<?, ? extends EventBus>) this.presenterFactory
					.createPresenter(EntityBreadCrumbPresenter.class);
			localView.setBreadCrumb((Component) breadCrumbPresenter.getView());
			headerPresenter = (ConfigurableBasePresenter<?, ? extends EventBus>) this.presenterFactory
					.createPresenter(EntityFormHeaderPresenter.class);
			localView.setHeader((Component) headerPresenter.getView());
		} else {
			headerPresenter = (ConfigurableBasePresenter<?, ? extends EventBus>) this.presenterFactory
					.createPresenter(EntityGridHeaderPresenter.class);
			localView.setHeader((Component) headerPresenter.getView());
		}

		Map<IPresenter<?, ? extends EventBus>, EventBus> res = (Map<IPresenter<?, ? extends EventBus>, EventBus>) this.entityFactory.create(this.metaData.getMasterComponent(), getConfig());
		this.masterPresenter = (IConfigurablePresenter) res.keySet().iterator().next();
		localView.setMaster((Component) ((IPresenter)masterPresenter).getView());

		config.put(IComponentFactory.FACTORY_PARAM_MVP_COMPONENT_MODEL, this.metaData.getLineEditorPanel());
		lineEditorPresenter = (ConfigurableBasePresenter<?, ? extends EventBus>) this.presenterFactory.createPresenter(EntityLineEditorPresenter.class);
		lineEditorBus = (EntityLineEditorEventBus) lineEditorPresenter.getEventBus();
		localView.setDetail((Component) lineEditorPresenter.getView(), !isDetailEditor());

		footerPresenter = (ConfigurableBasePresenter<?, ? extends EventBus>) this.presenterFactory
				.createPresenter(EntityTableFooterPresenter.class);
		localView.setFooter((Component) footerPresenter.getView());
		// VerticalLayout editorContainer = (VerticalLayout)
		// getConfig().get(IEntityEditorFactory.FACTORY_PARAM_MVP_EDITOR_CONTAINER);
		// Tab editorTab = (Tab) editorContainer.getParent();
		// editorTab.setCaption(this.metaData.getCaption());
		if (isDetailEditor()) {
			// editorTab.setIcon(new
			// ThemeResource("toolstrip/img/conx-bread-crumb-record-highlighted.png"));
			onSetItemDataSource((Item) getConfig().get(IComponentFactory.FACTORY_PARAM_MVP_ITEM_DATASOURCE));
		} else {
			// editorTab.setIcon(new
			// ThemeResource("breadcrumb/img/conx-bread-crumb-grid-highlighted.png"));
		}

		this.setInitialized(true);
	}
	
	
	protected IComponentModelFactory createComponentFactory() {
		this.entityFactory = this.componentFactoryManager.create(new HashMap<String,Object>(), presenterFactory);//new VaadinEntityEditorFactoryImpl(presenterFactory);
		return this.entityFactory;
	}

	@Override
	public void bind() {
	}

	public boolean isDetailEditor() {
		return !(this.metaData.getMasterComponent() instanceof GridComponent);
	}

	public boolean isInitialized() {
		return initialized;
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

	public EventBusManager getEbm() {
		return ebm;
	}

	public void setEbm(EventBusManager ebm) {
		this.ebm = ebm;
	}

	public MasterDetailComponent getMetaData() {
		return metaData;
	}

	public void setMetaData(MasterDetailComponent metaData) {
		this.metaData = metaData;
	}

	public MultiLevelEntityEditorPresenter getParentEditor() {
		return parentEditor;
	}

	public void setParentEditor(MultiLevelEntityEditorPresenter parentEditor) {
		this.parentEditor = parentEditor;
	}

	@Override
	public void onSplitPositionChanged(Integer newPos, int posUnit, boolean posReversed) {
	}

	@Override
	public void onFirstComponentHeightChanged(int height) {
		getEventBus().resizeForm(height);
	}

	@Override
	public void onSecondComponentHeightChanged(int height) {
	}
}
