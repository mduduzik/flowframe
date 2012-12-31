package org.flowframe.kernel.metamodel.dao.services.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute.PersistentAttributeType;
import javax.persistence.metamodel.IdentifiableType;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.PluralAttribute.CollectionType;
import javax.persistence.metamodel.SingularAttribute;

import org.flowframe.kernel.common.mdm.domain.metamodel.AbstractAttribute;
import org.flowframe.kernel.common.mdm.domain.metamodel.BasicAttribute;
import org.flowframe.kernel.common.mdm.domain.metamodel.EntityType;
import org.flowframe.kernel.common.mdm.domain.metamodel.EntityTypeAttribute;
import org.flowframe.kernel.common.mdm.domain.metamodel.ListAttribute;
import org.flowframe.kernel.common.mdm.domain.metamodel.MapAttribute;
import org.flowframe.kernel.common.mdm.domain.metamodel.SetAttribute;
import org.flowframe.kernel.metamodel.dao.services.IEntityTypeDAOService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of {@link EntityType} that uses JPA for persistence.
 * <p />
 * <p/>
 * This class is marked as {@link Transactional}. The Spring configuration for
 * this module, enables AspectJ weaving for adding transaction demarcation to
 * classes annotated with <code>@Transactional</code>.
 */
@Transactional
@Repository
public class EntityTypeDAOImpl implements IEntityTypeDAOService {
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
	public EntityType get(long id) {
		return em.getReference(EntityType.class, id);
	}

	@Override
	public List<EntityType> getAll() {
		return em.createQuery("select o from org.flowframe.kernel.common.mdm.domain.metamodel.metadata.EntityType o record by o.id", EntityType.class).getResultList();
	}

	@Override
	public List<AbstractAttribute> getAllAttributesByEntityType(EntityType parentEntityType) {
		TypedQuery<AbstractAttribute> q = em.createQuery("select o from org.flowframe.kernel.common.mdm.domain.metamodel.AbstractAttribute o WHERE o.parentEntityType = :parentEntityType",
				AbstractAttribute.class);
		q.setParameter("parentEntityType", parentEntityType);
		return q.getResultList();
	}

	@Override
	public List<org.flowframe.kernel.common.mdm.domain.metamodel.SingularAttribute> getAllSingularAttributesByEntityType(EntityType parentEntityType) {
		TypedQuery<org.flowframe.kernel.common.mdm.domain.metamodel.SingularAttribute> q = em.createQuery(
				"select o from org.flowframe.kernel.common.mdm.domain.metamodel.SingularAttribute o WHERE o.parentEntityType = :parentEntityType",
				org.flowframe.kernel.common.mdm.domain.metamodel.SingularAttribute.class);
		q.setParameter("parentEntityType", parentEntityType);
		return q.getResultList();
	}

	@Override
	public List<org.flowframe.kernel.common.mdm.domain.metamodel.PluralAttribute> getAllPluralAttributesByEntityType(EntityType parentEntityType) {
		TypedQuery<org.flowframe.kernel.common.mdm.domain.metamodel.PluralAttribute> q = em.createQuery(
				"select o from org.flowframe.kernel.common.mdm.domain.metamodel.PluralAttribute o WHERE o.parentEntityType = :parentEntityType", org.flowframe.kernel.common.mdm.domain.metamodel.PluralAttribute.class);
		q.setParameter("parentEntityType", parentEntityType);
		return q.getResultList();
	}

	@Override
	public List<BasicAttribute> getAllBasicAttributesByEntityType(EntityType parentEntityType) {
		TypedQuery<BasicAttribute> q = em.createQuery("select o from org.flowframe.kernel.common.mdm.domain.metamodel.BasicAttribute o WHERE o.parentEntityType = :parentEntityType", BasicAttribute.class);
		q.setParameter("parentEntityType", parentEntityType);

		return q.getResultList();
	}

	/**
	 * Counts the number of EntityTypes with the provided entityClass.
	 * 
	 * @param entityClass
	 * @return
	 * @throws Exception
	 */
	public Long countByClass(Class<?> entityClass) throws Exception {
		Long res = null;

		try {
			logger.debug("countByClass(" + entityClass.getName() + ")");
			Query q = em.createQuery("select COUNT(o) from org.flowframe.kernel.common.mdm.domain.metamodel.EntityType o WHERE o.entityJavaSimpleType = :entityJavaSimpleType", Long.class);
			q.setParameter("entityJavaSimpleType", entityClass.getSimpleName());
			res = (Long) q.getSingleResult();
		} catch (NoResultException e) {
		}

		return res;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public EntityType getByClass(Class entityClass) throws Exception {
		EntityType record = null;
		TypedQuery<EntityType> typedQuery = null;
		try {
			logger.debug("getByClass(" + entityClass.getName() + ")");
			/*
			 * CriteriaBuilder builder = em.getCriteriaBuilder();
			 * CriteriaQuery<EntityType> query =
			 * builder.createQuery(EntityType.class); Root<EntityType>
			 * rootEntity = query.from(EntityType.class);
			 * ParameterExpression<String> p = builder.parameter(String.class);
			 * query.select(rootEntity).where(builder.equal(rootEntity.get(
			 * "entityJavaSimpleType"), p));
			 * 
			 * typedQuery = em.createQuery(query); typedQuery.setParameter(p,
			 * entityClass.getSimpleName());
			 * 
			 * return typedQuery.getSingleResult();
			 */
			typedQuery = em.createQuery("select DISTINCT o from org.flowframe.kernel.common.mdm.domain.metamodel.EntityType o WHERE o.entityJavaSimpleType = :entityJavaSimpleType", EntityType.class);
			typedQuery.setParameter("entityJavaSimpleType", entityClass.getSimpleName());
			record = typedQuery.getSingleResult();
		} catch (NoResultException e) {
		} catch (NonUniqueResultException e) {
			record = typedQuery.getResultList().get(0);
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
			throw e;
		} catch (Error e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
			throw e;
		}

		return record;
	}

	@Override
	public EntityType add(EntityType record) {
		String simpleJavaType = record.getEntityJavaSimpleType();
//		em.persist(record);
		logger.debug("EntityType corrsponding to (" + simpleJavaType + ") has been persisted.");
		// em.flush();
		// em.refresh(record);
		return em.merge(record);
	}

	public AbstractAttribute add(AbstractAttribute record) {
		// em.persist(record);
		// em.flush();
		record = em.merge(record);
		logger.debug("Merging AbstractAttribute " + String.valueOf(record));
		// em.refresh(record);
		return record;
	}

	@Override
	public void delete(EntityType record) {
		em.remove(record);
	}

	@Override
	public EntityType update(EntityType record) {
		return em.merge(record);
	}

	@Override
	public EntityType provide(Class<?> entityJavaClass) throws Exception {
		javax.persistence.metamodel.EntityType<?> ie = em.getMetamodel().entity(entityJavaClass);
		return provide(ie);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	/**
	 * If an EntityType already exists for the given jpaEntityType, 
	 * it is fetched and returned. Otherwise, a new EntityType is created with 
	 * all the relevant attributes and super EntityTypes.
	 * 
	 * NOTE: This method assumes that the jpaEntityType IS A MAPPED TYPE.
	 * 
	 * @param jpaEntityType
	 * 			The IdentifiedType corresponding to the java class of the EntityType
	 * @return
	 * 			The EntityType which corresponds to the provided jpaEntityType parameter
	 * @throws Exception
	 */
	private EntityType provideEntityType(IdentifiableType jpaEntityType) throws Exception {
		// Return null right away if jpaEntityType is null
		if (jpaEntityType == null) {
			return null;
		}

		EntityType entityType = null;
		long classCount = countByClass(jpaEntityType.getJavaType());
		if (classCount == 1) {
			// There IS an EntityType corresponding to jpaEntityType
			entityType = getByClass(jpaEntityType.getJavaType());
		} else if (classCount == 0) {
			// There ISN'T an EntityType corresponding to jpaEntityType
			// Instantiating the new EntityType
			entityType = new EntityType(jpaEntityType.getJavaType().getName(), jpaEntityType.getJavaType(), null, null, null, jpaEntityType.getJavaType().getName());
			// Persist the EntityType so we can add attributes and a super
			// EntityType
			entityType = add(entityType);
			// FIXME set the id SingularAttribute to not be null
			if (jpaEntityType.getSupertype() != null) {
				// Get the super type for the new EntityType
				if (jpaEntityType.getSupertype() != null) {
					// Since we know that the super class is mapped, we don't
					// have to go all the way back to provide()
					entityType.setSuperType(provideEntityType(jpaEntityType.getSupertype()));
				}
			}

			Set<SingularAttribute> singularAttributes = jpaEntityType.getDeclaredSingularAttributes();
			// Add all the singular attributes
			PersistentAttributeType persistenceAttributeType;
			org.flowframe.kernel.common.mdm.domain.metamodel.AbstractAttribute attribute;
			EntityType attributeEntityType;
			for (SingularAttribute singularAttribute : singularAttributes) {
				attribute = null;
				persistenceAttributeType = singularAttribute.getPersistentAttributeType();
				if (persistenceAttributeType == PersistentAttributeType.BASIC) {
					// This attribute is not an Entity, it is basic (String,
					// int,
					// double etc.)
					attribute = new org.flowframe.kernel.common.mdm.domain.metamodel.BasicAttribute(singularAttribute.isId(), singularAttribute.getName(), singularAttribute.getJavaType(), entityType);
					attribute = add(attribute);
					entityType.getDeclaredAttributes().add(new EntityTypeAttribute(entityType, attribute));
				} else if ((persistenceAttributeType == PersistentAttributeType.ONE_TO_ONE) || (persistenceAttributeType == PersistentAttributeType.MANY_TO_ONE)) {
					// This attribute is of type Entity, but it isn't a
					// collection
					attributeEntityType = provide((IdentifiableType) singularAttribute.getType());
					attribute = new org.flowframe.kernel.common.mdm.domain.metamodel.SingularAttribute(singularAttribute.getName(), singularAttribute.getJavaType(), singularAttribute.isId(),
							singularAttribute.isVersion(), singularAttribute.isOptional(), persistenceAttributeType, attributeEntityType);
					attribute.setParentEntityType(entityType);
					attribute = add(attribute);
					entityType.getDeclaredAttributes().add(new EntityTypeAttribute(entityType, attribute));
				}

			}
			EntityType innerEntityType = null;
			Set<PluralAttribute> pluralAttributes = jpaEntityType.getDeclaredPluralAttributes();
			org.flowframe.kernel.common.mdm.domain.metamodel.PluralAttribute collectionAttribute = null;
			// Add all the plural attributes
			for (PluralAttribute pattr : pluralAttributes) {
				persistenceAttributeType = pattr.getPersistentAttributeType();
				if (persistenceAttributeType == PersistentAttributeType.ONE_TO_MANY || persistenceAttributeType == PersistentAttributeType.MANY_TO_MANY) {
					// This attribute is a collection of Entities
					innerEntityType = provide((IdentifiableType) pattr.getElementType());
					if (pattr.getCollectionType() == CollectionType.LIST) {
						// The collection is of type List<?>
						collectionAttribute = new ListAttribute(pattr.getName(), innerEntityType, entityType, persistenceAttributeType);
						collectionAttribute = (org.flowframe.kernel.common.mdm.domain.metamodel.PluralAttribute) add(collectionAttribute);
						entityType.getDeclaredAttributes().add(new EntityTypeAttribute(entityType, collectionAttribute));
					} else if (pattr.getCollectionType() == CollectionType.MAP) {
						// The collection is of type Map<?, ?>
						collectionAttribute = new MapAttribute(pattr.getName(), innerEntityType, entityType, persistenceAttributeType);
						collectionAttribute = (org.flowframe.kernel.common.mdm.domain.metamodel.PluralAttribute) add(collectionAttribute);
						entityType.getDeclaredAttributes().add(new EntityTypeAttribute(entityType, collectionAttribute));
					} else if (pattr.getCollectionType() == CollectionType.SET) {
						// The collection is of type Set<?>
						collectionAttribute = new SetAttribute(pattr.getName(), innerEntityType, entityType, persistenceAttributeType);
						collectionAttribute = (org.flowframe.kernel.common.mdm.domain.metamodel.PluralAttribute) add(collectionAttribute);
						entityType.getDeclaredAttributes().add(new EntityTypeAttribute(entityType, collectionAttribute));
					}
				}
			}
			// Commit all the changes to the new EntityType
			entityType = update(entityType);
		} else {
			throw new Exception("There were multiple (" + classCount + ") EntityTypes of the class " + jpaEntityType.getJavaType().getSimpleName() + ".");
		}

		return entityType;
	}

	@Override
	public EntityType provide(IdentifiableType<?> jpaEntityType) throws Exception {
		logger.debug("Providing type for (" + jpaEntityType.getJavaType() + ")");
		// Ensure that jpaEntityType is a mapped type
		try {
			em.getEntityManagerFactory().getMetamodel().managedType((Class<?>) jpaEntityType.getJavaType());
		} catch (IllegalArgumentException e) {
			logger.debug("Entity (" + jpaEntityType.getJavaType() + ") is not a managed type.");
			e.printStackTrace();
			return null;
		}
		// If we've gotten this far, jpaEntityType is mapped so we can call
		// provideEntityType()
		return provideEntityType(jpaEntityType);
	}

	// @Override
	// public EntityType provide(IdentifiableType<?> jpaEntityType) throws
	// Exception {
	// String entityJavaTypeClassName =
	// jpaEntityType.getJavaType().getSimpleName();
	// logger.debug("providing type for (" + entityJavaTypeClassName + ")");
	//
	// EntityType newEntityType = null;
	//
	// Long classCount = null;
	// try {
	// classCount = countByClass(jpaEntityType.getJavaType());
	// } catch (Exception e) {
	// logger.debug("Entity (" + entityJavaTypeClassName +
	// ") EntityType class count failed.");
	// e.printStackTrace();
	// return null;
	// }
	//
	// if (classCount == 1) {
	// logger.debug("Entity (" + entityJavaTypeClassName +
	// ") corresponds to an existing EntityType.");
	// newEntityType = getByClass(jpaEntityType.getJavaType());
	// } else if (classCount == 0) {
	// logger.debug("Entity (" + entityJavaTypeClassName +
	// ") has no EntityType yet.");
	// javax.persistence.metamodel.ManagedType<?> managedType = null;
	//
	// try {
	// managedType =
	// em.getEntityManagerFactory().getMetamodel().managedType((Class<?>)
	// jpaEntityType.getJavaType());
	// } catch (IllegalArgumentException e) {
	// logger.debug("Entity (" + jpaEntityType.getJavaType() +
	// ") is not a managed type.");
	// e.printStackTrace();
	// return null;
	// }
	//
	// EntityType mappedSuperClassEntityType = null;
	// if (managedType != null) {
	// logger.debug("Entity (" + jpaEntityType.getJavaType() +
	// ") is a managed type.");
	// // The Metamodel of jpaEntityType exists
	// if (managedType instanceof javax.persistence.metamodel.EntityType) {
	// // The Metamodel corresponding to jpaEntityType is of type
	// // Entity
	// newEntityType = new
	// EntityType(((javax.persistence.metamodel.EntityType<?>)
	// managedType).getName(), managedType.getJavaType(),
	// mappedSuperClassEntityType, null, null, managedType
	// .getJavaType().getName());
	// // Create the new Entity Type
	// newEntityType = add(newEntityType);
	//
	// if (((javax.persistence.metamodel.EntityType<?>)
	// managedType).getSupertype() != null) {
	// // Why are we using create mapped super class if
	// // provide(Class<?>) does the same thing?
	// // mappedSuperClassEntityType =
	// // createMappedSupperClass(((javax.persistence.metamodel.EntityType<?>)
	// // managedType).getSupertype());
	// mappedSuperClassEntityType =
	// provide(((javax.persistence.metamodel.EntityType<?>)
	// managedType).getSupertype());
	// logger.debug("[" + entityJavaTypeClassName +
	// "] Provided for Super EntityType (" +
	// mappedSuperClassEntityType.getEntityJavaSimpleType() + ")");
	// newEntityType.setSuperType(mappedSuperClassEntityType);
	// newEntityType = update(newEntityType);
	// }
	//
	// } else if (managedType instanceof MappedSuperclassType) {
	// // The Metamodel corresponding to jpaEntityType is of type
	// // Mapped Super Class
	// newEntityType = new
	// EntityType(((javax.persistence.metamodel.EntityType<?>)
	// managedType).getName(), managedType.getJavaType(), null, null, null,
	// managedType.getJavaType()
	// .getName());
	// // Create the new Entity Type
	// newEntityType = add(newEntityType);
	// if (((MappedSuperclassType<?>) managedType).getSupertype() != null) {
	// // Why are we using create mapped super class if
	// // provide(Class<?>) does the same thing?
	// // mappedSuperClassEntityType =
	// // createMappedSupperClass((((MappedSuperclassType<?>)
	// // managedType).getSupertype()));
	// mappedSuperClassEntityType = provide(((MappedSuperclassType<?>)
	// managedType).getSupertype());
	// logger.debug("[" + entityJavaTypeClassName +
	// "] Provided for Super EntityType (" +
	// mappedSuperClassEntityType.getEntityJavaSimpleType() + ")");
	// newEntityType.setSuperType(mappedSuperClassEntityType);
	// newEntityType = update(newEntityType);
	// }
	// }
	//
	// newEntityType = updateEntityTypeAttributes(newEntityType,
	// mappedSuperClassEntityType, (IdentifiableType<?>) managedType);
	// } else {
	// logger.debug("Entity (" + jpaEntityType.getJavaType() +
	// ") is not a managed type.");
	// // This entity is not managed
	// newEntityType = getByClass(jpaEntityType.getJavaType());
	// if (newEntityType == null) {
	// if (jpaEntityType.getSupertype() != null)
	// mappedSuperClassEntityType =
	// createMappedSupperClass(jpaEntityType.getSupertype());
	//
	// newEntityType = new EntityType(jpaEntityType.getJavaType().getName(),
	// jpaEntityType.getJavaType(), mappedSuperClassEntityType, null,//
	// SingularAttribute
	// // id,
	// null,// SingularAttribute version,
	// jpaEntityType.getJavaType().getName());
	//
	// // Save
	// newEntityType = add(newEntityType);
	//
	// newEntityType = updateEntityTypeAttributes(newEntityType,
	// mappedSuperClassEntityType, jpaEntityType);
	// }
	// }
	// } else {
	// throw new Exception("There were more than one (" + classCount +
	// ") EntityTypes of the class " + jpaEntityType.getJavaType().getName());
	// }
	//
	// return newEntityType;
	// }

	// private EntityType updateEntityTypeAttributes(EntityType
	// targetEntityType, EntityType superTargetEntityType, IdentifiableType
	// jpaEntityType) throws Exception {
	// // Process attributes
	// String entity = targetEntityType.getEntityJavaSimpleType();
	// logger.debug("Updating entity type attributes for " +
	// jpaEntityType.getJavaType().getName() + ":");
	// logger.debug("\tUpdating entity type " +
	// jpaEntityType.getDeclaredSingularAttributes().size() +
	// "singular attributes for " + jpaEntityType.getJavaType().getName());
	// logger.debug("\tUpdating entity type " +
	// jpaEntityType.getDeclaredPluralAttributes().size() +
	// "plural attributes for " + jpaEntityType.getJavaType().getName());
	//
	// Set<SingularAttribute<?, ?>> sattrs =
	// jpaEntityType.getDeclaredSingularAttributes();
	// Set<PluralAttribute<?, ?, ?>> pattrs =
	// jpaEntityType.getDeclaredPluralAttributes();
	//
	// PersistentAttributeType persistenceAttributeType;
	// EntityType entityType;
	// org.flowframe.kernel.common.mdm.domain.metamodel.AbstractAttribute attribute;
	// EntityType attributeEntityType;
	// for (SingularAttribute sattr : sattrs) {
	// attribute = null;
	// persistenceAttributeType = sattr.getPersistentAttributeType();
	// if (persistenceAttributeType == PersistentAttributeType.BASIC) {
	// // This attribute is not an Entity, it is basic (String, int,
	// // double etc.)
	// attribute = new
	// org.flowframe.kernel.common.mdm.domain.metamodel.BasicAttribute(sattr.isId(),
	// sattr.getName(), sattr.getJavaType(), targetEntityType);
	// attribute.setParentEntityType(targetEntityType);
	// attribute = add(attribute);
	// System.out.println("[" + entity + "] Adding BASIC attr (" +
	// attribute.getName() + ")");
	// targetEntityType.getDeclaredAttributes().add(new
	// EntityTypeAttribute(targetEntityType, (BasicAttribute) attribute));
	// } else if ((persistenceAttributeType ==
	// PersistentAttributeType.ONE_TO_ONE) || (persistenceAttributeType ==
	// PersistentAttributeType.MANY_TO_ONE)) {
	// attributeEntityType = provide((IdentifiableType) sattr.getType());
	// attribute = new
	// org.flowframe.kernel.common.mdm.domain.metamodel.SingularAttribute(sattr.getName(),
	// sattr.getJavaType(), sattr.isId(), sattr.isVersion(), sattr.isOptional(),
	// persistenceAttributeType, attributeEntityType);
	// attribute.setParentEntityType(targetEntityType);
	// attribute = add(attribute);
	// System.out.println("[" + entity +
	// "] Adding ONE_TO_ONE/MANY_TO_ONE attr (" + attribute.getName() + ")");
	// targetEntityType.getDeclaredAttributes().add(new
	// EntityTypeAttribute(targetEntityType,
	// (org.flowframe.kernel.common.mdm.domain.metamodel.SingularAttribute) attribute));
	// }
	//
	// }
	//
	// org.flowframe.kernel.common.mdm.domain.metamodel.PluralAttribute pattr_;
	// for (PluralAttribute pattr : pattrs) {
	// pattr_ = null;
	// persistenceAttributeType = pattr.getPersistentAttributeType();
	// if (persistenceAttributeType == PersistentAttributeType.ONE_TO_MANY ||
	// persistenceAttributeType == PersistentAttributeType.MANY_TO_MANY) {
	// entityType = provide((IdentifiableType) pattr.getElementType());
	// if (pattr.getCollectionType() == CollectionType.LIST) {
	// pattr_ = new ListAttribute(pattr.getName(), entityType, targetEntityType,
	// persistenceAttributeType);
	// pattr_ = (org.flowframe.kernel.common.mdm.domain.metamodel.PluralAttribute)
	// add(pattr_);
	// System.out.println("[" + entity + "] Adding LIST attr (" +
	// pattr_.getName() + ")");
	// targetEntityType.getDeclaredAttributes().add(new
	// EntityTypeAttribute(targetEntityType, pattr_));
	// } else if (pattr.getCollectionType() == CollectionType.MAP) {
	// pattr_ = new MapAttribute(pattr.getName(), entityType, targetEntityType,
	// persistenceAttributeType);
	// pattr_ = (org.flowframe.kernel.common.mdm.domain.metamodel.PluralAttribute)
	// add(pattr_);
	// System.out.println("[" + entity + "] Adding MAP attr (" +
	// pattr_.getName() + ")");
	// targetEntityType.getDeclaredAttributes().add(new
	// EntityTypeAttribute(targetEntityType, pattr_));
	// } else if (pattr.getCollectionType() == CollectionType.SET) {
	// pattr_ = new SetAttribute(pattr.getName(), entityType, targetEntityType,
	// persistenceAttributeType);
	// pattr_ = (org.flowframe.kernel.common.mdm.domain.metamodel.PluralAttribute)
	// add(pattr_);
	// System.out.println("[" + entity + "] Adding SET attr (" +
	// pattr_.getName() + ")");
	// targetEntityType.getDeclaredAttributes().add(new
	// EntityTypeAttribute(targetEntityType, pattr_));
	// }
	// }
	// }
	//
	// if (superTargetEntityType != null) {
	// Set<EntityTypeAttribute> ssAttrs =
	// superTargetEntityType.getDeclaredAttributes();
	// for (EntityTypeAttribute ssAttr : ssAttrs) {
	// System.out.println("[" + entity + "] Adding SuperClass attr (" +
	// ssAttr.getAttribute().getName() + ") for EntityType(" +
	// targetEntityType.getJpaEntityName() + ")");
	// targetEntityType.getDeclaredAttributes().add(new
	// EntityTypeAttribute(targetEntityType, ssAttr.getAttribute()));
	// }
	// }
	//
	// targetEntityType = add(targetEntityType);
	//
	// return targetEntityType;
	// }

	// @Override
	// public EntityType createMappedSupperClass(IdentifiableType
	// mappedSuperType) throws Exception {
	// EntityType record = null;
	// logger.debug("providing SuperMappedClass[" +
	// mappedSuperType.getJavaType().getSimpleName() + "] ...");
	//
	// long count = countByClass(mappedSuperType.getJavaType());
	// if (count == 0) {
	// // SuperType
	// IdentifiableType<?> sst = mappedSuperType.getSupertype();
	// EntityType sst_ = null;
	// if (sst != null) {
	// sst_ = provide(sst);
	// System.out.println("[" + mappedSuperType.getJavaType().getSimpleName() +
	// "] Provided for Super EntityType (" + sst_.getEntityJavaSimpleType() +
	// ")");
	// }
	//
	// record = new EntityType(mappedSuperType.getJavaType().getName(),
	// mappedSuperType.getJavaType(), sst_, null,// SingularAttribute
	// // id,
	// null,// SingularAttribute version,
	// mappedSuperType.getJavaType().getName());
	//
	// // Save
	// record = add(record);
	//
	// record = updateEntityTypeAttributes(record, sst_, mappedSuperType);
	// }
	//
	// return record;
	// }

	@Override
	public EntityType createMappedSupperClass(@SuppressWarnings("rawtypes") IdentifiableType mappedSuperType) throws Exception {
		return null;
	}

	@Override
	public AbstractAttribute getAttribute(Long entityTypeId, String name) {
		AbstractAttribute record = null;

		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<AbstractAttribute> query = builder.createQuery(AbstractAttribute.class);
			Root<AbstractAttribute> rootEntity = query.from(AbstractAttribute.class);
			ParameterExpression<String> p = builder.parameter(String.class);
			query.select(rootEntity).where(builder.equal(rootEntity.get("name"), p));

			TypedQuery<AbstractAttribute> typedQuery = em.createQuery(query);
			typedQuery.setParameter(p, name);

			return typedQuery.getSingleResult();
		} catch (NoResultException e) {
		}

		return record;
	}
}
