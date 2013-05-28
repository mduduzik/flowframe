package org.flowframe.ui.vaadin.common.editors.entity.ext.attachment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.flowframe.kernel.common.utils.StringUtil;

import com.vaadin.ui.Upload.Receiver;

public class AttachmentFormReceiver implements Receiver {
	private static final long serialVersionUID = -7977323550862088995L;
	private AttachmentForm parent;
	
	public AttachmentFormReceiver(AttachmentForm parent) {
		this.parent = parent;
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
		
		this.parent.setTempSourceFile(tempFile.getAbsolutePath());
		
		return fos;
	}

}
