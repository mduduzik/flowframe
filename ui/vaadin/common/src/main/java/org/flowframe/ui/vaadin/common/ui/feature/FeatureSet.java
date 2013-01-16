package org.flowframe.ui.vaadin.common.ui.feature;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.terminal.Resource;
import com.vaadin.terminal.ThemeResource;

public class FeatureSet extends Feature {
	private static final long serialVersionUID = 253233879821431L;

	private Feature[] features = null;

	public FeatureSet() {
	}

	public FeatureSet(String code, String name, String description, Feature[] content) {
		super(code, name, description);
		this.features = content;
	}

	public Feature[] getFeatures() {
		return features;
	}

	public static HierarchicalContainer toContainer(Feature[] features) {
		assert (features != null) : "Feature[] features was null.";

		HierarchicalContainer container = new HierarchicalContainer();
		container.addContainerProperty(PROPERTY_NAME, String.class, "");
		container.addContainerProperty(PROPERTY_DESCRIPTION, String.class, "");
		container.addContainerProperty(PROPERTY_ICON, Resource.class, "");
		addFeatures(null, features, container);
		return container;
	}

	private static void addFeatures(FeatureSet parentFeatureSet, Feature[] features, HierarchicalContainer container) {
		for (Feature feature : features) {
			assert (feature != null) : "Feature must be non-null to be shown in a Tree.";
			// Add this feature to the container right off the bat
			Item featureItem = container.addItem(feature);
			// Only add a new feature if it hasn't been added yet
			if (featureItem != null) {
				// Set the name property to the right value
				Property nameProperty = featureItem.getItemProperty(PROPERTY_NAME);
				assert (nameProperty != null) : "The name property id + \"" + PROPERTY_NAME + "\" is invalid.";
				nameProperty.setValue(feature.getName());
				// Set the icon property to the right value
				if (feature.getIcon() != null) {
					Property iconProperty = featureItem.getItemProperty(PROPERTY_ICON);
					assert (iconProperty != null) : "The icon property id + \"" + PROPERTY_ICON + "\" is invalid.";
					iconProperty.setValue(new ThemeResource(feature.getIcon()));
				}
				// Set the description property to the right value
				Property descriptionProperty = featureItem.getItemProperty(PROPERTY_DESCRIPTION);
				assert (descriptionProperty != null) : "The description property id + \"" + PROPERTY_DESCRIPTION + "\" is invalid.";
				descriptionProperty.setValue(feature.getDescription());

				if (parentFeatureSet != null) {
					// Set the parent is it exists
					container.setParent(feature, parentFeatureSet);
				}
				if (feature instanceof FeatureSet) {
					// This feature is a parent, so add its children
					addFeatures((FeatureSet) feature, ((FeatureSet) feature).getFeatures(), container);
				}
			}
		}
	}
}
