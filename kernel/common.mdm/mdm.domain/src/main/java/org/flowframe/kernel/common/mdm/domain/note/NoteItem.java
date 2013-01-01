package org.flowframe.kernel.common.mdm.domain.note;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.flowframe.kernel.common.mdm.domain.MultitenantBaseEntity;


@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name="ffmdmnoteitem")
public class NoteItem extends MultitenantBaseEntity {
    @ManyToOne(targetEntity = Note.class)
    private Note parentNote;
    
    @ManyToOne(targetEntity = NoteType.class)
    private NoteType noteType;    
    
    @Size(max=4096)
    private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public NoteType getNoteType() {
		return noteType;
	}

	public void setNoteType(NoteType noteType) {
		this.noteType = noteType;
	}

	public Note getParentNote() {
		return parentNote;
	}

	public void setParentNote(Note parentNote) {
		this.parentNote = parentNote;
	}
}