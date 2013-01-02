package org.flowframe.ui.component.domain.table;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.flowframe.ui.component.domain.AbstractComponent;

@Entity
public class ColumnWidth extends AbstractComponent implements Serializable {

	
	@ManyToOne 
	private GridComponent table;
	
    private String columnName;
	
	public ColumnWidth(String columnName,
			String width) {
		this();
		this.columnName = columnName;
		setWidth(width);
	}

	public ColumnWidth() {
		super("columnWidth");
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
}
