package com.cos.cache;

import java.util.HashMap;
import java.util.Map;

import com.cos.entity.StudentEntity;

public class StudentMemory {

	private static class StudentMemoryHolder {
		private static final StudentMemory INSTANCE = new StudentMemory();
	}

	private StudentMemory() {

	}

	public static final StudentMemory getInstance() {
		return StudentMemoryHolder.INSTANCE;
	}

	private Map<Integer, StudentEntity> studentMap = new HashMap<Integer, StudentEntity>();

	public void setStudent(Map<Integer, StudentEntity> studentMap) {
		this.studentMap = studentMap;
	}

	public Map<Integer, StudentEntity> getStudent() {
		return studentMap;
	}

}
