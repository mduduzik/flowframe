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
import org.flowframe.kernel.common.mdm.dao.services.documentlibrary.IFolderDAOService;
import org.flowframe.kernel.common.mdm.domain.documentlibrary.Folder;
import org.flowframe.kernel.common.mdm.domain.organization.Organization;
import org.flowframe.kernel.common.mdm.domain.user.User;
import org.flowframe.kernel.common.utils.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    
	@Autowired
	private IFolderDAOService folderDAOService;
    
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
	
	public Customer update(Customer record) {
		return em.merge(record);
	}

	@Override
	public Customer add(Customer record) {
		
		record = update(record);//to get id
		
		try {
			Folder folder = folderDAOService.provideFolderForEntity(Organization.class, record.getId());
			record.setDocFolder(folder);
		} catch (Exception e) {
			throw new IllegalArgumentException("Prviding folder for Customer("+record.getId()+") failed...",e);
		}
		
		record = update(record);//update

		return record;		
	}

	@Override
	public Customer provide(Customer record) {
		Customer existingRecord = getByName(record.getName());
		if (Validator.isNull(existingRecord)) {
			existingRecord = add(record);
		}
		return existingRecord;
	}
	

	@Override
	public Long getUserIdByEmailAddress(String emailAddress) {
		return getUserCustomerId(emailAddress);
	}


	private Long getUserCustomerId(String userEmailAddress) {
		
		User user = getUserByEmailAddress(userEmailAddress);
		if (Validator.isNull(user))
			return null;
		
		Organization cust = em.getReference(Organization.class, user.getTenant().getId());
		
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
}
