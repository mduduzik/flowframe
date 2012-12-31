package org.flowframe.ds.domain.formatters;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.flowframe.kernel.common.mdm.domain.MultitenantBaseEntity;

@Entity
@Table(name="sysdsformatter")
@Inheritance( strategy = InheritanceType.SINGLE_TABLE )
public class NativeDateDisplayFormatter extends MultitenantBaseEntity implements DateDisplayFormatter {
	
	private String format;
	
	public NativeDateDisplayFormatter(String format) {
		this.format = format;
	}
	
	public NativeDateDisplayFormatter() {
	}	
	
	@Override
	public String format(Date date) {
		// TODO Auto-generated method stub
		return null;
	}
}
