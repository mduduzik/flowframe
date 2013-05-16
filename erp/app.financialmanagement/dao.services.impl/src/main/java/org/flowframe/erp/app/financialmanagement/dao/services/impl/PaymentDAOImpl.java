package org.flowframe.erp.app.financialmanagement.dao.services.impl;

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

import org.flowframe.erp.app.financialmanagement.dao.services.IPaymentDAOService;
import org.flowframe.erp.app.financialmanagement.domain.payment.Payment;
import org.flowframe.erp.app.financialmanagement.domain.receivable.ARReceiptLine;
import org.flowframe.kernel.common.mdm.domain.geolocation.Country;
import org.flowframe.kernel.common.utils.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Repository
public class PaymentDAOImpl implements IPaymentDAOService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());	
    /**
     * Spring will inject a managed JPA {@link EntityManager} into this field.
     */
    @PersistenceContext
    private EntityManager em;	
    
  

	@Override
	public Payment get(long id) {
		return em.getReference(Payment.class, id);
	}    

	@Override
	public List<Payment> getAll() {
		return em.createQuery("select o from org.flowframe.erp.app.financialmanagement.domain.payment.Payment o record order by o.id",Payment.class).getResultList();
	}
	
	@Override
	public Payment getByCode(String code) {
		Payment rec = null;
		
		try
		{
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Payment> query = builder.createQuery(Payment.class);
			Root<Payment> rootEntity = query.from(Payment.class);
			ParameterExpression<String> p = builder.parameter(String.class);
			query.select(rootEntity).where(builder.equal(rootEntity.get("code"), p));

			TypedQuery<Payment> typedQuery = em.createQuery(query);
			typedQuery.setParameter(p, code);
			
			rec = typedQuery.getSingleResult();				
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
	public Payment add(Payment record) {
		record = em.merge(record);
		
		return record;
	}

	@Override
	public void delete(Payment record) {
		em.remove(record);
	}

	@Override
	public Payment update(Payment record) {
		return em.merge(record);
	}


	@Override
	public Payment provide(Payment record) {
		Payment existingRecord = getByCode(record.getCode());
		if (Validator.isNull(existingRecord))
		{		
			existingRecord = update(record);
		}
		else
			existingRecord = add(record);
		
		return existingRecord;
	}
}
