package org.flowframe.ui.vaadin.converters.common;

import org.flowframe.ui.vaadin.common.mvp.AbstractMainApplication;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseConverter {
	
	@Autowired
	protected AbstractMainApplication application;
}
