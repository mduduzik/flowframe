package org.flowframe.ui.vaadin.common.editors.entity.mvp.footer;

import java.util.Map;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.flowframe.ui.vaadin.common.editors.entity.mvp.ConfigurableBasePresenter;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.MultiLevelEntityEditorPresenter;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.footer.view.EntityTableFooterView;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.footer.view.IEntityTableFooterView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.presenter.annotation.Presenter;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;

@Presenter(view = EntityTableFooterView.class)
public class EntityTableFooterPresenter extends ConfigurableBasePresenter<IEntityTableFooterView, EntityTableFooterEventBus>
		implements Property.ValueChangeListener {
	private static final long serialVersionUID = 1L;

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	private boolean initialized = false;
	private MultiLevelEntityEditorPresenter parentPresenter;

	public EntityTableFooterPresenter() {
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
		try {
			this.setInitialized(true);
			this.getView().init();
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
		}		
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
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
		// TODO Auto-generated method stub
		
	}
}
