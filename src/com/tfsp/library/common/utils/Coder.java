package com.tfsp.library.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.security.MessageDigest;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.tfsp.library.common.logging.Logger;
import com.tfsp.library.common.logging.LoggerFactory;

/**
 * @author Shmilycharlene
 * @version 1.0
 */
/* 
 * 文件名: com.tfsp.library.common.code.Coder.java
 *
 * 修改记录
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 修改日期				修改者						备注信息
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 2016年4月27日			Shmilycharlene				初始化版本	
 * 
 */
public class Coder{
	private static final Logger logger = LoggerFactory.getLogger(Coder.class);
	
	public static class MD5{
		public static final String encode(String plainText){
			return MD5Encode(plainText, CharSets.UTF8);
		}
		
		/**
		 * 对字符串数组进行MD5加密, 忽略数组总的null对象
		 * @param strings
		 * @return
		 * <br>
		 * 创建日期: 2016年5月5日 <br>
		 * 创  建  人: Shmilycharlene
		 */
		public static final String encode(String...strings) {
			StringBuilder builder = new StringBuilder();
			for(String string : strings) {
				builder.append(string == null ? "" : string);
			}
			return MD5Encode(builder.toString(), CharSets.UTF8);
		}
		
		public static final String encode(String plainText, String charset){
			Charset charset2 = Charset.forName(charset);
			return MD5Encode(plainText, charset2);
		}
	
		public static final String encode(File file) throws IOException{
			return getFileMD5String(file);
		}
	}
	
	public static class SHA{
		public static final String encode(String plainText){
			return SHAEncode(plainText);
		}
	}
	
	public static class Base64{
		public static final String encode(String plainText){
			return base64Encode(plainText, CharSets.UTF8);
		}
		
		public static final String encode(String plainText, String charName){
			Charset charset = Charset.forName(charName);
			return base64Encode(plainText, charset);
		}
	
		public static final String decode(String cipherText){
			return base64Decode(cipherText, CharSets.UTF8);
		}
		
		public static final String decode(String cipherText, String charName){
			Charset charset = Charset.forName(charName);
			return base64Decode(cipherText, charset);
		}
	}
	
	public static class Password{
		public static final String encode(String password){
			return MD5.encode(SHA.encode(password)+password);
		}
		
		public static final String encode(String userName, String password){
			return encode(userName + "|" + password);
		}
	}
	
	/**
	 * MD5加密算法.
	 * 
	 * @param source
	 * @return encrypt
	 */
	private static final String MD5Encode(String source, Charset charset) {
		if (source == null)
			throw new NullPointerException("Method MD5 input can't be null.");
		StringBuffer hexValue = new StringBuffer();
		try {
			byte[] byteArray;
			if (charset != null) {
				byteArray = source.getBytes(charset);
			} else {
				byteArray = source.getBytes();
			}
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] byteMD5 = md5.digest(byteArray);
			for (int i = 0; i < byteMD5.length; i++) {
				int val = ((int)byteMD5[i]) & 0xff;
				if (val < 16) {
					hexValue.append("0");
				}
				hexValue.append(Integer.toHexString(val));
			}
		} catch (Exception e) {
			logger.error("MD5加密错误, 字符编码:{1} Exception: {0}", e.getMessage(), charset.name());
		} 
		return hexValue.toString().toUpperCase();
	}

 	/**
	 * 计算文件的MD5，重载方法
	 * 
	 * @param file 文件对象
	 * @return md5
	 * @throws IOException
	 */
	private static String getFileMD5String(File file) throws IOException {
		FileInputStream in = new FileInputStream(file);
		FileChannel ch = in.getChannel();
		MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
		StringBuffer hexValue = new StringBuffer();
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(byteBuffer);
			byte[] byteMD5 = md5.digest();
			for (int i = 0; i < byteMD5.length; i++) {
				int val = ((int)byteMD5[i]) & 0xff;
				if (val < 16) {
					hexValue.append("0");
				}
				hexValue.append(Integer.toHexString(val));
			}
		} catch (Exception e) {
			logger.error("MD5加密文件错误, 文件:{0}, Exception: {1}", file.getName(), e.getMessage());
		} finally {
			if(in != null)
				in.close();
			if(ch != null)
				ch.close();
			if(byteBuffer != null)
				byteBuffer.clear();
		}
		return hexValue.toString().toUpperCase();
	}  
	
	/**
	 * 散列码加密算法.
	 * 
	 * @param source
	 * @return
	 * <br>
	 * 创建日期: 2012-12-22 <br>
	 * 创 建 人: Shmilycharlene
	 */
	private static final String SHAEncode(String source) {
		if (source == null) {
			throw new NullPointerException("Method SHA input can't be null.");
		}
		StringBuffer hexValue = new StringBuffer();
		char[] charArray = source.toCharArray();
		byte[] byteArray = new byte[charArray.length];
		for (int i = 0; i < byteArray.length; i++) {
			byteArray[i] = (byte)charArray[i];
		}
		try {
			MessageDigest sha = MessageDigest.getInstance("SHA");
			byte[] byteSHA = sha.digest(byteArray);
			for (int i = 0; i < byteSHA.length; i++) {
				int val = ((int)byteSHA[i]) & 0xff;
				if (val < 16) {
					hexValue.append("0");
				}
				hexValue.append(Integer.toHexString(val));
			}
		} catch (Exception e) {
			logger.error("SHA加密错误, Exception: {0}", e.getMessage());
		}
		return hexValue.toString().toUpperCase();
	}

	private static String base64Encode(String str, Charset charset) {
		return new BASE64Encoder().encode(str.getBytes(charset));
	}
     
	private static String base64Decode(String str, Charset charset) {
		try{
			return new String(new BASE64Decoder().decodeBuffer(str), charset);
		}catch (IOException e){
			logger.error("解码Base64密文出错, 密文:{0}, Exception: {1}", str, e.getMessage());
		}
		return "";
	}
}

