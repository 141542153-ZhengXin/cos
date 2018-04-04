package com.cos.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cos.entity.StudentEntity;
import com.cos.entity.UserAccessEntity;

@Repository
public class StudentDao implements StudentDaoI {

	@Autowired
	private SessionFactory sessionFactory;

	public Map getStudentMap() {
		Session session = sessionFactory.getCurrentSession();
		Map<Integer, StudentEntity> map = new HashMap<Integer, StudentEntity>();
		Query query = session.createSQLQuery("select * from student")
				.setResultTransformer(Transformers.aliasToBean(StudentEntity.class));
		List list = query.list();
		for (int i = 0; i < list.size(); i++) {
			StudentEntity studentEntity = (StudentEntity) list.get(i);
			map.put(studentEntity.getId(), studentEntity);
		}
		return map;
	}

	@Override
	public StudentEntity resumeUpdate(int id) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("select * from student where id=:id")
				.setResultTransformer(Transformers.aliasToBean(StudentEntity.class));
		query.setParameter("id", id);
		StudentEntity studentEntity = (StudentEntity) query.list().get(0);
		return studentEntity;
	}

	@Override
	public String sendResume(StudentEntity studentEntity) {
		String result;
		Session session = sessionFactory.getCurrentSession();
		try {
			StudentEntity studentEntity2 = (StudentEntity) session.get(StudentEntity.class, studentEntity.getId());
			studentEntity2.setTelephone(studentEntity.getTelephone());
			studentEntity2.setOrganization(studentEntity.getOrganization());
			studentEntity2.setHobby(studentEntity.getHobby());
			studentEntity2.setEvaluation(studentEntity.getEvaluation());
			session.saveOrUpdate(studentEntity2);
			result = "success";
		} catch (Exception e) {
			result = "failure";
		}
		return result;
	}

	@Override
	public ArrayList<StudentEntity> getStudentTable() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("select * from student")
				.setResultTransformer(Transformers.aliasToBean(StudentEntity.class));
		Iterator iterator = query.list().iterator();
		ArrayList<StudentEntity> list = new ArrayList<StudentEntity>();
		while (iterator.hasNext()) {
			StudentEntity studentEntity = (StudentEntity) iterator.next();
			list.add(studentEntity);
		}
		return list;
	}

	@Override
	public void updatePassWord(UserAccessEntity userAccessEntity) {
		Session session = sessionFactory.getCurrentSession();
		userAccessEntity.setPermission(0);
		session.saveOrUpdate(userAccessEntity);
	}

	@Override
	public UserAccessEntity getPassWord(int userId) {
		Session session = sessionFactory.getCurrentSession();
		UserAccessEntity userAccessEntity1 = (UserAccessEntity) session.get(UserAccessEntity.class, userId);
		return userAccessEntity1;
	}

	@Override
	public boolean isResume(int id) {
		Session session = sessionFactory.getCurrentSession();
		StudentEntity StudentEntity = (StudentEntity) session.get(StudentEntity.class, id);
		if ("".equals(StudentEntity.getOrganization())) {
			return true;
		}
		return false;
	}

}
