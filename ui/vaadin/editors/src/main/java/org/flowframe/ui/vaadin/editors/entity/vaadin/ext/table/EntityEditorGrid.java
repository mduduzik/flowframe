package org.flowframe.ui.vaadin.editors.entity.vaadin.ext.table;

import java.util.HashSet;
import java.util.Set;

import org.flowframe.ui.vaadin.addons.filtertable.FilterDecorator;
import org.flowframe.ui.vaadin.addons.filtertable.FilterGenerator;
import org.flowframe.ui.vaadin.addons.filtertable.FilterTable;

import com.vaadin.data.Container;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.terminal.Resource;
import com.vaadin.ui.VerticalLayout;

public class EntityEditorGrid extends VerticalLayout implements FilterDecorator, FilterGenerator {
	private static final long serialVersionUID = 2367339435187822029L;

	private Set<IEditListener> editListenerSet;
	private Set<ISelectListener> selectListenerSet;
	private Set<IDepletedListener> depletedListenerSet;

	private FilterTable grid;

	public EntityEditorGrid() {
		this.grid = new FilterTable();
		this.editListenerSet = new HashSet<EntityEditorGrid.IEditListener>();
		this.selectListenerSet = new HashSet<EntityEditorGrid.ISelectListener>();
		this.depletedListenerSet = new HashSet<EntityEditorGrid.IDepletedListener>();

		initialize();
	}

	private void initialize() {
		this.grid.setSizeFull();
		this.grid.setMultiSelect(false);
		this.grid.setSelectable(true);
		this.grid.setNullSelectionAllowed(false);
		this.grid.setFilterDecorator(this);
		this.grid.setFilterGenerator(this);
		this.grid.setFiltersVisible(true);
		this.grid.addListener(new ItemClickListener() {
			private static final long serialVersionUID = -4650994592971165778L;

			@Override
			public void itemClick(ItemClickEvent event) {
				if (event.isDoubleClick()) {
					onEdit(event.getItemId());
				} else if (event.getButton() == ItemClickEvent.BUTTON_LEFT) {
					onSelect(event.getItemId());
				}
			}
		});

		setSizeFull();
		setStyleName("conx-entity-grid");
		addComponent(grid);
		setExpandRatio(grid, 1.0f);
	}

	private void onEdit(Object id) {
		if (id != null) {
			Item item = this.grid.getItem(id);
			for (IEditListener listener : editListenerSet) {
				listener.onEdit(item);
			}
		}
	}
	
	public Item getSelectedItem() {
		Object id = this.grid.getValue();
		if (id == null) {
			return null;
		} else {
			return this.grid.getItem(id);
		}
	}
	
	public void printGrid() {
		// FIXME : Recreate FilterTableExcelExport
		/*FilterTableExcelExport excelExport = new FilterTableExcelExport(this.grid);
		excelExport.excludeCollapsedColumns();
		excelExport.setReportTitle("Demo Report");
		excelExport.export();*/	
	}

	public void addEditListener(IEditListener listener) {
		editListenerSet.add(listener);
	}

	private void onSelect(Object id) {
		if (id != null) {
			Item item = this.grid.getItem(id);
			for (ISelectListener listener : selectListenerSet) {
				listener.onSelect(item);
			}
		}
	}

	public void addSelectListener(ISelectListener listener) {
		selectListenerSet.add(listener);
	}
	
	private void onDepleted() {
		for (IDepletedListener listener : depletedListenerSet) {
			listener.onDepleted();
		}
	}
	
	public void addDepletedListener(IDepletedListener listener) {
		depletedListenerSet.add(listener);
	}

	public void setContainerDataSource(Container container) {
		this.grid.setContainerDataSource(container);
	}

	public void setVisibleColumns(Object[] visibleColumnIds) {
		this.grid.setVisibleColumns(visibleColumnIds);
	}
	
	public void setColumnTitles(String[] columnTitles) {
		this.grid.setColumnHeaders(columnTitles);
	}

	public interface IEditListener {
		public void onEdit(Item item);
	}

	public interface ISelectListener {
		public void onSelect(Item item);
	}
	
	public interface IDepletedListener {
		public void onDepleted();
	}

	@Override
	public Filter generateFilter(Object propertyId, Object value) {
		return null;
	}

	@Override
	public String getEnumFilterDisplayName(Object propertyId, Object value) {
		return null;
	}

	@Override
	public Resource getEnumFilterIcon(Object propertyId, Object value) {
		return null;
	}

	@Override
	public String getBooleanFilterDisplayName(Object propertyId, boolean value) {
		return null;
	}

	@Override
	public Resource getBooleanFilterIcon(Object propertyId, boolean value) {
		return null;
	}

	@Override
	public boolean isTextFilterImmediate(Object propertyId) {
		return false;
	}

	@Override
	public int getTextChangeTimeout(Object propertyId) {
		return 0;
	}

	@Override
	public String getFromCaption() {
		return null;
	}

	@Override
	public String getToCaption() {
		return null;
	}

	@Override
	public String getSetCaption() {
		return null;
	}

	@Override
	public String getClearCaption() {
		return null;
	}
	
	public void deleteItem(Item item) throws Exception {
		if (!this.grid.removeItem(item)) {
			throw new Exception("Could not delete item" + item.toString());
		} else {
			if (this.grid.getItemIds().size() == 0) {
				onDepleted();
			}
		}
	}
	
	public Container getContainerDataSource() {
		return this.grid.getContainerDataSource();
	}
}
