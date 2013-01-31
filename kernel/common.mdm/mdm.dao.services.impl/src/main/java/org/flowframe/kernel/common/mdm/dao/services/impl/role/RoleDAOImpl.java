package org.flowframe.kernel.common.mdm.dao.services.impl.role;

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
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.flowframe.kernel.common.utils.Validator;
import org.flowframe.kernel.common.mdm.dao.services.role.IRoleDAOService;
import org.flowframe.kernel.common.mdm.domain.constants.RoleCustomCONSTANTS;
import org.flowframe.kernel.common.mdm.domain.role.Role;

@Transactional
@Repository
public class RoleDAOImpl implements IRoleDAOService {
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
	public Role get(long id) {
		return em.getReference(Role.class, id);
	}    

	@Override
	public List<Role> getAll() {
		return em.createQuery("select o from org.flowframe.kernel.common.mdm.domain.role.Role o record by o.id",Role.class).getResultList();
	}
	
	@Override
	public Role getByName(String name) {
		Role org = null;
		
		try
		{
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Role> query = builder.createQuery(Role.class);
			Root<Role> rootEntity = query.from(Role.class);
			ParameterExpression<String> p1 = builder.parameter(String.class);
			query.select(rootEntity).where(builder.equal(rootEntity.get("name"), p1));

			TypedQuery<Role> typedQuery = em.createQuery(query);
			typedQuery.setParameter(p1, name);
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
	public Role add(Role record) {
		record = em.merge(record);
		
		return record;
	}

	@Override
	public void delete(Role record) {
		em.remove(record);
	}

	@Override
	public Role update(Role record) {
		return em.merge(record);
	}


	@Override
	public Role provide(Role record) {
		Role existingRecord = getByName(record.getName());
		if (Validator.isNull(existingRecord))
		{		
			record = update(record);
		}
		return record;
	}
	
	@Override
	public Role provide(String name) {
		Role res = null;
		if ((res = getByName(name)) == null)
		{
			Role unit = new Role(name);
			res = add(unit);
		}
		return res;
	}	
	
	
	
	
	@Override
	public void provideDefaults()
	{	
	}

	@Override
	public Role getByCode(String code) {
		// TODO Auto-generated method stub
		return null;
	}

	/*	
	@Override
	public Role addRole(Long folderId, DocType attachmentType, Role) {
		Role res =  getByEmailOrScreenname(folderId,null);
		attachmentType = em.merge(attachmentType);
		fileEntry.setDocType(attachmentType);
		fileEntry.setRole(res);
		fileEntry = em.merge(fileEntry);
		res.getFiles().add(fileEntry);
		res = em.merge(res);
		return fileEntry;
	}

	@Override
	public Role addFileEntries(Long folderId, Set<Role> fileEntries) {
		for (Role fe : fileEntries)
		{
			addRole(folderId, fe.getDocType(), fe);
		}
		return getByEmailOrScreenname(folderId,null);
	}

	@Override
	public Role deleteRole(Long folderId, Role fileEntry) {
		Role res =  getByEmailOrScreenname(folderId,null);
		fileEntry = em.merge(fileEntry);
		res.getFiles().remove(fileEntry);
		res = em.merge(res);
		return res;
	}

	@Override
	public Role deleteFileEntries(Long folderId, Set<Role> fileEntries) {
		for (Role fe : fileEntries)
		{
			deleteRole(folderId, fe);
		}
		return getByEmailOrScreenname(folderId,null);
	}*/
}
