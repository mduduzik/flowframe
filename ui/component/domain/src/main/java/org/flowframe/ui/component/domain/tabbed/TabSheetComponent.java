package org.flowframe.ui.component.domain.tabbed;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.flowframe.ui.component.domain.AbstractComponent;

@Entity
public class TabSheetComponent extends AbstractComponent {

	@OneToMany(mappedBy="tabSheet")
    protected List<TabSheetTabComponent> components = new ArrayList<TabSheetTabComponent>();
    
    public TabSheetComponent() {
        super("tabSheet");
        setWidth("100%");
    }
}
