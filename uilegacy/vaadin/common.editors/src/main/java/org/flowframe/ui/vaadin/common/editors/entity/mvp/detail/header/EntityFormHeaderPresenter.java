package org.flowframe.ui.vaadin.common.editors.entity.mvp.detail.header;

import java.util.Map;

import org.flowframe.ui.pageflow.services.IMainApplication;
import org.flowframe.ui.services.factory.IComponentFactory;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.ConfigurableBasePresenter;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.MultiLevelEntityEditorEventBus;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.MultiLevelEntityEditorPresenter;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.detail.header.view.EntityFormHeaderView;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.detail.header.view.IEntityFormHeaderView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.presenter.annotation.Presenter;

import com.vaadin.data.Item;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@Presenter(view = EntityFormHeaderView.class)
public class EntityFormHeaderPresenter extends ConfigurableBasePresenter<IEntityFormHeaderView, EntityFormHeaderEventBus> {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	private boolean initialized = false;
	private MultiLevelEntityEditorPresenter parentPresenter;

	private MultiLevelEntityEditorPresenter multiLevelEntityEditorPresenter;
	@SuppressWarnings("unused")
	private IMainApplication mainApplication;
	private MultiLevelEntityEditorEventBus entityEditorEventListener;

	public EntityFormHeaderPresenter() {
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
		this.getView().addVerifyListener(new ClickListener() {
			private static final long serialVersionUID = -54023801L;

			@Override
			public void buttonClick(ClickEvent event) {
				validate();
			}
		});
		this.getView().addSaveListener(new ClickListener() {
			private static final long serialVersionUID = -52023801L;

			@Override
			public void buttonClick(ClickEvent event) {
				save();
			}
		});
		this.getView().addResetListener(new ClickListener() {
			private static final long serialVersionUID = -50023801L;

			@Override
			public void buttonClick(ClickEvent event) {
				reset();
			}
		});
	}
	
	public void validate() {
		this.getView().setVerifyEnabled(false);
		entityEditorEventListener.validateForm();
	}
	
	public void save() {
		entityEditorEventListener.saveForm();
	}
	
	public void reset() {
		this.getView().setVerifyEnabled(false);
		this.getView().setResetEnabled(false);
		entityEditorEventListener.resetForm();
	}

	public boolean isInitialized() {
		return initialized;
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

	@Override
	public void onConfigure(Map<String, Object> config) {
		super.setConfig(config);
		this.multiLevelEntityEditorPresenter = (MultiLevelEntityEditorPresenter) getConfig().get(IComponentFactory.FACTORY_PARAM_MVP_CURRENT_MLENTITY_EDITOR_PRESENTER);
		this.mainApplication = (IMainApplication) getConfig().get(IComponentFactory.FACTORY_PARAM_MAIN_APP);
		this.entityEditorEventListener = multiLevelEntityEditorPresenter.getEventBus();
	}
	
	public void onSetItemDataSource(Item item) {
	}
	
	public void onFormChanged() {
		if (this.getView().isSaveEnabled()) {
			this.getView().setSaveEnabled(false);
		}
		this.getView().setVerifyEnabled(true);
		this.getView().setResetEnabled(true);
	}
	
	public void onFormValidated() {
		this.getView().setSaveEnabled(true);
		this.getView().setVerifyEnabled(false);
	}
}
