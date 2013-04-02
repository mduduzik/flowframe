package org.flowframe.web.common.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JSONServlet extends HttpServlet {
	
	private static Logger _log = LoggerFactory.getLogger(JSONServlet.class);

	@Override
	public void init(ServletConfig servletConfig) {
		ServletContext servletContext = servletConfig.getServletContext();
	}

	@Override
	@SuppressWarnings("unused")
	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		try {
			resolveRemoteUser(request);
		}
		catch (Exception e) {
			_log.error(e.getMessage(), e);
		}
	}
	
	
	protected JSONAction getJSONAction(ServletContext servletContext) {
		JSONAction jsonAction = new JSONServiceAction();

		jsonAction.setServletContext(servletContext);

		return jsonAction;
	}


	protected void resolveRemoteUser(HttpServletRequest request)
		throws Exception {

		String remoteUser = request.getRemoteUser();

		if (_log.isDebugEnabled()) {
			_log.debug("Remote user " + remoteUser);
		}
	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}
