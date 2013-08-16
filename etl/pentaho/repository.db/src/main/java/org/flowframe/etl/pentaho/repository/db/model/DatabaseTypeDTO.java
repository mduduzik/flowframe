package org.flowframe.etl.pentaho.repository.db.model;

import org.pentaho.di.repository.LongObjectId;

/**
 * Created with IntelliJ IDEA.
 * User: Mduduzi
 * Date: 8/15/13
 * Time: 11:43 PM
 * To
 */
public class DatabaseTypeDTO {
    private long objectId;
    private String code;
    private String description;

    public DatabaseTypeDTO() {}

    public DatabaseTypeDTO(LongObjectId objId,
                           String code,
                           String description) {
        this.objectId = ((LongObjectId)objId).longValue();
        this.code = code;
        this.description = description;
    }

    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
