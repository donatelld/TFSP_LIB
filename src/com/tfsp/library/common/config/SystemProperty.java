/**
 * 
 */
package com.tfsp.library.common.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.tfsp.library.common.logging.Logger;
import com.tfsp.library.common.logging.LoggerFactory;
import com.tfsp.library.common.utils.StringUtils;

/**
 * 引领系统加载properties配置文件
 * <br>
 * 在不特殊指明的情况下, 系统默认采用类路径加载方式, 加载类路径下的system.properties文件<br>
 * 其它的配置文件路径都配置在system.properties文件中. 以SYSTEM.PROPERTY.LOCATION作为配置系统配置文件的KEY.
 * <p>基础配置文件如下:</p>
 * # 配置系统配置文件的路径. 多个文件之间使用 ","或者 ";"隔开<br>
 * SYSTEM.PROPERTY.LOCATION=jdbc_environment.properties, default.properties
 * 
 * @author Shmilycharlene
 * @version 1.0
 */
/* 
 * com.tfsp.library.common.config.SystemProperty.java
 *
 * MODIFICATION HISTORY
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 修改日期				修改者						备注信息
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 2012-12-8			Shmilycharlene				初始化版本	
 * 
 */
public abstract class SystemProperty {
	final static Lock lock = new ReentrantLock();

	protected static final Logger logger = LoggerFactory.getLogger(SystemProperty.class);
	/**定义系统默认的配置文件名称和路径*/
	private static final String DEFAULT_SYSTEM_CONFIG_LOCATION = "conf/system.properties";
	private static Properties properties = new Properties();
	
	static {
		reset();
	}
	
	private static Properties loadProperties(String resource){
		logger.debug("正在加载配置文件:{0}", resource);
		Properties properties = System.getProperties();
		InputStream is = null;
		try{
			is = SystemProperty.class.getClassLoader().getResourceAsStream(resource);
			properties.load(is);
		}catch (IOException ex){
			logger.error("加载配置文件: {0} 失败. Caused by: {1}", resource, ex);
		}finally{
			if(is != null){
				try{
					is.close();
				}catch (IOException e){
					logger.error("Exception: {0}", e);
				}
			}
			is = null;
		}
		logger.debug("加载配置文件:{0} 完成", resource);
		return properties;
	}
	
	public static void reset(){
		properties = loadProperties(DEFAULT_SYSTEM_CONFIG_LOCATION);
	}
	
	/**
	 * 通过key获取系统配置项
	 * @param key
	 * @return value
	 * <br>
	 * 创建日期: 2012-12-8 <br>
	 * 创  建  人: Shmilycharlene
	 */
	public static String getProperty(String key){
		if(properties.size() == 0){
			lock.lock();
			try{
				reset();
			} finally {
				lock.unlock();
			}
		}
		return properties.getProperty(key);
	}

	/**
	 * 检查系统是否处于开发调试状态
	 * @return
	 * <br>
	 * 创建日期: 2016年5月27日 <br>
	 * 创  建  人: Shmilycharlene
	 */
	public static boolean isDevelopMode() {
		return StringUtils.equalsIgnoreCase(getProperty("DEVELOP_MODE"), "true");
	}
}
