package org.flowframe.kernel.common.utils;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitialThreadLocal<T> extends CentralizedThreadLocal<T> {
	
	private static Logger _log = LoggerFactory.getLogger(InitialThreadLocal.class);

	public InitialThreadLocal(String name, T initialValue) {
		this(name, initialValue, false);
	}

	public InitialThreadLocal(String name, T initialValue, boolean shortLived) {
		super(shortLived);

		_name = name;
		_initialValue = initialValue;

		if (_initialValue instanceof Cloneable) {
			try {
				_cloneMethod = _initialValue.getClass().getMethod(
					_METHOD_CLONE);
			}
			catch (Exception e) {
				_log.error(e.getMessage(), e);
			}
		}
	}

	@Override
	public String toString() {
		if (_name != null) {
			return _name;
		}
		else {
			return super.toString();
		}
	}

	@Override
	protected T initialValue() {
		if (_cloneMethod != null) {
			try {
				return (T)_cloneMethod.invoke(_initialValue);
			}
			catch (Exception e) {
				_log.error(e.getMessage(), e);
			}
		}

		return _initialValue;
	}

	private static final String _METHOD_CLONE = "clone";

	private Method _cloneMethod;
	private T _initialValue;
	private String _name;

}