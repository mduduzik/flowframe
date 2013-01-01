package org.flowframe.ui.component.domain.attachment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.flowframe.ds.domain.DataSource;
import org.flowframe.ui.component.domain.AbstractComponent;
import org.flowframe.ui.component.domain.table.GridComponent;

@Entity
public class AttachmentEditorComponent extends AbstractComponent {
	public AttachmentEditorComponent() {
		super("attachmenteditorcomponent");
	}
	
	public AttachmentEditorComponent(DataSource ds) {
		this();
		setDataSource(ds);
	}		
}
