package com.conx.bi.etl.pentaho.repository.db.services.handler;


import com.conx.bi.etl.pentaho.repository.db.services.CustomRepository;
import com.conx.bi.etl.pentaho.repository.db.services.Identity;
import com.conx.bi.etl.pentaho.repository.db.services.repository.DBRepositoryWrapperImpl;
import com.conx.bi.etl.pentaho.repository.db.services.repository.RepositoryExporter;
import com.conx.bi.etl.pentaho.repository.db.services.util.HandlerWithoutModelContext;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.flowframe.kernel.common.mdm.domain.organization.Organization;
import org.pentaho.di.repository.RepositoryDirectoryInterface;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

import static com.conx.bi.etl.pentaho.repository.db.services.repository.DBRepositoryWrapperImpl.*;

@HandlerWithoutModelContext(uri = "/explorer")
public class RepositoryExplorerHandler extends HandlerBase {
	Properties props = null;
    CustomRepository repo = null;

	@Override
	public void init() {
		try {
            this.repo = getINSTANCE();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (Error e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}



	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response, Identity subject, Identity object) throws IOException {
		try {
			if ((request.getParameter("userId") != null) && (request.getParameter("call") != null)) {
				String userId = request.getParameter("userId");
				String call = request.getParameter("call");
                StringBuffer buffer = new StringBuffer();
                if ("all".equals(call)) {
                    Organization tenant = new Organization();
                    tenant.setId(1L);
                    tenant.setName("Test");

                    JSONArray tree = RepositoryExporter.exportTreeToJSONByTenant(null, DBRepositoryWrapperImpl.getINSTANCE(), tenant);

					response.setContentType("application/json");
					response.getWriter().write(tree.toString());
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