package org.flowframe.bpm.jbpm.taskserver;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

import org.drools.SystemEventListenerFactory;
import org.flowframe.bpm.jbpm.services.IBPMTaskService;
import org.flowframe.portal.remote.services.IPortalUserService;
import org.jbpm.task.Group;
import org.jbpm.task.User;
import org.jbpm.task.service.JTACustomTaskService;
import org.jbpm.task.service.TaskService;
import org.jbpm.task.service.TaskServiceSession;
import org.jbpm.task.service.local.LocalTaskService;
import org.jbpm.task.service.mina.MinaTaskServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jndi.JndiTemplate;
import org.springframework.transaction.PlatformTransactionManager;

public class HumanTaskServer implements IBPMTaskService {
	private static Logger logger = LoggerFactory
			.getLogger(HumanTaskServer.class);

	private MinaTaskServer minaServer;
	private Thread minaServerThread;

	private EntityManagerFactory emfOrgJbpmTask;
	private PlatformTransactionManager globalJTATransManager;
	private TransactionManager globalTransactionManager;
	private JndiTemplate jndiTemplate;
	private UserTransaction userTransaction;

	private LocalTaskService localTaskService;
	private JTACustomTaskService taskService;
	
	
	private IPortalUserService portalUserService;

	public void stop() {
		minaServer.stop();
	}

	public void start() throws NamingException, IllegalStateException,
			SecurityException, SystemException {

		// -- Submit to repository
		// emfOrgJbpmTask =
		// Persistence.createEntityManagerFactory("org.jbpm.task");
		//Context ctx = jndiTemplate.getContext();
		//UserTransaction ut = (UserTransaction) ctx
		//		.lookup("java:comp/UserTransaction");

		try {

			//ut.begin();

			taskService = new JTACustomTaskService(globalTransactionManager,
					jndiTemplate, userTransaction,emfOrgJbpmTask,
					SystemEventListenerFactory.getSystemEventListener());
			localTaskService = new LocalTaskService(taskService);

			//-- Add users and groups
			addUsersAndGroups();		
			
			// start server
			minaServer = new MinaTaskServer(taskService);
			minaServerThread = new Thread(minaServer);
			minaServerThread.start();		
			
			// kernelSystemBPMTransManager.commit(status);
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
			//ut.rollback();
			// kernelSystemBPMTransManager.rollback(status);
		}

		logger.debug("Task service started correctly !");
		logger.debug("Task service running ...");
	}

	@SuppressWarnings("rawtypes")
	public static Object eval(Reader reader, Map vars) {
		return TaskService.eval(reader, vars);
	}

	public static String readerToString(Reader reader) throws IOException {
		int charValue = 0;
		StringBuffer sb = new StringBuffer(1024);
		while ((charValue = reader.read()) != -1) {
			// result = result + (char) charValue;
			sb.append((char) charValue);
		}
		return sb.toString();
	}

	public void setJbpmTaskEMF(EntityManagerFactory emfOrgJbpmTask) {
		this.emfOrgJbpmTask = emfOrgJbpmTask;
	}

	public void setGlobalJTATransManager(
			PlatformTransactionManager kernelSystemBPMTransManager) {
		this.globalJTATransManager = kernelSystemBPMTransManager;
	}

	public void setGlobalTransactionManager(
			TransactionManager globalTransactionManager) {
		this.globalTransactionManager = globalTransactionManager;
	}

	public void setJndiTemplate(JndiTemplate jndiTemplate) {
		this.jndiTemplate = jndiTemplate;
	}

	public void setUserTransaction(UserTransaction userTransaction) {
		this.userTransaction = userTransaction;
	}
	
	public TaskService createHumanTaskService() {
		TaskService taskService = new TaskService(emfOrgJbpmTask,
				SystemEventListenerFactory.getSystemEventListener());
		return taskService;
	}

	@Override
	public LocalTaskService getLocalTaskService() {
		return localTaskService;
	}

	@Override
	public TaskService getTaskService() {
		return taskService;
	}
	
	
	
	public IPortalUserService getPortalUserService() {
		return portalUserService;
	}

	public void setPortalUserService(IPortalUserService portalUserService) {
		this.portalUserService = portalUserService;
	}

	private void addUsersAndGroups() throws NamingException
	{
		 Context ctx = jndiTemplate.getContext();
		 UserTransaction ut = (UserTransaction)ctx.lookup( "java:comp/UserTransaction" );

		 try
		 {
			ut.begin();
			
			TaskServiceSession ts = taskService.createSession();
			
			// Add users
			Map vars = new HashMap();
			Reader reader;
			URL usersURL;
			boolean adminUserProvided = false;
			try {
				List<org.flowframe.kernel.common.mdm.domain.user.User> portalusers = portalUserService.getUsersByDefaultCompany();
				for (org.flowframe.kernel.common.mdm.domain.user.User user : portalusers)
				{
					String screenName = user.getScreenName();
					if ("Administrator".equalsIgnoreCase(screenName))
						adminUserProvided = true;
					ts.addUser(new User(user.getScreenName()));
				}
				
				// Also provide 'Administrator'
				if (!adminUserProvided)
					ts.addUser(new User("Administrator"));
			} catch (Exception e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				String stacktrace = sw.toString();
				logger.error(stacktrace);
			}
			

			URL grpsURL = null;
			
			try {
				grpsURL = HumanTaskServer.class.getClassLoader().getResource(
						"LoadGroups.mvel");
				reader = new FileReader(new File(grpsURL.toURI()));
			} catch (IllegalArgumentException e) {
				grpsURL = HumanTaskServer.class.getClassLoader().getResource(
						"/LoadGroups.mvel");
				File file = new File(Thread.currentThread().getContextClassLoader().getResource("/LoadGroups.mvel").getFile());
				reader = new FileReader(file);				
			}

			Map<String, Group> groups = (Map<String, Group>) eval(reader, vars);
			for (Group group : groups.values()) {
				ts.addGroup(group);
			}
			
			
			ut.commit();
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
		}				
	}	
}
