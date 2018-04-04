package com.cos.cache;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cos.dao.StudentDaoI;
import com.cos.entity.StudentEntity;
import com.cos.model.StudentModel;

@Component
public class StudentControl {

	@Autowired
	private StudentDaoI StudentDao;

	public void InitStudent() {
		Map<Integer, StudentEntity> studentMap = StudentDao.getStudentMap();
		StudentMemory.getInstance().setStudent(studentMap);
	}

	public void UpdateStudentMemory(StudentEntity studentEntity) {
		StudentMemory.getInstance().getStudent().put(studentEntity.getId(), studentEntity);
	}

	public String[][] ResumeStatistics(String organization) {
		String[][] resumeStatisticsArray = new String[3][2];
		Calendar date = Calendar.getInstance();
		int year = date.get(Calendar.YEAR);
		int currentSum = 0;
		int lastSum = 0;
		int currentDeptSum = 0;
		int lastDeptSum = 0;
		int currentRatio = 0;
		int lastRatio = 0;
		Map<Integer, StudentEntity> studentMap = StudentMemory.getInstance().getStudent();
		Iterator<StudentEntity> iterator = studentMap.values().iterator();
		while (iterator.hasNext()) {
			StudentEntity next = iterator.next();
			if (next.getIsDelivery() == 0 && !organization.equals(next.getOrganization())) {
				continue;
			}
			date.setTime(next.getDeliveryTime());
			int yearDB = date.get(Calendar.YEAR);
			if (year == yearDB) {
				currentSum += 1;
			}
			if (year - 1 == yearDB) {
				lastSum += 1;
			}
			if (year == yearDB && organization.equals(next.getOrganization())) {
				currentDeptSum += 1;
			}
			if (year - 1 == yearDB && organization.equals(next.getOrganization())) {
				lastDeptSum += 1;
			}
			if (year == yearDB && 0 == next.getSex() && organization.equals(next.getOrganization())) {
				currentRatio += 1;
			}
			if (year - 1 == yearDB && 0 == next.getSex() && organization.equals(next.getOrganization())) {
				lastRatio += 1;
			}
		}
		resumeStatisticsArray[0][0] = lastSum + "";
		resumeStatisticsArray[0][1] = currentSum + "";
		resumeStatisticsArray[1][0] = lastDeptSum + "";
		resumeStatisticsArray[1][1] = currentDeptSum + "";
		resumeStatisticsArray[2][0] = lastRatio + ":" + (lastSum - lastRatio);
		resumeStatisticsArray[2][1] = currentRatio + ":" + (currentSum - currentRatio);
		return resumeStatisticsArray;
	}

	public ArrayList ResumeProcessTable(int pageIndex, int pageSize, int flag) {
		Map<Integer, StudentEntity> studentMap = StudentMemory.getInstance().getStudent();
		ArrayList list = new ArrayList();
		Iterator<StudentEntity> iterator = studentMap.values().iterator();
		int i = 0;
		int j = (i == pageIndex - 1 ? 0 : (pageIndex - 1) * pageSize);
		int rowCount = 0;
		while (iterator.hasNext()) {
			StudentEntity next = iterator.next();
			if (next.getIsDelivery() == 1 && next.getIsPass() == flag) {
				rowCount++;
				if (i >= j && i < j + pageSize) {
					StudentModel studentModel = new StudentModel();
					studentModel.setId(next.getId());
					studentModel.setName(next.getName());
					studentModel.setOrganization(next.getOrganization());
					list.add(studentModel);
				}
				i++;
			}
		}
		if (rowCount % pageSize != 0) {
			rowCount = rowCount / pageSize + 1;
		} else {
			rowCount = rowCount / pageSize;
		}
		list.add(pageIndex);
		list.add(rowCount);
		return list;
	}

	public ArrayList ResumeProcessTableById(int pageIndex, int pageSize, int sId) {
		Map<Integer, StudentEntity> studentMap = StudentMemory.getInstance().getStudent();
		ArrayList list = new ArrayList();
		int rowCount = 1;
		StudentEntity studentEntity = studentMap.get(sId);
		if (studentEntity != null) {
			if (studentEntity.getIsDelivery() == 1 && studentEntity.getIsPass() != 2) {
				StudentModel studentModel = new StudentModel();
				studentModel.setId(studentEntity.getId());
				studentModel.setName(studentEntity.getName());
				studentModel.setOrganization(studentEntity.getOrganization());
				studentModel.setIsPass(studentEntity.getIsPass());
				list.add(studentModel);
				list.add(pageIndex);
				list.add(rowCount);
			}
		}
		return list;
	}

	public StudentEntity CheckResume(int sId) {
		Map<Integer, StudentEntity> studentMap = StudentMemory.getInstance().getStudent();
		StudentEntity studentEntity = studentMap.get(sId);
		return studentEntity;
	}

	public StudentEntity MsgModelProcess(int sId) {
		Map<Integer, StudentEntity> studentMap = StudentMemory.getInstance().getStudent();
		StudentEntity studentEntity = studentMap.get(sId);
		return studentEntity;
	}
	
}
