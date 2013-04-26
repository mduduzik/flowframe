package com.conx.bi.kernel.core.domain.etl.trans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.flowframe.kernel.common.mdm.domain.MultitenantBaseEntity;


@Entity
@Table(name = "bietlinputfieldmeta")
public class InputFieldMeta extends MultitenantBaseEntity {
	@Column(name = "position_")
	private int position;
	private int length = -1;
	
	@Column(name = "type_")
	private int type;
	private String typeAsString;
	private String typeDesc;
	
	@Column(name = "ignore_")
	private boolean ignore;
	
	@Column(name = "format_")
	private String format;
	private int trimtype;
	private String trimtypedescr;
	
	@Column(name = "precision_")
	private int precision = -1;
	private String currencySymbol;
	private String decimalSymbol;
	private String groupSymbol;
	
	@Column(name = "repeat_")
	private boolean repeat;
	private String nullString;
	private String ifNullValue;
	
	@OneToOne(targetEntity = StepMeta.class)
	private StepMeta parentStepMeta;

	public InputFieldMeta(String fieldname, int position, int length) {
		setName(fieldname);
		this.position = position;
		this.length = length;
		this.ignore = false;
		this.format = "";
		this.groupSymbol = "";
		this.decimalSymbol = "";
		this.currencySymbol = "";
		this.precision = -1;
		this.repeat = false;
		this.nullString = "";
		this.ifNullValue = "";
		// this.containsDot=false;
		// this.containsComma=false;
	}

	public InputFieldMeta() {
		this(null, -1, -1);
	}

	@Transient
	public int compare(Object obj) {
		InputFieldMeta field = (InputFieldMeta) obj;

		return position - field.getPosition();
	}

	@Transient
	public int compareTo(InputFieldMeta field) {
		return position - field.getPosition();
	}

	@Transient
	public boolean equal(Object obj) {
		InputFieldMeta field = (InputFieldMeta) obj;

		return (position == field.getPosition());
	}

	@Transient
	public Object clone() {
		try {
			Object retval = super.clone();
			return retval;
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getName() {
		return name;
	}

	public void setName(String fieldname) {
		this.name = fieldname;
	}

	public int getType() {
		return type;
	}

	public String getTypeDesc() {
		return typeDesc;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public String getTypeAsString() {
		return typeAsString;
	}

	public void setTypeAsString(String typeAsString) {
		this.typeAsString = typeAsString;
	}

	public boolean isIgnored() {
		return ignore;
	}

	public void setIgnored(boolean ignore) {
		this.ignore = ignore;
	}

	public void flipIgnored() {
		ignore = !ignore;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public int getTrimType() {
		return trimtype;
	}

	public void setTrimType(int trimtype) {
		this.trimtype = trimtype;
	}
	
	

	public String getTrimtypedescr() {
		return trimtypedescr;
	}

	public void setTrimtypedescr(String trimtypedescr) {
		this.trimtypedescr = trimtypedescr;
	}

	public String getGroupSymbol() {
		return groupSymbol;
	}

	public void setGroupSymbol(String group_symbol) {
		this.groupSymbol = group_symbol;
	}

	public String getDecimalSymbol() {
		return decimalSymbol;
	}

	public void setDecimalSymbol(String decimal_symbol) {
		this.decimalSymbol = decimal_symbol;
	}

	public String getCurrencySymbol() {
		return currencySymbol;
	}

	public void setCurrencySymbol(String currency_symbol) {
		this.currencySymbol = currency_symbol;
	}

	public int getPrecision() {
		return precision;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
	}

	public boolean isRepeated() {
		return repeat;
	}

	public void setRepeated(boolean repeat) {
		this.repeat = repeat;
	}

	public void flipRepeated() {
		repeat = !repeat;
	}

	public String getNullString() {
		return nullString;
	}

	public void setNullString(String null_string) {
		this.nullString = null_string;
	}

	public String getIfNullValue() {
		return ifNullValue;
	}

	public void setIfNullValue(String ifNullValue) {
		this.ifNullValue = ifNullValue;
	}
	
	public StepMeta getParentStepMeta() {
		return parentStepMeta;
	}

	public void setParentStepMeta(StepMeta parentStepMeta) {
		this.parentStepMeta = parentStepMeta;
	}

	public String toString() {
		return name + "@" + position + ":" + length;
	}
}
