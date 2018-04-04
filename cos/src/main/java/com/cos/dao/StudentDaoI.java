package com.cos.dao;

import java.util.ArrayList;
import java.util.Map;

import com.cos.entity.StudentEntity;
import com.cos.entity.UserAccessEntity;

public interface StudentDaoI {

	public abstract Map<Integer, StudentEntity> getStudentMap();

	public abstract StudentEntity resumeUpdate(int id);

	public abstract String sendResume(StudentEntity studentEntity);

	public abstract ArrayList<StudentEntity> getStudentTable();

	public abstract void updatePassWord(UserAccessEntity userAccessEntity);

	public abstract UserAccessEntity getPassWord(int userId);

	public abstract boolean isResume(int id);


}
