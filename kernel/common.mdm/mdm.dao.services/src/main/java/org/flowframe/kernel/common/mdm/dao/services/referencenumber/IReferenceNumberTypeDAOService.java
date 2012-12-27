package org.flowframe.kernel.common.mdm.dao.services.referencenumber;

import java.util.List;

import org.flowframe.kernel.common.mdm.domain.referencenumber.ReferenceNumberType;

public interface IReferenceNumberTypeDAOService {
	public ReferenceNumberType get(long id);
	
	public List<ReferenceNumberType> getAll();
	
	public ReferenceNumberType getByCode(String code);	

	public ReferenceNumberType add(ReferenceNumberType record);

	public void delete(ReferenceNumberType record);

	public ReferenceNumberType update(ReferenceNumberType record);
	
	public ReferenceNumberType provide(ReferenceNumberType record);
	
	public ReferenceNumberType provide(String code, String name);
	
	public void provideDefaults();
}
