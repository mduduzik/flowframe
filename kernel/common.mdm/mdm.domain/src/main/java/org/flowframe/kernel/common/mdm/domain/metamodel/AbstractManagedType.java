package org.flowframe.kernel.common.mdm.domain.metamodel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.persistence.metamodel.Type.PersistenceType;

import org.flowframe.kernel.common.utils.StringUtil;

@Entity
public abstract class AbstractManagedType extends AbstractType {
	@Transient
	private Map<String, EntityTypeAttribute> attributeMap = null;

	@OneToOne
	private AbstractManagedType superType;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<EntityTypeAttribute> declaredAttributes = new HashSet<EntityTypeAttribute>();

	/*
	 * @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER) private final
	 * Set<BasicAttribute> basicAttributes = new HashSet<BasicAttribute>();
	 * 
	 * @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER) private final
	 * Set<SingularAttribute> declaredSingularAttributes = new
	 * HashSet<SingularAttribute>();
	 * 
	 * @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER) private final
	 * Set<PluralAttribute> declaredPluralAttributes = new
	 * HashSet<PluralAttribute>();
	 */

	public AbstractManagedType(String name, Class javaType, AbstractManagedType superType, PersistenceType persistentType) {
		super(name, javaType, javaType.getName(), javaType.getSimpleName(), persistentType);
		this.superType = superType;
	}

	public AbstractManagedType getSuperType() {
		return superType;
	}

	public void setSuperType(AbstractManagedType superType) {
		this.superType = superType;
	}

	public Set<EntityTypeAttribute> getDeclaredAttributes() {
		return declaredAttributes;
	}

	public Set<EntityTypeAttribute> getAllDeclaredAttributes() {
		HashSet<EntityTypeAttribute> attributeSet = new HashSet<EntityTypeAttribute>(getAttributeMap().values());
		return attributeSet;
	}

	private Map<String, EntityTypeAttribute> getAttributeMap() {
		if (this.attributeMap == null) {
			this.attributeMap = new HashMap<String, EntityTypeAttribute>();
			putAttributes(this.attributeMap, this);
		}
		return this.attributeMap;
	}

	public EntityTypeAttribute getAttribute(String attributeId) {
		if (attributeId.indexOf(".") == -1) {
			return getAttributeMap().get(attributeId);
		} else {
			return getNestedProperty(attributeId, this);
		}
	}

	private EntityTypeAttribute getNestedProperty(String path, AbstractManagedType type) {
		String nextAttributeName;
		boolean isLast = false;
		if (path.contains(".")) {
			nextAttributeName = StringUtil.extractFirst(path, ".");
			path = StringUtil.replace(path, nextAttributeName + ".", "").trim();
			isLast = false;
		} else {
			nextAttributeName = path.trim();
			isLast = true;
		}
		
		if (nextAttributeName != null) {
			for (EntityTypeAttribute attribute : type.getDeclaredAttributes()) {
				if (attribute != null && attribute.getAttribute() != null && nextAttributeName.equals(attribute.getAttribute().getName())) {
					if (isLast) {
						return attribute;
					} else {
						if (attribute.getAttribute() instanceof SingularAttribute) {
							return getNestedProperty(path, ((SingularAttribute) attribute.getAttribute()).getEntityType());
						} else if (attribute.getAttribute() instanceof PluralAttribute) {
							return getNestedProperty(path, ((PluralAttribute) attribute.getAttribute()).getEntityType());
						}
					}
				}
			}
		}
		return null;
	}

	private void putAttributes(Map<String, EntityTypeAttribute> attributes, AbstractManagedType type) {
		if (type.getSuperType() != null) {
			putAttributes(attributes, type.getSuperType());
		}

		for (EntityTypeAttribute attribute : type.getDeclaredAttributes()) {
			if (attribute != null) {
				attributes.put(attribute.getAttribute().getName(), attribute);
			}
		}
	}

	/*
	 * public Set<SingularAttribute> getDeclaredSingularAttributes() { return
	 * declaredSingularAttributes; }
	 * 
	 * public Set<PluralAttribute> getDeclaredPluralAttributes() { return
	 * declaredPluralAttributes; }
	 * 
	 * public Set<BasicAttribute> getBasicAttributes() { return basicAttributes;
	 * }
	 */
	public AbstractManagedType() {
		super();
	}
}
