package org.flowframe.ui.component.domain.search;

import javax.persistence.Entity;

import org.flowframe.ui.component.domain.AbstractComponent;
import org.flowframe.ui.component.domain.table.GridComponent;

@Entity
public class SearchGridComponent extends AbstractComponent {
	private static final long serialVersionUID = -3549577034613359094L;
	
	private String formTitle;
	private String[] visibleColumnIds;

	public SearchGridComponent() {
		super("searchgrid");
	}
	
	public void setFormTitle(String formTitle) {
		this.formTitle = formTitle;
	}

	public String getFormTitle() {
		return this.formTitle;
	}

	public String[] getVisibleColumnIds() {
		return visibleColumnIds;
	}

	public void setVisibleColumnIds(String[] visibleColumnIds) {
		this.visibleColumnIds = visibleColumnIds;
	}
	
}
