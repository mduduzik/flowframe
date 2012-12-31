package org.flowframe.ds.dao.services;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.flowframe.ds.domain.DataSource;
import org.flowframe.ds.domain.DataSourceField;
import org.flowframe.kernel.common.mdm.domain.metamodel.EntityType;
import org.flowframe.kernel.common.mdm.domain.metamodel.EntityTypeAttribute;

public interface IDataSourceDAOService {
	public DataSource get(long id);

	public DataSource getByCode(String code);

	public List<DataSource> getAll();

	public DataSource getByEntityType(EntityType entityType);

	public DataSourceField getField(Long DataSourceId, String name);

	public DataSource addField(Long DataSourceId, DataSourceField field);

	public DataSource addFields(Long DataSourceId, List<DataSourceField> fields);

	public DataSource deleteField(Long DataSourceId, DataSourceField field);

	public DataSource deleteFields(Long DataSourceId,
			List<DataSourceField> fields);

	public DataSource add(DataSource record);

	public DataSource provide(EntityType entityType)
			throws ClassNotFoundException;

	public DataSource provideCustomDataSource(String name,
			EntityType entityType, Collection<String> inherittedFieldNames)
			throws Exception;

	public DataSource findCustomDataSource(String name, EntityType entityType)
			throws Exception;

	public void delete(DataSource record);

	public DataSource update(DataSource record);

	public List<DataSourceField> getFields(DataSource parentDataSource);

	public DataSourceField provideDataSourceField(DataSource targetDataSource,
			EntityTypeAttribute aattr) throws ClassNotFoundException;

	public DataSourceField getFieldByName(DataSource parentDataSource,
			String attrName);

	public DataSourceField provideDataSourceFieldByAttrName(
			DataSource targetDataSource, String aattrName)
			throws ClassNotFoundException;

	public DataSource addField(DataSource record, DataSourceField dsf);

	public DataSource addFields(DataSource record, Set<DataSourceField> dsfs);

	public DataSourceField update(DataSourceField record);
}
