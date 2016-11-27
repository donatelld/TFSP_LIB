package com.tfsp.library.common.excel;

import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.tfsp.library.common.logging.Logger;
import com.tfsp.library.common.logging.LoggerFactory;
import com.tfsp.library.common.utils.StringUtils;

/**
 * @author Shmilycharlene
 * @version 1.0
 */
/* 
 * 文件名: com.tfsp.library.common.excel.POIExcelModel.java
 *
 * 修改记录
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 修改日期				修改者						备注信息
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 2015年9月1日			Shmilycharlene				初始化版本	
 * 
 */
@SuppressWarnings("rawtypes")
public class POIExcelModel extends AbstractExcelModel{
	protected final Logger	logger	= LoggerFactory.getLogger(getClass());

	protected Workbook workbook;
	
	/* (non-Javadoc)
	 * @see com.tfsp.library.common.excel.ExcelModel#writeSheet(java.lang.String, java.lang.String[], java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void writeSheet(String name, String[] header, List data){
		this.headers = header;
		this.data = data;
		if(StringUtils.isBlank(name))
			name = DEFAULT_SHEET + sheetIndex;
		Sheet sheet = workbook.createSheet(name);
		
		CellStyle titleStyle = Excel.Style.getTitleStyle(workbook);
		CellStyle contentStyle = Excel.Style.getContentStyle(workbook);
		
		Row titleName = sheet.createRow(0);
		for (int i = 0; i < getColCount(); i++) {
			Excel.Set.cellValue(titleName.createCell(i), getColumnName(i), titleStyle);
		}
		int lastRowNum = sheet.getLastRowNum();
		while(data != null && data.size() > 0) {
			rowData = converter.convert(data.remove(0));
			Row row = sheet.createRow( ++lastRowNum );//留出标题栏
			for(int j = 0; j < getColCount(); j++) {
				Object value = getValueAt(j);
				Excel.Set.cellValue(row.createCell(j), value, contentStyle);
			}
		}
		Excel.Set.autoSizeColumn(sheet);
		sheetIndex++;
	}

	/**
	 * 根据指定的名称创建工作表
	 * @param name
	 * @return
	 * <br>
	 * 创建日期: 2015年9月1日 <br>
	 * 创  建  人: Shmilycharlene
	 */
	public Sheet createSheet(String name) {
		return workbook.createSheet(name);
	}
	
	/* (non-Javadoc)
	 * @see com.tfsp.library.common.excel.ExcelModel#writeFile(java.lang.String)
	 */
	@Override
	public String writeFile(String relative, String fileName) throws IOException{
        return Excel.Work.writeExcel(workbook, relative, fileName);
	}

	/* (non-Javadoc)
	 * @see com.tfsp.library.common.excel.AbstractExcelModel#createWorkbook()
	 */
	@Override
	protected void createWorkbook(){
		workbook = Excel.Work.getNewWorkbook();
	}
}

