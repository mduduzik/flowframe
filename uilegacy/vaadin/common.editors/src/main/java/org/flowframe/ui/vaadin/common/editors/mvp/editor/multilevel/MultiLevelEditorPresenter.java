package org.flowframe.ui.vaadin.common.editors.mvp.editor.multilevel;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.flowframe.kernel.common.mdm.domain.documentlibrary.FileEntry;
import org.flowframe.ui.component.domain.editor.MultiLevelEntityEditorComponent;
import org.flowframe.ui.component.domain.masterdetail.MasterDetailComponent;
import org.flowframe.ui.services.factory.IComponentFactory;
import org.flowframe.ui.services.factory.IComponentFactoryManager;
import org.flowframe.ui.services.factory.IComponentModelFactory;
import org.flowframe.ui.vaadin.common.editors.ext.mvp.IConfigurablePresenter;
import org.flowframe.ui.vaadin.common.editors.ext.mvp.IContainerItemPresenter;
import org.flowframe.ui.vaadin.common.editors.ext.mvp.IVaadinDataComponent;
import org.flowframe.ui.vaadin.common.editors.mvp.editor.multilevel.view.IMultiLevelEditorView;
import org.flowframe.ui.vaadin.common.editors.mvp.editor.multilevel.view.MultiLevelEditorView;
import org.flowframe.ui.vaadin.common.mvp.ApplicationEventBus;
import org.vaadin.mvp.eventbus.EventBusManager;
import org.vaadin.mvp.presenter.BasePresenter;
import org.vaadin.mvp.presenter.IPresenter;
import org.vaadin.mvp.presenter.PresenterFactory;
import org.vaadin.mvp.presenter.annotation.Presenter;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.ui.Component;

@Presenter(view = MultiLevelEditorView.class)
public class MultiLevelEditorPresenter extends BasePresenter<IMultiLevelEditorView, MultiLevelEditorEventBus> implements IVaadinDataComponent, IConfigurablePresenter, IContainerItemPresenter {
	private IComponentModelFactory factory;
	private MultiLevelEntityEditorComponent componentModel;
	private Map<MasterDetailComponent, Component> editorCache;
	private Map<MasterDetailComponent, IComponentModelFactory> factoryCache;
	private Map<MasterDetailComponent, Item> itemDataSourceCache;
	private Stack<MasterDetailComponent> editorStack;
	private MasterDetailComponent originEditorComponent;
	private Map<String, Object> config;
	private ApplicationEventBus appEventBus;
	private IComponentFactoryManager factoryManager;

	@Override
	public void onConfigure(Map<String, Object> params) {
		this.editorCache = new HashMap<MasterDetailComponent, Component>();
		this.factoryCache = new HashMap<MasterDetailComponent, IComponentModelFactory>();
		this.itemDataSourceCache = new HashMap<MasterDetailComponent, Item>();
		this.editorStack = new Stack<MasterDetailComponent>();
		this.config = new HashMap<String, Object>(params);
		this.componentModel = (MultiLevelEntityEditorComponent) params.get(IComponentFactory.COMPONENT_MODEL);
		this.factory = (IComponentModelFactory) params.get(IComponentFactory.VAADIN_COMPONENT_FACTORY);
		this.factoryManager = (IComponentFactoryManager) params.get(IComponentFactory.VAADIN_COMPONENT_FACTORY);
		@SuppressWarnings("unchecked")
		IPresenter<?, ? extends ApplicationEventBus> appPresenter = (IPresenter<?, ? extends ApplicationEventBus>) config.get(IComponentFactory.FACTORY_PARAM_MVP_CURRENT_APP_PRESENTER);
		if (appPresenter != null) {
			this.appEventBus = appPresenter.getEventBus();
		}
		this.originEditorComponent = this.componentModel.getContent();

		this.getView().init();
		this.getView().setOwner(this);

		onRenderEditor(this.originEditorComponent);
	}

	/**
	 * Adds the MLE to the configuration of its sub-presenters and
	 * sub-components.
	 * 
	 * @param factoryConfig
	 * @return
	 */
	private Map<String, Object> adaptLocalizedFactoryConfig(Map<String, Object> factoryConfig) {
		factoryConfig = new HashMap<String, Object>(factoryConfig);
		factoryConfig.put(IComponentFactory.FACTORY_PARAM_MVP_CURRENT_MLENTITY_EDITOR_PRESENTER, this);
		return factoryConfig;
	}

	private IComponentModelFactory provideLocalizedFactory(MasterDetailComponent componentModel) {
		IComponentModelFactory localizedFactory = this.factoryCache.get(componentModel);
		if (localizedFactory == null) {
			PresenterFactory externalPresenterFactory = null;//((ConfigurablePresenterFactory)this.factory).getPresenterFactory(), localPresenterFactory = null;
			EventBusManager localEventBusManager = new EventBusManager();
			// Register this presenter and its event bus in the localized event
			// bus manager
			localEventBusManager.register(MultiLevelEditorEventBus.class, this);
			//localPresenterFactory = new PresenterFactory(localEventBusManager, externalPresenterFactory.getLocale());
			localizedFactory = null;//factoryManager.create(config, localPresenterFactory);
			// Store this localized factory in the cache
			this.factoryCache.put(componentModel, localizedFactory);
		}
		// Return the factory directly associated with the component model
		return localizedFactory;
	}

	private Component prepareEditor(MasterDetailComponent componentModel) {
		// Ensure that the top of the stack matches the referenced
		// componentModel
		if (this.editorStack.contains(componentModel)) {
			MasterDetailComponent highestEditorComponent = this.editorStack.peek();
			while (highestEditorComponent != null && !highestEditorComponent.equals(componentModel) && !highestEditorComponent.equals(this.originEditorComponent)) {
				this.editorStack.pop();
				highestEditorComponent = this.editorStack.peek();
			}
		} else {
			this.editorStack.push(componentModel);
		}
		// Get the Vaadin Component for the referenced componentModel
		Component editorComponent = this.editorCache.get(componentModel);
		if (editorComponent == null) {
			//editorComponent = provideLocalizedFactory(componentModel).create(componentModel);
			this.editorCache.put(componentModel, editorComponent);
		}
		// Update the Bread Crumb
		this.getView().updateBreadCrumb(this.editorStack.toArray(new MasterDetailComponent[] {}));

		return editorComponent;
	}

	public void onRenderEditor(MasterDetailComponent componentModel) {
		this.getView().setContent(prepareEditor(componentModel));
	}

	public void onRenderEditor(MasterDetailComponent componentModel, Item item, Container itemContainer) throws Exception {
		Component editorComponent = prepareEditor(componentModel);
		this.itemDataSourceCache.put(componentModel, item);
		IComponentModelFactory localizedFactory = provideLocalizedFactory(componentModel);
		//localizedFactory.getDataManager().applyItemDataSource(editorComponent, itemContainer, item, localizedFactory.getPresenterFactory(), this.config);
		this.getView().setContent(editorComponent);
	}

	@Override
	public Object getData() {
		Component originalEditor = this.editorCache.get(this.componentModel.getContent());
		IComponentModelFactory originalFactory = provideLocalizedFactory(this.componentModel.getContent());
		if (originalEditor != null && originalFactory != null) {
			//return originalFactory.getDataBuilder().buildResultData(originalEditor);
		}
		return null;
	}

	public MasterDetailComponent getCurrentEditorComponentModel() {
		return this.editorStack.peek();
	}

	@Override
	public void onSetItemDataSource(Item item, Container... containers) throws Exception {
		if (containers.length == 1) {
			MasterDetailComponent mdc = getCurrentEditorComponentModel();
			Component editorComponent = this.editorCache.get(mdc);
			IComponentModelFactory factory = provideLocalizedFactory(mdc);
			this.itemDataSourceCache.put(mdc, item);
			//factory.getDataManager().applyItemDataSource(editorComponent, containers[0], item, provideLocalizedFactory(mdc).getPresenterFactory(), this.config);
		} else {
			throw new Exception("Multi Level Editor supports one and only one container for onSetItemDataSource(Item, Container...)");
		}
	}

	public void onViewDocument(FileEntry viewable) throws Exception {
		if (this.appEventBus == null) {
			throw new Exception("The StartableApplicationEventBus was not provided in the configuration map.");
		} else {
			this.appEventBus.openDocument(viewable);
		}
	}

	/**
	 * Show a document tab with its content provided by the url. This is
	 * intended for use with reporting.
	 * 
	 * @param url
	 *            the url of the document
	 * @param caption
	 *            the caption of the feature
	 * @throws Exception
	 */
	public void onViewDocument(String url, String caption) throws Exception {
		if (this.appEventBus == null) {
			throw new Exception("The StartableApplicationEventBus was not provided in the configuration map.");
		} else {
			this.appEventBus.openDocument(url, caption);
		}
	}

	public Item getCurrentItemDataSource() {
		return this.itemDataSourceCache.get(getCurrentEditorComponentModel());
	}
}
