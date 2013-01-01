package org.flowframe.kernel.common.mdm.domain.referencenumber;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.flowframe.kernel.common.mdm.domain.MultitenantBaseEntity;
import org.flowframe.kernel.common.mdm.domain.metadata.DefaultEntityMetadata;


@SuppressWarnings("serial")
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name="ffmdmreferencenumber")
public class ReferenceNumber extends MultitenantBaseEntity implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ReferenceNumber> childReferenceNumbers = new java.util.HashSet<ReferenceNumber>();

    @OneToOne(targetEntity = ReferenceNumber.class)
    @JoinColumn
    private ReferenceNumber parentReferenceNumber;

    @ManyToOne(targetEntity = ReferenceNumberType.class,cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn
    private ReferenceNumberType type;
    
    @OneToOne(targetEntity = DefaultEntityMetadata.class)
    @JoinColumn
    private DefaultEntityMetadata entityMetadata;      

    private Long entityPK;

    private String entityClassName;

    private boolean rootNum;

    private String value;

	public Set<ReferenceNumber> getChildReferenceNumbers() {
		return childReferenceNumbers;
	}

	public void setChildReferenceNumbers(Set<ReferenceNumber> childReferenceNumbers) {
		this.childReferenceNumbers = childReferenceNumbers;
	}

	public ReferenceNumber getParentReferenceNumber() {
		return parentReferenceNumber;
	}

	public void setParentReferenceNumber(ReferenceNumber parentReferenceNumber) {
		this.parentReferenceNumber = parentReferenceNumber;
	}

	public ReferenceNumberType getType() {
		return type;
	}

	public void setType(ReferenceNumberType type) {
		this.type = type;
	}

	public DefaultEntityMetadata getEntityMetadata() {
		return entityMetadata;
	}

	public void setEntityMetadata(DefaultEntityMetadata entityMetadata) {
		this.entityMetadata = entityMetadata;
	}

	public Long getEntityPK() {
		return entityPK;
	}

	public void setEntityPK(Long entityPK) {
		this.entityPK = entityPK;
	}

	public String getEntityClassName() {
		return entityClassName;
	}

	public void setEntityClassName(String entityClassName) {
		this.entityClassName = entityClassName;
	}

	public boolean isRootNum() {
		return rootNum;
	}

	public void setRootNum(boolean rootNum) {
		this.rootNum = rootNum;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
