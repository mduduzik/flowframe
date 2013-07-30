package de.hpi.bpt.epc.aml.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

public class Serializer {
	
	private static final String TITLE_HTML_TMPL_EN = "Summary for Process Models";
	private static final String TITLE_HTML_TMPL_DE = "Zusammenfassung für Prozessmodelle";
	private static final String LEAKS_HTML_TMPL_EN = "Models with Effort Leaks";
	private static final String LEAKS_HTML_TMPL_DE = "Modelle haben nach der Abstraktion einen anderen Aufwand";
	private static final String MODEL_LIST_HTML_TMPL_EN = "<a name=\"toc\">Model List</a>";
	private static final String MODEL_LIST_HTML_TMPL_DE = "<a name=\"toc\">Liste von Modellen</a>";
	private static final String HEADER_1_HTML_TMPL_EN = "<a name=\"%1s\" href=\"#toc\" class=\"title\">Summary for \"%2s\"</a>";
	private static final String HEADER_1_HTML_TMPL_DE = "<a name=\"%1s\" href=\"#toc\" class=\"title\">Zusammenfassung für \"%2s\"</a>";
	private static final String HEADER_2_HTML_TMPL_EN = "Summary for \"%1s\"";
	private static final String HEADER_2_HTML_TMPL_DE = "Zusammenfassung für \"%1s\"";
	private static final String NODES_HTML_TMPL_EN = "%1s nodes (%2s functions, %3s events, %4s connectors)";
	private static final String NODES_HTML_TMPL_DE = "%1s Knoten (%2s Funktionen, %3s Ereignisse, %4s Konnektoren)";
	private static final String FUNCTION_HTML_TMPL_EN = "Function name";
	private static final String FUNCTION_HTML_TMPL_DE = "Funktionname";
	private static final String OCCURRENCE_HTML_TMPL_EN = "Mean occurence";
	private static final String OCCURRENCE_HTML_TMPL_DE = "Durchschnittliche Ausführungshäufigkeit";
	private static final String EFFORT_HTML_TMPL_EN = "Effort";
	private static final String EFFORT_HTML_TMPL_DE = "Aufwand";
	private static final String M_EFFORT_HTML_TMPL_EN = "Mean effort";
	private static final String M_EFFORT_HTML_TMPL_DE = "Durchschnittlicher Aufwand";
	private static final String TOTAL_HTML_TMPL_EN = "<b>Total</b>";
	private static final String TOTAL_HTML_TMPL_DE = "<b>Gesamt</b>";

	
	private static Map<String, String> fileNames;
	
	static{
		fileNames = new HashMap<String, String>();
		fileNames.put("original", "Original");
		fileNames.put("slider sequential-rr", "Sequenz");
		fileNames.put("slider sequential-rr, slider aok-block", "Sequenz-Block");
		fileNames.put("mainroad", "Hauptstrasse");
		fileNames.put("slider sequential-rr, slider aok-block, mainroad", "Sequenz-Block-Hauptstrasse");
		fileNames.put("slider mixed", "slider mixed");		
	}
	
	/**
	 * Export AML file
	 * @param epc EPC graph
	 * @param fileName Filename to export to
	 */
	public static void serializeAML(Document document, String fileName) {
        try {
            // Prepare the DOM document for writing
            Source source = new DOMSource(document);
            
            document.getDomConfig().setParameter("comments", true);
            document.getDomConfig().setParameter("entities", true);
            document.getDomConfig().setParameter("cdata-sections", true);
            document.getDomConfig().setParameter("datatype-normalization", false);
    
            // Prepare the output file
            File file = new File(fileName);
            Result result = new StreamResult(file);
    
            // Write the DOM document to the file
            Transformer xformer = TransformerFactory.newInstance().newTransformer();
            xformer.setOutputProperty("indent", "yes");
            xformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "ARIS-Export.dtd");
            xformer.transform(source, result);
        } catch (TransformerConfigurationException e) {
        	e.printStackTrace();
        } catch (TransformerException e) {
        	e.printStackTrace();
        }
    }
}