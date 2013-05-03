package org.flowframe.erp.app.contractmanagement.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.flowframe.kernel.common.mdm.domain.role.Role;
import org.flowframe.kernel.common.mdm.domain.user.User;

@Entity
public class CustomerUser extends User {
	@ManyToOne
	private Customer customer;
	
	@ManyToOne
	private Role role;
	

	public CustomerUser(User toCopy, Customer customer, Role role) {
		super();
		this.setUserId(toCopy.getUserId());
		this.setScreenName(toCopy.getScreenName());
		this.customer = customer;
		this.role = role;
	}

	public CustomerUser() {
		super();
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
