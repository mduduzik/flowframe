package org.flowframe.kernel.common.utils.reflection;


import java.lang.reflect.Method;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class MethodCache {

	public static Method get(
			Map<String, Class<?>> classesMap, Map<MethodKey, Method> methodsMap,
			String className, String methodName)
		throws ClassNotFoundException, NoSuchMethodException {

		return get(className, methodName, new Class[0]);
	}

	public static Method get(
			Map<String, Class<?>> classesMap, Map<MethodKey, Method> methodsMap,
			String className, String methodName, Class<?>[] parameterTypes)
		throws ClassNotFoundException, NoSuchMethodException {

		MethodKey methodKey = new MethodKey(
			className, methodName, parameterTypes);

		return getInstance()._get(classesMap, methodsMap, methodKey);
	}

	public static Method get(MethodKey methodKey)
		throws ClassNotFoundException, NoSuchMethodException {

		return getInstance()._get(null, null, methodKey);
	}

	public static Method get(String className, String methodName)
		throws ClassNotFoundException, NoSuchMethodException {

		return get(null, null, className, methodName);
	}

	public static Method get(
			String className, String methodName, Class<?>[] parameterTypes)
		throws ClassNotFoundException, NoSuchMethodException {

		return get(null, null, className, methodName, parameterTypes);
	}

	public static MethodCache getInstance() {
		//PortalRuntimePermission.checkGetBeanProperty(MethodCache.class);

		return _instance;
	}

	public static Method put(MethodKey methodKey, Method method) {
		return getInstance()._put(methodKey, method);
	}

	public static void remove(Class<?> clazz) {
		getInstance()._remove(clazz);
	}

	public static void reset() {
		getInstance()._reset();
	}

	private MethodCache() {
		_classesMap = new ConcurrentHashMap<String, Class<?>>();
		_methodsMap = new ConcurrentHashMap<MethodKey, Method>();
	}

	private Method _get(
			Map<String, Class<?>> classesMap, Map<MethodKey, Method> methodsMap,
			MethodKey methodKey)
		throws ClassNotFoundException, NoSuchMethodException {

		if (classesMap == null) {
			classesMap = _classesMap;
		}

		if (methodsMap == null) {
			methodsMap = _methodsMap;
		}

		Method method = methodsMap.get(methodKey);

		if (method == null) {
			String className = methodKey.getClassName();
			String methodName = methodKey.getMethodName();
			Class<?>[] parameterTypes = methodKey.getParameterTypes();

			Class<?> clazz = classesMap.get(className);

			if (clazz == null) {
				Thread currentThread = Thread.currentThread();

				ClassLoader contextClassLoader =
					currentThread.getContextClassLoader();

				clazz = contextClassLoader.loadClass(className);

				classesMap.put(className, clazz);
			}

			method = clazz.getMethod(methodName, parameterTypes);

			methodsMap.put(methodKey, method);
		}

		return method;
	}

	private Method _put(MethodKey methodKey, Method method) {
		return _methodsMap.put(methodKey, method);
	}

	private void _remove(Class<?> clazz) {
		_classesMap.remove(clazz.getName());

		for (Method method : clazz.getMethods()) {
			_methodsMap.remove(new MethodKey(method));
		}
	}

	private void _reset() {
		_classesMap.clear();
		_methodsMap.clear();
	}

	private static MethodCache _instance = new MethodCache();

	private Map<String, Class<?>> _classesMap;
	private Map<MethodKey, Method> _methodsMap;

}