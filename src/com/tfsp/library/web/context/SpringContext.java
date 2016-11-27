package com.tfsp.library.web.context;

import java.io.IOException;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.tfsp.library.common.logging.Logger;
import com.tfsp.library.common.logging.LoggerFactory;

/**
 * 在web容器启动时, 注入Spring容器上下文. 以便程序获取Spring容器中的bean.<br>
 * 要使该类生效. 需要在Spring MVC的dispatcher配置文件中, 定义一个该类的bean
 * @author Shmilycharlene
 * @version 1.0
 */
/* 
 * 文件名: com.tfsp.library.web.context.SpringContext.java
 *
 * 修改记录
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 修改日期				修改者						备注信息
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 2012-12-8			Shmilycharlene				初始化版本	
 * 
 */
@Component
public class SpringContext implements ApplicationContextAware {
	protected static final Logger logger = LoggerFactory.getLogger(SpringContext.class);
	private static ApplicationContext applicationContext;
	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		applicationContext = context;
		try{
			Resource resource = context.getResource("/");
			AttributeContext.put(AttributeContext.APP_PATH, resource.getFile().getAbsolutePath());
		}catch (IOException e){
			logger.error("初始化项目路径Exception: {0}", e);
		}
		logger.info("通过Web容器初始化Spring上下文完成");
	}
	
	/**
	 * 获取Spring容器的上下文
	 * @return applicationContext
	 * <br>
	 * 创建日期: 2012-12-8 <br>
	 * 创  建  人: Shmilycharlene
	 */
	public static ApplicationContext getSpringContext(){
		return applicationContext;  
	}
	
	/**
	 * 通过bean id获取Spring容器中的bean定义.
	 * @param beanName
	 * @return T
	 * <br>
	 * 创建日期: 2012-12-8 <br>
	 * 创  建  人: Shmilycharlene
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName){
		return (T) getSpringContext().getBean(beanName);
	}
	
	/**
	 * 通过bean id和限定的类型, 获取Spring容器中的bean定义
	 * @param beanName
	 * @param classType
	 * @return T
	 * <br>
	 * 创建日期: 2012-12-8 <br>
	 * 创  建  人: Shmilycharlene
	 */
	public static <T> T getBean(String beanName, Class<T> classType){
		return getSpringContext().getBean(beanName, classType);
	}
	
	/**
	 * 根据给定的class获取spring配置的bean实例. 该类在Spring容器中只为一定义一个bean
	 * @param classType
	 * @return
	 * <br>
	 * 创建日期: 2012-12-8 <br>
	 * 创  建  人: Shmilycharlene
	 */
	public static <T> T getBean(Class<T> classType){
		return getSpringContext().getBean(classType);
	}
}

