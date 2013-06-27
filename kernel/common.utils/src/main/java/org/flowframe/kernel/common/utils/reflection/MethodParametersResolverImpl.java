package org.flowframe.kernel.common.utils.reflection;


import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;

import org.flowframe.kernel.common.utils.InitialThreadLocal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import jodd.paramo.Paramo;


public class MethodParametersResolverImpl implements MethodParametersResolver {
	private static Logger _log = LoggerFactory.getLogger(MethodParametersResolverImpl.class);

	public MethodParameter[] resolveMethodParameters(Method method) {
		MethodParameter[] methodParameters = _methodParameters.get(method);

		if (methodParameters != null) {
			return methodParameters;
		}

		try {
			Class<?>[] methodParameterTypes = method.getParameterTypes();

			/*
			jodd.paramo.MethodParameter[] joddMethodParameters = Paramo.resolveParameters(method);
			methodParameters = new MethodParameter[joddMethodParameters.length];
			methodParameters = new MethodParameter[0];
			for (int i = 0; i < joddMethodParameters.length; i++) {
				methodParameters[i] = new MethodParameter(
					joddMethodParameters[i].getName(),
					joddMethodParameters[i].getSignature(),
					methodParameterTypes[i]);
			}
			*/
		}
		catch (Exception e) {
			_log.error(e.getMessage(), e);

			return null;
		}

		_methodParameters.put(method, methodParameters);

		return methodParameters;
	}

	private Map<AccessibleObject, MethodParameter[]> _methodParameters =
		new HashMap<AccessibleObject, MethodParameter[]>();

}