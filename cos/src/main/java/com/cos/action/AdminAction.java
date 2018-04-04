package com.cos.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.entity.ArticleEntity;
import com.cos.entity.UserAccessEntity;
import com.cos.service.AdminService;
import com.cos.service.ChatLogService;
import com.cos.service.InitService;
import com.cos.service.SendSMSService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "admin")
public class AdminAction {

	@Autowired
	private InitService initService;

	@Autowired
	private AdminService adminService;

	@Autowired
	private ChatLogService chatLogService;

	@Autowired
	private SendSMSService sendSMSService;

	private int count = 0;

	@RequestMapping(value = "/adminIndex")
	public String adminIndex(@ModelAttribute("user") UserAccessEntity userAccessEntity) {
		if (count++ == 0) {
			initService.InitStudentControl();
			initService.InitArticleControl(userAccessEntity.getUserId());
		}
		return "adminIndex";
	}

	@RequestMapping(value = "/adminContent")
	public String adminContent(UserAccessEntity userAccessEntity, Model model) {
		model.addAttribute("user", userAccessEntity);
		return "adminContent";
	}

	@RequestMapping(value = "/adminResume")
	public String adminResume(UserAccessEntity userAccessEntity, Model model) {
		model.addAttribute("user", userAccessEntity);
		return "adminResume";
	}

	@RequestMapping(value = "/ResumeStatistics")
	@ResponseBody
	public JSONArray ResumeStatistics(UserAccessEntity userAccessEntity) {
		int adminId = userAccessEntity.getUserId();
		JSONArray resumeStatistics = adminService.ResumeStatistics(adminId);
		return resumeStatistics;
	}

	@RequestMapping(value = "/ResumeProcessTable")
	@ResponseBody
	public JSONArray ResumeProcessTable(int page, int flag) {
		int pageSize = 10;
		JSONArray resumeProcessTable = adminService.ResumeProcessTable(page, pageSize, flag);
		return resumeProcessTable;
	}

	@RequestMapping(value = "/CheckResume")
	@ResponseBody
	public JSONObject CheckResume(int sId) {
		JSONObject jsonObject = adminService.CheckResume(sId);
		return jsonObject;
	}

	@RequestMapping(value = "/MsgModelProcess", produces = { "text/html;charset=UTF-8;" })
	@ResponseBody
	public String MsgModelProcess(int sId, int isPass) {
		if (isPass == 1) {
			String msg = adminService.MsgModelProcess(sId);
			return msg;
		}
		return "";
	}

	@RequestMapping(value = "/ResumeProcess")
	@ResponseBody
	public JSONArray ResumeProcess(int sId, int isPass, String msg) throws Exception {
		String sendSMS = (String) sendSMSService.sendSMS(sId, msg);
		JSONArray resumeProcess = adminService.ResumeProcess(sId, isPass, msg, sendSMS);
		return resumeProcess;
	}

	@RequestMapping(value = "/ResumeProcessTableById")
	@ResponseBody
	public JSONArray ResumeProcessTableById(int page, int sId) {
		int pageSize = 10;
		JSONArray resumeProcessTable = adminService.ResumeProcessTableById(page, pageSize, sId);
		return resumeProcessTable;
	}

	@RequestMapping(value = "/AreaStatistics")
	@ResponseBody
	public JSONArray AreaStatistics() {
		JSONArray areaStatistics = adminService.AreaStatistics();
		return areaStatistics;
	}

	@RequestMapping(value = "/MajorStatistics")
	@ResponseBody
	public JSONArray MajorStatistics() {
		JSONArray majorStatistics = adminService.MajorStatistics();
		return majorStatistics;
	}

	@RequestMapping(value = "/GetArticle")
	@ResponseBody
	public JSONArray GetArticle() {
		JSONArray article = adminService.getArticle();
		return article;
	}

	@RequestMapping(value = "/GetArticleTop5")
	@ResponseBody
	public JSONArray GetArticleTop5(int adminId) {
		JSONArray article = adminService.GetArticleTop5(adminId);
		return article;
	}

	@RequestMapping(value = "/AddArticle")
	@ResponseBody
	public JSONArray AddArticle(ArticleEntity articleEntity) {
		JSONArray addArticle = adminService.AddArticle(articleEntity);
		initService.InitArticleControl(articleEntity.getAdminId());
		return addArticle;
	}

	@RequestMapping(value = "/UpdateData")
	@ResponseBody
	public JSONArray UpdateData(int id) {
		JSONArray updateData = adminService.UpdateData(id);
		return updateData;
	}

	@RequestMapping(value = "/UpdateArticle")
	@ResponseBody
	public JSONArray UpdateArticle(ArticleEntity articleEntity) {
		JSONArray updateArticle = adminService.UpdateArticle(articleEntity);
		initService.InitArticleControl(articleEntity.getAdminId());
		return updateArticle;
	}

	@RequestMapping(value = "/DeleteArticle")
	@ResponseBody
	public JSONArray DeleteArticle(int id, int adminId) {
		JSONArray deleteArticle = adminService.DeleteArticle(id);
		initService.InitArticleControl(adminId);
		return deleteArticle;
	}

	@RequestMapping(value = "/GetMsgByAd")
	@ResponseBody
	public JSONArray GetMsgByAd(int id) throws Exception {
		JSONArray getMsgByAd = chatLogService.GetChatLogByAd(id);
		return getMsgByAd;
	}

	@RequestMapping(value = "/HistoryMsgList")
	@ResponseBody
	public JSONArray HistoryMsgList(String mId, String id) throws Exception {
		JSONArray getMsgByAd = chatLogService.ReadChatLog(Integer.parseInt(mId), Integer.parseInt(id));
		return getMsgByAd;
	}

	@RequestMapping(value = "/getPassWord")
	@ResponseBody
	public JSONArray getPassWord(int userId) {
		JSONArray getPassWord = adminService.getPassWord(userId);
		return getPassWord;
	}

	@RequestMapping(value = "/updatePassWord")
	@ResponseBody
	public JSONArray updatePassWord(UserAccessEntity userAccessEntity) {
		JSONArray updatePassWord = adminService.updatePassWord(userAccessEntity);
		return updatePassWord;
	}
}
