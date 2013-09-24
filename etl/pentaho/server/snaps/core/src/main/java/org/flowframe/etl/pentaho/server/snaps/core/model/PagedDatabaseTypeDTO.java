package org.flowframe.etl.pentaho.server.snaps.core.model;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mduduzi
 * Date: 8/15/13
 * Time: 11:43 PM
 * To
 */
public class PagedDatabaseTypeDTO {
    private long totalCount;
    private String root;
    private List<DatabaseTypeDTO> data;

    public PagedDatabaseTypeDTO() {}

    public PagedDatabaseTypeDTO(List<DatabaseTypeDTO> data, long totalCount, String root) {
        this.data = data;
        this.totalCount = totalCount;
        this.root = root;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public List<DatabaseTypeDTO> getData() {
        return data;
    }

    public void setData(List<DatabaseTypeDTO> data) {
        this.data = data;
    }
}
