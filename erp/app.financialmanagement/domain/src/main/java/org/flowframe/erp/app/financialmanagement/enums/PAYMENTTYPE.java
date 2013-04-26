package org.flowframe.erp.app.financialmanagement.enums;


public enum PAYMENTTYPE {
	CASH("CASH"),
	CHECQUE("CHECQUE"),
	WIRETRANSFER("WIRETRANSFER"),
	CREDITCARD("CREDITCARD");
    private final String value;

    PAYMENTTYPE(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PAYMENTTYPE fromValue(String v) {
        for (PAYMENTTYPE c: PAYMENTTYPE.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
	
}
