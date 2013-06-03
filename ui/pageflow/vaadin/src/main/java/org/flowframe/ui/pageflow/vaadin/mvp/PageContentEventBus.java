package org.flowframe.ui.pageflow.vaadin.mvp;

import java.util.Map;

import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.annotation.Event;

import org.flowframe.ui.pageflow.vaadin.mvp.lineeditor.EntityLineEditorPresenter;

import com.vaadin.data.Item;

public interface PageContentEventBus extends EventBus {
	public void applyPageParams(Map<String, Object> params);
	public Object getPageResults();
}
