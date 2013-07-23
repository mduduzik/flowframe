package org.flowframe.erp.app.contractmanagement.dao.services.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.flowframe.erp.app.contractmanagement.dao.services.ISubscriptionDAOService;
import org.flowframe.erp.app.contractmanagement.domain.Customer;
import org.flowframe.erp.app.contractmanagement.domain.Subscription;
import org.flowframe.erp.app.contractmanagement.domain.SubscriptionChange;
import org.flowframe.erp.app.contractmanagement.domain.SubscriptionPlan;
import org.flowframe.kernel.common.mdm.domain.organization.Organization;
import org.flowframe.kernel.common.mdm.domain.user.User;
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
	
	@Override
	public Subscription getByPlanIdAndCustomerId(Long planId, Long customerId) {
		Subscription sub = null;
		try
		{
			TypedQuery<Subscription> query = em.createQuery("select o from org.flowframe.erp.app.contractmanagement.domain.Subscription o where o.subscribedPlan.id = :planId and o.customer.id = :customerId",Subscription.class);
			query.setParameter("planId", planId);
			query.setParameter("customerId", customerId);
	/*		
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Subscription> query = builder.createQuery(Subscription.class);
			Root<Subscription> rootEntity = query.from(Subscription.class);
			
			TypedQuery<Subscription> typedQuery = em.createQuery(query);
			
			ParameterExpression<Long> p1 = builder.parameter(Long.class);
			ParameterExpression<Long> p2 = builder.parameter(Long.class);
			
			query.select(rootEntity).where(builder.and(builder.equal(rootEntity.get("subscribedPlan.id"), p1),builder.equal(rootEntity.get("customer.id"), p2)));
			typedQuery.setParameter(p1, planId);
			typedQuery.setParameter(p2, customerId);			*/
			
			sub = query.getSingleResult();				
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
		
		return sub;		
	}
	
	@Override
	public Subscription getByCustomerId(Long customerId) {
		Subscription sub = null;
		try
		{
			Query query = em.createQuery("select o from org.flowframe.erp.app.contractmanagement.domain.Subscription o where o.customer.id = :customerId AND o.active = TRUE");
			query.setParameter("customerId", customerId);

			sub = (Subscription)query.getSingleResult();				
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
		
		return sub;		
	}	
	
	@Override
	public Subscription getCurrentSubscriptionByUserEmail(String emailAddress) {
		Subscription sub = null;
		try
		{
			User user = getUserByEmailAddress(emailAddress);
			if (Validator.isNull(user))
				return null;
			
			Organization cust = em.getReference(Organization.class, user.getTenant().getId());
			
			sub = getByCustomerId(cust.getId());		
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
		
		return sub;		
	}
	
	@Override
	public SubscriptionPlan getCurrentSubscriptionbByUserEmail(String customerUserEmaillAddress) {
		SubscriptionPlan plan = null;
		try
		{
			User user = getUserByEmailAddress(customerUserEmaillAddress);
			if (Validator.isNull(user))
				return null;
			
			Organization cust = em.getReference(Organization.class, user.getTenant().getId());
			
			Subscription sub = getByCustomerId(cust.getId());		
			if (sub != null)
				plan = sub.getSubscribedPlan();
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
		
		return plan;		
	}		
	
	@Override
	public Long getCurrentSubscriptionPlanIdByUserEmail(String customerUserEmaillAddress) {
		SubscriptionPlan plan = getCurrentSubscriptionbByUserEmail(customerUserEmaillAddress);
		if (plan != null)
			return plan.getId();
		else
			return 0L;
	}
	
	@Override
	public Long getPlanIdByCustomerId(Long customerId) {
		Subscription sub = getByCustomerId(customerId);
		SubscriptionPlan plan = sub.getSubscribedPlan();
		return plan.getId();
	}
	
	public SubscriptionPlan getPlanByCode(String code) {
		SubscriptionPlan rec = null;
		
		try
		{
			Query query = em.createQuery("select o from org.flowframe.erp.app.contractmanagement.domain.SubscriptionPlan o where o.code = :code");
			query.setParameter("code", code);

			rec = (SubscriptionPlan)query.getSingleResult();				
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
	
	/**
	 * 
	 * Plans
	 * 
	 */

	@Override
	public SubscriptionPlan getFreePlan() {
		return getPlanByCode(ISubscriptionDAOService.FREE_TRIAL_PLAN_NAME);
	}

	@Override
	public SubscriptionPlan getPlan(long id) {
		SubscriptionPlan rec = null;
		
		try
		{
			Query query = em.createQuery("select o from org.flowframe.erp.app.contractmanagement.domain.SubscriptionPlan o where o.id = :id");
			query.setParameter("id", id);

			rec = (SubscriptionPlan)query.getSingleResult();				
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
	public List<SubscriptionPlan> getAllPlans() {
		return em.createQuery("select o from org.flowframe.erp.app.contractmanagement.domain.SubscriptionPlan o order by o.id").getResultList();
	}

	@Override
	public SubscriptionPlan getPlanByExternalRefId(String externalRefId) {
		SubscriptionPlan plan = null;
		
		try
		{
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<SubscriptionPlan> query = builder.createQuery(SubscriptionPlan.class);
			Root<SubscriptionPlan> rootEntity = query.from(SubscriptionPlan.class);
			ParameterExpression<String> p = builder.parameter(String.class);
			query.select(rootEntity).where(builder.equal(rootEntity.get("externalRefId"), p));

			TypedQuery<SubscriptionPlan> typedQuery = em.createQuery(query);
			typedQuery.setParameter(p, externalRefId);
			
			plan = typedQuery.getSingleResult();				
			//TypedQuery<SubscriptionPlan> q = em.createQuery("select o from plan.flowframe.kernel.common.mdm.domain.currency.SubscriptionPlan o WHERE o.code = :code",SubscriptionPlan.class);
			//q.setParameter("code", code);
						
			//plan = q.getSingleResult();
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
		
		return plan;
	}

	@Override
	public SubscriptionPlan addPlan(SubscriptionPlan record) {
		record = em.merge(record);
		
		return record;
	}

	@Override
	public void deletePlan(SubscriptionPlan record) {
		em.detach(record);
	}

	@Override
	public SubscriptionPlan updatePlan(SubscriptionPlan record) {
		return em.merge(record);
	}

	@Override
	public SubscriptionPlan providePlan(SubscriptionPlan record) {
		SubscriptionPlan existingRecord = getPlanByCode(record.getCode());
		if (Validator.isNull(existingRecord))
		{		
			record = updatePlan(record);
		}
		else
		{
			record = addPlan(record);
		}
		return record;
	}

	@Override
	public List<SubscriptionPlan> getAllPlansAvailableForSubscriptionByUserEmail(String userEmailAddress) {
		User user = getUserByEmailAddress(userEmailAddress);
		if (Validator.isNull(user))
			return null;
		
		Organization cust = em.getReference(Organization.class, user.getTenant().getId());
		
		Subscription sub = getByCustomerId(cust.getId());
		
		Query query = em.createQuery("select o from org.flowframe.erp.app.contractmanagement.domain.SubscriptionPlan o WHERE o.id NOT in (:planId)");
		query.setParameter("planId", sub.getSubscribedPlan().getId());
		
		return query.getResultList();
	}	
	
	private User getUserByEmailAddress(String userEmailAddress) {
		User record = null;
		try {
			Query query = em.createQuery("select o from org.flowframe.kernel.common.mdm.domain.user.User o where o.emailAddress = :emailAddress");
			query.setParameter("emailAddress", userEmailAddress);
			record = (User) query.getSingleResult();
		} catch (NoResultException e) {
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
		} catch (Error e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
		}
		
		return record;		
	}
	
	/**
	 * 
	 * SubscriptionChange
	 * 
	 */

	@Override
	public SubscriptionChange getSubscriptionChange(long id) {
		return em.getReference(SubscriptionChange.class, id);
	}

	@Override
	public List<SubscriptionChange> getAllSubscriptionChanges() {
		return em.createQuery("select o from org.flowframe.erp.app.contractmanagement.domain.SubscriptionChange o record by o.id",SubscriptionChange.class).getResultList();
	}

	@Override
	public SubscriptionChange getSubscriptionChangeByCode(String code) {
		SubscriptionChange record = null;
		try {
			Query query = em.createQuery("select o from org.flowframe.erp.app.contractmanagement.domain.SubscriptionChange o WHERE o.code = :code");
			query.setParameter("code", code);
			record = (SubscriptionChange) query.getSingleResult();
		} catch (NoResultException e) {
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
		} catch (Error e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
		}
		
		return record;
	}

	@Override
	public SubscriptionChange addSubscriptionChange(SubscriptionChange record) {
		record = em.merge(record);
		
		return record;
	}
}
