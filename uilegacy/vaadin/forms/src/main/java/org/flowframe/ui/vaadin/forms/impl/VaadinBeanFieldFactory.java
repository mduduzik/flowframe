package org.flowframe.ui.vaadin.forms.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.Attribute.PersistentAttributeType;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.Type;

import org.flowframe.kernel.common.mdm.domain.geolocation.AddressTypeAddress;
import org.flowframe.kernel.common.mdm.domain.metamodel.EntityTypeAttribute;
import org.flowframe.kernel.common.mdm.domain.organization.ContactTypeContact;
import org.flowframe.kernel.jpa.container.services.IEntityContainerProvider;
import org.flowframe.kernel.metamodel.dao.services.IEntityTypeDAOService;
import org.flowframe.ui.vaadin.forms.impl.field.VaadinBeanOneToOneForm;
import org.flowframe.ui.vaadin.forms.impl.field.VaadinPlaceHolderField;
import org.flowframe.ui.vaadin.forms.impl.field.VaadinRelationField;

import com.vaadin.addon.jpacontainer.EntityContainer;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.addon.jpacontainer.fieldfactory.MultiSelectTranslator;
import com.vaadin.addon.jpacontainer.fieldfactory.OneToOneForm;
import com.vaadin.addon.jpacontainer.fieldfactory.SingleSelectTranslator;
import com.vaadin.addon.jpacontainer.metadata.PropertyKind;
import com.vaadin.addon.jpacontainer.util.EntityManagerPerRequestHelper;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;

@SuppressWarnings("serial")
public class VaadinBeanFieldFactory extends DefaultFieldFactory {

	private HashMap<Class<?>, String[]> propertyOrders;
	private EntityManagerPerRequestHelper entityManagerPerRequestHelper;
	private HashMap<Class<?>, Class<? extends AbstractSelect>> multiselectTypes;
	private HashMap<Class<?>, Class<? extends AbstractSelect>> singleselectTypes;
	@SuppressWarnings("rawtypes")
	private BeanItemContainer beanContainer;
	private IEntityContainerProvider containerProvider;
	private IEntityTypeDAOService entityTypeDao;
	private Map<Class<?>, org.flowframe.kernel.common.mdm.domain.metamodel.EntityType> entityTypeCache;
	

	/**
	 * Creates a new JPAContainerFieldFactory. For referece/collection types
	 * ComboBox or multiselects are created by default.
	 */
	public VaadinBeanFieldFactory() {
		this.entityTypeCache = new HashMap<Class<?>, org.flowframe.kernel.common.mdm.domain.metamodel.EntityType>();
	}

	/**
	 * Creates a new JPAContainerFieldFactory. For referece/collection types
	 * ComboBox or multiselects are created by default.
	 * 
	 * @param beanContainer
	 *            container that bean items belong to
	 * @param containerProvider
	 *            provider for the jpa-backed fields
	 * @param entityTypeDao
	 *            entity type dao
	 */
	@SuppressWarnings("rawtypes")
	public VaadinBeanFieldFactory(BeanItemContainer beanContainer, IEntityContainerProvider containerProvider, IEntityTypeDAOService entityTypeDao) {
		this.beanContainer = beanContainer;
		this.containerProvider = containerProvider;
		this.entityTypeDao = entityTypeDao;
		this.entityTypeCache = new HashMap<Class<?>, org.flowframe.kernel.common.mdm.domain.metamodel.EntityType>();
	}

	/**
	 * Creates a new JPAContainerFieldFactory. For referece/collection types
	 * ComboBox or multiselects are created by default.
	 * 
	 * @param emprHelper
	 *            the {@link EntityManagerPerRequestHelper} to use for updating
	 *            the entity manager in internally generated JPAContainers for
	 *            each request.
	 */
	public VaadinBeanFieldFactory(EntityManagerPerRequestHelper emprHelper) {
		setEntityManagerPerRequestHelper(emprHelper);
	}

	private Field createBasicField(Item item, Object propertyId, Component uiContext) {
		Class<?> type = item.getItemProperty(propertyId).getType();
		if (Enum.class.isAssignableFrom(type)) {
			return createEnumSelect(item, type, propertyId, uiContext);
		} else if (Date.class.isAssignableFrom(type)) {
			return createDateSelect(item, type, propertyId, uiContext);
		}
		Field field = super.createField(item, propertyId, uiContext);
		if (field instanceof TextField) {
			((TextField) field).setNullRepresentation("");
		}
		return field;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Field createField(Item item, Object propertyId, Component uiContext) {
		if (this.beanContainer != null && this.containerProvider != null && this.entityTypeDao != null) {
			if (item instanceof BeanItem) {
				BeanItem jpaitem = (BeanItem) item;
				Property property = jpaitem.getItemProperty("id");
				PropertyKind propertyKind = getPropertyKind(beanContainer, propertyId);
				if (property != null && propertyKind != null) {
					// BeanItemContainer container = jpaitem.get
					Field field = createJPAContainerBackedField(jpaitem.getBean(), propertyId, beanContainer, propertyKind, uiContext);
					if (field != null) {
						return field;
					}
				}
			}
		}
		return configureBasicFields(createBasicField(item, propertyId, uiContext));
	}

	/**
	 * This method can be used to configure field generated by the
	 * DefaultFieldFactory. By default it sets null representation of textfields
	 * to empty string instead of 'null'.
	 * 
	 * @param field
	 * @return
	 */
	protected Field configureBasicFields(Field field) {
		if (field instanceof AbstractTextField) {
			((AbstractTextField) field).setNullRepresentation("");
		}
		return field;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Field createField(Container container, Object itemId, Object propertyId, Component uiContext) {
		if (this.containerProvider != null && this.entityTypeDao != null) {
			if (container instanceof BeanItemContainer) {
				BeanItemContainer jpacontainer = (BeanItemContainer) container;
				this.beanContainer = jpacontainer;
				PropertyKind propertyKind = getPropertyKind(beanContainer, propertyId);
				if (propertyKind != null) {
					Field field = createJPAContainerBackedField(itemId, propertyId, jpacontainer, propertyKind, uiContext);
					if (field != null) {
						return field;
					}
				}
			}
		}
		return configureBasicFields(super.createField(container, itemId, propertyId, uiContext));
	}

	private Field createEnumSelect(Item item, Class<?> type, Object propertyId, Component uiContext) {
		Object[] elements = type.getEnumConstants();
		if (elements == null) {
			return null;
		}
		
		IndexedContainer container = new IndexedContainer();
		container.addContainerProperty("name", String.class, "");
		Item enumItem = null;
		for (Object element : elements) {
			enumItem = container.addItem(element);
			enumItem.getItemProperty("name").setValue(element.toString());
		}

		AbstractSelect nativeSelect = new NativeSelect();
		nativeSelect.setMultiSelect(false);
		nativeSelect.setCaption(DefaultFieldFactory.createCaptionByPropertyId(propertyId));
		nativeSelect.setItemCaptionMode(NativeSelect.ITEM_CAPTION_MODE_PROPERTY);
		nativeSelect.setItemCaptionPropertyId("name");
		nativeSelect.setContainerDataSource(container);
		nativeSelect.setWidth("100%");
		nativeSelect.setPropertyDataSource(item.getItemProperty(propertyId));
		return nativeSelect;
	}
	
	private Field createDateSelect(Item item, Class<?> type, Object propertyId, Component uiContext) {
		final DateField df = new DateField();
        df.setResolution(DateField.RESOLUTION_MIN);
        return df;
	}

	@SuppressWarnings("rawtypes")
	private PropertyKind getPropertyKind(BeanItemContainer container, Object propertyId) {
		try {
			org.flowframe.kernel.common.mdm.domain.metamodel.EntityType entityType = this.entityTypeCache.get(container.getBeanType());
			if (entityType == null) {
				entityType = entityTypeDao.getByClass(container.getBeanType());
				this.entityTypeCache.put(container.getBeanType(), entityType);
			}
			EntityTypeAttribute attribute = entityType.getAttribute(propertyId.toString());
			if (attribute != null) {
				if (attribute.getAttribute() instanceof org.flowframe.kernel.common.mdm.domain.metamodel.PluralAttribute) {
					PersistentAttributeType attributeType = ((org.flowframe.kernel.common.mdm.domain.metamodel.PluralAttribute) attribute.getAttribute()).getAttributeType();
					switch (attributeType) {
					case ONE_TO_ONE:
						return PropertyKind.ONE_TO_ONE;
					case ONE_TO_MANY:
						return PropertyKind.ONE_TO_MANY;
					case MANY_TO_ONE:
						return PropertyKind.MANY_TO_ONE;
					case MANY_TO_MANY:
						return PropertyKind.MANY_TO_MANY;
					default:
						return PropertyKind.SIMPLE;
					}
				} else if (attribute.getAttribute() instanceof org.flowframe.kernel.common.mdm.domain.metamodel.SingularAttribute) {
					PersistentAttributeType attributeType = ((org.flowframe.kernel.common.mdm.domain.metamodel.SingularAttribute) attribute.getAttribute()).getAttributeType();
					switch (attributeType) {
					case ONE_TO_ONE:
						return PropertyKind.ONE_TO_ONE;
					case ONE_TO_MANY:
						return PropertyKind.ONE_TO_MANY;
					case MANY_TO_ONE:
						return PropertyKind.MANY_TO_ONE;
					case MANY_TO_MANY:
						return PropertyKind.MANY_TO_MANY;
					default:
						return PropertyKind.SIMPLE;
					}
				} else if (attribute.getAttribute() instanceof org.flowframe.kernel.common.mdm.domain.metamodel.BasicAttribute) {
					return PropertyKind.SIMPLE;
				}
			}
		} catch (Exception e) {
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	private Field createJPAContainerBackedField(Object itemId, Object propertyId, BeanItemContainer jpacontainer, PropertyKind propertyKind, Component uiContext) {
		Field field = null;
		switch (propertyKind) {
		case MANY_TO_ONE:
			field = createReferenceSelect(jpacontainer, itemId, propertyId, uiContext);
			break;
		case ONE_TO_ONE:
			// FIXME FIX ONE TO ONE FIELD
			// field = createOneToOneField(jpacontainer, itemId, propertyId,
			// uiContext);
			field = new VaadinPlaceHolderField();
			break;
		case ONE_TO_MANY:
			field = createMasterDetailEditor(jpacontainer, itemId, propertyId, uiContext);
			break;
		case MANY_TO_MANY:
			field = createCollectionSelect(jpacontainer, itemId, propertyId, uiContext);
			break;
		default:
			break;
		}
		return field;
	}

	@SuppressWarnings("rawtypes")
	protected OneToOneForm createOneToOneField(BeanItemContainer jpacontainer, Object itemId, Object propertyId, Component uiContext) {
		VaadinBeanOneToOneForm oneToOneForm = new VaadinBeanOneToOneForm(jpacontainer.getItem(itemId), containerProvider);
		oneToOneForm.setBackReferenceId(jpacontainer.getBeanType().getSimpleName().toLowerCase());
		oneToOneForm.setCaption(DefaultFieldFactory.createCaptionByPropertyId(propertyId));
		oneToOneForm.setFormFieldFactory(this);
		if (uiContext instanceof Form) {
			// write buffering is configure by Form after binding the data
			// source. Yes, you may read the previous sentence again or verify
			// this from the Vaadin code if you don't believe what you just
			// read.
			// As oneToOneForm creates the referenced type on demand if required
			// the buffering state needs to be available when proeprty is set
			// (otherwise the original master entity will be modified once the
			// form is opened).
			Form f = (Form) uiContext;
			oneToOneForm.setWriteThrough(f.isWriteThrough());
		}
		return oneToOneForm;
	}

	@SuppressWarnings({ "rawtypes" })
	protected Field createCollectionSelect(BeanItemContainer containerForProperty, Object itemId, Object propertyId, Component uiContext) {
		/*
		 * Detect what kind of reference type we have
		 */
		Class masterEntityClass = containerForProperty.getBeanType();
		Class referencedType = detectReferencedType(this.containerProvider, propertyId, masterEntityClass);
		final JPAContainer container = createJPAContainerFor(referencedType, false);
		final AbstractSelect select = constructCollectionSelect(containerForProperty, itemId, propertyId, uiContext, referencedType);
		select.setCaption(DefaultFieldFactory.createCaptionByPropertyId(propertyId));
		select.setContainerDataSource(container);
		// many to many, selectable from table listing all existing pojos
		select.setPropertyDataSource(new MultiSelectTranslator(select));
		select.setMultiSelect(true);
		if (select instanceof Table) {
			Table t = (Table) select;
			t.setSelectable(true);
			Object[] visibleProperties = getVisibleProperties(referencedType);
			if (visibleProperties == null) {
				List<Object> asList = new ArrayList<Object>(Arrays.asList(t.getVisibleColumns()));
				asList.remove("id");
				// TODO this should be the true "back reference" field from the
				// opposite direction, now we expect convention
				final String backReferencePropertyId = masterEntityClass.getSimpleName().toLowerCase() + "s";
				asList.remove(backReferencePropertyId);
				visibleProperties = asList.toArray();
			}
			t.setVisibleColumns(visibleProperties);
		} else {
			select.setItemCaptionMode(AbstractSelect.ITEM_CAPTION_MODE_ITEM);
		}

		return select;
	}

	@SuppressWarnings({ "rawtypes" })
	private Field createMasterDetailEditor(BeanItemContainer containerForProperty, Object itemId, Object propertyId, Component uiContext) {
		// return new MasterDetailEditor(this, containerForProperty, itemId,
		// propertyId, uiContext);
		// TODO fix master detail editor
		// return new VaadinSelectDetail(this, containerForProperty, itemId,
		// propertyId, uiContext);
		return new VaadinPlaceHolderField();
	}

	/**
	 * Detects the type entities in "collection types" (oneToMany, ManyToMany).
	 * 
	 * @param propertyId
	 * @param masterEntityClass
	 * @return the type of entities in collection type
	 */
	@SuppressWarnings("rawtypes")
	public Class detectReferencedType(IEntityContainerProvider containerProvider, Object propertyId, Class masterEntityClass) {
		Class referencedType = null;
		Metamodel metamodel = containerProvider.getEmf().getMetamodel();
		Set<EntityType<?>> entities = metamodel.getEntities();
		for (EntityType<?> entityType : entities) {
			Class<?> javaType = entityType.getJavaType();
			if (javaType == masterEntityClass) {
				Attribute<?, ?> attribute = entityType.getAttribute(propertyId.toString());
				PluralAttribute pAttribute = (PluralAttribute) attribute;
				Type elementType = pAttribute.getElementType();
				referencedType = elementType.getJavaType();
				break;
			}
		}
		return referencedType;
	}

	protected EntityManagerFactory getEntityManagerFactory(EntityContainer<?> containerForProperty) {
		return containerForProperty.getEntityProvider().getEntityManager().getEntityManagerFactory();
	}

	private boolean isRelationEntity(Class<?> type) {
		return type.isAssignableFrom(AddressTypeAddress.class) || type.isAssignableFrom(ContactTypeContact.class);
	}

	private String getRelationEntitySubEntityId(Class<?> type) {
		if (type.isAssignableFrom(AddressTypeAddress.class)) {
			return "address";
		} else if (type.isAssignableFrom(ContactTypeContact.class)) {
			return "contact";
		}
		return null;
	}

	private String getRelationEntitySelectorEntityId(Class<?> type) {
		if (type.isAssignableFrom(AddressTypeAddress.class)) {
			return "type";
		} else if (type.isAssignableFrom(ContactTypeContact.class)) {
			return "type";
		}
		return null;
	}

	/**
	 * Creates a field for simple reference (ManyToOne)
	 * 
	 * @param containerForProperty
	 * @param propertyId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected Field createReferenceSelect(BeanItemContainer containerForProperty, Object itemId, Object propertyId, Component uiContext) {
		Class<?> type = containerForProperty.getType(propertyId);
		if (isRelationEntity(type)) {
			String subEntityId = getRelationEntitySubEntityId(type), selectorEntityId = getRelationEntitySelectorEntityId(type);
			if (subEntityId != null && selectorEntityId != null && this.getContainerProvider() != null && this.entityTypeDao != null) {
				VaadinRelationField selectFormField = new VaadinRelationField(type, subEntityId, selectorEntityId, this.getContainerProvider(), this.entityTypeDao);
				return selectFormField;
			}
		}

		JPAContainer container = createJPAContainerFor(type, false);
		AbstractSelect nativeSelect = constructReferenceSelect(itemId, propertyId, uiContext, type);
		nativeSelect.setMultiSelect(false);
		nativeSelect.setCaption(DefaultFieldFactory.createCaptionByPropertyId(propertyId));
		nativeSelect.setItemCaptionMode(NativeSelect.ITEM_CAPTION_MODE_PROPERTY);
		nativeSelect.setItemCaptionPropertyId("name");
		nativeSelect.setContainerDataSource(container);
		nativeSelect.setWidth("100%");
		nativeSelect.setPropertyDataSource(new SingleSelectTranslator(nativeSelect));
		return nativeSelect;
	}

	protected AbstractSelect constructReferenceSelect(Object itemId, Object propertyId, Component uiContext, Class<?> type) {
		if (singleselectTypes != null) {
			Class<? extends AbstractSelect> class1 = singleselectTypes.get(type);
			if (class1 != null) {
				try {
					return class1.newInstance();
				} catch (Exception e) {
					Logger.getLogger(getClass().getName()).warning("Could not create select of type " + class1.getName());
				}
			}
		}
		return new NativeSelect();
	}

	@SuppressWarnings("rawtypes")
	protected AbstractSelect constructCollectionSelect(BeanItemContainer containerForProperty, Object itemId, Object propertyId, Component uiContext, Class<?> type) {
		if (multiselectTypes != null) {
			Class<? extends AbstractSelect> class1 = multiselectTypes.get(type);
			try {
				return class1.newInstance();
			} catch (Exception e) {
				Logger.getLogger(getClass().getName()).warning("Could not create select of type " + class1.getName());
			}
		}
		return new Table();
	}

	protected EntityManager getEntityManagerFor(EntityContainer<?> containerForProperty) {
		return containerForProperty.getEntityProvider().getEntityManager();
	}

	public JPAContainer<?> createJPAContainerFor(Class<?> type, boolean buffered) {
		JPAContainer<?> container = null;
		EntityManager em = this.containerProvider.getEmf().createEntityManager();
		if (buffered) {
			container = JPAContainerFactory.makeBatchable(type, em);
		} else {
			container = JPAContainerFactory.make(type, em);
		}
		// Set the lazy loading delegate to the same as the parent.
		// container.getEntityProvider().setLazyLoadingDelegate(containerForProperty.getEntityProvider().getLazyLoadingDelegate());
		if (entityManagerPerRequestHelper != null) {
			entityManagerPerRequestHelper.addContainer(container);
		}
		return container;
	}

	/**
	 * Configures visible properties and their order for fields created for
	 * reference/collection types referencing to given entity type. This order
	 * is for example used by Table's created for OneToMany or ManyToMany
	 * reference types.
	 * 
	 * @param containerType
	 *            the entity type for which the visible properties will be set
	 * @param propertyIdentifiers
	 *            the identifiers in wished order to be displayed
	 */
	public void setVisibleProperties(Class<?> containerType, String... propertyIdentifiers) {
		if (propertyOrders == null) {
			propertyOrders = new HashMap<Class<?>, String[]>();
		}
		propertyOrders.put(containerType, propertyIdentifiers);
	}

	public void setMultiSelectType(Class<?> referenceType, Class<? extends AbstractSelect> selectType) {
		if (multiselectTypes == null) {
			multiselectTypes = new HashMap<Class<?>, Class<? extends AbstractSelect>>();
		}
		multiselectTypes.put(referenceType, selectType);
	}

	public void setSingleSelectType(Class<?> referenceType, Class<? extends AbstractSelect> selectType) {
		if (singleselectTypes == null) {
			singleselectTypes = new HashMap<Class<?>, Class<? extends AbstractSelect>>();
		}
		singleselectTypes.put(referenceType, selectType);
	}

	/**
	 * Returns customized visible properties (and their order) for given entity
	 * type.
	 * 
	 * @param containerType
	 * @return property identifiers that are configured to be displayed
	 */
	public String[] getVisibleProperties(Class<?> containerType) {
		if (propertyOrders != null) {
			return propertyOrders.get(containerType);
		}
		return null;
	}

	/**
	 * @return The {@link EntityManagerPerRequestHelper} that is used for
	 *         updating the entity managers for all JPAContainers generated by
	 *         this field factory.
	 */
	public EntityManagerPerRequestHelper getEntityManagerPerRequestHelper() {
		return entityManagerPerRequestHelper;
	}

	/**
	 * Sets the {@link EntityManagerPerRequestHelper} that is used for updating
	 * the entity manager of JPAContainers generated by this field factory.
	 * 
	 * @param entityManagerPerRequestHelper
	 */
	public void setEntityManagerPerRequestHelper(EntityManagerPerRequestHelper entityManagerPerRequestHelper) {
		this.entityManagerPerRequestHelper = entityManagerPerRequestHelper;
	}

	@SuppressWarnings("rawtypes")
	public BeanItemContainer getContainer() {
		return beanContainer;
	}

	@SuppressWarnings("rawtypes")
	public void setContainer(BeanItemContainer container) {
		this.beanContainer = container;
	}

	public IEntityContainerProvider getContainerProvider() {
		return containerProvider;
	}

	public void setContainerProvider(IEntityContainerProvider containerProvider) {
		this.containerProvider = containerProvider;
	}

	public IEntityTypeDAOService getEntityTypeDao() {
		return entityTypeDao;
	}

	public void setEntityTypeDao(IEntityTypeDAOService entityTypeDao) {
		this.entityTypeDao = entityTypeDao;
	}
}
