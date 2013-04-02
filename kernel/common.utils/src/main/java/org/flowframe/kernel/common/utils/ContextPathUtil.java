package org.flowframe.kernel.common.utils;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class ContextPathUtil {

	public static String getContextPath(HttpServletRequest request) {
		return getContextPath(request.getContextPath());
	}

	public static String getContextPath(ServletContext servletContext) {
		String contextPath = null;

		if (ServletVersionDetector.is2_5()) {
			contextPath = servletContext.getContextPath();
		}
		else {
			//contextPath = (String)servletContext.getAttribute(WebKeys.CTX_PATH);

			if (contextPath == null) {
				contextPath = servletContext.getServletContextName();
			}
		}

		return getContextPath(contextPath);
	}

	public static String getContextPath(String contextPath) {
		contextPath = GetterUtil.getString(contextPath);

		if ((contextPath.length() == 0) ||
			contextPath.equals(StringPool.SLASH)) {

			contextPath = StringPool.BLANK;
		}
		else if (!contextPath.startsWith(StringPool.SLASH)) {
			contextPath = StringPool.SLASH.concat(contextPath);
		}

		return contextPath;
	}

}