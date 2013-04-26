package org.flowframe.erp.remote.services.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.impl.conn.SchemeRegistryFactory;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.flowframe.erp.remote.services.JsonConstants;
import org.flowframe.kernel.common.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseOBJSONWSServicesImpl {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	protected static final String URL_PART = "/org.openbravo.service.json.jsonrest";

	static public final String OBERP_SERVER_HOSTNAME = "oberp.server.hostname";
	static public final String OBERP_SERVER_PORT = "oberp.server.port";
	static public final String OBERP_USER_ID = "oberp.user.id";
	static public final String OBERP_USER_PASSWORD = "oberp.user.password";

	private Properties obProperties = new Properties();

	private BasicAuthCache authCache;
	private DefaultHttpClient httpclient;
	private HttpHost targetHost;
	private String loginUser;
	private String loginPassword;
	private String hostname;
	private String port;

	/**
	 * 
	 * Get Methods
	 * 
	 */
	public JSONObject get(String entityName, String id) throws Exception {
		JSONObject resultJsonObject = doRequest(URL_PART + "/" + entityName + "/" + id, JsonConstants.IDENTIFIER, "GET", 200);
		return resultJsonObject;
	}
	
	public JSONArray getAll(String entityName) throws Exception {
		JSONObject resultJsonObject = doRequest(URL_PART + "/" + entityName, JsonConstants.IDENTIFIER, "GET", 200);
	    final JSONArray jsonArray = resultJsonObject.getJSONObject(JsonConstants.RESPONSE_RESPONSE).getJSONArray(JsonConstants.DATA);			
		return jsonArray;
	}	
	
	public JSONObject getAllByRange(String entityName, int startRow, int endRow, String sortBy) throws Exception {
		JSONObject resultJsonObject = doRequest(URL_PART + "/" + entityName + "?_startRow="+endRow+"&_endRow="+50+"&_sortBy="+sortBy,JsonConstants.IDENTIFIER,"GET", 200);
	    //final JSONArray jsonArray = resultJsonObject.getJSONObject(JsonConstants.RESPONSE_RESPONSE).getJSONArray(JsonConstants.DATA);			
		return resultJsonObject;
	}	
	
	public JSONArray getAllByWhereClause(String entityName, String whereClause, String sortBy) throws Exception {
		JSONObject resultJsonObject = doRequest(URL_PART + "/" + entityName + "?where="+whereClause,JsonConstants.IDENTIFIER,"GET", 200);
	    final JSONArray jsonArray = resultJsonObject.getJSONObject(JsonConstants.RESPONSE_RESPONSE).getJSONArray(JsonConstants.DATA);			
		return jsonArray;
	}	

	protected JSONObject doRequest(String wsPart, String testContent, String method, int responseCode) throws Exception{
		// Add AuthCache to the execution context
		BasicHttpContext ctx = new BasicHttpContext();//http://prod.conxbi.com/openbravo/org.openbravo.service.json.jsonrest/BusinessPartner
		ctx.setAttribute(ClientContext.AUTH_CACHE, authCache);
		
		HttpGet get = new HttpGet("/openbravo/"+wsPart);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		//params.add(new BasicNameValuePair("companyId", companyId));
		//params.add(new BasicNameValuePair("emailAddress", "test@liferay.com"));
	
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
		//get.setEntity(entity);
		
		
		HttpResponse resp = httpclient.execute(targetHost, get, ctx);
		System.out.println("Response Status:["+resp.getStatusLine()+"]");
		
		final String response = resp.getEntity()!=null?StringUtil.replace(EntityUtils.toString(resp.getEntity()).toString(), "\"id\"", "code"):null;
		
	    final JSONObject jsonResult = new JSONObject(response);
	
		EntityUtils.consume(resp.getEntity());
		
		return jsonResult;	
/*		try {
			final HttpURLConnection hc = createConnection(wsPart, method);
			hc.connect();
			assert responseCode == hc.getResponseCode() : "Response code " + hc.getResponseCode() + " unexpected";
			if (hc.getResponseCode() != 200) {
				return null;
			}

			final InputStream is = hc.getInputStream();
			final String content = convertToString(is);
			final JSONObject jsonObject = new JSONObject(content);
			is.close();
			return jsonObject;
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}*/
	}

	protected HttpURLConnection createConnection(String wsPart, String method) throws Exception {
		Authenticator.setDefault(new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(loginUser, loginPassword.toCharArray());
			}
		});
		logger.debug(method + ": " + getOpenbravoURL() + wsPart);
		final URL url = new URL(getOpenbravoURL() + wsPart);
		final HttpURLConnection hc = (HttpURLConnection) url.openConnection();
		hc.setRequestMethod(method);
		hc.setAllowUserInteraction(false);
		hc.setDefaultUseCaches(false);
		hc.setDoOutput(true);
		hc.setDoInput(true);
		hc.setInstanceFollowRedirects(true);
		hc.setUseCaches(false);
		hc.setRequestProperty("Content-Type", "application/json");
		return hc;
	}

	protected String getOpenbravoURL() {
		return "http://" + hostname + ":" + port +"/openbravo";
	}

	protected String doContentRequest(String wsPart, String content, int expectedResponse, String expectedContent, String method)  throws Exception {
		// Add AuthCache to the execution context
		BasicHttpContext ctx = new BasicHttpContext();
		ctx.setAttribute(ClientContext.AUTH_CACHE, authCache);
		
		HttpGet get = new HttpGet("/openbravo/"+wsPart);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		//params.add(new BasicNameValuePair("companyId", companyId));
		//params.add(new BasicNameValuePair("emailAddress", "test@liferay.com"));
	
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
		//get.setEntity(entity);
		
		
		HttpResponse resp = httpclient.execute(targetHost, get, ctx);
		System.out.println("Response Status:["+resp.getStatusLine()+"]");
		
		final String response = resp.getEntity()!=null?EntityUtils.toString(resp.getEntity()):null;
		
		EntityUtils.consume(resp.getEntity());
		
		return response;		
/*		try {
			final HttpURLConnection hc = createConnection(wsPart, method);
			final OutputStream os = hc.getOutputStream();
			os.write(content.getBytes("UTF-8"));
			os.flush();
			os.close();
			hc.connect();

			assert expectedResponse == hc.getResponseCode() : "Response code " + hc.getResponseCode() + " unexpected";

			if (expectedResponse == 500) {
				// no content available anyway
				return null;
			}
			final InputStream is = hc.getInputStream();
			final String outputContent = convertToString(is);
			final JSONObject jsonObject = new JSONObject(outputContent);

			assert expectedResponse == hc.getResponseCode() : "Response code " + hc.getResponseCode() + " unexpected";

			is.close();
			return jsonObject;
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}*/
	}

	protected String convertToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (Exception e) {
			throw new IllegalStateException(e);
		} finally {
			try {
				is.close();
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}
		}

		return sb.toString();
	}

	public void init() {
		loadOBJSONWSProperties();

		hostname = obProperties.getProperty(OBERP_SERVER_HOSTNAME);
		port = obProperties.getProperty(OBERP_SERVER_PORT);
		loginUser = obProperties.getProperty(OBERP_USER_ID);
		loginPassword = obProperties.getProperty(OBERP_USER_PASSWORD);

		targetHost = new HttpHost(hostname, Integer.valueOf(port), "http");
		PoolingClientConnectionManager cxMgr = new PoolingClientConnectionManager(SchemeRegistryFactory.createDefault());
		cxMgr.setMaxTotal(100);
		cxMgr.setDefaultMaxPerRoute(20);
		httpclient = new DefaultHttpClient(cxMgr);

		httpclient.getCredentialsProvider().setCredentials(new AuthScope(targetHost.getHostName(), targetHost.getPort()),
				new UsernamePasswordCredentials(loginUser, loginPassword));

		// Create AuthCache instance
		this.authCache = new BasicAuthCache();
		// Generate BASIC scheme object and add it to the local
		// auth cache
		BasicScheme basicAuth = new BasicScheme();
		authCache.put(targetHost, basicAuth);
	}

	protected Properties loadOBJSONWSProperties() {
		if (!obProperties.isEmpty()) {
			return obProperties;
		}
		try {
			obProperties.load(OBPartnerServicesImpl.class.getResourceAsStream("/oberp.properties"));
		} catch (IOException e) {
			throw new RuntimeException("Could not load oberp.properties", e);
		}

		return obProperties;
	}
}
