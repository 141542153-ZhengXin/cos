package com.cos.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cos.cache.AccessMemory;
import com.cos.cache.ArticleControl;
import com.cos.cache.StudentControl;
import com.cos.dao.AdminDaoI;
import com.cos.dao.ArticleDaoI;
import com.cos.entity.AdminEntity;
import com.cos.entity.ArticleEntity;
import com.cos.entity.StudentEntity;
import com.cos.entity.UserAccessEntity;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class AdminService {

	@Autowired
	private AdminDaoI adminDao;

	@Autowired
	private ArticleDaoI articleDao;

	@Autowired
	private StudentControl studentControl;

	@Autowired
	private ArticleControl articleControl;

	@Autowired
	private SendSMSService sendSMSService;

	public JSONArray ResumeStatistics(int adminId) {
		String organization = adminDao.getAdmin(adminId).getOrganization();
		String[][] resumeStatistics = studentControl.ResumeStatistics(organization);
		JSONArray jsonArray = JSONArray.fromObject(resumeStatistics);
		return jsonArray;
	}

	public JSONArray ResumeProcessTable(int pageIndex, int pageSize, int flag) {
		ArrayList resumeProcessTable = studentControl.ResumeProcessTable(pageIndex, pageSize, flag);
		JSONArray jsonArray = JSONArray.fromObject(resumeProcessTable);
		return jsonArray;
	}

	public JSONObject CheckResume(int sId) {
		StudentEntity studentEntity = studentControl.CheckResume(sId);
		JSONObject jsonObject = JSONObject.fromObject(studentEntity);
		return jsonObject;
	}

	public String MsgModelProcess(int sId) {
		StudentEntity studentEntity = studentControl.MsgModelProcess(sId);
		String msg = MsgModel(studentEntity.getName(), studentEntity.getOrganization());
		return msg;
	}

	public String MsgModel(String name, String organization) {
		String msg = "恭喜" + name + "同学，通过我们" + organization + "的简历筛选，请同学于“ ”到“ ”参加我们的见面会";
		return msg;
	}

	public JSONArray ResumeProcess(int sId, int isPass, String message, String sendSMS) {
		String msg = null;
		try {
			if (sendSMS.equals("1")) {
				StudentEntity studentEntity = adminDao.PassProcess(sId, isPass);
				studentControl.UpdateStudentMemory(studentEntity);
				msg = "[{'msg':'success'}]";
			} else {
				msg = "[{'msg':'error'}]";
			}
		} catch (Exception e) {
			msg = "[{'msg':'error'}]";
		}
		JSONArray jsonArray = JSONArray.fromObject(msg);
		return jsonArray;
	}

	public JSONArray ResumeProcessTableById(int pageIndex, int pageSize, int sId) {
		ArrayList resumeProcessTable = studentControl.ResumeProcessTableById(pageIndex, pageSize, sId);
		JSONArray jsonArray = JSONArray.fromObject(resumeProcessTable);
		return jsonArray;
	}

	public JSONArray AreaStatistics() {
		ArrayList areaStatistics = adminDao.getAreaStatistics();
		JSONArray jsonArray = JSONArray.fromObject(areaStatistics);
		return jsonArray;
	}

	public JSONArray MajorStatistics() {
		ArrayList majorStatistics = adminDao.getMajorStatistics();
		JSONArray jsonArray = JSONArray.fromObject(majorStatistics);
		return jsonArray;
	}

	public JSONArray getArticle() {
		Collection<ArticleEntity> articleMap = articleControl.getArticleMap();
		JSONArray jsonArray = JSONArray.fromObject(articleMap);
		return jsonArray;
	}

	public JSONArray AddArticle(ArticleEntity articleEntity) {
		String msg;
		try {
			Map<Integer, UserAccessEntity> userAndAccess = AccessMemory.getInstance().getUserAndAccess();
			UserAccessEntity userAccessEntity = userAndAccess.get(articleEntity.getAdminId());
			AdminEntity admin = adminDao.getAdmin(articleEntity.getAdminId());
			articleEntity.setAdminName(userAccessEntity.getUserName());
			articleEntity.setDepartment(admin.getDepartments());
			articleDao.AddArticle(articleEntity);
			msg = "[{'msg':'success'}]";
		} catch (Exception e) {
			msg = "[{'msg':'error'}]";
		}
		JSONArray jsonArray = JSONArray.fromObject(msg);
		return jsonArray;
	}

	public JSONArray UpdateData(int id) {
		ArticleEntity updateData = articleDao.UpdateData(id);
		JSONArray jsonArray = JSONArray.fromObject(updateData);
		return jsonArray;
	}

	public JSONArray UpdateArticle(ArticleEntity articleEntity) {
		String msg;
		try {
			articleDao.UpdateArticle(articleEntity);
			msg = "[{'msg':'success'}]";
		} catch (Exception e) {
			msg = "[{'msg':'error'}]";
		}
		JSONArray jsonArray = JSONArray.fromObject(msg);
		return jsonArray;
	}

	public JSONArray GetArticleTop5(int adminId) {
		ArrayList<ArticleEntity> articleTop5 = articleDao.getArticleTop5(adminId);
		JSONArray jsonArray = JSONArray.fromObject(articleTop5);
		return jsonArray;
	}

	public JSONArray DeleteArticle(int id) {
		String msg;
		try {
			articleDao.deleteArticle(id);
			msg = "[{'msg':'success'}]";
		} catch (Exception e) {
			msg = "[{'msg':'error'}]";
		}
		JSONArray jsonArray = JSONArray.fromObject(msg);
		return jsonArray;
	}

	public JSONArray updatePassWord(UserAccessEntity userAccessEntity) {
		String msg;
		try {
			adminDao.updatePassWord(userAccessEntity);
			msg = "[{'msg':'success'}]";
		} catch (Exception e) {
			msg = "[{'msg':'error'}]";
		}
		JSONArray jsonArray = JSONArray.fromObject(msg);
		return jsonArray;
	}

	public JSONArray getPassWord(int userId) {
		UserAccessEntity getPassWord = adminDao.getPassWord(userId);
		JSONArray jsonArray = JSONArray.fromObject(getPassWord);
		return jsonArray;
	}

	public JSONArray getAdmin() {
		List admin = adminDao.getAdmin();
		JSONArray jsonArray = JSONArray.fromObject(admin);
		return jsonArray;
	}
}
