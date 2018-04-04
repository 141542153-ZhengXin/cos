package com.cos.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cos.dao.ArticleDaoI;
import com.cos.dao.DBAdminDaoI;
import com.cos.dao.StudentDaoI;
import com.cos.entity.ArticleEntity;
import com.cos.entity.StudentEntity;
import com.cos.entity.UserAccessEntity;

import net.sf.json.JSONArray;

@Service
public class DBAdminService {

	@Autowired
	private StudentDaoI studentDao;
	
	@Autowired
	private ArticleDaoI articleDao;
	
	@Autowired
	private DBAdminDaoI dbAdminDao;

	public JSONArray StudentTable() {
		ArrayList<StudentEntity> studentTable = studentDao.getStudentTable();
		JSONArray jsonArray = JSONArray.fromObject(studentTable);
		return jsonArray;
	}
	
	public JSONArray ArticleTable() {
		ArrayList<ArticleEntity> articleTable = articleDao.getArticleTable();
		JSONArray jsonArray = JSONArray.fromObject(articleTable);
		return jsonArray;
	}

	public JSONArray updatePassWord(UserAccessEntity userAccessEntity) {
		String msg;
		try {
			dbAdminDao.updatePassWord(userAccessEntity);
			msg = "[{'msg':'success'}]";
		} catch (Exception e) {
			msg = "[{'msg':'error'}]";
		}
		JSONArray jsonArray = JSONArray.fromObject(msg);
		return jsonArray;
	}

	public JSONArray getPassWord(int userId) {
		UserAccessEntity getPassWord = dbAdminDao.getPassWord(userId);
		JSONArray jsonArray = JSONArray.fromObject(getPassWord);
		return jsonArray;
	}

}
