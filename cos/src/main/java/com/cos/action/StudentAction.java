package com.cos.action;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.entity.StudentEntity;
import com.cos.entity.UserAccessEntity;
import com.cos.service.AdminService;
import com.cos.service.ChatLogService;
import com.cos.service.InitService;
import com.cos.service.StudentService;
import com.cos.utils.PropertiesUtil;

import net.sf.json.JSONArray;

@Controller
@RequestMapping(value = "student")
public class StudentAction {

	@Autowired
	private InitService initService;

	@Autowired
	private StudentService studentService;

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private ChatLogService chatLogService;

	private int count = 0;

	private static String[] readOrganization = PropertiesUtil.getInstance().readOrganization();

	@RequestMapping(value = "/studentIndex")
	public String studentIndex(@ModelAttribute("user") UserAccessEntity userAccessEntity) {
		if (count++ == 0) {
			initService.InitArticleControl();
		}
		return "studentIndex";
	}

	@RequestMapping(value = "/studentResume")
	public String studentResume(UserAccessEntity userAccessEntity, Model model) {
		model.addAttribute("user", userAccessEntity);
		model.addAttribute("organization", readOrganization);
		return "studentResume";
	}

	@RequestMapping(value = "/studentQA")
	public String studentQA(UserAccessEntity userAccessEntity, Model model) {
		model.addAttribute("user", userAccessEntity);
		return "studentQA";
	}

	@RequestMapping(value = "/GetArticle")
	@ResponseBody
	public JSONArray GetArticle() {
		JSONArray article = studentService.getArticle();
		return article;
	}

	/*@RequestMapping(value = "/GetMessage")
	@ResponseBody
	public JSONArray GetMessage(int id) {
		JSONArray message = studentService.getMessage(id);
		return message;
	}*/

	@RequestMapping(value = "/ResumeUpdate")
	@ResponseBody
	public JSONArray ResumeUpdate(int id) {
		JSONArray resume = studentService.resumeUpdate(id);
		return resume;
	}

	@RequestMapping(value = "/isResume")
	@ResponseBody
	public JSONArray isResume(int id) {
		JSONArray resume = studentService.isResume(id);
		return resume;
	}

	@RequestMapping(value = "/SendResume")
	@ResponseBody
	public JSONArray SendResume(int id, String telephone, String organization, String hobby, String evaluation) {
		StudentEntity studentEntity = new StudentEntity();
		studentEntity.setId(id);
		studentEntity.setTelephone(new BigInteger(telephone));
		studentEntity.setOrganization(organization);
		studentEntity.setHobby(hobby);
		studentEntity.setEvaluation(evaluation);
		JSONArray sendResume = studentService.sendResume(studentEntity);
		return sendResume;
	}

	@RequestMapping(value = "/getPassWord")
	@ResponseBody
	public JSONArray getPassWord(int userId) {
		JSONArray getPassWord = studentService.getPassWord(userId);
		return getPassWord;
	}

	@RequestMapping(value = "/updatePassWord")
	@ResponseBody
	public JSONArray updatePassWord(UserAccessEntity userAccessEntity) {
		JSONArray updatePassWord = studentService.updatePassWord(userAccessEntity);
		return updatePassWord;
	}

	@RequestMapping(value = "/AddAttention")
	@ResponseBody
	public JSONArray AddAttention(int id) {
		JSONArray addAttention = studentService.addAttention(id);
		initService.InitArticleControl();
		return addAttention;
	}

	@RequestMapping(value = "/getAdmin")
	@ResponseBody
	public JSONArray getAdmin() {
		JSONArray getAdmin = adminService.getAdmin();
		return getAdmin;
	}

	@RequestMapping(value = "/HistoryMsgList")
	@ResponseBody
	public JSONArray HistoryMsgList(String mId, String id) throws Exception {
		JSONArray getMsgByAd = chatLogService.ReadChatLog(Integer.parseInt(mId), Integer.parseInt(id));
		return getMsgByAd;
	}
}
