package org.flowframe.erp.integration.adaptors.stripe.webhooks.service.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.flowframe.erp.integration.adaptors.stripe.services.IEventBusinessService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

@Service
//@Transactional
// E.g.
// http://localhost:9080/reporting/pdfgen?PARAM_REPORT_NAME=stockItemSingleLabelByPK&jrpStockItemCode=RDFLT000001-01L1_0
public class StripeWebhooksServlet extends HttpServlet {
	
	@Autowired
	private WebApplicationContext webApplicationContext;	

	@Autowired
	private IEventBusinessService eventBusinessService;
	
	
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		eventBusinessService = getEventBusinessService(request);
		int httpResponseStatus = eventBusinessService.processEvent(readBody(request));
		response.setStatus(httpResponseStatus);
	}
	
	/**
	 * Called by the servlet container to indicate to a servlet that the servlet
	 * is being placed into service.
	 * 
	 * @param servletConfig
	 *            the object containing the servlet's configuration and
	 *            initialization parameters
	 * @throws javax.servlet.ServletException
	 *             if an exception has occurred that interferes with the
	 *             servlet's normal operation.
	 */
	@Override
	public void init(javax.servlet.ServletConfig servletConfig) throws javax.servlet.ServletException {
		super.init(servletConfig);
		try {
			this.webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletConfig.getServletContext());
		} catch (IllegalStateException e) {
			throw new ServletException("could not locate containing WebApplicationContext");
		}
	}

	/**
	 * Get the containing Spring {@link WebApplicationContext}. This only works
	 * after the servlet has been initialized (via {@link #init init()}).
	 * 
	 * @throws ServletException
	 *             if the operation fails
	 */
	protected final WebApplicationContext getWebApplicationContext() throws ServletException {
		if (this.webApplicationContext == null)
			throw new ServletException("can't retrieve WebApplicationContext before init() is invoked");
		return this.webApplicationContext;
	}

	/**
	 * Get the {@link AutowireCapableBeanFactory} associated with the containing
	 * Spring {@link WebApplicationContext}. This only works after the servlet
	 * has been initialized (via {@link #init init()}).
	 * 
	 * @throws ServletException
	 *             if the operation fails
	 */
	protected final AutowireCapableBeanFactory getAutowireCapableBeanFactory() throws ServletException {
		try {
			return getWebApplicationContext().getAutowireCapableBeanFactory();
		} catch (IllegalStateException e) {
			throw new ServletException("containing context " + getWebApplicationContext() + " is not autowire-capable", e);
		}
	}
	

	protected IEventBusinessService getEventBusinessService(HttpServletRequest request) throws ServletException {
		Class cl = null;
		try {
			cl = IEventBusinessService.class;
			AutowireCapableBeanFactory beanFactory = getAutowireCapableBeanFactory();
			return (IEventBusinessService) beanFactory.getBean("eventBusinessService");
		} catch (BeansException e) {
			if (cl == null) {
				throw new ServletException("failed to create new IEventBusinessService instance", e);
			} else {
				throw new ServletException("failed to create new instance of " + cl, e);
			}
		}
	}		
	
	private String readBody(HttpServletRequest request) throws IOException{
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;
		try {
		  InputStream inputStream = request.getInputStream();
		  if (inputStream != null) {
		   bufferedReader = new BufferedReader(new InputStreamReader(
		inputStream));
		   char[] charBuffer = new char[128];
		   int bytesRead = -1;
		   while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
		    stringBuilder.append(charBuffer, 0, bytesRead);
		   }
		  } else {
		   stringBuilder.append("");
		  }
		} catch (IOException ex) {
		  throw ex;
		} finally {
		  if (bufferedReader != null) {
		   try {
		    bufferedReader.close();
		   } catch (IOException ex) {
		    throw ex;
		   }
		  }
		}
		return stringBuilder.toString();	
	}
}