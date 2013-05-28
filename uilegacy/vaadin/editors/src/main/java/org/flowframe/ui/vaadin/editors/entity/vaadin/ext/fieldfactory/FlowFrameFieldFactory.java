package org.flowframe.ui.vaadin.editors.entity.vaadin.ext.fieldfactory;

import com.vaadin.addon.jpacontainer.EntityContainer;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.fieldfactory.FieldFactory;
import com.vaadin.addon.jpacontainer.fieldfactory.SingleSelectTranslator;
import com.vaadin.data.Item;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextArea;

public class FlowFrameFieldFactory extends FieldFactory {
	private static final long serialVersionUID = -2958448405826099336L;

	public FlowFrameFieldFactory() {
		super();
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected Field createManyToOneField(EntityContainer containerForProperty, Object itemId, Object propertyId, Component uiContext) {
		Class<?> type = containerForProperty.getType(propertyId);
		JPAContainer container = createJPAContainerFor(containerForProperty, type, false);

		AbstractSelect nativeSelect = constructReferenceSelect(containerForProperty, itemId, propertyId, uiContext, type);
		nativeSelect.setMultiSelect(false);
		nativeSelect.setWidth("100%");
		nativeSelect.setItemCaptionPropertyId("code");
		nativeSelect.setCaption(DefaultFieldFactory.createCaptionByPropertyId(propertyId));
		nativeSelect.setItemCaptionMode(NativeSelect.ITEM_CAPTION_MODE_PROPERTY);
		nativeSelect.setContainerDataSource(container);
		nativeSelect.setPropertyDataSource(new SingleSelectTranslator(nativeSelect));
		return nativeSelect;
	}

	@Override
	public Field createField(Item item, Object propertyId, Component uiContext) {
//		if (propertyId.equals("content")) {
//			// JPAContainerItem jc = (JPAContainerItem) item;
//			// ClassMetadata cm =
//			// MetadataFactory.getInstance().getEntityClassMetadata(jc.getEntity().getClass());
//			// PropertyMetadata pm = cm.getProperty(propertyId.toString());
//			// if (pm != null) {
//			// Size size = pm.getAnnotation(Size.class);
//			// if (size != null) {
//			TextArea textArea = new TextArea();
//			textArea.setRows(3);
//			textArea.setColumns(0);
//			textArea.setSizeFull();
//			textArea.setCaption(DefaultFieldFactory.createCaptionByPropertyId(propertyId));
//			return textArea;
//			// }
//		}
		return super.createField(item, propertyId, uiContext);
	}
}
