package org.flowframe.bpm.jbpm.ui.pageflow.vaadin.ext.form.container;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

import javax.persistence.Entity;

import org.flowframe.kernel.common.mdm.dao.services.IBaseEntityDAOService;
import org.flowframe.kernel.common.mdm.domain.BaseEntity;
import org.flowframe.kernel.common.utils.StringUtil;
import org.flowframe.kernel.jpa.container.services.IDAOProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.util.BeanItemContainer;

public class VaadinBeanItemContainer<T> extends BeanItemContainer<T> {
	private static final long serialVersionUID = -6242091934532649159L;
	private static final long BASIC_ENTITY_FLAG = -1;
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	private IDAOProvider daoProvider;
	private boolean persistentAutoInstantiation = false;

	public VaadinBeanItemContainer(Class<? super T> type) throws IllegalArgumentException {
		super(type);
	}

	@Override
	public com.vaadin.data.util.BeanItem<T> addBean(T bean) {
		try {
			furnishNewBean(bean);
			return super.addBean(bean);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private boolean isGetter(Method method, String fieldName, Class<?> fieldType) {
		if (method.getName().toLowerCase().contains(fieldName.toLowerCase())) {
			if (method.getName().toLowerCase().contains("get")) {
				if (method.getParameterTypes().length == 0) {
					if (fieldType.isAssignableFrom(method.getReturnType())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean isSetter(Method method, String fieldName, Class<?> fieldType) {
		if (method.getName().toLowerCase().contains(fieldName.toLowerCase())) {
			if (method.getName().toLowerCase().contains("set")) {
				Class<?>[] parameterTypes = method.getParameterTypes();
				if (parameterTypes.length == 1) {
					if (fieldType.isAssignableFrom(parameterTypes[0])) {
						if (method.getReturnType().equals(Void.TYPE)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	private boolean isEntityType(Class<?> type) {
		return type.getAnnotation(Entity.class) != null;
	}

	private Object initializeRequiredField(String fieldName, Class<?> fieldType, Object bean) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException,
			InstantiationException {
		Method[] methods = bean.getClass().getMethods();
		Method getterMethod = null, setterMethod = null;
		// Find the getter and the setter for this field
		for (Method method : methods) {
			if (isGetter(method, fieldName, fieldType)) {
				getterMethod = method;
			} else if (isSetter(method, fieldName, fieldType)) {
				setterMethod = method;
			}
			// Stop looping if we find both the getter and setter early
			if (getterMethod != null && setterMethod != null) {
				break;
			}
		}
		// Give this field a value if it does not have one already.
		// Otherwise, return its existing value.
		if (getterMethod != null && setterMethod != null) {
			Object value = getterMethod.invoke(bean);
			if (value == null) {
				// The field is null right now, lets give it a value if it is an
				// entity
				Class<?> type = getterMethod.getReturnType();
				if (isEntityType(type)) {
					Object newInstance = type.newInstance();
					if (this.daoProvider != null && this.persistentAutoInstantiation && newInstance instanceof BaseEntity) {
						newInstance = this.daoProvider.provideByDAOClass(IBaseEntityDAOService.class).add((BaseEntity) newInstance);
					}
					setterMethod.invoke(bean, newInstance);
					return newInstance;
				} else {
					return BASIC_ENTITY_FLAG;
				}
			} else {
				return value;
			}
		}
		// Return null if there was a problem invoking the setter
		return null;
	}

	private Field getDeclaredOrInherittedField(Class<?> type, String nextAttributeName) throws SecurityException, NoSuchFieldException {
		Field result = null;
		Class<?> currentType = type;
		while (true) {
			try {
				result = currentType.getDeclaredField(nextAttributeName);
			} catch (NoSuchFieldException e) {
				// Kill the exception that indicates that the field did not
				// exist
			}
			if (result == null && currentType.getSuperclass() != null) {
				currentType = currentType.getSuperclass();
			} else {
				if (result == null) {
					throw new NoSuchFieldException();
				}
				return result;
			}
		}
	}

	private boolean initializeRequiredNestedField(String path, Object bean) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException,
			SecurityException, NoSuchFieldException {
		String nextAttributeName = null;
		Class<?> nextAttributeType = null;
		boolean isLast = false;
		// Treats the propertyId path like a stack, and it pops the top-level
		// field name of the stack
		if (path.contains(".")) {
			nextAttributeName = StringUtil.extractFirst(path, ".");
			path = StringUtil.replace(path, nextAttributeName + ".", "").trim();
			isLast = false;
		} else {
			nextAttributeName = path.trim();
			isLast = true;
		}

		nextAttributeType = getDeclaredOrInherittedField(bean.getClass(), nextAttributeName).getType();

		if (isLast) {
			return true;
		} else {
			// Get the value of the newly initialized field. If its null, we
			// cannot continue so we have to return false. Otherwise, we can
			// continue going even deeper.
			Object nextAttributeValue = initializeRequiredField(nextAttributeName, nextAttributeType, bean);
			if (nextAttributeValue != null) {
				if (!nextAttributeValue.equals(BASIC_ENTITY_FLAG)) {
					return initializeRequiredNestedField(path, nextAttributeValue);
				} else {
					// Basic attribute wasn't the last item in the path
					return false;
				}
			} else {
				return false;
			}
		}

	}

	private void furnishNewBean(T bean) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException, SecurityException, NoSuchFieldException {
		Collection<String> propertyIds = this.getContainerPropertyIds();
		for (String propertyId : propertyIds) {
			if (propertyId.contains(".")) {
				if (!initializeRequiredNestedField(propertyId, bean)) {
					this.logger.error("Failed to initialize property [" + propertyId + "].");
				}
			}
		}
	}
	
	public void setDAOProvider(IDAOProvider daoProvider) {
		this.daoProvider = daoProvider;
	}
	
	public void setPersistentAutoInstantiation(boolean persistentAutoInstantiation) {
		this.persistentAutoInstantiation = persistentAutoInstantiation;
	}
}
