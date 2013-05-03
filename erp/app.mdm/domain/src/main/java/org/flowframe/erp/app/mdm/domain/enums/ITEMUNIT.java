package org.flowframe.erp.app.mdm.domain.enums;


public enum ITEMUNIT {
	STORAGEINMB("STORAGEINMB"),
	FILECOUNT("FILECOUNT"),
	DATASOURCECOUNT("DATASOURCECOUNT"),	
	DATATARGETCOUNT("DATATARGETCOUNT"),	
	DOWNLOADINMB("DOWNLOADINMB"),
	DOWNLOADCOUNT("DOWNLOADCOUNT"),
	UPLOADINMB("UPLOADINMB"),
	UPLOADCOUNT("DOWNLOADCOUNT"),
	RECORDCOUNT("RECORDCOUNT"),
	ROWCOUNT("ROWCOUNT"),
	COLUMNCOUNT("COLUMNCOUNT"),
	QUERYRESULTSIZE("QUERYRESULTSIZE"),
	REPORTPAGECOUNT("REPORTPAGECOUNT"),
	JOBDEFCOUNT("JOBDEFCOUNT"),
	JOBRUNCOUNT("JOBRUNCOUNT");
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
