package org.flowframe.ui.vaadin.common.item;

import java.lang.reflect.Constructor;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.flowframe.kernel.common.mdm.domain.metamodel.BasicType;
import org.flowframe.kernel.common.mdm.domain.preferences.EntityPreference;
import org.flowframe.kernel.common.mdm.domain.preferences.EntityPreferenceItem;
import org.flowframe.kernel.metamodel.dao.services.IBasicTypeDAOService;

import com.vaadin.data.Item;
import com.vaadin.data.Property;

public class PreferenceItem implements Item{
	
	private Map<String, Property> typedProperties = new LinkedHashMap<String, Property>();
	private IBasicTypeDAOService basicTypeDApService;
	private EntityPreference preference;
	
	

	public PreferenceItem(EntityPreference preference, IBasicTypeDAOService basicTypeDApService) {
		this.basicTypeDApService = basicTypeDApService;
		this.preference = preference;
		init(preference);
	}

	private void init(EntityPreference preference) {
		Set<EntityPreferenceItem> items = preference.getItems();
		Property newProperty = null;
		for (EntityPreferenceItem item : items) {
			newProperty = new EntityPreferenceProperty(item,this.preference,this.basicTypeDApService);
			typedProperties.put(item.getPreferenceKey(), newProperty);
		}
	}

	@Override
	public Property getItemProperty(Object id) {
		return this.typedProperties.get(id);
	}

	@Override
	public Collection<?> getItemPropertyIds() {
		String[] ids = new String[typedProperties.keySet().size()];
		List<EntityPreferenceItem> orderedItems = new ArrayList<EntityPreferenceItem>(this.preference.getItems());
		int index = 0;
		Collections.sort(orderedItems,new EntityPreferenceItemComparator());
		for (EntityPreferenceItem epi : orderedItems)
		{
			ids[index++] = epi.getPreferenceKey();
		}

		return Arrays.asList(ids);
	}

	@Override
	public boolean addItemProperty(Object id, Property property) throws UnsupportedOperationException {
		Class type = property.getType();
		BasicType bt = null;
		try {
			bt = this.basicTypeDApService.provide(type);
		} catch (ClassNotFoundException e) {
			throw new UnsupportedOperationException("basicTypeDAOService threw UnsupportedOperationException",e);
		}
		String strValue = property.getValue() != null ? property.getValue().toString() : "";
		EntityPreferenceItem item = new EntityPreferenceItem((String)id, strValue, bt, 0);
		
		Property newProperty = new EntityPreferenceProperty(item,this.preference,this.basicTypeDApService);
		
		this.typedProperties.put((String)id, newProperty);
		return true;
	}

	@Override
	public boolean removeItemProperty(Object id) throws UnsupportedOperationException {
		return typedProperties.remove(id) != null;
	}
	
	
	public class EntityPreferenceItemComparator implements Comparator<EntityPreferenceItem> {
		@Override
		public int compare(EntityPreferenceItem o1, EntityPreferenceItem o2) {
			Integer o1o = o1.getOrdinal();
			Integer o2o = o2.getOrdinal();
			return o1o.compareTo(o2o);
		}
	}
	
	/**
	 * Property implementation that wraps a single item in Properties into
	 * Vaadin-compatible Property.
	 */
	public class EntityPreferenceProperty implements Property {

		private static final long serialVersionUID = 1L;
		private BasicType type = null;
		private EntityPreference preference;
		private boolean readOnly = false;
		private String key;
		private Constructor constructor = null;
		private SimpleDateFormat df;
		private EntityPreferenceItem prefItem;
		private IBasicTypeDAOService basicTypeDAOService;
		private List<String> options = null;

		private EntityPreferenceProperty(EntityPreferenceItem prefItem, EntityPreference preference, IBasicTypeDAOService basicTypeDAOService) {
			this.prefItem = prefItem;
			this.key = prefItem.getPreferenceKey();
			this.preference = preference;
			this.basicTypeDAOService = basicTypeDAOService;
			try {
				this.type = prefItem.getBasicType();
			}
			catch(Exception e){
				e.printStackTrace();
			}
			Class javaType = null;
			try {
				javaType = this.type.getJavaType();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (javaType.equals(Date.class)) {
				df = new SimpleDateFormat();
			}
			
			if (this.prefItem.getOption() != null && this.prefItem.getOption().getElements().size() > 0) {
				this.options = new ArrayList<String>(this.prefItem.getOption().getElements());
				Collections.sort(this.options);
			}
				
		}

		/**
		 * @see com.vaadin.data.Property#getValue()
		 */
		public Object getValue() {
			try {
				return this.prefItem.getTypedValue();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		/**
		 * @see com.vaadin.data.Property#setValue(java.lang.Object)
		 */
		public void setValue(Object newValue) throws ReadOnlyException,
				ConversionException {
			if (newValue == null)
				this.prefItem.setPreferenceValue(null);
			else {
				try {
					this.prefItem.setTypedValue(newValue);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		/**
		 * @see com.vaadin.data.Property#getType()
		 */
		public Class getType() {
			Class type_ = null;
			try {
				type_ = type.getJavaType();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return type_;
		}

		public void setType(Class newType) {
			try {
				this.type = basicTypeDAOService.provide(newType);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		/**
		 * @see com.vaadin.data.Property#isReadOnly()
		 */
		public boolean isReadOnly() {
			return this.readOnly;
		}

		/**
		 * @see com.vaadin.data.Property#setReadOnly(boolean)
		 */
		public void setReadOnly(boolean newStatus) {
			this.readOnly = newStatus;
		}

		/**
		 * @see java.lang.Object#toString()
		 */
		public String toString() {
			return "" + getValue();
		}
		
		public String getLabel() {
			return this.prefItem.getName();
		}

		public List<String> getOptions() {
			return options;
		}

		public void setOptions(List<String> options) {
			this.options = options;
		}
	}
}
