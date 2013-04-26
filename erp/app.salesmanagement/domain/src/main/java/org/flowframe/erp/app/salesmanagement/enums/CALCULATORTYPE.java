package org.flowframe.erp.app.salesmanagement.enums;


public enum CALCULATORTYPE {
	BASE("BASE"),
	CUSTOM("CUSTOM");
    private final String value;

    CALCULATORTYPE(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CALCULATORTYPE fromValue(String v) {
        for (CALCULATORTYPE c: CALCULATORTYPE.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
	
}
