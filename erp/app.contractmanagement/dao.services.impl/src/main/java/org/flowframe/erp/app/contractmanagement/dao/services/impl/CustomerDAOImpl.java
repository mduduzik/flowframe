package org.flowframe.erp.app.contractmanagement.dao.services.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.flowframe.erp.app.contractmanagement.dao.customer.ICustomerDAOService;
import org.flowframe.erp.app.contractmanagement.domain.Customer;
import org.flowframe.erp.app.contractmanagement.domain.CustomerUser;
import org.flowframe.kernel.common.utils.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Repository
public class CustomerDAOImpl implements ICustomerDAOService{
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
	public Customer get(long id) {
		return em.getReference(Customer.class, id);
	}

	@Override
	public List<Customer> getAll() {
		return em.createQuery("select o from org.flowframe.erp.app.contractmanagement.domain.Customer o record by o.id").getResultList();
	}

	@Override
	public Customer add(Customer record) {
		return em.merge(record);
	}

	@Override
	public Customer provide(Customer record) {
		Customer existingRecord = getByName(record.getName());
		if (Validator.isNull(existingRecord)) {
			existingRecord = add(record);
		}
		return existingRecord;
	}
	
	
	public CustomerUser addUser(CustomerUser record) {
		return em.merge(record);
	}

	public CustomerUser updateUser(CustomerUser record) {
		return em.merge(record);
	}
	
	@Override
	public CustomerUser provideUser(CustomerUser record) {
		CustomerUser existingRecord = getUserByEmailAddress(record.getEmailAddress());
		if (Validator.isNull(existingRecord))
		{		
			record = addUser(record);
		}
		else
		{
			record = updateUser(record);
		}
		return record;
	}	

	@Override
	public Long getUserCustomerIdByEmailAddress(String emailAddress) {
		return getUserCustomerId(emailAddress);
	}


	private Long getUserCustomerId(String userEmailAddress) {
		
		CustomerUser user = getUserByEmailAddress(userEmailAddress);
		if (Validator.isNull(user))
			return null;
		
		Customer cust = em.getReference(Customer.class, user.getCustomer().getId());
		
		if (Validator.isNotNull(cust))
			return cust.getId();
		else
			return null;
			
	}
	
	@Override
	public Customer getByCode(String code) {
		Customer record = null;
		try {
			Query query = em.createQuery("select o from org.flowframe.erp.app.contractmanagement.domain.Customer o WHERE o.code = :code");
			query.setParameter("code", code);
			record = (Customer) query.getSingleResult();
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
	
	private Customer getByName(String name) {
		Customer record = null;
		try {
			Query query = em.createQuery("select o from org.flowframe.erp.app.contractmanagement.domain.Customer o WHERE o.name = :name");
			query.setParameter("name", name);
			record = (Customer) query.getSingleResult();
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

	private CustomerUser getUserByEmailAddress(String userEmailAddress) {
		CustomerUser record = null;
		try {
			Query query = em.createQuery("select o from org.flowframe.erp.app.contractmanagement.domain.CustomerUser o where o.emailAddress = :emailAddress");
			query.setParameter("emailAddress", userEmailAddress);
			record = (CustomerUser) query.getSingleResult();
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
}
