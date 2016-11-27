package com.tfsp.library.common.xstream;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Shmilycharlene
 * @version 1.0
 */
/* 
 * 文件名: com.sprite.ystpay.service.commons.annotation.XmlNode.java
 *
 * 修改记录
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 修改日期				修改者						备注信息
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 2016年5月19日			Shmilycharlene				初始化版本	
 * 
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface XmlNode {
	String value();
	String formatDate() default "";
}