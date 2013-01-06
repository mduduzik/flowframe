package org.flowframe.ui.vaadin.forms.impl.field;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.flowframe.kernel.common.mdm.domain.BaseEntity;
import org.flowframe.kernel.common.mdm.domain.geolocation.Address;
import org.flowframe.kernel.common.mdm.domain.metamodel.BasicAttribute;
import org.flowframe.kernel.common.mdm.domain.metamodel.EntityType;
import org.flowframe.kernel.common.mdm.domain.metamodel.EntityTypeAttribute;
import org.flowframe.kernel.common.mdm.domain.metamodel.SingularAttribute;
import org.flowframe.kernel.common.mdm.domain.organization.Contact;
import org.flowframe.kernel.jpa.container.services.IEntityContainerProvider;
import org.flowframe.kernel.metamodel.dao.services.IEntityTypeDAOService;
import org.flowframe.ui.vaadin.forms.impl.VaadinJPAFieldFactory;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.fieldfactory.SingleSelectTranslator;
import com.vaadin.data.Container;
import com.vaadin.data.Container.ItemSetChangeListener;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.VerticalLayout;

public class VaadinRelationField extends VerticalLayout implements Field, Container.Viewer, Container.ItemSetChangeNotifier {
	private static final long serialVersionUID = 1L;

	private AbstractSelect selector;
	private HorizontalLayout selectorPanel;
	private VaadinRelationFieldForm form;
	private VerticalLayout formPanel;
	private JPAContainer<?> entityContainer;
	private VaadinJPAFieldFactory fieldFactory;
	private IEntityContainerProvider provider;
	private Object subPropertyId;
	private IEntityTypeDAOService entityTypeDao;
	private Embedded newButton;
	private VaadinRelationFieldSelectorForm selectorForm;
	private VerticalLayout selectorFormPanel;
	private Object selectorPropertyId;
	private JPAContainer<?> subEntityContainer;
	private Map<Object, Object> newItemIdMap;

	public VaadinRelationField(Class<?> propertyType, Object subPropertyId, Object selectorPropertyId, IEntityContainerProvider provider, IEntityTypeDAOService entityTypeDao) {
		this.subPropertyId = subPropertyId;
		this.selectorPropertyId = selectorPropertyId;
		this.entityTypeDao = entityTypeDao;
		this.provider = provider;
		this.newItemIdMap = new HashMap<Object, Object>();

		this.entityContainer = (JPAContainer<?>) this.provider.createPersistenceContainer(propertyType);
		this.entityContainer.setWriteThrough(false);
		this.subEntityContainer = (JPAContainer<?>) this.provider.createPersistenceContainer(this.entityContainer.getType(subPropertyId));
		this.subEntityContainer.setWriteThrough(false);

		this.selector = new NativeSelect();
		this.selector.setMultiSelect(false);
		this.selector.setItemCaptionMode(NativeSelect.ITEM_CAPTION_MODE_PROPERTY);
		this.selector.setItemCaptionPropertyId("name");
		this.selector.setContainerDataSource(this.entityContainer);
		this.selector.setWriteThrough(false);
		this.selector.setImmediate(true);
		this.selector.setWidth("100%");
		this.selector.setPropertyDataSource(new SingleSelectTranslator(this.selector));
		this.selector.addListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(Property.ValueChangeEvent event) {
				updateForm(event.getProperty().getValue());
			}
		});

		this.newButton = new Embedded();
		this.newButton.setStyleName("conx-relation-field-new-button");
		this.newButton.setWidth("22px");
		this.newButton.setHeight("23px");
		this.newButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void click(ClickEvent event) {
				showNewItemSelector();
			}
		});

		this.selectorPanel = new HorizontalLayout();
		this.selectorPanel.setWidth("100%");
		this.selectorPanel.setSpacing(false);
		this.selectorPanel.addComponent(this.selector);
		this.selectorPanel.addComponent(this.newButton);
		this.selectorPanel.setExpandRatio(this.selector, 1.0f);

		this.fieldFactory = new VaadinJPAFieldFactory();
		this.fieldFactory.setEntityTypeDao(entityTypeDao);
		this.fieldFactory.setContainerProvider(provider);

		this.form = new VaadinRelationFieldForm();
		this.form.setFormFieldFactory(this.fieldFactory);

		this.formPanel = new VerticalLayout();
		this.formPanel.setWidth("100%");
		this.formPanel.addComponent(this.form);
		this.formPanel.setVisible(false);

		this.selectorForm = new VaadinRelationFieldSelectorForm();
		this.selectorForm.addListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("rawtypes")
			@Override
			public void valueChange(Property.ValueChangeEvent event) {
				try {
					Item item = VaadinRelationField.this.selectorForm.getContainerDataSource().getItem(event.getProperty().getValue());
					if (item instanceof EntityItem) {
						showNewItemEditor(((EntityItem) item).getEntity());
					}
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		});

		this.selectorFormPanel = new VerticalLayout();
		this.selectorFormPanel.setWidth("100%");
		this.selectorFormPanel.addComponent(this.selectorForm);
		this.selectorFormPanel.setVisible(false);

		this.addComponent(this.selectorPanel);
		this.addComponent(this.selectorFormPanel);
		this.addComponent(this.formPanel);
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		this.formPanel.setVisible(enabled);
	}

	private void showNewItemSelector() {
		Class<?> selectorPropertyType = this.entityContainer.getType(this.selectorPropertyId);
		if (selectorPropertyType != null) {
			JPAContainer<?> jpaContainer = (JPAContainer<?>) this.provider.createPersistenceContainer(selectorPropertyType);
			this.selectorForm.setContainerDataSource(jpaContainer);
			this.formPanel.setVisible(false);
			this.selectorFormPanel.setVisible(true);
			this.selectorPanel.setEnabled(false);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void showNewItemEditor(Object selectorValue) throws InstantiationException, IllegalAccessException, SecurityException, NoSuchFieldException, IllegalArgumentException,
			InvocationTargetException {
		if (selectorValue instanceof BaseEntity) {
			Object newItemBean = this.entityContainer.getEntityClass().newInstance();
			Object newSubItemBean = this.subEntityContainer.getEntityClass().newInstance();
			Method[] methods = newItemBean.getClass().getDeclaredMethods();
			Type[] paramTypes = null;
			for (Method method : methods) {
				paramTypes = method.getGenericParameterTypes();
				if (paramTypes.length == 1 && method.getName().toLowerCase().contains("set")) {
					if (paramTypes[0] instanceof Class) {
						if (((Class<?>) paramTypes[0]).isAssignableFrom(newSubItemBean.getClass())) {
							method.invoke(newItemBean, newSubItemBean);
						} else if (((Class<?>) paramTypes[0]).isAssignableFrom(selectorValue.getClass())) {
							method.invoke(newItemBean, selectorValue);
						}
					}
				}
			}
			if (newItemBean instanceof BaseEntity) {
				((BaseEntity) newItemBean).setName(((BaseEntity) selectorValue).getName());
				Item siblingItem = this.selector.getItem(VaadinRelationField.this.selector.getValue());
				if (siblingItem != null) {
					Property siblingOwnerIdProperty = siblingItem.getItemProperty("ownerEntityId");
					if (siblingOwnerIdProperty != null) {
						Object siblingOwnerId = siblingOwnerIdProperty.getValue();
						if (siblingOwnerId instanceof Long) {
							((BaseEntity) newItemBean).setOwnerEntityId((Long) siblingOwnerId);
						}
					}
				}
				if (newSubItemBean instanceof BaseEntity) {
				}
			}

			Object newSubItemId = ((JPAContainer) this.subEntityContainer).addEntity(newSubItemBean);
			Object newItemId = ((JPAContainer) this.entityContainer).addEntity(newItemBean);
			this.newItemIdMap.put(newItemId, newSubItemId);
			this.selector.setValue(newItemId);
			updateForm(newItemId);
			this.selectorPanel.setEnabled(true);
		}
	}

	private List<String> getAddressPropertyIds() {
		ArrayList<String> ids = new ArrayList<String>();
		ids.add("street1");
		ids.add("street2");
		ids.add("state");
		ids.add("email");
		ids.add("unloco");
		ids.add("zipCode");
		return ids;
	}

	private List<String> getContactPropertyIds() {
		ArrayList<String> ids = new ArrayList<String>();
		ids.add("firstName");
		ids.add("lastName");
		ids.add("officePhoneNumber");
		ids.add("cellPhoneNumber");
		ids.add("faxPhoneNumber");
		ids.add("email");
		return ids;
	}

	private List<String> getSubPropertyIds(Class<?> type) {
		if (Address.class.isAssignableFrom(type)) {
			return getAddressPropertyIds();
		} else if (Contact.class.isAssignableFrom(type)) {
			return getContactPropertyIds();
		} else {
			List<String> subPropertyIds = new ArrayList<String>();
			if (this.entityTypeDao != null) {
				try {
					EntityType entityType = this.entityTypeDao.provide(type);
					Set<EntityTypeAttribute> attributes = entityType.getDeclaredAttributes();
					for (EntityTypeAttribute attribute : attributes) {
						if (attribute.getAttribute() instanceof BasicAttribute || attribute.getAttribute() instanceof SingularAttribute) {
							subPropertyIds.add(attribute.getAttribute().getName());
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return subPropertyIds;
		}
	}

	private void updateForm(Object itemId) {
		Item newItem = entityContainer.getItem(itemId);
		if (newItem != null) {
			Property subProperty = newItem.getItemProperty(this.subPropertyId);
			if (subProperty != null) {
				if (this.subEntityContainer != null) {
					if (subProperty.getValue() instanceof BaseEntity) {
						Object formItemId = null;
						if (itemId instanceof UUID) {
							formItemId = this.newItemIdMap.get(itemId);
						} else if (itemId instanceof Long) {
							formItemId = ((BaseEntity) subProperty.getValue()).getId();
						}

						if (formItemId != null) {
							Item formItem = this.subEntityContainer.getItem(formItemId);
							if (formItem != null) {
								this.form.setItemDataSource(formItem, getSubPropertyIds(subProperty.getType()));
								this.form.setReadOnly(true);
								this.form.requestRepaint();
								this.formPanel.setVisible(true);
								this.selectorFormPanel.setVisible(false);
								return;
							}
						}
					}
				}
			}
		}
		this.formPanel.setVisible(false);
	}

	@Override
	public void focus() {
		super.focus();
	}

	@Override
	public boolean isInvalidCommitted() {
		return false;
	}

	@Override
	public void setInvalidCommitted(boolean isCommitted) {
	}

	@Override
	public void commit() throws SourceException, InvalidValueException {
		this.selector.commit();
	}

	@Override
	public void discard() throws SourceException {
		this.selector.discard();
	}

	@Override
	public boolean isWriteThrough() {
		return false;
	}

	@Override
	public void setWriteThrough(boolean writeThrough) throws SourceException, InvalidValueException {
	}

	@Override
	public boolean isReadThrough() {
		return false;
	}

	@Override
	public void setReadThrough(boolean readThrough) throws SourceException {
	}

	@Override
	public boolean isModified() {
		return this.selector.isModified();
	}

	@Override
	public void addValidator(Validator validator) {
		this.selector.addValidator(validator);
	}

	@Override
	public void removeValidator(Validator validator) {
		this.selector.removeValidator(validator);
	}

	@Override
	public Collection<Validator> getValidators() {
		return this.selector.getValidators();
	}

	@Override
	public boolean isValid() {
		return this.selector.isValid();
	}

	@Override
	public void validate() throws InvalidValueException {
		// this.form.validate();
		this.selector.validate();
	}

	@Override
	public boolean isInvalidAllowed() {
		return this.selector.isInvalidAllowed();
	}

	@Override
	public void setInvalidAllowed(boolean invalidValueAllowed) throws UnsupportedOperationException {
		this.selector.setInvalidAllowed(invalidValueAllowed);
	}

	@Override
	public Object getValue() {
		return this.selector.getValue();
	}

	@Override
	public void setValue(Object newValue) throws ReadOnlyException, ConversionException {
		this.selector.setValue(newValue);
	}

	@Override
	public Class<?> getType() {
		return this.selector.getType();
	}

	@Override
	public void addListener(ValueChangeListener listener) {
		this.selector.addListener(listener);
	}

	@Override
	public void removeListener(ValueChangeListener listener) {
		this.selector.removeListener(listener);
	}

	@Override
	public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
		this.selector.valueChange(event);
	}

	@Override
	public void setPropertyDataSource(Property newDataSource) {
		this.selector.setPropertyDataSource(newDataSource);
	}

	@Override
	public Property getPropertyDataSource() {
		return this.selector.getPropertyDataSource();
	}

	@Override
	public int getTabIndex() {
		return this.selector.getTabIndex();
	}

	@Override
	public void setTabIndex(int tabIndex) {
		this.selector.setTabIndex(tabIndex);
	}

	@Override
	public boolean isRequired() {
		return this.selector.isRequired();
	}

	@Override
	public void setRequired(boolean required) {
		this.selector.setRequired(required);
	}

	@Override
	public void setRequiredError(String requiredMessage) {
		this.selector.setRequiredError(requiredMessage);
	}

	@Override
	public String getRequiredError() {
		return this.selector.getRequiredError();
	}

	@Override
	public void setContainerDataSource(Container newDataSource) {
	}

	@Override
	public Container getContainerDataSource() {
		return this.selector.getContainerDataSource();
	}

	@Override
	public void addListener(ItemSetChangeListener listener) {
		this.selector.addListener(listener);
	}

	@Override
	public void removeListener(ItemSetChangeListener listener) {
		this.selector.addListener(listener);
	}

}
