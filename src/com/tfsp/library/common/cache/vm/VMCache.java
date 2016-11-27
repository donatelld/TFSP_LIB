package com.tfsp.library.common.cache.vm;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Shmilycharlene
 * @version 1.0
 */
/* 
 * 文件名: com.tfsp.library.common.cache.VMCache.java
 *
 * 修改记录
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 修改日期				修改者						备注信息
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 2016年5月16日			Shmilycharlene				初始化版本	
 * 
 */
public class VMCache{
//	protected static final Logger	logger	= LoggerFactory.getLogger(VMCache.class);
//	private String cacheName;
	private Map<String, Object> map;
	private Lock lock = new ReentrantLock();
	
	VMCache(String name) {
		map = new HashMap<String, Object>();
//		cacheName = name;
	}
	
	public void put(String key, Object value) {
		lock.lock();
		try {
			if(!map.containsKey(key)) {
				map.put(key, value);
			}
		}finally {
			lock.unlock();
		}
	}
	
	public <T> T get(String key) {
		lock.lock();
		try {
			return (T)map.get(key);
		}finally {
			lock.unlock();
		}
	}
	
	public void remove(String key) {
		lock.lock();
		try{
			if(map.containsKey(key))
				map.remove(key);
		}finally {
			lock.unlock();
		}
	}
	
	public void clear() {
		lock.lock();
		try {
			map.clear();
		}finally {
			lock.unlock();
		}
	}

	public boolean contains(String key){
		lock.lock();
		try {
			return map.containsKey(key);
		}finally {
			lock.unlock();
		}
	}
}

