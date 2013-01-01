package org.flowframe.ui.component.domain.tabbed;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.flowframe.ui.component.domain.AbstractComponent;

@Entity
public class TabComponent extends AbstractComponent implements ITabComponent {

    private boolean closable = false;
    private String styleName;
    
    @OneToOne
    private AbstractComponent component;
    
    public TabComponent() {
        super("tab");
    }

    public boolean isClosable() {
        return closable;
    }

    public void setClosable(boolean closable) {
        this.closable = closable;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public String getStyleName() {
        return styleName;
    }

	@Override
	public AbstractComponent getComponent() {
		return component;
	}    
}
