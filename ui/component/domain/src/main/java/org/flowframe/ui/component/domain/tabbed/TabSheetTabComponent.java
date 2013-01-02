package org.flowframe.ui.component.domain.tabbed;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.flowframe.ui.component.domain.AbstractComponent;

@Entity
public class TabSheetTabComponent extends AbstractComponent {

	@OneToOne
    private TabSheetComponent tabSheet;
	
	@OneToOne
	private TabComponent tab;
	
	private int ordinal;
	
    
    public TabSheetTabComponent(String typeId, TabSheetComponent tabSheet, TabComponent tab,
			int ordinal) {
		super(typeId);
		this.tabSheet = tabSheet;
		this.tab = tab;
		this.ordinal = ordinal;
	}

	public TabSheetTabComponent() {
        super("tabSheetTab");
    }

	public TabSheetComponent getTabSheet() {
		return tabSheet;
	}

	public void setTabSheet(TabSheetComponent tabSheet) {
		this.tabSheet = tabSheet;
	}

	public TabComponent getTab() {
		return tab;
	}

	public void setTab(TabComponent tab) {
		this.tab = tab;
	}

	public int getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}
}
