package org.b3mn.poem.handler;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.b3mn.poem.Identity;
import org.b3mn.poem.util.HandlerWithoutModelContext;

import com.conx.bi.app.reporting.dao.services.Endpoint;
import com.conx.bi.app.reporting.dao.services.IReportingDAOService;
import com.conx.bi.app.reporting.dao.services.IReportingDAOServicePortType;

@HandlerWithoutModelContext(uri = "/endpoint")
public class EndpointHandler extends HandlerBase {
	private static final boolean isTesting = false;
	Properties props = null;
	final static String configPreFix = "profile.stencilset.mapping.";
	final static String defaultSS = "/stencilsets/bpmn/bpmn.json";

	private IReportingDAOServicePortType reportingDAOService;

	@Override
	public void init() {
		// Load properties
		FileInputStream in;

		// initialize properties from backend.properties
		try {
			in = new FileInputStream(this.getBackendRootDirectory() + "/WEB-INF/backend.properties");
			props = new Properties();
			props.load(in);
			in.close();

			String url = props.getProperty("conxbi.soap.hostname");
			String port = props.getProperty("conxbi.soap.port");
			if (url == null)
				url = "localhost";
			if (port == null)
				url = "8080";

			IReportingDAOService service = new IReportingDAOService(new URL("http://" + url + ":" + port
					+ "/cxf/com/conx/bi/app/reporting/dao/services/IReportingDAOService?wsdl"));
			this.reportingDAOService = service.getIReportingDAOServicePort();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (Error e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private List<Endpoint> getEndpoints(String userId) {
		if (!isTesting) {
			return this.reportingDAOService.getEndpointsByTenantUserName(userId);
		} else {
			List<Endpoint> list = new ArrayList<Endpoint>();
			Endpoint temp = new Endpoint();
			temp.setId(101L);
			temp.setCode("endpoint-mysql");
			temp.setName("MySQL Endpoint");
			list.add(temp);
			temp = new Endpoint();
			temp.setId(102L);
			temp.setCode("endpoint-oracle");
			temp.setName("Oracle Endpoint");
			list.add(temp);
			temp = new Endpoint();
			temp.setId(103L);
			temp.setCode("endpoint-mongo");
			temp.setName("MongoDB Endpoint");
			list.add(temp);
			return list;
		}
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response, Identity subject, Identity object) throws IOException {
		try {
			if ((request.getParameter("userId") != null) && (request.getParameter("call") != null)) {
				String userId = request.getParameter("userId");
				String call = request.getParameter("call");
				if ("getEndpointsByTenant".equals(call)) {
					StringBuffer buffer = new StringBuffer();
					buffer.append('[');
					List<Endpoint> endpoints = getEndpoints(userId);
					boolean isFirst = true;
					for (Endpoint endpoint : endpoints) {
						if (!isFirst)
							buffer.append(',');

						buffer.append('{');

						buffer.append("\"title\":\"");
						if (endpoint.getName() == null) {
							buffer.append("<undefined>");
						} else {
							buffer.append(endpoint.getName());
						}
						buffer.append("\",");

						buffer.append("\"value\":");
						if (endpoint.getId() == null) {
							buffer.append("-1");
						} else {
							buffer.append(endpoint.getId());
						}
						buffer.append(",");

						buffer.append("\"id\":");
						if (endpoint.getId() == null) {
							buffer.append(0);
						} else {
							buffer.append(endpoint.getId());
						}

						buffer.append('}');

						if (isFirst)
							isFirst = false;
					}
					buffer.append(']');
					response.setContentType("application/xml");
					response.getWriter().write(buffer.toString());
					response.setStatus(200);
				}
			} else {
				response.setStatus(400);
				response.getWriter().println("Invalid parameters");
			}
		} catch (Exception e) {
			response.setStatus(400);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			response.getWriter().println(sw.toString());
		}
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response, Identity subject, Identity object) throws IOException {
		response.setStatus(400);
		response.getWriter().println("Post not allowed");
	}
}
