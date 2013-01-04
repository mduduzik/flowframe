package org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.search.header;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.presenter.annotation.Presenter;

import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.ConfigurableBasePresenter;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.MultiLevelEntityEditorEventBus;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.MultiLevelEntityEditorPresenter;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.search.header.view.EntityGridHeaderView;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.search.header.view.IEntityGridHeaderView;
import com.conx.logistics.kernel.ui.factory.services.IEntityEditorFactory;
import com.conx.logistics.kernel.ui.service.contribution.IMainApplication;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@Presenter(view = EntityGridHeaderView.class)
public class EntityGridHeaderPresenter extends ConfigurableBasePresenter<IEntityGridHeaderView, EntityGridHeaderEventBus> {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	private boolean initialized = false;
	private MultiLevelEntityEditorPresenter parentPresenter;

	private MultiLevelEntityEditorPresenter multiLevelEntityEditorPresenter;
	@SuppressWarnings("unused")
	private IMainApplication mainApplication;
	private MultiLevelEntityEditorEventBus entityEditorEventListener;

	public EntityGridHeaderPresenter() {
		super();
	}

	public MultiLevelEntityEditorPresenter getParentPresenter() {
		return parentPresenter;
	}

	public void setParentPresenter(MultiLevelEntityEditorPresenter parentPresenter) {
		this.parentPresenter = parentPresenter;
	}

	@Override
	public void bind() {
		this.setInitialized(true);
		this.getView().init();
		this.getView().addCreateListener(new ClickListener() {
			private static final long serialVersionUID = -54023801L;

			@Override
			public void buttonClick(ClickEvent event) {
				entityEditorEventListener.createItem();
			}
		});
		this.getView().addEditListener(new ClickListener() {
			private static final long serialVersionUID = -52023801L;

			@Override
			public void buttonClick(ClickEvent event) {
				entityEditorEventListener.editItem();
			}
		});
		this.getView().addDeleteListener(new ClickListener() {
			private static final long serialVersionUID = -50023801L;

			@Override
			public void buttonClick(ClickEvent event) {
				entityEditorEventListener.deleteItem();
			}
		});
		this.getView().addPrintListener(new ClickListener() {
			private static final long serialVersionUID = -99023801L;

			@Override
			public void buttonClick(ClickEvent event) {
				entityEditorEventListener.printGrid();
			}
		});
		this.getView().addReportListener(new ClickListener() {
			private static final long serialVersionUID = -99023801L;

			@Override
			public void buttonClick(ClickEvent event) {
				entityEditorEventListener.reportItem();
			}
		});
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
	
	public void onItemSelected() {
		this.getView().setEditEnabled(true);
		this.getView().setDeleteEnabled(true);
		this.getView().setPrintEnabled(true);
		this.getView().setReportEnabled(true);
	}
	
	public void onItemsDepleted() {
		this.getView().setEditEnabled(false);
		this.getView().setDeleteEnabled(false);
		this.getView().setPrintEnabled(false);
		this.getView().setReportEnabled(false);
	}
}
