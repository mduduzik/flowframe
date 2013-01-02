package org.flowframe.ui.component.domain.table;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.flowframe.ui.component.domain.AbstractComponent;
import org.flowframe.ui.component.domain.types.Alignment;

@Entity
public class ColumnIconPath extends AbstractComponent implements Serializable {
	
	@ManyToOne 
	private GridComponent table;

    private String columnName;
	
	public ColumnIconPath(String columnName,
			String iconPath) {
		this();
		this.columnName = columnName;
		setIconPath(iconPath);
	}

	public ColumnIconPath() {
		super("columnIconPath");
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
}
