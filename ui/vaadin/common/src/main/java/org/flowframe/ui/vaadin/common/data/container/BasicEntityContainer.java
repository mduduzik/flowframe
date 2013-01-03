package org.flowframe.ui.vaadin.common.data.container;

import javax.persistence.EntityManager;

import org.flowframe.kernel.common.mdm.domain.BaseEntity;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.provider.CachingLocalEntityProvider;

public class BasicEntityContainer<T extends BaseEntity> extends JPAContainer<T> {

	public BasicEntityContainer(Class<T> entityClass, EntityManager entityManager) {
		super(entityClass);
		setEntityProvider(new CachingLocalEntityProvider<T>(
				entityClass,
				entityManager));		
	}
}
