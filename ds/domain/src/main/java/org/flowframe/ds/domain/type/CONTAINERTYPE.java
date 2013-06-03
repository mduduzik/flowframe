package org.flowframe.ds.domain.type;


public enum CONTAINERTYPE {
	BEAN("BEAN"),
	ENTITY("ENTITY");
    private final String value;

    CONTAINERTYPE(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CONTAINERTYPE fromValue(String v) {
        for (CONTAINERTYPE c: CONTAINERTYPE.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
	
}
