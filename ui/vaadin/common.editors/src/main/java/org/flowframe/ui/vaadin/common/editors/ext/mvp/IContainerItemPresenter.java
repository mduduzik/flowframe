package org.flowframe.ui.vaadin.common.editors.ext.mvp;

import com.vaadin.data.Container;
import com.vaadin.data.Item;

public interface IContainerItemPresenter {
	public void onSetItemDataSource(Item item, Container...container) throws Exception;
}
