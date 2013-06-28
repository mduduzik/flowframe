package org.phakama.vaadin.mvp;

import java.io.Serializable;

public interface ILogger extends Serializable {
	boolean isLogging();
	void enableLogging();
	void disableLogging();
}
