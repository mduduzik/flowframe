package org.flowframe.ui.vaadin.common.editors.pageflow.mvp.editor.form.header;

import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.annotation.Event;

public interface EditorFormHeaderEventBus extends EventBus {
	@Event(handlers = { EditorFormHeaderPresenter.class })
	public void enableValidate();
	@Event(handlers = { EditorFormHeaderPresenter.class })
	public void disableValidate();
	@Event(handlers = { EditorFormHeaderPresenter.class })
	public void enableSave();
	@Event(handlers = { EditorFormHeaderPresenter.class })
	public void disableSave();
	@Event(handlers = { EditorFormHeaderPresenter.class })
	public void enableReset();
	@Event(handlers = { EditorFormHeaderPresenter.class })
	public void disableReset();
}
