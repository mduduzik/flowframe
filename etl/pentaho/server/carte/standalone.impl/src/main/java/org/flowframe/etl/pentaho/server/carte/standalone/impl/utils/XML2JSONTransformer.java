package org.flowframe.etl.pentaho.server.carte.standalone.impl.utils;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.flowframe.kernel.common.utils.StringUtil;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by Mduduzi on 10/2/13.
 */
public class XML2JSONTransformer {
    static public File configFile = null;

    private static String escapeJSON(String json) {
        // escape (some) JSON special characters
        String res = json.replace("\"", "\\\"");
        res = res.replace("\n","\\n");
        res = res.replace("\r","\\r");
        res = res.replace("\t","\\t");
        return res;
    }


    public static void setConfigFile(File configFile_) {
        XML2JSONTransformer.configFile = configFile_;
    }

    public static File getConfigFile() {
        return XML2JSONTransformer.configFile;
    }

    public static JSONArray transform(String xmlContent) throws TransformerException, JSONException, IOException, URISyntaxException {

        try {
            InputStream xsltStream = XML2JSONTransformer.class.getResourceAsStream("/xslt/xmltojsonv1.xsl");
            URL configDocRc = XML2JSONTransformer.class.getResource("/xslt/config.xml");
            String configDoc = new File(configDocRc.toURI()).getAbsolutePath();

            return transform_(xmlContent, xsltStream, configDoc);
        } catch (TransformerException e) {
            throw e;
        }
    }

    public static JSONArray transform(String xmlContent,  File tmpDir) throws TransformerException, JSONException, IOException, URISyntaxException {

        try {
            InputStream xsltStream = XML2JSONTransformer.class.getResourceAsStream("/xslt/xmltojsonv1.xsl");
            InputStream configStream = XML2JSONTransformer.class.getResourceAsStream("/xslt/config.xml");
            if (getConfigFile() == null) {
                File configFile = writeStreamToTempFile(configStream,"xmltojsonv1.config.xml",tmpDir);
                setConfigFile(configFile);
            }
            return transform_(xmlContent, xsltStream, getConfigFile().getAbsolutePath());
        } catch (TransformerException e) {
            throw e;
        }
    }

    private static JSONArray transform_(String xmlContent, InputStream xsltStream, String configFile) throws TransformerException, JSONException {
        //String xml = StringUtil.read(xsltStream);
        Source xsltSource = new StreamSource(xsltStream);
        Source xmlContentSource = new StreamSource(new StringReader(xmlContent));

        TransformerFactory transFact =
                TransformerFactory.newInstance();
        Transformer trans = transFact.newTransformer(xsltSource);
        trans.setParameter("confDoc",configFile);
        StringWriter output = new StringWriter();
        trans.transform(xmlContentSource, new StreamResult(output));
        String res = output.toString();
        res = StringUtil.replace(res, ":,", ":\"\",");
        res = StringUtil.replace(res,":}",":\"\"}");
        return new JSONArray(res);
    }


    static protected File writeStreamToTempFile(InputStream in, String fileName, File tmpDir) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead;
        File fileOut = new File(tmpDir, fileName+"."+System.currentTimeMillis());
        FileOutputStream out = new FileOutputStream(fileOut);
        //read from is to buffer
        while ((bytesRead = in.read(buffer)) != -1) {
            out.write(buffer, 0, bytesRead);
        }
        in.close();
        //flush OutputStream to write any buffered data to file
        out.flush();
        out.close();

        return fileOut;
    }
}
