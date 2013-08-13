package com.conx.bi.etl.pentaho.repository.db.services.handler;


import com.conx.bi.etl.pentaho.repository.db.services.CustomRepository;
import com.conx.bi.etl.pentaho.repository.db.services.Identity;
import com.conx.bi.etl.pentaho.repository.db.services.util.HandlerWithoutModelContext;
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

@HandlerWithoutModelContext(uri = "/repositoryexplorer")
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

                    RepositoryDirectoryInterface mdDir = repo.provideMetadataDirectoryForTenant(tenant);
                    RepositoryDirectoryInterface dbConnectionsMdDir = repo.provideDBConnectionsMetadataDirectoryForTenant(tenant);
                    RepositoryDirectoryInterface excelMdDir = repo.provideExcelMetadataDirectoryForTenant(tenant);
                    RepositoryDirectoryInterface delimitedMdDir = repo.provideDelimitedMetadataDirectoryForTenant(tenant);

                    JSONObject metadata = new JSONObject();
                    metadata.put("id","metadata");
                    metadata.put("text",mdDir.getName());
                    metadata.put("title",mdDir.getName());
                    metadata.put("icon","images/package.gif");
                    metadata.put("leaf",false);
                    metadata.put("hasChildren",true);
                    metadata.put("singleClickExpand",true);

                    //-- DB Connections
                    String[] dbConnectionMetadataTransNames = repo.getTransformationNames(dbConnectionsMdDir.getObjectId(), true);

/*
					StringBuffer buffer = new StringBuffer();
					buffer.append('[');
					List<ReportingJobDefinition> jobDefs = getJobDefinitions(userId);
					boolean isFirst = true;
					for (ReportingJobDefinition jobDef : jobDefs) {
						if (jobDef.isTemplate()) {
							if (!isFirst)
								buffer.append(',');

							buffer.append('{');

							buffer.append("\"title\":\"");
							if (jobDef.getName() == null) {
								buffer.append("New Process");
							} else {
								buffer.append(jobDef.getName());
							}
							buffer.append("\",");

							buffer.append("\"value\":\"");
							if (jobDef.getExternalRefId() == null) {
								buffer.append(0);
							} else {
								buffer.append(jobDef.getExternalRefId());
							}
							buffer.append("\",");

							buffer.append("\"id\":");
							if (jobDef.getId() == null) {
								buffer.append(0);
							} else {
								buffer.append(jobDef.getId());
							}

							buffer.append('}');

							if (isFirst)
								isFirst = false;
						}
					}
					buffer.append(']');*/
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
