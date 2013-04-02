package org.flowframe.kernel.common.utils.reflection;

import java.lang.reflect.Method;


public interface MethodParametersResolver {

	public MethodParameter[] resolveMethodParameters(Method method);

}