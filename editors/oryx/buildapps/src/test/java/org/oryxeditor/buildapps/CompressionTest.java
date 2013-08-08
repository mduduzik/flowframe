package org.oryxeditor.buildapps;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.yahoo.platform.yui.compressor.JavaScriptCompressor;

@Ignore
public class CompressionTest {

	private String bpmn2;
	private InputStreamReader bpmn2reader;
	private File bpmn2file;


	@Before
	public void setUp() {
		try {
			URL testfile = CompressionTest.class.getResource("/bpmn2.0.js");
			bpmn2file = new File(testfile.toURI());		
			bpmn2 = FileUtils.readFileToString(bpmn2file, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	@Test
	public void testYuiCompression() {
		OutputStreamWriter writer2 = null;
		File compressedTempFile = null;
		try {
			compressedTempFile = File.createTempFile(bpmn2file.getName()+"Compressed",".js");
			compressedTempFile.deleteOnExit();
			bpmn2reader = new InputStreamReader(new FileInputStream(bpmn2file),"UTF8");
			writer2 = new OutputStreamWriter(new FileOutputStream(compressedTempFile),"UTF8");
			String compressedBpmn2 = FileUtils.readFileToString(compressedTempFile, "UTF-8");
			Assert.assertTrue(compressedBpmn2.indexOf("get ") < 0);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			com.yahoo.platform.yui.compressor.JavaScriptCompressor x= new JavaScriptCompressor(bpmn2reader, null);
			x.compress(writer2, 1, true, false, false, false);
			writer2.close();
			bpmn2reader.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
