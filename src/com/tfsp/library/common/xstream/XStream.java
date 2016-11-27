package com.tfsp.library.common.xstream;

import java.lang.reflect.Field;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.tfsp.library.common.logging.Logger;
import com.tfsp.library.common.logging.LoggerFactory;
import com.tfsp.library.common.orm.BasicDO;
import com.tfsp.library.common.utils.DateUtils;
import com.tfsp.library.common.utils.ReflectUtils;
import com.tfsp.library.common.utils.ReflectUtils.ReflectIgnore;
import com.tfsp.library.common.utils.StringUtils;
import com.tfsp.library.common.utils.XMLParser;

/**
 * Java对象与XML文档相互转换类, 兼容JAXB的注解(@XmlElement), 以便进行字段与XML节点映射时的别名实现.
 * XStream仅支持Java对象中的字段, 如果字段未添加注解, 则使用字段名称作为映射关系.
 * 
 * @author Shmilycharlene
 * @version 1.0
 */
/* 
 * 文件名: com.tfsp.library.common.xstream.XStream.java
 *
 * 修改记录
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 修改日期				修改者						备注信息
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 2016年6月12日			Shmilycharlene				初始化版本	
 * 
 */
public class XStream{
	private static final Logger	logger	= LoggerFactory.getLogger(XStream.class);
	
	/**
	 * 将Java对象转XML文档, 忽略添加了ReflectIgnore注解的字段
	 * @param object
	 * @return
	 * <br>
	 * 创建日期: 2016年6月13日 <br>
	 * 创  建  人: Shmilycharlene
	 */
	public static String toXML(Object object) {
		XmlRootElement rootElement = object.getClass().getAnnotation(XmlRootElement.class);
		StringBuilder builder = new StringBuilder();
		if(rootElement == null){
			if(!object.getClass().isMemberClass()){
				builder.append("<").append(object.getClass().getSimpleName()).append(">");
			}
		}else{
			builder.append("<").append(rootElement.name()).append(">");
		}
		try{
			List<Field> list = ReflectUtils.getDeclardFields(object.getClass(), BasicDO.class);
			for(Field field: list) {
				ReflectIgnore ignore = field.getAnnotation(ReflectIgnore.class);
				if(ignore != null)
					continue;
				field.setAccessible(true);
				XmlElement element = field.getAnnotation(XmlElement.class);
				if(element == null)
					builder.append("<").append(field.getName()).append(">");
				else
					builder.append("<").append(element.name()).append(">");
				Object value = field.get(object);
				
				builder.append(value == null? "" : value.toString());
				if(element == null)
					builder.append("</").append(field.getName()).append(">");
				else
					builder.append("</").append(element.name()).append(">");
			}
		}catch (Exception e){
			logger.error("{0}转XML文档失败, Exception:{1}", object.getClass().getName(), e);
		}
		if(!object.getClass().isMemberClass()){
			builder.append("</").append(object.getClass().getSimpleName()).append(">");
		}
		return builder.toString();
	}

	/**
	 * 将XML文档转为指定的Java对象
	 * @param xml
	 * @param clazz
	 * @return
	 * @throws XStreamException 
	 * <br>
	 * 创建日期: 2016年6月13日 <br>
	 * 创  建  人: Shmilycharlene
	 */
	public static <T> T fromXML(String xml, Class<T> clazz) throws XStreamException {
			try{
				T t = clazz.newInstance();
				List<Field> list = ReflectUtils.getDeclardFields(clazz);
				for(Field field : list) {
					field.setAccessible(true);
					XmlNode node = field.getAnnotation(XmlNode.class);
					String value = XMLParser.getXMLNode(node != null ? node.value() : field.getName(), xml);
					if(StringUtils.isNotBlank(value)){
						switch(field.getType().getSimpleName()){
							case "String":
								field.set(t, value);
								break;
							case "Integer": case "int":
								field.set(t, new Integer(value));
								break;
							case "Double": case "double":
								field.set(t, new Double(value));
								break;
							case "Date":
								if(node != null && StringUtils.isNotBlank(node.formatDate())){
									field.set(t, DateUtils.Date.stringDate(value, node.formatDate()));
								}
								break;
						}
					}
				}
				return t;
			}catch (Exception e){
				logger.error("转换失败: {0}", e);
				throw new XStreamException(e);
			}
	}

}

