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

import org.flowframe.erp.app.contractmanagement.dao.services.IServiceProvisionDAOService;
import org.flowframe.erp.app.contractmanagement.domain.service.ServiceProvision;
import org.flowframe.erp.app.contractmanagement.domain.service.ServiceProvisionGroup;
import org.flowframe.kernel.common.utils.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Repository
public class ServiceProvisionDAOImpl implements IServiceProvisionDAOService {
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
	public ServiceProvision get(long id) {
		return em.getReference(ServiceProvision.class, id);
	}    

	@Override
	public List<ServiceProvision> getAll() {
		return em.createQuery("select o from org.flowframe.erp.app.contractmanagement.domain.ServiceProvision o record order by o.id",ServiceProvision.class).getResultList();
	}
	
	@Override
	public ServiceProvision getByCode(String code) {
		ServiceProvision org = null;
		
		try
		{
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<ServiceProvision> query = builder.createQuery(ServiceProvision.class);
			Root<ServiceProvision> rootEntity = query.from(ServiceProvision.class);
			ParameterExpression<String> p = builder.parameter(String.class);
			query.select(rootEntity).where(builder.equal(rootEntity.get("code"), p));

			TypedQuery<ServiceProvision> typedQuery = em.createQuery(query);
			typedQuery.setParameter(p, code);
			
			org = typedQuery.getSingleResult();				
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
	public ServiceProvision add(ServiceProvision record) {
		record = em.merge(record);
		
		return record;
	}

	@Override
	public void delete(ServiceProvision record) {
		em.remove(record);
	}

	@Override
	public ServiceProvision update(ServiceProvision record) {
		return em.merge(record);
	}


	@Override
	public ServiceProvision provide(ServiceProvision record) {
		ServiceProvision existingRecord = getByCode(record.getCode());
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
	public ServiceProvisionGroup getGroup(long id) {
		return em.getReference(ServiceProvisionGroup.class, id);
	}    

	@Override
	public List<ServiceProvisionGroup> getAllGroups() {
		return em.createQuery("select o from org.flowframe.erp.app.contractmanagement.domain.ServiceProvisionGroup o record order by o.id",ServiceProvisionGroup.class).getResultList();
	}
	
	@Override
	public ServiceProvisionGroup getGroupByCode(String code) {
		ServiceProvisionGroup org = null;
		
		try
		{
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<ServiceProvisionGroup> query = builder.createQuery(ServiceProvisionGroup.class);
			Root<ServiceProvisionGroup> rootEntity = query.from(ServiceProvisionGroup.class);
			ParameterExpression<String> p = builder.parameter(String.class);
			query.select(rootEntity).where(builder.equal(rootEntity.get("code"), p));

			TypedQuery<ServiceProvisionGroup> typedQuery = em.createQuery(query);
			typedQuery.setParameter(p, code);
			
			org = typedQuery.getSingleResult();				
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
	public ServiceProvisionGroup addGroup(ServiceProvisionGroup record) {
		record = em.merge(record);
		
		return record;
	}

	@Override
	public void deleteGroup(ServiceProvisionGroup record) {
		em.remove(record);
	}

	@Override
	public ServiceProvisionGroup updateGroup(ServiceProvisionGroup record) {
		return em.merge(record);
	}


	@Override
	public ServiceProvisionGroup provideGroup(ServiceProvisionGroup record) {
		ServiceProvisionGroup existingRecord = getGroupByCode(record.getCode());
		if (Validator.isNull(existingRecord))
		{		
			record = updateGroup(record);
		}
		else
		{
			record = addGroup(record);
		}
		return record;
	}	
}
