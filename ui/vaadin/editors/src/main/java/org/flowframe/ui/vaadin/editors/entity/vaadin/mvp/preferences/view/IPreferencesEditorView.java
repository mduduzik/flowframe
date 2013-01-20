package org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.preferences.view;

import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.preferences.view.PreferencesEditorView.ICreatePreferenceListener;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.preferences.view.PreferencesEditorView.IDeletePreferenceListener;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.preferences.view.PreferencesEditorView.IEditPreferenceListener;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.preferences.view.PreferencesEditorView.ISavePreferenceListener;

import com.vaadin.data.Container;
import com.vaadin.data.Item;

public interface IPreferencesEditorView {
	public void setContainerDataSource(Container container, String[] visibleGridColumnPropertyIds, String[] visibleGridColumnNames);
	
	public void showContent();
	
	public void create(Item item);
	public void delete(Item item);
	public void edit(Item item);
	public void save(Item item);
	
	public void addCreatePreferenceListener(ICreatePreferenceListener listener);
	public void addSavePreferenceListener(ISavePreferenceListener listener);
	public void addDeletePreferenceListener(IDeletePreferenceListener listener);
	public void addEditPreferenceListener(IEditPreferenceListener listener);
}