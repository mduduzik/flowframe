package org.flowframe.ui.component.domain.masterdetail;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.flowframe.ds.domain.DataSource;
import org.flowframe.ui.component.domain.AbstractComponent;
import org.flowframe.ui.component.domain.form.FormComponent;
import org.flowframe.ui.component.domain.table.GridComponent;

@Entity
public class MasterDetailComponent extends AbstractComponent {
	private static final long serialVersionUID = -3219294587155961706L;
	
	private boolean hasGrid;

	@ManyToOne
	private AbstractComponent masterComponent;
	
	@OneToOne
	private LineEditorContainerComponent lineEditorPanel;

	public MasterDetailComponent() {
		super("masterdetailcomponent");
	}
	
	public MasterDetailComponent(String code, String name, DataSource ds) {
		this(code,name);
		setDataSource(ds);
	}		
	
	public MasterDetailComponent(String code, String name) {
		this();
		setCode(code);
		setName(name);
	}	

	public MasterDetailComponent(String name,
			GridComponent table,
			LineEditorContainerComponent lineEditorPanel) {
		this();
		this.masterComponent = table;
		this.lineEditorPanel = lineEditorPanel;
	}

	public AbstractComponent getMasterComponent() {
		return masterComponent;
	}

	public void setMasterComponent(GridComponent masterComponent) {
		this.masterComponent = masterComponent;
		this.setDataSource(getDataSource());
		this.setHasGrid(true);
	}
	
	public void setMasterComponent(FormComponent masterComponent) {
		this.masterComponent = masterComponent;
		this.setDataSource(getDataSource());
		this.setHasGrid(false);
	}

	public LineEditorContainerComponent getLineEditorPanel() {
		return lineEditorPanel;
	}

	public void setLineEditorPanel(LineEditorContainerComponent lineEditorPanel) {
		this.lineEditorPanel = lineEditorPanel;
	}

	public boolean hasGrid() {
		return hasGrid;
	}

	public void setHasGrid(boolean hasGrid) {
		this.hasGrid = hasGrid;
	} 
}
