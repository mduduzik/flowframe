package org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.preferences;

import org.flowframe.kernel.common.mdm.domain.BaseEntity;
import org.flowframe.kernel.common.mdm.domain.preferences.EntityPreferenceItem;
import org.flowframe.ui.services.contribution.IMainApplication;
import org.flowframe.ui.services.factory.IComponentFactory;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.ConfigurableBasePresenter;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.preferences.view.IPreferencesEditorView;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.preferences.view.PreferencesEditorView;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.preferences.view.PreferencesEditorView.ICreatePreferenceListener;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.preferences.view.PreferencesEditorView.IDeletePreferenceListener;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.preferences.view.PreferencesEditorView.IEditPreferenceListener;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.preferences.view.PreferencesEditorView.ISavePreferenceListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.presenter.annotation.Presenter;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.Item;
import com.vaadin.data.util.filter.Compare.Equal;

@Presenter(view = PreferencesEditorView.class)
public class PreferencesEditorPresenter extends ConfigurableBasePresenter<IPreferencesEditorView, PreferencesEditorEventBus> {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	private boolean initialized = false;
	private Item newEntityItem;
	private BaseEntity bean;
	private IMainApplication mainApplication;
	private JPAContainer<EntityPreferenceItem> container;

	@SuppressWarnings("unchecked")
	private void initialize() {
		this.container = (JPAContainer<EntityPreferenceItem>) this.mainApplication.createPersistenceContainer(EntityPreferenceItem.class);

		ICreatePreferenceListener createListener = new ICreatePreferenceListener() {

			@Override
			public void onCreatePreference() {
				PreferencesEditorPresenter.this.newEntityItem = PreferencesEditorPresenter.this.container.createEntityItem(new EntityPreferenceItem());
				PreferencesEditorPresenter.this.getView().create(PreferencesEditorPresenter.this.newEntityItem);
			}
		};
		IEditPreferenceListener editListener = new IEditPreferenceListener() {

			@Override
			public void onEditPreference(Item item) {
				PreferencesEditorPresenter.this.getView().edit(item);
			}
		};
		ISavePreferenceListener saveListener = new ISavePreferenceListener() {

			@Override
			public void onSavePreference(Item item) {
				PreferencesEditorPresenter.this.getView().save(item);
			}
		};
		IDeletePreferenceListener deleteListener = new IDeletePreferenceListener() {

			@Override
			public void onDeletePreference(Item item) {
				PreferencesEditorPresenter.this.getView().delete(item);
			}
		};

		this.getView().addCreatePreferenceListener(createListener);
		this.getView().addDeletePreferenceListener(deleteListener);
		this.getView().addSavePreferenceListener(saveListener);
		this.getView().addEditPreferenceListener(editListener);
		
		String[] columnPropertyIds = new String[] { "preferenceKey", "preferenceValue", "dateCreated" };
		String[] columnNames = new String[] { "Name", "Value", "Date Created" };
		
		this.getView().showContent();
		this.getView().setContainerDataSource(this.container, columnPropertyIds, columnNames);

		this.initialized = true;
	}

	public void onEntityItemEdit(EntityItem<?> item) {
		assert (item instanceof BaseEntity) : "The item bean was not of type BaseEntity.";
		this.bean = (BaseEntity) item.getEntity();

		if (!this.initialized) {
			initialize();
		}

		clearFilters();
		applyFilter();
	}

	public void onEntityItemAdded(EntityItem<?> item) {
		this.container.refresh();
	}

	public void onPreferenceAdded(EntityPreferenceItem preference) {
		this.container.addEntity(preference);
	}

	@Override
	public void configure() {
		this.mainApplication = (IMainApplication) getConfig().get(IComponentFactory.FACTORY_PARAM_MAIN_APP);
	}

	private void clearFilters() {
		this.container.removeAllContainerFilters();
	}

	private void applyFilter() {
		if (this.bean.getPreferences() != null) {
			Equal filter = new Equal("parentEntityPreference.id", this.bean.getPreferences().getId());
			this.container.addContainerFilter(filter);
			this.container.applyFilters();
		}
	}
}
