package org.flowframe.ui.vaadin.common.editors.entity.mvp.lineeditor.section.grid.header;
import java.util.Map;

import org.flowframe.ui.pageflow.services.IMainApplication;
import org.flowframe.ui.services.factory.IComponentFactory;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.ConfigurableBasePresenter;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.MultiLevelEntityEditorEventBus;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.MultiLevelEntityEditorPresenter;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.lineeditor.section.grid.header.view.EntityLineEditorGridHeaderView;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.lineeditor.section.grid.header.view.IEntityLineEditorGridHeaderView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.presenter.annotation.Presenter;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@Presenter(view = EntityLineEditorGridHeaderView.class)
public class EntityLineEditorGridHeaderPresenter extends ConfigurableBasePresenter<IEntityLineEditorGridHeaderView, EntityLineEditorGridHeaderEventBus> {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	private boolean initialized = false;
	private MultiLevelEntityEditorPresenter parentPresenter;

	private MultiLevelEntityEditorPresenter multiLevelEntityEditorPresenter;
	private IMainApplication mainApplication;
	private MultiLevelEntityEditorEventBus entityEditorEventListener;

	public EntityLineEditorGridHeaderPresenter() {
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
			}
		});
		this.getView().addEditListener(new ClickListener() {
			private static final long serialVersionUID = -52023801L;

			@Override
			public void buttonClick(ClickEvent event) {
			}
		});
		this.getView().addDeleteListener(new ClickListener() {
			private static final long serialVersionUID = -50023801L;

			@Override
			public void buttonClick(ClickEvent event) {
			}
		});
		this.getView().addPrintListener(new ClickListener() {
			private static final long serialVersionUID = -99023801L;

			@Override
			public void buttonClick(ClickEvent event) {
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
	public void onConfigure(Map<String, Object> config) {
		super.setConfig(config);
		this.multiLevelEntityEditorPresenter = (MultiLevelEntityEditorPresenter) getConfig().get(IComponentFactory.FACTORY_PARAM_MVP_CURRENT_MLENTITY_EDITOR_PRESENTER);
		this.mainApplication = (IMainApplication) getConfig().get(IComponentFactory.FACTORY_PARAM_MAIN_APP);
		this.entityEditorEventListener = multiLevelEntityEditorPresenter.getEventBus();
	}
}
