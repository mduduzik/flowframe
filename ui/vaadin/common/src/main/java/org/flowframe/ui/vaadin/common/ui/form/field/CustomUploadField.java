package org.flowframe.ui.vaadin.common.ui.form.field;

import org.vaadin.easyuploads.UploadField;

import com.vaadin.ui.Upload.FinishedEvent;
import com.vaadin.ui.Upload.FinishedListener;

public class CustomUploadField extends UploadField {
	private FinishedListener listener = null;
	
	public CustomUploadField() {
		super();
	}
	
	@Override
	public void uploadFinished(FinishedEvent event) {
		super.uploadFinished(event);
		if (listener != null)
			listener.uploadFinished(event);
	}
	
	public void registerFileUploadFinishedListener(FinishedListener listener){
		this.listener = listener;
	}
	
	public void unregisterFileUploadFinishedListener(){
		this.listener = null;
	}	
}
