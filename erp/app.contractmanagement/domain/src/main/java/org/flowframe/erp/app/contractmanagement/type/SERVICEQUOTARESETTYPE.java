package org.flowframe.erp.app.contractmanagement.type;


public enum SERVICEQUOTARESETTYPE {
	HOURLY("HOURLY"),
	DAILY("DAILY"),
	WEEKLY("WEEKLY"),
	MONTHLY("MONTHLY"),
	NEVER("NEVER"),
	CUSTOM("CUSTOM");
    private final String value;

    SERVICEQUOTARESETTYPE(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SERVICEQUOTARESETTYPE fromValue(String v) {
        for (SERVICEQUOTARESETTYPE c: SERVICEQUOTARESETTYPE.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
	
}
