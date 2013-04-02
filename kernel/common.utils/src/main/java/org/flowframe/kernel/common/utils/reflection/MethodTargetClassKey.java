package org.flowframe.kernel.common.utils.reflection;

import java.lang.reflect.Method;

import org.flowframe.kernel.common.utils.StringBundler;
import org.flowframe.kernel.common.utils.StringPool;
import org.flowframe.kernel.common.utils.Validator;


public class MethodTargetClassKey {

	public MethodTargetClassKey(Method method, Class<?> targetClass) {
		_method = method;
		_targetClass = targetClass;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof MethodTargetClassKey)) {
			return false;
		}

		MethodTargetClassKey methodTargetClassKey = (MethodTargetClassKey)obj;

		if ((_targetClass == methodTargetClassKey._targetClass) &&
			Validator.equals(_method, methodTargetClassKey._method)) {

			return true;
		}

		return false;
	}

	public Method getMethod() {
		return _method;
	}

	public Class<?> getTargetClass() {
		return _targetClass;
	}

	public Method getTargetMethod() {
		if ((_targetMethod == null) && (_targetClass != null)) {
			try {
				_targetMethod = _targetClass.getDeclaredMethod(
					_method.getName(), _method.getParameterTypes());
			}
			catch (Throwable t) {
			}
		}

		return _targetMethod;
	}

	@Override
	public int hashCode() {
		if (_hashCode == 0) {
			int hashCode = 77;

			if (_method != null) {
				hashCode += _method.hashCode();
			}

			hashCode = 11 * hashCode;

			if (_targetClass != null) {
				String targetClassName = _targetClass.getName();

				hashCode += targetClassName.hashCode();
			}

			_hashCode = hashCode;
		}

		return _hashCode;
	}

	@Override
	public String toString() {
		if (_toString == null) {
			Class<?>[] parameterTypes = _method.getParameterTypes();

			StringBundler sb = new StringBundler(parameterTypes.length * 2 + 6);

			sb.append(_method.getDeclaringClass().getName());
			sb.append(StringPool.PERIOD);
			sb.append(_method.getName());
			sb.append(StringPool.OPEN_PARENTHESIS);

			for (int i = 0; i < parameterTypes.length; i++) {
				sb.append(parameterTypes[i].getName());

				if ((i + 1) < parameterTypes.length) {
					sb.append(StringPool.COMMA);
				}
			}

			sb.append(StringPool.CLOSE_PARENTHESIS);

			if (_targetClass != null) {
				sb.append(StringPool.AT);
				sb.append(_targetClass.getName());
			}

			_toString = sb.toString();
		}

		return _toString;
	}

	private int _hashCode;
	private Method _method;
	private Class<?> _targetClass;
	private Method _targetMethod;
	private String _toString;

}