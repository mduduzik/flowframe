package org.flowframe.kernel.common.mdm.dao.services.impl.user;

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
import org.flowframe.kernel.common.mdm.dao.services.user.IUserDAOService;
import org.flowframe.kernel.common.mdm.domain.user.User;

@Transactional
@Repository
public class UserDAOImpl implements IUserDAOService {
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
	public User get(long id) {
		return em.getReference(User.class, id);
	}    

	@Override
	public List<User> getAll() {
		return em.createQuery("select o from org.flowframe.kernel.common.mdm.domain.user.User o record by o.id",User.class).getResultList();
	}
	
	@Override
	public User getByEmailOrScreenname(String email, String screenName) {
		User org = null;
		
		try
		{
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<User> query = builder.createQuery(User.class);
			Root<User> rootEntity = query.from(User.class);
			ParameterExpression<String> p1 = builder.parameter(String.class);
			ParameterExpression<String> p2 = builder.parameter(String.class);
			query.select(rootEntity).where(builder.or(builder.equal(rootEntity.get("emailAddress"), p1),builder.equal(rootEntity.get("screenName"), p2)));

			TypedQuery<User> typedQuery = em.createQuery(query);
			typedQuery.setParameter(p1, email);
			typedQuery.setParameter(p2, screenName);
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
	public User add(User record) {
		record = em.merge(record);
		
		return record;
	}

	@Override
	public void delete(User record) {
		em.remove(record);
	}

	@Override
	public User update(User record) {
		return em.merge(record);
	}


	@Override
	public User provide(User record) {
		User existingRecord = getByEmailOrScreenname(record.getEmailAddress(),record.getScreenName());
		if (Validator.isNull(existingRecord))
		{		
			record = update(record);
		}
		return record;
	}
	
	@Override
	public User provide(String email, String screenName) {
		User res = null;
		if ((res = getByEmailOrScreenname(email,screenName)) == null)
		{
			User unit = new User();

			unit.setEmailAddress(email);
			unit.setScreenName(email);

			res = add(unit);
		}
		return res;
	}	
	
	@Override
	public void provideDefaults()
	{
		User testUser = new User();
		testUser.setEmailAddress("test@liferay.com");
		testUser.setScreenName("test");
		testUser.setFirstName("ConX");
		testUser.setLastName("User");
		add(testUser);
	}

	@Override
	public User getByCode(String code) {
		// TODO Auto-generated method stub
		return null;
	}

	/*	
	@Override
	public User addUser(Long folderId, DocType attachmentType, User) {
		User res =  getByEmailOrScreenname(folderId,null);
		attachmentType = em.merge(attachmentType);
		fileEntry.setDocType(attachmentType);
		fileEntry.setUser(res);
		fileEntry = em.merge(fileEntry);
		res.getFiles().add(fileEntry);
		res = em.merge(res);
		return fileEntry;
	}

	@Override
	public User addFileEntries(Long folderId, Set<User> fileEntries) {
		for (User fe : fileEntries)
		{
			addUser(folderId, fe.getDocType(), fe);
		}
		return getByEmailOrScreenname(folderId,null);
	}

	@Override
	public User deleteUser(Long folderId, User fileEntry) {
		User res =  getByEmailOrScreenname(folderId,null);
		fileEntry = em.merge(fileEntry);
		res.getFiles().remove(fileEntry);
		res = em.merge(res);
		return res;
	}

	@Override
	public User deleteFileEntries(Long folderId, Set<User> fileEntries) {
		for (User fe : fileEntries)
		{
			deleteUser(folderId, fe);
		}
		return getByEmailOrScreenname(folderId,null);
	}*/
}
