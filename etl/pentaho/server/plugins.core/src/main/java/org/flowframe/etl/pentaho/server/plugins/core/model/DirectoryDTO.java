package org.flowframe.etl.pentaho.server.plugins.core.model;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Mduduzi
 * Date: 8/15/13
 * Time: 11:43 PM
 * To
 */
public class DirectoryDTO implements Serializable {
    private String name;
    private long dirObjectId;
    private String itemtype;

    private DirectoryDTO parent;

    public DirectoryDTO(String name, long dirObjectId) {
        this.name = name;
        this.dirObjectId = dirObjectId;
    }

    public DirectoryDTO() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDirObjectId() {
        return dirObjectId;
    }

    public void setDirObjectId(long dirObjectId) {
        this.dirObjectId = dirObjectId;
    }

    public String getItemtype() {
        return itemtype;
    }

    public void setItemtype(String itemtype) {
        this.itemtype = itemtype;
    }

    public DirectoryDTO getParent() {
        return parent;
    }

    public void setParent(DirectoryDTO parent) {
        this.parent = parent;
    }
}
