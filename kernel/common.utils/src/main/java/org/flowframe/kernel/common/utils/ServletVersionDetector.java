package org.flowframe.kernel.common.utils;

import javax.servlet.ServletContext;

public class ServletVersionDetector {

	public static boolean is2_5() {
		if (_2_5 != null) {
			return _2_5.booleanValue();
		}

		try {
			ServletContext.class.getMethod("getContextPath");

			_2_5 = Boolean.TRUE;
		}
		catch (Exception e) {
			_2_5 = Boolean.FALSE;
		}

		return _2_5.booleanValue();
	}

	private static Boolean _2_5;

}