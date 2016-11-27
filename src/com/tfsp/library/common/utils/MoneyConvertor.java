package com.tfsp.library.common.utils;


/**
 * 货币单位转换工具类
 * @author Shmilycharlene
 * @version 1.0
 */
public class MoneyConvertor{
//--- 元转分	
	public static String yuanToFenString(String y){
		if(StringUtils.isBlank(y)) return null;
		if(StringUtils.equals(y, "0")) return "0";
		return Calculate.create(y).mul("100").toString();
	}
	
	public static String yuanToFenString(Integer y){
		if(y == null) return null;
		if(y.intValue() == 0) return "0";
		return Calculate.create(y).mul("100").toString();
	}
	
	public static Integer yuanToFenInteger(String y){
		if(StringUtils.isBlank(y)) return null;
		if(StringUtils.equals(y, "0")) return 0;
		return Calculate.create(y).mul("100").iv();
	}
	
	public static Integer yuanToFenInteger(Integer y){
		if(y == null) return null;
		if(y.intValue() == 0) return 0;
		return Calculate.create(y).mul("100").iv();
	}
	
//--- 分转元
	public static String fenToYuanString(String f){
		if(StringUtils.isBlank(f)) return null;
		if(StringUtils.equals(f, "0")) return "0";
		return Calculate.create(f).div("100").toString();
	}
	
	public static String fenToYuanString(Integer f){
		if(f == null) return null;
		if(f.intValue() == 0) return "0";
		return Calculate.create(f).div("100").toString();
	}
	
	public static Integer fenToYuanInteger(String f){
		if(StringUtils.isBlank(f)) return null;
		if(StringUtils.equals(f, "0")) return 0;
		return Calculate.create(f).div("100").iv();
	}
	
	public static Integer fenToYuanInteger(Integer f){
		if(f == null) return null;
		if(f.intValue() == 0) return 0;
		return Calculate.create(f).div("100").iv();
	}
	
	
}

