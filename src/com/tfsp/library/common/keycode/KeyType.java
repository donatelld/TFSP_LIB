package com.tfsp.library.common.keycode;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.tfsp.library.common.utils.StringUtils;

/**
 * 表示KEY_CODE_MASTER表中, 某一个键值KeyType下所有的Code记录的实体类.
 * @author Shmilycharlene
 * @version 1.0
 */
/* 
 * 文件名: com.tfsp.library.common.keycode.KeyType.java
 *
 * 修改记录
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 修改日期				修改者						备注信息
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 2012-12-23			Shmilycharlene				初始化版本	
 * 
 */
public class KeyType implements Cloneable, Serializable {
	private static final long serialVersionUID = 8665674982523917134L;
	protected static final String Y = "Y";
	protected static final String N = "N";
	private String keyType;
    private Map<String, Code> cachedCode;
	private String defaultCode;
    
	protected KeyType(String keyType){
		this.keyType = keyType;
		cachedCode = Collections.synchronizedMap(new LinkedHashMap<String, Code>());
	}
	
	protected void addCode(Code code, String defaultInd, boolean ovveride){
		if(ovveride){
			cachedCode.remove(code.getCode());
			addCode(code, defaultInd);
		}
	}
	
	protected void addCode(Code code, String defaultInd) {
		cachedCode.put(code.getCode(), code);
		if(StringUtils.equals(defaultInd, Y)){
			defaultCode = code.getCode();
		}
	}
	
	protected void addCode(String code, String decode, String display, String defaultInd, String editableInd, String description) {
		Code code2 = new Code(code, decode, display, editableInd, description);
		addCode(code2, defaultInd);
	}
	
	protected Code getCode(String code){
		return cachedCode.get(code);
	}
	
	public String code(String decode){
		Iterator<String> codeIterator = cachedCode.keySet().iterator();
		while(codeIterator.hasNext()){
			String codeStr = codeIterator.next();
			Code code = getCode(codeStr);
			if(StringUtils.equals(decode, code.getDecode())){
				return code.getCode();
			}
		}
		return "";
	}
	
 	public String decode(String code){
 		Code code2 = cachedCode.get(code);
 		if(code2 != null)
 			return code2.getDecode();
		return "";
	}
	
	public String getDisplay(String code){
		Code code2 = cachedCode.get(code);
		if(code2 != null)
			return code2.getDisplay();
		return "";
	}
	
	public String getDefaultCode() {
        return this.defaultCode;
    }
	
	public String getKeyType() {
        return keyType;
    }
	
	public String getDiscription(String code){
		Code code2 = cachedCode.get(code);
		if(code2 != null)
			return code2.getDescription();
		return "";
	}
	
	public boolean isEditable(String code){
		Code code2 = cachedCode.get(code);
		if(code2 != null)
			return code2.isEditable();
		return false;
	}
	
	protected String getEditableInd(String code){
		if(cachedCode.get(code) != null)
			return cachedCode.get(code).getEditableInd();
		return N;
	}
	
	protected boolean isDefaultCode(String code) {
		return StringUtils.equals(code, defaultCode);
	}
	
	protected String getDefaultCodeInd(String code) {
		return StringUtils.equals(code, defaultCode) ? Y : N;
	}
	
	public String[] getCodes() {
        String[] codes = null;
        synchronized (cachedCode) {
            Set<String> keySet = this.cachedCode.keySet();
            codes = new String[keySet.size()];
            int i = 0;
            for (String code : keySet) {
                codes[i++] = code;
            }
        }
        return codes;
    }
}

