package org.flowframe.ui.vaadin.common.fieldfactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.flowframe.kernel.common.utils.StringUtil;
import org.flowframe.ui.vaadin.common.fieldfactory.EntityPreferenceFieldFactory.FileUploadFieldReceiver;
import org.flowframe.ui.vaadin.common.item.PreferenceItem;
import org.flowframe.ui.vaadin.common.item.PreferenceItem.EntityPreferenceProperty;
import org.flowframe.ui.vaadin.common.ui.form.field.CustomUploadField;
import org.vaadin.easyuploads.FileFactory;
import org.vaadin.easyuploads.UploadField;
import org.vaadin.easyuploads.UploadField.FieldType;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FinishedEvent;
import com.vaadin.ui.Upload.FinishedListener;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.StartedEvent;

public class EntityPreferenceFieldFactory extends DefaultFieldFactory {
	
	private Map<String,FinishedListener> propertyId2FinishedListenerMap = new HashMap<String,FinishedListener>();
	private FinishedListener sampleFileUploadedListener;
	
	public EntityPreferenceFieldFactory(FinishedListener sampleFileUploadedListener){
		this.sampleFileUploadedListener = sampleFileUploadedListener;
	}
	@Override
	public Field createField(Item item, final Object propertyId, Component uiContext) {
		EntityPreferenceProperty prop = (EntityPreferenceProperty)item.getItemProperty(propertyId);
		Class<?> type = prop.getType();
		if (type == String.class && prop.getOptions() != null) {
			ComboBox l = new ComboBox();
			for (String o : prop.getOptions()) {
				l.addItem(o);
			}
			return l;
		}
		else if (type == File.class)
		{
/*			FileUploadFieldReceiver fufr = new FileUploadFieldReceiver((String)propertyId);
			final Upload upload = new Upload((String)propertyId,fufr);
			upload.addListener(new Upload.StartedListener() {
				private static final long serialVersionUID = 6192550643127666044L;

				public void uploadStarted(StartedEvent event) {
					upload.setEnabled(false);
				}
			});
			upload.addListener(new Upload.ProgressListener() {
				private static final long serialVersionUID = 1830359180657976775L;
				public void updateProgress(long readBytes, long contentLength) {
					//uploadStateField.setValue(new Float((readBytes / (float) contentLength) * 100) + "%");
				}
			});
			upload.addListener(new Upload.FinishedListener() {
				private static final long serialVersionUID = 982087759549669305L;

				public void uploadFinished(FinishedEvent event) {
					upload.setEnabled(true);
				}
			});
			
			return upload;*/
			CustomUploadField uploadField = new CustomUploadField();
			uploadField.registerFileUploadFinishedListener(this.sampleFileUploadedListener);
			uploadField.setFieldType(FieldType.FILE);
			uploadField.setImmediate(false);
			prop.setValue(null);
			uploadField.setPropertyDataSource(prop);
			//uploadField.setValue(null);
			uploadField.setFileFactory(new FileFactory() {
				@Override
				public File createFile(String fileName, String mimeType) {
					File tempFile = null;
					
					String prefix = null;
					String suffix = null;
					
					String[] fileNameTokens = StringUtil.split(fileName,".");
					if (fileNameTokens.length == 1)
					{
						prefix = fileName;
					}
					else
					{
						prefix = fileNameTokens[0];
						suffix = "."+fileNameTokens[fileNameTokens.length-1];
					}
					
					try {
						tempFile = File.createTempFile(prefix, suffix);
						tempFile.deleteOnExit();
					} catch (IOException e) {
						e.printStackTrace();
					}
                    return tempFile;
				}
			});
			
			return uploadField;
		}
		else
			return super.createField(item, propertyId, uiContext);
	}
	
	public class FileUploadFieldReceiver implements Receiver {
		private static final long serialVersionUID = -7977323550862088995L;
		private String propertyId;
		private String currentTempUploadedFile;
		
		public FileUploadFieldReceiver(String propertyId) {
			this.propertyId = propertyId;
		}

		@Override
		public OutputStream receiveUpload(String filename, String mimeType) {
			FileOutputStream fos = null;
			File tempFile = null;
			
			String prefix = null;
			String suffix = null;
			
			String[] fileNameTokens = StringUtil.split(filename,".");
			if (fileNameTokens.length == 1)
			{
				prefix = filename;
			}
			else
			{
				prefix = fileNameTokens[0];
				suffix = "."+fileNameTokens[fileNameTokens.length-1];
			}
			
			try {
				tempFile = File.createTempFile(prefix, suffix);
				tempFile.deleteOnExit();
				fos = new FileOutputStream(tempFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			this.currentTempUploadedFile = tempFile.getAbsolutePath();
			
			return fos;
		}

	}	
	
	
}