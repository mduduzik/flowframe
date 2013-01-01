package org.flowframe.ui.component.dao.services;

import java.util.List;

import org.flowframe.ds.domain.DataSource;
import org.flowframe.ui.component.domain.AbstractComponent;
import org.flowframe.ui.component.domain.masterdetail.MasterDetailComponent;

public interface IComponentDAOService {
	public AbstractComponent get(long id);
	
	public AbstractComponent getByCode(String code);
	
	public MasterDetailComponent getMasterDetailByDataSource(DataSource ds);
	
	public List<AbstractComponent> getAll();	

	public AbstractComponent add(AbstractComponent record);
	
	public void delete(AbstractComponent record);

	public AbstractComponent update(AbstractComponent record);
}
