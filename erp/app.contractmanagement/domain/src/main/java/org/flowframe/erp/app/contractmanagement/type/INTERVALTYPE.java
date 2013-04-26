package org.flowframe.erp.app.contractmanagement.type;


public enum INTERVALTYPE {
	WEEKLY("WEEKLY"),
	MONTHLY("MONTHLY"),
	ANNUALLY("ANNUALLY");
    private final String value;

    INTERVALTYPE(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static INTERVALTYPE fromValue(String v) {
        for (INTERVALTYPE c: INTERVALTYPE.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
	
}
