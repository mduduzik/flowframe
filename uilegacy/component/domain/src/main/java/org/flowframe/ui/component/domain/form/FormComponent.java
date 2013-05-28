package org.flowframe.ui.component.domain.form;

import javax.persistence.Entity;

import org.flowframe.ds.domain.DataSource;
import org.flowframe.ui.component.domain.AbstractFieldComponent;

@Entity
public abstract class FormComponent extends AbstractFieldComponent {
	private static final long serialVersionUID = 2526927001704625541L;

	public FormComponent(String typeId) {
		super(typeId);
	}
	
	public FormComponent(String typeId, DataSource ds) {
		this(typeId);
		this.setDataSource(ds);
	}
}
