package com.omdp.webapp.base.common.util;

import org.apache.commons.lang.StringUtils;


public class BaseStringUtils {

	public static String ParamTrimToNull(String s){
		return StringUtils.trimToNull(StringUtils.trimToEmpty(s).replaceAll("'", "''"));
	}
}
