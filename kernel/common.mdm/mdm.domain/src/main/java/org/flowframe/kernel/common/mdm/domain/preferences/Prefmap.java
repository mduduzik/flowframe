package org.flowframe.kernel.common.mdm.domain.preferences;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ffprefmap")
@Inheritance(strategy = InheritanceType.JOINED)
public class Prefmap implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7869822230680505669L;
	protected Set<Prefentry> prefentry;
	protected Long hjid;

	/**
	 * Gets the value of the prefentry property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the prefentry property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getPrefentry().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link Prefentry }
	 * 
	 * 
	 */
	@OneToMany(targetEntity = Prefentry.class, cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinColumn(name = "PREFENTRY_PREFMAP_HJID")
	public Set<Prefentry> getPrefentry() {
		if (prefentry == null) {
			prefentry = new HashSet<Prefentry>();
		}
		return this.prefentry;
	}

	/**
     * 
     * 
     */
	public void setPrefentry(Set<Prefentry> prefentry) {
		this.prefentry = prefentry;
	}

	/**
	 * Gets the value of the hjid property.
	 * 
	 * @return possible object is {@link Long }
	 * 
	 */
	@Id
	@Column(name = "HJID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getHjid() {
		return hjid;
	}

	/**
	 * Sets the value of the hjid property.
	 * 
	 * @param value
	 *            allowed object is {@link Long }
	 * 
	 */
	public void setHjid(Long value) {
		this.hjid = value;
	}
}
