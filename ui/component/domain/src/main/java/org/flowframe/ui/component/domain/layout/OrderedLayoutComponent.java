package org.flowframe.ui.component.domain.layout;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.flowframe.ui.component.domain.AbstractComponent;

@Entity
public class OrderedLayoutComponent extends AbstractComponent implements Serializable {

	@OneToOne
    private AbstractOrderedLayoutComponent orderedLayout;
	
	@OneToOne
	private AbstractComponent component;
    
	private float ratio = 1.0f;
	
	public OrderedLayoutComponent(AbstractOrderedLayoutComponent orderLayout,
			AbstractComponent component) {
		this();
		this.component = component;
	}

	public OrderedLayoutComponent() {
		super("orderedLayoutComponent");
	}
}
