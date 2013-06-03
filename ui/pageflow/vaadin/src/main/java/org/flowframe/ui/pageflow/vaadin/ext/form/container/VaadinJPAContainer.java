package org.flowframe.ui.pageflow.vaadin.ext.form.container;

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

import com.vaadin.addon.jpacontainer.EntityProvider;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.Item;

public class VaadinJPAContainer<T> extends JPAContainer<T> {
	private static final long serialVersionUID = 1L;

	public VaadinJPAContainer(Class<T> entityClass, EntityProvider<T> entityProvider) {
		super(entityClass);
		this.setEntityProvider(entityProvider);
	}

	@Override
	public Collection<Object> getItemIds() {
		// Filters the cached items
		HashSet<Object> filteredItemIds = new HashSet<Object>(super.getItemIds()),
				itemIds = new HashSet<Object>(filteredItemIds);
		Filter filter = getAppliedFiltersAsConjunction();
		Item item = null;
		for (Object itemId : itemIds) {
			if (itemId instanceof UUID) {
				item = getItem(itemId);
				if (item != null) {
					if (!filter.passesFilter(itemId, item)) {
						filteredItemIds.remove(itemId);
					}
				} else {
					filteredItemIds.remove(itemId);
				}
			}
		}
		return filteredItemIds;
	}
}
