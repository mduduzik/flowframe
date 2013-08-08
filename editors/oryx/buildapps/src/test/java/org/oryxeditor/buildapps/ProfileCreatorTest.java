package org.oryxeditor.buildapps;

import com.yahoo.platform.yui.compressor.JavaScriptCompressor;
import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.naming.spi.DirectoryManager;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

@Ignore
public class ProfileCreatorTest {

    private String pluginDirPath = "";
    String pluginXMLPath;
    String profilePath;
    private String outputPath;
    private String pluginDir;


    @Before
	public void setUp() {
		try {
            URL pluginXMLPathURL = ProfileCreatorTest.class.getResource("/Plugins/plugins.xml");
            URL profilePathURL = ProfileCreatorTest.class.getResource("/Plugins/profiles.xml");
			pluginXMLPath = pluginXMLPathURL.toURI().getPath();
			profilePath = profilePathURL.toURI().getPath();
            pluginDir = new File(pluginXMLPath).getParentFile().getAbsolutePath();
            outputPath = createTempDirectory().getAbsolutePath();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Test
    public void testExecute() {
        try{
            ProfileCreator.execute(pluginXMLPath,profilePath,pluginDir,outputPath,true);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static File createTempDirectory()
            throws IOException
    {
        final File temp;

        temp = File.createTempFile("temp", Long.toString(System.nanoTime()));

        if(!(temp.delete()))
        {
            throw new IOException("Could not delete temp file: " + temp.getAbsolutePath());
        }

        if(!(temp.mkdir()))
        {
            throw new IOException("Could not create temp directory: " + temp.getAbsolutePath());
        }

        return (temp);
    }
}
