package com.cos.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cos.entity.ArticleEntity;

@Repository
public class ArticleDao implements ArticleDaoI {

	@Autowired
	private SessionFactory sessionFactory;

	public Map<String, ArticleEntity> getArticleList(int adminId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("select * from article where adminId=:adminId order by id desc")
				.setResultTransformer(Transformers.aliasToBean(ArticleEntity.class));
		query.setParameter("adminId", adminId);
		ArrayList<ArticleEntity> list = (ArrayList<ArticleEntity>) query.list();
		Map map = new HashMap<Integer, ArticleEntity>();
		for (ArticleEntity articleEntity : list) {
			map.put(articleEntity.getId() + "", articleEntity);
		}
		return map;
	}

	public Map<String, ArticleEntity> getArticleList() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("select * from article order by id desc")
				.setResultTransformer(Transformers.aliasToBean(ArticleEntity.class));
		ArrayList<ArticleEntity> list = (ArrayList<ArticleEntity>) query.list();
		Map map = new HashMap<Integer, ArticleEntity>();
		for (ArticleEntity articleEntity : list) {
			map.put(articleEntity.getId() + "", articleEntity);
		}
		return map;
	}

	@Override
	public ArrayList<ArticleEntity> getArticleTop5(int adminId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session
				.createSQLQuery("select * from article where adminId=:adminId order by attention desc limit 5")
				.setResultTransformer(Transformers.aliasToBean(ArticleEntity.class));
		query.setParameter("adminId", adminId);
		ArrayList<ArticleEntity> list = (ArrayList<ArticleEntity>) query.list();
		return list;
	}

	@Override
	public void AddArticle(ArticleEntity articleEntity) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(articleEntity);
	}

	@Override
	public void deleteArticle(int id) {
		Session session = sessionFactory.getCurrentSession();
		ArticleEntity articleEntity = (ArticleEntity) session.get(ArticleEntity.class, id);
		session.delete(articleEntity);
	}

	@Override
	public void UpdateArticle(ArticleEntity articleEntity) {
		Session session = sessionFactory.getCurrentSession();
		ArticleEntity articleEntity1 = (ArticleEntity) session.get(ArticleEntity.class, articleEntity.getId());
		articleEntity1.setTitle(articleEntity.getTitle());
		articleEntity1.setContent(articleEntity.getContent());
		session.saveOrUpdate(articleEntity1);
	}

	@Override
	public ArticleEntity UpdateData(int id) {
		Session session = sessionFactory.getCurrentSession();
		ArticleEntity articleEntity = (ArticleEntity) session.get(ArticleEntity.class, id);
		return articleEntity;
	}

	@Override
	public ArrayList<ArticleEntity> getArticleTable() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("select * from article")
				.setResultTransformer(Transformers.aliasToBean(ArticleEntity.class));
		Iterator iterator = query.list().iterator();
		ArrayList<ArticleEntity> list = new ArrayList<ArticleEntity>();
		while (iterator.hasNext()) {
			ArticleEntity articleEntity = (ArticleEntity) iterator.next();
			list.add(articleEntity);
		}
		return list;
	}

	@Override
	public void addAttention(int id) {
		Session session = sessionFactory.getCurrentSession();
		ArticleEntity articleEntity = (ArticleEntity) session.get(ArticleEntity.class, id);
		if (articleEntity.getAttention() == null) {
			articleEntity.setAttention(1);
		}else {
			articleEntity.setAttention(articleEntity.getAttention()+1);
		}
		session.saveOrUpdate(articleEntity);
	}

}
