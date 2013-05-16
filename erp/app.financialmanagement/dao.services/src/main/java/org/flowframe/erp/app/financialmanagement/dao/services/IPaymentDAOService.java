package org.flowframe.erp.app.financialmanagement.dao.services;

import java.util.List;

import org.flowframe.erp.app.financialmanagement.domain.payment.Payment;


public interface IPaymentDAOService {
	public Payment get(long id);
	
	public List<Payment> getAll();
	
	public Payment getByCode(String code);	

	public Payment add(Payment record);

	public void delete(Payment record);

	public Payment update(Payment record);
	
	public Payment provide(Payment record);
}
