package com.cos.dao;

import java.util.Map;

import com.cos.entity.UserAccessEntity;

public interface UserAccessDaoI {
	public abstract Map<Integer, UserAccessEntity> getUserAndAccessMap();
}
