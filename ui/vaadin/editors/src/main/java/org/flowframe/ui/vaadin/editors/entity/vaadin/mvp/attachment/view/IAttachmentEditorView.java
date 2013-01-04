package org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.attachment.view;

import java.util.Collection;

import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.IEntityEditorComponentView;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.attachment.view.AttachmentEditorView.ICreateAttachmentListener;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.attachment.view.AttachmentEditorView.IInspectAttachmentListener;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.attachment.view.AttachmentEditorView.ISaveAttachmentListener;
import com.conx.logistics.kernel.ui.forms.vaadin.FormMode;
import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Button;

public interface IAttachmentEditorView extends IEntityEditorComponentView {
	public void setItemDataSource(Item item, FormMode mode);
	public void setContainerDataSource(Container container, Object[] visibleGridColumns, Collection<?> visibleFormFields);
	public void showContent();
	public void hideContent();
	public void showDetail();
	public void hideDetail();
	public void addCreateAttachmentListener(ICreateAttachmentListener listener);
	public void addSaveAttachmentListener(ISaveAttachmentListener listener);
	public void addInspectAttachmentListener(IInspectAttachmentListener listener);
	
	public void addItemClickListener(ItemClickListener clickListener);
	public void entityItemSingleClicked(EntityItem item);
	
	public void addNewItemListener(Button.ClickListener clickListener);
	public void newEntityItemActioned();
}