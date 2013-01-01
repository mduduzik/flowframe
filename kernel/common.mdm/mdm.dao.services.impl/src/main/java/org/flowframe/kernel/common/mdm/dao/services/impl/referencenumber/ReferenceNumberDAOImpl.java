package org.flowframe.kernel.common.mdm.dao.services.impl.referencenumber;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.flowframe.kernel.common.mdm.dao.services.IEntityMetadataDAOService;
import org.flowframe.kernel.common.mdm.dao.services.referencenumber.IReferenceNumberDAOService;
import org.flowframe.kernel.common.mdm.domain.BaseEntity;
import org.flowframe.kernel.common.mdm.domain.metadata.DefaultEntityMetadata;
import org.flowframe.kernel.common.mdm.domain.referencenumber.ReferenceNumber;

@Transactional
@Repository
public class ReferenceNumberDAOImpl implements IReferenceNumberDAOService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());	
    /**
     * Spring will inject a managed JPA {@link EntityManager} into this field.
     */
    @PersistenceContext
    private EntityManager em;
    
    @Autowired
    private IEntityMetadataDAOService entityMetadataDao;
    
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public ReferenceNumber get(long id) {
		return em.getReference(ReferenceNumber.class, id);
	}    

	@Override
	public List<ReferenceNumber> getAll() {
		return em.createQuery("select o from org.flowframe.kernel.common.mdm.domain.referencenumber.ReferenceNumber o record by o.id",ReferenceNumber.class).getResultList();
	}
	
	@Override
	public ReferenceNumber getByCode(String code) {
		ReferenceNumber org = null;
		
		try
		{
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<ReferenceNumber> query = builder.createQuery(ReferenceNumber.class);
			Root<ReferenceNumber> rootEntity = query.from(ReferenceNumber.class);
			ParameterExpression<String> p = builder.parameter(String.class);
			query.select(rootEntity).where(builder.equal(rootEntity.get("code"), p));

			TypedQuery<ReferenceNumber> typedQuery = em.createQuery(query);
			typedQuery.setParameter(p, code);
			
			org = typedQuery.getSingleResult();			
			//TypedQuery<ReferenceNumber> q = em.createQuery("select o from org.flowframe.kernel.common.mdm.domain.referencenumber.ReferenceNumber o WHERE o.code = :code",ReferenceNumber.class);
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
	public ReferenceNumber add(ReferenceNumber record) {
		record = em.merge(record);
		
		return record;
	}

	@Override
	public void delete(ReferenceNumber record) {
		em.remove(record);
	}

	@Override
	public ReferenceNumber update(ReferenceNumber record) {
		return em.merge(record);
	}

	@Override
	public ReferenceNumber add(Long parentEntityPK, Class<?> parentEntityType) {
		assert (parentEntityPK != null) : "Parent Entity Id was null.";
		assert (parentEntityType != null) : "Parent Entity Type was null.";
		Object parentEntity = em.getReference(parentEntityType, parentEntityPK);
		assert (parentEntity instanceof BaseEntity) : "Parent Entity was not of type Base Entity.";
		DefaultEntityMetadata parentEntityMetaData = entityMetadataDao.provide(parentEntityType);
		assert (parentEntityMetaData != null) : "Could not get parent entity metadata.";
		ReferenceNumber newRecord = new ReferenceNumber();
		newRecord.setEntityPK(((BaseEntity) parentEntity).getId());
		newRecord.setEntityMetadata(parentEntityMetaData);
		newRecord = em.merge(newRecord);
		
		String format = String.format("%%0%dd", 3);
		String paddedId = String.format(format, newRecord.getId());
		String code = ((BaseEntity) parentEntity).getCode() + "-RN" + paddedId;
		newRecord.setName(code);
		newRecord.setCode(code);
		newRecord = em.merge(newRecord);
		
		return newRecord;
	}

	public IEntityMetadataDAOService getEntityMetadataDao() {
		return entityMetadataDao;
	}

	public void setEntityMetadataDao(IEntityMetadataDAOService entityMetadataDao) {
		this.entityMetadataDao = entityMetadataDao;
	}
}
