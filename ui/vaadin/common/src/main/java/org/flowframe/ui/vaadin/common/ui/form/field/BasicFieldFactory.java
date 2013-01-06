package org.flowframe.ui.vaadin.common.ui.form.field;

import java.util.Collection;

import org.flowframe.kernel.common.mdm.domain.BaseEntity;
import org.flowframe.kernel.common.utils.Validator;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.Select;
import com.vaadin.ui.TextField;

public class BasicFieldFactory extends DefaultFieldFactory {

	private Collection<?> visibleProperties;
	private Collection<?> editableProps;

	/**
	 * @param roProps
	 * 
	 */
	public BasicFieldFactory(Collection<?> visibleProperties, Collection<?> roProps) {
		this.visibleProperties = visibleProperties;
		this.editableProps = roProps;
	}

	/**
	 * Creates fields based on the property type.
	 * <p>
	 * The default field type is {@link TextField}. Other field types generated
	 * by this method:
	 * <p>
	 * <b>Boolean</b>: {@link CheckBox}.<br/>
	 * <b>Date</b>: {@link DateField}(resolution: day).<br/>
	 * <b>Item</b>: {@link Form}. <br/>
	 * <b>default field type</b>: {@link TextField}.
	 * <p>
	 * 
	 * @param type
	 *            the type of the property
	 * @return the most suitable generic {@link Field} for given type
	 */
	public static Field createFieldByPropertyType(Class<?> type) {
		// -- Entity
		if (BaseEntity.class.isAssignableFrom(type)) {
			// -- Let's try getting a container for this type
			// FIXME THIS IS OUT OF DATE
			// Container fieldContainer =
			// etcFactory.createEntityTypeContainer(type);
			Container fieldContainer = null;
			if (Validator.isNotNull(fieldContainer)) {
				Select select = new Select("", fieldContainer);
				select.setItemCaptionPropertyId("name");
				return select;
			} else
				return DefaultFieldFactory.createFieldByPropertyType(type);
		} else {
			Field field = DefaultFieldFactory.createFieldByPropertyType(type);
			if (TextField.class.isAssignableFrom(field.getClass())) {
				((TextField) field).setNullRepresentation("");
			}
			return field;
		}
	}

	public Field createField(Item item, Object propertyId, Component uiContext) {
		if (visibleProperties.contains(propertyId)) {
			Class<?> type = item.getItemProperty(propertyId).getType();
			Field field = BasicFieldFactory.createFieldByPropertyType(type);
			field.setCaption(super.createCaptionByPropertyId(propertyId));

			if (editableProps.contains(propertyId))
				field.setReadOnly(false);
			else
				field.setReadOnly(true);

			return field;
		} else
			return null;
	}
}
