package org.flowframe.kernel.common.utils.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.flowframe.kernel.common.utils.StringPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MethodInvoker {
	
	private static Logger _log = LoggerFactory.getLogger(MethodInvoker.class);

	public static Object invoke(MethodWrapper methodWrapper)
		throws ClassNotFoundException, IllegalAccessException,
			   InstantiationException, InvocationTargetException,
			   NoSuchFieldException, NoSuchMethodException {

		return invoke(methodWrapper, true);
	}

	public static Object invoke(
			MethodWrapper methodWrapper, boolean newInstance)
		throws ClassNotFoundException, IllegalAccessException,
			   InstantiationException, InvocationTargetException,
			   NoSuchFieldException, NoSuchMethodException {

		Object targetObject = null;

		if (newInstance) {
			Thread currentThread = Thread.currentThread();

			ClassLoader contextClassLoader =
				currentThread.getContextClassLoader();

			targetObject = contextClassLoader.loadClass(
				methodWrapper.getClassName()).newInstance();
		}

		Object[] methodAndArguments = _lookupMethodAndArguments(
			methodWrapper, targetObject);

		Object returnObject = null;

		if (methodAndArguments[0] != null) {
			Method method = (Method)methodAndArguments[0];
			Object[] arguments = (Object[])methodAndArguments[1];

			returnObject = method.invoke(targetObject, arguments);
		}

		return returnObject;
	}

	public static Object invoke(
			MethodWrapper methodWrapper, Object targetObject)
		throws ClassNotFoundException, IllegalAccessException,
			   InvocationTargetException, NoSuchFieldException,
			   NoSuchMethodException {

		Object[] methodAndArguments = _lookupMethodAndArguments(
			methodWrapper, targetObject);

		Object returnObject = null;

		if (methodAndArguments[0] != null) {
			Method method = (Method)methodAndArguments[0];
			Object[] arguments = (Object[])methodAndArguments[1];

			returnObject = method.invoke(targetObject, arguments);
		}

		return returnObject;
	}

	private static Object[] _lookupMethodAndArguments(
			MethodWrapper methodWrapper, Object targetObject)
		throws ClassNotFoundException, IllegalAccessException,
			   InvocationTargetException, NoSuchFieldException,
			   NoSuchMethodException {

		Object[] methodAndArguments = new Object[2];

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		String className = methodWrapper.getClassName();
		String methodName = methodWrapper.getMethodName();
		Object[] arguments = methodWrapper.getArguments();
		String[] argumentClassNames = methodWrapper.getArgumentClassNames();

		List<Class<?>> parameterTypes = new ArrayList<Class<?>>();

		for (int i = 0; i < arguments.length; i++) {
			if (arguments[i] == null) {
				_log.error(
					"Cannot invoke " + className + " " + methodName +
						" on position " + i + " because it is null");
			}

			Class<?> argClass = null;

			if (argumentClassNames != null) {
				argClass = _primitiveTypeMap.get(argumentClassNames[i]);

				if (argClass == null) {
					argClass = Class.forName(
						argumentClassNames[i], true, contextClassLoader);
				}
			}
			else {
				argClass = arguments[i].getClass();
			}

			if (ClassUtil.isSubclass(argClass, PrimitiveWrapper.class)) {
				parameterTypes.add(
					(Class<?>)argClass.getField("TYPE").get(arguments[i]));

				MethodKey methodKey = new MethodKey(
					argClass.getName(), "getValue");

				Method method = MethodCache.get(methodKey);

				arguments[i] = method.invoke(arguments[i], (Object[])null);
			}
			else if (arguments[i] instanceof NullWrapper) {
				NullWrapper nullWrapper = (NullWrapper)arguments[i];

				String wrappedClassName = nullWrapper.getClassName();

				if (wrappedClassName.startsWith(StringPool.OPEN_BRACKET) &&
					wrappedClassName.endsWith(StringPool.SEMICOLON)) {

					wrappedClassName = wrappedClassName.substring(
						2, wrappedClassName.length() - 1);

					Class<?> wrappedClass = contextClassLoader.loadClass(
						wrappedClassName);

					parameterTypes.add(
						Array.newInstance(wrappedClass, 0).getClass());
				}
				else {
					Class<?> wrappedClass = contextClassLoader.loadClass(
						wrappedClassName);

					parameterTypes.add(wrappedClass);
				}

				arguments[i] = null;
			}
			else {
				parameterTypes.add(argClass);
			}
		}

		MethodKey methodKey = null;

		Method method = null;

		try {
			methodKey = new MethodKey(
				methodWrapper.getClassName(), methodWrapper.getMethodName(),
				parameterTypes.toArray(new Class[parameterTypes.size()]));

			method = MethodCache.get(methodKey);
		}
		catch (NoSuchMethodException nsme) {
			Class<?> clazz = null;

			if (targetObject == null) {
				clazz = contextClassLoader.loadClass(className);
			}
			else {
				clazz = targetObject.getClass();
			}

			Method[] methods = clazz.getMethods();

			for (int i = 0; i < methods.length; i++) {
				Class<?>[] methodParameterTypes =
					methods[i].getParameterTypes();

				if (methods[i].getName().equals(methodName) &&
					(methodParameterTypes.length == parameterTypes.size())) {

					boolean correctParams = true;

					for (int j = 0; j < parameterTypes.size(); j++) {
						Class<?> a = parameterTypes.get(j);
						Class<?> b = methodParameterTypes[j];

						if (!ClassUtil.isSubclass(a, b)) {
							correctParams = false;

							break;
						}
					}

					if (correctParams) {
						method = methods[i];

						MethodCache.put(methodKey, method);

						break;
					}
				}
			}

			if (method == null) {
				throw nsme;
			}
		}

		methodAndArguments[0] = method;
		methodAndArguments[1] = arguments;

		return methodAndArguments;
	}


	private static Map<String, Class<?>> _primitiveTypeMap =
		new HashMap<String, Class<?>>();

	static {
		_primitiveTypeMap.put("char", char.class);
		_primitiveTypeMap.put("boolean", boolean.class);
		_primitiveTypeMap.put("byte", byte.class);
		_primitiveTypeMap.put("double", double.class);
		_primitiveTypeMap.put("float", float.class);
		_primitiveTypeMap.put("int", int.class);
		_primitiveTypeMap.put("long", long.class);
		_primitiveTypeMap.put("short", short.class);
	}

}