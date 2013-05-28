package org.flowframe.ui.vaadin.common.editors.pageflow.mvp.lineeditor.section.attachment.view;

import java.util.Collection;

import org.flowframe.ui.vaadin.common.editors.pageflow.mvp.lineeditor.section.attachment.view.AttachmentEditorView.ICreateAttachmentListener;
import org.flowframe.ui.vaadin.common.editors.pageflow.mvp.lineeditor.section.attachment.view.AttachmentEditorView.IInspectAttachmentListener;
import org.flowframe.ui.vaadin.common.editors.pageflow.mvp.lineeditor.section.attachment.view.AttachmentEditorView.ISaveAttachmentListener;
import org.flowframe.ui.component.domain.attachment.AttachmentEditorComponent;
import org.flowframe.ui.vaadin.forms.FormMode;
import org.vaadin.mvp.uibinder.IUiBindable;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Button;

public interface IAttachmentEditorView extends IUiBindable {
	public void setItemDataSource(Item item, FormMode mode);
	public void setContainerDataSource(Container container, Collection<?> visibleGridColumns, Collection<?> visibleFormFields);
	public void showContent();
	public void hideContent();
	public void showDetail();
	public void hideDetail();
	public void addCreateAttachmentListener(ICreateAttachmentListener listener);
	public void addSaveAttachmentListener(ISaveAttachmentListener listener);
	public void addInspectAttachmentListener(IInspectAttachmentListener listener);
	public void setComponentModel(AttachmentEditorComponent componentModel);
	
	public void addItemClickListener(ItemClickListener clickListener);
	public void entityItemSingleClicked(EntityItem<?> item);
	
	public void addNewItemListener(Button.ClickListener clickListener);
	public void newEntityItemActioned();
	public void init();
}