package org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.preferences.view;

import java.util.Set;

import org.flowframe.ui.vaadin.addons.common.FlowFrameVerticalSplitPanel;
import org.flowframe.ui.vaadin.editors.entity.vaadin.ext.EntityEditorToolStrip;
import org.flowframe.ui.vaadin.editors.entity.vaadin.ext.EntityEditorToolStrip.EntityEditorToolStripButton;
import org.flowframe.ui.vaadin.editors.entity.vaadin.ext.preferences.PreferencesEditorForm;
import org.flowframe.ui.vaadin.editors.entity.vaadin.ext.table.EntityEditorGrid;
import org.flowframe.ui.vaadin.editors.entity.vaadin.ext.table.EntityEditorGrid.IDepletedListener;
import org.flowframe.ui.vaadin.editors.entity.vaadin.ext.table.EntityEditorGrid.IEditListener;
import org.flowframe.ui.vaadin.editors.entity.vaadin.ext.table.EntityEditorGrid.ISelectListener;
import org.flowframe.ui.vaadin.forms.listeners.IFormChangeListener;
import org.vaadin.mvp.uibinder.annotation.UiField;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;

public class PreferencesEditorView extends VerticalLayout implements IPreferencesEditorView {
	private static final long serialVersionUID = 1L;
	
	@UiField
	private VerticalLayout mainLayout;

	private FlowFrameVerticalSplitPanel splitPanel;
	private VerticalLayout masterLayout;
	private VerticalLayout detailLayout;
	
	private EntityEditorToolStrip gridToolStrip;
	private EntityEditorToolStripButton createButton;
	private EntityEditorToolStripButton editButton;
	private EntityEditorToolStripButton deleteButton;
	private EntityEditorGrid grid;
	private Item currentItem;
	
	private EntityEditorToolStrip formToolStrip;
	private EntityEditorToolStripButton validateButton;
	private EntityEditorToolStripButton saveButton;
	private PreferencesEditorForm form;
	
	private Set<ICreatePreferenceListener> createPreferenceItemListenerSet;
	private Set<ISavePreferenceListener> savePreferenceItemListenerSet;
	private Set<IEditPreferenceListener> editPreferenceItemListenerSet;
	private Set<IDeletePreferenceListener> deletePreferenceItemListenerSet;
	
	private void buildMasterLayout() {
		this.gridToolStrip = new EntityEditorToolStrip();
		this.createButton = this.gridToolStrip.addToolStripButton(EntityEditorToolStrip.TOOLSTRIP_CAPTION_CREATE);
		this.createButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				PreferencesEditorView.this.onCreatePreferenceItem();
			}
		});
		this.editButton = this.gridToolStrip.addToolStripButton(EntityEditorToolStrip.TOOLSTRIP_CAPTION_CREATE);
		this.editButton.setEnabled(true);
		this.editButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				PreferencesEditorView.this.onEditPreferenceItem(PreferencesEditorView.this.currentItem);
			}
		});
		this.deleteButton = this.gridToolStrip.addToolStripButton(EntityEditorToolStrip.TOOLSTRIP_CAPTION_DELETE);
		this.deleteButton.setEnabled(true);
		this.deleteButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				PreferencesEditorView.this.onDeletePreferenceItem(PreferencesEditorView.this.currentItem);
			}
		});
		
		this.grid = new EntityEditorGrid();
		this.grid.addDepletedListener(new IDepletedListener() {
			
			@Override
			public void onDepleted() {
				PreferencesEditorView.this.editButton.setEnabled(false);
				PreferencesEditorView.this.deleteButton.setEnabled(false);
			}
		});
		this.grid.addSelectListener(new ISelectListener() {
			
			@Override
			public void onSelect(Item item) {
				PreferencesEditorView.this.currentItem = item;
				PreferencesEditorView.this.editButton.setEnabled(true);
				PreferencesEditorView.this.deleteButton.setEnabled(true);
			}
		});
		this.grid.addEditListener(new IEditListener() {
			
			@Override
			public void onEdit(Item item) {
				PreferencesEditorView.this.currentItem = item;
				PreferencesEditorView.this.onEditPreferenceItem(item);
			}
		});
		
		this.masterLayout = new VerticalLayout();
		this.masterLayout.setSizeFull();
		this.masterLayout.addComponent(this.gridToolStrip);
		this.masterLayout.addComponent(this.grid);
		this.masterLayout.setExpandRatio(this.gridToolStrip, 0.0f);
		this.masterLayout.setExpandRatio(this.grid, 1.0f);
	}
	
	private void buildDetailLayout() {
		this.formToolStrip = new EntityEditorToolStrip();
		this.validateButton = this.formToolStrip.addToolStripButton(EntityEditorToolStrip.TOOLSTRIP_CAPTION_VERIFY);
		this.validateButton.setEnabled(false);
		this.validateButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (PreferencesEditorView.this.form.validateForm()) {
					PreferencesEditorView.this.validateButton.setEnabled(false);
					PreferencesEditorView.this.saveButton.setEnabled(true);
				}
			}
		});
		this.saveButton = this.formToolStrip.addToolStripButton(EntityEditorToolStrip.TOOLSTRIP_CAPTION_SAVE);
		this.saveButton.setEnabled(false);
		this.saveButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				PreferencesEditorView.this.onEditPreferenceItem(PreferencesEditorView.this.form.getItemDataSource());
			}
		});
		
		this.form = new PreferencesEditorForm();
		this.form.addListener(new IFormChangeListener() {
			
			@Override
			public void onFormChanged() {
				PreferencesEditorView.this.validateButton.setEnabled(true);
				PreferencesEditorView.this.saveButton.setEnabled(true);
			}
		});
		
		this.detailLayout = new VerticalLayout();
		this.detailLayout.setSizeFull();
		this.detailLayout.addComponent(this.formToolStrip);
		this.detailLayout.addComponent(this.form);
		this.detailLayout.setExpandRatio(this.formToolStrip, 0.0f);
		this.detailLayout.setExpandRatio(this.form, 1.0f);
	}
	
	private void buildView() {
		buildMasterLayout();
		buildDetailLayout();
		
		this.splitPanel = new FlowFrameVerticalSplitPanel();
		this.splitPanel.setFirstComponent(this.masterLayout);
		this.splitPanel.setSecondComponent(this.detailLayout);
		this.splitPanel.setLocked(true);
	}
	
	@Override
	public void showContent() {
		if (this.splitPanel == null) {
			buildView();
		}
		this.mainLayout.addComponent(this.splitPanel);
	}

	public Layout getMainLayout() {
		return mainLayout;
	}
	
	public void onCreatePreferenceItem() {
		for (ICreatePreferenceListener listener : createPreferenceItemListenerSet) {
			listener.onCreatePreference();
		}
	}
	
	public void onSavePreferenceItem(Item item) {
		for (ISavePreferenceListener listener : savePreferenceItemListenerSet) {
			listener.onSavePreference(item);
		}
	}
	
	public void onEditPreferenceItem(Item item) {
		for (IEditPreferenceListener listener : editPreferenceItemListenerSet) {
			listener.onEditPreference(item);
		}
	}
	
	public void onDeletePreferenceItem(Item item) {
		for (IDeletePreferenceListener listener : deletePreferenceItemListenerSet) {
			listener.onDeletePreference(item);
		}
	}
	
	@Override
	public void addCreatePreferenceListener(ICreatePreferenceListener listener) {
		createPreferenceItemListenerSet.add(listener);
	}
	
	@Override
	public void addSavePreferenceListener(ISavePreferenceListener listener) {
		savePreferenceItemListenerSet.add(listener);
	}
	
	@Override
	public void addEditPreferenceListener(IEditPreferenceListener listener) {
		editPreferenceItemListenerSet.add(listener);
	}
	
	@Override
	public void addDeletePreferenceListener(IDeletePreferenceListener listener) {
		deletePreferenceItemListenerSet.add(listener);
	}
	
	public interface ICreatePreferenceListener {
		public void onCreatePreference();
	}
	
	public interface IEditPreferenceListener {
		public void onEditPreference(Item item);
	}
	
	public interface IDeletePreferenceListener {
		public void onDeletePreference(Item item);
	}
	
	public interface ISavePreferenceListener {
		public void onSavePreference(Item item);
	}

	@Override
	public void setContainerDataSource(Container container, String[] visibleGridColumnPropertyIds, String[] visibleGridColumnNames) {
		this.grid.setContainerDataSource(container);
		this.grid.setVisibleColumns(visibleGridColumnPropertyIds);
		this.grid.setColumnTitles(visibleGridColumnNames);
	}

	@Override
	public void create(Item item) {
		assert (this.splitPanel != null) : "showContent() must be called first.";
		
		this.form.setItemDataSource(item);
		this.form.setActionCaption("Creating");
		this.splitPanel.setSplitPosition(100);
	}

	@Override
	public void delete(Item item) {
		assert (this.splitPanel != null) : "showContent() must be called first.";
	}

	@Override
	public void edit(Item item) {
		assert (this.splitPanel != null) : "showContent() must be called first.";

		this.form.setItemDataSource(item);
		this.form.setActionCaption("Editing");
		this.splitPanel.setSplitPosition(100);
	}

	@Override
	public void save(Item item) {
		assert (this.splitPanel != null) : "showContent() must be called first.";
		
		this.form.commit();
		this.splitPanel.setSplitPosition(0);
	}
}
