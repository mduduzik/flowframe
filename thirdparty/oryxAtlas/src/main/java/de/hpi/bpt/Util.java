package de.hpi.bpt;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import de.hpi.bpt.epc.EPCNode;
import de.hpi.bpt.epc.EPCPath;

public class Util {
	
	public static final String LOG_DATE_FORMAT = "yyyy/MM/dd HH:mm:ss.0";
	
	private static final DecimalFormat decimalFormat = new DecimalFormat("###0.00");
	
	private static Collection<Character> specialCharacters = new HashSet<Character>();
	
	static{
		specialCharacters.add('\\');
		specialCharacters.add('/');
		specialCharacters.add('.');
		specialCharacters.add(',');
		specialCharacters.add('?');
		specialCharacters.add('!');
		specialCharacters.add(':');
		specialCharacters.add('-');
		specialCharacters.add(';');
		specialCharacters.add('(');
		specialCharacters.add('%');
		specialCharacters.add(')');
		specialCharacters.add('§');
	}
	
	public static String currentTimeToString(String format)
	{
	    Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat(format);
	    return sdf.format(cal.getTime());
	}
	
	public static String formatNumber(double value)
	{
		return decimalFormat.format(value).replace(",", ".");
	}
	
	public static String formatNumber(double value, String f)
	{
		DecimalFormat format = new DecimalFormat(f);
		return format.format(value).replace(",",".");
	}
	
	public static String filterCharacters(String s)
	{
		StringBuffer r = new StringBuffer();
		for(int i = 0; i < s.length(); i ++)
		{
			char c = s.charAt(i);
			if(Character.isLetter(c) || Character.isWhitespace(c) || Character.isDigit(c) || c == '(' || c == ')')
				r.append(c);
			else
				r.append(' ');
		}
		String res = r.toString().trim();
		while(res.contains("  "))
			res = res.replace("  ", " ");
		
		return res;
	}
	
	public static String filterSpecialCharacters(String s)
	{
		StringBuffer r = new StringBuffer();
		for(int i = 0; i < s.length(); i ++)
		{
			char c = s.charAt(i);
			if(!specialCharacters.contains(c))
				r.append(c);
			else
				r.append(' ');
		}
		String res = r.toString().trim();
		while(res.contains("  "))
			res = res.replace("  ", " ");
		
		return res;
	}
	
	public static String filterNumbers(String s)
	{
		StringBuffer r = new StringBuffer();
		for(int i = 0; i < s.length(); i ++)
		{
			char c = s.charAt(i);
			if(!Character.isDigit(c))
				r.append(c);
			else
				r.append(' ');
		}
		String res = r.toString().trim();
		while(res.contains("  "))
			res = res.replace("  ", " ");
		
		return res;
	}
	
	public static String now(String format)
	{
	    Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat(format);
	    return sdf.format(cal.getTime());
	}
	
	public static Map<Double, Double> getDistribution(List<Double> values)
	{
		Map<Double, Double> distribution = new HashMap<Double, Double>();
		
		for (int i = 0; i < values.size(); i ++)
		{
			Double k = values.get(i);
			Double v = distribution.get(k);
			
			if (v == null)
				distribution.put(k, 1.0);
			else
				distribution.put(k, (v.doubleValue() + 1.0));
		}
		
		Iterator<Double> i = distribution.keySet().iterator();
		while (i.hasNext())
		{
			Double k = i.next();
			distribution.put(k, new Double(distribution.get(k).doubleValue() / values.size()));
		}
		
		return distribution;
	}
	
	public static void printPath(List<EPCNode> path)
	{			
		for (int j = 0; j < path.size(); j ++)
			System.out.print(path.get(j).getName().replace("\n", " ") + "->");
		
		System.out.println();
	}
	
	public static void printPath(EPCPath path)
	{
		if(!path.isEmpty())
		{
			System.out.print("Path (" + path.getNodes().size() + " nodes and duration of " + path.getDuration() + " min): ");
			System.out.print(path.getNodes().get(0).getName().replace("\n", " "));
			if(path.getLength() > 1)
				for (int j = 1; j < path.getNodes().size(); j ++)
					System.out.print("->" + path.getNodes().get(j).getName().replace("\n", " "));
			System.out.println(".");
			
		}else
		{
			System.out.println("Empty path.");
		}
		
		System.out.println();
	}

}