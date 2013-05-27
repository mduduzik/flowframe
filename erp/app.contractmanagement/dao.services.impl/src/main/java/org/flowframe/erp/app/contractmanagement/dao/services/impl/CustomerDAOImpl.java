package org.flowframe.erp.app.contractmanagement.dao.services.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Customer> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer add(Customer record) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer provide(Customer record) {
		// TODO Auto-generated method stub
		return null;
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

	private CustomerUser getUserByEmailAddress(String userEmailAddress) {
		CustomerUser res = null;
		try
		{
			TypedQuery<CustomerUser> query = em.createQuery("select o from org.flowframe.erp.app.contractmanagement.domain.CustomerUser o where o.emailAddress = :emailAddress",CustomerUser.class);
			query.setParameter("emailAddress", userEmailAddress);

			
			res = query.getSingleResult();				
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
		
		return res;
	}	
}
