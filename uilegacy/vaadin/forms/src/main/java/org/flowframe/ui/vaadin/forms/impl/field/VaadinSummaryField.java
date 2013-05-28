package org.flowframe.ui.vaadin.forms.impl.field;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.flowframe.kernel.common.mdm.domain.geolocation.Unloco;
import org.flowframe.kernel.common.mdm.domain.metamodel.BasicAttribute;
import org.flowframe.kernel.common.mdm.domain.metamodel.EntityType;
import org.flowframe.kernel.common.mdm.domain.metamodel.EntityTypeAttribute;
import org.flowframe.kernel.common.mdm.domain.metamodel.SingularAttribute;
import org.flowframe.kernel.jpa.container.services.IEntityContainerProvider;
import org.flowframe.kernel.metamodel.dao.services.IEntityTypeDAOService;
import org.flowframe.ui.vaadin.forms.impl.VaadinJPAFieldFactory;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.fieldfactory.SingleSelectTranslator;
import com.vaadin.data.Container;
import com.vaadin.data.Container.ItemSetChangeListener;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Field;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.VerticalLayout;

public class VaadinSummaryField extends VerticalLayout implements Field, Container.Viewer, Container.ItemSetChangeNotifier {
	private static final long serialVersionUID = 1L;

	private AbstractSelect selector;
	private VaadinSummaryFieldForm form;
	private VerticalLayout formPanel;
	private JPAContainer<?> entityContainer;
	private VaadinJPAFieldFactory fieldFactory;
	private IEntityContainerProvider provider;
	private IEntityTypeDAOService entityTypeDao;
	private List<String> propertyIds;

	public VaadinSummaryField(Class<?> propertyType, IEntityContainerProvider provider, IEntityTypeDAOService entityTypeDao) {
		this.provider = provider;
		this.entityTypeDao = entityTypeDao;

		this.entityContainer = (JPAContainer<?>) this.provider.createPersistenceContainer(propertyType);
		this.entityContainer.setWriteThrough(false);

		this.selector = new NativeSelect();
		this.selector.setMultiSelect(false);
		this.selector.setItemCaptionMode(NativeSelect.ITEM_CAPTION_MODE_PROPERTY);
		this.selector.setItemCaptionPropertyId("code");
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

		this.fieldFactory = new VaadinJPAFieldFactory();
		this.fieldFactory.setEntityTypeDao(entityTypeDao);
		this.fieldFactory.setContainerProvider(provider);

		this.form = new VaadinSummaryFieldForm();
		this.form.setFormFieldFactory(this.fieldFactory);

		this.formPanel = new VerticalLayout();
		this.formPanel.setWidth("100%");
		this.formPanel.addComponent(this.form);
		this.formPanel.setVisible(false);

		this.addComponent(this.selector);
		this.addComponent(this.formPanel);
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		this.formPanel.setVisible(enabled);
	}

	private List<String> getPropertyIds() {
		if (this.propertyIds == null) {
			if (this.entityContainer.getEntityClass().isAssignableFrom(Unloco.class)) {
				List<String> subPropertyIds = new ArrayList<String>();
				subPropertyIds.add("country");
				subPropertyIds.add("countryState");
				subPropertyIds.add("portCity");
				this.propertyIds = subPropertyIds;
			} else if (this.entityTypeDao != null) {
				List<String> subPropertyIds = new ArrayList<String>();
				try {
					EntityType entityType = this.entityTypeDao.provide(this.entityContainer.getEntityClass());
					Set<EntityTypeAttribute> attributes = entityType.getDeclaredAttributes();
					for (EntityTypeAttribute attribute : attributes) {
						if (attribute.getEntityType() == entityType) {
							if (attribute.getAttribute() instanceof BasicAttribute || attribute.getAttribute() instanceof SingularAttribute) {
								subPropertyIds.add(attribute.getAttribute().getName());
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				this.propertyIds = subPropertyIds;
			}
		}
		return this.propertyIds;
	}

	private void updateForm(Object itemId) {
		if (itemId != null) {
			Item newItem = entityContainer.getItem(itemId);
			if (newItem != null) {
				this.form.setItemDataSource(newItem, getPropertyIds());
				this.form.requestRepaint();
				this.formPanel.setVisible(true);
			} else {
				this.formPanel.setVisible(false);
			}
		} else {
			this.formPanel.setVisible(false);
		}
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
	
	@Override
	public void setReadOnly(boolean readOnly) {
		this.selector.setReadOnly(readOnly);
	}
	
	@Override
	public boolean isReadOnly() {
		return this.selector.isReadOnly();
	}
}
