package org.flowframe.ui.component.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class ComponentExpandRatio extends AbstractComponent implements Serializable {

	@OneToOne
    private AbstractComponent component;
    
	private float ratio = 1.0f;
	
	public ComponentExpandRatio(AbstractComponent component,
			float ratio) {
		this();
		this.component = component;
		this.ratio = ratio;
	}

	public ComponentExpandRatio() {
		super("componentExpandRatio");
	}
}
