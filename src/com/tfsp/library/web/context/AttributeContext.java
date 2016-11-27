package com.tfsp.library.web.context;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 应用程序属性上下文, 可作为不同程序模块间数据传递功能.
 * @author Shmilycharlene
 * @version 1.0
 */
/* 
 * 文件名: com.tfsp.library.web.context.AttributeContext.java
 *
 * 修改记录
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 修改日期				修改者						备注信息
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 2013-1-7			Shmilycharlene				初始化版本	
 * 
 */
public class AttributeContext {
	public static final String APP_PATH = "com.tfsp.library.web.context.Application.Path";
	private static ConcurrentMap<String, Object> map;
	
	/**
	 * 将属性放入属性上下文中.
	 * @param attributeName
	 * @param attribute
	 * <br>
	 * 创建日期: 2013-1-7 <br>
	 * 创  建  人: Shmilycharlene
	 */
	public static <T> void put(String key, T value){
		context().put(key, value);
	}
	
	/**
	 * 通过属性名获取之前放入上下文的属性.
	 * @param attributeName
	 * @return attribute
	 * <br>
	 * 创建日期: 2013-1-7 <br>
	 * 创  建  人: Shmilycharlene
	 */
	@SuppressWarnings("unchecked")
	public static <T> T get(String key){
		return (T)context().get(key);
	}
	
	/**
	 * 从属性上下文中移除属性attributeName
	 * @param attributeName
	 * <br>
	 * 创建日期: 2013-1-7 <br>
	 * 创  建  人: Shmilycharlene
	 */
	public static void remove(String key){
		context().remove(key);
	}
	
	private static <T> ConcurrentMap<String, Object> context(){
		if(map == null)
			map = new ConcurrentHashMap<String, Object>();
		return map;
	}
	
	/**
	 * 获取当前项目绝对路径
	 * @return
	 * <br>
	 * 创建日期: 2016年6月22日 <br>
	 * 创  建  人: ALVIN
	 */
	public static String getAppPath() {
		return get(APP_PATH);
	}
}

