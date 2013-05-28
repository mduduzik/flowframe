package org.flowframe.ui.vaadin.common.editors.pageflow.mvp.editor.grid.header;

import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.annotation.Event;


public interface EditorGridHeaderEventBus extends EventBus {
	@Event(handlers = { EditorGridHeaderPresenter.class })
	public void enableCreate();
	@Event(handlers = { EditorGridHeaderPresenter.class })
	public void disableCreate();
	@Event(handlers = { EditorGridHeaderPresenter.class })
	public void enableEdit();
	@Event(handlers = { EditorGridHeaderPresenter.class })
	public void disableEdit();
	@Event(handlers = { EditorGridHeaderPresenter.class })
	public void enableDelete();
	@Event(handlers = { EditorGridHeaderPresenter.class })
	public void disableDelete();
	@Event(handlers = { EditorGridHeaderPresenter.class })
	public void enablePrint();
	@Event(handlers = { EditorGridHeaderPresenter.class })
	public void disablePrint();
}
