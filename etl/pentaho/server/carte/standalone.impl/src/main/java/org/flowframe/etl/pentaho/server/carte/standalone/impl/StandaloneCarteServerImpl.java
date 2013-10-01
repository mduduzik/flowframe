package org.flowframe.etl.pentaho.server.carte.standalone.impl;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.impl.conn.SchemeRegistryFactory;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.flowframe.etl.pentaho.server.carte.services.ICarteJobService;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.xml.XMLHandler;
import org.pentaho.di.trans.TransConfiguration;
import org.pentaho.di.trans.TransExecutionConfiguration;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.www.AddTransServlet;
import org.pentaho.di.www.GetStatusServlet;
import org.pentaho.di.www.SlaveServerStatus;
import org.pentaho.di.www.SlaveServerTransStatus;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mduduzi on 10/1/13.
 */
public class StandaloneCarteServerImpl implements ICarteJobService {
    private String hostname;
    private String port;
    private HttpHost targetHost;
    private DefaultHttpClient httpclient;

    public void init() {
        targetHost = new HttpHost(hostname, Integer.valueOf(port), "http");
        PoolingClientConnectionManager cxMgr = new PoolingClientConnectionManager( SchemeRegistryFactory.createDefault());
        cxMgr.setMaxTotal(100);
        cxMgr.setDefaultMaxPerRoute(20);
        httpclient = new DefaultHttpClient(cxMgr);
    }

    @Override
    public SlaveServerTransStatus addTransformationJob(TransMeta transMeta) throws IOException, KettleException, URISyntaxException {
        TransExecutionConfiguration transExecConfig = new TransExecutionConfiguration();
        TransConfiguration transConfig = new TransConfiguration(transMeta, transExecConfig);

        String response = executeHttpGet(AddTransServlet.CONTEXT_PATH,
                new HashMap(){{
                    put("xml","Y");
                    put("test","test");}},
                transConfig.getXML(),
                null);

        Document document = XMLHandler.loadXMLString(response);
        NodeList nodes = document.getElementsByTagName("result");

        String result = nodes.item(0).getTextContent();


        SlaveServerStatus status = getStatus();
        SlaveServerTransStatus transStatus = status.findTransStatus(transMeta.getName(), null); // find the first one

        return transStatus;
    }

    @Override
    public SlaveServerTransStatus startTransformationJob(String transName) {
        return null;
    }

    @Override
    public SlaveServerTransStatus getTransformationJobStatus(String transName) {
        return null;
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

    private String executeHttpGet(String contextPath, Map<String,Object> params, String content, String contextType) throws IOException, URISyntaxException {
        HttpGetWithEntity get = new HttpGetWithEntity(contextPath);
        HttpParams httpParams = new BasicHttpParams();
        for (String key : params.keySet()){
            httpParams.setParameter(key, params.get(key));
        }

        if (content != null)
            get.setEntity(new StringEntity(content, ContentType.create("text/xml", "UTF-8")));
        get.setParams(httpParams);

        HttpResponse resp = httpclient.execute(targetHost,get);
        System.out.println(resp.getStatusLine());

        String response = null;
        if (resp.getEntity() != null) {
            response = EntityUtils.toString(resp.getEntity());
        }

        return response;
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
