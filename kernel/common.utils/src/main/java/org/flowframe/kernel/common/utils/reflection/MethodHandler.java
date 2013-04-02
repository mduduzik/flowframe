package org.flowframe.kernel.common.utils.reflection;

import java.io.Serializable;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import java.util.Arrays;

public class MethodHandler implements Serializable {

	public MethodHandler(Method method, Object... arguments) {
		this(new MethodKey(method), arguments);
	}

	public MethodHandler(MethodKey methodKey, Object... arguments) {
		_methodKey = methodKey;
		_arguments = arguments;
	}

	public Object[] getArguments() {
		Object[] arguments = new Object[_arguments.length];

		System.arraycopy(_arguments, 0, arguments, 0, _arguments.length);

		return arguments;
	}

	public Class<?>[] getArgumentsClasses() {
		return _methodKey.getParameterTypes();
	}

	public String getClassName() {
		return _methodKey.getClassName();
	}

	public MethodKey getMethodKey() {
		return _methodKey;
	}

	public String getMethodName() {
		return _methodKey.getMethodName();
	}

	public Object invoke(boolean newInstance) throws Exception {
		Method method = MethodCache.get(_methodKey);

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		Object targetObject = null;

		if (newInstance && !Modifier.isStatic(method.getModifiers())) {
			Class<?> targetClass = contextClassLoader.loadClass(getClassName());

			targetObject = targetClass.newInstance();
		}

		return method.invoke(targetObject, _arguments);
	}

	public Object invoke(Object target) throws Exception {
		Method method = MethodCache.get(_methodKey);

		return method.invoke(target, _arguments);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(5);

		sb.append("{arguments=");
		sb.append(Arrays.toString(_arguments));
		sb.append(", methodKey=");
		sb.append(_methodKey);
		sb.append("}");

		return sb.toString();
	}

	private Object[] _arguments;
	private MethodKey _methodKey;

}