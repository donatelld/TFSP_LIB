package com.tfsp.library.common.excel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import com.tfsp.library.common.logging.Logger;
import com.tfsp.library.common.logging.LoggerFactory;
import com.tfsp.library.web.context.AttributeContext;

@SuppressWarnings("rawtypes")
public class CSVModel extends AbstractExcelModel{
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void writeSheet(String name, String[] header, List data) {
		this.headers = header;
		this.data = data;
	}

	@Override
	public String writeFile(String relative, String fileName) throws IOException {
		fileName += ".csv";
		File file = new File(AttributeContext.getAppPath() + relative + fileName);
    	if (!file.getParentFile().exists()) {
    		file.getParentFile().mkdirs();
        }
    	if (!file.exists()) {
    		file.createNewFile();
        }
		FileOutputStream out = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        try {
            out = new FileOutputStream(file);
            osw = new OutputStreamWriter(out);
            bw =new BufferedWriter(osw);
            StringBuilder rowBuilder = new StringBuilder();
            for (int i = 0; i < getColCount(); i++) {
            	rowBuilder.append(getColumnName(i));
            	if(i < getColCount() - 1){
            		rowBuilder.append(",");
            	}
            }
            bw.append(rowBuilder.toString()).append("\r");
            while(data != null && data.size() > 0) {
            	rowBuilder = new StringBuilder();
    			rowData = converter.convert(data.remove(0));
    			for(int j = 0; j < getColCount(); j++) {
    				Object value = getValueAt(j);
    				if(value != null){
    					if(value instanceof String){
    						rowBuilder.append("=\"").append(value).append("\"");
    					}else{
    						rowBuilder.append(value);
    					}
    				}
    				if(j < getColCount() - 1){
                		rowBuilder.append(",");
                	}
    			}
    			bw.append(rowBuilder.toString()).append("\r");
    		}
        } catch (Exception e) {
        	logger.error("输出CSV文件异常：{0}", e);
        } finally{
            if(bw!=null){
                try {
                    bw.close();
                    bw=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
            if(osw!=null){
                try {
                    osw.close();
                    osw=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
            if(out!=null){
                try {
                    out.close();
                    out=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
        }
        return relative + fileName;
	}

	@Override
	protected void createWorkbook() {
		
	}
}

