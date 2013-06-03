package org.flowframe.ui.vaadin.editors.entity.vaadin.ext.search;

import org.flowframe.ui.component.domain.search.SearchGridComponent;
import org.flowframe.ui.component.domain.table.GridComponent;
import org.flowframe.ui.vaadin.addons.common.FlowFrameVerticalSplitPanel;
import org.flowframe.ui.vaadin.addons.filteredtable.FilterTable;
import org.flowframe.ui.vaadin.editors.entity.vaadin.ext.EntityEditorToolStrip;
import org.flowframe.ui.vaadin.editors.entity.vaadin.ext.EntityEditorToolStrip.EntityEditorToolStripButton;
import org.flowframe.ui.vaadin.editors.entity.vaadin.ext.table.EntityGridFilterManager;
import org.flowframe.ui.vaadin.forms.impl.VaadinFormAlertPanel;
import org.flowframe.ui.vaadin.forms.impl.VaadinFormAlertPanel.AlertType;
import org.flowframe.ui.vaadin.forms.impl.VaadinSearchForm;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.VerticalLayout;

public class EntityGrid extends VerticalLayout {
	private static final long serialVersionUID = 5347124943163564312L;

	private VaadinSearchForm searchForm;
	private FilterTable grid;
	private FlowFrameVerticalSplitPanel splitPanel;
	private EntityEditorToolStrip formToolStrip;
	private EntityEditorToolStrip gridToolStrip;
	private GridComponent componentModel;
	private VaadinFormAlertPanel gridStatus;
	private boolean statusEnabled;
	private Object selectedEntity;

	public EntityGrid(GridComponent componentModel) {
		this.componentModel = componentModel;
		this.searchForm = new VaadinSearchForm();
		this.grid = new FilterTable();
		this.splitPanel = new FlowFrameVerticalSplitPanel();
		this.formToolStrip = new EntityEditorToolStrip();
		this.gridToolStrip = new EntityEditorToolStrip();
		this.gridStatus = new VaadinFormAlertPanel();

		initialize();
	}
	
	public GridComponent getComponentModel() {
		return this.componentModel;
	}

	private void initialize() {
		final EntityEditorToolStripButton applyFilterButton = this.formToolStrip.addToolStripButton(EntityEditorToolStrip.TOOLSTRIP_IMG_SEARCH_PNG);
		applyFilterButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("rawtypes")
			@Override
			public void buttonClick(ClickEvent event) {
				//((JPAContainer) EntityGrid.this.grid.getContainerDataSource()).removeAllContainerFilters();
				//EntityGrid.this.searchForm.buildQuery();
				((JPAContainer) EntityGrid.this.grid.getContainerDataSource()).applyFilters();
				//updateGridStatus();
			}
		});

		final EntityEditorToolStripButton resetFilterButton = this.formToolStrip.addToolStripButton(EntityEditorToolStrip.TOOLSTRIP_IMG_RESET_PNG);
		resetFilterButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				//EntityGrid.this.searchForm.resetForm();
			}
		});

		final EntityEditorToolStripButton clearFilterButton = this.formToolStrip.addToolStripButton(EntityEditorToolStrip.TOOLSTRIP_IMG_CLEAR_PNG);
		clearFilterButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("rawtypes")
			@Override
			public void buttonClick(ClickEvent event) {
				//EntityGrid.this.searchForm.clearForm();
				((JPAContainer) EntityGrid.this.grid.getContainerDataSource()).removeAllContainerFilters();
				//updateGridStatus();
			}
		});

		final ThemeResource filterIcon = new ThemeResource(EntityEditorToolStrip.TOOLSTRIP_IMG_FILTER_PNG);
		final ThemeResource hideFilterIcon = new ThemeResource(EntityEditorToolStrip.TOOLSTRIP_IMG_HIDE_FILTER_PNG);
		final EntityEditorToolStripButton showFilterButton = this.gridToolStrip.addToolStripButton(EntityEditorToolStrip.TOOLSTRIP_IMG_FILTER_PNG);
		showFilterButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (EntityGrid.this.splitPanel.getSplitPosition() == 0) {
					EntityGrid.this.splitPanel.setSplitPosition(50);
					EntityGrid.this.splitPanel.setLocked(false);
					showFilterButton.setIcon(hideFilterIcon);
				} else {
					EntityGrid.this.splitPanel.setSplitPosition(0);
					EntityGrid.this.splitPanel.setLocked(true);
					showFilterButton.setIcon(filterIcon);
				}
			}
		});

		//this.searchForm.setTitle(componentModel.getFormTitle());

		EntityGridFilterManager gridManager = new EntityGridFilterManager();

		this.gridStatus.setVisible(false);
		this.gridStatus.setCloseable(false);

		this.grid.setSizeFull();
		this.grid.setMultiSelect(false);
		this.grid.setSelectable(true);
		this.grid.setImmediate(true);
		this.grid.setNullSelectionAllowed(false);
		this.grid.setFilterDecorator(gridManager);
		this.grid.setFilterGenerator(gridManager);
		this.grid.setFiltersVisible(true);
		this.grid.setImmediate(true);
		this.grid.addListener(new ItemClickListener() {
			private static final long serialVersionUID = -4650994592971165778L;

			@Override
			public void itemClick(ItemClickEvent event) {
				updateGridStatus(event.getItem());
			}
		});

		this.searchForm.setSizeFull();

		VerticalLayout formWrapper = new VerticalLayout();
		formWrapper.setSizeFull();
		formWrapper.addComponent(this.formToolStrip);
		formWrapper.addComponent(this.searchForm);
		formWrapper.setExpandRatio(this.searchForm, 1.0f);

		VerticalLayout gridWrapper = new VerticalLayout();
		gridWrapper.setSizeFull();
		gridWrapper.setStyleName("conx-entity-grid");
		gridWrapper.addComponent(this.gridToolStrip);
		gridWrapper.addComponent(this.gridStatus);
		gridWrapper.addComponent(this.grid);
		gridWrapper.setExpandRatio(this.grid, 1.0f);

		this.splitPanel = new FlowFrameVerticalSplitPanel();
		this.splitPanel.setSizeFull();
		this.splitPanel.setImmediate(true);
		this.splitPanel.setSplitPosition(0);
		this.splitPanel.setStyleName("conx-entity-editor");
		EntityGrid.this.splitPanel.setLocked(true);
		this.splitPanel.setFirstComponent(formWrapper);
		this.splitPanel.setSecondComponent(gridWrapper);

		setWidth("100%");
		// setHeight("400px");
		setHeight("100%");
		// addComponent(this.gridStatus);
		addComponent(this.splitPanel);
		setExpandRatio(this.splitPanel, 1.0f);
		
		setStatusEnabled(false);
	}

	public void setContainerDataSource(Container container) {
		this.grid.setContainerDataSource(container);
		//this.searchForm.setContainer(container);
		this.grid.setVisibleColumns(this.componentModel.getVisibleColumnIds());
	}

	public Object getSelectedEntity() {
		return this.selectedEntity;
	}

	public void setStatusEnabled(boolean enabled) {
		this.statusEnabled = enabled;
		if (this.statusEnabled) {
			updateGridStatus();
			this.gridStatus.setVisible(true);
		} else {
			this.gridStatus.setVisible(false);
		}
	}

	private void updateGridStatus() {
		Object id = EntityGrid.this.grid.getValue();
		if (id != null) {
			Item item = this.grid.getItem(id);
			updateGridStatus(item);
		} else {
			this.gridStatus.setAlertType(AlertType.ERROR);
			this.gridStatus.setMessage("No item is currently selected.");
		}
	}

	private void updateGridStatus(Item item) {
		if (this.statusEnabled) {
			this.selectedEntity = ((EntityItem<?>) item).getEntity();
			Property nameProperty = item.getItemProperty("name");
			String name = nameProperty.getValue().toString();
			if (name != null) {
				EntityGrid.this.gridStatus.setAlertType(AlertType.SUCCESS);
				EntityGrid.this.gridStatus.setMessage(name + " is currently selected.");
				return;
			}
		}
	}
}
