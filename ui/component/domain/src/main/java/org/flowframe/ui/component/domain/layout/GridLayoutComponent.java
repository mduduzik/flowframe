package org.flowframe.ui.component.domain.layout;

import javax.persistence.Entity;

@Entity
public class GridLayoutComponent extends AbstractOrderedLayoutComponent {
	
    /**
     * Initial grid columns.
     */
    private int cols = 0;

    /**
     * Initial grid rows.
     */
    private int rows = 0;	

    public GridLayoutComponent() {
        super("gridLayout");
    }

	public GridLayoutComponent(String typeId, int cols, int rows) {
		super(typeId);
		this.cols = cols;
		this.rows = rows;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
}
