package com.tfsp.library.common.utils;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.tfsp.library.common.cache.vm.CacheManager;

public class ReflectUtils {
	private static final String FIELDS_POOL = "com.tfsp.library.common.utls.Reflect.CacheFieldsPool";
	private static final String FIELD_POOL  = "com.tfsp.library.common.utls.Reflect.CacheFieldPool";
	
	/**
	 * 获取给定的class下所有的字段列表, 包含所有继承而来的字段, 以及private修饰符修饰的字段.
	 * @param clazz
	 * @return
	 * <br>
	 * 创建日期: 2016年6月2日 <br>
	 * 创  建  人: Shmilycharlene
	 */
	public static List<Field> getDeclardFields(Class<?> clazz){
		return getDeclardFields(clazz, Object.class);
	}
	
	/**
	 * 获取给定的class下所有的字段列表, 包含所有继承而来的字段, 以及private修饰符修饰的字段.
	 * 在追溯继承字段时, 追溯到指定的stopClass后停止, 不在继续追溯父类字段
	 * @param clazz
	 * @param stopClass
	 * @return
	 * <br>
	 * 创建日期: 2016年6月2日 <br>
	 * 创  建  人: Shmilycharlene
	 */
	public static List<Field> getDeclardFields(Class<?> clazz, Class<?> stopClass){
		List<Field> list = CacheManager.getInstance().getCache(FIELDS_POOL).get(clazz.getName());
		if(list != null) //先检查缓存情况, 如果有缓存则直接使用缓存数据
			return list;
		list = new ArrayList<Field>();
		list = getDeclardFields(clazz, stopClass, list);
		CacheManager.getInstance().getCache(FIELDS_POOL).put(clazz.getName(), list);//放入缓存
		return list;
		
	}
	
	private static List<Field> getDeclardFields(Class<?> clazz, Class<?> rootClazz, List<Field> list){
		if(list == null) list = new ArrayList<Field>();
		if(clazz == Object.class)
			return list;
		Field[] declaredFields = clazz.getDeclaredFields();
		for(Field field : declaredFields){
			if(field.getAnnotation(ReflectIgnore.class) == null){
				field.setAccessible(true);
				list.add(field);
			}
		}
		if(clazz == rootClazz)//如果类继承层次过深, 达到指定层数数后, 不在取父类的字段了.
			return list;
		return getDeclardFields(clazz.getSuperclass(), rootClazz, list);
	}
	
	/**
	 * 获取给定class下指定名称fieldName的字段, 自动追溯父类, 如果没有指定名称的字段, 则返回null;
	 * @param clazz
	 * @param fieldName
	 * @return
	 * <br>
	 * 创建日期: 2016年6月2日 <br>
	 * 创  建  人: Shmilycharlene
	 */
	public static Field getDeclardField(Class<?> clazz, String fieldName) {
		Field field = CacheManager.getInstance().getCache(FIELD_POOL).get(clazz.getName()+fieldName);
		if(field != null)
			return field;
		field = getField(clazz, fieldName);
		if(field != null)
			CacheManager.getInstance().getCache(FIELD_POOL).put(clazz.getName()+fieldName, field);
		return field;
	}
	
	private static Field getField(Class<?> clazz, String fieldName) {
		Field field = null;
		if(clazz == Object.class)
			return field;
		try{
			field = clazz.getDeclaredField(fieldName);
		}catch (Exception e){
		}
		if(field != null)
			return field;
		return getField(clazz.getSuperclass(), fieldName);
	}
	
	/**
	 * 指示在进行字段反射时, 忽略掉的字段或者方法
	 * @author Shmilycharlene
	 * @version 1.0
	 */
	@Target({ElementType.FIELD,ElementType.METHOD})
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface ReflectIgnore{
	}
}
