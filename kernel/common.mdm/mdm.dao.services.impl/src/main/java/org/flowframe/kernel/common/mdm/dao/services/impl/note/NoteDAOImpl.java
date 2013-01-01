package org.flowframe.kernel.common.mdm.dao.services.impl.note;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.flowframe.kernel.metamodel.dao.services.IEntityTypeDAOService;
import org.flowframe.kernel.common.mdm.dao.services.note.INoteDAOService;
import org.flowframe.kernel.common.mdm.domain.BaseEntity;
import org.flowframe.kernel.common.mdm.domain.constants.NoteTypeCustomCONSTANTS;
import org.flowframe.kernel.common.mdm.domain.metamodel.EntityType;
import org.flowframe.kernel.common.mdm.domain.note.Note;
import org.flowframe.kernel.common.mdm.domain.note.NoteItem;
import org.flowframe.kernel.common.mdm.domain.note.NoteType;

@Transactional
@Repository
public class NoteDAOImpl implements INoteDAOService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * Spring will inject a managed JPA {@link EntityManager} into this field.
	 */
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private IEntityTypeDAOService entityTypeDao;

	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public Note get(long id) {
		return em.getReference(Note.class, id);
	}

	@Override
	public List<Note> getAll() {
		return em.createQuery("select o from org.flowframe.kernel.common.mdm.domain.note.Note o record order by o.id", Note.class).getResultList();
	}

	@Override
	public Note add(Note record) {
		record = em.merge(record);

		return record;
	}

	public NoteType addNoteType(NoteType record) {
		record = em.merge(record);

		return record;
	}

	@Override
	public void delete(Note record) {
		em.remove(record);
	}

	@Override
	public Note update(Note record) {
		return em.merge(record);
	}

	@Override
	public Note addNoteItem(Long noteId, NoteItem fileEntry) {
		Note res = get(noteId);
		fileEntry = em.merge(fileEntry);
		res.getNotes().add(fileEntry);
		res = em.merge(res);
		return res;
	}

	@Override
	public Note addNoteItems(Long noteId, Set<NoteItem> noteItems) {
		for (NoteItem fe : noteItems) {
			addNoteItem(noteId, fe);
		}
		return get(noteId);
	}

	@Override
	public Note deleteNoteItem(Long noteId, NoteItem fileEntry) {
		Note res = get(noteId);
		fileEntry = em.merge(fileEntry);
		res.getNotes().remove(fileEntry);
		res = em.merge(res);
		return res;
	}

	@Override
	public Note deleteNoteItems(Long noteId, Set<NoteItem> noteItems) {
		for (NoteItem fe : noteItems) {
			deleteNoteItem(noteId, fe);
		}
		return get(noteId);
	}

	@Override
	public NoteType getByNoteTypeCode(String code) {
		NoteType res = null;

		try {
			TypedQuery<NoteType> q = em.createQuery("select o from org.flowframe.kernel.common.mdm.domain.note.NoteType o WHERE o.code = :code",
					NoteType.class);
			q.setParameter("code", code);

			res = q.getSingleResult();
		} catch (NoResultException e) {
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Error e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
		}

		return res;
	}

	public NoteType provideNoteType(String code, String name) {
		NoteType res = null;
		if ((res = getByNoteTypeCode(code)) == null) {
			NoteType unit = new NoteType();

			unit.setCode(code);
			unit.setName(name);

			res = addNoteType(unit);
		}
		return res;
	}

	@Override
	public void provideDefaults() {
		provideNoteType(NoteTypeCustomCONSTANTS.TYPE_SPECIAL_INSTRUCTION_CODE, NoteTypeCustomCONSTANTS.TYPE_SPECIAL_INSTRUCTION_NAME);
		provideNoteType(NoteTypeCustomCONSTANTS.TYPE_OTHER_CODE, NoteTypeCustomCONSTANTS.TYPE_OTHER_NAME);
	}

	@Override
	public BaseEntity provideNoteForEntity(BaseEntity baseEntity) {
		assert (baseEntity != null) : "The entity was null.";
		assert (baseEntity.getId() != null || baseEntity.getCode() != null) : "The entity was not persistent.";
		
		if (baseEntity.getNote() == null) {
			Note note = new Note();
			note = em.merge(note);
			note.setCode(baseEntity.getCode() + "-NT" + note.getId());
			note.setName(note.getCode());
			note = em.merge(note);
			baseEntity.setNote(note);
			em.merge(baseEntity);
		}
		
		return baseEntity;
	}

	@Override
	public NoteItem add(Long parentEntityPK, Class<?> parentType) throws Exception {
		assert (parentEntityPK != null) : "Parent Entity Id was null.";
		assert (parentType != null) : "Parent Entity Type was null.";
		Object parentEntity = em.getReference(parentType, parentEntityPK);
		assert (parentEntity instanceof BaseEntity) : "Parent Entity was not of type Base Entity.";
		EntityType parentEntityType = entityTypeDao.provide(parentType);
		assert (parentEntityType != null) : "Could not get parent entity metadata.";
		NoteItem newRecord = new NoteItem();
		newRecord.setOwnerEntityId(((BaseEntity) parentEntity).getId());
		newRecord.setOwnerEntityTypeId(parentEntityType.getId());
		newRecord = em.merge(newRecord);
		
		String format = String.format("%%0%dd", 3);
		String paddedId = String.format(format, newRecord.getId());
		String code = ((BaseEntity) parentEntity).getCode() + "-NI" + paddedId;
		newRecord.setName(code);
		newRecord.setCode(code);
		newRecord = em.merge(newRecord);
		
		return newRecord;
	}

	public IEntityTypeDAOService getEntityTypeDao() {
		return entityTypeDao;
	}

	public void setEntityTypeDao(IEntityTypeDAOService entityTypeDao) {
		this.entityTypeDao = entityTypeDao;
	}
}
