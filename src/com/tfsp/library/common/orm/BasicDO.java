package com.tfsp.library.common.orm;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.tfsp.library.common.utils.ReflectUtils.ReflectIgnore;
import com.tfsp.library.common.xstream.XStream;

/**
 * 所有实体对象的父类, 以支持所有的实体对象克隆和序列化.
 * 
 * @author Shmilycharlene
 * @version 1.0
 */
/* 
 * 文件名: com.tfsp.library.common.orm.BasicDO.java
 *
 * 修改记录
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 修改日期				修改者						备注信息
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 2012-12-23			Shmilycharlene				初始化版本	
 * 
 */
public class BasicDO implements Serializable{
	@ReflectIgnore
	private static final long	serialVersionUID	= 1L;
	
	public String toString(){
		return toXml();
	}
	
	public String toXml(){
		return XStream.toXML(this);
	}
	
	public String toJson(){
		return JSON.toJSONString(this);
	}
}

