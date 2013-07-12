package org.flowframe.kernel.common.mdm.dao.services.impl.documentlibrary;

import java.io.File;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.flowframe.documentlibrary.remote.services.IRemoteDocumentRepository;
import org.flowframe.kernel.common.utils.Validator;
import org.flowframe.kernel.common.mdm.dao.services.documentlibrary.IFolderDAOService;
import org.flowframe.kernel.common.mdm.domain.BaseEntity;
import org.flowframe.kernel.common.mdm.domain.documentlibrary.DocType;
import org.flowframe.kernel.common.mdm.domain.documentlibrary.FileEntry;
import org.flowframe.kernel.common.mdm.domain.documentlibrary.Folder;

@Transactional
@Repository
public class FolderDAOImpl implements IFolderDAOService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * Spring will inject a managed JPA {@link EntityManager} into this field.
	 */
	@PersistenceContext
	protected EntityManager em;
	
	@Autowired
	protected IRemoteDocumentRepository remoteDocumentRepository;

	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public Folder get(long id) {
		return em.getReference(Folder.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Folder> getAll() {
		return em.createQuery("select o from org.flowframe.kernel.common.mdm.domain.documentlibrary.Folder o record by o.id").getResultList();
	}

	private Folder getByName(String name) throws Exception {
		try {
			Query query = em.createQuery("select o from org.flowframe.kernel.common.mdm.domain.documentlibrary.Folder o WHERE o.name = :name");
			query.setParameter("name", name);
			return (Folder) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Folder getByFolderId(Long folderId) throws Exception {
		try {
			Query query = em
					.createQuery("select o from org.flowframe.kernel.common.mdm.domain.documentlibrary.Folder o WHERE o.folderId = :folderId");
			query.setParameter("folderId", folderId);
			return (Folder) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private Folder getByFolderIdAndName(Long folderId, String name)  {
		try {
			Query query = em
					.createQuery("select o from org.flowframe.kernel.common.mdm.domain.documentlibrary.Folder o WHERE o.folderId = :folderId AND o.name = :name");
			query.setParameter("folderId", folderId);
			query.setParameter("name", name);
			return (Folder) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}	

	@Override
	public Folder getByFolderIdOrName(Long folderId, String name) {
		assert (folderId != null || name != null) : "Both parameters were null.";

		if (folderId != null) {
			try {
				return getByFolderId(folderId);
			} catch (Exception e) {
				return null;
			}
		} else if (name != null) {
			try {
				return getByName(name);
			} catch (Exception e) {
				return null;
			}
		}

		return null;
	}

	@Override
	public Folder add(Folder record) {
		Folder res = null;
		if ((res = getByFolderIdAndName(record.getFolderId(), record.getName())) == null) {
			res = new Folder();

			res.setFolderId(record.getFolderId());
			res.setName(record.getName());
		}		

		res = em.merge(record);
		
		return res;
	}

	@Override
	public void delete(Folder record) {
		em.remove(record);
	}

	@Override
	public Folder update(Folder record) {
		return em.merge(record);
	}

	@Override
	public Folder provide(Folder record) {
		return add(record);
	}

	@Override
	public Folder provide(Long folderId, String name) {
		Folder folder = new Folder();

		folder.setFolderId(folderId);
		folder.setName(name);		

		return add(folder);
	}
	
	@Override
	public Folder provideFolderByJavaTypeName(String folderName) throws Exception {
		// -- Create remote
		Folder fldr = new Folder();
		if (remoteDocumentRepository.isAvailable())
			fldr = remoteDocumentRepository.addFolder(folderName, "Attachments for Record[" + folderName + "]");
		else {
			fldr.setFolderId(System.currentTimeMillis());
		}

		// -- Create local
		fldr.setName(folderName);
		fldr = provide(fldr);
		return fldr;
	}	
	
	@SuppressWarnings("rawtypes")
	@Override
	public Folder provideFolderForEntity(Class entityJavaClass, Long entityId) throws Exception {
		String st = entityJavaClass.getSimpleName();
		String folderName = st + "-" + entityId;

		return provideFolderByJavaTypeName(folderName);
	}	

	@Override
	public void provideDefaults() {
	}
	
	/**
	 * 
	 * FileEntry
	 * 
	 */
	private FileEntry getFileEntryByFileEntryId(Long fileEntryId) throws Exception {
		try {
			Query query = em.createQuery("select o from org.flowframe.kernel.common.mdm.domain.documentlibrary.FileEntry o WHERE o.fileEntryId = :fileEntryId");
			query.setParameter("fileEntryId", fileEntryId);
			return (FileEntry) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}	

	@Override
	public FileEntry addFileEntry(Long folderId, DocType attachmentType, FileEntry fileEntry) {
		Folder res = getByFolderIdOrName(folderId, null);
		fileEntry.setFolder(res);
		attachmentType = em.merge(attachmentType);
		fileEntry.setDocType(attachmentType);	
		fileEntry = em.merge(fileEntry);
		return fileEntry;
	}

	@Override
	public Folder addFileEntries(Long folderId, Set<FileEntry> fileEntries) {
		for (FileEntry fe : fileEntries) {
			addFileEntry(folderId, fe.getDocType(), fe);
		}
		return getByFolderIdOrName(folderId, null);
	}

	@Override
	public Folder deleteFileEntry(Long folderId, FileEntry fileEntry) {
		Folder res = getByFolderIdOrName(folderId, null);
		fileEntry = em.merge(fileEntry);
		res.getFiles().remove(fileEntry);
		res = em.merge(res);
		return res;
	}

	@Override
	public Folder deleteFileEntries(Long folderId, Set<FileEntry> fileEntries) {
		for (FileEntry fe : fileEntries) {
			deleteFileEntry(folderId, fe);
		}
		return getByFolderIdOrName(folderId, null);
	}
	

	@Override
	public FileEntry addorUpdateFileEntry(BaseEntity entity, DocType attachmentType, File sourceFile, String mimeType, String title,
			String description) throws Exception {
		FileEntry fe = null;
		Folder df = remoteDocumentRepository.provideFolderForEntity(entity).getDocFolder();
		df = provide(df);

		fe = remoteDocumentRepository.addorUpdateFileEntry(Long.toString(df.getFolderId()), sourceFile, mimeType, title, description);

		if (getFileEntryByFileEntryId(fe.getFileEntryId()) == null)
			fe = addFileEntry(df.getFolderId(), attachmentType, fe);
/*		else
			fe = em.merge(fe);*/

		return fe;
	}

	@Override
	public FileEntry addorUpdateFileEntry(Folder folder, DocType attachmentType, String sourceFileName, String mimeType, String title,
			String description) throws Exception {
		FileEntry fe = remoteDocumentRepository.addorUpdateFileEntryOnRepoOnly(folder, attachmentType, sourceFileName, mimeType, title, description);

		fe = addFileEntry(folder.getFolderId(), attachmentType, fe);

		return fe;
	}	
	

	@Override
	public FileEntry addorUpdateFileEntry(Folder folder, DocType attachmentType, File sourceFile, String mimeType, String title,
			String description) throws Exception {
		FileEntry fe = remoteDocumentRepository.addorUpdateFileEntry(Long.toString(folder.getFolderId()), sourceFile, mimeType, title, description);

		fe = addFileEntry(folder.getFolderId(), attachmentType, fe);

		return fe;
	}	
}
