package org.flowframe.erp.app.contractmanagement.dao.services;

import java.util.List;

import org.flowframe.erp.app.contractmanagement.domain.Subscription;
import org.flowframe.erp.app.contractmanagement.domain.SubscriptionPlan;


public interface ISubscriptionDAOService {
	
	final static public String FREE_TRIAL_PLAN_NAME ="FF Free Plan - $0";
	
	public Subscription get(long id);
	
	public List<Subscription> getAll();
	
	public Subscription getByCode(String code);	
	
	public Subscription getByPlanIdAndCustomerId(Long planId, Long customerId);
	
	public Subscription getByCustomerId(Long customerId);

	public Subscription add(Subscription record);

	public void delete(Subscription record);

	public Subscription update(Subscription record);
	
	public Subscription provide(Subscription record);
	
	
	public SubscriptionPlan getPlan(long id);
	
	public List<SubscriptionPlan> getAllPlans();
	
	public SubscriptionPlan getPlanByCode(String code);	
	
	public SubscriptionPlan getPlanByExternalRefId(String externalRefId);

	public SubscriptionPlan addPlan(SubscriptionPlan record);

	public void deletePlan(SubscriptionPlan record);

	public SubscriptionPlan updatePlan(SubscriptionPlan record);
	
	public SubscriptionPlan providePlan(SubscriptionPlan record);	
	
	public SubscriptionPlan getFreePlan();

	public Long getPlanIdByCustomerId(Long customerId);
}
