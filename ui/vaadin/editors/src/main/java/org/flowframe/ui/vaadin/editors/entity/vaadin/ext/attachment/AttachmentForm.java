package org.flowframe.ui.vaadin.editors.entity.vaadin.ext.attachment;

import java.util.Collection;

import org.flowframe.kernel.common.mdm.domain.documentlibrary.DocType;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.attachment.view.AttachmentEditorView;
import org.flowframe.ui.vaadin.forms.FormMode;
import org.flowframe.ui.vaadin.forms.impl.VaadinFormHeader;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FinishedEvent;
import com.vaadin.ui.Upload.StartedEvent;
import com.vaadin.ui.VerticalLayout;

public class AttachmentForm extends Form {
	private static final long serialVersionUID = -7906217975800435620L;

	private VaadinFormHeader header;
	private VerticalLayout layout;
	private Panel innerLayoutPanel;
	private GridLayout innerLayout;

	private Button uploadToDocRepoButton;
	private Label fileNameField;
	private Label fileTypeField;
	private Label uploadStateField;
	private TextArea fileDescription;
	private Field attachedField;
	private Upload upload;

	protected String sourceFileName;
	protected String mimeType;
	protected String title;
	protected String description;
	protected String folderId;
	protected String tempSourceFileName;
	private FormMode mode;
	private AttachmentEditorView view;

	public AttachmentForm() {
		this.attachedField = null;
		this.innerLayoutPanel = new Panel();
		this.innerLayout = new GridLayout(4, 4);
		this.layout = new VerticalLayout();
		this.header = new VaadinFormHeader();
		this.fileNameField = new Label();
		this.fileTypeField = new Label();
		this.uploadStateField = new Label();
		this.upload = new Upload();
		this.fileDescription = new TextArea();
		this.uploadToDocRepoButton = new Button();

		initialize();
	}

	@SuppressWarnings("deprecation")
	private void initialize() {
		this.fileNameField.setStyleName("conx-entity-editor-readonly-field");
		this.fileNameField.setCaption("File");
		this.fileNameField.setHeight("24px");
		this.fileNameField.setContentMode(Label.CONTENT_XHTML);
		this.fileNameField.setValue("<i>No file uploaded</i>");

		this.fileTypeField.setStyleName("conx-entity-editor-readonly-field");
		this.fileTypeField.setCaption("File Type");
		this.fileTypeField.setWidth("100%");
		this.fileTypeField.setHeight("24px");
		this.fileTypeField.setContentMode(Label.CONTENT_XHTML);
		this.fileTypeField.setValue("<i>No file uploaded</i>");

		this.uploadStateField.setStyleName("conx-entity-editor-readonly-field");
		this.uploadStateField.setCaption("Upload State");
		this.uploadStateField.setWidth("100%");
		this.uploadStateField.setHeight("24px");
		this.uploadStateField.setContentMode(Label.CONTENT_XHTML);
		this.uploadStateField.setValue("<i>Idle</i>");

		this.upload.setImmediate(true);
		this.upload.setButtonCaption("...");
		this.upload.setReceiver(new AttachmentFormReceiver(this));
		this.upload.addListener(new Upload.StartedListener() {
			private static final long serialVersionUID = 6192550643127666044L;

			public void uploadStarted(StartedEvent event) {
				uploadStateField.setValue("Started");
				fileNameField.setValue(event.getFilename());
				AttachmentForm.this.title = event.getFilename();
			}
		});
		this.upload.addListener(new Upload.ProgressListener() {
			private static final long serialVersionUID = 1830359180657976775L;

			public void updateProgress(long readBytes, long contentLength) {
				uploadStateField.setValue(new Float((readBytes / (float) contentLength) * 100) + "%");
			}

		});
		this.upload.addListener(new Upload.FinishedListener() {
			private static final long serialVersionUID = 982087759549669305L;

			public void uploadFinished(FinishedEvent event) {
				AttachmentForm.this.sourceFileName = event.getFilename();
				AttachmentForm.this.mimeType = event.getMIMEType();
				fileTypeField.setValue(event.getMIMEType());
				uploadStateField.setValue("Uploaded");
			}
		});

		HorizontalLayout fileUploadLayout = new HorizontalLayout();
		fileUploadLayout.setSpacing(true);
		fileUploadLayout.addComponent(fileNameField);
		fileUploadLayout.addComponent(upload);
		fileUploadLayout.setWidth("100%");
		fileUploadLayout.setExpandRatio(fileNameField, 1.0f);
		fileUploadLayout.setComponentAlignment(upload, Alignment.BOTTOM_LEFT);

		this.fileDescription.setCaption("Description");
		this.fileDescription.setRows(3);
		this.fileDescription.setColumns(0);
		this.fileDescription.setWidth("100%");
		this.fileDescription.setHeight("100%");

		this.uploadToDocRepoButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 5272247497155767335L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (mode.equals(FormMode.CREATING)) {
					save();
				} else if (mode.equals(FormMode.EDITING)) {
					save();
				}
			}
		});

		this.header.setAction("Editing");
		this.header.setTitle("Attachment");
		this.header.addExtraComponent(uploadToDocRepoButton);

		this.innerLayout.setWidth("100%");
		this.innerLayout.setSpacing(true);
		this.innerLayout.setMargin(true);
		this.innerLayout.setHeight(-1, UNITS_PIXELS);
		this.innerLayout.addComponent(fileUploadLayout, 0, 0, 0, 0);
		this.innerLayout.addComponent(fileTypeField, 0, 1, 0, 1);
		this.innerLayout.addComponent(uploadStateField, 1, 0, 1, 0);
		this.innerLayout.addComponent(fileDescription, 2, 0, 3, 1);
		
		this.innerLayoutPanel = new Panel();
		this.innerLayoutPanel.setSizeFull();
		this.innerLayoutPanel.getLayout().setMargin(false, true, false, true);
		this.innerLayoutPanel.setStyleName("light");
		this.innerLayoutPanel.addComponent(innerLayout);

		this.layout.setWidth("100%");
		this.layout.setStyleName("conx-entity-editor-form");
		this.layout.addComponent(header);
		this.layout.addComponent(innerLayoutPanel);
		this.layout.setExpandRatio(innerLayoutPanel, 1.0f);

		this.setMode(FormMode.CREATING);
		this.setLayout(layout);
		this.setSizeFull();
		this.setWriteThrough(false);
	}

	@Override
	protected void attachField(Object propertyId, com.vaadin.ui.Field field) {
		if (attachedField == null) {
			field.setWidth("100%");
			innerLayout.addComponent(field, 1, 1, 1, 1);
			attachedField = field;
		}
	}

	public void setMode(FormMode mode) {
		this.mode = mode;
		switch (mode) {
		case CREATING:
			this.header.setAction("Creating");
			this.uploadToDocRepoButton.setCaption("Upload");
			fileTypeField.setValue("<i>No file uploaded</i>");
			fileNameField.setValue("<i>No file uploaded</i>");
			uploadStateField.setValue("<i>Idle</i>");
			break;
		case EDITING:
			this.header.setAction("Editing");
			this.uploadToDocRepoButton.setCaption("Update");
			break;
		}
	}

	private void save() {
		if (view != null) {
			AttachmentForm.this.description = (String) AttachmentForm.this.fileDescription.getValue();
			attachedField.commit();
			DocType docTypeValue = (DocType) AttachmentForm.this.getItemDataSource().getItemProperty("docType").getValue();
			view.onSaveAttachment(getItemDataSource(), docTypeValue, this.tempSourceFileName, this.mimeType, this.sourceFileName, this.description);
		}
	}

	private void updateStaticFields(Item newDataSource) {
		attachedField = null;
		innerLayout.removeComponent(1, 1);
		uploadStateField.setValue("Uploaded");
		Property description = newDataSource.getItemProperty("description");
		Property mimeType = newDataSource.getItemProperty("mimeType");
		Property title = newDataSource.getItemProperty("title");
		if (description != null && description.getValue() != null) {
			fileDescription.setValue(description.getValue());
		}
		if (mimeType != null && mimeType.getValue() != null) {
			fileTypeField.setValue(mimeType.getValue());
		}
		if (title != null && title.getValue() != null) {
			fileNameField.setValue(title.getValue());
			header.setTitle("Attachment (" + title.getValue() + ")");
		}
	}

	@Override
	public void setItemDataSource(com.vaadin.data.Item newDataSource, Collection<?> propertyIds) {
		updateStaticFields(newDataSource);
		super.setItemDataSource(newDataSource, propertyIds);
	}

	public void setTempSourceFile(String absolutePath) {
		this.tempSourceFileName = absolutePath;
	}

	public void setView(AttachmentEditorView view) {
		this.view = view;
	}
}
