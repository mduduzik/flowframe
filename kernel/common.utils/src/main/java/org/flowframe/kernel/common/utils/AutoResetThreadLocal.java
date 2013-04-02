package org.flowframe.kernel.common.utils;

public class AutoResetThreadLocal<T> extends InitialThreadLocal<T> {

	public AutoResetThreadLocal(String name) {
		super(name, null, true);
	}

	public AutoResetThreadLocal(String name, T initialValue) {
		super(name, initialValue, true);
	}

}