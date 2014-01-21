package com.andersonlfeitosa.mavendependencyanalyzer.log;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class Log {
	
	private static final Log instance = new Log();
	
	private Logger logger;
	
	private Log() {
		initLogger();
	}
	
	private void initLogger() {
		BasicConfigurator.configure();
		logger = Logger.getLogger("com.andersonlfeitosa.mavendependencyanalyzer");
	}

	public static Log getInstance() {
		return instance;
	}

	public void trace(Object message) {
		logger.trace(message);
	}

	public void trace(Object message, Throwable t) {
		logger.trace(message, t);
	}

	public void debug(Object message) {
		logger.debug(message);
	}

	public void debug(Object message, Throwable t) {
		logger.debug(message, t);
	}

	public void error(Object message) {
		logger.error(message);
	}

	public void error(Object message, Throwable t) {
		logger.error(message, t);
	}

	public void fatal(Object message) {
		logger.fatal(message);
	}

	public void fatal(Object message, Throwable t) {
		logger.fatal(message, t);
	}

	public void info(Object message) {
		logger.info(message);
	}

	public void info(Object message, Throwable t) {
		logger.info(message, t);
	}

	public void warn(Object message) {
		logger.warn(message);
	}

	public void warn(Object message, Throwable t) {
		logger.warn(message, t);
	}
	
}
