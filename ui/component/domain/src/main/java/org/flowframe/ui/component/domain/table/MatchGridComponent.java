package org.flowframe.ui.component.domain.table;

import org.flowframe.ds.domain.DataSource;

public class MatchGridComponent extends GridComponent {
	private static final long serialVersionUID = -4200861331740833004L;
	
	private DataSource unmatchedDataSource;
	private DataSource matchedDataSource;
	private boolean isDynamic;

	public MatchGridComponent() {
		super("matchgrid");
	}
	
	public MatchGridComponent(DataSource unmatchedDataSource, DataSource matchedDataSource) {
		this();
		this.setDataSource(matchedDataSource);
		this.unmatchedDataSource = unmatchedDataSource;
		this.matchedDataSource = matchedDataSource;
	}

	public DataSource getUnmatchedDataSource() {
		return unmatchedDataSource;
	}

	public void setUnmatchedDataSource(DataSource unmatchedDataSource) {
		this.unmatchedDataSource = unmatchedDataSource;
	}

	public DataSource getMatchedDataSource() {
		this.setDataSource(matchedDataSource);
		return matchedDataSource;
	}

	public void setMatchedDataSource(DataSource matchedDataSource) {
		this.matchedDataSource = matchedDataSource;
	}

	public boolean isDynamic() {
		return isDynamic;
	}

	public void setDynamic(boolean isDynamic) {
		this.isDynamic = isDynamic;
	}
}
