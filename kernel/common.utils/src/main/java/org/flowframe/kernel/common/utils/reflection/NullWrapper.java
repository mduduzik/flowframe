package org.flowframe.kernel.common.utils.reflection;

import java.io.Serializable;

public class NullWrapper implements Serializable {

	public NullWrapper(String className) {
		_className = className;
	}

	public String getClassName() {
		return _className;
	}

	private String _className;

}