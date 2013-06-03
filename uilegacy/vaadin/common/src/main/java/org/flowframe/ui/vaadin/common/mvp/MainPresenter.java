package org.flowframe.ui.vaadin.common.mvp;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.flowframe.ui.pageflow.services.IMainApplication;
import org.flowframe.ui.services.contribution.IApplicationContribution;
import org.flowframe.ui.vaadin.common.mvp.view.IMainView;
import org.flowframe.ui.vaadin.common.mvp.view.MainView;
import org.flowframe.ui.vaadin.common.ui.menu.app.AppMenuEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.presenter.BasePresenter;
import org.vaadin.mvp.presenter.IPresenter;
import org.vaadin.mvp.presenter.annotation.Presenter;

import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.terminal.Resource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet.Tab;

@Presenter(view = MainView.class)
public class MainPresenter extends BasePresenter<IMainView, MainEventBus> {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private IMainApplication application;
	private Map<String, Tab> appTabMap = new HashMap<String, Tab>();
	private Map<String, Class<? extends BasePresenter<?, ? extends EventBus>>> appName2PresenterMap = new HashMap<String, Class<? extends BasePresenter<?, ? extends EventBus>>>();

	private IndexedContainer appSelectionContainer;

	public void onStart(IMainApplication app) {
		// keep a reference to the application instance
		this.application = app;
		this.getView().addAppSelectionListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			@Override
			public void valueChange(ValueChangeEvent event) {
				String id = event.getProperty().toString();
				Item menuEntry = (Item) MainPresenter.this.appSelectionContainer.getItem(id);

				assert (menuEntry.getItemProperty("name").getValue() instanceof String) : "This application must have a \"name\" property of type String.";
				String appName = (String) menuEntry.getItemProperty("name").getValue();
				assert (menuEntry.getItemProperty("icon").getValue() instanceof ThemeResource) : "This application must have a \"icon\" property of type ThemeResource.";
				ThemeResource appIcon = (ThemeResource) menuEntry.getItemProperty("icon").getValue();
				assert (menuEntry.getItemProperty("presenterClass").getValue() instanceof Class<?>) : "This application must have a \"presenterClass\" property of type Class<? implements IPresenter<?, ? extends EventBus>>.";
				Class<? extends BasePresenter<?, ? extends EventBus>> appPresenterClass = null;
				try {
					appPresenterClass = (Class<? extends BasePresenter<?, ? extends EventBus>>) menuEntry.getItemProperty("presenterClass").getValue();
				} catch (ClassCastException e) {
					throw new RuntimeException("The application presenter class was not of type Class<? extends BasePresenter<?, ? extends EventBus>>.");
				}

				IPresenter<?, ? extends EventBus> appPresenter = MainPresenter.this.application.getPresenterFactory().createPresenter(appPresenterClass);
				if (appPresenter.getEventBus() instanceof ApplicationEventBus) {
					((ApplicationEventBus) appPresenter.getEventBus()).start(MainPresenter.this.application);
				}
				assert (appPresenter.getView() instanceof Component) : "The application presenter must have a view that implements com.vaadin.ui.Component.";
				Component appView = (Component) appPresenter.getView();

				onOpenApplicationComponent(appView, appName, appIcon.getResourceId(), appPresenterClass, true);
			}
		});
		
		this.appSelectionContainer = new IndexedContainer();
		this.appSelectionContainer.addContainerProperty("code", String.class, null);
		this.appSelectionContainer.addContainerProperty("name", String.class, null);
		this.appSelectionContainer.addContainerProperty("id", String.class, null);
		this.appSelectionContainer.addContainerProperty("icon", Resource.class, null);
		this.appSelectionContainer.addContainerProperty("presenterClass", Object.class, null);

		updateAppSelectContainer(createAppMenuEntries());
		this.view.setAppSelectionContainer(appSelectionContainer, "id", "name", "icon");
	}
	
	public Set<AppMenuEntry> createAppMenuEntries() {
		HashSet<AppMenuEntry> entries = new HashSet<AppMenuEntry>();
		
		for (IApplicationContribution appContribution : this.application.getAllApplicationContributions()) {
			entries.add(new AppMenuEntry(appContribution.getCode(), appContribution.getName(), appContribution.getIcon(), appContribution.getPresenterClass()));
		}
		
		return entries;
	}

	public IndexedContainer updateAppSelectContainer(Set<AppMenuEntry> entries) {
		for (AppMenuEntry entry : entries) {
			Item item = this.appSelectionContainer.addItem(entry.getCode());
			item.getItemProperty("name").setValue(entry.getCaption());
			item.getItemProperty("id").setValue(entry.getCode());
			item.getItemProperty("code").setValue(entry.getCode());
			item.getItemProperty("icon").setValue(new ThemeResource(entry.getIconPath()));
			item.getItemProperty("presenterClass").setValue(entry.getAppPresenterClass());
		}
		this.appSelectionContainer.sort(new Object[] { "name" }, new boolean[] { true });
		return this.appSelectionContainer;
	}

	public void onOpenApplicationComponent(Component appComponent, String name, String iconPath, Class<? extends BasePresenter<?, ? extends EventBus>> presenterClass, boolean closable) {
		try {
			if (appTabMap.containsKey(name)) {
				Tab selectedTab = appTabMap.get(name);
				this.view.getApplicationTabSheet().setSelectedTab(selectedTab.getComponent());
			} else {
				appComponent.setSizeFull();
				Tab newTab = this.view.getApplicationTabSheet().addTab(appComponent, name, new ThemeResource(iconPath));
				newTab.setClosable(closable);
				this.view.getApplicationTabSheet().setSelectedTab(appComponent);
				appTabMap.put(name, newTab);
				appName2PresenterMap.put(name, presenterClass);
			}
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
		}
	}
}
