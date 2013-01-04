package org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.lineeditor.section.form.header;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.presenter.annotation.Presenter;

import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.ConfigurableBasePresenter;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.lineeditor.section.EntityLineEditorSectionEventBus;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.lineeditor.section.EntityLineEditorSectionPresenter;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.lineeditor.section.form.header.view.EntityLineEditorFormHeaderView;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.lineeditor.section.form.header.view.IEntityLineEditorFormHeaderView;
import com.conx.logistics.kernel.ui.factory.services.IEntityEditorFactory;
import com.vaadin.data.Item;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@Presenter(view = EntityLineEditorFormHeaderView.class)
public class EntityLineEditorFormHeaderPresenter extends ConfigurableBasePresenter<IEntityLineEditorFormHeaderView, EntityLineEditorFormHeaderEventBus> {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	private boolean initialized = false;
	
	private EntityLineEditorSectionPresenter lineEditorSectionPresenter;
	private EntityLineEditorSectionEventBus lineEditorSectionEventBus;

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
		lineEditorSectionEventBus.validateForm();
	}

	public void save() {
		lineEditorSectionEventBus.saveForm();
	}

	public void reset() {
		this.getView().setVerifyEnabled(false);
		this.getView().setResetEnabled(false);
		lineEditorSectionEventBus.resetForm();
	}

	public boolean isInitialized() {
		return initialized;
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

	@Override
	public void configure() {
		this.lineEditorSectionPresenter = (EntityLineEditorSectionPresenter) getConfig().get(IEntityEditorFactory.FACTORY_PARAM_MVP_LINE_EDITOR_SECTION_PRESENTER);
		this.lineEditorSectionEventBus = lineEditorSectionPresenter.getEventBus();
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
