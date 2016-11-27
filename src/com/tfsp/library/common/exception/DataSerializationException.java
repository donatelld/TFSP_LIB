package com.tfsp.library.common.exception;

/**
 * @author Shmilycharlene
 * @version 1.0
 */
/* 
 * 文件名: com.tfsp.library.common.utils.exception.DataSerializationException.java
 *
 * 修改记录
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 修改日期				修改者						备注信息
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 2012-12-10			Shmilycharlene				初始化版本	
 * 
 */
public class DataSerializationException extends Exception {
	private static final long serialVersionUID = 1L;

	public DataSerializationException() {
		super();
	}

	public DataSerializationException(String message) {
		super(message);
	}

	public DataSerializationException(Throwable cause) {
		super(cause);
	}

	public DataSerializationException(String message,Throwable cause) {
		super(message, cause);
	}
}
