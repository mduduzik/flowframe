package org.flowframe.erp.app.financialmanagement.dao.services.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
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

import org.flowframe.erp.app.financialmanagement.dao.services.IARReceiptDAOService;
import org.flowframe.erp.app.financialmanagement.domain.receivable.ARReceipt;
import org.flowframe.erp.app.financialmanagement.domain.receivable.ARReceiptLine;
import org.flowframe.kernel.common.mdm.dao.services.ICountryDAOService;
import org.flowframe.kernel.common.mdm.dao.services.user.IUserDAOService;
import org.flowframe.kernel.common.mdm.domain.geolocation.Country;
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
public class ARReceiptDAOImpl implements IARReceiptDAOService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());	
    /**
     * Spring will inject a managed JPA {@link EntityManager} into this field.
     */
    @PersistenceContext
    private EntityManager em;	
    
    @Autowired
    private ICountryDAOService countryDao;  
    
    @Autowired
    private IUserDAOService userDAOService;
    
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public ARReceipt get(long id) {
		return em.getReference(ARReceipt.class, id);
	}    

	@Override
	public List<ARReceipt> getAll() {
		return em.createQuery("select o from org.flowframe.erp.app.financialmanagement.domain.receivable.ARReceipt o record order by o.id").getResultList();
	}
	
	@Override
	public List<ARReceipt> getAllByCustomerId(Long customerId) {
		List<ARReceipt> res = null;
		try
		{
			Query query = em.createQuery("select o from org.flowframe.erp.app.financialmanagement.domain.receivable.ARReceipt o where o.debtor.id = :debtorId order by o.dateCreated desc");
			query.setParameter("debtorId", customerId);

			
			res = query.getResultList();				
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
	
	@Override
	public ARReceipt getByCode(String code) {
		ARReceipt rec = null;
		try
		{
			Query query = em.createQuery("select o from org.flowframe.erp.app.financialmanagement.domain.receivable.ARReceipt o where o.code = :code order by o.dateCreated desc");
			query.setParameter("code", code);

			
			rec = (ARReceipt)query.getSingleResult();				
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
	public ARReceipt add(ARReceipt record) {
		record = em.merge(record);
		
		return record;
	}

	@Override
	public void delete(ARReceipt record) {
		em.remove(record);
	}

	@Override
	public ARReceipt update(ARReceipt record) {
		return em.merge(record);
	}


	@Override
	public ARReceipt provide(ARReceipt record) {
		ARReceipt existingRecord = getByCode(record.getCode());
		if (Validator.isNull(existingRecord))
		{		
			record = add(record);
		}
		else {
			record = update(record);
		}
		return record;
	}
	
	@Override
	public ARReceipt provide(String code, String name, Country country) {
		ARReceipt res = null;
		if ((res = getByCode(code)) == null)
		{
			ARReceipt unit = new ARReceipt();

			unit.setCode(code);
			unit.setName(name);

			res = add(unit);
		}
		return res;
	}

	@Override
	public ARReceiptLine getLine(long id) {
		return em.getReference(ARReceiptLine.class, id);
	}

	@Override
	public ARReceiptLine addLine(Long arreceiptId, ARReceiptLine record) {
		ARReceipt ar = get(arreceiptId);
		ar.getLines().add(record);
		ar = update(ar);
		em.refresh(record);
		return record;
	}

	@Override
	public List<ARReceipt> getAllByUserEmail(String emailAddress) {
		logger.info("Getting user by email["+emailAddress+"]");
		User user = getUserByEmailAddress(emailAddress);
		logger.info("User by email["+emailAddress+"] is "+user);
		if (Validator.isNull(user))
			return null;
		
		Organization cust = em.getReference(Organization.class, user.getTenant().getId());
		logger.info("User tenant is ["+cust.getName()+"]");
		
		if (Validator.isNotNull(cust)) {
			List<ARReceipt> rcpts = getAllByCustomerId(cust.getId());
			logger.info("Retrieved "+rcpts.size()+" receipts");
			return rcpts;
		}
		else
			return new ArrayList<ARReceipt>();		
		
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
