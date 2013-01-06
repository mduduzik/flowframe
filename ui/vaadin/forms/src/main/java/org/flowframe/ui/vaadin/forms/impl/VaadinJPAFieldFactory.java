package org.flowframe.ui.vaadin.forms.impl;

import org.flowframe.kernel.common.mdm.domain.geolocation.Unloco;
import org.flowframe.kernel.jpa.container.services.IEntityContainerProvider;
import org.flowframe.kernel.metamodel.dao.services.IEntityTypeDAOService;
import org.flowframe.ui.vaadin.forms.impl.field.VaadinSummaryField;

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

public class VaadinJPAFieldFactory extends FieldFactory {
	private static final long serialVersionUID = -2958448405826099336L;

	private IEntityTypeDAOService entityTypeDao;
	private IEntityContainerProvider containerProvider;

	public VaadinJPAFieldFactory() {
		super();
	}

	// @Override
	// protected OneToOneForm createOneToOneField(EntityContainer<?>
	// jpacontainer, Object itemId, Object propertyId, Component uiContext) {
	// OneToOneForm oneToOneForm = new VaadinJPAOneToOneForm();
	// oneToOneForm.setBackReferenceId(jpacontainer.getEntityClass().getSimpleName().toLowerCase());
	// oneToOneForm.setCaption(DefaultFieldFactory.createCaptionByPropertyId(propertyId));
	// oneToOneForm.setFormFieldFactory(this);
	// if (uiContext instanceof Form) {
	// // write buffering is configured by Form after binding the data
	// // source. Yes, you may read the previous sentence again or verify
	// // this from the Vaadin code if you don't believe what you just
	// // read.
	// // As oneToOneForm creates the referenced type on demand if required
	// // the buffering state needs to be available when property is set
	// // (otherwise the original master entity will be modified once the
	// // form is opened).
	// Form f = (Form) uiContext;
	// oneToOneForm.setWriteThrough(f.isWriteThrough());
	// }
	// return oneToOneForm;
	// }

	private boolean isSummaryRequired(Class<?> type) {
		return (type.isAssignableFrom(Unloco.class));
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected Field createManyToOneField(EntityContainer containerForProperty, Object itemId, Object propertyId, Component uiContext) {
		Class<?> type = containerForProperty.getType(propertyId);
		if (isSummaryRequired(type)) {
			if (this.containerProvider != null && this.entityTypeDao != null) {
				VaadinSummaryField summaryField = new VaadinSummaryField(type, this.containerProvider, this.entityTypeDao);
				summaryField.setWidth("100%");
				summaryField.setCaption(DefaultFieldFactory.createCaptionByPropertyId(propertyId));

				return summaryField;
			}
		}

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
		return super.createField(item, propertyId, uiContext);
	}

	public IEntityTypeDAOService getEntityTypeDao() {
		return entityTypeDao;
	}

	public void setEntityTypeDao(IEntityTypeDAOService entityTypeDao) {
		this.entityTypeDao = entityTypeDao;
	}

	public IEntityContainerProvider getContainerProvider() {
		return containerProvider;
	}

	public void setContainerProvider(IEntityContainerProvider containerProvider) {
		this.containerProvider = containerProvider;
	}
}
