package org.flowframe.kernel.common.mdm.dao.services.documentlibrary;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.flowframe.kernel.common.mdm.domain.BaseEntity;
import org.flowframe.kernel.common.mdm.domain.documentlibrary.DocType;
import org.flowframe.kernel.common.mdm.domain.documentlibrary.FileEntry;
import org.flowframe.kernel.common.mdm.domain.documentlibrary.Folder;

public interface IFolderDAOService {
	public Folder get(long id);
	
	public List<Folder> getAll();
	
	public Folder getByFolderIdOrName(Long folderId, String name);	

	public Folder add(Folder record);
	
	public Folder update(Folder record);
	
	public Folder provide(Folder record);
	
	public Folder provide(Long folderId, String name);
	
	public Folder provideFolderForEntity(Class entityJavaClass, Long entityId) throws Exception;	
	
	public Folder provideFolderByJavaTypeName(String folderName) throws Exception;	
	
	public void provideDefaults();	
	
	public FileEntry addFileEntry(Long folderId, DocType attachmentType, FileEntry fileEntry);
	
	public Folder addFileEntries(Long folderId, Set<FileEntry> fileEntries);

	public void delete(Folder record);
	
	public Folder deleteFileEntry(Long folderId, FileEntry fileEntry);
	
	public Folder deleteFileEntries(Long folderId, Set<FileEntry> fileEntries);	

	public FileEntry addorUpdateFileEntry(BaseEntity entity, DocType attachmentType, File sourceFile, String mimeType, String title, String description) throws Exception;

	public FileEntry addorUpdateFileEntry(Folder folder, DocType attachmentType, String sourceFileName, String mimeType, String title, String description) throws Exception;
}
