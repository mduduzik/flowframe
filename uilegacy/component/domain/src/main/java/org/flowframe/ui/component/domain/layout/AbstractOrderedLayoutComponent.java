package org.flowframe.ui.component.domain.layout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.flowframe.ui.component.domain.AbstractComponent;
import org.flowframe.ui.component.domain.ComponentAlignment;
import org.flowframe.ui.component.domain.ComponentExpandRatio;

@Entity
public abstract class AbstractOrderedLayoutComponent extends AbstractLayoutComponent {
	
    /**
     * Custom layout slots containing the components.
     */
	@OneToMany(mappedBy="orderedLayout")
    protected List<OrderedLayoutComponent> components = new ArrayList<OrderedLayoutComponent>();
    
    /**
     * Mapping from components to alignments (horizontal + vertical).
     */
	//@OneToMany
    //private Set<ComponentAlignment> componentToAlignment = new HashSet<ComponentAlignment>();

    /**
     * Mapping from components to expandRatio.
     */   
	//@OneToMany
    //private Set<ComponentExpandRatio> componentToExpandRatio = new HashSet<ComponentExpandRatio>();

    /**
     * Is spacing between contained components enabled. Defaults to false.
     */
    private boolean spacing = false;

    public AbstractOrderedLayoutComponent() {
        super("abstractConXOrderedLayout");
    }

	public AbstractOrderedLayoutComponent(String typeId) {
		super(typeId);
	}
}
