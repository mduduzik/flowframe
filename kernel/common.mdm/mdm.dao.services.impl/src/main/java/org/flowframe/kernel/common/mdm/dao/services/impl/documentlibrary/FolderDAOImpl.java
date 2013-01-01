package org.flowframe.kernel.common.mdm.dao.services.impl.documentlibrary;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.flowframe.kernel.common.utils.Validator;
import org.flowframe.kernel.common.mdm.dao.services.documentlibrary.IFolderDAOService;
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
	private EntityManager em;

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
		record = em.merge(record);

		return record;
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
		Folder existingRecord = getByFolderIdOrName(record.getFolderId(), record.getName());
		if (Validator.isNull(existingRecord)) {
			record = update(record);
		}
		return record;
	}

	@Override
	public Folder provide(Long folderId, String name) {
		Folder res = null;
		if ((res = getByFolderIdOrName(folderId, name)) == null) {
			Folder unit = new Folder();

			unit.setFolderId(folderId);
			unit.setName(name);

			res = add(unit);
		}
		return res;
	}

	@Override
	public void provideDefaults() {
	}

	@Override
	public FileEntry addFileEntry(Long folderId, DocType attachmentType, FileEntry fileEntry) {
		Folder res = getByFolderIdOrName(folderId, null);
		if (res != null) {
			attachmentType = em.merge(attachmentType);
			fileEntry.setDocType(attachmentType);
			fileEntry.setFolder(res);
			fileEntry = em.merge(fileEntry);
			res.getFiles().add(fileEntry);
			res = em.merge(res);
			return fileEntry;
		}
		return null;
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
}
