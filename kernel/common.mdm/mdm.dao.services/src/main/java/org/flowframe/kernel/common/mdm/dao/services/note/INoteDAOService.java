package org.flowframe.kernel.common.mdm.dao.services.note;

import java.util.List;
import java.util.Set;

import org.flowframe.kernel.common.mdm.domain.BaseEntity;
import org.flowframe.kernel.common.mdm.domain.note.Note;
import org.flowframe.kernel.common.mdm.domain.note.NoteItem;
import org.flowframe.kernel.common.mdm.domain.note.NoteType;

public interface INoteDAOService {
	public Note get(long id);
	
	public List<Note> getAll();

	public Note add(Note record);
	
	public NoteItem add(Long parentEntityPK, Class<?> parentEntityType) throws Exception;
	
	public BaseEntity provideNoteForEntity(BaseEntity baseEntity);
	
	public Note addNoteItem(Long noteId, NoteItem noteItem);
	
	public Note addNoteItems(Long noteId, Set<NoteItem> noteItems);

	public void delete(Note record);
	
	public Note deleteNoteItem(Long noteId, NoteItem noteItem);
	
	public Note deleteNoteItems(Long noteId, Set<NoteItem> noteItems);	

	public Note update(Note record);
	
	public void provideDefaults();

	public NoteType getByNoteTypeCode(String code);
}
