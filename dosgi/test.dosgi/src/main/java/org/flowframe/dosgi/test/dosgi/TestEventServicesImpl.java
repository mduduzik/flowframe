package org.flowframe.dosgi.test.dosgi;

import java.util.Map;

import org.flowframe.erp.integration.adaptors.stripe.services.IEventBusinessService;
import org.flowframe.erp.integration.adaptors.stripe.services.IEventDAOService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestEventServicesImpl  {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	//@Autowired
	protected IEventDAOService eventDAOService = null;
	
	//@Autowired
	protected IEventBusinessService eventBusinessService = null;
	
	public void init(){
		try {
			logger.info("Init...");
			logger.info("About to call eventDAOService");
			if (eventDAOService != null) {
				logger.info("eventDAOService: "+eventDAOService.getAll());
			}
			else
				logger.info("eventDAOService not available");
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
/*			Runnable svc = new Runnable() {
				
				@Override
				public void run() {
					while (true) {
						logger.info("About to call eventDAOService");
						if (eventDAOService != null) {
							logger.info("eventDAOService: "+eventDAOService.getAll());
						}
						else
							logger.info("eventDAOService not available");
						
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			};
			svc.run();
*/			logger.info("Init...done");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void setEventDAOService(IEventDAOService eventDAOService, Map<String,Object> properties) {
		logger.info("Wiring eventDAOService");
		this.eventDAOService = eventDAOService;
		init();
	}
	
	public void unsetEventDAOService(IEventDAOService eventDAOService, Map<String,Object> properties) {
		logger.info("Unwiring eventDAOService");
		this.eventDAOService = eventDAOService;
	}	
	
	public void setEventBusinessService(IEventBusinessService eventBusinessService, Map<String,Object> properties) {
		logger.info("Wiring eventBusinessService");
		this.eventBusinessService = eventBusinessService;
	}
	
	public void unsetEventBusinessService(IEventBusinessService eventBusinessService, Map<String,Object> properties) {
		logger.info("Unwiring eventBusinessService");
		this.eventBusinessService = eventBusinessService;
	}	
}
