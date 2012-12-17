package com.conx.logistics.kernel.bpm.impl.jbpm.bam;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.NotSupportedException;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.drools.runtime.Environment;
import org.drools.runtime.EnvironmentName;
import org.jbpm.process.audit.NodeInstanceLog;
import org.jbpm.process.audit.ProcessInstanceLog;
import org.jbpm.process.audit.VariableInstanceLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jndi.JndiTemplate;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * This class is essentially a very simple implementation of a service
 * that deals with ProcessInstanceLog entities. 
 * </p>
 * Please see the public methods for the interface of this service. 
 * </p>
 * Similar to the "ProcessInstanceDbLog", the idea here is that we 
 * have a entity manager factory (similar to a session factory) that
 * we repeatedly use to generate an entity manager (which is a persistence context)
 * for the specific service command. 
 * </p>
 * While ProcessInstanceLog entities do not contain LOB's (which sometimes
 * necessitate the use of tx's even in <i>read</i> situations, we use
 * transactions here none-the-less, just to be safe. Obviously, if 
 * there is already a running transaction present, we don't do anything
 * to it. 
 * </p>
 * At the end of every command, we make sure to close the entity manager
 * we've been using -- which also means that we detach any entities that
 * might be associated with the entity manager/persistence context. 
 * After all, this is a <i>service</i> which means our philosophy here 
 * is to provide a real interface, and not a leaky one. 
 */
public class CustomJPAProcessInstanceDbLog {

    private static Logger logger = LoggerFactory.getLogger(CustomJPAProcessInstanceDbLog.class);
	private EntityManagerFactory emf;
	private JndiTemplate jndiTemplate;
	private UserTransaction userTransaction;
    

    public CustomJPAProcessInstanceDbLog(EntityManagerFactory emf, UserTransaction userTransaction) {
    	this.emf = emf;
    	this.userTransaction = userTransaction;
    }


    public void og() { 
        
    }
    
    @SuppressWarnings("unchecked")
    public List<ProcessInstanceLog> findProcessInstances() {
        EntityManager em = getEntityManager();
        UserTransaction ut = joinTransaction(em);
        List<ProcessInstanceLog> result = em.createQuery("FROM ProcessInstanceLog").getResultList();
        closeEntityManager(em, ut);
        return result;
    }

    @SuppressWarnings("unchecked")
    public List<ProcessInstanceLog> findProcessInstances(String processId) {
        EntityManager em = getEntityManager();
        UserTransaction ut = joinTransaction(em);
        List<ProcessInstanceLog> result = em
            .createQuery("FROM ProcessInstanceLog p WHERE p.processId = :processId")
                .setParameter("processId", processId).getResultList();
        closeEntityManager(em, ut);
        return result;
    }

    @SuppressWarnings("unchecked")
    public List<ProcessInstanceLog> findActiveProcessInstances(String processId) {
        EntityManager em = getEntityManager();
        UserTransaction ut = joinTransaction(em);
        List<ProcessInstanceLog> result = getEntityManager()
            .createQuery("FROM ProcessInstanceLog p WHERE p.processId = :processId AND p.end is null")
                .setParameter("processId", processId).getResultList();
        closeEntityManager(em, ut);
        return result;
    }

    public ProcessInstanceLog findProcessInstance(long processInstanceId) {
        EntityManager em = getEntityManager();
        UserTransaction ut = joinTransaction(em);
        ProcessInstanceLog result = (ProcessInstanceLog) getEntityManager()
            .createQuery("FROM ProcessInstanceLog p WHERE p.processInstanceId = :processInstanceId")
                .setParameter("processInstanceId", processInstanceId).getSingleResult();
        closeEntityManager(em, ut);
        return result;
    }
    
    @SuppressWarnings("unchecked")
    public List<NodeInstanceLog> findNodeInstances(long processInstanceId) {
        EntityManager em = getEntityManager();
        UserTransaction ut = joinTransaction(em);
        List<NodeInstanceLog> result = getEntityManager()
            .createQuery("FROM NodeInstanceLog n WHERE n.processInstanceId = :processInstanceId ORDER BY date")
                .setParameter("processInstanceId", processInstanceId).getResultList();
        closeEntityManager(em, ut);
        return result;
    }

    @SuppressWarnings("unchecked")
    public List<NodeInstanceLog> findNodeInstances(long processInstanceId, String nodeId) {
        EntityManager em = getEntityManager();
        UserTransaction ut = joinTransaction(em);
        List<NodeInstanceLog> result = getEntityManager()
            .createQuery("FROM NodeInstanceLog n WHERE n.processInstanceId = :processInstanceId AND n.nodeId = :nodeId ORDER BY date")
                .setParameter("processInstanceId", processInstanceId)
                .setParameter("nodeId", nodeId).getResultList();
        closeEntityManager(em, ut);
        return result;
    }

    @SuppressWarnings("unchecked")
    public List<VariableInstanceLog> findVariableInstances(long processInstanceId) {
        EntityManager em = getEntityManager();
        UserTransaction ut = joinTransaction(em);
        List<VariableInstanceLog> result = getEntityManager()
            .createQuery("FROM VariableInstanceLog v WHERE v.processInstanceId = :processInstanceId ORDER BY date")
                .setParameter("processInstanceId", processInstanceId).getResultList();
        closeEntityManager(em, ut);
        return result;
    }

    @SuppressWarnings("unchecked")
    public List<VariableInstanceLog> findVariableInstances(long processInstanceId, String variableId) {
        EntityManager em = getEntityManager();
        UserTransaction ut = joinTransaction(em);
        List<VariableInstanceLog> result = em
            .createQuery("FROM VariableInstanceLog v WHERE v.processInstanceId = :processInstanceId AND v.variableId = :variableId ORDER BY date")
                .setParameter("processInstanceId", processInstanceId)
                .setParameter("variableId", variableId).getResultList();
        closeEntityManager(em, ut);
        return result;
    }

    @SuppressWarnings("unchecked")
    public void clear() {
            EntityManager em = getEntityManager();
            UserTransaction ut = joinTransaction(em);
            
            List<ProcessInstanceLog> processInstances = em.createQuery("FROM ProcessInstanceLog").getResultList();
            for (ProcessInstanceLog processInstance: processInstances) {
                em.remove(processInstance);
            }
            List<NodeInstanceLog> nodeInstances = em.createQuery("FROM NodeInstanceLog").getResultList();
            for (NodeInstanceLog nodeInstance: nodeInstances) {
                em.remove(nodeInstance);
            }
            List<VariableInstanceLog> variableInstances = em.createQuery("FROM VariableInstanceLog").getResultList();
            for (VariableInstanceLog variableInstance: variableInstances) {
                em.remove(variableInstance);
            }           
            closeEntityManager(em, ut);
    }

    public void dispose() {
    }
    
    @Override
    protected void finalize() throws Throwable {
        if (emf != null) {
            emf.close();
        }
    }

    /**
     * This method opens a new transaction, if none is currently running, and joins the entity manager/persistence context
     * to that transaction. 
     * @param em The entity manager we're using. 
     * @return {@link UserTransaction} If we've started a new transaction, then we return it so that it can be closed. 
     * @throws NotSupportedException 
     * @throws SystemException 
     * @throws Exception if something goes wrong. 
     */
    private UserTransaction joinTransaction(EntityManager em) {
        boolean newTx = false;
        UserTransaction ut = null;
        try { 
   		 	//Context ctx = jndiTemplate.getContext();
   		 	ut = (UserTransaction)userTransaction;        	
            if( ut.getStatus() == Status.STATUS_NO_TRANSACTION ) { 
                ut.begin();
                newTx = true;
            }
        } catch(Exception e) { 
            logger.error("Unable to find or open a transaction: " + e.getMessage());
            e.printStackTrace();
        }
        
        em.joinTransaction(); 
       
        if( newTx ) { 
            return ut;
        }
        return null;
    }

    /**
     * This method closes the entity manager and transaction. It also makes sure that any objects associated 
     * with the entity manager/persistence context are detached. 
     * </p>
     * Obviously, if the transaction returned by the {@link #joinTransaction(EntityManager)} method is null, 
     * nothing is done with the transaction parameter.
     * @param em The entity manager.
     * @param ut The (user) transaction.
     */
    private void closeEntityManager(EntityManager em, UserTransaction ut) {
/*        em.flush(); // This saves any changes made
        em.clear(); // This makes sure that any returned entities are no longer attached to this entity manager/persistence context
        em.close(); // and this closes the entity manager
        try { 
            if( ut != null ) { 
                // There's a tx running, close it.
                ut.commit();
            }
        } catch(Exception e) { 
            logger.error("Unable to commit transaction: " + e.getMessage());
            e.printStackTrace();
        }*/
    }

    /**
     * This method creates a entity manager. If an environment has already been 
     * provided, we use the entity manager factory present in the environment. 
     * </p> 
     * Otherwise, we assume that the persistence unit is called "org.jbpm.persistence.jpa"
     * and use that to build the entity manager factory. 
     * @return an entity manager
     */
    private EntityManager getEntityManager() {
    	return emf.createEntityManager(); 
    }

}
