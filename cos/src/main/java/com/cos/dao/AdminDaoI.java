package com.cos.dao;

import java.util.ArrayList;
import java.util.List;

import com.cos.entity.AdminEntity;
import com.cos.entity.StudentEntity;
import com.cos.entity.UserAccessEntity;

public interface AdminDaoI {

	public abstract AdminEntity getAdmin(int adminId);

	public abstract void MsgProcess(int sId, String msg);

	public abstract StudentEntity PassProcess(int sId, int isPass);

	public abstract ArrayList getAreaStatistics();

	public abstract ArrayList getMajorStatistics();

	public abstract void updatePassWord(UserAccessEntity userAccessEntity);

	public abstract UserAccessEntity getPassWord(int userId);

	public abstract List getAdmin();

}
