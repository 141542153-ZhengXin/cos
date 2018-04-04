package com.cos.service;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cos.cache.ArticleControl;
import com.cos.dao.ArticleDaoI;
import com.cos.dao.StudentDaoI;
import com.cos.entity.ArticleEntity;
import com.cos.entity.StudentEntity;
import com.cos.entity.UserAccessEntity;

import net.sf.json.JSONArray;

@Service
public class StudentService {

	@Autowired
	private ArticleControl articleControl;

	@Autowired
	private StudentDaoI studentDao;

	@Autowired
	private ArticleDaoI articleDao;

	public JSONArray getArticle() {
		Collection<ArticleEntity> articleMap = articleControl.getArticleMap();
		JSONArray jsonArray = JSONArray.fromObject(articleMap);
		return jsonArray;
	}

	public JSONArray resumeUpdate(int id) {
		StudentEntity resume = studentDao.resumeUpdate(id);
		JSONArray jsonArray = JSONArray.fromObject(resume);
		return jsonArray;
	}

	public JSONArray sendResume(StudentEntity studentEntity) {
		String msg = null;
		String result = studentDao.sendResume(studentEntity);
		if ("success".equals(result)) {
			msg = "[{'msg':'success'}]";
		} else {
			msg = "[{'msg':'error'}]";
		}
		JSONArray jsonArray = JSONArray.fromObject(msg);
		return jsonArray;
	}

	public JSONArray updatePassWord(UserAccessEntity userAccessEntity) {
		String msg;
		try {
			studentDao.updatePassWord(userAccessEntity);
			msg = "[{'msg':'success'}]";
		} catch (Exception e) {
			msg = "[{'msg':'error'}]";
		}
		JSONArray jsonArray = JSONArray.fromObject(msg);
		return jsonArray;
	}

	public JSONArray getPassWord(int userId) {
		UserAccessEntity getPassWord = studentDao.getPassWord(userId);
		JSONArray jsonArray = JSONArray.fromObject(getPassWord);
		return jsonArray;
	}

	public JSONArray isResume(int id) {
		String msg = null;
		boolean flag = studentDao.isResume(id);
		if (flag == true) {
			msg = "[{'msg':'success'}]";
		} else {
			msg = "[{'msg':'error'}]";
		}
		JSONArray jsonArray = JSONArray.fromObject(msg);
		return jsonArray;
	}

	public JSONArray addAttention(int id) {
		String msg = null;
		try {
			articleDao.addAttention(id);
			msg = "[{'msg':'success'}]";
		}catch (Exception e) {
			msg = "[{'msg':'error'}]";
		}
		JSONArray jsonArray = JSONArray.fromObject(msg);
		return jsonArray;
	}
}
