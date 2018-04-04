package com.cos.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cos.entity.UserAccessEntity;


@Repository
public class DBAdminDao implements DBAdminDaoI {

	@Autowired
	private SessionFactory sessionFactory;	

	@Override
	public void updatePassWord(UserAccessEntity userAccessEntity) {
		Session session = sessionFactory.getCurrentSession();
		userAccessEntity.setPermission(2);
		session.saveOrUpdate(userAccessEntity);
	}

	@Override
	public UserAccessEntity getPassWord(int userId) {
		Session session = sessionFactory.getCurrentSession();
		UserAccessEntity userAccessEntity1=(UserAccessEntity) session.get(UserAccessEntity.class, userId);
		return userAccessEntity1;
	}
}
