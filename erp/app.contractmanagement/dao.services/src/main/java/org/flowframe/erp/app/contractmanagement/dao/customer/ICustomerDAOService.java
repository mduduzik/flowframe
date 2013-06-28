package org.flowframe.erp.app.contractmanagement.dao.customer;

import java.util.List;

import org.flowframe.erp.app.contractmanagement.domain.Customer;
import org.flowframe.erp.app.contractmanagement.domain.CustomerUser;

public interface ICustomerDAOService {
	public Customer get(long id);
	
	public List<Customer> getAll();
	
	public Customer getByCode(String code);

	public Customer add(Customer record);
	
	public Customer provide(Customer record);
	
	public CustomerUser provideUser(CustomerUser record);
	
	public Long getUserCustomerIdByEmailAddress(String emailAddress);
}

