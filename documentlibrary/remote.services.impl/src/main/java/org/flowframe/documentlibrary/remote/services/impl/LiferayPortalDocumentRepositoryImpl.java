package org.flowframe.documentlibrary.remote.services.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.params.SyncBasicHttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.flowframe.documentlibrary.remote.services.IRemoteDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.flowframe.kernel.common.utils.HTTPUtil;
import org.flowframe.kernel.common.utils.StringUtil;
import org.flowframe.kernel.common.utils.Validator;
import org.flowframe.kernel.metamodel.dao.services.IEntityTypeDAOService;
import org.flowframe.kernel.common.mdm.dao.services.documentlibrary.IFolderDAOService;
import org.flowframe.kernel.common.mdm.domain.BaseEntity;
import org.flowframe.kernel.common.mdm.domain.documentlibrary.DocType;
import org.flowframe.kernel.common.mdm.domain.documentlibrary.FileEntry;
import org.flowframe.kernel.common.mdm.domain.documentlibrary.Folder;
import org.flowframe.kernel.common.mdm.domain.metamodel.EntityType;

import flexjson.JSONDeserializer;

@Transactional
@Service
public class LiferayPortalDocumentRepositoryImpl implements IRemoteDocumentRepository {

	@PersistenceContext
	private EntityManager em;

	static public final String FFDOCREPO_SERVER_HOSTNAME = "ffdocrepo.server.hostname";// localhost
	static public final String FFDOCREPO_SERVER_PORT = "ffdocrepo.server.port";// 8080
	static public final String FFDOCREPO_REPOSITORY_ID = "ffdocrepo.repository.id";// 10180
	static public final String FFDOCREPO_REPOSITORY_COMPANYID = "ffdocrepo.repository.companyid";// 10154
	static public final String FFDOCREPO_REPOSITORY_MAIN_FOLDERID = "ffdocrepo.repository.main.folderid";// 10644
	static public final String FFDOCREPO_USER_EMAIL = "ffdocrepo.user.email";// test@liferay.com
	static public final String FFDOCREPO_USER_PASSWORD = "ffdocrepo.user.password";// test
	static public final String FFDOCREPO_USER_GROUP_ID = "ffdocrepo.user.group.id";// 10180

	private Properties liferayProperties = new Properties();

	private BasicAuthCache authCache;
	private DefaultHttpClient httpclient;
	private HttpHost targetHost;
	private String repositoryId;
	private String companyId;
	private String fflogiFolderId;
	private String loginEmail;
	private String loginPassword;
	private String hostname;
	private String port;
	private String loginGroupId;

	@Autowired
	private IFolderDAOService folderDAOService;

	@Autowired
	private IEntityTypeDAOService entityTypeDAOService;

	@Override
	public void init() {
		loadLiferayProperties();

		hostname = liferayProperties.getProperty(FFDOCREPO_SERVER_HOSTNAME);
		port = liferayProperties.getProperty(FFDOCREPO_SERVER_PORT);
		repositoryId = liferayProperties.getProperty(FFDOCREPO_REPOSITORY_ID);
		companyId = liferayProperties.getProperty(FFDOCREPO_REPOSITORY_COMPANYID);
		fflogiFolderId = liferayProperties.getProperty(FFDOCREPO_REPOSITORY_MAIN_FOLDERID);
		loginGroupId = liferayProperties.getProperty(FFDOCREPO_USER_GROUP_ID);
		loginEmail = liferayProperties.getProperty(FFDOCREPO_USER_EMAIL);
		loginPassword = liferayProperties.getProperty(FFDOCREPO_USER_PASSWORD);

		targetHost = new HttpHost(hostname, Integer.valueOf(port), "http");
		httpclient = new DefaultHttpClient();
		httpclient.getCredentialsProvider().setCredentials(new AuthScope(targetHost.getHostName(), targetHost.getPort()),
				new UsernamePasswordCredentials(loginEmail, loginPassword));

		// Create AuthCache instance
		this.authCache = new BasicAuthCache();
		// Generate BASIC scheme object and add it to the local
		// auth cache
		BasicScheme basicAuth = new BasicScheme();
		authCache.put(targetHost, basicAuth);
	}

	@Override
	public Boolean isAvailable() throws Exception {
		return getLoginUserId() != null;
	}

	@Override
	public Folder getFolderById(String folderId) throws Exception {
		// Add AuthCache to the execution context
		BasicHttpContext ctx = new BasicHttpContext();
		ctx.setAttribute(ClientContext.AUTH_CACHE, authCache);

		HttpPost post = new HttpPost("/api/secure/jsonws/dlapp/get-folder");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("folderId", folderId));
		// params.add(new BasicNameValuePair("name", "Receive1"));
		// params.add(new BasicNameValuePair("description", "Receive1"));

		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
		post.setEntity(entity);

		HttpResponse resp = httpclient.execute(targetHost, post, ctx);
		System.out.println("getFolderById Status:[" + resp.getStatusLine() + "]");

		String response = null;
		if (resp.getEntity() != null) {
			response = EntityUtils.toString(resp.getEntity());
		}
		System.out.println("getFolderById Res:[" + response + "]");

		JSONDeserializer<Folder> deserializer = new JSONDeserializer<Folder>();
		Folder fldr = deserializer.deserialize(response, Folder.class);

		return fldr;
	}

	@Override
	public Folder getFolderByName(String parentFolderId, String name) throws Exception {
		// Add AuthCache to the execution context
		BasicHttpContext ctx = new BasicHttpContext();
		ctx.setAttribute(ClientContext.AUTH_CACHE, authCache);

		HttpPost post = new HttpPost("/api/secure/jsonws/dlapp/get-folder");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("repositoryId", repositoryId));
		params.add(new BasicNameValuePair("parentFolderId", parentFolderId));
		params.add(new BasicNameValuePair("name", name));

		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
		post.setEntity(entity);

		HttpResponse resp = httpclient.execute(targetHost, post, ctx);
		System.out.println(resp.getStatusLine());

		String response = null;
		if (resp.getEntity() != null) {
			response = EntityUtils.toString(resp.getEntity());
		}
		System.out.println("getFolderByName Res:[" + response + "]");

		Folder fldr = null;
		if (!StringUtil.contains(response, "com.liferay.portlet.documentlibrary.NoSuchFolderException", "")) {
			JSONDeserializer<Folder> deserializer = new JSONDeserializer<Folder>();
			fldr = deserializer.deserialize(response, Folder.class);
		}

		return fldr;
	}

	@Override
	public boolean folderExists(String parentFolderId, String name) throws Exception {
		Folder fldr = getFolderByName(parentFolderId, name);
		return fldr != null;
	}

	@Override
	public void deleteFolderById(String folderId) throws Exception {
		// Add AuthCache to the execution context
		BasicHttpContext ctx = new BasicHttpContext();
		ctx.setAttribute(ClientContext.AUTH_CACHE, authCache);

		HttpPost post = new HttpPost("/api/secure/jsonws/dlapp/delete-folder");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("folderId", folderId));

		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
		post.setEntity(entity);

		HttpResponse resp = httpclient.execute(targetHost, post, ctx);
		System.out.println(resp.getStatusLine());

		String response = null;
		if (resp.getEntity() != null) {
			response = EntityUtils.toString(resp.getEntity());
		}

		System.out.println("deleteFolderById Res:[" + response + "]");
	}

	@Override
	public void deleteFolderByName(String parentFolderId, String name) throws Exception {
		Folder fdlr = getFolderByName(parentFolderId, name);
		deleteFolderById(Long.toString(fdlr.getFolderId()));
	}

	@Override
	public List<FileEntry> getFileEntries(String folderId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FileEntry getFileEntryById(String fileEntryId) throws Exception {
		// Add AuthCache to the execution context
		BasicHttpContext ctx = new BasicHttpContext();
		ctx.setAttribute(ClientContext.AUTH_CACHE, authCache);

		HttpPost post = new HttpPost("/api/secure/jsonws/dlapp/get-file-entry");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("fileEntryId", fileEntryId));

		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
		post.setEntity(entity);

		HttpResponse resp = httpclient.execute(targetHost, post, ctx);
		System.out.println("getFolderById Status:[" + resp.getStatusLine() + "]");

		String response = null;
		if (resp.getEntity() != null) {
			response = EntityUtils.toString(resp.getEntity());
		}
		System.out.println("getFileEntry Res:[" + response + "]");

		JSONDeserializer<FileEntry> deserializer = new JSONDeserializer<FileEntry>();
		FileEntry fe = deserializer.deserialize(response, FileEntry.class);

		return fe;
	}

	@Override
	public InputStream getFileAsStream(String fileEntryId, String version) throws Exception {
		// Add AuthCache to the execution context
		BasicHttpContext ctx = new BasicHttpContext();
		ctx.setAttribute(ClientContext.AUTH_CACHE, authCache);

		HttpGet get = new HttpGet("/api/secure/jsonws/dlfileentry/get-file-as-stream");

		HttpParams params = new SyncBasicHttpParams();
		params.setParameter("fileEntryId", fileEntryId);
		params.setParameter("version", version);
		get.setParams(params);

		HttpResponse resp = httpclient.execute(targetHost, get, ctx);
		System.out.println("getFileAsStream Status:[" + resp.getStatusLine() + "]");

		InputStream is = resp.getEntity().getContent();

		return is;
	}

	@Override
	public String getFileAsURL(String fileEntryId, String version) throws Exception {
		FileEntry fe = getFileEntryById(fileEntryId);
		// TODO Auto-generated method stub
		// http://localhost:8080/documents/10180/16279/Bill+Of+Laden/7b30b6bd-4174-40e3-aadb-63f2cbadd8fe
		// http://<host>:<port>/documents/<groupd.id>/<folder.id>/<url_encode_title>/<uuid>
		String encodedUrl = targetHost.toString() + "/documents/" + loginGroupId + "/" + fe.getFolderId() + "/"
				+ HTTPUtil.encodeURL(fe.getTitle(), true) + "/" + fe.getUuid();
		return encodedUrl;
	}

	@Override
	public FileEntry deleteFileEntryById(String folderId, String fileEntryId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	protected Properties loadLiferayProperties() {
		if (!liferayProperties.isEmpty()) {
			return liferayProperties;
		}
		try {
			liferayProperties.load(LiferayPortalDocumentRepositoryImpl.class.getResourceAsStream("/ffdocrepo.properties"));
		} catch (IOException e) {
			throw new RuntimeException("Could not load ffportal.properties", e);
		}

		return liferayProperties;
	}

	private String getLoginUserId() throws ParseException, IOException {
		// Add AuthCache to the execution context
		BasicHttpContext ctx = new BasicHttpContext();
		ctx.setAttribute(ClientContext.AUTH_CACHE, authCache);

		HttpPost post = new HttpPost("/api/secure/jsonws/user/get-user-id-by-email-address");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("companyId", companyId));
		params.add(new BasicNameValuePair("emailAddress", "test@liferay.com"));

		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
		post.setEntity(entity);

		HttpResponse resp = httpclient.execute(targetHost, post, ctx);
		System.out.println(resp.getStatusLine());

		String response = null;
		if (resp.getEntity() != null) {
			response = EntityUtils.toString(resp.getEntity());
		}

		return response;
	}

	@Override
	public Folder addFolder(String parentFolderId, String name, String description) throws Exception {
		// Add AuthCache to the execution context
		BasicHttpContext ctx = new BasicHttpContext();
		ctx.setAttribute(ClientContext.AUTH_CACHE, authCache);

		HttpPost post = new HttpPost("/api/secure/jsonws/dlapp/add-folder");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("repositoryId", repositoryId));
		params.add(new BasicNameValuePair("parentFolderId", parentFolderId));
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("description", description));

		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
		post.setEntity(entity);

		HttpResponse resp = httpclient.execute(targetHost, post, ctx);
		System.out.println(resp.getStatusLine());

		String response = null;
		if (resp.getEntity() != null) {
			response = EntityUtils.toString(resp.getEntity());
		}

		JSONDeserializer<Folder> deserializer = new JSONDeserializer<Folder>();
		Folder fldr = deserializer.deserialize(response, Folder.class);

		System.out.println("addFolder Res:[" + response + "]");
		return fldr;
	}

	@Override
	public FileEntry addorUpdateFileEntry(String folderId, String sourceFileName, String mimeType, String title, String description)
			throws Exception {
		FileEntry fe = null;
		if (fileEntryExists(folderId, title)) {
			FileEntry fe_ = getFileEntryByTitle(folderId, title);
			fe = updateFileEntry(fe_.getFileEntryId(), sourceFileName, mimeType, title, description);
		} else {
			fe = addFileEntry(folderId, sourceFileName, mimeType, title, description);
		}

		return fe;
	}

	@Override
	public FileEntry addorUpdateFileEntry(String folderId, File sourceFile, String mimeType, String title, String description)
			throws Exception {
		FileEntry fe = null;
		if (fileEntryExists(folderId, title)) {
			FileEntry fe_ = getFileEntryByTitle(folderId, title);
			fe = updateFileEntry(fe_.getFileEntryId(), sourceFile, mimeType, title, description);
		} else {
			fe = addFileEntry(folderId, sourceFile, mimeType, title, description);
		}

		return fe;
	}

	private FileEntry updateFileEntry(long fileEntryId, String sourceFileName, String mimeType, String title, String description)
			throws Exception {
		// Add AuthCache to the execution context
		BasicHttpContext ctx = new BasicHttpContext();
		ctx.setAttribute(ClientContext.AUTH_CACHE, authCache);

		HttpPost post = new HttpPost("/api/secure/jsonws/dlapp/update-file-entry");
		MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

		final URL testfile = LiferayPortalDocumentRepositoryImpl.class.getResource(sourceFileName);// "/bol.pdf");
		File file = null;
		if (Validator.isNotNull(testfile)) {
			file = new File(testfile.toURI());
		} else {
			file = new File(sourceFileName);
			sourceFileName = title;
		}

		entity.addPart("fileEntryId", new StringBody(Long.toString(fileEntryId), Charset.forName("UTF-8")));
		entity.addPart("sourceFileName", new StringBody(sourceFileName, Charset.forName("UTF-8")));
		entity.addPart("mimeType ", new StringBody(mimeType, Charset.forName("UTF-8")));
		entity.addPart("title", new StringBody(title, Charset.forName("UTF-8")));
		entity.addPart("description", new StringBody(description, Charset.forName("UTF-8")));
		entity.addPart("changeLog", new StringBody("", Charset.forName("UTF-8")));
		entity.addPart("file", new FileBody(file, mimeType, sourceFileName));
		// entity.addPart("bytes", new
		// ByteArrayBody("Test Content".getBytes(),mimeType,sourceFileName));
		entity.addPart("majorVersion", new StringBody("false", Charset.forName("UTF-8")));
		entity.addPart("serviceContext", new StringBody("{}", Charset.forName("UTF-8")));

		post.setEntity(entity);

		HttpResponse resp = httpclient.execute(targetHost, post, ctx);
		System.out.println(resp.getStatusLine());

		String response = null;
		if (resp.getEntity() != null) {
			response = EntityUtils.toString(resp.getEntity());
		}
		System.out.println("updateFileEntry Res:[" + response + "]");

		FileEntry fe = null;
		if (!StringUtil.contains(response, "com.liferay.portlet.documentlibrary.NoSuchFileEntryException", "")) {
			JSONDeserializer<FileEntry> deserializer = new JSONDeserializer<FileEntry>();
			fe = deserializer.deserialize(response, FileEntry.class);
		}
		return fe;
	}

	private FileEntry updateFileEntry(long fileEntryId, File sourceFile, String mimeType, String title, String description)
			throws Exception {
		// Add AuthCache to the execution context
		BasicHttpContext ctx = new BasicHttpContext();
		ctx.setAttribute(ClientContext.AUTH_CACHE, authCache);

		HttpPost post = new HttpPost("/api/secure/jsonws/dlapp/update-file-entry");
		MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

		entity.addPart("fileEntryId", new StringBody(Long.toString(fileEntryId), Charset.forName("UTF-8")));
		entity.addPart("sourceFileName", new StringBody(sourceFile.getName(), Charset.forName("UTF-8")));
		entity.addPart("mimeType ", new StringBody(mimeType, Charset.forName("UTF-8")));
		entity.addPart("title", new StringBody(title, Charset.forName("UTF-8")));
		entity.addPart("description", new StringBody(description, Charset.forName("UTF-8")));
		entity.addPart("changeLog", new StringBody("", Charset.forName("UTF-8")));
		entity.addPart("file", new FileBody(sourceFile, mimeType, sourceFile.getName()));
		// entity.addPart("bytes", new
		// ByteArrayBody("Test Content".getBytes(),mimeType,sourceFileName));
		entity.addPart("majorVersion", new StringBody("false", Charset.forName("UTF-8")));
		entity.addPart("serviceContext", new StringBody("{}", Charset.forName("UTF-8")));

		post.setEntity(entity);

		HttpResponse resp = httpclient.execute(targetHost, post, ctx);
		System.out.println(resp.getStatusLine());

		String response = null;
		if (resp.getEntity() != null) {
			response = EntityUtils.toString(resp.getEntity());
		}
		System.out.println("updateFileEntry Res:[" + response + "]");

		FileEntry fe = null;
		if (!StringUtil.contains(response, "com.liferay.portlet.documentlibrary.NoSuchFileEntryException", "")
				&& !StringUtil.contains(response, "com.liferay.portal.kernel.exception.SystemException", "")) {
			JSONDeserializer<FileEntry> deserializer = new JSONDeserializer<FileEntry>();
			fe = deserializer.deserialize(response, FileEntry.class);
		}
		return fe;
	}

	@Override
	public FileEntry addFileEntry(String folderId, File sourceFile, String mimeType, String title, String description) throws Exception {
		// Add AuthCache to the execution context
		BasicHttpContext ctx = new BasicHttpContext();
		ctx.setAttribute(ClientContext.AUTH_CACHE, authCache);

		HttpPost post = new HttpPost("/api/secure/jsonws/dlapp/add-file-entry");
		MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

		entity.addPart("repositoryId", new StringBody(repositoryId, Charset.forName("UTF-8")));
		entity.addPart("folderId", new StringBody(folderId, Charset.forName("UTF-8")));
		entity.addPart("sourceFileName", new StringBody(sourceFile.getName(), Charset.forName("UTF-8")));
		entity.addPart("mimeType ", new StringBody(mimeType, Charset.forName("UTF-8")));
		entity.addPart("title", new StringBody(title, Charset.forName("UTF-8")));
		entity.addPart("description", new StringBody(description, Charset.forName("UTF-8")));
		entity.addPart("changeLog", new StringBody("", Charset.forName("UTF-8")));
		entity.addPart("file", new FileBody(sourceFile, mimeType, sourceFile.getName()));
		// entity.addPart("bytes", new
		// ByteArrayBody("Test Content".getBytes(),mimeType,sourceFileName));
		entity.addPart("size", new StringBody("0", Charset.forName("UTF-8")));
		entity.addPart("serviceContext", new StringBody("{}", Charset.forName("UTF-8")));

		post.setEntity(entity);

		HttpResponse resp = httpclient.execute(targetHost, post, ctx);
		System.out.println(resp.getStatusLine());

		String response = null;
		if (resp.getEntity() != null) {
			response = EntityUtils.toString(resp.getEntity());
		}
		System.out.println("addFileEntry Res:[" + response + "]");

		FileEntry fe = null;
		if (!StringUtil.contains(response, "com.liferay.portlet.documentlibrary.NoSuchFileEntryException", "")
				&& !StringUtil.contains(response, "com.liferay.portal.NoSuchRepositoryEntryException", "")) {
			JSONDeserializer<FileEntry> deserializer = new JSONDeserializer<FileEntry>();
			fe = deserializer.deserialize(response, FileEntry.class);
		}
		return fe;
	}

	@Override
	public FileEntry addFileEntry(String folderId, String sourceFileName, String mimeType, String title, String description)
			throws Exception {
		// Add AuthCache to the execution context
		BasicHttpContext ctx = new BasicHttpContext();
		ctx.setAttribute(ClientContext.AUTH_CACHE, authCache);

		HttpPost post = new HttpPost("/api/secure/jsonws/dlapp/add-file-entry");
		MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

		final URL testfile = LiferayPortalDocumentRepositoryImpl.class.getResource(sourceFileName);// "/bol.pdf");
		File file = null;
		if (Validator.isNotNull(testfile)) {
			file = new File(testfile.toURI());
		} else {
			file = new File(sourceFileName);
			sourceFileName = title;
		}

		entity.addPart("repositoryId", new StringBody(repositoryId, Charset.forName("UTF-8")));
		entity.addPart("folderId", new StringBody(folderId, Charset.forName("UTF-8")));
		entity.addPart("sourceFileName", new StringBody(sourceFileName, Charset.forName("UTF-8")));
		entity.addPart("mimeType ", new StringBody(mimeType, Charset.forName("UTF-8")));
		entity.addPart("title", new StringBody(title, Charset.forName("UTF-8")));
		entity.addPart("description", new StringBody(description, Charset.forName("UTF-8")));
		entity.addPart("changeLog", new StringBody("", Charset.forName("UTF-8")));
		entity.addPart("file", new FileBody(file, mimeType, sourceFileName));
		// entity.addPart("bytes", new
		// ByteArrayBody("Test Content".getBytes(),mimeType,sourceFileName));
		entity.addPart("size", new StringBody("0", Charset.forName("UTF-8")));
		entity.addPart("serviceContext", new StringBody("{}", Charset.forName("UTF-8")));

		post.setEntity(entity);

		HttpResponse resp = httpclient.execute(targetHost, post, ctx);
		System.out.println(resp.getStatusLine());

		String response = null;
		if (resp.getEntity() != null) {
			response = EntityUtils.toString(resp.getEntity());
		}
		System.out.println("addFileEntry Res:[" + response + "]");

		FileEntry fe = null;
		if (!StringUtil.contains(response, "com.liferay.portlet.documentlibrary.NoSuchFileEntryException", "")
				&& !StringUtil.contains(response, "com.liferay.portal.NoSuchRepositoryEntryException", "")) {
			JSONDeserializer<FileEntry> deserializer = new JSONDeserializer<FileEntry>();
			fe = deserializer.deserialize(response, FileEntry.class);
		}
		return fe;
	}

	@Override
	public String getConxlogiFolderId() {
		return fflogiFolderId;
	}

	@Override
	public boolean fileEntryExists(String parentFolderId, String title) throws Exception {
		FileEntry fe = getFileEntryByTitle(parentFolderId, title);
		return fe != null;
	}

	@Override
	public FileEntry getFileEntryByTitle(String parentFolderId, String title) throws Exception {
		// Add AuthCache to the execution context
		BasicHttpContext ctx = new BasicHttpContext();
		ctx.setAttribute(ClientContext.AUTH_CACHE, authCache);

		HttpPost post = new HttpPost("/api/secure/jsonws/dlapp/get-file-entry");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("folderId", parentFolderId));
		params.add(new BasicNameValuePair("groupId", loginGroupId));
		params.add(new BasicNameValuePair("title", title));

		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
		post.setEntity(entity);

		HttpResponse resp = httpclient.execute(targetHost, post, ctx);
		System.out.println("getFolderById Status:[" + resp.getStatusLine() + "]");

		String response = null;
		if (resp.getEntity() != null) {
			response = EntityUtils.toString(resp.getEntity());
		}
		System.out.println("getFileEntry Res:[" + response + "]");

		FileEntry fe = null;
		if (!StringUtil.contains(response, "com.liferay.portlet.documentlibrary.NoSuchFileEntryException", "")
				&& !StringUtil.contains(response, "com.liferay.portal.NoSuchRepositoryEntryException", "")) {
			JSONDeserializer<FileEntry> deserializer = new JSONDeserializer<FileEntry>();
			fe = deserializer.deserialize(response, FileEntry.class);
		}
		return fe;
	}

	@Override
	public Folder provideFolderForEntity(EntityType entityType, Long entityId) throws Exception {
		String recordTypeFolderName = entityType.getEntityJavaSimpleType();
		if (recordTypeFolderName.endsWith("s"))
			recordTypeFolderName += "es";
		else
			recordTypeFolderName += "s";

		String recordFolderName = entityType.getEntityJavaSimpleType() + "-" + entityId;

		// -- Ensure type folder
		Folder parentFldr = ensureFolderByName(fflogiFolderId, recordTypeFolderName);

		// -- Ensure record folder
		Folder fldr = ensureFolderByName(Long.toString(parentFldr.getFolderId()), recordFolderName);
		return fldr;
	}

	private Folder ensureFolderByName(String parentFolderId, String folderName) throws Exception {
		Folder res = getFolderByName(parentFolderId, folderName);
		if (Validator.isNull(res)) {
			res = addFolder(parentFolderId, folderName, folderName);
		}
		return res;
	}

	private Folder provideFolderByJavaTypeName(String folderName) throws Exception {
		// -- Create remote
		Folder fldr = addFolder(fflogiFolderId, folderName, "Attachments for Record[" + folderName + "]");

		// -- Create local
		fldr.setName(folderName);
		fldr = folderDAOService.provide(fldr);
		return fldr;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Folder provideFolderForEntity(Class entityJavaClass, Long entityId) throws Exception {
		String st = entityJavaClass.getSimpleName();
		String folderName = st + "-" + entityId;

		return provideFolderByJavaTypeName(folderName);
	}

	@Override
	public FileEntry addorUpdateFileEntry(BaseEntity entity, DocType attachmentType, String sourceFileName, String mimeType, String title,
			String description) throws Exception {
		FileEntry fe = null;
		Folder df = entity.getDocFolder();

		fe = addorUpdateFileEntry(Long.toString(df.getFolderId()), sourceFileName, mimeType, title, description);

		fe = folderDAOService.addFileEntry(df.getFolderId(), attachmentType, fe);

		return fe;
	}

	@Override
	public FileEntry addorUpdateFileEntry(BaseEntity entity, DocType attachmentType, File sourceFile, String mimeType, String title,
			String description) throws Exception {
		FileEntry fe = null;
		Folder df = provideFolderForEntity(entity).getDocFolder();

		fe = addorUpdateFileEntry(Long.toString(df.getFolderId()), sourceFile, mimeType, title, description);

		fe = folderDAOService.addFileEntry(df.getFolderId(), attachmentType, fe);

		return fe;
	}

	@Override
	public FileEntry addorUpdateFileEntry(Folder folder, DocType attachmentType, String sourceFileName, String mimeType, String title,
			String description) throws Exception {
		FileEntry fe = addorUpdateFileEntryOnRepoOnly(folder, attachmentType, sourceFileName, mimeType, title, description);

		fe = folderDAOService.addFileEntry(folder.getFolderId(), attachmentType, fe);

		return fe;
	}

	@Override
	public FileEntry addorUpdateFileEntryOnRepoOnly(Folder folder, DocType attachmentType, String sourceFileName, String mimeType,
			String title, String description) throws Exception {
		FileEntry fe = addorUpdateFileEntry(Long.toString(folder.getFolderId()), sourceFileName, mimeType, title, description);
		return fe;
	}

	@Override
	public BaseEntity provideFolderForEntity(BaseEntity entity) throws Exception {
		assert (entity != null) : "The entity was null.";
		assert (entity.getId() != null || entity.getCode() != null) : "The entity was not persistent.";

		EntityType entityType = this.entityTypeDAOService.provide(entity.getClass());
		assert (entityType != null) : "Could not provide an EntityType for this entity";
		Folder newFolder = this.provideFolderForEntity(entityType, entity.getId());
		newFolder = em.merge(newFolder);
		assert (newFolder != null) : "Could not create a folder for this entity";
		entity.setDocFolder(newFolder);
		
		return em.merge(entity);
	}

	public IEntityTypeDAOService getEntityTypeDAOService() {
		return entityTypeDAOService;
	}

	public void setEntityTypeDAOService(IEntityTypeDAOService entityTypeDAOService) {
		this.entityTypeDAOService = entityTypeDAOService;
	}
}
