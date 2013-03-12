package org.flowframe.ui.vaadin.common.editors.mvp.lineeditor.section.form.header;

import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.annotation.Event;

public interface EntityLineEditorFormHeaderEventBus extends EventBus {
	@Event(handlers = { EntityLineEditorFormHeaderPresenter.class })
	public void enableValidate();
	@Event(handlers = { EntityLineEditorFormHeaderPresenter.class })
	public void disableValidate();
	@Event(handlers = { EntityLineEditorFormHeaderPresenter.class })
	public void enableSave();
	@Event(handlers = { EntityLineEditorFormHeaderPresenter.class })
	public void disableSave();
	@Event(handlers = { EntityLineEditorFormHeaderPresenter.class })
	public void enableReset();
	@Event(handlers = { EntityLineEditorFormHeaderPresenter.class })
	public void disableReset();
}
