package org.flowframe.ui.vaadin.common.entityprovider.jta;

import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.addon.jpacontainer.provider.MutableLocalEntityProvider;

public class CustomNonCachingMutableLocalEntityProvider<T> extends MutableLocalEntityProvider<T> {
	protected static Logger logger = LoggerFactory.getLogger(CustomNonCachingMutableLocalEntityProvider.class);
	
	private UserTransaction userTransaction;

	public CustomNonCachingMutableLocalEntityProvider(Class<T> entityClass) {
		super(entityClass);
	}
	
	public CustomNonCachingMutableLocalEntityProvider(Class<T> entityClass, EntityManagerFactory entityManagerFactory, UserTransaction userTransaction) {
		super(entityClass, entityManagerFactory.createEntityManager());
		this.userTransaction = userTransaction;
		this.setEntitiesDetached(false);
	}

	@Override
	protected void runInTransaction(Runnable operation) {
		try {
			this.userTransaction.begin();
			this.getEntityManager().joinTransaction();
			operation.run();
			this.userTransaction.commit();
		} catch (Exception e) {
			try {
				this.userTransaction.rollback();
			} catch (Exception e2) {
				logger.error("Rollback failed", e2);
			}
			throw new RuntimeException(e);
		}
	}
}
