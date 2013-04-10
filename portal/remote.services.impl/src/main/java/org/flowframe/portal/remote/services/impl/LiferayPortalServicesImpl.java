package org.flowframe.portal.remote.services.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import org.flowframe.portal.remote.services.IPortalCompanyService;
import org.flowframe.portal.remote.services.IPortalOrganizationService;
import org.flowframe.portal.remote.services.IPortalRoleService;
import org.flowframe.portal.remote.services.IPortalUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Transactional
@Service
public class LiferayPortalServicesImpl implements IPortalUserService, IPortalOrganizationService, IPortalRoleService, IPortalCompanyService {
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
		System.out.println("getLoginUserId Status:["+resp.getStatusLine()+"]");
		
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
	public String generateUnencryptedTemporaryPassword(String userEmailAddress)  throws Exception{
		// Add AuthCache to the execution context
		BasicHttpContext ctx = new BasicHttpContext();
		ctx.setAttribute(ClientContext.AUTH_CACHE, authCache);
		HttpPost post = new HttpPost(
				"/com.conx.bi.portal.liferay.common-portlet/api/secure/jsonws/conxbiregistration/generate-unencrypted-temporary-password");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("companyId", companyId));
		params.add(new BasicNameValuePair("userEmailAddress", userEmailAddress));

		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
		post.setEntity(entity);
		
		
		HttpResponse resp = httpclient.execute(targetHost, post, ctx);
		System.out.println("generateUnencryptedTemporaryPassword Status:["+resp.getStatusLine()+"]");
		
		String response = null;
		if(resp.getEntity()!=null) {
		    response = EntityUtils.toString(resp.getEntity());
		}
		
		String upwd = null;
		if (!StringUtil.contains(response, "Exception", ""))
		{
			JSONDeserializer<String> deserializer = new JSONDeserializer<String>();
			upwd = deserializer.deserialize(response,String.class);
		}		
		
		EntityUtils.consume(resp.getEntity());
		
		return upwd;
	}
	
	/**
	 * 
	 * 
	 * Orgs
	 * 
	 * 
	 */

	@Override
	public Set<Organization> getOrganizationsByCompanyId(String companyId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Organization addOrganizationUserIds(String portalOrganizationName, String[] userIds) throws Exception {
		Organization org = provideOrganization(portalOrganizationName);
		updateOrganizationUserIds(Long.toString(org.getOrganizationId()),userIds);
		return org;
	}	

	@Override
	public Organization provideOrganization(String portalOrganizationName) throws Exception {
		String orgId = getOrganizationIdByName(portalOrganizationName);
		Organization org = getOrganizationById(orgId);
		if (Validator.isNull(org))
			org = addOrganization(portalOrganizationName, null);
		return org;
	}
	
	public void updateOrganizationUserIds(String portalOrganizationId, String[] userIds) throws Exception {

		BasicHttpContext ctx = new BasicHttpContext();
		ctx.setAttribute(ClientContext.AUTH_CACHE, authCache);
		
		HttpPost post = new HttpPost(
				"/api/secure/jsonws//user/add-organization-users");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		JSONSerializer serializer = new JSONSerializer();
		String userIdsStr = serializer.serialize(userIds);
		
		params.add(new BasicNameValuePair("organizationId", portalOrganizationId));
		params.add(new BasicNameValuePair("userIds",userIdsStr));
		
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
		post.setEntity(entity);
		
		
		HttpResponse resp = httpclient.execute(targetHost, post, ctx);
		System.out.println("updateOrganizationUserIds Status:["+resp.getStatusLine()+"]");
		
		
		EntityUtils.consume(resp.getEntity());
	}	

	public Organization addOrganization(String organizationName, String parentPortalOrganizationId) throws Exception {
		String type = "regular-organization";

		BasicHttpContext ctx = new BasicHttpContext();
		ctx.setAttribute(ClientContext.AUTH_CACHE, authCache);
		
		HttpPost post = new HttpPost(
				"/api/secure/jsonws/organization/add-organization");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		params.add(new BasicNameValuePair("companyId", companyId));
		params.add(new BasicNameValuePair("parentOrganizationId","0"));
		params.add(new BasicNameValuePair("name",organizationName));
		params.add(new BasicNameValuePair("type",type));
		params.add(new BasicNameValuePair("recursable","false"));
		params.add(new BasicNameValuePair("regionId","0"));
		params.add(new BasicNameValuePair("countryId","0"));
		params.add(new BasicNameValuePair("statusId","12017"));
		params.add(new BasicNameValuePair("comments",null));
		params.add(new BasicNameValuePair("site","false"));
		params.add(new BasicNameValuePair("serviceContext",null));
		
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
		post.setEntity(entity);
		
		
		HttpResponse resp = httpclient.execute(targetHost, post, ctx);
		System.out.println("addOrganization Status:["+resp.getStatusLine()+"]");
		
		String response = null;
		if(resp.getEntity()!=null) {
		    response = EntityUtils.toString(resp.getEntity());
		}
		
		Organization org = null;
		if (!StringUtil.contains(response, "Exception", ""))
		{
			JSONDeserializer<Organization> deserializer = new JSONDeserializer<Organization>();
			org = deserializer.deserialize(response,Organization.class);
		}		
		
		EntityUtils.consume(resp.getEntity());
		
		return org;		
	}
	
	public String getOrganizationIdByName(String organizationName) throws Exception {
		// Add AuthCache to the execution context
		BasicHttpContext ctx = new BasicHttpContext();
		ctx.setAttribute(ClientContext.AUTH_CACHE, authCache);
		
		HttpPost post = new HttpPost(
				"/api/secure/jsonws/organization/get-organization-id");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		params.add(new BasicNameValuePair("companyId", companyId));
		params.add(new BasicNameValuePair("name",organizationName));
		
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
		post.setEntity(entity);
		
		
		HttpResponse resp = httpclient.execute(targetHost, post, ctx);
		System.out.println("getOrganizationByName Status:["+resp.getStatusLine()+"]");
		
		String response = null;
		if(resp.getEntity()!=null) {
		    response = EntityUtils.toString(resp.getEntity());
		}
		
		String id = null;
		if (!StringUtil.contains(response, "Exception", ""))
		{
			id = response;
		}		
		
		EntityUtils.consume(resp.getEntity());
		
		return id;
	}	
	

	
	@Override
	public User provideUserByEmailAddress(String firstName, String lastName, String emailAddress) throws Exception {
		User user = getUserByEmailAddress(emailAddress);
		if (Validator.isNull(user))
			user = addUser(firstName, lastName, emailAddress);
		return user;
	}
	

	@Override
	public User provideUserByEmailAddress(String firstName, String lastName, String emailAddress, String portalOrganizationName) throws Exception {
		Organization org = provideOrganization(portalOrganizationName);
		User user = provideUserByEmailAddress(firstName,lastName,emailAddress);
		updateDefaultOrganizationId(user,Long.toString(org.getOrganizationId()));

		addOrganizationUserIds(org.getName(), new String[]{Long.toString(user.getUserId())});
		return user;
	}	
	
	private void updateDefaultOrganizationId(User user, String portalOrganizationId) throws Exception {
		BasicHttpContext ctx = new BasicHttpContext();
		ctx.setAttribute(ClientContext.AUTH_CACHE, authCache);
		
		HttpPost post = new HttpPost(
				"/api/secure/jsonws/user/update-user");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		JSONSerializer serializer = new JSONSerializer();
		String orgIds = serializer.serialize(new String[]{portalOrganizationId});
		
		params.add(new BasicNameValuePair("companyId", companyId));
		params.add(new BasicNameValuePair("autoPassword","true"));
		params.add(new BasicNameValuePair("password1","password1"));
		params.add(new BasicNameValuePair("password2","password2"));
		params.add(new BasicNameValuePair("autoScreenName","true"));
		params.add(new BasicNameValuePair("screenName",null));
		params.add(new BasicNameValuePair("emailAddress",user.getEmailAddress()));
		params.add(new BasicNameValuePair("facebookId","0"));
		params.add(new BasicNameValuePair("openId","0"));
		params.add(new BasicNameValuePair("locale",null));
		params.add(new BasicNameValuePair("firstName",user.getFirstName()));
		params.add(new BasicNameValuePair("middleName",null));
		params.add(new BasicNameValuePair("lastName",user.getLastName()));
		params.add(new BasicNameValuePair("prefixId","0"));
		params.add(new BasicNameValuePair("suffixId","0"));
		params.add(new BasicNameValuePair("male","true"));
		params.add(new BasicNameValuePair("birthdayMonth","0"));
		params.add(new BasicNameValuePair("birthdayDay","1"));
		params.add(new BasicNameValuePair("birthdayYear","1971"));
		params.add(new BasicNameValuePair("jobTitle","Tenant"));
		params.add(new BasicNameValuePair("groupIds",null));//long[]
		params.add(new BasicNameValuePair("organizationIds",orgIds));
		params.add(new BasicNameValuePair("roleIds",null));
		params.add(new BasicNameValuePair("userGroupIds",null));
		params.add(new BasicNameValuePair("addresses","[]"));
		params.add(new BasicNameValuePair("emailAddresses","[]"));
		params.add(new BasicNameValuePair("phones","[]"));
		params.add(new BasicNameValuePair("websites","[]"));
		params.add(new BasicNameValuePair("announcementsDelivers","[]"));
		params.add(new BasicNameValuePair("sendEmail","false"));
		params.add(new BasicNameValuePair("serviceContext",null));
		
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
		post.setEntity(entity);
		
		
		HttpResponse resp = httpclient.execute(targetHost, post, ctx);
		System.out.println("updateDefaultOrganizationId Status:["+resp.getStatusLine()+"]");

		
		EntityUtils.consume(resp.getEntity());
	}

	private User addUser(String firstName, String lastName, String emailAddress) throws Exception {
		// Add AuthCache to the execution context
		BasicHttpContext ctx = new BasicHttpContext();
		ctx.setAttribute(ClientContext.AUTH_CACHE, authCache);
		
		HttpPost post = new HttpPost(
				"/api/secure/jsonws/user/add-user");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		params.add(new BasicNameValuePair("companyId", companyId));
		params.add(new BasicNameValuePair("autoPassword","true"));
		params.add(new BasicNameValuePair("password1","password1"));
		params.add(new BasicNameValuePair("password2","password2"));
		params.add(new BasicNameValuePair("autoScreenName","true"));
		params.add(new BasicNameValuePair("screenName",null));
		params.add(new BasicNameValuePair("emailAddress",emailAddress));
		params.add(new BasicNameValuePair("facebookId","0"));
		params.add(new BasicNameValuePair("openId","0"));
		params.add(new BasicNameValuePair("locale",null));
		params.add(new BasicNameValuePair("firstName",firstName));
		params.add(new BasicNameValuePair("middleName",null));
		params.add(new BasicNameValuePair("lastName",lastName));
		params.add(new BasicNameValuePair("prefixId","0"));
		params.add(new BasicNameValuePair("suffixId","0"));
		params.add(new BasicNameValuePair("male","true"));
		params.add(new BasicNameValuePair("birthdayMonth","0"));
		params.add(new BasicNameValuePair("birthdayDay","1"));
		params.add(new BasicNameValuePair("birthdayYear","1971"));
		params.add(new BasicNameValuePair("jobTitle","Tenant"));
		params.add(new BasicNameValuePair("groupIds",null));//long[]
		params.add(new BasicNameValuePair("organizationIds",null));
		params.add(new BasicNameValuePair("roleIds",null));
		params.add(new BasicNameValuePair("userGroupIds",null));
		params.add(new BasicNameValuePair("addresses","[]"));
		params.add(new BasicNameValuePair("emailAddresses","[]"));
		params.add(new BasicNameValuePair("phones","[]"));
		params.add(new BasicNameValuePair("websites","[]"));
		params.add(new BasicNameValuePair("announcementsDelivers","[]"));
		params.add(new BasicNameValuePair("sendEmail","false"));
		params.add(new BasicNameValuePair("serviceContext",null));
		
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
		post.setEntity(entity);
		
		
		HttpResponse resp = httpclient.execute(targetHost, post, ctx);
		System.out.println("addUser Status:["+resp.getStatusLine()+"]");
		
		String response = null;
		if(resp.getEntity()!=null) {
		    response = EntityUtils.toString(resp.getEntity());
		}
		
		User user = null;
		if (!StringUtil.contains(response, "Exception", ""))
		{
			JSONDeserializer<User> deserializer = new JSONDeserializer<User>();
			user = deserializer.deserialize(response,User.class);
		}		
		
		EntityUtils.consume(resp.getEntity());
		
		return user;
	}	
	
	@Override
	public User getUserByEmailAddress(String emailAddress) throws Exception {
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
		System.out.println("getUserByEmailAddress Status:["+resp.getStatusLine()+"]");
		
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
		System.out.println("provideUserByScreenName Status:["+resp.getStatusLine()+"]");
		
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
		System.out.println("getRoleById Status:["+resp.getStatusLine()+"]");
		
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
		System.out.println("addRole Status:["+resp.getStatusLine()+"]");
		
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
		System.out.println("getOrganizationsByUserId Status:["+resp.getStatusLine()+"]");
		
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
		System.out.println("userHasRole Status:["+resp.getStatusLine()+"]");
		
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
		System.out.println("getOrganizationsByUserId Status:["+resp.getStatusLine()+"]");
		
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
		User user = provideUserByEmailAddress(emailAddress,null,null);
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
	
	public Organization getOrganizationById(String portalOrgId) throws Exception {
		// Add AuthCache to the execution context
		BasicHttpContext ctx = new BasicHttpContext();
		ctx.setAttribute(ClientContext.AUTH_CACHE, authCache);
		
		HttpPost post = new HttpPost(
				"/api/secure/jsonws/organization/get-organization");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("organizationId",portalOrgId));
		
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
		post.setEntity(entity);
		
		
		HttpResponse resp = httpclient.execute(targetHost, post, ctx);
		System.out.println("getOrganizationById Status:["+resp.getStatusLine()+"]");
		
		String response = null;
		if(resp.getEntity()!=null) {
		    response = EntityUtils.toString(resp.getEntity());
		}
		System.out.println("getOrganizationById Res:["+response+"]");
		
		Organization org = new JSONDeserializer<Organization>().deserialize(response,Organization.class);
		
		EntityUtils.consume(resp.getEntity());
		
		return org;
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

	@Override
	public long getDefaultCompanyId() {
		return Long.valueOf(companyId);
	}

}