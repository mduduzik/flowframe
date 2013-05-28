package org.flowframe.ui.vaadin.common.editors.pageflow.mvp.editor.multilevel;

import java.util.Map;

import org.flowframe.kernel.common.mdm.domain.documentlibrary.FileEntry;
import org.flowframe.ui.component.domain.masterdetail.MasterDetailComponent;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.annotation.Event;

import com.vaadin.data.Container;
import com.vaadin.data.Item;

public interface MultiLevelEditorEventBus extends EventBus {
	@Event(handlers = { MultiLevelEditorPresenter.class })
	public void configure(Map<String, Object> params);
	@Event(handlers = { MultiLevelEditorPresenter.class })
	public void viewDocument(FileEntry viewable);
	@Event(handlers = { MultiLevelEditorPresenter.class })
	public void viewDocument(String url, String caption);
	@Event(handlers = { MultiLevelEditorPresenter.class })
	public void renderEditor(MasterDetailComponent componentModel);
	@Event(handlers = { MultiLevelEditorPresenter.class })
	public void renderEditor(MasterDetailComponent componentModel, Item item, Container itemContainer);
}
