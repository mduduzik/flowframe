package org.flowframe.kernel.common.mdm.dao.services;

import java.util.List;

import org.flowframe.kernel.common.mdm.domain.metadata.DefaultEntityMetadata;

public interface IEntityMetadataDAOService {
	public DefaultEntityMetadata get(long id);
	
	public List<DefaultEntityMetadata> getAll();
	
	public DefaultEntityMetadata getByClass(Class entityClass);	

	public DefaultEntityMetadata add(DefaultEntityMetadata record);

	public void delete(DefaultEntityMetadata record);

	public DefaultEntityMetadata update(DefaultEntityMetadata record);
	
	public DefaultEntityMetadata provide(DefaultEntityMetadata record) throws ClassNotFoundException;
	
	public DefaultEntityMetadata provide(Class entityClass);	
}
