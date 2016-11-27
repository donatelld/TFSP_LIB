package com.tfsp.library.common.excel;

import java.util.List;

import com.tfsp.library.common.logging.Logger;
import com.tfsp.library.common.logging.LoggerFactory;

/**
 * @author Shmilycharlene
 * @version 1.0
 */
/* 
 * 文件名: com.tfsp.library.common.excel.AbstractExcelModel.java
 *
 * 修改记录
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 修改日期				修改者						备注信息
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 2015年9月1日			Shmilycharlene				初始化版本	
 * 
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractExcelModel implements ExcelModel{
	protected final Logger	logger	= LoggerFactory.getLogger(getClass());
	//默认的工作表名称
	protected static final String DEFAULT_SHEET = "Sheet";
	//默认的工作表索引
	protected int sheetIndex = 0;
	
	protected String[] headers;
	//行数据转换器
	
	protected Converter converter;
	//数据列表
	protected List data;
	protected List rowData;
	
	public AbstractExcelModel() {
		createWorkbook();
	}
	protected abstract void createWorkbook();
	
	@Override
	public void setConverter(Converter converter){
		this.converter = converter;
	}
	
	protected int getColCount() {
		return headers == null ? 0 : headers.length;
	}
	
	protected String getColumnName(int col) {
		return headers == null ? "Columns" : headers[col];
	}
	
	protected Object getValueAt(int col) {
		return rowData == null ? "" : rowData.get(col);
	}
	
	public void writeSheet(String name, String[] header, List data, Converter converter) {
		setConverter(converter);
		writeSheet(name, header, data);
	}
}

