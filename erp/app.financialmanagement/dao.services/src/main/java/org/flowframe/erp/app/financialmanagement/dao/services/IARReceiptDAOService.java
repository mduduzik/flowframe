package org.flowframe.erp.app.financialmanagement.dao.services;

import java.util.List;

import org.flowframe.erp.app.financialmanagement.domain.receivable.ARReceipt;
import org.flowframe.erp.app.financialmanagement.domain.receivable.ARReceiptLine;
import org.flowframe.kernel.common.mdm.domain.geolocation.Country;


public interface IARReceiptDAOService {
	public ARReceipt get(long id);
	
	public List<ARReceipt> getAll();
	
	public List<ARReceipt> getAllByCustomerId(Long customerId);	
	
	public List<ARReceipt> getAllByUserEmail(String emailAddress);
	
	public ARReceipt getByCode(String code);	

	public ARReceipt add(ARReceipt record);

	public void delete(ARReceipt record);

	public ARReceipt update(ARReceipt record);
	
	public ARReceipt provide(ARReceipt record);
	
	public ARReceipt provide(String code, String name, Country country);
	
	
	public ARReceiptLine getLine(long id);
	
	public ARReceiptLine addLine(Long arreceiptId, ARReceiptLine record);
}
