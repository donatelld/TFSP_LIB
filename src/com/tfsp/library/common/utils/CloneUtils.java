package com.tfsp.library.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.tfsp.library.common.exception.DataSerializationException;

/**
 * 用来克隆对象的工具类. <br>
 * 使用本工具类克隆对象前, 首先要确保被克隆的对象类实现了<code>{@link java.io.Serializable} </code>接口.<br>
 * 为实现该接口的对象在克隆时会抛出<code>{@link java.io.NotSerializableException}</code>
 * 
 * @author Shmilycharlene
 * @version 1.0
 */
/* 
 * 文件名: com.tfsp.library.common.utils.SerializerUtils.java
 *
 * 修改记录
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 修改日期				修改者						备注信息
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 2012-12-10			Shmilycharlene				初始化版本	
 * 
 */
public class CloneUtils{
	private static final String NULL_VALUE = "null";

	/**
	 * 深度克隆一个对象. 待克隆的对象须已实现<code>{@link java.io.Serializable}</code>接口.
	 * @param input
	 * @return T
	 * @throws DataSerializationException
	 * <br>
	 * 创建日期: 2012-12-10 <br>
	 * 创  建  人: Shmilycharlene
	 */
	public static <T> T deepClone(Object input) throws DataSerializationException {
		byte[] byteArrIn = getBytes(input);
		return getObject(byteArrIn);
	}

	/**
	 * 将对象转换为字节数组, 待转换的对象须已实现<code>{@link java.io.Serializable}</code>接口.
	 * @param obj
	 * @return byte[]
	 * @throws DataSerializationException
	 * <br>
	 * 创建日期: 2012-12-10 <br>
	 * 创  建  人: Shmilycharlene
	 */
	private static byte[] getBytes(Object obj) throws DataSerializationException {
		if (obj == null) {
			obj = NULL_VALUE;
		}
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.flush();
			oos.close();
			bos.close();
			return bos.toByteArray();
		} catch (IOException ex) {
			throw new DataSerializationException(ex);
		}
	}
	
	@SuppressWarnings("unchecked")
	private static <T>T getObject(byte[] input) throws DataSerializationException {
		try {
			ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(input));
			Object obj = in.readObject();
			if (obj != null && obj.equals(NULL_VALUE)) {
				return null;
			}
			return (T)obj;
		} catch (IOException ex) {
			throw new DataSerializationException(ex);
		} catch (ClassNotFoundException ex) {
			throw new DataSerializationException(ex);
		}
	}
}
