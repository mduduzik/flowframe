package org.flowframe.kernel.common.utils.reflection;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;

import org.flowframe.kernel.common.utils.StringBundler;
import org.flowframe.kernel.common.utils.StringPool;

public class MethodKey implements Serializable {

	public MethodKey(Method method) {
		this(
			method.getDeclaringClass().getName(), method.getName(),
			method.getParameterTypes());
	}

	public MethodKey(
		String className, String methodName, Class<?>... parameterTypes) {

		_className = className;
		_methodName = methodName;
		_parameterTypes = parameterTypes;
	}

	public MethodKey(
			String className, String methodName, String[] parameterTypeNames)
		throws ClassNotFoundException {

		_className = className;
		_methodName = methodName;

		_parameterTypes = new Class[parameterTypeNames.length];

		ClassLoader classLoader = null;

		if (parameterTypeNames.length > 0) {
			Thread currentThread = Thread.currentThread();

			classLoader = currentThread.getContextClassLoader();
		}

		for (int i = 0; i < parameterTypeNames.length; i++) {
			String parameterTypeName = parameterTypeNames[i];

			_parameterTypes[i] = _getClass(parameterTypeName, classLoader);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		MethodKey methodKey = (MethodKey)obj;

		String string = toString();

		if (string.equals(methodKey.toString())) {
			return true;
		}
		else {
			return false;
		}
	}

	public String getClassName() {
		return _className;
	}

	public String getMethodName() {
		return _methodName;
	}

	public Class<?>[] getParameterTypes() {
		return _parameterTypes;
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public String toString() {
		return _toString();
	}

	private Class<?> _getClass(String typeName, ClassLoader classLoader)
		throws ClassNotFoundException {

		if (_primitiveClasses.containsKey(typeName)) {
			return _primitiveClasses.get(typeName);
		}
		else {
			return Class.forName(typeName, true, classLoader);
		}
	}

	private String _toString() {
		if (_toString == null) {
			if ((_parameterTypes != null) && (_parameterTypes.length > 0)) {
				StringBundler sb = new StringBundler(
					3 + _parameterTypes.length);

				sb.append(_className);
				sb.append(_methodName);
				sb.append(StringPool.DASH);

				for (Class<?> parameterType : _parameterTypes) {
					sb.append(parameterType.getName());
				}

				_toString = sb.toString();
			}
			else {
				_toString = _className.concat(_methodName);
			}
		}

		return _toString;
	}

	private static Map<String, Class<?>> _primitiveClasses =
		new HashMap<String, Class<?>>();

	static {
		_primitiveClasses.put("byte", byte.class);
		_primitiveClasses.put("boolean", boolean.class);
		_primitiveClasses.put("char", char.class);
		_primitiveClasses.put("double", double.class);
		_primitiveClasses.put("float", float.class);
		_primitiveClasses.put("int", int.class);
		_primitiveClasses.put("long", long.class);
		_primitiveClasses.put("short", short.class);
	}

	private String _className;
	private String _methodName;
	private Class<?>[] _parameterTypes;
	private String _toString;

}