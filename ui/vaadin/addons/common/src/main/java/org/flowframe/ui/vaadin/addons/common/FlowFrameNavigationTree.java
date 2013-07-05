package org.flowframe.ui.vaadin.addons.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.ui.TreeTable;

public class FlowFrameNavigationTree extends TreeTable implements ValueChangeListener {
	private static final long serialVersionUID = -4744167266095653799L;

	private static final String DEFAULT_PARENT_ICON_URL = "icons/conx/conx-navigation-warehouse-icon.png";
	private static final String DEFAULT_CHILD_ICON_URL = "icons/conx/conx-navigation-warehouse-icon.png";
	private static final String DEFAULT_NAME_PROPERTY_ID = "name";

	private ArrayList<FlowFrameNavigationTreeItem> itemList;
	private HashMap<Integer, Object> idMap;
	private ValueChangeListener navigationListener;
	private Object namePropertyId;

	public FlowFrameNavigationTree() {
		itemList = new ArrayList<FlowFrameNavigationTreeItem>();
		idMap = new HashMap<Integer, Object>();
		namePropertyId = DEFAULT_NAME_PROPERTY_ID;

		setSizeFull();
		setSelectable(true);
		setNullSelectionAllowed(false);
		setImmediate(true);
		addStyleName("conx-tree-nav");
		addListener(new FlowFrameNavigationTreeValueChangeListener());
		setColumnHeaderMode(FlowFrameNavigationTree.COLUMN_HEADER_MODE_HIDDEN);
		addContainerProperty("name", FlowFrameNavigationTreeItem.class, "");
	}

	public FlowFrameNavigationTreeItem addConXNavigationTreeItem(Object value, String caption, String iconUrl) {
		FlowFrameNavigationTreeItem newItem = new FlowFrameNavigationTreeItem(caption, iconUrl, this, itemList.size());
		itemList.add(newItem);
		addItem(new Object[] { newItem }, newItem.getId());
		setChildrenAllowed(newItem.getId(), false);
		return newItem;
	}

	public FlowFrameNavigationTreeItem addConXNavigationTreeItem(Object value, String caption, String iconUrl, int parentId) {
		FlowFrameNavigationTreeItem newItem = addConXNavigationTreeItem(value, caption, iconUrl);
		if (itemList.get(parentId) != null) {
			setChildrenAllowed(parentId, true);
			setParent(newItem.getId(), parentId);
			setCollapsed(parentId, false);
		}
		return newItem;
	}

	public void setNavigationContainer(HierarchicalContainer container, Object parentId) {
		addChildren(container, parentId, null);
	}

	public void setNavigationContainer(JPAContainer<?> container, Object parentId) {
		addChildren(container, parentId, null);
	}

	private void addChildren(HierarchicalContainer container, Object parentId, Integer parentNavItemId) {
		Collection<?> ids = null;
		
        Object[] properties = new Object[1];
        properties[0] = namePropertyId;

        boolean[] ascending = new boolean[1];
        ascending[0] = true;

        container.sort(properties, ascending);
		
		if (parentId == null) {
			ids = container.rootItemIds();
		} else {
			ids = container.getChildren(parentId);
		}

		Item item = null;
		Object caption = null;
		Property captionProperty = null;
		for (Object id : ids) {
			item = container.getItem(id);
			if (item != null) {
				captionProperty = item.getItemProperty(namePropertyId);
				if (captionProperty != null) {
					caption = captionProperty.getValue();
					if (caption instanceof String) {
						FlowFrameNavigationTreeItem treeItem = null;
						if (container.hasChildren(id)) {
							if (parentNavItemId == null) {
								treeItem = addConXNavigationTreeItem(id, (String) caption, DEFAULT_PARENT_ICON_URL);
								idMap.put(treeItem.getId(), id);
							} else {
								treeItem = addConXNavigationTreeItem(id, (String) caption, DEFAULT_PARENT_ICON_URL, parentNavItemId);
								idMap.put(treeItem.getId(), id);
							}
							addChildren(container, id, treeItem.getId());
						} else {
							if (parentNavItemId == null) {
								treeItem = addConXNavigationTreeItem(id, (String) caption, DEFAULT_CHILD_ICON_URL);
								idMap.put(treeItem.getId(), id);
							} else {
								treeItem = addConXNavigationTreeItem(id, (String) caption, DEFAULT_CHILD_ICON_URL, parentNavItemId);
								idMap.put(treeItem.getId(), id);
							}
						}
					}
				}
			}
		}
	}

	private void addChildren(JPAContainer<?> container, Object parentId, Integer parentNavItemId) {
		Collection<?> ids = null;
		if (parentId == null) {
			ids = container.rootItemIds();
		} else {
			ids = container.getChildren(parentId);
		}

		EntityItem<?> item = null;
		Object caption = null;
		Property captionProperty = null;
		for (Object id : ids) {
			item = container.getItem(id);
			if (item != null) {
				captionProperty = item.getItemProperty(namePropertyId);
				if (captionProperty != null) {
					caption = captionProperty.getValue();
					if (caption instanceof String) {
						FlowFrameNavigationTreeItem treeItem = null;
						if (container.hasChildren(id)) {
							if (parentNavItemId == null) {
								treeItem = addConXNavigationTreeItem(id, (String) caption, DEFAULT_PARENT_ICON_URL);
								idMap.put(treeItem.getId(), item.getItemId());
							} else {
								treeItem = addConXNavigationTreeItem(id, (String) caption, DEFAULT_PARENT_ICON_URL, parentNavItemId);
								idMap.put(treeItem.getId(), item.getItemId());
							}
							addChildren(container, id, treeItem.getId());
						} else {
							if (parentNavItemId == null) {
								treeItem = addConXNavigationTreeItem(id, (String) caption, DEFAULT_CHILD_ICON_URL);
								idMap.put(treeItem.getId(), item.getItemId());
							} else {
								treeItem = addConXNavigationTreeItem(id, (String) caption, DEFAULT_CHILD_ICON_URL, parentNavItemId);
								idMap.put(treeItem.getId(), item.getItemId());
							}
						}
					}
				}
			}
		}
	}

	public ValueChangeListener getNavigationListener() {
		return navigationListener;
	}

	public void setNavigationListener(ValueChangeListener navigationListener) {
		this.navigationListener = navigationListener;
	}

	private class FlowFrameNavigationTreeValueChangeListener implements ValueChangeListener {
		private static final long serialVersionUID = -8010282811L;

		public void valueChange(final com.vaadin.data.Property.ValueChangeEvent event) {
			if (event.getProperty().getValue() != null) {
				if (navigationListener != null) {
					navigationListener.valueChange(new Property.ValueChangeEvent() {
						private static final long serialVersionUID = 982374932L;

						public Property getProperty() {
							return new Property() {
								private static final long serialVersionUID = -103948478L;

								public Object getValue() {
									Integer id = (Integer) event.getProperty().getValue();
									if (id != null) {
										return idMap.get(id);
									} else {
										return null;
									}
								}

								public void setValue(Object newValue)
										throws ReadOnlyException,
										ConversionException {
								}

								public Class<?> getType() {
									return null;
								}

								public boolean isReadOnly() {
									return false;
								}

								public void setReadOnly(boolean newStatus) {
								}

							};
						}
					});
				}
			}
		}
	}

	public Object getNamePropertyId() {
		return namePropertyId;
	}

	public void setNamePropertyId(Object namePropertyId) {
		this.namePropertyId = namePropertyId;
	}
}
