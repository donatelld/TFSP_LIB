package com.tfsp.library.common.excel;

import java.util.List;

/**
 * @author Shmilycharlene
 * @version 1.0
 */
/* 
 * 文件名: com.tfsp.library.common.excel.Converter.java
 *
 * 修改记录
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 修改日期				修改者						备注信息
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 2015年9月1日			Shmilycharlene				初始化版本	
 * 
 */
public interface Converter<T>{
	public List<Object> convert(T row);
}

