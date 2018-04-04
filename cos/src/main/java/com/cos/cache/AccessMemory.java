package com.cos.cache;

import java.util.HashMap;
import java.util.Map;
import com.cos.entity.UserAccessEntity;

public class AccessMemory {

	private static class AccessMemoryHolder {
		private static final AccessMemory INSTANCE = new AccessMemory();
	}

	private AccessMemory() {

	}

	public static final AccessMemory getInstance() {
		return AccessMemoryHolder.INSTANCE;
	}

	private Map<Integer, UserAccessEntity> userAndAccessMap = new HashMap<Integer, UserAccessEntity>();

	public void setUserAndAccess(Map<Integer, UserAccessEntity> userAndAccessMap) {
		this.userAndAccessMap = userAndAccessMap;
	}

	public Map<Integer, UserAccessEntity> getUserAndAccess() {
		return userAndAccessMap;
	}

}
