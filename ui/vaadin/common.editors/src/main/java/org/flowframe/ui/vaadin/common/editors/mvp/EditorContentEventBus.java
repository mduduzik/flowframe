package org.flowframe.ui.vaadin.common.editors.mvp;

import java.util.Map;

import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.annotation.Event;

import org.flowframe.ui.vaadin.common.editors.mvp.lineeditor.EntityLineEditorPresenter;
import com.vaadin.data.Item;

public interface EditorContentEventBus extends EventBus {
	public void applyEditorParams(Map<String, Object> params);
	public Object getEditorResults();
}
