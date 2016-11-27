package com.tfsp.library.common.keycode;

import com.tfsp.library.common.cache.vm.CacheManager;
import com.tfsp.library.common.cache.vm.VMCache;

/**
 * @author Shmilycharlene
 * @version 1.0
 */
/* 
 * 文件名: com.tfsp.library.common.keycode.JvmCodeMaster.java
 *
 * 修改记录
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 修改日期				修改者						备注信息
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 2013-12-19			Shmilycharlene				初始化版本	
 * 
 */
public class JvmCodeMaster extends CodeMaster{
	private static final String CODE_MASTER_CACHE = "com.sprite.codemaster.VMCache";
	private VMCache cache;
	
	protected JvmCodeMaster(){
		super();
		cache = CacheManager.getInstance().getCache(CODE_MASTER_CACHE);
	}

	/* (non-Javadoc)
	 * @see com.tfsp.library.common.keycode.CodeMaster#get(java.lang.String)
	 */
	@Override
	protected KeyType get(String keyType){
		return cache.get(keyType);
	}

	/* (non-Javadoc)
	 * @see com.tfsp.library.common.keycode.CodeMaster#put(java.lang.String, com.tfsp.library.common.keycode.KeyType)
	 */
	@Override
	protected void put(String keyType, KeyType keyTypeObject){
		cache.put(keyType, keyTypeObject);
	}

	/* (non-Javadoc)
	 * @see com.tfsp.library.common.keycode.CodeMaster#remove(java.lang.String)
	 */
	@Override
	protected void remove(String keyType){
		cache.remove(keyType);
	}

	/* (non-Javadoc)
	 * @see com.tfsp.library.common.keycode.CodeMaster#clear()
	 */
	@Override
	public void clear(){
		cache.clear();
	}
}

