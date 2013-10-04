package org.flowframe.etl.pentaho.server.carte.standalone.impl;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.impl.conn.SchemeRegistryFactory;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.flowframe.etl.pentaho.server.carte.services.ICarteJobService;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.xml.XMLHandler;
import org.pentaho.di.trans.TransConfiguration;
import org.pentaho.di.trans.TransExecutionConfiguration;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.www.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Mduduzi on 10/1/13.
 */
public class StandaloneCarteServerImpl implements ICarteJobService {
    private static final Logger logger = LoggerFactory
            .getLogger(StandaloneCarteServerImpl.class);
    private String hostname;
    private String port;
    private HttpHost targetHost;
    private DefaultHttpClient httpclient;
    private BasicAuthCache authCache;

    public void init() {
        targetHost = new HttpHost(hostname, Integer.valueOf(port), "http");
        PoolingClientConnectionManager cxMgr = new PoolingClientConnectionManager( SchemeRegistryFactory.createDefault());
        cxMgr.setMaxTotal(100);
        cxMgr.setDefaultMaxPerRoute(20);
        httpclient = new DefaultHttpClient(cxMgr);
        httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_0);

        httpclient.getCredentialsProvider().setCredentials(new AuthScope(targetHost.getHostName(), targetHost.getPort()),
                new UsernamePasswordCredentials("cluster", "cluster"));

        // Create AuthCache instance
        authCache = new BasicAuthCache();
        // Generate BASIC scheme object and add it to the local
        // auth cache
        BasicScheme basicAuth = new BasicScheme();
        authCache.put(targetHost, basicAuth);
    }

    @Override
    public SlaveServerTransStatus addTransformationJob(TransMeta transMeta) throws IOException, KettleException, URISyntaxException {
        TransExecutionConfiguration transExecConfig = new TransExecutionConfiguration();
        TransConfiguration transConfig = new TransConfiguration(transMeta, transExecConfig);

        final String content = transConfig.getXML();
        String response = executeHttpPost(AddTransServlet.CONTEXT_PATH + "/",
                new HashMap() {{
                    put("xml", "Y");
                    put("length", content.length() + "");
                }},
                content,
                null);

        Document document = XMLHandler.loadXMLString(response);
        NodeList nodes = document.getElementsByTagName("result");

        String result = nodes.item(0).getTextContent();


        SlaveServerStatus status = getStatus();
        SlaveServerTransStatus transStatus = status.findTransStatus(transMeta.getName(), null); // find the first one

        return transStatus;
    }

    @Override
    public SlaveServerTransStatus startTransformationJob(final String transName) {
        final String carteObjectId = UUID.randomUUID().toString();
        try {
            String response = executeHttpGet(StartTransServlet.CONTEXT_PATH,
                    new HashMap(){{
                        put("xml","Y");
                        put("name",transName);
                        /*put("id",carteObjectId);*/}},
                    null,
                    null);
            return SlaveServerTransStatus.fromXML(response);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    @Override
    public SlaveServerTransStatus getTransformationJobStatus(final String transName,final String carteObjectId) {
        try {
            String response = executeHttpGet(GetStatusServlet.CONTEXT_PATH,
                    new HashMap(){{
                        put("xml","Y");
                        put("name",transName);
                        put("id",carteObjectId);}},
                    null,
                    null);
            return SlaveServerTransStatus.fromXML(response);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    @Override
    public SlaveServerTransStatus getTransformationJobStatus(final String transName) {
        try {
            String response = executeHttpGet(GetTransStatusServlet.CONTEXT_PATH,
                    new HashMap(){{
                        put("xml","Y");
                        put("name",transName);}},
                    null,
                    null);
            return SlaveServerTransStatus.fromXML(response);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    @Override
    public SlaveServerTransStatus executeTransformationJob(final String transName, final String level) {
        try {
            String response = executeHttpGet(ExecuteTransServlet.CONTEXT_PATH,
                    new HashMap(){{
                        put("xml","Y");
                        put("rep","ffmdmrepoetl");
                        put("user","admin");
                        put("pass","admin");
                        put("level",level);
                        put("trans",transName);}},
                    null,
                    null);
            return SlaveServerTransStatus.fromXML(response);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    @Override
    public SlaveServerTransStatus pauseTransformationJob(String transName) {
        return null;
    }

    @Override
    public SlaveServerStatus getCarteStatus() {
        return getStatus();
    }


    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getHostname() {
        return hostname;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPort() {
        return port;
    }


    /***
     *
     * Private methods
     *
     */
    public SlaveServerStatus getStatus() {

        try {
            String response = executeHttpGet(GetStatusServlet.CONTEXT_PATH,
                    new HashMap(){{
                        put("xml","Y");
                        put("test","test");}},
                    null,
                    null);
            return SlaveServerStatus.fromXML(response);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    private String executeHttpGet(String contextPath, Map<String,String> params, String content, String contextType) throws IOException, URISyntaxException {
        BasicHttpContext ctx = new BasicHttpContext();
        ctx.setAttribute(ClientContext.AUTH_CACHE, authCache);

        URIBuilder builder = new URIBuilder();
        builder.setPath(contextPath);
        for (String key : params.keySet()){
            builder.setParameter(key, params.get(key));
        }
        URI uri = builder.build();

        HttpGetWithEntity get = new HttpGetWithEntity(uri.toString());

        if (content != null)  {
            //get.setHeader("Content-Length",""+content.length());
            StringEntity entity = new StringEntity(content, ContentType.create("text/xml", "UTF-8"));
            long len = entity.getContentLength();
            get.setEntity(entity);
            printRequest(get);
        }

        HttpResponse resp = httpclient.execute(targetHost,get,ctx);
        System.out.println(resp.getStatusLine());

        String response = null;
        if (resp.getEntity() != null) {
            response = EntityUtils.toString(resp.getEntity());
        }

        return response;
    }

    private String executeHttpPost(String contextPath, Map<String,String> params, String content, String contextType) throws IOException, URISyntaxException {
        BasicHttpContext ctx = new BasicHttpContext();
        ctx.setAttribute(ClientContext.AUTH_CACHE, authCache);

        URIBuilder builder = new URIBuilder();
        builder.setPath(contextPath);
        for (String key : params.keySet()){
            builder.setParameter(key, params.get(key));
        }
        URI uri = builder.build();

        HttpPost get = new HttpPost(uri.toString());

        if (content != null)  {
            //get.setHeader("Content-Length",""+content.length());
            StringEntity entity = new StringEntity(content, ContentType.create("text/xml", "UTF-8"));
            long len = entity.getContentLength();
            get.setEntity(entity);
        }

        HttpResponse resp = httpclient.execute(targetHost,get,ctx);
        System.out.println(resp.getStatusLine());

        String response = null;
        if (resp.getEntity() != null) {
            response = EntityUtils.toString(resp.getEntity());
        }

        return response;
    }

    private void printRequest(HttpGetWithEntity request) {
        logger.info("*** Request headers ***");
        Header[] requestHeaders = request.getAllHeaders();
        for(Header header : requestHeaders) {
            logger.info(header.toString());
        }
        logger.info("***********************");
    }

    public class HttpGetWithEntity extends HttpEntityEnclosingRequestBase {
        public final static String METHOD_NAME = "GET";

        public HttpGetWithEntity(String contextPath) throws URISyntaxException {
            super();
            setURI(new URI(contextPath));
        }

        @Override
        public String getMethod() {
            return METHOD_NAME;
        }
    }
}
