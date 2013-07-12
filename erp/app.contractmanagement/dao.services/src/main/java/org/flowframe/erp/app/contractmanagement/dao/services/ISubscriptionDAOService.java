package org.flowframe.erp.app.contractmanagement.dao.services;

import java.util.List;

import org.flowframe.erp.app.contractmanagement.domain.Subscription;
import org.flowframe.erp.app.contractmanagement.domain.SubscriptionPlan;


public interface ISubscriptionDAOService {
	
	final static public String FREE_TRIAL_PLAN_NAME ="FF Free Plan - $0";
	final static public String BASIC_PLAN_NAME ="FF Basic Plan";
	final static public String PROF_PLAN_NAME ="FF Professional Plan";
	
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
	
	public List<SubscriptionPlan> getAllPlansAvailableForSubscriptionByUserEmail(String emailAddress);
	
	public SubscriptionPlan getCurrentSubscriptionbByUserEmail(String emailAddress);
	
	public SubscriptionPlan getPlanByCode(String code);	
	
	public SubscriptionPlan getPlanByExternalRefId(String externalRefId);

	public SubscriptionPlan addPlan(SubscriptionPlan record);

	public void deletePlan(SubscriptionPlan record);

	public SubscriptionPlan updatePlan(SubscriptionPlan record);
	
	public SubscriptionPlan providePlan(SubscriptionPlan record);	
	
	public SubscriptionPlan getFreePlan();

	public Long getPlanIdByCustomerId(Long customerId);
}
