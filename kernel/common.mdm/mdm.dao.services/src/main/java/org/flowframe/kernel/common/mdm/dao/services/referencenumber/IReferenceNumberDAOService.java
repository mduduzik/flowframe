package org.flowframe.kernel.common.mdm.dao.services.referencenumber;

import java.util.List;

import org.flowframe.kernel.common.mdm.domain.BaseEntity;
import org.flowframe.kernel.common.mdm.domain.referencenumber.ReferenceNumber;

public interface IReferenceNumberDAOService {
	public ReferenceNumber get(long id);
	
	public List<ReferenceNumber> getAll();
	
	public ReferenceNumber getByCode(String code);	

	public ReferenceNumber add(ReferenceNumber record);
	
	public ReferenceNumber add(Long parentEntityPK, Class<?> parentEntityType);

	public void delete(ReferenceNumber record);

	public ReferenceNumber update(ReferenceNumber record);
}
