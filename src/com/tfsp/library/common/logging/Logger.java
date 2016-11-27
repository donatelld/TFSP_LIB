package com.tfsp.library.common.logging;

import java.text.MessageFormat;

//import com.thoughtworks.xstream.XStream;

/**
 * 支持格式化参数输出的日志类, 日志配置文件参考<code>{@link LoggerFactory}</code>中的例子
 * @author Shmilycharlene
 * @version 1.0
 */
/* 
 * 文件名: com.tfsp.library.common.logging.Logger.java
 *
 * 修改记录
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 修改日期				修改者						备注信息
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 2012-12-8			Shmilycharlene				初始化版本	
 * 
 */
public class Logger{
	private org.slf4j.Logger logger;

	protected Logger(Class<?> clazz) {
		logger = org.slf4j.LoggerFactory.getLogger(clazz);
	}
	
	public org.slf4j.Logger getLogger(){
		return logger;
	}

	/**
	 * 输出INFO级别的日志到日志文件<br>
	 * <blockquote>
	 * <p>
	 * 1: 输出日志信息, 不带任何参数 <code>logger.info("这是一条不带参数的日志信息");</code>
	 * </p>
	 * <p>
	 * 2: 输入日志信息, 带参数 <code>logger.info("这是一条带参数的日志信息, 参数1: {0}, 参数2: {1}", params1, params2);</code> </blockquote>
	 * 
	 * @param pattern 日志输出模式
	 * @param params 日志输出时, 所带的参数列表, 如果有参数
	 */
	public void info(String pattern, Object... params) {
		if (params == null || params.length == 0)
			logger.info(pattern);
		else {
//			logger.info(pattern, params);
			logger.info(MessageFormat.format(pattern, parseParameters(params)));
		}
	}

	/**
	 * 输出DEBUG级别的日志到日志文件<br>
	 * <blockquote>
	 * <p>
	 * 1: 输出日志信息, 不带任何参数 <code>logger.debug("这是一条不带参数的日志信息");</code>
	 * </p>
	 * <p>
	 * 2: 输入日志信息, 带参数 <code>logger.debug("这是一条带参数的日志信息, 参数1: {0}, 参数2: {1}", params1, params2);</code> </blockquote>
	 * 
	 * @param pattern 日志输出模式
	 * @param params 日志输出时, 所带的参数列表, 如果有参数
	 */
	public void debug(String pattern, Object... params) {
		if (logger.isDebugEnabled()) {
			if (params == null || params.length == 0)
				logger.debug(pattern);
			else {
				logger.debug(MessageFormat.format(pattern, parseParameters(params)));
			}
		}
	}

	/**
	 * 输出WARN级别的日志到日志文件<br>
	 * <blockquote>
	 * <p>
	 * 1: 输出日志信息, 不带任何参数 <code>logger.warn("这是一条不带参数的日志信息");</code>
	 * </p>
	 * <p>
	 * 2: 输入日志信息, 带参数 <code>logger.warn("这是一条带参数的日志信息, 参数1: {0}, 参数2: {1}", params1, params2);</code> </blockquote>
	 * </p>
	 * 
	 * @param pattern 日志输出模式
	 * @param params 日志输出时, 所带的参数列表, 如果有参数
	 */
	public void warn(String pattern, Object... params) {
		if (logger.isWarnEnabled()) {
			if (params == null || params.length == 0)
				logger.warn(pattern);
			else {
				logger.warn(MessageFormat.format(pattern, parseParameters(params)));
			}
		}
	}

	/**
	 * 输出ERROR级别的日志到日志文件<br>
	 * <blockquote>
	 * <p>
	 * 1: 输出日志信息, 不带任何参数 <code>logger.error("这是一条不带参数的日志信息");</code>
	 * </p>
	 * <p>
	 * 2: 输入日志信息, 带参数 <code>logger.error("这是一条带参数的日志信息, 参数1: {0}, 参数2: {1}", params1, params2);</code> </blockquote>
	 * </p>
	 * 
	 * @param pattern 日志输出模式
	 * @param params 日志输出时, 所带的参数列表, 如果有参数
	 */
	public void error(String pattern, Object... params) {
		if (logger.isErrorEnabled()) {
			if (params == null || params.length == 0)
				logger.error(pattern);
			else {
				logger.error(MessageFormat.format(pattern, parseParameters(params)));
			}
		}
	}

	/**
	 * 输出INFO级别的日志到日志文件, 如果带有参数, 参数以XML格式输出<br>
	 * <blockquote>
	 * <p>
	 * 1: 输出日志信息, 不带任何参数 <code>logger.infoAsXML("这是一条不带参数的日志信息");</code>
	 * </p>
	 * <p>
	 * 2: 输入日志信息, 带参数 <code>logger.infoAsXML("这是一条带参数的日志信息, 参数1: {0}, 参数2: {1}", params1, params2);</code> </blockquote>
	 * </p>
	 * 
	 * @param pattern 日志输出模式
	 * @param params 日志输出时, 所带的参数列表, 如果有参数
	 */
	public void infoAsXML(String pattern, Object... params) {
		if (params == null || params.length == 0)
			logger.info(pattern);
		else {
			logger.info(MessageFormat.format(pattern, parseParameterAsXML(params)));
		}
	}

	/**
	 * 输出DEBUG级别的日志到日志文件, 如果带有参数, 参数以XML格式输出<br>
	 * <blockquote>
	 * <p>
	 * 1: 输出日志信息, 不带任何参数 <code>logger.debugAsXML("这是一条不带参数的日志信息");</code>
	 * </p>
	 * <p>
	 * 2: 输入日志信息, 带参数 <code>logger.debugAsXML("这是一条带参数的日志信息, 参数1: {0}, 参数2: {1}", params1, params2);</code> </blockquote>
	 * </p>
	 * 
	 * @param pattern 日志输出模式
	 * @param params 日志输出时, 所带的参数列表, 如果有参数
	 */
	public void debugAsXML(String pattern, Object... params) {
		if (logger.isDebugEnabled()) {
			if (params == null || params.length == 0)
				logger.debug(pattern);
			else {
				logger.debug(MessageFormat.format(pattern, parseParameterAsXML(params)));
			}
		}
	}

	/**
	 * 输出WARN级别的日志到日志文件, 如果带有参数, 参数以XML格式输出<br>
	 * <blockquote>
	 * <p>
	 * 1: 输出日志信息, 不带任何参数 <code>logger.warnAsXML("这是一条不带参数的日志信息");</code>
	 * </p>
	 * <p>
	 * 2: 输入日志信息, 带参数 <code>logger.warnAsXML("这是一条带参数的日志信息, 参数1: {0}, 参数2: {1}", params1, params2);</code> </blockquote>
	 * </p>
	 * 
	 * @param pattern 日志输出模式
	 * @param params 日志输出时, 所带的参数列表, 如果有参数
	 */
	public void warnAsXML(String pattern, Object... params) {
		if (logger.isWarnEnabled()) {
			if (params == null || params.length == 0)
				logger.warn(pattern);
			else {
				logger.warn(MessageFormat.format(pattern, parseParameterAsXML(params)));
			}
		}
	}

	/**
	 * 输出ERROR级别的日志到日志文件, 如果带有参数, 参数以XML格式输出<br>
	 * <blockquote>
	 * <p>
	 * 1: 输出日志信息, 不带任何参数 <code>logger.errorAsXML("这是一条不带参数的日志信息");</code>
	 * </p>
	 * <p>
	 * 2: 输入日志信息, 带参数 <code>logger.errorAsXML("这是一条带参数的日志信息, 参数1: {0}, 参数2: {1}", params1, params2);</code> </blockquote>
	 * </p>
	 * 
	 * @param pattern 日志输出模式
	 * @param params 日志输出时, 所带的参数列表, 如果有参数
	 */
	public void errorAsXML(String pattern, Object... params) {
		if (logger.isErrorEnabled()) {
			if (params == null || params.length == 0)
				logger.error(pattern);
			else {
				logger.error(MessageFormat.format(pattern, parseParameterAsXML(params)));
			}
		}
	}

	private Object[] parseParameters(Object... objects) {
		Object[] args = new Object[objects.length];
		int index = 0;
		for (Object object : objects) {
			if (object == null) {
				args[index] = "null";
			}  else if (object instanceof Throwable) {
				args[index] = parseException((Throwable)object).toString();
			} else {
				args[index] = object;
			}
			index++;
		}
		return args;
	}

	private StringBuilder parseException(Throwable tr) {
		StringBuilder sb = new StringBuilder(tr.toString()).append("\n");
		StackTraceElement[] stacks = tr.getStackTrace();
		for (StackTraceElement stack : stacks) {
			sb.append("\tat " + stack.toString() + "\n");
		}
		Throwable cause = tr.getCause();
		if(cause != null){
			sb.append(printCauseException(cause, stacks));
		}
		return sb;
	}

	private StringBuilder printCauseException(Throwable cause, StackTraceElement[] stacks){
		StringBuilder buffer = new StringBuilder();
		StackTraceElement[] trace = cause.getStackTrace();
		int m = trace.length-1, n = stacks.length-1;
        while (m >= 0 && n >=0 && trace[m].equals(stacks[n])) {
            m--; n--;
        }
        int framesInCommon = trace.length - 1 - m;
        buffer.append("Caused by: " + cause.toString()).append("\n");
        for (int i=0; i <= m; i++){
        	buffer.append("\tat " + trace[i]).append("\n");
        }
        if (framesInCommon != 0){
        	buffer.append("\t... " + framesInCommon + " more");
        }
        Throwable ourCause = cause.getCause();
        if (ourCause != null){
        	buffer.append(printCauseException(ourCause, trace));
        }
		return buffer;
	}
	
	private Object[] parseParameterAsXML(Object... objects) {
//		XStream xstream = new XStream();
		Object[] args = new Object[objects.length];
		int index = 0;
		for (Object object : objects) {
			if (object == null) {
				args[index] = "<node>null</node>";
			}
			else {
				args[index] = object.toString();//xstream.toXML(object);
			}
			index++;
		}
		return args;
	}
}
