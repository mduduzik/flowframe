package org.flowframe.erp.app.contractmanagement.dao.services;

import java.util.List;

import org.flowframe.erp.app.contractmanagement.domain.Subscription;
import org.flowframe.erp.app.contractmanagement.domain.SubscriptionPlan;


public interface ISubscriptionDAOService {
	
	final static public String FREE_TRIAL_PLAN_NAME ="Free Trial Plan";
	
	public Subscription get(long id);
	
	public List<Subscription> getAll();
	
	public Subscription getByCode(String code);	

	public Subscription add(Subscription record);

	public void delete(Subscription record);

	public Subscription update(Subscription record);
	
	public Subscription provide(Subscription record);
	
	public SubscriptionPlan getFreePlan();
}
