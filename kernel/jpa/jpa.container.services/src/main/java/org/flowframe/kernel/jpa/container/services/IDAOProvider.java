package org.flowframe.kernel.jpa.container.services;

public interface IDAOProvider {
	public <T> T provideByDAOClass(Class<T> daoClass);
}
