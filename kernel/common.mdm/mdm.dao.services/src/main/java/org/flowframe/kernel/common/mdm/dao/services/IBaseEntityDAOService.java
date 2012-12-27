package org.flowframe.kernel.common.mdm.dao.services;

import java.util.List;

import org.flowframe.kernel.common.mdm.domain.BaseEntity;

public interface IBaseEntityDAOService {
	@SuppressWarnings("rawtypes")
	public BaseEntity get(long id, Class entityClass);
	
	@SuppressWarnings("rawtypes")
	public List<?> getAll(Class entityClass);
	
	public BaseEntity getByCode(String code, @SuppressWarnings("rawtypes") Class entityClass);	

	public BaseEntity add(BaseEntity record);

	public void delete(BaseEntity record);

	public BaseEntity update(BaseEntity record);
	
	public BaseEntity provide(BaseEntity record);
}
