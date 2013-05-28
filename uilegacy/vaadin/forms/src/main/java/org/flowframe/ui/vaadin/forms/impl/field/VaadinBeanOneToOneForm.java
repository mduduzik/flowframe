package org.flowframe.ui.vaadin.forms.impl.field;

import org.flowframe.kernel.jpa.container.services.IEntityContainerProvider;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.fieldfactory.OneToOneForm;
import com.vaadin.data.Item;
import com.vaadin.data.Property;

public class VaadinBeanOneToOneForm extends OneToOneForm {
	private static final long serialVersionUID = 1L;
	
	private Object masterEntity;
	private Property property;
	private IEntityContainerProvider provider;
	private Object createdInstance;

	private Item editedEntityItem;

	public VaadinBeanOneToOneForm(Object itemEntity, IEntityContainerProvider provider) {
		this.masterEntity = itemEntity;
		this.provider = provider;
		setWriteThrough(false);
	}
	
	@Override
    public void setPropertyDataSource(Property newDataSource) {
        property = newDataSource;
//        masterEntity = property.getItem().getEntity();
        // TODO, should use item from generated JPAContainer instead of
        // beanitem??
        if (newDataSource.getValue() == null) {
            try {
                createdInstance = newDataSource.getType().newInstance();
                editedEntityItem = createItemForInstance(createdInstance);
            } catch (InstantiationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            editedEntityItem = getItemForInstance(newDataSource.getValue());
            setItemDataSource(editedEntityItem);
        }
        // super.setPropertyDataSource(newDataSource);
    }
	
	private Item getItemForInstance(Object createdInstance) {
        @SuppressWarnings("rawtypes")
		JPAContainer jpaContainer = getContainer(createdInstance);
        @SuppressWarnings({ "unchecked", "rawtypes" })
		EntityItem item = jpaContainer.getItem(jpaContainer.getEntityProvider().getIdentifier(createdInstance));
        return item;
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
    private Item createItemForInstance(Object createdInstance) {
        JPAContainer jpaContainer = getContainer(createdInstance);
//        jpaContainer.setWriteThrough(false);
        Object itemId = jpaContainer.addEntity(createdInstance);
        return jpaContainer.getItem(itemId);
    }
	
	private JPAContainer<?> getContainer(Object createdInstance) {
		JPAContainer<?> jpaContainer = (JPAContainer<?>) provider.createPersistenceContainer(createdInstance.getClass());
        return jpaContainer;
    }

	public Object getMasterEntity() {
		return masterEntity;
	}

	public void setMasterEntity(Object masterEntity) {
		this.masterEntity = masterEntity;
	}

	public Property getProperty() {
		return property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}

	public IEntityContainerProvider getProvider() {
		return provider;
	}

	public void setProvider(IEntityContainerProvider provider) {
		this.provider = provider;
	}
}
