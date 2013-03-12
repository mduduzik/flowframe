package org.flowframe.portal.remote.services.impl;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.impl.conn.SchemeRegistryFactory;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.flowframe.kernel.common.mdm.domain.documentlibrary.FileEntry;
import org.flowframe.kernel.common.mdm.domain.organization.Organization;
import org.flowframe.kernel.common.mdm.domain.role.Role;
import org.flowframe.kernel.common.mdm.domain.user.User;
import org.flowframe.kernel.common.utils.StringUtil;
import org.flowframe.kernel.common.utils.Validator;
import org.flowframe.portal.remote.services.IPortalOrganizationService;
import org.flowframe.portal.remote.services.IPortalRoleService;
import org.flowframe.portal.remote.services.IPortalUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import flexjson.JSONDeserializer;

@Transactional
@Service
public class LiferayPortalServicesImpl implements IPortalUserService, IPortalOrganizationService, IPortalRoleService {
	static public final String FFPORTAL_SERVER_HOSTNAME = "ffportal.server.hostname";//localhost
	static public final String FFPORTAL_SERVER_PORT = "ffportal.server.port";//8080
	static public final String FFPORTAL_REPOSITORY_ID = "ffportal.repository.id";//10180
	static public final String FFPORTAL_REPOSITORY_COMPANYID = "ffportal.repository.companyid";//10154
	static public final String FFPORTAL_REPOSITORY_MAIN_FOLDERID = "ffportal.repository.main.folderid";//10644
	static public final String FFPORTAL_USER_EMAIL = "ffportal.user.email";//test@liferay.com
	static public final String FFPORTAL_USER_PASSWORD = "ffportal.user.password";//test
	static public final String FFPORTAL_USER_GROUP_ID = "ffportal.user.group.id";//10180
	
	private Properties liferayProperties = new Properties();

	private BasicAuthCache authCache;
	private DefaultHttpClient httpclient;
	private HttpHost targetHost;
	private String companyId;
	private String loginEmail;
	private String loginPassword;
	private String hostname;
	private String port;
	private String loginGroupId;

	
	public void init()
	{
		loadLiferayProperties();
		
		hostname = liferayProperties.getProperty(FFPORTAL_SERVER_HOSTNAME);
		port = liferayProperties.getProperty(FFPORTAL_SERVER_PORT);
		companyId = liferayProperties.getProperty(FFPORTAL_REPOSITORY_COMPANYID);
		loginGroupId = liferayProperties.getProperty(FFPORTAL_USER_GROUP_ID);
		loginEmail = liferayProperties.getProperty(FFPORTAL_USER_EMAIL);
		loginPassword = liferayProperties.getProperty(FFPORTAL_USER_PASSWORD);
		
		targetHost = new HttpHost(hostname, Integer.valueOf(port), "http");
		PoolingClientConnectionManager cxMgr = new PoolingClientConnectionManager( SchemeRegistryFactory.createDefault());
		cxMgr.setMaxTotal(100);
		cxMgr.setDefaultMaxPerRoute(20);		
		httpclient = new DefaultHttpClient(cxMgr);
		
		httpclient.getCredentialsProvider().setCredentials(
				new AuthScope(targetHost.getHostName(), targetHost.getPort()),
				new UsernamePasswordCredentials(loginEmail, loginPassword));

		// Create AuthCache instance
		this.authCache = new BasicAuthCache();
		// Generate BASIC scheme object and add it to the local
		// auth cache
		BasicScheme basicAuth = new BasicScheme();
		authCache.put(targetHost, basicAuth);	
	}	
	
	private String getLoginUserId() throws ParseException, IOException
	{
		// Add AuthCache to the execution context
		BasicHttpContext ctx = new BasicHttpContext();
		ctx.setAttribute(ClientContext.AUTH_CACHE, authCache);
		
		HttpPost post = new HttpPost(
				"/api/secure/jsonws/user/get-user-id-by-email-address");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("companyId", companyId));
		params.add(new BasicNameValuePair("emailAddress", "test@liferay.com"));
	
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
		post.setEntity(entity);
		
		
		HttpResponse resp = httpclient.execute(targetHost, post, ctx);
		System.out.println(resp.getStatusLine());
		
		String response = null;
		if(resp.getEntity()!=null) {
		    response = EntityUtils.toString(resp.getEntity());
		}
		
		EntityUtils.consume(resp.getEntity());
		
		return response;
	}	
	

	@Override
	public Boolean isAvailable()    throws Exception {
		return (getLoginUserId() != null);
	}

	protected Properties loadLiferayProperties() {
		if (!liferayProperties.isEmpty()) {
			return liferayProperties;
		}
		try {
			liferayProperties.load(LiferayPortalServicesImpl.class
					.getResourceAsStream("/ffliferay.properties"));
		} catch (IOException e) {
			throw new RuntimeException(
					"Could not load ffliferay.properties", e);
		}

		return liferayProperties;
	}
	
	
	/**
	 * 
	 * IPortalUserService
	 * 
	 */
	@Override
	public int getUserCountByCompanyId(String companyId)  throws Exception {
		// Add AuthCache to the execution context
		BasicHttpContext ctx = new BasicHttpContext();
		ctx.setAttribute(ClientContext.AUTH_CACHE, authCache);
		
		HttpPost post = new HttpPost(
				"/api/secure/jsonws/user/get-company-users-count");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("companyId ", companyId));

		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
		post.setEntity(entity);
		
		
		HttpResponse resp = httpclient.execute(targetHost, post, ctx);
		System.out.println("getUserCountByCompanyId Status:["+resp.getStatusLine()+"]");
		
		String response = null;
		if(resp.getEntity()!=null) {
		    response = EntityUtils.toString(resp.getEntity());
		}
		System.out.println("getUserCountByCompanyId Res:["+response+"]");
		
		JSONDeserializer<Integer> deserializer = new JSONDeserializer<Integer>();
		Integer count = deserializer.deserialize(response,Integer.class);
		
		EntityUtils.consume(resp.getEntity());
		
		return count;
	}	

	@Override
	public List<User> getUsersByCompanyId(String companyId)   throws Exception {
		Integer totalUserCount = getUserCountByCompanyId(companyId);
		
		
		// Add AuthCache to the execution context
		BasicHttpContext ctx = new BasicHttpContext();
		ctx.setAttribute(ClientContext.AUTH_CACHE, authCache);
		
		HttpPost post = new HttpPost(
				"/api/secure/jsonws/user/get-company-users");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("companyId", companyId));
		params.add(new BasicNameValuePair("start", "0"));
		params.add(new BasicNameValuePair("end", totalUserCount.toString()));
		
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
		post.setEntity(entity);
		
		
		HttpResponse resp = httpclient.execute(targetHost, post, ctx);
		System.out.println("getUsersByCompanyId Status:["+resp.getStatusLine()+"]");
		
		String response = null;
		if(resp.getEntity()!=null) {
		    response = EntityUtils.toString(resp.getEntity());
		}
		System.out.println("getUsersByCompanyId Res:["+response+"]");
		
		ArrayList<User> users = new JSONDeserializer<ArrayList<User>>()
			    .use("values", User.class)
			    .deserialize(response);

		EntityUtils.consume(resp.getEntity());
		
		return users;
	}
	
	@Override
	public List<User> getUsersByDefaultCompany() throws Exception {
		return getUsersByCompanyId(companyId);
	}		

	@Override
	public Set<User> getUsersByOrganizationId(String organizationId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User provideUserByScreenName(String portalOrganizationId,
			String screenName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User provideUserByEmailAddress(String portalOrganizationId,
			String emailAddress) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void syncUsersFromPortal() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<Organization> getOrganizationsByCompanyId(String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Organization provideOrganization(String portalOrganizationId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User provideUserByEmailAddress(String emailAddress) throws Exception {
		// Add AuthCache to the execution context
		BasicHttpContext ctx = new BasicHttpContext();
		ctx.setAttribute(ClientContext.AUTH_CACHE, authCache);
		
		HttpPost post = new HttpPost(
				"/api/secure/jsonws/user/get-user-by-email-address");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("companyId", companyId));
		params.add(new BasicNameValuePair("emailAddress", emailAddress));
	
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
		post.setEntity(entity);
		
		
		HttpResponse resp = httpclient.execute(targetHost, post, ctx);
		System.out.println(resp.getStatusLine());
		
		String response = null;
		if(resp.getEntity()!=null) {
		    response = EntityUtils.toString(resp.getEntity());
		}
		
		User user = null;
		if (!StringUtil.contains(response, "NoSuch", "") 
			|| !StringUtil.contains(response, "Exception", ""))
		{
			JSONDeserializer<User> deserializer = new JSONDeserializer<User>();
			user = deserializer.deserialize(response,User.class);
		}		
		
		EntityUtils.consume(resp.getEntity());
		
		return user;
	}
	
	@Override
	public User provideUserByScreenName(String screenName) throws Exception {
		// Add AuthCache to the execution context
		BasicHttpContext ctx = new BasicHttpContext();
		ctx.setAttribute(ClientContext.AUTH_CACHE, authCache);
		
		HttpPost post = new HttpPost(
				"/api/secure/jsonws/user/get-user-by-screen-name");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("companyId", companyId));
		params.add(new BasicNameValuePair("screenName", screenName));
	
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
		post.setEntity(entity);
		
		
		HttpResponse resp = httpclient.execute(targetHost, post, ctx);
		System.out.println(resp.getStatusLine());
		
		String response = null;
		if(resp.getEntity()!=null) {
		    response = EntityUtils.toString(resp.getEntity());
		}
		
		User user = null;
		if (!StringUtil.contains(response, "NoSuch", "") 
			|| !StringUtil.contains(response, "Exception", ""))
		{
			JSONDeserializer<User> deserializer = new JSONDeserializer<User>();
			user = deserializer.deserialize(response,User.class);
		}		
		
		EntityUtils.consume(resp.getEntity());
		
		return user;
	}
	

	
	/*
	 * 
	 * 
	 * Roles
	 * 
	 * 
	 */		
	private Role getRoleById(String roleId) throws ParseException, IOException
	{
		// Add AuthCache to the execution context
		BasicHttpContext ctx = new BasicHttpContext();
		ctx.setAttribute(ClientContext.AUTH_CACHE, authCache);
		
		HttpPost post = new HttpPost(
				"/api/secure/jsonws//role/get-role");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("companyId", companyId));
		params.add(new BasicNameValuePair("roleId", roleId));
	
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
		post.setEntity(entity);
		
		
		HttpResponse resp = httpclient.execute(targetHost, post, ctx);
		System.out.println(resp.getStatusLine());
		
		String response = null;
		if(resp.getEntity()!=null) {
		    response = EntityUtils.toString(resp.getEntity());
		}
		
		Role role = null;
		if (!StringUtil.contains(response, "NoSuch", "") )
		{
			JSONDeserializer<Role> deserializer = new JSONDeserializer<Role>();
			role = deserializer.deserialize(response,Role.class);
		}	
		
		EntityUtils.consume(resp.getEntity());
		
		return role;
	}		
	
	@Override
	public Role addRole(String roleName)
			throws Exception {
		// Add AuthCache to the execution context
		BasicHttpContext ctx = new BasicHttpContext();
		ctx.setAttribute(ClientContext.AUTH_CACHE, authCache);
		
		HttpPost post = new HttpPost("/api/secure/jsonws/role/add-role");
		MultipartEntity entity = new MultipartEntity(
				HttpMultipartMode.BROWSER_COMPATIBLE);

		
		entity.addPart("name",new StringBody(roleName, Charset.forName("UTF-8")));
		entity.addPart("descriptionMap",null);
		entity.addPart("titleMap",null);
		entity.addPart("type", new StringBody("0",Charset.forName("UTF-8")));
	
		post.setEntity(entity);

		HttpResponse resp = httpclient.execute(targetHost, post, ctx);
		System.out.println(resp.getStatusLine());
		
		String response = null;
		if(resp.getEntity()!=null) {
		    response = EntityUtils.toString(resp.getEntity());
		}
		System.out.println("addRole Res:["+response+"]");

		Role role = null;
		JSONDeserializer<Role> deserializer = new JSONDeserializer<Role>();
		role = deserializer.deserialize(response,FileEntry.class);

		EntityUtils.consume(resp.getEntity());
		
		return role;
	}
	
	

	@Override
	public Role provideRole(String roleId, String roleName) throws Exception {
		Role role = getRoleById(roleId);
		if (Validator.isNull(role))
		{
			role = addRole(roleName);
		}
		return role;
	}

	@Override
	public List<Role> getUserRoles(String userId) throws Exception {
		// Add AuthCache to the execution context
		BasicHttpContext ctx = new BasicHttpContext();
		ctx.setAttribute(ClientContext.AUTH_CACHE, authCache);
		
		HttpPost post = new HttpPost("/api/secure/jsonws/role/get-user-roles");
		MultipartEntity entity = new MultipartEntity(
				HttpMultipartMode.BROWSER_COMPATIBLE);

		entity.addPart("userId",new StringBody(userId, Charset.forName("UTF-8")));
	
		post.setEntity(entity);

		HttpResponse resp = httpclient.execute(targetHost, post, ctx);
		System.out.println(resp.getStatusLine());
		
		String response = null;
		if(resp.getEntity()!=null) {
		    response = EntityUtils.toString(resp.getEntity());
		}
		System.out.println("getUserRoles Res:["+response+"]");

		ArrayList<Role> roles = new JSONDeserializer<ArrayList<Role>>()
			    .use("values", Role.class)
			    .deserialize(response);
		
		EntityUtils.consume(resp.getEntity());

		return roles;
	}

	@Override
	public boolean userHasRole(String userId, String roleName)  throws Exception {
		// Add AuthCache to the execution context
		BasicHttpContext ctx = new BasicHttpContext();
		ctx.setAttribute(ClientContext.AUTH_CACHE, authCache);
		
		HttpPost post = new HttpPost("/api/secure/jsonws/role/has-user-role");
		MultipartEntity entity = new MultipartEntity(
				HttpMultipartMode.BROWSER_COMPATIBLE);

		entity.addPart("userId",new StringBody(userId, Charset.forName("UTF-8")));
		entity.addPart("companyId",new StringBody(companyId, Charset.forName("UTF-8")));
		entity.addPart("name",new StringBody(roleName, Charset.forName("UTF-8")));
		entity.addPart("inherited",new StringBody("false", Charset.forName("UTF-8")));
		
		post.setEntity(entity);

		HttpResponse resp = httpclient.execute(targetHost, post, ctx);
		System.out.println(resp.getStatusLine());
		
		String response = null;
		if(resp.getEntity()!=null) {
		    response = EntityUtils.toString(resp.getEntity());
		}
		System.out.println("userHasRole Res:["+response+"]");

		JSONDeserializer<Boolean> deserializer = new JSONDeserializer<Boolean>();
		Boolean hasRole = deserializer.deserialize(response,Boolean.class);
		
		EntityUtils.consume(resp.getEntity());

		return hasRole;
	}

	@Override
	public void setUserRole(String userId, String roleId)   throws Exception {
		// Add AuthCache to the execution context
		BasicHttpContext ctx = new BasicHttpContext();
		ctx.setAttribute(ClientContext.AUTH_CACHE, authCache);
		
		HttpPost post = new HttpPost("/api/secure/jsonws//user/set-role-users");
		MultipartEntity entity = new MultipartEntity(
				HttpMultipartMode.BROWSER_COMPATIBLE);

		entity.addPart("roleId",new StringBody(roleId, Charset.forName("UTF-8")));
		entity.addPart("userIds[]",new StringBody(userId, Charset.forName("UTF-8")));

		post.setEntity(entity);

		HttpResponse resp = httpclient.execute(targetHost, post, ctx);
		System.out.println(resp.getStatusLine());
		
		String response = null;
		if(resp.getEntity()!=null) {
		    response = EntityUtils.toString(resp.getEntity());
		}
		System.out.println("setUserRole Res:["+response+"]");
		
		EntityUtils.consume(resp.getEntity());
	}

	@Override
	public boolean userHasRoleByScreenName(String userScreenName,
			String roleName) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}	
	
	@Override
	public boolean userHasRoleByEmailAddress(String emailAddress,
			String roleName) throws Exception {
		User user = provideUserByEmailAddress(emailAddress);
		Boolean hasRole = userHasRole(Long.toString(user.getUserId()), roleName);
		return hasRole;
	}

	@Override
	public List<Organization> getOrganizationsByUserId(String portalUserId) throws Exception {
		// Add AuthCache to the execution context
		BasicHttpContext ctx = new BasicHttpContext();
		ctx.setAttribute(ClientContext.AUTH_CACHE, authCache);
		
		HttpPost post = new HttpPost(
				"/api/secure/jsonws//organization/get-user-organizations");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("userId",portalUserId));
		
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
		post.setEntity(entity);
		
		
		HttpResponse resp = httpclient.execute(targetHost, post, ctx);
		System.out.println("getOrganizationsByUserId Status:["+resp.getStatusLine()+"]");
		
		String response = null;
		if(resp.getEntity()!=null) {
		    response = EntityUtils.toString(resp.getEntity());
		}
		System.out.println("getOrganizationsByUserId Res:["+response+"]");
		
		ArrayList<Organization> orgs = new JSONDeserializer<ArrayList<Organization>>()
			    .use("values", Organization.class)
			    .deserialize(response);
		
		EntityUtils.consume(resp.getEntity());
		
		return orgs;
	}

	@Override
	public Organization getUserDefaultOrganization(String portalUserId)
			throws Exception {
		List<Organization> orgs = getOrganizationsByUserId(portalUserId);
		if (Validator.isNull(orgs) || orgs.isEmpty())
			return null;
		else
			return orgs.get(0);
	}
	
}