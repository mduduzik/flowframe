package org.flowframe.erp.app.contractmanagement.dao.services.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.flowframe.erp.app.contractmanagement.dao.services.ISubscriptionDAOService;
import org.flowframe.erp.app.contractmanagement.domain.Subscription;
import org.flowframe.erp.app.contractmanagement.domain.SubscriptionPlan;
import org.flowframe.kernel.common.utils.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Repository
public class SubscriptionDAOImpl implements ISubscriptionDAOService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());	
    /**
     * Spring will inject a managed JPA {@link EntityManager} into this field.
     */
    @PersistenceContext
    private EntityManager em;	
    
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public Subscription get(long id) {
		return em.getReference(Subscription.class, id);
	}    

	@Override
	public List<Subscription> getAll() {
		return em.createQuery("select o from org.flowframe.erp.app.contractmanagement.domain.Subscription o record by o.id",Subscription.class).getResultList();
	}
	
	@Override
	public Subscription getByCode(String code) {
		Subscription org = null;
		
		try
		{
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Subscription> query = builder.createQuery(Subscription.class);
			Root<Subscription> rootEntity = query.from(Subscription.class);
			ParameterExpression<String> p = builder.parameter(String.class);
			query.select(rootEntity).where(builder.equal(rootEntity.get("code"), p));

			TypedQuery<Subscription> typedQuery = em.createQuery(query);
			typedQuery.setParameter(p, code);
			
			org = typedQuery.getSingleResult();				
			//TypedQuery<Subscription> q = em.createQuery("select o from org.flowframe.kernel.common.mdm.domain.currency.Subscription o WHERE o.code = :code",Subscription.class);
			//q.setParameter("code", code);
						
			//org = q.getSingleResult();
		}
		catch(NoResultException e){}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		catch(Error e)
		{
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
		}		
		
		return org;
	}	
	
	public SubscriptionPlan getPlanByCode(String code) {
		SubscriptionPlan rec = null;
		
		try
		{
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<SubscriptionPlan> query = builder.createQuery(SubscriptionPlan.class);
			Root<SubscriptionPlan> rootEntity = query.from(SubscriptionPlan.class);
			ParameterExpression<String> p = builder.parameter(String.class);
			query.select(rootEntity).where(builder.equal(rootEntity.get("code"), p));

			TypedQuery<SubscriptionPlan> typedQuery = em.createQuery(query);
			typedQuery.setParameter(p, code);
			
			rec = typedQuery.getSingleResult();				
			//TypedQuery<SubscriptionPlan> q = em.createQuery("select o from org.flowframe.kernel.common.mdm.domain.currency.Subscription o WHERE o.code = :code",Subscription.class);
			//q.setParameter("code", code);
						
			//org = q.getSingleResult();
		}
		catch(NoResultException e){}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		catch(Error e)
		{
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
		}		
		
		return rec;
	}	

	@Override
	public Subscription add(Subscription record) {
		record = em.merge(record);
		
		return record;
	}

	@Override
	public void delete(Subscription record) {
		em.remove(record);
	}

	@Override
	public Subscription update(Subscription record) {
		return em.merge(record);
	}


	@Override
	public Subscription provide(Subscription record) {
		Subscription existingRecord = getByCode(record.getCode());
		if (Validator.isNull(existingRecord))
		{		
			record = update(record);
			try {
				//em.flush();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return record;
	}

	@Override
	public SubscriptionPlan getFreePlan() {
		return getPlanByCode(ISubscriptionDAOService.FREE_TRIAL_PLAN_NAME);
	}	
}
