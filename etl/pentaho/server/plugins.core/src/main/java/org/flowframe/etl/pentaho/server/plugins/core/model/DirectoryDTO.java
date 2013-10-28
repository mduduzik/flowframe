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

    private String icon = "/etl/images/conxbi/etl/folder_open.gif";
    private Boolean allowDrag = false;
    private Boolean allowDrop = false;
    private Boolean leaf = false;
    private Boolean hasChildren = false;
    private Boolean singleClickExpand = true;
    private String ddenabled = "true";

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean getAllowDrag() {
        return allowDrag;
    }

    public void setAllowDrag(Boolean allowDrag) {
        this.allowDrag = allowDrag;
    }

    public Boolean getAllowDrop() {
        return allowDrop;
    }

    public void setAllowDrop(Boolean allowDrop) {
        this.allowDrop = allowDrop;
    }

    public Boolean getLeaf() {
        return leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    public Boolean getHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(Boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    public Boolean getSingleClickExpand() {
        return singleClickExpand;
    }

    public void setSingleClickExpand(Boolean singleClickExpand) {
        this.singleClickExpand = singleClickExpand;
    }

    public String getDdenabled() {
        return ddenabled;
    }

    public void setDdenabled(String ddenabled) {
        this.ddenabled = ddenabled;
    }
}
