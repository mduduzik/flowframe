package org.flowframe.ui.vaadin.common.mvp.docviewer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.flowframe.kernel.common.mdm.domain.documentlibrary.FileEntry;
import org.flowframe.ui.vaadin.common.mvp.MainMVPApplication;
import org.flowframe.ui.vaadin.common.mvp.docviewer.view.DocViewerView;
import org.flowframe.ui.vaadin.common.mvp.docviewer.view.IDocViewerView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.presenter.BasePresenter;
import org.vaadin.mvp.presenter.annotation.Presenter;

import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Embedded;

@Presenter(view = DocViewerView.class)
public class DocViewerPresenter extends BasePresenter<IDocViewerView, DocViewerEventBus> {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	private FileEntry fileEntry;
	
	
	public void onViewDocument(FileEntry fileEntry) {
		this.fileEntry = fileEntry;
    	try {
			String fileEntryId = Long.toString(DocViewerPresenter.this.fileEntry.getFileEntryId());
			String version = fileEntry.getVersion();
			
			MainMVPApplication mvpApp = (MainMVPApplication)super.getApplication();
			IRemoteDocumentRepository docLibRepository = mvpApp.getDaoProvider().provideByDAOClass(IRemoteDocumentRepository.class);
			
			String docUrl = docLibRepository.getFileAsURL(fileEntryId,version);
			ExternalResource eress = new ExternalResource(docUrl, fileEntry.getMimeType()); 
			
			Embedded pdf = new Embedded(null,eress);   
			
			//pdf.setType(Embedded.TYPE_BROWSER);
			pdf.setType(Embedded.TYPE_BROWSER);
			String mt = fileEntry.getMimeType();
			pdf.setMimeType(mt); 
			pdf.setSizeFull();	
			pdf.setHeight("800px");
			getView().getMainLayout().addComponent(pdf);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    	finally
    	{
    		
    	}
	}
	
	public class CopyInputStream
	{
		private InputStream _is;
		private ByteArrayOutputStream _copy = new ByteArrayOutputStream();

		/**
		 * 
		 */
		public CopyInputStream(InputStream is)
		{
			_is = is;
			
			try
			{
				copy();
			}
			catch(IOException ex)
			{
				// do nothing
			}
		}

		private int copy() throws IOException
		{
			int read = 0;
			int chunk = 0;
			byte[] data = new byte[256];
			
			while(-1 != (chunk = _is.read(data)))
			{
				read += data.length;
				_copy.write(data, 0, chunk);
			}
			
			return read;
		}
		
		public InputStream getCopy()
		{
			return (InputStream)new ByteArrayInputStream(_copy.toByteArray());
		}
	}
}
