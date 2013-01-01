package org.flowframe.kernel.common.mdm.domain.geolocation;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.flowframe.kernel.common.mdm.domain.BaseEntity;


@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name="ffrefaddresscapability")
public class AddressCapability extends BaseEntity implements Serializable {
}
