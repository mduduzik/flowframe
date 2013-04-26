package org.flowframe.erp.app.salesmanagement.enums;


public enum SALESINVOICELINETYPE {
	SUBSCRIPTION("SUBSCRIPTION"),
	ADDED("ADDED");
    private final String value;

    SALESINVOICELINETYPE(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SALESINVOICELINETYPE fromValue(String v) {
        for (SALESINVOICELINETYPE c: SALESINVOICELINETYPE.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
	
}
