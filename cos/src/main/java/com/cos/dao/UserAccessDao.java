package com.cos.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cos.entity.UserAccessEntity;

@Repository
public class UserAccessDao implements UserAccessDaoI {

	@Autowired
	private SessionFactory sessionFactory;	

	public Map getUserAndAccessMap() {
		Session session = sessionFactory.getCurrentSession();
		Map<Integer, UserAccessEntity> map = new HashMap<Integer, UserAccessEntity>();
		Query query = session.createSQLQuery("select * from UserAccess")
				.setResultTransformer(Transformers.aliasToBean(UserAccessEntity.class));
		List list = query.list();
		for (int i = 0; i < list.size(); i++) {
			UserAccessEntity userAccessEntity = (UserAccessEntity) list.get(i);
			map.put(userAccessEntity.getUserId(), userAccessEntity);
		}
		return map;
	}

}
