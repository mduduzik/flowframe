package org.flowframe.ds.dao.services.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Type.PersistenceType;

import org.flowframe.ds.dao.services.IDataSourceDAOService;
import org.flowframe.ds.domain.DataSource;
import org.flowframe.ds.domain.DataSourceField;
import org.flowframe.kernel.common.mdm.domain.metamodel.AbstractAttribute;
import org.flowframe.kernel.common.mdm.domain.metamodel.BasicAttribute;
import org.flowframe.kernel.common.mdm.domain.metamodel.BasicType;
import org.flowframe.kernel.common.mdm.domain.metamodel.EntityType;
import org.flowframe.kernel.common.mdm.domain.metamodel.EntityTypeAttribute;
import org.flowframe.kernel.metamodel.dao.services.IBasicTypeDAOService;
import org.flowframe.kernel.metamodel.dao.services.IEntityTypeDAOService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of {@link DataSource} that uses JPA for persistence.
 * <p />
 * <p/>
 * This class is marked as {@link Transactional}. The Spring configuration for
 * this module, enables AspectJ weaving for adding transaction demarcation to
 * classes annotated with <code>@Transactional</code>.
 */
@Transactional
@Repository
public class DataSourceDAOImpl implements IDataSourceDAOService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Spring will inject a managed JPA {@link EntityManager} into this field.
	 */
	@PersistenceContext
	private EntityManager em;

	@Autowired
	private IEntityTypeDAOService entityTypeDao;

	@Autowired
	private IBasicTypeDAOService basicTypeDao;

	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public DataSource get(long id) {
		return em.getReference(DataSource.class, id);
	}

	@Override
	public List<DataSource> getAll() {
		return em.createQuery("select o from org.flowframe.ds.domain.DataSource o record by o.id", DataSource.class)
				.getResultList();
	}

	@Override
	public List<DataSourceField> getFields(DataSource parentDataSource) {
		TypedQuery<DataSourceField> q = em.createQuery(
				"select o from org.flowframe.ds.domain.DataSourceField o WHERE o.parentDataSource = :parentDataSource",
				DataSourceField.class);
		q.setParameter("parentDataSource", parentDataSource);
		return q.getResultList();
	}

	@Override
	public DataSourceField getFieldByName(DataSource parentDataSource, String attrName) {
		// DataSourceField dsf = null;
		// try
		// {
		// TypedQuery<DataSourceField> q =
		// em.createQuery("select o from org.flowframe.ds.domain.DataSourceField o WHERE o.parentDataSource = :parentDataSource AND o.name = :name",
		// DataSourceField.class);
		// q.setParameter("parentDataSource", parentDataSource);
		// q.setParameter("name", attrName);
		//
		// dsf = q.getSingleResult();
		// }
		// catch(NoResultException e){}
		//
		// return dsf;
		DataSourceField ds = null;

		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<DataSourceField> query = builder.createQuery(DataSourceField.class);
			Root<DataSourceField> rootEntity = query.from(DataSourceField.class);
			ParameterExpression<String> p = builder.parameter(String.class);
			ParameterExpression<DataSource> p2 = builder.parameter(DataSource.class);
			query.select(rootEntity).where(
					builder.and(builder.equal(rootEntity.get("name"), p), builder.equal(rootEntity.get("parentDataSource"), p2)));

			TypedQuery<DataSourceField> typedQuery = em.createQuery(query);
			typedQuery.setParameter(p, attrName);
			typedQuery.setParameter(p2, parentDataSource);

			ds = typedQuery.getSingleResult();
		} catch (NoResultException e) {
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Error e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
		}

		return ds;
	}

	@Override
	public DataSource add(DataSource record) {
		record = em.merge(record);

		return record;
	}

	@Override
	public DataSource addField(DataSource record, DataSourceField dsf) {
		record = em.merge(record);
		dsf.setParentDataSource(record);
		record.getDSFields().add(dsf);

		return em.merge(record);
	}

	@Override
	public DataSource addFields(DataSource record, Set<DataSourceField> dsfs) {
		record = em.merge(record);
		for (DataSourceField dsf : dsfs) {
			dsf.setParentDataSource(record);
			dsf = em.merge(dsf);
		}
		record.getDSFields().addAll(dsfs);
		record = em.merge(record);

		return em.merge(record);
	}

	@Override
	public DataSource provide(EntityType entityType) throws ClassNotFoundException {
		logger.debug("START providing datasource for " + entityType.getJavaType().getName());

		DataSource targetDS = null;
		String simpleType = entityType.getJavaType().getSimpleName();
		simpleType = simpleType.substring(0, 1).toLowerCase() + simpleType.substring(1);

		targetDS = getByCode(simpleType);
		if (targetDS == null) {
			// This DataSource does not yet exist
			targetDS = new DataSource(simpleType, entityType);
			targetDS = em.merge(targetDS);
			// Providing the Super DataSource if it exists
			if (entityType.getSuperType() instanceof EntityType) {
				targetDS.setSuperDataSource(provide((EntityType) entityType.getSuperType()));
			}
			// Adding Data Source Fields
			Set<EntityTypeAttribute> aattrs = new HashSet<EntityTypeAttribute>(targetDS.getEntityType().getDeclaredAttributes());
			try {
				DataSourceField entityDSField = null;
				for (EntityTypeAttribute aattr : aattrs) {
					entityDSField = provideDataSourceField(targetDS, aattr);
					targetDS.getDSFields().add(entityDSField);
				}
			} catch (ConcurrentModificationException e) {
				logger.error("Could not finish provide() for DataSource of type " + entityType.getJavaType().getName() + ".");
			}

			targetDS = em.merge(targetDS);
		}

		logger.debug("FINISH providing datasource for " + entityType.getJavaType().getName());
		return targetDS;
	}

	@Override
	@SuppressWarnings("unused")
	public DataSource provideCustomDataSource(String name, EntityType entityType, Collection<String> inherittedFieldNames) throws Exception {
		DataSource existingDataSource = findCustomDataSource(name, entityType);
		if (existingDataSource != null) {
			return existingDataSource;
		}
		// First clone the default DataSource
		DataSource targetDS = provide(entityType), customDataSource = new DataSource(name + "-" + targetDS.getCode(), entityType);
		if (targetDS != null) {
			customDataSource.setSuperDataSource(targetDS);
			customDataSource = em.merge(customDataSource);
			// Add all the DataSourceFields according to inherittedFieldNames
			DataSourceField inherittedField = null;
			Map<String, DataSourceField> fieldMap = targetDS.provideSuperDataSourceFieldMap();
			for (String inherittedFieldName : inherittedFieldNames) {
				inherittedField = fieldMap.get(inherittedFieldName);
				if (inherittedField != null) {
					customDataSource.getDSFields().add(cloneDataSourceField(customDataSource, inherittedField));
				}
			}
			// Save added DataSourceFields
			customDataSource = em.merge(customDataSource);
			// Created custom DataSource is returned
			return customDataSource;
		} else {
			return null;
		}
	}

	private DataSourceField cloneDataSourceField(DataSource targetDataSource, DataSourceField inherittedField) {
		DataSourceField clonedField = new DataSourceField(inherittedField.getPrimaryKey(), inherittedField.getName(), targetDataSource,
				inherittedField.getDataType(), inherittedField.getTitle());
		clonedField = em.merge(clonedField);
		return clonedField;
	}

	@Override
	public DataSource getByCode(String code) {
		DataSource ds = null;

		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<DataSource> query = builder.createQuery(DataSource.class);
			Root<DataSource> rootEntity = query.from(DataSource.class);
			ParameterExpression<String> p = builder.parameter(String.class);
			query.select(rootEntity).where(builder.equal(rootEntity.get("code"), p));

			TypedQuery<DataSource> typedQuery = em.createQuery(query);
			typedQuery.setParameter(p, code);

			ds = typedQuery.getSingleResult();
		} catch (NoResultException e) {
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Error e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
		}

		return ds;
	}

	@Override
	public DataSourceField provideDataSourceField(DataSource targetDataSource, EntityTypeAttribute aattr) throws ClassNotFoundException {
		DataSourceField entityDSField = null;
		DataSource attrDataSource;
		PersistenceType pt;
		pt = aattr.getAttribute().getPersistenceType();
		logger.debug("START providing datasourcefield for " + aattr.getAttribute().getName());
		if (getFieldByName(targetDataSource, aattr.getAttribute().getName()) == null) {
			if (pt == PersistenceType.BASIC) {
				// - provide BasicType
				BasicType basicType = basicTypeDao.provide(aattr.getAttribute().getJavaType());

				// - create DataSourceField
				entityDSField = new DataSourceField(((BasicAttribute) aattr.getAttribute()).isId(), aattr.getAttribute().getName(),
						targetDataSource, basicType, humanifyFieldName(aattr.getAttribute().getName()));
				entityDSField = em.merge(entityDSField);

				logger.debug("Adding Basic-type DataSourceField (" + aattr.getAttribute().getName() + ") of type ("
						+ basicType.getEntityJavaType() + ")");
			} else if (pt == PersistenceType.ENTITY) {
				if (aattr.getAttribute() instanceof org.flowframe.kernel.common.mdm.domain.metamodel.SingularAttribute) {
					// - provide DataSource for entity type
					EntityType entityType = aattr.getAttribute().getEntityType();
					attrDataSource = provide(aattr.getAttribute().getEntityType());

					// - create Entity attribute
					entityDSField = new DataSourceField(aattr.getAttribute().getName(), targetDataSource, attrDataSource, entityType,
							humanifyFieldName(aattr.getAttribute().getName()),
							((org.flowframe.kernel.common.mdm.domain.metamodel.SingularAttribute) aattr.getAttribute()).getAttributeType());
					entityDSField = em.merge(entityDSField);

					logger.debug("Adding Entity-type DataSourceField (" + aattr.getAttribute().getName() + ") of type ("
							+ entityType.getEntityJavaType() + ")");

				} else if (aattr.getAttribute() instanceof org.flowframe.kernel.common.mdm.domain.metamodel.PluralAttribute) {
					// - provide DataSource for entity type
					EntityType entityType = aattr.getAttribute().getEntityType();
					attrDataSource = provide(aattr.getAttribute().getEntityType());

					// - create Entity attribute
					entityDSField = new DataSourceField(aattr.getAttribute().getName(), targetDataSource, attrDataSource, entityType,
							humanifyFieldName(aattr.getAttribute().getName()),
							((org.flowframe.kernel.common.mdm.domain.metamodel.PluralAttribute) aattr.getAttribute()).getAttributeType());
					entityDSField = em.merge(entityDSField);

					logger.debug("Adding Collection Entity-type DataSourceField (" + aattr.getAttribute().getName() + ") of type ("
							+ entityType.getEntityJavaType() + ")");
				}
			}
		}

		logger.debug("FINISH providing datasourcefield for " + aattr.getAttribute().getName());
		return entityDSField;
	}

	@Override
	public DataSourceField provideDataSourceFieldByAttrName(DataSource targetDataSource, String aattrName) throws ClassNotFoundException {
		DataSourceField entityDSField = getFieldByName(targetDataSource, aattrName);

		if (entityDSField == null) {
			AbstractAttribute attr = entityTypeDao.getAttribute(targetDataSource.getEntityType().getId(), aattrName);
			entityDSField = provideDataSourceField(targetDataSource, new EntityTypeAttribute(targetDataSource.getEntityType(), attr));
		}

		return entityDSField;
	}

	@Override
	public DataSource addField(Long dataSourceId, DataSourceField field) {
		DataSource ds = em.getReference(DataSource.class, dataSourceId);
		ds.getDSFields().add(field);

		ds = em.merge(ds);

		return ds;
	}

	@Override
	public DataSource addFields(Long dataSourceId, List<DataSourceField> fields) {
		DataSource ds = em.getReference(DataSource.class, dataSourceId);
		ds.getDSFields().addAll(fields);

		ds = em.merge(ds);

		return ds;
	}

	@Override
	public DataSource deleteField(Long dataSourceId, DataSourceField field) {
		DataSource ds = em.getReference(DataSource.class, dataSourceId);
		ds.getDSFields().remove(field);
		em.remove(field);
		ds = em.merge(ds);

		return ds;
	}

	@Override
	public DataSource deleteFields(Long dataSourceId, List<DataSourceField> fields) {
		DataSource ds = em.getReference(DataSource.class, dataSourceId);
		ds.getDSFields().removeAll(fields);

		ds = em.merge(ds);

		return ds;
	}

	@Override
	public DataSourceField getField(Long dataSourceId, String name) {
		DataSource ds = em.getReference(DataSource.class, dataSourceId);
		return getFieldByName(ds, name);
	}

	@Override
	public void delete(DataSource record) {
		em.remove(record);
	}

	@Override
	public DataSource update(DataSource record) {
		return em.merge(record);
	}

	@Override
	public DataSourceField update(DataSourceField record) {
		return em.merge(record);
	}

	@Override
	public DataSource getByEntityType(EntityType entityType) {
		TypedQuery<DataSource> q = em
				.createQuery("select o from org.flowframe.ds.domain.DataSource o WHERE o.entityType = :entityType",
						DataSource.class);
		q.setParameter("entityType", entityType);
		return q.getSingleResult();
	}

	@Override
	public DataSource findCustomDataSource(String name, EntityType entityType) throws Exception {
		TypedQuery<DataSource> q = em
				.createQuery(
						"select o from org.flowframe.ds.domain.DataSource o WHERE o.entityType = :entityType AND o.code = :code",
						DataSource.class);
		q.setParameter("entityType", entityType);
		q.setParameter("code", name + "-" + entityType.getJavaType().getSimpleName());
		List<DataSource> result = q.getResultList();
		if (result.size() == 0) {
			return null;
		} else {
			return result.get(0);
		}
	}

	/**
	 * Create a human legible name out of a field name.
	 * 
	 * @param fieldName
	 *            field name to make human legible
	 * @return more friendly version of field name
	 */
	private String humanifyFieldName(String fieldName) {
		if (fieldName != null) {
			String title = "";
			String[] sections = fieldName.split("(?=\\p{Upper})");
			boolean isFirst = true;
			for (String section : sections) {
				if (!isFirst) {
					if (section.length() > 1) {
						title += " ";
					}
				}
				title += section;
				if (!"".equals(section)) {
					isFirst = false;
				}
			}
			
			if (title.length() > 1) {
				title = String.valueOf(title.charAt(0)).toUpperCase() + title.substring(1);
			}
			
			return title;
		}
		return null;
	}
}
