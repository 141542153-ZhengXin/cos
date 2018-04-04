package com.cos.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cos.entity.AdminEntity;
import com.cos.entity.StudentEntity;
import com.cos.entity.UserAccessEntity;


@Repository
public class AdminDao implements AdminDaoI {

	@Autowired
	private SessionFactory sessionFactory;	

	public AdminEntity getAdmin(int adminId) {
		// TODO 自动生成的方法存根
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("select * from admin")
				.setResultTransformer(Transformers.aliasToBean(AdminEntity.class));
		AdminEntity adminEntity = null;
		if (query.list().get(0) != null) {
			adminEntity = (AdminEntity) query.list().get(0);
		}
		return adminEntity;
	}

	public void MsgProcess(int sId, String msg) {
		// TODO 自动生成的方法存根
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("update studentmsg set resumeMsg=:resumeMsg where id=:id");
		query.setParameter("resumeMsg", msg);
		query.setParameter("id", sId);
		int executeUpdate = query.executeUpdate();
	}

	public StudentEntity PassProcess(int sId, int isPass) {
		// TODO 自动生成的方法存根
		Session session = sessionFactory.getCurrentSession();
		StudentEntity studentEntity = null;
		Query query = session.createSQLQuery("update student s set s.isPass=:isPass where s.id=:id");
		query.setParameter("isPass", isPass);
		query.setParameter("id", sId);
		int executeUpdate = query.executeUpdate();
		if (executeUpdate == 1) {
			studentEntity = (StudentEntity) session.get(StudentEntity.class, sId);
			return studentEntity;
		}
		return studentEntity;
	}
	
	public ArrayList getAreaStatistics() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(
				"select area,count(area) as sumArea from student where isDelivery=1 group by area order by area desc limit 6")
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		ArrayList<Map<String, Integer>> list=(ArrayList<Map<String, Integer>>) query.list();
		return list;
	}
	
	public ArrayList getMajorStatistics() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(
				"select major,count(major) as sumMajor from student where isDelivery=1 group by major order by major desc limit 6")
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		ArrayList<Map<String, Integer>> list=(ArrayList<Map<String, Integer>>) query.list();
		return list;
	}

	@Override
	public void updatePassWord(UserAccessEntity userAccessEntity) {
		Session session = sessionFactory.getCurrentSession();
		userAccessEntity.setPermission(1);
		session.saveOrUpdate(userAccessEntity);
	}

	@Override
	public UserAccessEntity getPassWord(int userId) {
		Session session = sessionFactory.getCurrentSession();
		UserAccessEntity userAccessEntity1=(UserAccessEntity) session.get(UserAccessEntity.class, userId);
		return userAccessEntity1;
	}

	@Override
	public List getAdmin() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("select * from admin")
				.setResultTransformer(Transformers.aliasToBean(AdminEntity.class));
		return query.list();
	}
}
