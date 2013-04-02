package org.flowframe.kernel.common.utils.reflection;


import java.lang.reflect.Method;


public class MethodParametersResolverUtil {

	public static MethodParametersResolver getMethodParametersResolver() {
/*		PortalRuntimePermission.checkGetBeanProperty(
			MethodParametersResolverUtil.class);*/

		return _methodParametersResolver;
	}

	public static MethodParameter[] resolveMethodParameters(Method method) {
		return getMethodParametersResolver().resolveMethodParameters(method);
	}

	public void setMethodParametersResolver(
		MethodParametersResolver methodParametersResolver) {

		/*PortalRuntimePermission.checkSetBeanProperty(getClass());*/

		_methodParametersResolver = methodParametersResolver;
	}

	private static MethodParametersResolver _methodParametersResolver;

}