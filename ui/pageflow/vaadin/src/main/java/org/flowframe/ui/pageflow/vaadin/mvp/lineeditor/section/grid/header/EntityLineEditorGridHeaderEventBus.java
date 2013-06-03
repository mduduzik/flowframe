package org.flowframe.ui.pageflow.vaadin.mvp.lineeditor.section.grid.header;

import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.annotation.Event;


public interface EntityLineEditorGridHeaderEventBus extends EventBus {
	@Event(handlers = { EntityLineEditorGridHeaderPresenter.class })
	public void enableCreate();
	@Event(handlers = { EntityLineEditorGridHeaderPresenter.class })
	public void disableCreate();
	@Event(handlers = { EntityLineEditorGridHeaderPresenter.class })
	public void enableEdit();
	@Event(handlers = { EntityLineEditorGridHeaderPresenter.class })
	public void disableEdit();
	@Event(handlers = { EntityLineEditorGridHeaderPresenter.class })
	public void enableDelete();
	@Event(handlers = { EntityLineEditorGridHeaderPresenter.class })
	public void disableDelete();
	@Event(handlers = { EntityLineEditorGridHeaderPresenter.class })
	public void enablePrint();
	@Event(handlers = { EntityLineEditorGridHeaderPresenter.class })
	public void disablePrint();
}
