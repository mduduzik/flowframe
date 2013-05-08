package org.flowframe.erp.app.contractmanagement.type;


public enum SUBSCRIPTIONSTATUS {
	ACTIVE("ACTIVE"),
	TRAILING("TRAILING"),
	PASTDUE("PASTDUE"),
	CANCELLED("CANCELLED"),
	UNPAID("UNPAID");
	
    private final String value;

    SUBSCRIPTIONSTATUS(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SUBSCRIPTIONSTATUS fromValue(String v) {
        for (SUBSCRIPTIONSTATUS c: SUBSCRIPTIONSTATUS.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
	
}
