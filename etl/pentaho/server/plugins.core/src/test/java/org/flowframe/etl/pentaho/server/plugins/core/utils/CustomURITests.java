package org.flowframe.etl.pentaho.server.plugins.core.utils;

import junit.framework.Assert;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Mduduzi on 11/9/13.
 */
public class CustomURITests {
    @Test

    public void testCustomUri() throws URISyntaxException {
        String scheme = "ff";
        String authority = "repo";
        String path = "/internal";
        String query="fileentry";
        String fragment ="12345";
        URI test = new URI(scheme, authority, path, query, fragment);
        String str = test.toString();
        Assert.assertNotNull(str);

        test = new URI(str);
        str = test.toString();
        Assert.assertNotNull(str);
    }
}
