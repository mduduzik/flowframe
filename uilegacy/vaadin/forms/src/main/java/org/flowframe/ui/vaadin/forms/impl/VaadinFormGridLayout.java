package org.flowframe.ui.vaadin.forms.impl;

import com.vaadin.ui.Field;
import com.vaadin.ui.GridLayout;

class VaadinFormGridLayout extends GridLayout {
	private static final long serialVersionUID = -21016456L;

	private int columnLimit;
	private int rowCursor;
	private int columnCursor;

	public VaadinFormGridLayout() {
		super(4, 1);

		setWidth("100%");
		setSpacing(true);
		setMargin(true, false, false, true);
		setStyleName("conx-entity-editor-form");

		this.columnLimit = 4;
		this.rowCursor = 0;
		this.columnCursor = 0;
	}

	public VaadinFormGridLayout(int columnLimit) {
		if (columnLimit > 4) {
			this.setColumns(columnLimit);
			this.columnLimit = columnLimit;
		} else {
			this.setColumns(4);
			if (columnLimit > 0) {
				this.columnLimit = columnLimit;
			} else {
				this.columnLimit = 4;
			}
		}
		this.setRows(1);

		setWidth("100%");
		setSpacing(true);
		setMargin(true, false, false, true);
		setStyleName("conx-entity-editor-form");

		this.rowCursor = 0;
		this.columnCursor = 0;
	}

	/**
	 * Adds a field according to the specified column limit. This limit defaults
	 * to 4.
	 * 
	 * @param field
	 *            field that will be added to the layout
	 */
	public void addField(Field field) {
		if (this.columnCursor == this.columnLimit) {
			this.rowCursor++;
			this.setRows(rowCursor + 1);
			this.columnCursor = 0;
		}

		this.addComponent(field, this.columnCursor, this.rowCursor);
		this.columnCursor++;
	}
}
