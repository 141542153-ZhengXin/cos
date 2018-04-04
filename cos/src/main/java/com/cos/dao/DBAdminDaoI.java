package com.cos.dao;

import com.cos.entity.UserAccessEntity;

public interface DBAdminDaoI {

	public abstract void updatePassWord(UserAccessEntity userAccessEntity);

	public abstract UserAccessEntity getPassWord(int userId);

}
