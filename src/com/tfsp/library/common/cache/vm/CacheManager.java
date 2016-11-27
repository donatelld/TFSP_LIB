package com.tfsp.library.common.cache.vm;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import net.rubyeye.xmemcached.MemcachedClient;

import com.tfsp.library.web.context.SpringContext;


/**
 * @author Shmilycharlene
 * @version 1.0
 */
/* 
 * 文件名: com.tfsp.library.common.cache.vm.CacheManager.java
 *
 * 修改记录
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 修改日期				修改者						备注信息
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 2016年5月27日			Shmilycharlene				初始化版本	
 * 
 */
public class CacheManager{
	private static final String DEFAULT_CACHE = "com.tfsp.library.cache.DEFAULT_CACHE";
	private static final CacheManager instance = new CacheManager();
	
	private Map<String, VMCache> cachePool; 
	private Lock lock = new ReentrantLock();
	private CacheManager() {
		cachePool = new HashMap<String, VMCache>();
	}
	
	/**
	 * 获取系统级缓存管理器
	 * @return
	 * <br>
	 * 创建日期: 2016年5月27日 <br>
	 * 创  建  人: Shmilycharlene
	 */
	public static CacheManager getInstance() {
		return instance;
	}
	
	/**
	 * 获取指定名称的缓存空间, 如果系统中没有这个指定的空间则创建一个返回.
	 * @param poolName
	 * @return
	 * <br>
	 * 创建日期: 2016年5月27日 <br>
	 * 创  建  人: Shmilycharlene
	 */
	public VMCache getCache(String poolName) {
		lock.lock();
		try{
			if(cachePool.containsKey(poolName))
				return cachePool.get(poolName);
			else {
				VMCache cache = new VMCache(poolName);
				cachePool.put(poolName, cache);
				return cache;
			}
		}finally {
			lock.unlock();
		}
	}

	/**
	 * 获取系统默认的缓存空间
	 * @return
	 * <br>
	 * 创建日期: 2016年5月27日 <br>
	 * 创  建  人: Shmilycharlene
	 */
	public VMCache getCache() {
		return getCache(DEFAULT_CACHE);
	}
	
	public static MemcachedClient getMemCache() {
		MemcachedClient memcachedClient = SpringContext.getBean("memcachedClient", MemcachedClient.class);
		return memcachedClient;
	}
}

