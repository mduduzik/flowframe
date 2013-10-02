package org.flowframe.etl.pentaho.server.carte.standalone.impl.utils;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

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
    private static String escapeJSON(String json) {
        // escape (some) JSON special characters
        String res = json.replace("\"", "\\\"");
        res = res.replace("\n","\\n");
        res = res.replace("\r","\\r");
        res = res.replace("\t","\\t");
        return res;
    }

    public static JSONObject transform(String xmlContent) throws TransformerException, JSONException, IOException, URISyntaxException {

        try {
            InputStream xsltStream = XML2JSONTransformer.class.getResourceAsStream("/xslt/xmltojsonv1.xsl");
            URL configDocRc = XML2JSONTransformer.class.getResource("/xslt/config.xml");
            String configDoc = new File(configDocRc.toURI()).getAbsolutePath();
            //String xml = StringUtil.read(xsltStream);
            Source xsltSource = new StreamSource(xsltStream);
            Source xmlContentSource = new StreamSource(new StringReader(xmlContent));

            TransformerFactory transFact =
                    TransformerFactory.newInstance();
            Transformer trans = transFact.newTransformer(xsltSource);
            trans.setParameter("confDoc",configDoc);
            StringWriter output = new StringWriter();
            trans.transform(xmlContentSource, new StreamResult(output));
            final String res = output.toString();
            return new JSONObject(res);
        } catch (TransformerException e) {
            throw e;
        }
    }
}
