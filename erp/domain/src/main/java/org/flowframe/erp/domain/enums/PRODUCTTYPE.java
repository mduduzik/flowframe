package org.flowframe.erp.domain.enums;


public enum PRODUCTTYPE {
	SERVICE("SERVICE"),
	COMMODITY("COMMODITY");
    private final String value;

    PRODUCTTYPE(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PRODUCTTYPE fromValue(String v) {
        for (PRODUCTTYPE c: PRODUCTTYPE.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
	
}
