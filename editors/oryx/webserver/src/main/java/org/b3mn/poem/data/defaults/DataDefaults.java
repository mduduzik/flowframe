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

package org.b3mn.poem.data.defaults;

import org.b3mn.poem.Identity;
import org.b3mn.poem.Interaction;
import org.b3mn.poem.Persistance;
import org.b3mn.poem.Setting;
import org.b3mn.poem.Structure;
import org.hibernate.Session;




public class DataDefaults {
	public static void all() {
		createIdentities();
		createInterations();
		createSettings();
		createStructures();
	}

	public static void createIdentities() {
		Session session = Persistance.getSession();
		//Identity identity = (Identity) session.createSQLQuery("{CALL identity_(?)}").addEntity("identity_", Identity.class).setString(0, "root").uniqueResult();
		//session.save(identity); 
		Identity identity = new Identity();
		identity.setId(1);
		identity.setUri("root");
		session.saveOrUpdate(identity);
		
		identity = new Identity();
		identity.setId(2);
		identity.setUri("public");
		session.saveOrUpdate(identity);	
	
		identity = new Identity();
		identity.setId(3);
		identity.setUri("groups");
		session.saveOrUpdate(identity);

		identity = new Identity();
		identity.setId(4);
		identity.setUri("ownership");
		session.saveOrUpdate(identity);	
		
		Persistance.commit();
	}
	
	public static void createInterations() {
		Session session = Persistance.getSession();
		
		Interaction inter = new Interaction();
		inter.setId(1);
		inter.setSubject("U2");
		inter.setSubject_descend(true);
		inter.setObject("U2");
		inter.setObject_self(false);
		inter.setObject_descend(true);
		inter.setObject_restrict_to_parent(true);
		inter.setScheme("http://b3mn.org/http");
		inter.setTerm("owner");
		
		session.saveOrUpdate(inter);
		
		Persistance.commit();
	}	
	
	public static void createSettings() {
		Session sess = Persistance.getSession();
		Setting set = new Setting();
		set.setKey("UserManager.DefaultCountryCode");
		set.setValue("us");
		sess.saveOrUpdate(set);
		
		set = new Setting();
		set.setKey("UserManager.DefaultLanguageCode");
		set.setValue("en");
		sess.saveOrUpdate(set);
		
		Persistance.commit();
	}	
	
	public static void createStructures() {
		Session sess = Persistance.getSession();
		
		Structure set = new Structure();
		set.setHierarchy("U");
		set.setIdent_id(1);
		sess.saveOrUpdate(set);
		
		set = new Structure();
		set.setHierarchy("U1");
		set.setIdent_id(2);
		sess.saveOrUpdate(set);	
		
		set = new Structure();
		set.setHierarchy("U20");
		set.setIdent_id(2);
		sess.saveOrUpdate(set);			
		
		set = new Structure();
		set.setHierarchy("U2");
		set.setIdent_id(4);
		sess.saveOrUpdate(set);		
		
		set = new Structure();
		set.setHierarchy("U3");
		set.setIdent_id(3);
		sess.saveOrUpdate(set);
		
		Persistance.commit();
	}	
}
