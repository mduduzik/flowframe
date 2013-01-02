package org.flowframe.ui.component.domain.table;

import javax.persistence.Entity;

import org.flowframe.ds.domain.DataSource;

@Entity
public class DetailGridComponent extends GridComponent {

	public DetailGridComponent() {
		super("detailtable");
	}
	
	public DetailGridComponent(DataSource dataSource) {
		this();
		super.setDataSource(dataSource);
	}	
}
