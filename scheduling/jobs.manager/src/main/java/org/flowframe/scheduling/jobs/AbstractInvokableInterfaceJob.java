package org.flowframe.scheduling.jobs;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.flowframe.kernel.common.utils.GetterUtil;
import org.flowframe.kernel.common.utils.StringBundler;
import org.flowframe.kernel.common.utils.StringPool;
import org.flowframe.kernel.common.utils.StringUtil;
import org.flowframe.kernel.common.utils.Validator;
import org.flowframe.kernel.services.ISuperService;
import org.flowframe.scheduling.remote.services.IJobsManager;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public abstract class  AbstractInvokableInterfaceJob implements Job {
	
	private static final String APPLICATION_CONTEXT_KEY = "applicationContext";
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static Pattern _fieldDescriptorPattern = Pattern.compile("^(.*?)((\\[\\])*)$", Pattern.DOTALL);

	private Map<String, Object[]> _methodCache = new HashMap<String, Object[]>();	
	
	private String fullJavaInterfaceClassName;
	
	private String methodName;
	
	private String[] parameterNames;
	
	private String[] parameterTypes;
	
	private String[] parameterValues;

	private IJobsManager jobManager;
	
	public AbstractInvokableInterfaceJob(){
	}
	
	public AbstractInvokableInterfaceJob(String fullJavaInterfaceClassName, String methodName, String[] parameterNames, String[] parameterTypes, String[] parameterValues) {
		super();
		this.fullJavaInterfaceClassName = fullJavaInterfaceClassName;
		this.methodName = methodName;
		this.parameterNames = parameterNames;
		this.parameterTypes = parameterTypes;
		this.parameterValues = parameterValues;
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		ISuperService service = null;
		try {
			service = (ISuperService)getJobManager(context).getServiceByInterfaceClassName(this.fullJavaInterfaceClassName);
		} catch (SchedulerException e1) {
			throw new JobExecutionException("Error Getting IJobManager from SrpingContext",e1);
		}
		Class clazz = service.getClass();
		Object[] methodAndParameterTypes = getMethodAndParameterTypes(service.getClass(), methodName, this.parameterNames, this.parameterTypes);
		if (methodAndParameterTypes != null) {
			Method method = (Method)methodAndParameterTypes[0];
			Type[] parameterTypes = (Type[])methodAndParameterTypes[1];
			Object[] args = new Object[this.parameterNames.length];

			for (int i = 0; i < this.parameterNames.length; i++) {
				try {
					args[i] = getArgValue(
						this.parameterValues[i], clazz, methodName, this.parameterNames[i],
						parameterTypes[i]);
				} catch (Exception e) {
					throw new JobExecutionException("AbstractInvokableInterfaceJob.execute unable to getArgValue("+this.parameterNames[i]+")");
				}
			}

			try {
				if (logger.isDebugEnabled()) {
					logger.debug(
						"Invoking " + clazz + " on method " + method.getName() +
							" with args " + Arrays.toString(args));
				}

				Object returnObj = method.invoke(clazz, args);
			}
			catch (Exception e) {
				if (logger.isDebugEnabled()) {
					logger.debug(
						"Invoked " + clazz + " on method " + method.getName() +
							" with args " + Arrays.toString(args),
						e);
				}
			}
		}
	}

	private IJobsManager getJobManager(JobExecutionContext jobCtx) throws SchedulerException {
		ApplicationContext app = getApplicationContext(jobCtx);
		this.jobManager = (IJobsManager)app.getBean(IJobsManager.class.getName());
		return this.jobManager;
	}
	
    private ApplicationContext getApplicationContext(JobExecutionContext context )
    throws SchedulerException {
        ApplicationContext appCtx = null;
        appCtx = (ApplicationContext)context.getScheduler().getContext().get(APPLICATION_CONTEXT_KEY);
        if (appCtx == null) {
            throw new JobExecutionException(
                    "No application context available in scheduler context for key \"" + APPLICATION_CONTEXT_KEY + "\"");
        }
        return appCtx;
    }	

	private Object[] getMethodAndParameterTypes(Class<? extends ISuperService> clazz, String methodName, String[] parameterNames, String[] parameterTypes) {
		
		StringBundler sb = new StringBundler(5);

		sb.append(clazz.getName());
		sb.append("_METHOD_NAME_");
		sb.append(methodName);
		sb.append("_PARAMETERS_");

		String parameterTypesNames = StringUtil.merge(parameterTypes);

		if (Validator.isNull(parameterTypesNames)) {
			sb.append(parameterNames.length);
		}
		else {
			sb.append(parameterTypesNames);
		}

		String key = sb.toString();

		Object[] methodAndParameterTypes = _methodCache.get(key);

		if (methodAndParameterTypes != null) {
			return methodAndParameterTypes;
		}

		Method method = null;
		Type[] methodParameterTypes = null;

		Method[] methods = clazz.getMethods();

		for (Method curMethod : methods) {
			if (curMethod.getName().equals(methodName)) {
				Type[] curParameterTypes = curMethod.getGenericParameterTypes();

				if (curParameterTypes.length == parameterNames.length) {
					if ((parameterTypes.length > 0) &&
						(parameterTypes.length == curParameterTypes.length)) {

						boolean match = true;

						for (int j = 0; j < parameterTypes.length; j++) {
							String t1 = parameterTypes[j];
							String t2 = getTypeNameOrClassDescriptor(curParameterTypes[j]);

							if (!t1.equals(t2)) {
								match = false;
							}
						}

						if (match) {
							method = curMethod;
							methodParameterTypes = curParameterTypes;

							break;
						}
					}
					else if (method != null) {
						String parametersString = StringUtil.merge(parameterNames);

						logger.error(
							"Obscure method name for class " + clazz +
								", method " + methodName + ", and parameters " +
									parametersString);

						return null;
					}
					else {
						method = curMethod;
						methodParameterTypes = curParameterTypes;
					}
				}
			}
		}

		if (method != null) {
			methodAndParameterTypes = new Object[] {
				method, methodParameterTypes
			};

			_methodCache.put(key, methodAndParameterTypes);

			return methodAndParameterTypes;
		}
		else {
			String parametersString = StringUtil.merge(parameterNames);

			logger.error(
				"No method found for class " + clazz + ", method " +
					methodName + ", and parameters " + parametersString);

			return null;
		}	
	}
	
	protected String getTypeNameOrClassDescriptor(Type type) {
		String typeName = type.toString();

		if (typeName.contains("class ")) {
			return typeName.substring(6);
		}

		Matcher matcher = _fieldDescriptorPattern.matcher(typeName);

		while (matcher.find()) {
			String dimensions = matcher.group(2);
			String fieldDescriptor = matcher.group(1);

			if (Validator.isNull(dimensions)) {
				return fieldDescriptor;
			}

			dimensions = dimensions.replace(
				StringPool.CLOSE_BRACKET, StringPool.BLANK);

			if (fieldDescriptor.equals("boolean")) {
				fieldDescriptor = "Z";
			}
			else if (fieldDescriptor.equals("byte")) {
				fieldDescriptor = "B";
			}
			else if (fieldDescriptor.equals("char")) {
				fieldDescriptor = "C";
			}
			else if (fieldDescriptor.equals("double")) {
				fieldDescriptor = "D";
			}
			else if (fieldDescriptor.equals("float")) {
				fieldDescriptor = "F";
			}
			else if (fieldDescriptor.equals("int")) {
				fieldDescriptor = "I";
			}
			else if (fieldDescriptor.equals("long")) {
				fieldDescriptor = "J";
			}
			else if (fieldDescriptor.equals("short")) {
				fieldDescriptor = "S";
			}
			else {
				fieldDescriptor = "L".concat(fieldDescriptor).concat(
					StringPool.SEMICOLON);
			}

			return dimensions.concat(fieldDescriptor);
		}

		throw new IllegalArgumentException(type.toString() + " is invalid");
	}
	
	protected Object getArgValue(
			String value, Class<?> clazz, String methodName,
			String parameter, Type parameterType)
		throws Exception {

		String typeNameOrClassDescriptor = getTypeNameOrClassDescriptor(
			parameterType);

		if (Validator.isNull(value) &&
			!typeNameOrClassDescriptor.equals("[Ljava.lang.String;")) {

			return null;
		}
		else if (typeNameOrClassDescriptor.equals("boolean") ||
				 typeNameOrClassDescriptor.equals(Boolean.class.getName())) {

			return GetterUtil.getBoolean(value);
		}
		else if (typeNameOrClassDescriptor.equals("double") ||
				 typeNameOrClassDescriptor.equals(Double.class.getName())) {

			return GetterUtil.getDouble(value);
		}
		else if (typeNameOrClassDescriptor.equals("int") ||
				 typeNameOrClassDescriptor.equals(Integer.class.getName())) {

			return GetterUtil.getInteger(value);
		}
		else if (typeNameOrClassDescriptor.equals("long") ||
				 typeNameOrClassDescriptor.equals(Long.class.getName())) {

			return GetterUtil.getLong(value);
		}
		else if (typeNameOrClassDescriptor.equals("short") ||
				 typeNameOrClassDescriptor.equals(Short.class.getName())) {

			return GetterUtil.getShort(value);
		}
		else if (typeNameOrClassDescriptor.equals(Date.class.getName())) {
			return new Date(GetterUtil.getLong(value));
		}
		else if (typeNameOrClassDescriptor.equals(String.class.getName())) {
			return value;
		}
		
		return null;
	}

	public String getFullJavaInterfaceClassName() {
		return fullJavaInterfaceClassName;
	}

	public void setFullJavaInterfaceClassName(String fullJavaInterfaceClassName) {
		this.fullJavaInterfaceClassName = fullJavaInterfaceClassName;
	}

	public String[] getParameterNames() {
		return parameterNames;
	}

	public void setParameterNames(String[] parameterNames) {
		this.parameterNames = parameterNames;
	}

	public String[] getParameterTypes() {
		return parameterTypes;
	}

	public void setParameterTypes(String[] parameterTypes) {
		this.parameterTypes = parameterTypes;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String[] getParameterValues() {
		return parameterValues;
	}

	public void setParameterValues(String[] parameterValues) {
		this.parameterValues = parameterValues;
	}
}