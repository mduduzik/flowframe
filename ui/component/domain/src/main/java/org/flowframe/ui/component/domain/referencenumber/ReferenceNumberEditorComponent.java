package org.flowframe.ui.component.domain.referencenumber;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.flowframe.ds.domain.DataSource;
import org.flowframe.ui.component.domain.AbstractComponent;
import org.flowframe.ui.component.domain.table.GridComponent;

@Entity
public class ReferenceNumberEditorComponent extends AbstractComponent {
	public ReferenceNumberEditorComponent() {
		super("referencenumbereditorcomponent");
	}
	
	public ReferenceNumberEditorComponent(DataSource ds) {
		this();
		setDataSource(ds);
	}		
}
