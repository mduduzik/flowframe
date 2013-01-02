package org.flowframe.ui.vaadin.common.entityprovider.jta;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Util {
	protected static Logger logger = LoggerFactory.getLogger(Util.class);
	
    static EntityManager getEntityManager(EntityManagerFactory entityManagerFactory) {
        try {
            EntityManager em = entityManagerFactory.createEntityManager();
            return em;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    static void runInJTATransaction(UserTransaction utx,
            Runnable operation) {
        try {
            utx.begin();
            operation.run();
            utx.commit();
        } catch (Exception e) {
            try {
                utx.rollback();
            } catch (Exception e2) {
            	logger.error("Rollback failed", e2);
            }
            throw new RuntimeException(e);
        }
    }
}
