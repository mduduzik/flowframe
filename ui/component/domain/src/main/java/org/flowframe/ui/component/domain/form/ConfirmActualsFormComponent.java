package org.flowframe.ui.component.domain.form;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.flowframe.ds.domain.DataSource;

@Entity
public class ConfirmActualsFormComponent extends FormComponent {
	private static final long serialVersionUID = 2274128688130249656L;
	
	@OneToOne(cascade=CascadeType.ALL)
	private ConfirmActualsFieldSetComponent fieldSet;
	
	public ConfirmActualsFormComponent() {
		super("confirmactualsform");
	}
	
	public ConfirmActualsFormComponent(DataSource ds) {
		this();
		this.setDataSource(ds);
	}
	
	public ConfirmActualsFormComponent(DataSource ds, String caption) {
		this(ds);
		this.setCaption(caption);
	}

	public ConfirmActualsFieldSetComponent getFieldSet() {
		return fieldSet;
	}

	public void setFieldSet(ConfirmActualsFieldSetComponent fieldSet) {
		this.fieldSet = fieldSet;
	}

}
