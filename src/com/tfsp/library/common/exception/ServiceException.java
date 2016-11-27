package com.tfsp.library.common.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.tfsp.library.common.utils.StringUtils;

/**
 * 系统中所以自定义异常的父类. 表示程序在运行过程中遇到的各种类型的错误.
 * @author Shmilycharlene
 * @version 1.0
 */
/* 
 * 文件名: com.tfsp.library.common.exception.ServiceException.java
 *
 * 修改记录
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 修改日期				修改者						备注信息
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 2012-12-8			Shmilycharlene				初始化版本	
 * 
 */
public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = 8795633589880171537L;
	private String exceptionCode;
	private Map<String, Object> parameters;

	public ServiceException(String message){
		super(message);
	}
	
	public ServiceException(Throwable cause){
		super(cause);
	}
	
	public ServiceException(String code,String message) {
		super(message);
		exceptionCode = code;
	}

	public ServiceException(String message,Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String code,String message,Map<String, Object> params) {
		super(message);
		this.exceptionCode = code;
		if (params != null) {
			putAllParam(params);
		}
	}

	public ServiceException(String code,String message,Throwable cause) {
		super(message, cause);
		this.exceptionCode = code;
	}

	public ServiceException(String code,String message,Throwable cause,Map<String, Object> params) {
		super(message, cause);
		this.exceptionCode = code;
		if (params != null) {
			putAllParam(params);
		}
	}

	/**
	 * 根据参数名, 获取此异常下的一个参数值
	 * @param paramName
	 * @return Object <br>
	 * 创建日期: 2012-12-8 <br>
	 * 创  建  人: Shmilycharlene
	 */
	public Object getParameter(String paramName) {
		if (parameters == null) {
			return null;
		}
		return parameters.get(paramName);
	}

	/**
	 * 获取此异常下, 所有异常参数的名字
	 * @return Array of String <br>
	 * 创建日期: 2012-12-8 <br>
	 * 创  建  人: Shmilycharlene
	 */
	public String[] getParameterNames() {
		if (parameters == null) {
			return new String[0];
		}
		Set<String> keySet = parameters.keySet();
		String[] paramNames = new String[keySet.size()];
		int i = 0;
		for (String paramName : keySet) {
			paramNames[i++] = paramName;
		}
		return paramNames;
	}

	/**
	 * 将一个参数放入异常实例中
	 * @param paramName
	 * @param paramValue <br>
	 * 创建日期: 2012-12-8 <br>
	 * 创  建  人: Shmilycharlene
	 */
	public void putParameter(String paramName, Object paramValue) {
		if (parameters == null) {
			parameters = new HashMap<String, Object>();
		}
		parameters.put(paramName, paramValue);
	}

	/**
	 * 获取此异常的自定义代码
	 * @return  exceptionCode<br>
	 * 创建日期: 2012-12-8 <br>
	 * 创  建  人: Shmilycharlene
	 */
	public String getExceptionCode() {
		return exceptionCode;
	}

	/**
	 * 获取此异常下的所有相关参数
	 * @return parameter <br>
	 * 创建日期: 2012-12-8 <br>
	 * 创  建  人: Shmilycharlene
	 */
	public Map<String, Object> getParameters() {
		if (parameters == null)
			parameters = new HashMap<String, Object>();
		return this.parameters;
	}

	public String getMessage() {
		StringBuilder builder = new StringBuilder();
		builder.append("Exception Code: ").append(StringUtils.isBlank(exceptionCode) ? "NULL" : getExceptionCode());
		builder.append("\t").append(super.getMessage()).append("\n");
		if(getParameterNames().length > 0){
			builder.append("异常参数列表: \n").append(parameters == null || parameters.size() == 0 ? "NULL\n" : "");
			String[] names = getParameterNames();
			for (String paraName : names) {
				builder.append(paraName).append(": ").append(getParameter(paraName)).append("\n");
			}
		}
		builder.deleteCharAt(builder.length()-1);
		return builder.toString();
	}
	
	private void putAllParam(Map<String, Object> params) {
		Set<String> keySet = params.keySet();
		for (String key : keySet) {
			putParameter(key, params.get(key));
		}
	}
}
