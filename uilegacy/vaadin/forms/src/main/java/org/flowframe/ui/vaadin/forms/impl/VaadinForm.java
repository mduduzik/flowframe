package org.flowframe.ui.vaadin.forms.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.flowframe.ds.domain.DataSourceField;
import org.flowframe.ds.domain.DataSourceFieldDependenceExpression;
import org.flowframe.ds.domain.DataSourceFieldValidator;
import org.flowframe.kernel.common.utils.StringUtil;
import org.flowframe.kernel.jpa.container.services.IEntityContainerProvider;
import org.flowframe.kernel.metamodel.dao.services.IEntityTypeDAOService;
import org.flowframe.ui.component.domain.form.FormComponent;
import org.flowframe.ui.vaadin.forms.impl.field.VaadinPlaceHolderField;
import org.flowframe.ui.vaadin.forms.listeners.IFormChangeListener;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.Container;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Container.ItemSetChangeEvent;
import com.vaadin.data.Container.ItemSetChangeListener;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Validator;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.MethodProperty.MethodException;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.terminal.CompositeErrorMessage;
import com.vaadin.terminal.ErrorMessage;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TextField;

public class VaadinForm extends Form {
	private static final long serialVersionUID = 1985860905705409799L;
	// Vaadin form requires a component model to function properly
	private FormComponent componentModel;
	// Internal vaadin variables that had to be employed here
	// due to overriding setItemDataSource
	private int gridlayoutCursorX = -1;
	private int gridlayoutCursorY = -1;
	// The item data source of the form, it has a getter and
	// a setter
	private Item itemDatasource;
	private Collection<?> visibleItemProperties;
	// Listener fields for form field change events
	private Set<IFormChangeListener> formChangeListeners;
	private ValueChangeListener fieldValueChangeListener;
	// Fields map caches all fields created by the field factory and
	// strores them by property id
	private Map<Object, Field> fields;
	private HashMap<Field, Object> propertyIds;
	private List<Field> fieldOrdinalCache;
	private HashMap<Field, DataSourceField> dataSourceFieldCache;
	// Expression engine specific fields
	private StandardEvaluationContext evaluationContext;
	private ExpressionParser expressionParser;

	protected void fireFormChangedEvent() {
		for (IFormChangeListener listener : this.formChangeListeners) {
			listener.onFormChanged();
		}
	}

	public void addListener(IFormChangeListener listener) {
		this.formChangeListeners.add(listener);
	}

	public void removeListener(IFormChangeListener listener) {
		this.formChangeListeners.remove(listener);
	}

	public VaadinForm() {
		this.fields = new HashMap<Object, Field>();
		this.propertyIds = new HashMap<Field, Object>();
		this.fieldOrdinalCache = new LinkedList<Field>();

		this.formChangeListeners = new HashSet<IFormChangeListener>();
		this.fieldValueChangeListener = new ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
				fireFormChangedEvent();
			}
		};
	}

	public VaadinForm(Layout formLayout, FormComponent componentModel) {
		super(formLayout);

		this.fields = new HashMap<Object, Field>();
		this.propertyIds = new HashMap<Field, Object>();
		this.fieldOrdinalCache = new LinkedList<Field>();
		this.dataSourceFieldCache = new HashMap<Field, DataSourceField>();

		this.componentModel = componentModel;
		this.formChangeListeners = new HashSet<IFormChangeListener>();
		this.fieldValueChangeListener = new ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
				fireFormChangedEvent();
			}
		};
	}

	public VaadinForm(Layout formLayout, FormComponent componentModel, FormFieldFactory fieldFactory) {
		super(formLayout, fieldFactory);

		this.fields = new HashMap<Object, Field>();
		this.propertyIds = new HashMap<Field, Object>();
		this.fieldOrdinalCache = new LinkedList<Field>();
		this.dataSourceFieldCache = new HashMap<Field, DataSourceField>();

		this.componentModel = componentModel;
		this.formChangeListeners = new HashSet<IFormChangeListener>();
		this.fieldValueChangeListener = new ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
				fireFormChangedEvent();
			}
		};
	}

	public void setTitle(String title) {
		throw new UnsupportedOperationException("setTitle() is unsupported by this form");
	}

	protected String getPropertyId(DataSourceField dsField) {
		if (dsField.getJPAPath() != null) {
			return dsField.getJPAPath();
		} else {
			return dsField.getName();
		}
	}

	private DataSourceField getDataSourceField(Object propertyId) {
		Set<DataSourceField> dsFields = this.componentModel.getDataSource().getDSFields();
		for (DataSourceField dsField : dsFields) {
			if (getPropertyId(dsField).equals(propertyId)) {
				return dsField;
			}
		}

		return null;
	}

	private Collection<Object> getChildDataSourceFieldPropertyIds(DataSourceField dsField) {
		HashSet<Object> childListenerFieldPropertyIds = new HashSet<Object>();
		Set<DataSourceField> childDataSourceFields = dsField.getChildDataSourceFields();
		for (DataSourceField childDsField : childDataSourceFields) {
			childListenerFieldPropertyIds.add(getPropertyId(childDsField));
		}

		return childListenerFieldPropertyIds;
	}

	@Override
	public void setItemDataSource(Item newDataSource, Collection<?> propertyIds) {
		if (this.componentModel != null) {
			if (getLayout() instanceof GridLayout) {
				GridLayout gl = (GridLayout) getLayout();
				if (gridlayoutCursorX == -1) {
					// first setItemDataSource, remember initial cursor
					gridlayoutCursorX = gl.getCursorX();
					gridlayoutCursorY = gl.getCursorY();
				} else {
					// restore initial cursor
					gl.setCursorX(gridlayoutCursorX);
					gl.setCursorY(gridlayoutCursorY);
				}
			}

			// Removes all fields first from the form
			removeAllProperties();

			// Sets the datasource
			itemDatasource = newDataSource;

			// If the new datasource is null, just set null datasource
			if (itemDatasource == null) {
				requestRepaint();
				return;
			}

			// Reset field caches
			this.fields = new HashMap<Object, Field>();
			this.propertyIds = new HashMap<Field, Object>();
			this.dataSourceFieldCache = new HashMap<Field, DataSourceField>();
			this.fieldOrdinalCache = new LinkedList<Field>();

			HashMap<Object, Field> addedPropertyIds = new HashMap<Object, Field>();
			// Binds all the properties to fields
			for (final Iterator<?> i = propertyIds.iterator(); i.hasNext();) {
				final Object id = i.next();
				final Property property = itemDatasource.getItemProperty(id);
				if (id != null && property != null) {
					initField(itemDatasource, id, addedPropertyIds);
				}
			}

			// Finally add all the fields to the form layout IN ORDER
			Object propertyId = null;
			for (Field field : this.fieldOrdinalCache) {
				propertyId = this.propertyIds.get(field);
				if (propertyId != null) {
					VaadinFormFieldAugmenter.augment(field, this.fieldValueChangeListener);
					addField(propertyId, field);
				}
			}
		}
	}

	public void setItemDataSource(Item newDataSource, Collection<?> propertyIds, IEntityTypeDAOService entityTypeDao) {
		this.setItemDataSource(newDataSource, propertyIds);
	}

	public void setItemDataSource(Item newDataSource, Collection<?> propertyIds, IEntityTypeDAOService entityTypeDao,
			BeanItemContainer<?> itemParentContainer, IEntityContainerProvider containerProvider) {
		VaadinBeanFieldFactory formFieldFactory = new VaadinBeanFieldFactory();
		formFieldFactory.setEntityTypeDao(entityTypeDao);
		formFieldFactory.setContainer(itemParentContainer);
		formFieldFactory.setContainerProvider(containerProvider);

		this.setFormFieldFactory(formFieldFactory);
		this.setItemDataSource(newDataSource, propertyIds, entityTypeDao);
	}

	public boolean validateForm() throws Exception {
		throw new UnsupportedOperationException("This form has no support for validating this way. Use validate().");
	}

	public boolean saveForm() throws Exception {
		throw new UnsupportedOperationException("This form has no support for commiting this way. Use commit().");
	}

	public void resetForm() throws Exception {
		throw new UnsupportedOperationException("This form has no support for validating this way. Use discard().");
	}

	private ValueChangeListener buildDependenceListener(Field childListenerField) {
		if (childListenerField instanceof Container.Viewer) {
			return new ValueDependenceListener(((Container.Viewer) childListenerField).getContainerDataSource(), childListenerField);
		}

		return null;
	}

	private ItemSetChangeListener buildRefreshListener(Field listenerField) {
		if (listenerField instanceof Container.Viewer) {
			return new ContainerRefreshListener(listenerField);
		}

		return null;
	}

	private void applyRefreshListener(Field field, ItemSetChangeListener listener) {
		if (field instanceof Container.ItemSetChangeNotifier) {
			((Container.ItemSetChangeNotifier) field).addListener(listener);
		}
	}

	private void applyDependenceListener(final Field field, final ValueChangeListener dependenceListener) {
		if (field != null && dependenceListener != null) {
			if (field instanceof TextField) {
				((TextField) field).addListener(new TextChangeListener() {
					private static final long serialVersionUID = 1L;

					@Override
					public void textChange(TextChangeEvent event) {
						dependenceListener.valueChange(new Property.ValueChangeEvent() {
							private static final long serialVersionUID = 1L;

							@Override
							public Property getProperty() {
								return field.getPropertyDataSource();
							}
						});
					}
				});
			} else {
				field.addListener(dependenceListener);
			}
		}
	}

	private DependenceExpressionUpdateListener buildDependenceExpresssionListener(DataSourceFieldDependenceExpression dependenceExpression) {
		return new DependenceExpressionUpdateListener(dependenceExpression);
	}

	private Field initField(Item itemDataSource, Object propertyId, Map<Object, Field> addedPropertyIds) {
		if (!addedPropertyIds.containsKey(propertyId) && itemDataSource.getItemPropertyIds().contains(propertyId)) {
			final Property p = itemDatasource.getItemProperty(propertyId);
			if (p != null) {
				final Field f = getFormFieldFactory().createField(itemDatasource, propertyId, this);
				DataSourceField dsField = getDataSourceField(propertyId);

				if (f != null && dsField != null) {
					Collection<Object> childListenerFieldPropertyIds = getChildDataSourceFieldPropertyIds(dsField);
					for (final Object childListenerFieldPropertyId : childListenerFieldPropertyIds) {
						Field childListenerField = addedPropertyIds.get(childListenerFieldPropertyId);
						if (childListenerField == null) {
							childListenerField = initField(itemDataSource, childListenerFieldPropertyId, addedPropertyIds);
						}
						childListenerField.setEnabled(false);
						applyDependenceListener(f, buildDependenceListener(childListenerField));
						applyRefreshListener(childListenerField, buildRefreshListener(childListenerField));
					}

					if (dsField.getValidators() != null) {
						for (DataSourceFieldValidator validator : dsField.getValidators()) {
							f.addValidator(new ExpressionBasedValidator(validator.getValidationExpression(), validator.getErrorMessage()));
						}
					}

					if (dsField.getDependenceExpressions() != null) {
						for (DataSourceFieldDependenceExpression expression : dsField.getDependenceExpressions()) {
							ValueChangeListener dependenceListener = buildDependenceExpresssionListener(expression);

							Object fieldPropertyId;
							Field field;
							for (DataSourceField dependeeDsField : expression.getDependees()) {
								fieldPropertyId = getPropertyId(dependeeDsField);
								field = getField(fieldPropertyId);

								if (field == null) {
									field = initField(itemDataSource, fieldPropertyId, addedPropertyIds);
								}

								if (field != null) {
									field.addListener(dependenceListener);
								}
							}
						}
					}

					try {
						// Field captions should default to ds field title
						if (dsField.getTitle() != null) {
							f.setCaption(dsField.getTitle());
						} else {
							// The title is null so we have to generate one
							f.setCaption(createCaption(propertyId.toString()));
						}
						// Add property data source to field
						addedPropertyIds.put(propertyId, f);
						bindPropertyToField(propertyId, p, f);
						if (isNestedParentPropertyNull(f)) {
							throw new MethodException(p, "Nested Parent Properties for Property " + propertyId + " were null.");
						}
						clearErrorMessage(f);
						// Fields used to be added to the form at this point,
						// but now we will add them to the ordinal cache first
						this.fields.put(propertyId, f);
						this.propertyIds.put(f, propertyId);
						this.dataSourceFieldCache.put(f, dsField);
						// Using the ds field's ordinal, put the Field in the
						// field ordinal cache
						DataSourceField comparator;
						int i;
						for (i = 0; i < this.fieldOrdinalCache.size(); i++) {
							comparator = this.dataSourceFieldCache.get(this.fieldOrdinalCache.get(i));
							if (comparator != null) {
								if (comparator.getOrdinal() > dsField.getOrdinal()) {
									this.fieldOrdinalCache.add(i, f);
									break;
								}
							}
						}
						
						if (i == this.fieldOrdinalCache.size()) {
							// Means we have to this field to the end of the list
							this.fieldOrdinalCache.add(f);
						}
						
						return f;
					} catch (Exception e) {
						e.printStackTrace();
						Field placeHolderField = new VaadinPlaceHolderField();
						attachField(propertyId, placeHolderField);
						requestRepaint();
						return placeHolderField;
					}
				}
			}
		}
		return null;
	}

	private void clearErrorMessage(final Field f) {
		if (f instanceof AbstractField) {
			((AbstractField) f).setComponentError(null);
		}
	}

	private boolean isNestedParentPropertyNull(final Field f) {
		if (f instanceof AbstractComponent) {
			ErrorMessage error = ((AbstractComponent) f).getErrorMessage();
			if (error != null) {
				if (error instanceof CompositeErrorMessage) {
					Iterator<ErrorMessage> iterator = ((CompositeErrorMessage) error).iterator();
					ErrorMessage errorMessage = null;
					while (iterator.hasNext()) {
						errorMessage = iterator.next();
						if (errorMessage instanceof SourceException) {
							Throwable[] causes = ((SourceException) errorMessage).getCauses();
							for (Throwable cause : causes) {
								if (cause instanceof MethodException) {
									if (cause.getCause() instanceof NullPointerException) {
										return true;
									}
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	@Override
	public Collection<?> getVisibleItemProperties() {
		return visibleItemProperties;
	}

	@Override
	public Item getItemDataSource() {
		return this.itemDatasource;
	}

	@Override
	public void setVisibleItemProperties(Collection<?> visibleProperties) {
		visibleItemProperties = visibleProperties;
		Object value = getValue();
		if (value == null) {
			value = itemDatasource;
		}
		setFormDataSource(value, getVisibleItemProperties());
	}

	protected class ContainerRefreshListener implements ItemSetChangeListener {
		private static final long serialVersionUID = 7364596329095103693L;

		private Field childField;

		public ContainerRefreshListener(Field childField) {
			this.childField = childField;
		}

		@Override
		public void containerItemSetChange(ItemSetChangeEvent event) {
			if (childField instanceof Container.Viewer) {
				Container container = ((Container.Viewer) this.childField).getContainerDataSource();
				Collection<?> ids = container.getItemIds();
				if (ids.size() == 0) {
					childField.setValue(null);
					childField.setEnabled(false);
				} else {
					childField.setEnabled(true);
					if (childField instanceof Field) {
						((Field) childField).setValue(ids.iterator().next());
					}
				}
			}
		}
	}

	private class DependenceExpressionUpdateListener implements ValueChangeListener {
		private static final long serialVersionUID = 3740705199870793080L;

		private DataSourceFieldDependenceExpression dependenceExpression;

		public DependenceExpressionUpdateListener(DataSourceFieldDependenceExpression dependenceExpression) {
			this.dependenceExpression = dependenceExpression;
		}

		@Override
		public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
			try {
				Object value = provideExpressionParser().parseExpression(this.dependenceExpression.getExpression()).getValue(
						provideEvaluationContext(), this.dependenceExpression.getEvaluationType());
				Field field = getField(getPropertyId(this.dependenceExpression.getField()));
				if (field != null && value != null) {
					field.setValue(String.valueOf(value));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	protected class ValueDependenceListener implements ValueChangeListener {
		private static final long serialVersionUID = -7197186226450185330L;

		private Container childFieldContainer;
		private Field childField;
		private Filter parentFilter;
		private Object entityId;

		public ValueDependenceListener(Container childFieldContainer, Field childField) {
			this.childFieldContainer = childFieldContainer;
			this.childField = childField;
		}

		@Override
		public void valueChange(final Property.ValueChangeEvent event) {
			if (childFieldContainer instanceof JPAContainer) {
				if (event != null && event.getProperty() != null && event.getProperty().getValue() != null) {
					if (parentFilter != null) {
						((JPAContainer<?>) this.childFieldContainer).removeContainerFilter(this.parentFilter);
					}

					this.entityId = event.getProperty().getValue();
					if (this.entityId instanceof Long) {
						this.parentFilter = new com.vaadin.data.util.filter.Compare.Equal("ownerEntityId", this.entityId);
						((JPAContainer<?>) this.childFieldContainer).addContainerFilter(this.parentFilter);
						((JPAContainer<?>) this.childFieldContainer).applyFilters();
					} else {
						if (this.childField instanceof Component) {
							this.childField.setEnabled(false);
						}
					}
				} else {
					if (this.childField instanceof Component) {
						this.childField.setEnabled(false);
					}
				}
			}
		}
	}

	public FormComponent getComponentModel() {
		return componentModel;
	}

	public void setComponentModel(FormComponent componentModel) {
		this.componentModel = componentModel;
	}

	/**
	 * Gets a field by property id in this form.
	 * 
	 * @return the field is it exists or null otherwise
	 */
	public Field getField(Object propertyId) {
		return this.fields.get(propertyId);
	}
	
	/**
	 * Generates a human-readable caption from a property id.
	 * 
	 * @param propertyId
	 * @return caption
	 */
	private String createCaption(String propertyId) {
		String simpleName = propertyId, title = "";
		String[] sections = simpleName.split("(?=\\p{Upper})");
		boolean isFirst = true;
		for (String section : sections) {
			if (!isFirst) {
				if (section.length() > 1) {
					title += " ";
				}
			}
			title += section;
			if (!"".equals(section)) {
				isFirst = false;
			}
		}
		return title;
	}

	/**
	 * Creates or gets the evaluation context for this form. The evaluation
	 * context is used to evaluate expressions in the spring expression
	 * language.
	 * 
	 * @return this form's evaluation context
	 */
	private StandardEvaluationContext provideEvaluationContext() {
		if (this.evaluationContext == null) {
			this.evaluationContext = new StandardEvaluationContext();
			this.evaluationContext.setVariable("form", this);
			try {
				this.evaluationContext.registerFunction("isNumber", StringUtil.class.getMethod("isNumber", String.class));
				this.evaluationContext.registerFunction("toNumber", StringUtil.class.getMethod("toNumber", String.class));
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}

		return this.evaluationContext;
	}

	/**
	 * Creates or gets the expression parser necessary to parse validation
	 * expressions.
	 * 
	 * @return the expression parser
	 */
	private ExpressionParser provideExpressionParser() {
		// FIXME Wire in the IExpressionEngine
		if (this.expressionParser == null) {
			this.expressionParser = new SpelExpressionParser();
		}

		return this.expressionParser;
	}

	/**
	 * Evaluates a field validation expression string into a boolean value.
	 * 
	 * @param validationExpression
	 *            the string expression for validation
	 * @return true if the expression holds for this form
	 */
	private boolean evaluateValidationExpression(String validationExpression) {
		return provideExpressionParser().parseExpression(validationExpression).getValue(provideEvaluationContext(), Boolean.class);
	}

	/**
	 * A VaadinForm specific validator that takes a validation expression and
	 * validates using the IExpressionEngine.
	 * 
	 * @author Sandile
	 */
	private class ExpressionBasedValidator implements Validator {
		private static final long serialVersionUID = 2613741136955843017L;

		private String expression, errorMessage;

		public ExpressionBasedValidator(String expression, String errorMessage) {
			this.expression = expression;
			this.errorMessage = errorMessage;
		}

		@Override
		public void validate(Object value) throws InvalidValueException {
			if (!isValid(value)) {
				throw new InvalidValueException(errorMessage);
			}
		}

		@Override
		public boolean isValid(Object value) {
			try {
				return evaluateValidationExpression(this.expression);
			} catch (Exception e) {
				e.printStackTrace();
			} catch (Error e) {
				e.printStackTrace();
			}
			
			return false;
		}
	}
}
