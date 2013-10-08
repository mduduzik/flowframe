package org.flowframe.documentlibrary.remote.services.impl.tests;


import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.flowframe.documentlibrary.remote.services.impl.LiferayPortalDocumentRepositoryImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Ignore
public class SimpleLiferayPortalDocumentRepositoryTests {

	@Before
	public void setUp() throws Exception {
		
	}	

    @Ignore
    @Test
    public void testDownloadFileEntryAsStream() throws Exception {
    	LiferayPortalDocumentRepositoryImpl.initProperties();
/*		BasicHttpContext ctx = new BasicHttpContext();
		ctx.setAttribute(ClientContext.AUTH_CACHE, LiferayPortalDocumentRepositoryImpl.authCache);

		HttpGet get = new HttpGet("/api/secure/jsonws/dlfileentry/get-file-as-stream");
		
		HttpParams params = new SyncBasicHttpParams();
		params.setParameter("fileEntryId", 40522);
		params.setParameter("version", null);
		 
		get.setParams(params);

		HttpResponse resp = LiferayPortalDocumentRepositoryImpl.httpclient.execute(LiferayPortalDocumentRepositoryImpl.targetHost, get, ctx);
		System.out.println("getFileAsStream Status:[" + resp.getStatusLine() + "]");*/
    	
		BasicHttpContext ctx = new BasicHttpContext();
		ctx.setAttribute(ClientContext.AUTH_CACHE, LiferayPortalDocumentRepositoryImpl.authCache);

		HttpPost post = new HttpPost("/api/secure/jsonws/dlfileentry/get-file-as-stream");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("fileEntryId", "40522"));
		params.add(new BasicNameValuePair("version", null));

		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
		post.setEntity(entity);

		HttpResponse resp = LiferayPortalDocumentRepositoryImpl.httpclient.execute(LiferayPortalDocumentRepositoryImpl.targetHost, post, ctx);
		System.out.println("testDownloadFileEntryAsStream Status:[" + resp.getStatusLine() + "]");
		System.out.println("testDownloadFileEntryAsStream Res:[" + resp + "]");
		
		InputStream in = resp.getEntity().getContent();
		byte[] contentByteArray = EntityUtils.toByteArray(resp.getEntity());

		//System.out.println("testDownloadFileEntryAsStream EntityUtils:["+responseString);

		File tmpFile = File.createTempFile("report", ".jasper");
		System.out.println("testDownloadFileEntryAsStream Tmp File:[" + tmpFile.getAbsolutePath() + "]");
		IOUtils.write(contentByteArray, new FileOutputStream(tmpFile));
		//resp.getEntity().writeTo(new FileOutputStream(tmpFile));
    	//IOUtils.copy(in, new FileOutputStream(tmpFile));
    	
    	Assert.assertEquals(tmpFile.length(), 170269);
    }   
    
    public static byte[] getBytes(InputStream is) throws IOException {

        int len;
        int size = 1024;
        byte[] buf;

        if (is instanceof ByteArrayInputStream) {
          size = is.available();
          buf = new byte[size];
          len = is.read(buf, 0, size);
        } else {
          ByteArrayOutputStream bos = new ByteArrayOutputStream();
          buf = new byte[size];
          while ((len = is.read(buf, 0, size)) != -1)
            bos.write(buf, 0, len);
          buf = bos.toByteArray();
        }
        return buf;
      }    
}
