package org.flowframe.ui.vaadin.common.editors.pageflow.mvp;

import java.util.Map;

import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.annotation.Event;

import org.flowframe.ui.vaadin.common.editors.pageflow.mvp.lineeditor.EntityLineEditorPresenter;
import com.vaadin.data.Item;

public interface PageContentEventBus extends EventBus {
	public void applyPageParams(Map<String, Object> params);
	public Object getPageResults();
}
