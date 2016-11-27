package com.tfsp.library.common.keycode;

import net.rubyeye.xmemcached.MemcachedClient;

import com.tfsp.library.web.context.SpringContext;


/**
 * @author Shmilycharlene
 * @version 1.0
 */
/* 
 * 文件名: com.tfsp.library.common.keycode.MemCodeMaster.java
 *
 * 修改记录
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 修改日期				修改者						备注信息
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 2016年5月30日			Shmilycharlene				初始化版本	
 * 
 */
public class MemCodeMaster extends CodeMaster{
	private MemcachedClient cacheClient;
	public MemCodeMaster() {
		super();
		cacheClient = SpringContext.getBean("memcachedClient", MemcachedClient.class);
	}
	
	/* (non-Javadoc)
	 * @see com.tfsp.library.common.keycode.CodeMaster#get(java.lang.String)
	 */
	@Override
	protected KeyType get(String keyType){
		try{
			return cacheClient.get(keyType);
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tfsp.library.common.keycode.CodeMaster#put(java.lang.String, com.tfsp.library.common.keycode.KeyType)
	 */
	@Override
	protected void put(String keyType, KeyType keyTypeObject){
		try{
			cacheClient.set(keyType, 0, keyTypeObject);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.tfsp.library.common.keycode.CodeMaster#remove(java.lang.String)
	 */
	@Override
	protected void remove(String keyType){
		try{
			cacheClient.delete(keyType);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.tfsp.library.common.keycode.CodeMaster#clear()
	 */
	@Override
	public void clear(){
		try{
			cacheClient.flushAll();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}

