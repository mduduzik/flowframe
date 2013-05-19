package org.flowframe.kernel.jpa.container.services;

import javax.persistence.EntityManagerFactory;

public interface IEntityContainerProvider {
	public Object createNonCachingPersistenceContainer(Class<?> entityClass);
	public Object createPersistenceContainer(Class<?> entityClass);
	public Object createBeanContainer(Class<?> entityClass);
	public EntityManagerFactory getEmf();
}
