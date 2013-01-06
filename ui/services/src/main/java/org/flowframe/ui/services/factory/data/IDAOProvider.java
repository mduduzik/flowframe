package org.flowframe.ui.services.factory.data;

public interface IDAOProvider {
	public <T> T provideByDAOClass(Class<T> daoClass);
}
