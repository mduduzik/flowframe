package org.flowframe.bpm.jbpm.ui.pageflow.vaadin.mvp.lineeditor.section.attachment.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.flowframe.kernel.common.mdm.domain.documentlibrary.DocType;
import org.flowframe.kernel.common.mdm.domain.documentlibrary.FileEntry;
import org.flowframe.ui.component.domain.attachment.AttachmentEditorComponent;
import org.flowframe.ui.vaadin.addons.common.FlowFrameAbstractSplitPanel.ISplitPositionChangeListener;
import org.flowframe.ui.vaadin.addons.common.FlowFrameVerticalSplitPanel;
import org.flowframe.ui.vaadin.addons.filteredtable.FilterTable;
import org.flowframe.ui.vaadin.editors.entity.vaadin.ext.EntityEditorToolStrip;
import org.flowframe.ui.vaadin.editors.entity.vaadin.ext.EntityEditorToolStrip.EntityEditorToolStripButton;
import org.flowframe.ui.vaadin.editors.entity.vaadin.ext.attachment.AttachmentEditorToolStrip;
import org.flowframe.ui.vaadin.editors.entity.vaadin.ext.fieldfactory.FlowFrameFieldFactory;
import org.flowframe.ui.vaadin.editors.entity.vaadin.ext.table.EntityGridFilterManager;
import org.flowframe.ui.vaadin.forms.FormMode;
import org.flowframe.ui.vaadin.forms.impl.VaadinAttachmentForm;
import org.vaadin.mvp.uibinder.annotation.UiField;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;

public class AttachmentEditorView extends VerticalLayout implements IAttachmentEditorView, ISplitPositionChangeListener {
	private static final long serialVersionUID = 1L;

	@UiField
	private VerticalLayout mainLayout;

	private FlowFrameVerticalSplitPanel splitPanel;
	private AttachmentEditorToolStrip toolStrip;
	private VerticalLayout masterLayout;
	private VerticalLayout detailLayout;
	private FilterTable grid;
	private VaadinAttachmentForm form;
	private Collection<?> visibleFormFields;
	private VerticalLayout defaultPanel;
	private boolean detailHidden;
	private AttachmentEditorComponent componentModel;

	private Set<ICreateAttachmentListener> createAttachmentListenerSet;
	private Set<ISaveAttachmentListener> saveAttachmentListenerSet;
	private Set<IInspectAttachmentListener> inspectAttachmentListenerSet;
	
	private EntityEditorToolStrip formToolStrip;
	private EntityEditorToolStripButton validateButton;
	private EntityEditorToolStripButton saveButton;
	private EntityEditorToolStripButton resetButton;

	public AttachmentEditorView() {
		this.grid = new FilterTable();
		this.splitPanel = new FlowFrameVerticalSplitPanel();
		this.toolStrip = new AttachmentEditorToolStrip();
		this.masterLayout = new VerticalLayout();
		this.detailLayout = new VerticalLayout();
		this.form = new VaadinAttachmentForm();
		this.defaultPanel = new VerticalLayout();
		this.detailHidden = true;
		this.createAttachmentListenerSet = new HashSet<ICreateAttachmentListener>();
		this.saveAttachmentListenerSet = new HashSet<ISaveAttachmentListener>();
		this.inspectAttachmentListenerSet = new HashSet<IInspectAttachmentListener>();
		
		this.form.addListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(Property.ValueChangeEvent event) {
				AttachmentEditorView.this.saveButton.setEnabled(false);
				AttachmentEditorView.this.validateButton.setEnabled(true);
			}
		});
		
		this.formToolStrip = new EntityEditorToolStrip();
		this.validateButton = this.formToolStrip.addToolStripButton(EntityEditorToolStrip.TOOLSTRIP_IMG_VERIFY_PNG);
		this.validateButton.setEnabled(false);
		this.validateButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 2217714313753854212L;

			@Override
			public void buttonClick(ClickEvent event) {
				AttachmentEditorView.this.validateButton.setEnabled(false);
				AttachmentEditorView.this.saveButton.setEnabled(true);
			}
		});
		this.saveButton = this.formToolStrip.addToolStripButton(EntityEditorToolStrip.TOOLSTRIP_IMG_SAVE_PNG);
		this.saveButton.setEnabled(false);
		this.saveButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 2217714313753854212L;

			@Override
			public void buttonClick(ClickEvent event) {
				AttachmentEditorView.this.form.saveForm();
				onSaveAttachment(AttachmentEditorView.this.form.getItemDataSource(), 
						AttachmentEditorView.this.form.getDocTypeValue(), 
						AttachmentEditorView.this.form.getTempSourceFileName(), 
						AttachmentEditorView.this.form.getMimeType(), 
						AttachmentEditorView.this.form.getSourceFileName(), 
						AttachmentEditorView.this.form.getDescription());
				AttachmentEditorView.this.hideDetail();
//				AttachmentEditorView.this.form.commit();
			}
		});
		this.resetButton = this.formToolStrip.addToolStripButton(EntityEditorToolStrip.TOOLSTRIP_IMG_RESET_PNG);
		this.resetButton.setEnabled(false);
		this.resetButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 2217714313753854212L;

			@Override
			public void buttonClick(ClickEvent event) {
			}
		});
	}

	@Override
	public void init() {
		Label defaultPanelCaption = new Label();
		defaultPanelCaption.setValue("<i>Select an item from the grid</i>");
		defaultPanelCaption.setContentMode(Label.CONTENT_XHTML);

		this.defaultPanel.setSizeFull();
		this.defaultPanel.addComponent(defaultPanelCaption);
		this.defaultPanel.setComponentAlignment(defaultPanelCaption, Alignment.MIDDLE_CENTER);

		EntityGridFilterManager gridManager = new EntityGridFilterManager();

		this.grid.setSizeFull();
		this.grid.setSelectable(true);
		this.grid.setNullSelectionAllowed(false);
		this.grid.addStyleName("conx-line-editor-grid");
		this.grid.setFilterDecorator(gridManager);
		this.grid.setFiltersVisible(true);

		this.toolStrip = new AttachmentEditorToolStrip();
		this.toolStrip.setEditButtonEnabled(false);
		this.toolStrip.setDeleteButtonEnabled(false);
		this.toolStrip.addNewButtonClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				newEntityItemActioned();
			}
		});

		this.masterLayout.setSizeFull();
		this.masterLayout.removeAllComponents();
		this.masterLayout.addComponent(toolStrip);
		this.masterLayout.addComponent(grid);
		this.masterLayout.setExpandRatio(grid, 1.0f);

		this.form.setFormFieldFactory(new FlowFrameFieldFactory());

		this.detailLayout.setSizeFull();
		this.detailLayout.addComponent(this.formToolStrip);
		this.detailLayout.addComponent(this.form);
		this.detailLayout.setExpandRatio(this.form, 1.0f);

		this.splitPanel.removeAllComponents();
		this.splitPanel.setStyleName("conx-entity-editor");
		this.splitPanel.addComponent(masterLayout);
		this.splitPanel.addComponent(detailLayout);
		this.splitPanel.setSizeFull();
		this.splitPanel.setImmediate(true);
		this.splitPanel.addSplitPositionChangeListener(this);

		hideDetail();
		hideContent();

		setSizeFull();
	}

	@Override
	public void showContent() {
		this.mainLayout.removeAllComponents();
		this.mainLayout.addComponent(this.splitPanel);
	}

	@Override
	public void hideContent() {
		this.mainLayout.removeAllComponents();
		this.mainLayout.addComponent(this.defaultPanel);
	}

	@Override
	public void showDetail() {
		this.form.setEnabled(true);
		this.splitPanel.setSplitPosition(50);
		this.splitPanel.setLocked(false);
		this.detailHidden = false;
	}

	@Override
	public void hideDetail() {
		this.form.setEnabled(false);
		this.splitPanel.setSplitPosition(100);
		this.splitPanel.setLocked(true);
		this.detailHidden = true;
	}

	public Layout getMainLayout() {
		return mainLayout;
	}

	@Override
	public void setContainerDataSource(Container container, Collection<?> visibleGridColumns, Collection<?> visibleFormFields) {
		this.grid.setContainerDataSource(container);
		this.grid.setVisibleColumns(visibleGridColumns.toArray());
		this.visibleFormFields = visibleFormFields;
	}

	@Override
	public void addCreateAttachmentListener(ICreateAttachmentListener listener) {
		createAttachmentListenerSet.add(listener);
	}
	
	@Override
	public void addSaveAttachmentListener(ISaveAttachmentListener listener) {
		saveAttachmentListenerSet.add(listener);
	}
	
	@Override
	public void addInspectAttachmentListener(IInspectAttachmentListener listener) {
		inspectAttachmentListenerSet.add(listener);
	}

	private void onCreateAttachment() {
		for (ICreateAttachmentListener listener : this.createAttachmentListenerSet) {
			listener.onCreateAttachment();
		}
	}
	
	@SuppressWarnings("unused")
	private void onInspectAttachment(FileEntry fileEntry) {
		for (IInspectAttachmentListener listener : this.inspectAttachmentListenerSet) {
			listener.onInspectAttachment(fileEntry);
		}
	}

	@Override
	public void setItemDataSource(Item item, FormMode mode) {
		form.setMode(mode);
		form.setItemDataSource(item, visibleFormFields);
	}
	
	public void onSaveAttachment(Item item, DocType attachmentType, String sourceFileName, String mimeType, String title, String description) {
		for (ISaveAttachmentListener listener : this.saveAttachmentListenerSet) {
			listener.onSaveAttachment(item, attachmentType, sourceFileName, mimeType, title, description);
		}
	}
	
	public interface IInspectAttachmentListener {
		public void onInspectAttachment(FileEntry fileEntry);
	}
	
	public interface ICreateAttachmentListener {
		public void onCreateAttachment();
	}
	
	public interface ISaveAttachmentListener {
		public boolean onSaveAttachment(Item item, DocType attachmentType, String sourceFileName, String mimeType, String title, String description);
	}

	@Override
	public void addItemClickListener(ItemClickListener clickListener) {
		this.grid.addListener(clickListener);
	}

	@Override
	public void entityItemSingleClicked(EntityItem<?> item) {
		if (visibleFormFields == null) {
			ArrayList<String> tempVisibleFormFields = new ArrayList<String>();
			tempVisibleFormFields.add("code");
			visibleFormFields = tempVisibleFormFields;
		}

		AttachmentEditorView.this.toolStrip.setEditButtonEnabled(true);
		AttachmentEditorView.this.toolStrip.setDeleteButtonEnabled(true);
		form.setMode(FormMode.EDITING);
		form.setItemDataSource(item, visibleFormFields);
		if (detailHidden) {
			showDetail();
		}		
	}

	@Override
	public void addNewItemListener(Button.ClickListener clickListener) {
		this.toolStrip.addNewButtonClickListener(clickListener);
	}

	@Override
	public void newEntityItemActioned() {
		if (detailHidden) {
			onCreateAttachment();
		} else {
			hideDetail();
		}
	}
	
	@Override
	public void onSplitPositionChanged(Integer newPos, int posUnit, boolean posReversed) {
	}

	@Override
	public void onFirstComponentHeightChanged(int height) {
	}

	@Override
	public void onSecondComponentHeightChanged(int height) {
		this.form.getLayout().setHeight((height - 41) + "px");
	}

	@Override
	public void setComponentModel(AttachmentEditorComponent componentModel) {
		this.componentModel = componentModel;
	}
	
	public AttachmentEditorComponent getComponentModel() {
		return this.componentModel;
	}
}
