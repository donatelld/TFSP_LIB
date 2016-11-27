package com.tfsp.library.common.excel;

import java.io.IOException;
import java.util.List;


/**
 * @author Shmilycharlene
 * @version 1.0
 */
/* 
 * 文件名: com.tfsp.library.common.excel.ExcelModel.java
 *
 * 修改记录
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 修改日期				修改者						备注信息
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 2015年9月1日			Shmilycharlene				初始化版本	
 * 
 */
@SuppressWarnings("rawtypes")
public interface ExcelModel{
	public void writeSheet(String name, String[] header, List data);
	public void writeSheet(String name, String[] header, List data, Converter converter);
	public void setConverter(Converter converter);
	public String writeFile(String relativePath, String fileName) throws IOException;
}

