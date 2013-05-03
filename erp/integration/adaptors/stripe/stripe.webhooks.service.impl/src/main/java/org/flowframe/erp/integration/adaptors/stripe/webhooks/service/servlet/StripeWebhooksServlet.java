package org.flowframe.erp.integration.adaptors.stripe.webhooks.service.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.flowframe.erp.integration.adaptors.remote.services.payments.IRemotePaymentProcessorWebHookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
// E.g.
// http://localhost:9080/reporting/pdfgen?PARAM_REPORT_NAME=stockItemSingleLabelByPK&jrpStockItemCode=RDFLT000001-01L1_0
public class StripeWebhooksServlet extends HttpServlet {

	@Autowired
	private IRemotePaymentProcessorWebHookService webhookService;
	
	
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		int httpResponseStatus = webhookService.processEvent(readBody(request));
		response.setStatus(httpResponseStatus);
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