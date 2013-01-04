package org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.footer;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.presenter.BasePresenter;
import org.vaadin.mvp.presenter.annotation.Presenter;

import com.conx.logistics.kernel.ui.components.domain.AbstractComponent;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.AbstractEntityEditorEventBus;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.ConfigurableBasePresenter;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.MultiLevelEntityEditorPresenter;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.footer.view.EntityTableFooterView;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.footer.view.IEntityTableFooterView;
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
	public void configure() {
		// TODO Auto-generated method stub
		
	}
}
