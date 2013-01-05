package org.flowframe.reporting.jasper.web.services.web.service.servlet;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.FileBufferedOutputStream;
import net.sf.jasperreports.j2ee.servlets.BaseHttpServlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
// E.g.
// http://localhost:9080/reporting/pdfgen?PARAM_REPORT_NAME=stockItemSingleLabelByPK&jrpStockItemCode=RDFLT000001-01L1_0
public class PdfReportingServlet extends HttpServlet {
	static public final String PARAM_REPORT_NAME = "PARAM_REPORT_NAME";
	static public final String PARAM_REPORT_PREFIX = "jrp";

	private static DataSource dataSource;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 *
	 */
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		ServletContext context = this.getServletConfig().getServletContext();

		JasperPrint jasperPrint = null;

		try {
			String reportName = request.getParameter(PARAM_REPORT_NAME);
			String reportFileName = context.getRealPath("/jasperreports/"
					+ reportName + ".jasper");
			File reportFile = new File(reportFileName);
			if (!reportFile.exists())
				throw new JRRuntimeException(
						"File WebappReport.jasper not found. The report design must be compiled first.");

			Map jaspRptingParams = new HashMap();

			Map<String, String[]> parameters = request.getParameterMap();
			Enumeration<String> num = request.getParameterNames();
			while (num.hasMoreElements()) {
				String param = num.nextElement();
				if (param.startsWith(PARAM_REPORT_PREFIX)) {
					jaspRptingParams.put(param, request.getParameter(param));
				}
			}

			// jaspRptingParams.put("jrpStockItemCode","RBCVNTC000002-01L101_01");

			try {
				System.out.println("Params: " + jaspRptingParams);
				Connection connection = dataSource.getConnection();
				jasperPrint = JasperFillManager.fillReport(reportFileName,
						jaspRptingParams, connection);

				connection.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		List jasperPrintList = new ArrayList();
		jasperPrintList.add(jasperPrint);

		Boolean isBuffered = Boolean
				.valueOf(request
						.getParameter(BaseHttpServlet.BUFFERED_OUTPUT_REQUEST_PARAMETER));
		if (isBuffered != null && isBuffered.booleanValue()) {
			FileBufferedOutputStream fbos = new FileBufferedOutputStream();
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST,
					jasperPrintList);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, fbos);

			try {
				exporter.exportReport();
				fbos.close();

				if (fbos.size() > 0) {
					response.setContentType("application/pdf");
					response.setContentLength(fbos.size());
					ServletOutputStream ouputStream = response
							.getOutputStream();

					try {
						fbos.writeData(ouputStream);
						fbos.dispose();
						ouputStream.flush();
					} finally {
						if (ouputStream != null) {
							try {
								ouputStream.close();
							} catch (IOException ex) {
							}
						}
					}
				}
			} catch (JRException e) {
				throw new ServletException(e);
			} finally {
				fbos.close();
				fbos.dispose();
			}
		} else {
			response.setContentType("application/pdf");

			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST,
					jasperPrintList);

			OutputStream ouputStream = response.getOutputStream();
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
					ouputStream);

			try {
				exporter.exportReport();
			} catch (JRException e) {
				throw new ServletException(e);
			} finally {
				if (ouputStream != null) {
					try {
						ouputStream.close();
					} catch (IOException ex) {
					}
				}
			}
		}
	}
}