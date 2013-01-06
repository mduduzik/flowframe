package org.flowframe.ui.vaadin.forms.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.flowframe.kernel.common.mdm.domain.documentlibrary.DocType;
import org.flowframe.kernel.common.utils.StringUtil;
import org.flowframe.ui.vaadin.forms.FormMode;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerItem;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FinishedEvent;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.StartedEvent;
import com.vaadin.ui.VerticalLayout;

public class VaadinAttachmentForm extends Form implements Receiver {
	private static final long serialVersionUID = 5566061819199928712L;

	private VaadinFormHeader header;
	private VerticalLayout layout;
	private Panel innerLayoutPanel;
	private GridLayout innerLayout;

	private Label fileNameField;
	private Label fileTypeField;
	private Label uploadStateField;
	private TextArea fileDescription;
	private Field attachedField;
	private Upload upload;
	private Property.ValueChangeListener listener;
	private Set<Property.ValueChangeListener> subscribers;

	protected String sourceFileName;
	protected String mimeType;
	protected String title;
	protected String description;
	protected String folderId;
	protected String tempSourceFileName;

	private DocType docTypeValue;

	public VaadinAttachmentForm() {
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
		this.subscribers = new HashSet<Property.ValueChangeListener>();
		this.listener = new ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(Property.ValueChangeEvent event) {
				onFormChanged(event);
			}
		};

		initialize();
	}

	public void addListener(Property.ValueChangeListener listener) {
		this.subscribers.add(listener);
	}

	public void removeListener(Property.ValueChangeListener listener) {
		this.subscribers.remove(listener);
	}

	private void onFormChanged(Property.ValueChangeEvent event) {
		for (Property.ValueChangeListener subscriber : subscribers) {
			subscriber.valueChange(event);
		}
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
		this.upload.setReceiver(this);
		this.upload.addListener(new Upload.StartedListener() {
			private static final long serialVersionUID = 6192550643127666044L;

			public void uploadStarted(StartedEvent event) {
				uploadStateField.setValue("Started");
				fileNameField.setValue(event.getFilename());
				VaadinAttachmentForm.this.title = event.getFilename();
				onFormChanged(null);
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
				VaadinAttachmentForm.this.sourceFileName = event.getFilename();
				VaadinAttachmentForm.this.mimeType = event.getMIMEType();
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
		this.fileDescription.addListener(this.listener);

		this.header.setAction("Editing");
		this.header.setTitle("Attachment");

		this.innerLayout.setWidth("100%");
		this.innerLayout.setSpacing(true);
		this.innerLayout.setMargin(true);
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
			field.addListener(this.listener);
			innerLayout.addComponent(field, 1, 1, 1, 1);
			attachedField = field;
		}
	}

	public void setMode(FormMode mode) {
		switch (mode) {
		case CREATING:
			this.header.setAction("Creating");
			fileTypeField.setValue("<i>No file uploaded</i>");
			fileNameField.setValue("<i>No file uploaded</i>");
			uploadStateField.setValue("<i>Idle</i>");
			break;
		case EDITING:
			this.header.setAction("Editing");
			break;
		}
	}

	public void saveForm() {
		this.description = (String) this.fileDescription.getValue();
		// this.attachedField.commit();
		if (this.attachedField instanceof AbstractSelect) {
			if (((AbstractSelect) this.attachedField).getContainerDataSource() instanceof JPAContainer<?>) {
				if (DocType.class.isAssignableFrom(((JPAContainer<?>) ((AbstractSelect) this.attachedField).getContainerDataSource())
						.getEntityClass())) {
					if (this.attachedField.getValue() != null) {
						Item item = ((AbstractSelect) this.attachedField).getContainerDataSource().getItem(this.attachedField.getValue());
						if (item instanceof JPAContainerItem<?>) {
							@SuppressWarnings("rawtypes")
							Object entity = ((JPAContainerItem) item).getEntity();
							if (entity instanceof DocType) {
								this.docTypeValue = (DocType) entity;
								return;
							}
						}
					}
				}
			}
		}
		this.docTypeValue = null;
	}

	private void updateStaticFields(Item newDataSource) {
		attachedField = null;
		innerLayout.removeComponent(1, 1);
		uploadStateField.setValue("Idle");
		Property description = newDataSource.getItemProperty("description");
		Property mimeType = newDataSource.getItemProperty("mimeType");
		Property title = newDataSource.getItemProperty("title");
		if (description != null && description.getValue() != null) {
			fileDescription.setValue(description.getValue());
		} else {
			this.fileDescription.setValue("");
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

	@Override
	public OutputStream receiveUpload(String filename, String mimeType) {
		FileOutputStream fos = null;
		File tempFile = null;

		String prefix = null;
		String suffix = null;

		String[] fileNameTokens = StringUtil.split(filename, ".");
		if (fileNameTokens.length == 1) {
			prefix = filename;
		} else {
			prefix = fileNameTokens[0];
			suffix = "." + fileNameTokens[fileNameTokens.length - 1];
		}

		try {
			tempFile = File.createTempFile(prefix, suffix);
			tempFile.deleteOnExit();
			fos = new FileOutputStream(tempFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		setTempSourceFile(tempFile.getAbsolutePath());

		return fos;
	}

	public String getFileDescription() {
		return this.description;
	}

	public String getSourceFileName() {
		return sourceFileName;
	}

	public String getMimeType() {
		return mimeType;
	}

	public String getTitle() {
		return title;
	}

	public String getTempSourceFileName() {
		return tempSourceFileName;
	}

	public DocType getDocTypeValue() {
		return docTypeValue;
	}
}
