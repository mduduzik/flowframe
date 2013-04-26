package com.conx.bi.kernel.core.enums;


public enum CALCULATORUNIT {
	MEGABYTES("MEGABYTES"),
	DOWNLOADCOUNT("DOWNLOADCOUNT"),
	UPLOADCOUNT("DOWNLOADCOUNT"),
	RECORDCOUNT("RECORDCOUNT"),
	ROWCOUNT("ROWCOUNT"),
	COLUMNCOUNT("COLUMNCOUNT");
    private final String value;

    CALCULATORUNIT(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CALCULATORUNIT fromValue(String v) {
        for (CALCULATORUNIT c: CALCULATORUNIT.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
	
}
