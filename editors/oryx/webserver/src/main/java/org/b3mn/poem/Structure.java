/***************************************
 * Copyright (c) 2008
 * Ole Eckermann
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 ****************************************/

package org.b3mn.poem;

import java.util.ArrayList;

import javax.persistence.*;

@Entity
@Table(name="structure_")
public class Structure {

	@Id
	private String hierarchy;
	private int ident_id;

	public String getHierarchy() {
		return hierarchy;
	}

	public void setHierarchy(String hierarchy) {
		this.hierarchy = hierarchy;
	}

	public int getIdent_id() {
		return ident_id;
	}

	public void setIdent_id(int ident_id) {
		this.ident_id = ident_id;
	}

	public static Structure instance(int owner_id, String hierarchy) {
/*		Structure structure = (Structure) Persistance.getSession().
		createSQLQuery("select * from ensure_descendant(:hierarchy,:id)").
		addEntity("structure", Structure.class).
		setString("hierarchy", hierarchy).
		setInteger("id", owner_id).
		uniqueResult();
		Persistance.commit();
		
		return structure;*/
		
		String parent = parent(hierarchy);
		int cp = child_position(hierarchy)+1;
		int encode_position = Math.max(cp, 0);
		String root_hierarchy_next_child = (String)Persistance.getSession().createSQLQuery("{CALL next_child_position(:hierarchy,:hierarchy_position,:id)}")
				.setString("hierarchy", hierarchy)
				//.setString("parent", parent)
				.setInteger("hierarchy_position", encode_position)
				.setInteger("id", owner_id).uniqueResult();
		if (root_hierarchy_next_child == null)
			root_hierarchy_next_child = "";

		Structure structure = (Structure) Persistance.getSession().
		createSQLQuery("{CALL ensure_descendant(:hierarchy,:root_hierarchy_next_child,:id)}").
		addEntity("structure_", Structure.class).
		setString("hierarchy", hierarchy).
		setString("root_hierarchy_next_child",root_hierarchy_next_child).
		setInteger("id", owner_id).
		uniqueResult();
		Persistance.commit();
		
		return structure;		
	}

	@Transient
	private String next_child_position(String hierarchy, int owner_id) {
		return null;
	}

	@Transient
	private static Integer child_position(String hierarchy) {
		String[] path = poem_path(hierarchy);
		int parent = -2;
		int current = -1;
		
		current = translateIndex(path,current);
		parent = translateIndex(path,parent);
		
		//path[current][len(path[parent]):] 
		String currentString = path[current];
		String parentString = path[parent];
		int lenParentString = parentString.length();
		String local = subStringToEnd(currentString,lenParentString);
	    
		return decode_position(local);	
	}
	
	@Transient
	private static String subStringToEnd(String parentString, int lenParentString) {
		if (lenParentString < 0)
			lenParentString =  parentString.length() + lenParentString;

		return parentString.substring(lenParentString);
	}

	@Transient
	private static int translateIndex(String[] path, int index) {
		if (index < 0)
			return path.length + index;
		else
			return index;
	}

	@Transient
	private static String parent(String hierarchy) {
		
		
		 String[] pathArray = poem_path(hierarchy);
		 int index = pathArray.length - 2;
		 return pathArray[index];
/*		ArrayList<String> all = new ArrayList<String>();
		all.add("");
		all.add("");

		if (hierarchy == null || hierarchy.trim().isEmpty())
			return all.toArray(new String[] {});
		else {
			int position = 0;
			while (position < hierarchy.length()) {
				// count tildes
				int tilde_count = 0;
				while (hierarchy.charAt(position + tilde_count) == '~') {
					tilde_count += 1;
				}
				if (tilde_count == 0) {
					all.add(hierarchy.substring(0, position + 1));
					position += 1;
				} else {
					all.add(hierarchy.substring(0, position + 2 * tilde_count));
					position += 2 * tilde_count;
				}
			}
		}

		return all.toArray(new String[] {});*/
	}

	@Transient
	private static String[] poem_path(String hierarchy) {
		ArrayList<String> all = new ArrayList<String>();
		all.add("");
		all.add("");

		if (hierarchy == null || hierarchy.trim().isEmpty())
			return all.toArray(new String[] {});
		else {
			int position = 0;
			// for each character in the input string
			while (position < hierarchy.length()) {
				// count tildes
				int tilde_count = 0;
				while (hierarchy.charAt(position + tilde_count) == '~') {
					tilde_count += 1;
				}
				if (tilde_count == 0) {
					all.add(hierarchy.substring(0, position + 1));
					position += 1;
				} else {
					all.add(hierarchy.substring(0, position + 2 * tilde_count));
					position += 2 * tilde_count;
				}
			}

			return all.toArray(new String[] {});
		}
	}
	
	@Transient
	private static Integer decode_position(String code) {
	 	int offset = 0;
	 	if (code == "")
	 		return null;
	 	
	 	ArrayList<Integer> digits = null;
	 	
	 	while (code.charAt(offset) == '~') {
	 		 	offset+=1;
	 		 	digits = new ArrayList<Integer>();
	 		 	int digit_no;
	 		 			
	 			for (int i = offset; i < code.length(); i++){
	 			    char c = code.charAt(i);        
	 			    //Process char
	 			   digit_no = (int)c;
	 			   if (digit_no >= 48 && digit_no <= 58)
	 				  digits.add(digit_no-48);
	 			   if (digit_no >= 65 && digit_no <= 91)
	 				  digits.add(digit_no-55);	
	 			   if (digit_no >= 97 && digit_no <= 123)
	 				  digits.add(digit_no-61);	 			   
	 			}	 	
	 			
	 			if (offset == 0)
	 				return digits.get(0);
	 			if (offset == 1)
	 				return digits.get(0) + 62;
	 			if (offset == 2)
	 				return 124 + digits.get(0)*62 + digits.get(1);	 		
	 			if (offset == 3)
	 				return 3968 + digits.get(0)*62*62 + digits.get(1)*62 + digits.get(2);	 	 			
	 	}
		return 0;
	}
}
