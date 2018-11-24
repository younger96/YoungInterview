package com.example.chenqiuyang.younginterview.cache.lru;

import android.support.v4.util.LruCache;

public class LruUserCache {

	private static LruCache<String, User> lruCache;
	private LruUserCache userCache;

	private LruUserCache(){
		this((int)Runtime.getRuntime().maxMemory()/1024/16);
	}


	//设置自定义大小的LruCache
	private LruUserCache(int maxSize){
		lruCache= new LruCache<>(maxSize * 1024);
	}

	public synchronized LruUserCache getInstance(){
		if (userCache == null){
			userCache = new LruUserCache();
		}
		return userCache;
	}

    /**
     * 写入索引key对应的缓存
     * @param key 索引
     * @param bitmap 缓存内容
     * @return 写入结果
     */
	public User putCache(String key,User bitmap){
		User user=getCache(key);
		if(user==null){
			if(lruCache!=null&&bitmap!=null)
			user= lruCache.put(key, bitmap);
		}
		return user;
	}
	/**
	 * 获取缓存
	 * @param key 索引key对应的缓存
	 * @return  缓存
	 */
	public User getCache(String key){
		if(lruCache!=null){
			return lruCache.get(key);
		}
		return null;
	}
	
	public void deleteCache(){
		if(lruCache!=null)
		lruCache.evictAll();
	}
	
	public void removeCache(String key){
		if(lruCache!=null)
		lruCache.remove(key);
	}
	
	public int size(){
		int size=0;
		if(lruCache!=null)
			size+=lruCache.size();
		return size;
	}
}