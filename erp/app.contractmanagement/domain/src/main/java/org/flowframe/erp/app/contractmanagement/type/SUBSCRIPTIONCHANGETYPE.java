package org.flowframe.erp.app.contractmanagement.type;


public enum SUBSCRIPTIONCHANGETYPE {
	UPDATE("UPDATE"),
	CANCEL("CANCEL");
    private final String value;

    SUBSCRIPTIONCHANGETYPE(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SUBSCRIPTIONCHANGETYPE fromValue(String v) {
        for (SUBSCRIPTIONCHANGETYPE c: SUBSCRIPTIONCHANGETYPE.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
	
}
