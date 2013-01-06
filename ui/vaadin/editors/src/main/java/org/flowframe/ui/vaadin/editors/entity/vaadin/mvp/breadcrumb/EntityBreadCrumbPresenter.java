package org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.breadcrumb;

import org.flowframe.ui.services.contribution.IMainApplication;
import org.flowframe.ui.services.factory.IEntityEditorFactory;
import org.flowframe.ui.vaadin.editors.entity.vaadin.ext.header.EntityEditorBreadCrumbItem;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.ConfigurableBasePresenter;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.MultiLevelEntityEditorEventBus;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.MultiLevelEntityEditorPresenter;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.breadcrumb.view.EntityBreadCrumbView;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.breadcrumb.view.IEntityBreadCrumbView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.presenter.annotation.Presenter;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainerItem;
import com.vaadin.data.Container;
import com.vaadin.data.Container.Indexed;
import com.vaadin.data.Item;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@Presenter(view = EntityBreadCrumbView.class)
public class EntityBreadCrumbPresenter extends ConfigurableBasePresenter<IEntityBreadCrumbView, EntityBreadCrumbEventBus> {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	private boolean initialized = false;

	private MultiLevelEntityEditorPresenter multiLevelEntityEditorPresenter;
	@SuppressWarnings("unused")
	private IMainApplication mainApplication;
	private MultiLevelEntityEditorEventBus entityEditorEventListener;
	private EntityItem<?> itemDataSource;
	private Container.Indexed containerDataSource;

	private int itemTotal = -1;
	private int itemIndex = -1;
	private String itemCode = null;

	@Override
	public void bind() {
		this.getView().init();
		if (!initialized) {
			this.recurseBreadCrumb(this.multiLevelEntityEditorPresenter);
			this.getView().addFirstItemListener(new ClickListener() {
				private static final long serialVersionUID = -882978210664264656L;

				@Override
				public void buttonClick(ClickEvent event) {
					firstItem();
				}
			});
			this.getView().addPreviousItemListener(new ClickListener() {
				private static final long serialVersionUID = 7639420529104417316L;

				@Override
				public void buttonClick(ClickEvent event) {
					previousItem();
				}
			});
			this.getView().addNextItemListener(new ClickListener() {
				private static final long serialVersionUID = -987379184334984250L;

				@Override
				public void buttonClick(ClickEvent event) {
					nextItem();
				}
			});
			this.getView().addLastItemListener(new ClickListener() {
				private static final long serialVersionUID = -6784306425542390096L;

				@Override
				public void buttonClick(ClickEvent event) {
					lastItem();
				}
			});
		}
		this.setInitialized(true);
	}

	private void firstItem() {
		Item item = this.containerDataSource.getItem(this.containerDataSource.firstItemId());
		entityEditorEventListener.setItemDataSource(item);
	}

	private void previousItem() {
		Item item = this.containerDataSource.getItem(this.containerDataSource.prevItemId(itemDataSource.getItemId()));
		entityEditorEventListener.setItemDataSource(item);
	}

	private void nextItem() {
		Item item = this.containerDataSource.getItem(this.containerDataSource.nextItemId(itemDataSource.getItemId()));
		entityEditorEventListener.setItemDataSource(item);
	}

	private void lastItem() {
		Item item = this.containerDataSource.getItem(this.containerDataSource.lastItemId());
		entityEditorEventListener.setItemDataSource(item);
	}

	private void updatePager() {
		if (this.itemDataSource != null) {
			this.itemTotal = this.containerDataSource.getItemIds().size();
			this.itemIndex = this.containerDataSource.indexOfId(this.itemDataSource.getItemId());
			this.itemCode = this.itemDataSource.getItemProperty("code").getValue().toString();
			this.getView().setFirstItemEnabled(true);
			this.getView().setPreviousItemEnabled(true);
			this.getView().setLastItemEnabled(true);
			this.getView().setNextItemEnabled(true);
			if (itemIndex == 0) {
				this.getView().setFirstItemEnabled(false);
				this.getView().setPreviousItemEnabled(false);
			}
			if (itemIndex == (itemTotal - 1)) {
				this.getView().setLastItemEnabled(false);
				this.getView().setNextItemEnabled(false);
			}
			this.getView().setPagerCaption(itemCode + " (Item " + (itemIndex + 1) + " of " + itemTotal + ")");
		}
	}

	private void recurseBreadCrumb(final MultiLevelEntityEditorPresenter presenter) {
		MultiLevelEntityEditorPresenter parent = presenter.getParentEditor();
		if (parent != null) {
			recurseBreadCrumb(parent);
		}

		EntityEditorBreadCrumbItem item = new EntityEditorBreadCrumbItem(presenter.getMetaData().hasGrid(), presenter.getMetaData().getName());
		if (!presenter.equals(multiLevelEntityEditorPresenter)) {
			item.addListener(new ClickListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void buttonClick(ClickEvent event) {
					EntityBreadCrumbPresenter.this.entityEditorEventListener.showPresenter(presenter);
				}
			});
		}
		this.getView().addBreadCrumbItem(item);
	}

	public boolean isInitialized() {
		return initialized;
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

	@Override
	public void configure() {
		this.multiLevelEntityEditorPresenter = (MultiLevelEntityEditorPresenter) getConfig().get(IEntityEditorFactory.FACTORY_PARAM_MVP_CURRENT_MLENTITY_EDITOR_PRESENTER);
		this.mainApplication = (IMainApplication) getConfig().get(IEntityEditorFactory.FACTORY_PARAM_MAIN_APP);
		this.entityEditorEventListener = multiLevelEntityEditorPresenter.getEventBus();
	}

	public void onSetItemDataSource(Item item) {
		if (item != null) {
			this.itemDataSource = (EntityItem<?>) item;
			this.containerDataSource = (Indexed) ((JPAContainerItem<?>) this.itemDataSource).getContainer();
			this.updatePager();
		}
	}
}
