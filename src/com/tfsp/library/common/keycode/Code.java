package com.tfsp.library.common.keycode;

import java.io.Serializable;

import com.tfsp.library.common.utils.StringUtils;

/**
 * 表示一个Code记录的实体类. 该类只能在Library内部使用. 
 * @author Shmilycharlene
 * @version 1.0
 */
/* 
 * 文件名: com.tfsp.library.common.keycode.Code.java
 *
 * 修改记录
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 修改日期				修改者						备注信息
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 2012-12-23			Shmilycharlene				初始化版本	
 * 
 */
public class Code implements Serializable{
	private static final long	serialVersionUID	= 1L;
	
	private String code;
	private String decode;
	private String display;
	private boolean editable;
	private String description;
	
	protected Code(){
		
	}
	
	protected Code(String code, String decode, String display, String editableInd, String description){
		this.code = code;
		this.decode = decode;
		this.display = display;
		if(StringUtils.equals(editableInd, KeyType.Y)){
			editable = true;
		}
		this.description = description;
	}
	/**
	 * 设置Code的值, 该方法只能在Library内部调用, 防止外部修改.
	 * @param code
	 * <br>
	 * 创建日期: 2012-12-24 <br>
	 * 创  建  人: Shmilycharlene
	 */
	protected void setCode(String code){
		this.code = code;
	}
	
	/**
	 * 设置Decode的值,  该方法只能在Library内部调用, 防止外部修改.
	 * @param decode
	 * <br>
	 * 创建日期: 2012-12-24 <br>
	 * 创  建  人: Shmilycharlene
	 */
	protected void setDecode(String decode){
		this.decode = decode;
	}
	
	/**
	 * 设置Display的值, 该方法只能在Library内部调用, 防止外部修改.
	 * @param display
	 * <br>
	 * 创建日期: 2012-12-24 <br>
	 * 创  建  人: Shmilycharlene
	 */
	protected void setDisplay(String display) {
		this.display = display;
	}
	
	/**
	 * 设置该Code是否可编辑标示,  该方法只能在Library内部调用, 防止外部修改.
	 * @param editableInd
	 * <br>
	 * 创建日期: 2012-12-24 <br>
	 * 创  建  人: Shmilycharlene
	 */
	protected void setEditable(String editableInd) {
		if(StringUtils.equals(editableInd, KeyType.Y)){
			editable = true;
		} else if (StringUtils.equals(editableInd, KeyType.N)) {
			editable = false;
		}
	}
	
	/**
	 * 设置描述信息,  该方法只能在Library内部调用, 防止外部修改.
	 * @param description
	 * <br>
	 * 创建日期: 2012-12-24 <br>
	 * 创  建  人: Shmilycharlene
	 */
	protected void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * 获取Code
	 * @return code
	 * <br>
	 * 创建日期: 2012-12-24 <br>
	 * 创  建  人: Shmilycharlene
	 */
	protected String getCode(){
		return code;
	}
	
	/**
	 * 获取Decode
	 * @return decode
	 * <br>
	 * 创建日期: 2012-12-24 <br>
	 * 创  建  人: Shmilycharlene
	 */
	protected String getDecode(){
		return decode;
	}
	
	/**
	 * 获取页面显示值
	 * @return display
	 * <br>
	 * 创建日期: 2012-12-24 <br>
	 * 创  建  人: Shmilycharlene
	 */
	protected String getDisplay(){
		return display;
	}
	
	/**
	 * 返回该Code是否可编辑
	 * @return editable
	 * <br>
	 * 创建日期: 2012-12-24 <br>
	 * 创  建  人: Shmilycharlene
	 */
	protected boolean isEditable(){
		return editable;
	}
	
	/**
	 * 返回该Code是否可编辑的标示符
	 * @return Y/N
	 * <br>
	 * 创建日期: 2012-12-24 <br>
	 * 创  建  人: Shmilycharlene
	 */
	protected String getEditableInd(){
		return editable ? KeyType.Y : KeyType.N;
	}
	
	/**
	 * 返回该Code的描述信息.
	 * @return description
	 * <br>
	 * 创建日期: 2012-12-24 <br>
	 * 创  建  人: Shmilycharlene
	 */
	protected String getDescription(){
		return description;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object object){
		if(object != null && object instanceof Code){
			Code targetCode = (Code)object;
			if(StringUtils.equals(targetCode.getCode(), code) &&
			   StringUtils.equals(targetCode.getDecode(), decode) && 
			   StringUtils.equals(targetCode.getDescription(), description) &&
			   StringUtils.equals(targetCode.getDisplay(), display) &&
			   targetCode.isEditable() == editable
			) {
				return true;
			}else {
				return false;
			}
		}else{
			return false;
		}
	}
}


