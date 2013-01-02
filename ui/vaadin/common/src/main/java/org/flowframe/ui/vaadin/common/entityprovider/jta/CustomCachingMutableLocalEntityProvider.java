package org.flowframe.ui.vaadin.common.entityprovider.jta;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.addon.jpacontainer.SortBy;
import com.vaadin.addon.jpacontainer.provider.CachingBatchableLocalEntityProvider;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.util.filter.Compare;

public class CustomCachingMutableLocalEntityProvider<T> extends CachingBatchableLocalEntityProvider<T> {

	protected static Logger logger = LoggerFactory.getLogger(CustomCachingMutableLocalEntityProvider.class);

	@SuppressWarnings("unused")
	private EntityManagerFactory entityManagerFactory;
	private UserTransaction userTransaction;

	private EntityManager entityManager;

	public CustomCachingMutableLocalEntityProvider(Class<T> entityClass) {
		super(entityClass);
	}

	public CustomCachingMutableLocalEntityProvider(Class<T> entityClass, EntityManagerFactory entityManagerFactory, UserTransaction userTransaction) {
		super(entityClass);
		this.entityManagerFactory = entityManagerFactory;
		this.entityManager = entityManagerFactory.createEntityManager();
		this.userTransaction = userTransaction;
	}
	
	@Override
	protected TypedQuery<Object> createFilteredQuery(List<String> arg0, Filter arg1, List<SortBy> arg2, boolean arg3) {
		return super.createFilteredQuery(arg0, arg1, arg2, arg3);
	}
	
	@Override
	public boolean containsEntity(Object entityId, Filter filter) {
		if (filter instanceof Compare) {
			if (((Compare) filter).getValue() instanceof UUID) {
				filter = null;
			}
		}
		return super.containsEntity(entityId, filter);
	}
	
	@Override
	public List<Object> getAllEntityIdentifiers(Filter filter, List<SortBy> sortBy) {
		if (filter instanceof Compare) {
			if (((Compare) filter).getValue() instanceof UUID) {
				filter = null;
			}
		}
		return super.getAllEntityIdentifiers(filter, sortBy);
	}

	@Override
	public boolean isEntitiesDetached() {
		return false;
	}

	@Override
	protected void runInTransaction(Runnable operation) {
		try {
			this.userTransaction.begin();
			this.entityManager.joinTransaction();
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
	
	@Override
	public int getEntityCount(Filter filter) {
		return super.getEntityCount(filter);
	}
	
	@Override
	protected TypedQuery<Object> createUnsortedFilteredQuery(List<String> fieldsToSelect, Filter filter) {
		return super.createUnsortedFilteredQuery(fieldsToSelect, filter);
	}

	@Override
	public EntityManager getEntityManager() {
		return this.entityManager;
	}
	
	@Override
	public void batchUpdate(com.vaadin.addon.jpacontainer.BatchableEntityProvider.BatchUpdateCallback<T> callback) throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		super.batchUpdate(callback);
	}
}
