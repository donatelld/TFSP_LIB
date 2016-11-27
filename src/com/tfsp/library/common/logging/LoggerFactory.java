package com.tfsp.library.common.logging;

/**
 * 创建日志输出类Logger实例的工厂. 调用<code>getLogger()</code>将会以Logger类为标签的日志输出实例
 * <p>
 * 将下面的配置信息复制到log4j.properties文件中, 并吧log4j.properties文件放到src目录下. <br>
 * #日志输出控制配置文件内容 <br>
 * log4j.rootCategory=DEBUG, LOGFILE<br>
 * #日志控制台输出配置<br>
 * log4j.appender.stdout=org.apache.log4j.ConsoleAppender<br>
 * log4j.appender.stdout.threshold=debug<br>
 * log4j.appender.stdout.target=System.out<br>
 * log4j.appender.stdout.layout=org.apache.log4j.PatternLayout<br>
 * <br>
 * #输出到日志文件配置<br>
 * log4j.appender.LOGFILE=org.apache.log4j.RollingFileAppender<br>
 * log4j.appender.LOGFILE.File=log/common.log<br>
 * log4j.appender.LOGFILE.MaxFileSize=51200KB<br>
 * log4j.appender.LOGFILE.MaxBackupIndex=150<br>
 * log4j.appender.LOGFILE.layout=org.apache.log4j.PatternLayout<br>
 * log4j.appender.LOGFILE.layout.ConversionPattern=%d{ISO8601} [%t][%-5p][%c] - %m%n<br>
 * <br>
 * #过滤第三方包日志输出配置<br>
 * log4j.logger.org.springframework=ERROR<br>
 * </p>
 * 
 * @author Shmilycharlene
 * @version 1.0
 */
/* 
 * 文件名: com.tfsp.library.common.logging.LoggerFactory.java
 *
 * 修改记录
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 修改日期				修改者						备注信息
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 2012-12-8			Shmilycharlene				初始化版本	
 * 
 */
public class LoggerFactory {
	
	/**
	 * 创建默认使用Logger类路径作为日志标签的Logger实例
	 * @return Logger实例
	 */
	public static Logger getLogger(){
		return new Logger(Logger.class);
	}
	
	/**
	 * 创建使用指定的类路径作为日志输出标签的Logger实例
	 * @param clazz
	 * @return Logger实例
	 */
	public static Logger getLogger(Class<?> clazz){
		return new Logger(clazz);
	}
}

