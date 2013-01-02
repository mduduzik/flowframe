package org.flowframe.ui.component.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.flowframe.ui.component.domain.types.Alignment;

@Entity
public class ComponentAlignment extends AbstractComponent implements Serializable {

	@OneToOne
    private AbstractComponent component;
    
	private Alignment alignment;
	
	public ComponentAlignment(AbstractComponent component,
			Alignment alignment) {
		this();
		this.component = component;
		this.alignment = alignment;
	}

	public ComponentAlignment() {
		super("componentAlignment");
	}
}
