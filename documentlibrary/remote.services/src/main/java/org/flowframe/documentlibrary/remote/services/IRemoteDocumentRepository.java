package org.flowframe.documentlibrary.remote.services;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.flowframe.kernel.common.mdm.domain.BaseEntity;
import org.flowframe.kernel.common.mdm.domain.documentlibrary.DocType;
import org.flowframe.kernel.common.mdm.domain.documentlibrary.FileEntry;
import org.flowframe.kernel.common.mdm.domain.documentlibrary.Folder;
import org.flowframe.kernel.common.mdm.domain.metamodel.EntityType;


public interface IRemoteDocumentRepository {
	public void init() throws Exception;
	public Boolean isAvailable() throws Exception;
	
	/**
	 * 
	 * Folder API
	 * 
	 */
	public BaseEntity provideFolderForEntity(BaseEntity entity) throws Exception;
	
	public Folder getFolderById(String folderId)  throws Exception;
	
	public boolean folderExists(String parentFolderId, String name) throws Exception;	
	
	public Folder getFolderByName(String parentFolderId, String folderId)  throws Exception;
	
	public Folder addFolder(String parentFolderId, String name, String description)  throws Exception;
	
	public Folder provideFolderForEntity(EntityType entityType, Long entityId) throws Exception;
	
	public Folder provideFolderForEntity(Class entityJavaClass, Long id) throws Exception;
	
	public void deleteFolderById(String folderId)  throws Exception;
	
	public void deleteFolderByName(String parentFolderId, String folderId)  throws Exception;
	
	/**
	 * 
	 * File API
	 * 
	 */
	public List<FileEntry> getFileEntries(String folderId)  throws Exception;
	
	public FileEntry getFileEntryById(String fileEntryId)  throws Exception;
	
	public FileEntry getFileEntryByTitle(String parentFolderId, String title)  throws Exception;
	
	public 	InputStream getFileAsStream(String fileEntryId, String version) throws Exception;
	
	public 	String getFileAsURL(String fileEntryId, String version) throws Exception;
	
	public InputStream getFileEntryInputStreamWithURL(String fileEntryId,String version) throws Exception;	
	
	public boolean fileEntryExists(String parentFolderId, String name) throws Exception;
	
	public FileEntry addFileEntry(String folderId, String sourceFileName, String mimeType, String title, String description) throws Exception;
	
	public FileEntry addFileEntry(String folderId, File sourceFile, String mimeType,String title, String description) throws Exception;	
	
	public FileEntry addorUpdateFileEntry(String folderId, String sourceFileName, String mimeType, String title, String description) throws Exception;	
	
	public FileEntry addorUpdateFileEntry(BaseEntity entity, DocType attachmentType, String sourceFileName, String mimeType, String title, String description) throws Exception;
	
	public FileEntry addorUpdateFileEntry(String folderId, File sourceFile,String mimeType, String title, String description) throws Exception;	
	
	public FileEntry addorUpdateFileEntry(Folder folder, 
			DocType attachmentType,
			String sourceFileName, 
			String mimeType, 
			String title,
			String description) throws Exception;
	
	public FileEntry addorUpdateFileEntryOnRepoOnly(Folder folder, 
			DocType attachmentType,
			String sourceFileName, 
			String mimeType, 
			String title,
			String description) throws Exception;	
	
	public FileEntry addorUpdateFileEntry(BaseEntity entity, DocType attachmentType,
			File source, String mimeType, String title, String description)
			throws Exception;	
	
	public FileEntry deleteFileEntryById(String folderId, String fileEntryId)  throws Exception;
	
	/**
	 * 
	 * Props
	 * 
	 * @return
	 */
	public String getConxlogiFolderId();

}
