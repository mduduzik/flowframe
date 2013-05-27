package org.flowframe.erp.app.contractmanagement.dao.customer;

import java.util.List;

import org.flowframe.erp.app.contractmanagement.domain.Customer;

public interface ICustomerDAOService {
	public Customer get(long id);
	
	public List<Customer> getAll();

	public Customer add(Customer record);
	
	public Customer provide(Customer record);
	
	public Long getUserCustomerIdByEmailAddress(String emailAddress);
}

