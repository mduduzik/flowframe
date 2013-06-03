package org.flowframe.ui.pageflow.vaadin.mvp.editor.form.header;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.eventbus.EventBusManager;
import org.vaadin.mvp.presenter.BasePresenter;
import org.vaadin.mvp.presenter.annotation.Presenter;

import org.flowframe.ui.pageflow.vaadin.ext.mvp.ILocalizedEventSubscriber;
import org.flowframe.ui.pageflow.vaadin.mvp.editor.form.header.view.EditorFormHeaderView;
import org.flowframe.ui.pageflow.vaadin.mvp.editor.form.header.view.IEditorFormHeaderView;

import com.vaadin.data.Item;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@Presenter(view = EditorFormHeaderView.class)
public class EditorFormHeaderPresenter extends BasePresenter<IEditorFormHeaderView, EditorFormHeaderEventBus> implements ILocalizedEventSubscriber {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	private boolean initialized = false;
	private EventBusManager sectionEventBusManager;

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
		this.sectionEventBusManager.fireAnonymousEvent("validate");
	}

	public void save() {
		this.sectionEventBusManager.fireAnonymousEvent("save");
	}

	public void reset() {
		this.sectionEventBusManager.fireAnonymousEvent("reset");
	}

	public boolean isInitialized() {
		return initialized;
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

	public void onConfigure(Map<String, Object> params) {
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
	
	@Override
	public void subscribe(EventBusManager eventBusManager) {
		this.sectionEventBusManager = eventBusManager;
		this.sectionEventBusManager.register(EditorFormHeaderEventBus.class, this);
	}
	
	public void onEnableValidate() {
		this.getView().setVerifyEnabled(true);
	}
	
	public void onDisableValidate() {
		this.getView().setVerifyEnabled(false);
	}
	
	public void onEnableSave() {
		this.getView().setSaveEnabled(true);
	}
	
	public void onDisableSave() {
		this.getView().setSaveEnabled(false);
	}
	
	public void onEnableReset() {
		this.getView().setResetEnabled(true);
	}
	
	public void onDisableReset() {
		this.getView().setResetEnabled(false);
	}
}
