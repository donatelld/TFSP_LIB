package com.tfsp.library.common.utils;

import java.nio.charset.Charset;


/**
 * @author Shmilycharlene
 * @version 1.0
 */
/* 
 * 文件名: com.tfsp.library.common.utils.CharSets.java
 *
 * 修改记录
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 修改日期				修改者						备注信息
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 2016年5月27日			Shmilycharlene				初始化版本	
 * 
 */
public interface CharSets{
	public Charset UTF8 = Charset.forName("UTF-8");
	public Charset GB2312 = Charset.forName("GB2312");
	public Charset GBK = Charset.forName("GBK");
	public Charset ISO8859_1 = Charset.forName("ISO8859-1");
}

