package com.tfsp.library.common.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.tfsp.library.common.utils.DateUtils;
import com.tfsp.library.common.utils.StringUtils;
import com.tfsp.library.web.context.AttributeContext;

public class Excel{
	/**
	 * Style
	 */
    public static class Style{
    	public static CellStyle getTitleStyle(Workbook workbook) {
            Font font = workbook.createFont();
            font.setBoldweight(Font.BOLDWEIGHT_BOLD);
            font.setFontHeight((short) (11 * 20));
            CellStyle style = workbook.createCellStyle();
            style.setAlignment(CellStyle.ALIGN_CENTER);
            style.setFont(font);
            style.setWrapText(true);
            return style;
        }
    	
        public static CellStyle getContentStyle(Workbook workbook) {
            Font font = workbook.createFont();
            font.setFontHeight((short) (10 * 20));
            CellStyle style = workbook.createCellStyle();
            style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            style.setFont(font);
            return style;
        }
        
        public static CellStyle getErrorStyle(Workbook workbook) {
        	Font font = workbook.createFont();
        	font.setColor(Font.COLOR_RED);
        	CellStyle style = workbook.createCellStyle();
			style.setFont(font);
        	return style;
        }
    }
    /**
     * Work
     */
    public static class Work{
    	public static Workbook getWorkbook(File file) throws IOException, InvalidFormatException{
        	if (file == null || !file.exists()) {  return null; }
            return WorkbookFactory.create(file);
        }
        
        public static Workbook getWorkbook(InputStream inputStream) throws IOException, InvalidFormatException{
        	return WorkbookFactory.create(inputStream);
        }
        
        public static Workbook getWorkbook(String fileName) throws IOException, InvalidFormatException{
        	File excelFile = new File(AttributeContext.getAppPath(), fileName);
        	return getWorkbook(excelFile);
        }
        
        public static Workbook getNewWorkbook(){
        	return getNewWorkbook03();
        }
        
        public static Workbook getNewWorkbook07(){
			return new XSSFWorkbook();
        }
        
        public static Workbook getNewWorkbookOnlyWrite07(){
        	return new SXSSFWorkbook();
        }
        
        public static Workbook getNewWorkbook03(){
			return new HSSFWorkbook();
        }
        
        public static void writeExcel(Workbook workbook, File outoutFile) throws IOException  {
            File path = outoutFile.getParentFile();
            if (!path.exists()) {  path.mkdirs(); }
            if (!outoutFile.exists()) {  outoutFile.createNewFile(); }
            FileOutputStream fos  = new FileOutputStream(outoutFile);
            workbook.write(fos);
            fos.flush();
            fos.close();
        }
        
        public static String writeExcel(Workbook workbook, String dir, String fileName) throws IOException {
        	String filePath = dir + fileName + ((workbook instanceof XSSFWorkbook || workbook instanceof SXSSFWorkbook) ? ".xlsx"  :  ".xls" );
        	writeExcel(workbook, new File(AttributeContext.getAppPath(), filePath));
        	return filePath;
        }
        
        public static String writeExcel(Workbook workbook, String fileName) throws Exception{
            return writeExcel(workbook, "/file/export/", fileName);
        }
    }
    
    /**
     * Get
     */
    public static class Get{
    	public static String[] title(Sheet sheet, int titleIndex) {
        	Row row = sheet.getRow(titleIndex);
        	if(row == null){ return new String[]{}; }
        	int total = row.getLastCellNum();
        	String[] title = new String[total];
        	for(int i=0;i<total;i++){
        		Cell cell = row.getCell(i);
        		if(cell == null){ title[i] = "";}
        		else{
        			title[i] = cell.getStringCellValue();
        		}
        	}
        	return title;
        }
        
        public static String[] title(Sheet sheet) {
        	return title(sheet, 0);
        }
    	
        
        public static Integer integer(Row row, int column) {
        	String val = string(row, column, null);
        	try{ return StringUtils.isNotBlank(val) ? new Integer(val) : null;}
        	catch (NumberFormatException e){ return null; }
        }
        
        public static Integer doublev(Row row, int column) {
        	String val = string(row, column, null);
        	try{ return StringUtils.isNotBlank(val) ? new Integer(val) : null; }
        	catch (NumberFormatException e){ return null; }
        }
        
        public static Date date(Row row, int column, String format) {
        	String val = string(row, column, null);
        	try{ return StringUtils.isNotBlank(val) ? DateUtils.Date.stringDate(val, format) : null; }
        	catch (Exception e){ return null; }
        }
        
        public static String string(Row row, int column) {
        	return string(row, column, null);
        }
        
        public static String string(Row row, int column, FormulaEvaluator evaluator) {
        	return string(row.getCell(column), evaluator);
        }
        
        public static String string(Cell cell) {
        	return string(cell, null);
        }
        
        public static String string(Cell cell, FormulaEvaluator evaluator) { //evaluator = workbook.getCreationHelper().createFormulaEvaluator();    
    		if(cell == null) return null; 
    	    switch (cell.getCellType()) {
    			case Cell.CELL_TYPE_BOOLEAN: return String.valueOf(cell.getBooleanCellValue());  
    			case Cell.CELL_TYPE_FORMULA:  
    				switch (cell.getCachedFormulaResultType()) {
    		            case Cell.CELL_TYPE_NUMERIC: 
    		            	if (DateUtil.isCellDateFormatted(cell)) { return null; } 
    		            	else { return NumberToTextConverter.toText(cell.getNumericCellValue()); }  
    		            case Cell.CELL_TYPE_STRING:
    		                return cell.getRichStringCellValue().getString(); 
    		            case Cell.CELL_TYPE_BOOLEAN:
    		            	return String.valueOf(cell.getBooleanCellValue()); 
    		            case Cell.CELL_TYPE_FORMULA: 
		            		return evaluator != null ? string(evaluator.evaluateInCell(cell), evaluator) : null;  
    		            default:  return null;  
    	            }
    			case Cell.CELL_TYPE_NUMERIC:  
    			    if (DateUtil.isCellDateFormatted(cell)) { return null; } else {  return NumberToTextConverter.toText(cell.getNumericCellValue());  }  
    			case Cell.CELL_TYPE_STRING:  
    			    return cell.getRichStringCellValue().getString();  
    			default:  
    			    return null;  
    	    }
    	}
    }
    
    /**
     * Set
     */
    public static class Set{
    	
    	public static Cell cellValue(Row row, int column, Object value) {
        	return cellValue(row, column, value, null);
        }
    	
    	public static Cell cellValue(Row row, int column, Object value, Object defaultValue) {
    		return cellValue(row, column, value, defaultValue, null);
    	}
    	
        public static Cell cellValue(Row row, int column, Object value, Object defaultValue, CellStyle style) {
        	Cell cell = row.getCell(column);
        	if(cell == null){ cell = row.createCell(column);}
        	return cellValue(cell, value, style);
        }
        public static Cell cellValue(Row row, int column, Object value, CellStyle style) {
        	return cellValue(row, column, value, null, style);
        }
        
        public static Cell cellValue(Cell cell, Object value){
        	return cellValue(cell, value, null);
        }
        
        public static Cell cellValue(Cell cell, Object value, Object defaultValue){
        	return cellValue(cell, value, defaultValue, null);
        }
        
        public static Cell cellValue(Cell cell, Object value, CellStyle style){
        	return cellValue(cell, value, null, style);
        }
        
        public static Cell cellValue(Cell cell, Object value, Object defaultValue, CellStyle style){
    		if(value == null){ 
    			if(defaultValue == null){
    				cell.setCellValue(""); 
    			} else {
    				return cellValue(cell, defaultValue, null, style);
    			}
    		} else if(value instanceof String){ cell.setCellValue((String) value); }
        	else if(value instanceof Boolean){ cell.setCellValue((Boolean) value); }
        	else if(value instanceof Calendar){ cell.setCellValue((Calendar) value); }
        	else if(value instanceof Integer){ cell.setCellType(Cell.CELL_TYPE_NUMERIC); cell.setCellValue((Integer) value); }
        	else if(value instanceof Double){ cell.setCellType(Cell.CELL_TYPE_NUMERIC);	cell.setCellValue((Double) value); 
        	}
        	else if(value instanceof Date){ cell.setCellValue((Date) value); }
        	else if(value instanceof RichTextString){ cell.setCellValue((RichTextString) value); }
        	else if(value instanceof Float){ cell.setCellValue((Float) value); }
        	else { cell.setCellValue(value.toString());}
    		//--
        	if(style != null){ cell.setCellStyle(style); }
        	return cell;
    	}
        
        public static void autoSizeColumn(Sheet sheet) {
        	Row row = sheet.getRow(0);
        	if(row != null){
        		int col = row.getLastCellNum();
        		for (int i = 0; i < col; i++) {
        			Cell cell = row.getCell(i);
        			CellStyle cellStyle = null;
        			if(cell != null && (cellStyle = cell.getCellStyle()) != null){
        				cellStyle.setWrapText(false);
        			}
    				sheet.autoSizeColumn(i, true); 
    			}
        	}
        }
        
        public static Row title(Sheet sheet, String[] cellValues) {
            CellStyle style = Style.getTitleStyle(sheet.getWorkbook());
            return title(sheet, cellValues, style);
        }
        
        public static Row title(Sheet sheet, String[] cellValues, CellStyle style) {
            Row titleRow = sheet.createRow(0);
            int colIndex = 0;
            for (String value : cellValues) {
                Cell cell = titleRow.createCell(colIndex);
                cell.setCellValue(value);
                cell.setCellStyle(style);
                colIndex++;
            }
            return titleRow;
        }
    }
    
}



