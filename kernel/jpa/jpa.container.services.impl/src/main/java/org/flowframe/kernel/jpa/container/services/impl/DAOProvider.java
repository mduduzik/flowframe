package org.flowframe.kernel.jpa.container.services.impl;

import org.flowframe.kernel.jpa.container.services.IDAOProvider;

public class DAOProvider implements IDAOProvider {

	@Override
	public <T> T provideByDAOClass(Class<T> daoClass) {
		return null;
	}

}
