package org.flowframe.ui.component.domain.layout;

import javax.persistence.Entity;

@Entity
public class VerticalLayoutComponent extends AbstractOrderedLayoutComponent {

    public VerticalLayoutComponent() {
        super("verticalLayout");
        setWidth("100%");
    }
}
