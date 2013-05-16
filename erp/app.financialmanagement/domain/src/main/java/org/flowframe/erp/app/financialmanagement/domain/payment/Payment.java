package org.flowframe.erp.app.financialmanagement.domain.payment;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.flowframe.erp.app.financialmanagement.enums.PAYMENTTYPE;
import org.flowframe.kernel.common.mdm.domain.BaseEntity;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Table(name = "fferpfinpayment")
public class Payment extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private PAYMENTTYPE type;	
    
	public Payment(){
	}    
}