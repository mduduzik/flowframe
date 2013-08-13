/***************************************
 * Copyright (c) 2008
 * Bjoern Wagner
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

package com.conx.bi.etl.pentaho.repository.db.services.business;

import com.conx.bi.etl.pentaho.repository.db.services.*;
import com.conx.bi.etl.pentaho.repository.db.services.util.AccessRight;


import java.util.*;

public class Model extends BusinessObject {
	
	
	private static final Object lock = new Object();
	
	// Cache data
	protected String author = null;
	
	protected Representation representation;
	
	protected TagDefinition getTagDefintion(User user, String name) {
/*		TagDefinition tagDefinition = (TagDefinition) Persistance.getSession()
		.createSQLQuery("SELECT {tag_definition.*} FROM {tag_definition} WHERE subject_id=:subject_id AND name=:name")
		.addEntity("tag_definition", TagDefinition.class)
		.setInteger("subject_id", user.getId())
		.setString("name", name)
		.uniqueResult();
		
		Persistance.commit();
		return tagDefinition;*/
        return null;
	}
	
	protected TagRelation getTagRelation(User user, String name) {
/*		TagRelation tagRelation = (TagRelation) Persistance.getSession()
		.createSQLQuery("SELECT {tag_relation.*} FROM {tag_relation}, tag_definition WHERE tag_definition.subject_id=:subject_id AND tag_definition.name=:name AND tag_definition.id=tag_relation.tag_id AND tag_relation.object_id=:object_id")
		.addEntity("tag_relation", TagRelation.class)
		.setInteger("subject_id", user.getId())
		.setInteger("object_id", this.getId())
		.setString("name", name)
		.uniqueResult();
		
		Persistance.commit();
		return tagRelation;*/
        return null;
	}
	
	public Model(int id) throws Exception {
/*		super(Identity.instance(id));
		representation = identity.read();
		if ((identity == null) || (representation == null)) 
			throw new Exception("Model cannot be initalized");*/
	}
	
	public Model(String uri) throws Exception {
/*		super(Identity.instance(uri));
		representation = identity.read();	
		if ((identity == null) || (representation == null)) 
			throw new Exception("Model cannot be initalized");*/
	}
	
	public Model(Identity identity) throws Exception {
/*		super(identity);
		representation = identity.read();	
		if ((identity == null) || (representation == null)) 
			throw new Exception("Model cannot be initalized");*/
	}
	
	public int getId() {
		return this.identity.getId();
	}

	public String getUri() {
		return this.identity.getUri();
	}
	
	public String getTitle() {
		return this.representation.getTitle();
	}
	
	public void setTitle(String title) {
		this.representation.setTitle(title);
		updateDBObject(representation);
	}
	
	public String getSummary() {
		return this.representation.getSummary();
	}
	
	public void setSummary(String summary) {
		this.representation.setSummary(summary);
		updateDBObject(representation);
	}
	
	public String getType() {
		return this.representation.getType();
	}
	
	public void setType(String type) {
		this.representation.setType(type);
	}
	
	public Date getCreationDate() {
		return this.representation.getCreated();
	}
	
	public void setCreationDate(Date creationDate) {
		this.representation.setCreated(creationDate);
	}
	
	public Date getLastUpdate() {
		return this.representation.getUpdated();
	}
	
	public void setLastUpdate(Date lastUpdate) {
		this.representation.setCreated(lastUpdate);
	}
	
	// TODO: implement erdf and svg db access here instead of the representation class
	@SuppressWarnings("deprecation")
	public String geteRdf() {
		return representation.getContent();
	}
	
	public void seteRdf(String eRdf) {
		representation.setContent(eRdf);
	}
	
	public String getSvg() {
		return representation.getSvg();
	}
	
	public void setSvg(String svg) {
		representation.setSvg(svg);
	}
	
	public String getAuthor() {
/*		if (this.author == null) {
			this.author = (String) Persistance.getSession().createSQLQuery(
					"SELECT identity.uri FROM access, identity WHERE " +
					"access.object_id=:object_id AND " +
					"access.subject_id=identity.id AND " +
					"access.access_term='owner'")
					.setInteger("object_id", getId())
					.uniqueResult();
			Persistance.commit();
		}
		return this.author;*/
        return null;
	}
	
	public Collection<String> getPublicTags(User user) {
        /*
		Collection<?> tags = Persistance.getSession().
			createSQLQuery("SELECT DISTINCT ON(tag_definition.name) tag_definition.name " 
			+ "FROM tag_definition, tag_relation " 
			+ "WHERE tag_definition.id=tag_relation.tag_id AND "
			+ "tag_relation.object_id=:object_id AND "
			+ "NOT tag_definition.subject_id=:subject_id")
			.setInteger("object_id", this.identity.getId())
			.setInteger("subject_id", user.getId())
			.list();
		
		Persistance.commit();
		return toStringCollection(tags);*/
        return null;
	}
	
	public Collection<String> getUserTags(User user) {
/*		Collection<?> tags = Persistance.getSession().
			createSQLQuery("SELECT DISTINCT ON(tag_definition.name) tag_definition.name " 
			+ "FROM tag_definition, tag_relation " 
			+ "WHERE tag_definition.id=tag_relation.tag_id AND "
			+ "tag_relation.object_id=:object_id AND "
			+ "tag_definition.subject_id=:subject_id")
			.setInteger("object_id", this.identity.getId())
			.setInteger("subject_id", user.getId())
			.list();
		
		Persistance.commit();
		return toStringCollection(tags);*/
        return null;
	}
	
	public synchronized void addTag(User user, String tag) {
/*		synchronized(lock) {
			// TODO check access right of the user
			// If the user hasn't already tagged the model with this tag
			if (!this.getUserTags(user).contains(tag)) {
				
				TagDefinition tagDefinition = this.getTagDefintion(user, tag);
				Session session = Persistance.getSession();
				// User uses this tag for the first time
				if (tagDefinition == null) {
					// Create definition
					tagDefinition = new TagDefinition();
					tagDefinition.setName(tag);
					tagDefinition.setSubject_id(user.getId());
					session.save(tagDefinition);
				}
				// Create tag relation
				TagRelation tagRelation = new TagRelation();
				tagRelation.setObject_id(this.getId());
				tagRelation.setTag_id(tagDefinition.getId());
				session.save(tagRelation);
				Persistance.commit();
			}
		}*/
	}
	
	public void removeTag(User user, String tag) {
/*		TagRelation tagRel = this.getTagRelation(user, tag);
		if (tagRel != null) {
			Persistance.getSession().delete(tagRel);
			Persistance.commit();
			// TODO: remove tag_defintion if no other models are tagged with this tag anymore
		}*/
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> getAccessRights() {
/*		List<Object[]> results = Persistance.getSession().createSQLQuery(
				"SELECT identity.uri, access.access_term FROM access, identity WHERE " +
				"access.object_id=:object_id AND access.subject_id=identity.id")
				.setInteger("object_id", this.getId())
				.list();
		
		Persistance.commit();
		
		Map<String, String> accessRights = new HashMap<String, String>();
		
		
		for(Object rowObj : results) {
			Object[] row = (Object[]) rowObj;
			accessRights.put(row[0].toString(), row[1].toString());
		}
		return accessRights;*/
        return null;
	}
	
	public AccessRight getAccessRight(String openId) {
/*		String term = (String) Persistance.getSession().createSQLQuery(
				"SELECT access_.access_term FROM access_, identity_ WHERE " +
				"access_.object_id=:object_id AND access_.subject_id=identity_.id AND identity_.uri=:open_id LIMIT 1")
				.setInteger("object_id", this.getId())
				.setString("open_id", openId)
				.uniqueResult();
		
		Persistance.commit();
		if (term==null) term="none"; // Set right to none if nothing else is defined
		AccessRight accessRight = Enum.valueOf(AccessRight.class, term.toUpperCase());
		return accessRight;*/
        return null;
	}
	
	public boolean addAccessRight(String openId, String term) {
/*
		synchronized(lock) {
			
			if (!User.openIdExists(openId)) return false;
			Identity sub = Identity.ensureSubject(openId);
			String subject_hierarchy = sub.getUserHierarchy();
			String object_hierarchy = this.identity.getModelHierarchy();
			Interaction right = Interaction.exist(subject_hierarchy, object_hierarchy, term);
			
			if (right == null) {
				right = new Interaction();
				right.setSubject(subject_hierarchy);
				right.setObject(object_hierarchy);
				right.setScheme("http://b3mn.org/http");
				right.setTerm(term);
				right.setObject_self(true);
				right.save();
			} else {
				right.setTerm(term); // Overwrite old term
				right.save();
			}
			return true;
		}
		*/
        return false;
	}
	
	public boolean removeAccessRight(String openId) {
/*		AccessRight term = this.getAccessRight(openId);
		// None and owner rights cannot be removed
		if ((term != AccessRight.NONE) && (term != AccessRight.OWNER)){
			try {
				Identity sub = Identity.ensureSubject(openId);
				String subject_hierarchy = sub.getUserHierarchy();
				String object_hierarchy = this.identity.getModelHierarchy();
				Interaction right = Interaction.exist(subject_hierarchy, object_hierarchy, term.toString().toLowerCase());
				List<User> users = new ArrayList<User>();
				
				for (String userOpenId : getAccessRights().keySet()) {
					User user = new User(userOpenId);
					users.add(user);
				}

						
				right.delete();
				return true; // Deleted
			} catch (Exception e) { return false; }
		} else {
			return false; // Doesn't exist
		}*/
        return false;
	}
	
	@SuppressWarnings("unchecked")
	private List<ModelRating> getRatingList() {
/*		List<ModelRating> scores = Persistance.getSession()
			.createSQLQuery("SELECT {model_rating.*} FROM {model_rating} " +
			"WHERE model_rating.object_id=:object_id")
			.addEntity("model_rating", ModelRating.class)
			.setInteger("object_id", this.getId())
			.list();
		
		Persistance.commit();
		return scores;*/
        return null;
	}
	
	public float getTotalScore() {
		List<ModelRating> scores = getRatingList();
		int sum=0;
		for (ModelRating score : scores)
			sum+=score.getScore();
		
		if (scores.size() > 0) return ((float)sum) / ((float)scores.size());
		else return 0;
	}

	public int getTotalVotes() {
		List<ModelRating> scores = getRatingList();
		return scores.size();
	}
	
	public int getUserScore(Identity subject) {
/*		if (subject.getUri().equals(Dispatcher.getPublicUser())) return 0;
		ModelRating rating = (ModelRating) Persistance.getSession()
			.createSQLQuery("SELECT {model_rating.*} FROM {model_rating} " +
			"WHERE model_rating.object_id=:object_id AND model_rating.subject_id=:subject_id")
			.addEntity("model_rating", ModelRating.class)
			.setInteger("object_id", this.getId())
			.setInteger("subject_id", subject.getId())
			.uniqueResult();
		
		Persistance.commit();
		if (rating != null)
			return rating.getScore();
		else return 0;*/
        return -1;
	}
	
	public void setUserScore(Identity subject, int score) {
/*
		if (subject.getUri().equals(Dispatcher.getPublicUser())) return;
		if (score > 5) score=5;
		if (score < 0) score=0;
		
		ModelRating rating = (ModelRating) Persistance.getSession()
			.createSQLQuery("SELECT {model_rating.*} FROM {model_rating} " +
			"WHERE model_rating.object_id=:object_id AND model_rating.subject_id=:subject_id")
			.addEntity("model_rating", ModelRating.class)
			.setInteger("object_id", this.getId())
			.setInteger("subject_id", subject.getId())
			.uniqueResult();
		
		Persistance.commit();
		
		if (rating == null) {
			rating = new ModelRating();
			rating.setObject_id(this.getId());
			rating.setSubject_id(subject.getId());
			rating.setScore(score);
			persistDBObject(rating);
		} else {
			rating.setScore(score);
			updateDBObject(rating);
		}
*/
	}
}
