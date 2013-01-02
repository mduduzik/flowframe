package org.flowframe.ui.component.domain.table;

import java.io.Serializable;

import javax.persistence.Entity;

import org.flowframe.ui.component.domain.AbstractComponent;
import org.flowframe.ui.component.domain.types.Alignment;

@Entity
public class ColumnAlignment extends AbstractComponent implements Serializable {

    private String columnName;
    
    private Alignment alignment;
	
	public ColumnAlignment(String columnName,
			Alignment alignment) {
		this();
		this.columnName = columnName;
		this.alignment = alignment;
	}

	public ColumnAlignment() {
		super("columnAlignment");
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public Alignment getAlignment() {
		return alignment;
	}

	public void setAlignment(Alignment alignment) {
		this.alignment = alignment;
	}
}
