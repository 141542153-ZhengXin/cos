package com.cos.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.cos.websocket.MessageQueue;

public class CoordinationCache {

	private static class CoordinationCacheHolder {
		private static final CoordinationCache INSTANCE = new CoordinationCache();
	}

	private CoordinationCache() {

	}

	public static final CoordinationCache getInstance() {
		return CoordinationCacheHolder.INSTANCE;
	}

	public Map<Integer, MessageQueue> coordinationCache = new ConcurrentHashMap<Integer, MessageQueue>();

	public void setCoordinationCache(Map<Integer, MessageQueue> coordinationCache) {
		this.coordinationCache = coordinationCache;
	}

	public Map<Integer, MessageQueue> getCoordinationCache() {
		return coordinationCache;
	}

}
