package org.flowframe.ui.component.domain.layout;

import javax.persistence.Entity;

@Entity
public class HorizontalLayoutComponent extends AbstractOrderedLayoutComponent {

    public HorizontalLayoutComponent() {
        super("horizontalLayout");
        setWidth("100%");
    }
}
