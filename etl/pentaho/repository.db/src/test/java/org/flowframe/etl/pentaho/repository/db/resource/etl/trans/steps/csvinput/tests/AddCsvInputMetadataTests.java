package org.flowframe.etl.pentaho.repository.db.resource.etl.trans.steps.csvinput.tests;

import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: Mduduzi
 * Date: 8/22/13
 * Time: 10:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class AddCsvInputMetadataTests {//}   extends TestsBase {
    private Client client = null;

    @Before
    public void setUp() {
        client = ClientBuilder.newBuilder()
                .register(MultiPartFeature.class)
                .build();
    }

    @After
    public void shutDown() {
        client.close();
    }

    protected String getResourcePath() {
        return "http://localhost:8082/etlrepo/csvinput";
    }

    @Test
    public void testResourceScope() throws Exception {
        WebTarget t = client.target(getResourcePath());

        //-- 1. New Meta
        Invocation.Builder builder = t.path("onnew").request().accept(MediaType.APPLICATION_JSON_TYPE).header("userid","test");
        String newMeta = builder.get(String.class);

        //-- 2. Check delegate
        builder = t.path("getcurrentlyedited").request().accept(MediaType.APPLICATION_JSON_TYPE).header("userid","test");
        String cacheMeta = builder.get(String.class);
        Assert.assertEquals(newMeta,cacheMeta);

        //-- 3. Post sample csv
        URL csv = AddCsvInputMetadataTests.class.getResource("/testfiles/sales_data.csv");
        File file = new File(csv.toURI());
        final FileDataBodyPart filePart = new FileDataBodyPart("ext-gen12345", file);
        final FormDataMultiPart multipart = (FormDataMultiPart)new FormDataMultiPart()
                .field("MAX_FILE_SIZE", "50000")
                .bodyPart(filePart);
        builder = t.path("uploadsample").request();
        final Response response_ = builder.post(Entity.entity(multipart, multipart.getMediaType()));

        //-- 4. Guess metadata
        builder = t.path("ongetmetadata").request().accept(MediaType.APPLICATION_JSON_TYPE).header("userid", "test");
        Response analyzedMetadataResponse = builder.post(Entity.entity(newMeta, MediaType.APPLICATION_JSON_TYPE));

    }


}