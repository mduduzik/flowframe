package org.flowframe.erp.domain.enums;


public enum ITEMUNIT {
	MEGABYTES("MEGABYTES"),
	DOWNLOADCOUNT("DOWNLOADCOUNT"),
	UPLOADCOUNT("DOWNLOADCOUNT"),
	RECORDCOUNT("RECORDCOUNT"),
	ROWCOUNT("ROWCOUNT"),
	COLUMNCOUNT("COLUMNCOUNT");
    private final String value;

    ITEMUNIT(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ITEMUNIT fromValue(String v) {
        for (ITEMUNIT c: ITEMUNIT.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
	
}
