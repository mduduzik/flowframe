package org.flowframe.ui.vaadin.addons.common;

import org.flowframe.ui.vaadin.addons.filtertable.FilterDecorator;
import org.flowframe.ui.vaadin.addons.filtertable.FilterGenerator;
import org.flowframe.ui.vaadin.addons.filtertable.FilterTable;

import com.vaadin.data.Container;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;
import com.vaadin.terminal.Resource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomTable;
import com.vaadin.ui.CustomTable.ColumnGenerator;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public class FlowFrameEntityGrid extends VerticalLayout implements FilterDecorator, FilterGenerator, ColumnGenerator {
	private static final long serialVersionUID = -19074647254L;
	private static final String EDIT_ICON_URL = "toolstrip/img/edit-disabled.png";
	private static final String NEW_ICON_URL = "toolstrip/img/new-disabled.png";
	
	private FilterTable grid;
	
	public FlowFrameEntityGrid(Container container) {
		setSizeFull();
		grid = new FilterTable();
		grid.setSizeFull();
		grid.setSelectable(true);
		grid.setContainerDataSource(container);
		grid.setFilterDecorator(this);
		grid.setFilterGenerator(this);
		grid.setFiltersVisible(true);
		grid.addGeneratedColumn("actions", this);
		setStyleName("conx-entity-grid");
		addComponent(grid);
		setExpandRatio(grid, 1.0f);
	}

	public String getEnumFilterDisplayName(Object propertyId, Object value) {
		return "wooot";
	}

	public Resource getEnumFilterIcon(Object propertyId, Object value) {
		return null;
	}

	public String getBooleanFilterDisplayName(Object propertyId, boolean value) {
		return "BOOL";
	}

	public Resource getBooleanFilterIcon(Object propertyId, boolean value) {
		return null;
	}

	public boolean isTextFilterImmediate(Object propertyId) {
		return true;
	}

	public int getTextChangeTimeout(Object propertyId) {
		return 0;
	}

	public String getFromCaption() {
		return "from";
	}

	public String getToCaption() {
		return "to";
	}

	public String getSetCaption() {
		return "caption";
	}

	public String getClearCaption() {
		return "clear";
	}

	@SuppressWarnings("serial")
	public Filter generateFilter(Object propertyId, Object value) {
		return new Filter() {
			
			public boolean passesFilter(Object itemId, Item item)
					throws UnsupportedOperationException {
				return false;
			}
			
			public boolean appliesToProperty(Object propertyId) {
				return false;
			}
		};
	}

	public Object generateCell(CustomTable customTable, Object itemId,
			Object columnId) {
		return new CheckBox();
	}

	private class FlowFrameEntityGridActionPanel extends HorizontalLayout {
		private static final long serialVersionUID = 1004774389384738L;

		public FlowFrameEntityGridActionPanel(final Object itemId) {
			Button edit = new Button();
			edit.setIcon(new ThemeResource(EDIT_ICON_URL));
			edit.setStyleName("conx-grid-action");
			edit.addListener(new ClickListener() {
				private static final long serialVersionUID = 1L;

				public void buttonClick(ClickEvent event) {
					onEdit(itemId);
				}
			});
			Button create = new Button();
			create.setIcon(new ThemeResource(NEW_ICON_URL));
			create.setStyleName("conx-grid-action");
			create.addListener(new ClickListener() {
				private static final long serialVersionUID = 1L;

				public void buttonClick(ClickEvent event) {
					onCreate(itemId);
				}
			});
		}
	}
	
	private void onEdit(Object itemId) {
	}
	
	private void onCreate(Object itemId) {
	}
}
