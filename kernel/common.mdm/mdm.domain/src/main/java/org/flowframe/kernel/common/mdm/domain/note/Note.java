package org.flowframe.kernel.common.mdm.domain.note;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.flowframe.kernel.common.mdm.domain.MultitenantBaseEntity;
import org.flowframe.kernel.common.mdm.domain.metadata.DefaultEntityMetadata;
import org.flowframe.kernel.common.mdm.domain.referencenumber.ReferenceNumber;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name="ffmdmnote")
public class Note extends MultitenantBaseEntity {
    @OneToMany(mappedBy="parentNote",cascade = CascadeType.ALL,targetEntity=NoteItem.class)
    private Set<NoteItem> notes = new java.util.HashSet<NoteItem>();
    
    public Note() {
    }

	public Set<NoteItem> getNotes() {
		return notes;
	}

	public void setNotes(Set<NoteItem> notes) {
		this.notes = notes;
	}
}