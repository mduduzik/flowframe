package org.flowframe.ui.vaadin.common.mvp;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.flowframe.kernel.common.mdm.domain.application.Application;
import org.flowframe.kernel.common.mdm.domain.application.Feature;
import org.flowframe.kernel.common.utils.Validator;
import org.flowframe.ui.services.contribution.IApplicationViewContribution;
import org.flowframe.ui.vaadin.common.mvp.view.IMainView;
import org.flowframe.ui.vaadin.common.mvp.view.MainView;
import org.flowframe.ui.vaadin.common.ui.menu.app.AppMenuEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.presenter.BasePresenter;
import org.vaadin.mvp.presenter.IPresenter;
import org.vaadin.mvp.presenter.IPresenterFactory;
import org.vaadin.mvp.presenter.annotation.Presenter;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.filter.Not;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.terminal.Resource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.Window;

@Presenter(view = MainView.class)
public class MainPresenter extends BasePresenter<IMainView, MainEventBus>
		implements Property.ValueChangeListener {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private MainMVPApplication application;

	private IPresenter<?, ? extends EventBus> contentPresenter;

	private EntityManagerFactory kernelSystemEntityManagerFactory;

	private EntityManager kernelSystemEntityManager;

	private JPAContainer<Application> launchableAppsContainer;

	private Map<String, Tab> appName2TabMap = new HashMap<String, Tab>();
	private Map<String, Class<? extends BasePresenter<?, ? extends EventBus>>> appName2PresenterMap = new HashMap<String, Class<? extends BasePresenter<?, ? extends EventBus>>>();

	private IndexedContainer appSelectionContainer;

	private boolean initialized = false;

	/**
	 * 
	 * EventBus callbacks
	 * 
	 */
	public void onStart(MainMVPApplication app) {
		try {
			// keep a reference to the application instance
			this.application = app;

			/**
			 * this.kernelSystemEntityManagerFactory =
			 * this.application.getKernelSystemEntityManagerFactory();
			 * this.kernelSystemEntityManager =
			 * this.kernelSystemEntityManagerFactory.createEntityManager();
			 * 
			 * this.launchableAppsContainer =
			 * JPAContainerFactory.make(Application
			 * .class,this.kernelSystemEntityManager);
			 * this.view.getAppSelection(
			 * ).setContainerDataSource(this.launchableAppsContainer);
			 */
			this.view.addAppSelectionListener(MainPresenter.this);
			// set the applications main windows (the view)
			this.application.setMainWindow((Window) this.view);

			// load the workspace presenter
			
			//By default, add workspace
			IApplicationViewContribution ac = app.getApplicationContributionByCode("KERNEL.WORKSPACE");
			if (Validator.isNotNull(ac))
			{
				Class<? extends BasePresenter<?, ? extends EventBus>> acClass = ac.getPresenterClass();
				onOpenApplication(acClass,ac.getName(),ac.getIcon(),false);
			}
			
			initialized = true;
			//AppMenuEntry[] entries = createAppMenuEntries();
			//mainEventBus.updateAppContributions(entries);	
			appSelectionContainer = new IndexedContainer();			
			appSelectionContainer.addContainerProperty("code", String.class, null);
			appSelectionContainer.addContainerProperty("name", String.class, null);
			appSelectionContainer.addContainerProperty("id", String.class, null);
			appSelectionContainer.addContainerProperty("icon", Resource.class, null);
			appSelectionContainer.addContainerProperty("launchable", Component.class, null);
			appSelectionContainer.addContainerProperty("presenterClass", Object.class, null);
			Filter filter = new Not(new SimpleStringFilter("id","KERNEL.WORKSPACE", true, false));
			appSelectionContainer.addContainerFilter(filter);
			AppMenuEntry[] menuEntries = this.application.createAppMenuEntries();	
			updateAppSelectContainer(menuEntries);
			this.view.setAppSelectionContainer(appSelectionContainer, "id", "name", "icon");
			this.initialized  = true;
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
		}
	}

	/**
	 * 
	 * App tab callback methods
	 *
	 */	
	public void onUpdateAppContributions(AppMenuEntry[] appMenuEntries) {
		try {
			updateAppSelectContainer(appMenuEntries);
			initialized = true;
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void onAddAppContribution(AppMenuEntry appMenuEntry) {
		if (initialized)
		{
			String id = appMenuEntry.getCode();
			String name = appMenuEntry.getCaption();
			if (!appSelectionContainer.containsId(id))
			{
				try {
					Item item = this.appSelectionContainer.addItem(id);
					item.getItemProperty("launchable").setValue(appMenuEntry.getLaunchableAppComponent());
					item.getItemProperty("name").setValue(name);
					item.getItemProperty("id").setValue(id);
					item.getItemProperty("icon").setValue(new ThemeResource(appMenuEntry.getIconPath()));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Error e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		}
	}	
	
	public void onRemoveAppContribution(AppMenuEntry appMenuEntry) {
		if (initialized)
		{
			String id = appMenuEntry.getCode();
			if (appSelectionContainer.containsId(id))
			{
				appSelectionContainer.removeItem(id);
				removeAppComponent(appMenuEntry.getCaption());
			}
		}
	}		

	private void removeAppComponent(String caption) {
		Tab tab = appName2TabMap.get(caption);
		this.view.getApplicationTabSheet().removeTab(tab);
	}

	/**
	 * 
	 * Local methods
	 *
	 */
	public IndexedContainer updateAppSelectContainer(
			AppMenuEntry[] appMenuEntries) {
		fillAppSelectContainer(appSelectionContainer, appMenuEntries);
		return appSelectionContainer;
	}

	private void fillAppSelectContainer(IndexedContainer container,
			AppMenuEntry[] appMenuEntries) {
		for (int i = 0; i < appMenuEntries.length; i++) {
			String name = appMenuEntries[i].getCaption();
			String id = appMenuEntries[i].getCode();
			Item item = container.addItem(id);
			item.getItemProperty("launchable").setValue(
					appMenuEntries[i].getLaunchableAppComponent());
			item.getItemProperty("presenterClass").setValue(
					appMenuEntries[i].getAppPresenterClass());			
			item.getItemProperty("name").setValue(name);
			item.getItemProperty("id").setValue(id);
			item.getItemProperty("icon").setValue(
					new ThemeResource(appMenuEntries[i].getIconPath()));
		}
		appSelectionContainer.sort(new Object[] { "name" }, new boolean[] { true });		
	}

	public void onOpenModule(
			Class<? extends BasePresenter<?, ? extends EventBus>> presenter) {
		// load the menu presenter
		IPresenterFactory pf = application.getPresenterFactory();
		this.contentPresenter = pf.createPresenter(presenter);
		// this.view.setContent((Component) this.contentPresenter.getView());
	}

	public void onOpenApplication(
			Class<? extends BasePresenter<?, ? extends EventBus>> appPresenterClass,
			String name, String iconPath,
			boolean closable) {
		try {
			openApplicationTab(appPresenterClass,name,iconPath,true);
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
		}
	}
	
	public void onOpenApplicationFeature(Feature appFeature) {
		try {
			Application app = appFeature.getParentApplication();
			IApplicationViewContribution ac = application.getApplicationContributionByCode(app.getCode());
			IPresenter<?, ? extends EventBus> appPresenter = null;
			StartableApplicationEventBus wsEventBus = null;
			if (Validator.isNotNull(ac))
			{
				if (appName2TabMap.containsKey(ac.getName()))
				{
					Class<? extends BasePresenter<?, ? extends EventBus>> appPresenterClass = appName2PresenterMap.get(ac.getName());
					IPresenterFactory pf = application.getPresenterFactory();
					appPresenter = pf.createPresenter(appPresenterClass);
					wsEventBus = (StartableApplicationEventBus) appPresenter.getEventBus();					
				}
				else
				{
					appPresenter = openApplicationTab(ac.getPresenterClass(), ac.getName(), ac.getIcon(), true);
					wsEventBus = (StartableApplicationEventBus)appPresenter.getEventBus();
				}
				
				wsEventBus.openFeatureView(appFeature);
			}
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
		}
	}	
	
	private IPresenter<?, ? extends EventBus> openApplicationTab(
			Class<? extends BasePresenter<?, ? extends EventBus>> appPresenterClass,
			String name, String iconPath,
			boolean closable) {
		IPresenter<?, ? extends EventBus> appPresenter = null;
		try {
			appPresenter = createAndStartApplication(appPresenterClass, name, iconPath, closable);
			Component ws = (Component) appPresenter.getView();
			((Layout) ws).setSizeFull();
			onOpenApplicationComponent(ws,name,iconPath,appPresenterClass,closable);			
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
		}
		
		return appPresenter;
	}	
	
	private IPresenter<?, ? extends EventBus> createAndStartApplication(Class<? extends BasePresenter<?, ? extends EventBus>> appPresenterClass,
			String name, String iconPath,
			boolean closable)
	{
		IPresenter<?, ? extends EventBus> appPresenter = null;
		try {
			IPresenterFactory pf = application.getPresenterFactory();
			appPresenter = pf
					.createPresenter(appPresenterClass);
			StartableApplicationEventBus wsEventBus = (StartableApplicationEventBus) appPresenter
					.getEventBus();
			wsEventBus.start(this.application);
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
		}	
		
		return appPresenter;
	}
	

	public void onOpenApplicationComponent(Component appComponent,
			String name, String iconPath,Class<? extends BasePresenter<?, ? extends EventBus>> presenterClass, boolean closable) {
		try {
			if (appName2TabMap.containsKey(name)) {
				Tab selectedTab = appName2TabMap.get(name);
				this.view.getApplicationTabSheet().setSelectedTab(selectedTab.getComponent());
			} else {
				appComponent.setSizeFull();
				Tab newTab = this.view.getApplicationTabSheet().addTab(appComponent, name, new ThemeResource(iconPath));
				newTab.setClosable(closable);
				this.view.getApplicationTabSheet().setSelectedTab(appComponent);
				appName2TabMap.put(name, newTab);
				appName2PresenterMap.put(name, presenterClass);
			}
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
		}
	}

	public void onShowDialog(Window dialog) {
		this.application.getMainWindow().addWindow(dialog);
	}

	@Override
	public void bind() {
	}

	/**
	 * OnApplicationSelection changed
	 */
	@Override
	public void valueChange(ValueChangeEvent event) {
		String id = event.getProperty().toString();
		Item menuEntry = (Item) this.appSelectionContainer.getItem(id);
		onOpenApplicationComponent(
				(Component) menuEntry.getItemProperty("launchable").getValue(),
				(String) menuEntry.getItemProperty("name").getValue(),
				((ThemeResource) menuEntry.getItemProperty("icon").getValue()).getResourceId(),
				(Class<? extends BasePresenter<?, ? extends EventBus>>)menuEntry.getItemProperty("presenterClass").getValue(),
				true);
	}
}
