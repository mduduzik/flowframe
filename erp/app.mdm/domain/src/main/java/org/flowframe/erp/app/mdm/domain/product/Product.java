package org.flowframe.erp.app.mdm.domain.product;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.flowframe.erp.app.mdm.domain.enums.ITEMUNIT;
import org.flowframe.erp.app.mdm.domain.enums.PRODUCTTYPE;
import org.flowframe.kernel.common.mdm.domain.MultitenantBaseEntity;
import org.flowframe.kernel.common.mdm.domain.organization.Organization;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Table(name="fferpproduct")
public class Product extends MultitenantBaseEntity {

    @ManyToOne(targetEntity = Organization.class)
    @JoinColumn
    private Organization supplier;

    @Enumerated(EnumType.STRING)
    private PRODUCTTYPE type;
    
    @Enumerated(EnumType.STRING)
    private ITEMUNIT unit;    
    
    private Integer amount;
}
