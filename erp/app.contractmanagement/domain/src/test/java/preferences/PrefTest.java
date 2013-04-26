package preferences;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.prefs.BackingStoreException;
import java.util.prefs.InvalidPreferencesFormatException;
import java.util.prefs.Preferences;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.conx.bi.kernel.core.domain.job.Endpoint;

public class PrefTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        Preferences prefsRoot = Preferences.userRoot();
        Preferences myPrefs = prefsRoot
                .node(Endpoint.class.getName());
        myPrefs.put("fruit", "apple");
        myPrefs.putDouble("price", 40);
        myPrefs.putBoolean("available", false);
        
        try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();       
			
			myPrefs.exportNode(os);
			
			String aString = new String(os.toByteArray(),"UTF-8");
			
			
			System.out.println(aString);
			os.close();
			
			InputStream stream = new ByteArrayInputStream(aString.getBytes("UTF-8"));
			
			Preferences loadedPrefs = XmlSupport.importPreferences(stream);
			double price = loadedPrefs.node(Endpoint.class.getName()).getDouble("price",0.0);
			System.out.println("Price: "+price);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BackingStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidPreferencesFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        
        
	}
}
