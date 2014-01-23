package com.andersonlfeitosa.mavendependencyanalyzer.util;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.andersonlfeitosa.mavendependencyanalyzer.log.Log;

public class GAVFormatter {

	private static final String GAV_SEPARATOR = ":";
	
	private static final Log logger = Log.getInstance();

	public static String gavToString(Object project) {
		StringBuilder sb = new StringBuilder();
		
		if (project != null) {
			try {
				sb.append(BeanUtils.getProperty(project, "groupId"));
				sb.append(GAV_SEPARATOR);
				sb.append(BeanUtils.getProperty(project, "artifactId"));
				sb.append(GAV_SEPARATOR);
				sb.append(BeanUtils.getProperty(project, "version"));
			} catch (IllegalAccessException e) {
				logger.error(e.getMessage(), e);
			} catch (InvocationTargetException e) {
				logger.error(e.getMessage(), e);
			} catch (NoSuchMethodException e) {
				logger.error(e.getMessage(), e);
			}
		}
		
		return sb.toString();
	}

}
