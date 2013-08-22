package org.flowframe.etl.pentaho.repository.db.resource.etl.trans.steps.csvinput.tests;

import org.flowframe.etl.pentaho.repository.db.resource.etl.trans.steps.csvinput.CSVInputDialogDelegateResource;
import org.flowframe.etl.pentaho.repository.db.tests.TestsBase;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 * Created with IntelliJ IDEA.
 * User: Mduduzi
 * Date: 8/22/13
 * Time: 10:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class AddCsvInputMetadataTests   extends TestsBase {
    @Override
    protected ResourceConfig configure(ResourceConfig rc) {
        return rc.register(CSVInputDialogDelegateResource.class);
    }

    @Override
    protected String getResourcePath() {
        return "/csvinput";
    }

    @Test
    public void testResourceScope() throws Exception {
        WebTarget t = target(getResourceFullPath());
        Invocation.Builder builder = t.path("onnew").request().accept(MediaType.APPLICATION_JSON_TYPE).header("userid","test");
        String response = builder.get(String.class);

        builder = t.path("getcurrentlyedited").request().accept(MediaType.APPLICATION_JSON_TYPE).header("userid","test");
        response = builder.get(String.class);
        Assert.assertEquals("Test",response);
    }}