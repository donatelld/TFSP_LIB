package com.sprite.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.tfsp.library.common.keycode.KeyTypeDO;
import com.tfsp.library.common.keycode.KeyTypeMapper;
import com.tfsp.library.common.logging.Logger;
import com.tfsp.library.common.logging.LoggerFactory;
import com.tfsp.library.common.utils.StringUtils;
import com.tfsp.library.web.context.SpringContext;


/**
 * @author John
 * @version 1.0
 */
/* 
 * 文件名: com.sprite.test.MainTest.java
 *
 * 修改记录
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 修改日期				修改者						备注信息
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 2016年4月27日			John				初始化版本	
 * 
 */
@ContextConfiguration("classpath:application.xml")
public class MainTest extends AbstractJUnit4SpringContextTests{
	protected static final Logger logger = LoggerFactory.getLogger(MainTest.class);
//	private static final org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(MainTest.class);
	
	
	/**
	 * @param args
	 * <br>
	 * 创建日期: 2016年4月27日 <br>
	 * 创  建  人: John
	 */
	public static void main(String[] args){
		
//		String string = "asdfasfasdf";
//		System.err.println(StringUtils.splitWithNull(string, ":").length);
		
		
//		long start = System.currentTimeMillis();
//		for(long i = 0; i < 9000000000L; i++) {
//			Field field = ReflectUtils.getDeclardField(KeyCodeMasterDO.class, "validateResult");
//			ReflectUtils.getDeclardFields(KeyCodeMasterDO.class);
//			logger.info("{0}测试日志", "SLF4j");
//		}
//		logger.info("UseCache: {0}", System.currentTimeMillis() - start);
//		
//		start = System.currentTimeMillis();
//		try{
//			for(int i = 0; i < 200000; i++) {
//				Field field = null;
//				try{
//					field =	KeyCodeMasterDO.class.getDeclaredField("validateResult");
//				}catch (Exception e){
//				}
//			    if(field == null)
//			    	field = KeyCodeMasterDO.class.getSuperclass().getDeclaredField("validateResult");
//				logger.info("干扰日志: {0}", "1212121");
//				KeyCodeMasterDO.class.getDeclaredFields();
//				KeyCodeMasterDO.class.getSuperclass().getDeclaredFields();
//			}
//		}catch (Exception e){
//			e.printStackTrace();
//		}
//		logger.info("NotUseCache: {0}", System.currentTimeMillis() - start);
		
		
		String keyString = "com.sprite.device.${storeID} - ${1:merchantID}-缓存键 ${3}-----";
		
		List<KeyPair> list = testCacheAnnotation(keyString);
		
		StringBuilder builder = new StringBuilder();
		
		for(KeyPair keyPair : list) {
			builder.append(keyPair.value==null?"":keyPair.value)
			.append(keyPair.index == -1?"":keyPair.index)
			.append(keyPair.fieldName == null? "":keyPair.fieldName);
		}
		
		System.err.println(builder.toString());
		
		
	}
	
	public static List<KeyPair> testCacheAnnotation(String key) {
//			String key = cache.keyValue();
			List<KeyPair> list = new ArrayList<KeyPair>();
			if(StringUtils.isBlank(key)) {//如果未设置keyValue(), 则默认使用第一个参数.toString()作为key
				KeyPair keyPair = new KeyPair();
				keyPair.index = 0;
				list.add(keyPair);
				return list;
			}
			while(key.length() > 0) {
				KeyPair pair = new KeyPair();
				int pos1 = key.indexOf("${");
				int pos2 = key.indexOf("}");
				if(pos1 < 0) {//key中已经不再包含${}表达式
					pair.value = key;
					key="";
					list.add(pair);
					continue;
				}
				if(pos1 > 0) {//表示${}表达式之前包含常量字符
					pair.value = key.substring(0, pos1);
				}
				String el = key.substring(pos1 + 2, pos2);
				if(StringUtils.contains(el, ":")) {//${}表达式中包含 : 运算符
					String[] els = StringUtils.splitWithNull(el, ":");
					if(StringUtils.isNotBlank(els[0])) {
						try{
							pair.index = Integer.parseInt(els[0].trim());
						}catch (Exception e){//${name:}
							pair.index = 0;
							pair.fieldName = els[0];
						}
					}
					if(StringUtils.isNotBlank(els[1])) {
						if(pair.index == -1)
							pair.index = 0;
						pair.fieldName = els[1];
					}
				}else { // ${index} 或者 ${name}
					try{
						pair.index = Integer.parseInt(el.trim());
					}catch (Exception e){//${name:}
						pair.index = 0;
						pair.fieldName = el;
					}
				}
				key = key.substring(pos2+1);
				list.add(pair);
			}
			
			return list;
	}
	
	
	@Test
	public void test() throws InterruptedException {
		KeyTypeMapper mapper = SpringContext.getBean(KeyTypeMapper.class);
		List<KeyTypeDO> list = mapper.getKeyType("KEY_ALIPAY_CONFIG");
		KeyTypeDO keyType = list.get(0);
		System.err.println(keyType.toString());
		long start = System.currentTimeMillis();
		for(int i =0; i < 100000; i++) {
			keyType.toString();
		}
		System.err.println("转换耗时:" + (System.currentTimeMillis()-start));
	}
	
	
}
class KeyPair{
	int index = -1;
	String fieldName;
	String value;
	
	public KeyPair() {
		
	}
}
