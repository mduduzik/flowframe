package org.flowframe.documentlibrary.remote.services.impl.min;

import flexjson.JSONDeserializer;
import flexjson.ObjectBinder;
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
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.impl.conn.SchemeRegistryFactory;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.flowframe.documentlibrary.remote.services.IRemoteDocumentRepository;
import org.flowframe.kernel.common.mdm.domain.BaseEntity;
import org.flowframe.kernel.common.mdm.domain.documentlibrary.DocType;
import org.flowframe.kernel.common.mdm.domain.documentlibrary.FileEntry;
import org.flowframe.kernel.common.mdm.domain.documentlibrary.Folder;
import org.flowframe.kernel.common.mdm.domain.metamodel.EntityType;
import org.flowframe.kernel.common.utils.HTTPUtil;
import org.flowframe.kernel.common.utils.StringUtil;
import org.flowframe.kernel.common.utils.Validator;
import org.flowframe.kernel.metamodel.dao.services.IEntityTypeDAOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;

@Transactional
public class LiferayPortalDocumentRepositoryImpl implements IRemoteDocumentRepository {

	//@PersistenceContext
	//private EntityManager em;

	static public final String FFDOCREPO_SERVER_HOSTNAME = "ffdocrepo.server.hostname";// localhost
	static public final String FFDOCREPO_SERVER_PORT = "ffdocrepo.server.port";// 8080
	static public final String FFDOCREPO_REPOSITORY_ID = "ffdocrepo.repository.id";// 10180
	static public final String FFDOCREPO_REPOSITORY_COMPANYID = "ffdocrepo.repository.companyid";// 10154
	static public final String FFDOCREPO_REPOSITORY_MAIN_FOLDERID = "ffdocrepo.repository.main.folderid";// 10644
	static public final String FFDOCREPO_USER_EMAIL = "ffdocrepo.user.email";// test@liferay.com
	static public final String FFDOCREPO_USER_PASSWORD = "ffdocrepo.user.password";// test
	static public final String FFDOCREPO_USER_GROUP_ID = "ffdocrepo.user.group.id";// 10180

	static public Properties liferayProperties = new Properties();

	public static BasicAuthCache authCache;
	public static DefaultHttpClient httpclient;
	public static HttpHost targetHost;
	
	public static String repositoryId;
	public static String companyId;
	public static String fflogiFolderId;
	public static String loginEmail;
	public static String loginPassword;
	public static String hostname;
	public static String port;
	public static String loginGroupId;

	//@Autowired
	//private IFolderDAOService folderDAOService;
    public LiferayPortalDocumentRepositoryImpl() {
    }

    public  LiferayPortalDocumentRepositoryImpl(String repositoryId, String companyId, String folderId, String loginEmail, String loginPassword, String hostname, String port, String loginGroupId) {
        this.repositoryId = repositoryId;
        this.companyId = companyId;
        this.fflogiFolderId = folderId;
        this.loginEmail = loginEmail;
        this.loginPassword = loginPassword;
        this.hostname = hostname;
        this.port = port;
        this.loginGroupId = loginGroupId;
    }

	@Autowired(required = false)
	private IEntityTypeDAOService entityTypeDAOService;
	
	private Boolean initialized = false;
	
	@Override
	public void init() {
		initProperties();

        //Ping
        try {
            if (!pingDLServer())
                throw new IllegalArgumentException("DocRepo not ping'able");
        } catch (Exception e) {
            throw new IllegalArgumentException("Error ping'ing DocRepo");
        }

        initialized = true;
	}
	

	public static void initProperties() {
/*		loadLiferayProperties();

		hostname = liferayProperties.getProperty(FFDOCREPO_SERVER_HOSTNAME);
		port = liferayProperties.getProperty(FFDOCREPO_SERVER_PORT);
		repositoryId = liferayProperties.getProperty(FFDOCREPO_REPOSITORY_ID);
		companyId = liferayProperties.getProperty(FFDOCREPO_REPOSITORY_COMPANYID);
		fflogiFolderId = liferayProperties.getProperty(FFDOCREPO_REPOSITORY_MAIN_FOLDERID);
		loginGroupId = liferayProperties.getProperty(FFDOCREPO_USER_GROUP_ID);
		loginEmail = liferayProperties.getProperty(FFDOCREPO_USER_EMAIL);
		loginPassword = liferayProperties.getProperty(FFDOCREPO_USER_PASSWORD);
*/
		targetHost = new HttpHost(hostname, Integer.valueOf(port), "http");
		PoolingClientConnectionManager cxMgr = new PoolingClientConnectionManager( SchemeRegistryFactory.createDefault());
		cxMgr.setMaxTotal(100);
		cxMgr.setDefaultMaxPerRoute(20);		
		httpclient = new DefaultHttpClient(cxMgr);
		httpclient.getCredentialsProvider().setCredentials(new AuthScope(targetHost.getHostName(), targetHost.getPort()),
				new UsernamePasswordCredentials(loginEmail, loginPassword));

		// Create AuthCache instance
		authCache = new BasicAuthCache();
		// Generate BASIC scheme object and add it to the local
		// auth cache
		BasicScheme basicAuth = new BasicScheme();
		authCache.put(targetHost, basicAuth);

	}

	@Override
	public Boolean isAvailable() throws Exception {
		return initialized;
	}


    @Override
    public List<Folder> getSubFolders(String parentFolderId) throws Exception {
        // Add AuthCache to the execution context
        BasicHttpContext ctx = new BasicHttpContext();
        ctx.setAttribute(ClientContext.AUTH_CACHE, authCache);

        URIBuilder builder = new URIBuilder();
        builder.setPath("/api/secure/jsonws/dlapp/get-folders");
        builder.setParameter("parentFolderId", parentFolderId);
        builder.setParameter("repositoryId", repositoryId);
        URI uri = builder.build();

        HttpGet get = new HttpGet(uri.toString());
        HttpResponse resp = httpclient.execute(targetHost, get, ctx);
        System.out.println("getFolderById Status:[" + resp.getStatusLine() + "]");

        String response = null;
        if (resp.getEntity() != null) {
            response = EntityUtils.toString(resp.getEntity());
        }
        System.out.println("getFolderById Res:[" + response + "]");

        final JSONDeserializer<ArrayList<Map>> deserializer = new JSONDeserializer<ArrayList<Map>>().use(null, ArrayList.class);
        final List<Map> fldrs = deserializer.deserialize(response);

        final List<Folder> folders = new ArrayList<Folder>();
        Folder fldr = null;
        for (Map fldrMap : fldrs){
            ObjectBinder binder = new ObjectBinder();
            fldrMap.put("class",Folder.class.getName());
            fldr = (Folder)binder.bind( fldrMap );
            folders.add(fldr);
        }

        return folders;
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

    public boolean pingDLServer() throws Exception {
        // Add AuthCache to the execution context
        BasicHttpContext ctx = new BasicHttpContext();
        ctx.setAttribute(ClientContext.AUTH_CACHE, authCache);

        HttpPost post = new HttpPost("/api/secure/jsonws//company/get-company-by-id");
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("companyId ", companyId));


        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
        post.setEntity(entity);

        HttpResponse resp = httpclient.execute(targetHost, post, ctx);
        System.out.println(resp.getStatusLine());

        String response = null;
        if (resp.getEntity() != null) {
            response = EntityUtils.toString(resp.getEntity());
        }
        System.out.println("pingDLServer Res:[" + response + "]");

        if (resp.getStatusLine().getStatusCode() != 200) {
            return false;
        }
        else
            return true;
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
    public boolean isFolderEmpty(String folderId) throws Exception {
        // Add AuthCache to the execution context
        BasicHttpContext ctx = new BasicHttpContext();
        ctx.setAttribute(ClientContext.AUTH_CACHE, authCache);

        HttpPost post = new HttpPost("/api/secure/jsonws/dlfolder/get-folders-and-file-entries-and-file-shortcuts-count");
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("groupId", loginGroupId));
        params.add(new BasicNameValuePair("folderId", folderId));
        params.add(new BasicNameValuePair("status", "1"));
        params.add(new BasicNameValuePair("includeMountFolders", "false"));

        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
        post.setEntity(entity);

        HttpResponse resp = httpclient.execute(targetHost, post, ctx);
        System.out.println(resp.getStatusLine());

        String response = null;
        int count = 0;
        if (resp.getEntity() != null) {
            response = EntityUtils.toString(resp.getEntity());
            count = Integer.valueOf(response);
        }

        return (count < 1);
    }

    @Override
	public List<FileEntry> getFileEntries(String folderId) throws Exception {
        // Add AuthCache to the execution context
        BasicHttpContext ctx = new BasicHttpContext();
        ctx.setAttribute(ClientContext.AUTH_CACHE, authCache);

        URIBuilder builder = new URIBuilder();
        builder.setPath("/api/secure/jsonws/dlapp/get-file-entries");
        builder.setParameter("folderId", folderId);
        builder.setParameter("repositoryId", repositoryId);
        URI uri = builder.build();

        HttpGet get = new HttpGet(uri.toString());
        HttpResponse resp = httpclient.execute(targetHost, get, ctx);
        System.out.println("getFileEntries Status:[" + resp.getStatusLine() + "]");

        String response = null;
        if (resp.getEntity() != null) {
            response = EntityUtils.toString(resp.getEntity());
        }
        System.out.println("getFileEntries Res:[" + response + "]");

        JSONDeserializer<ArrayList<Map>> deserializer = new JSONDeserializer<ArrayList<Map>>().use(null, ArrayList.class);
        List<Map> feMapList = deserializer.deserialize(response);

        List<FileEntry> fes = new ArrayList<FileEntry>();
        FileEntry fe = null;
        for (Map feMap : feMapList){
            ObjectBinder binder = new ObjectBinder();
            feMap.put("class",FileEntry.class.getName());
            fe = (FileEntry)binder.bind( feMap );
            fes.add(fe);
        }

        return fes;
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
    public FileEntry moveFileEntryById(String fileEntryId, String folderId) throws Exception {
        // Add AuthCache to the execution context
        BasicHttpContext ctx = new BasicHttpContext();
        ctx.setAttribute(ClientContext.AUTH_CACHE, authCache);

        HttpPost post = new HttpPost("/api/secure/jsonws/dlapp/move-file-entry");
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("fileEntryId", fileEntryId));
        params.add(new BasicNameValuePair("newFolderId", folderId));

        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
        post.setEntity(entity);


        HttpResponse resp = httpclient.execute(targetHost, post, ctx);
        System.out.println("moveFileEntryById Status:[" + resp.getStatusLine() + "]");

        String response = null;
        if (resp.getEntity() != null) {
            response = EntityUtils.toString(resp.getEntity());
        }
        System.out.println("moveFileEntryById Res:[" + response + "]");

        if (StringUtil.contains(response, "Exception", "")) {
            throw new IllegalArgumentException("Error moving file "+fileEntryId+" to Folder "+folderId+": "+response);
        }

        JSONDeserializer<FileEntry> deserializer = new JSONDeserializer<FileEntry>();
        FileEntry fe = deserializer.deserialize(response, FileEntry.class);

        return fe;
    }

	@Override
	public InputStream getFileAsStream(String fileEntryId, String version) throws Exception {
		BasicHttpContext ctx = new BasicHttpContext();
		ctx.setAttribute(ClientContext.AUTH_CACHE, authCache);

		HttpPost post = new HttpPost("/api/secure/jsonws/dlfileentry/get-file-as-stream");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("fileEntryId", fileEntryId));
		params.add(new BasicNameValuePair("version", version));

		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
		post.setEntity(entity);

		HttpResponse resp = httpclient.execute(targetHost, post, ctx);
		System.out.println("getFileAsStream Status:[" + resp.getStatusLine() + "]");
		System.out.println("getFileAsStream Res:[" + resp + "]");
		
		InputStream in = resp.getEntity().getContent();
		byte[] contentByteArray = EntityUtils.toByteArray(resp.getEntity());

		return new ByteArrayInputStream(contentByteArray);
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
	public InputStream getFileEntryInputStreamWithURL(String fileEntryId, String version) throws Exception {
		FileEntry fe = getFileEntryById(fileEntryId);
		// TODO Auto-generated method stub
		// http://localhost:8080/documents/10180/16279/Bill+Of+Laden/7b30b6bd-4174-40e3-aadb-63f2cbadd8fe
		// http://<host>:<port>/documents/<groupd.id>/<folder.id>/<url_encode_title>/<uuid>
		String encodedUrl = "/documents/"+fe.getGroupId()+"/"+fe.getFolderId()+"/"+HTTPUtil.encodeURL(fe.getTitle(), true)+"/"+fe.getUuid();
		System.out.println("getFileEntryInputStreamWithURL URL:[" + encodedUrl + "]");		
		
		//BasicHttpContext ctx = new BasicHttpContext();
		//ctx.setAttribute(ClientContext.AUTH_CACHE, authCache);

		HttpGet get = new HttpGet(encodedUrl);

		HttpResponse resp = httpclient.execute(get);
		System.out.println("getFileEntryInputStreamWithURL Status:[" + resp.getStatusLine() + "]");
		System.out.println("getFileEntryInputStreamWithURL Res:[" + resp + "]");
		
		InputStream in = resp.getEntity().getContent();
		byte[] contentByteArray = EntityUtils.toByteArray(resp.getEntity());
		
		System.out.println("getFileEntryInputStreamWithURL StreamLength:[" + contentByteArray.length + "]");

		return new ByteArrayInputStream(contentByteArray);
	}	
	

	@Override
	public FileEntry deleteFileEntryById(String folderId, String fileEntryId) throws Exception {
        //
        // Add AuthCache to the execution context
        BasicHttpContext ctx = new BasicHttpContext();
        ctx.setAttribute(ClientContext.AUTH_CACHE, authCache);

        HttpPost post = new HttpPost("/api/secure/jsonws/dlapp/delete-file-entry");
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("fileEntryId", fileEntryId));

        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
        post.setEntity(entity);

        HttpResponse resp = httpclient.execute(targetHost, post, ctx);
        System.out.println("deleteFileEntryById Status:[" + resp.getStatusLine() + "]");

        String response = null;
        if (resp.getEntity() != null) {
            response = EntityUtils.toString(resp.getEntity());
        }
        System.out.println("deleteFileEntryById Res:[" + response + "]");

        return null;
	}



    public static Properties loadLiferayProperties() {
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
	public Folder addFolder(String name, String description) throws Exception {
		return addFolder(fflogiFolderId,name,description);
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

        File file = null;
        FileBody fb = null;
        StringBody sb = null;
        if (Validator.isNotNull(sourceFileName)) {
            final URL testfile = LiferayPortalDocumentRepositoryImpl.class.getResource(sourceFileName);// "/bol.pdf");
            if (Validator.isNotNull(testfile)) {
                file = new File(testfile.toURI());
            } else {
                file = new File(sourceFileName);
                sourceFileName = title;
            }
            fb = new FileBody(file, mimeType, sourceFileName);
            sb = new StringBody(sourceFileName, Charset.forName("UTF-8"));
        }


		entity.addPart("fileEntryId", new StringBody(Long.toString(fileEntryId), Charset.forName("UTF-8")));
		entity.addPart("sourceFileName", sb);
		entity.addPart("mimeType ", new StringBody(mimeType, Charset.forName("UTF-8")));
		entity.addPart("title", new StringBody(title, Charset.forName("UTF-8")));
		entity.addPart("description", new StringBody(description, Charset.forName("UTF-8")));
		entity.addPart("changeLog", new StringBody("", Charset.forName("UTF-8")));
		entity.addPart("file", fb);
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

    @Override
    public FileEntry renameFileEntry(String fileEntryId, String title, String description)
            throws Exception {
        // Add AuthCache to the execution context
        BasicHttpContext ctx = new BasicHttpContext();
        ctx.setAttribute(ClientContext.AUTH_CACHE, authCache);

        HttpPost post = new HttpPost("/api/secure/jsonws/dlapp/update-file-entry");
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("fileEntryId", fileEntryId));
        params.add(new BasicNameValuePair("sourceFileName", ""));
        params.add(new BasicNameValuePair("mimeType", ""));
        params.add(new BasicNameValuePair("title", title));
        params.add(new BasicNameValuePair("description", description));
        params.add(new BasicNameValuePair("changeLog", ""));
        params.add(new BasicNameValuePair("bytes", ""));
        params.add(new BasicNameValuePair("majorVersion", "false"));

        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
        post.setEntity(entity);


        HttpResponse resp = httpclient.execute(targetHost, post, ctx);
        System.out.println(resp.getStatusLine());

        String response = null;
        if (resp.getEntity() != null) {
            response = EntityUtils.toString(resp.getEntity());
        }
        System.out.println("renameFileEntry Res:[" + response + "]");

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
    public FileEntry copyFileEntryById(String destFolderId, String originFileEntryId, String originalFilename, String originalExt, String newFilename) throws Exception {
        //Check for same filename
        if (fileEntryExists(destFolderId,originalFilename)) {
            FileEntry existingFE = getFileEntryByTitle(destFolderId, originalFilename);
            renameFileEntry(Long.toString(existingFE.getFileEntryId()),originalFilename+"_renamed_"+System.currentTimeMillis()+"."+originalExt,"Renamed from "+originalFilename);
        }
        // Add AuthCache to the execution context
        BasicHttpContext ctx = new BasicHttpContext();
        ctx.setAttribute(ClientContext.AUTH_CACHE, authCache);

        HttpPost post = new HttpPost("/api/secure/jsonws/dlfileentry/copy-file-entry");
        MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);


        entity.addPart("groupId", new StringBody(loginGroupId, Charset.forName("UTF-8")));
        entity.addPart("repositoryId", new StringBody(repositoryId, Charset.forName("UTF-8")));
        entity.addPart("destFolderId", new StringBody(destFolderId, Charset.forName("UTF-8")));
        entity.addPart("fileEntryId ", new StringBody(originFileEntryId, Charset.forName("UTF-8")));
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

        //--Rename
        if (!fe.getTitle().equals(newFilename)) {
            fe = renameFileEntry(Long.toString(fe.getFileEntryId()), newFilename, "Copied from FE ID("+originFileEntryId+")");
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

	@Override
	public Folder ensureFolderByName(String parentFolderId, String folderName) throws Exception {
		Folder res = getFolderByName(parentFolderId, folderName);
		if (Validator.isNull(res)) {
			res = addFolder(parentFolderId, folderName, folderName);
		}
		return res;
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
		//newFolder = em.merge(newFolder);
		assert (newFolder != null) : "Could not create a folder for this entity";
		entity.setDocFolder(newFolder);
		
		//entity = em.merge(entity);
		
		return entity;
	}

	public IEntityTypeDAOService getEntityTypeDAOService() {
		return entityTypeDAOService;
	}

	public void setEntityTypeDAOService(IEntityTypeDAOService entityTypeDAOService) {
		this.entityTypeDAOService = entityTypeDAOService;
	}
	
	public static String getRepositoryId() {
		return repositoryId;
	}


	public static void setRepositoryId(String repositoryId) {
		LiferayPortalDocumentRepositoryImpl.repositoryId = repositoryId;
	}

	public static String getCompanyId() {
		return companyId;
	}


	public static void setCompanyId(String companyId) {
		LiferayPortalDocumentRepositoryImpl.companyId = companyId;
	}


	public static String getFflogiFolderId() {
		return fflogiFolderId;
	}


	public static void setFflogiFolderId(String fflogiFolderId) {
		LiferayPortalDocumentRepositoryImpl.fflogiFolderId = fflogiFolderId;
	}


	public static String getLoginEmail() {
		return loginEmail;
	}


	public static void setLoginEmail(String loginEmail) {
		LiferayPortalDocumentRepositoryImpl.loginEmail = loginEmail;
	}


	public static String getLoginPassword() {
		return loginPassword;
	}


	public static void setLoginPassword(String loginPassword) {
		LiferayPortalDocumentRepositoryImpl.loginPassword = loginPassword;
	}


	public static String getHostname() {
		return hostname;
	}


	public static void setHostname(String hostname) {
		LiferayPortalDocumentRepositoryImpl.hostname = hostname;
	}


	public static String getPort() {
		return port;
	}


	public static void setPort(String port) {
		LiferayPortalDocumentRepositoryImpl.port = port;
	}


	public static String getLoginGroupId() {
		return loginGroupId;
	}


	public static void setLoginGroupId(String loginGroupId) {
		LiferayPortalDocumentRepositoryImpl.loginGroupId = loginGroupId;
	}	
}
