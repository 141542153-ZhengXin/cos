package com.cos.cache;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cos.dao.UserAccessDaoI;
import com.cos.entity.UserAccessEntity;

@Component
public class AccessControl {

	@Autowired
	private UserAccessDaoI userAccessDao;

	public void InitUserAndAccess() {
		Map<Integer, UserAccessEntity> userAndAccessMap = userAccessDao.getUserAndAccessMap();
		AccessMemory.getInstance().setUserAndAccess(userAndAccessMap);
	}

}
