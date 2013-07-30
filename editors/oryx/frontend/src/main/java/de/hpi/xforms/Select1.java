package de.hpi.xforms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @author jan-felix.schwarz@student.hpi.uni-potsdam.de
 *
 */
public class Select1 extends FormControl implements ListUICommonContainer {
	
	protected List<ListUICommon> listUICommons;

	public Select1() {
		super();
		attributes.put("selection", null);
		attributes.put("incremental", null);
	}
	
	public List<ListUICommon> getListUICommons() {
		if(listUICommons==null)
			listUICommons = new ArrayList<ListUICommon>();
		Collections.sort(listUICommons, new ListUICommonComparator());
		return listUICommons;
	}
	
	@Override
	public String getStencilId() {
		return "Select1";
	}
	
	@Override
	public String getTagName() {
		return "select1";
	}
	
}
