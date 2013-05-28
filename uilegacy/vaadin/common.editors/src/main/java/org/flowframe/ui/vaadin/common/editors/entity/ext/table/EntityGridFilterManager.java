package org.flowframe.ui.vaadin.common.editors.entity.ext.table;

import java.util.Locale;

import org.flowframe.ui.vaadin.addons.filteredtable.FilterDecorator;
import org.flowframe.ui.vaadin.addons.filteredtable.FilterGenerator;

import com.vaadin.data.Container.Filter;
import com.vaadin.terminal.Resource;
import com.vaadin.ui.CustomTable;
import com.vaadin.ui.CustomTable.ColumnGenerator;

public class EntityGridFilterManager implements FilterDecorator, FilterGenerator, ColumnGenerator {
	private static final long serialVersionUID = 2131588858L;

	@Override
	public Object generateCell(CustomTable customTable, Object itemId,
			Object columnId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Filter generateFilter(Object propertyId, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getEnumFilterDisplayName(Object propertyId, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resource getEnumFilterIcon(Object propertyId, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getBooleanFilterDisplayName(Object propertyId, boolean value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resource getBooleanFilterIcon(Object propertyId, boolean value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isTextFilterImmediate(Object propertyId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getTextChangeTimeout(Object propertyId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Locale getLocale() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFromCaption() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getToCaption() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSetCaption() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getClearCaption() {
		// TODO Auto-generated method stub
		return null;
	}

}
