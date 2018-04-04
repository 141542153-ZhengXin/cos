package com.cos.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.entity.UserAccessEntity;
import com.cos.service.DBAdminService;

import net.sf.json.JSONArray;

@Controller
@RequestMapping(value = "dbadmin")
public class DBAdminAction {

	@Autowired
	private DBAdminService dbAdminService;

	@RequestMapping(value = "/dbadminIndex")
	public String dbadminIndex(@ModelAttribute("user") UserAccessEntity userAccessEntity) {
		return "dbadminIndex";
	}

	@RequestMapping(value = "/StudentTable")
	@ResponseBody
	public JSONArray StudentTable() {
		JSONArray studentTable = dbAdminService.StudentTable();
		return studentTable;
	}

	@RequestMapping(value = "/ArticleTable")
	@ResponseBody
	public JSONArray ArticleTable() {
		JSONArray articleTable = dbAdminService.ArticleTable();
		return articleTable;
	}
	
	@RequestMapping(value = "/getPassWord")
	@ResponseBody
	public JSONArray getPassWord(int userId) {
		JSONArray getPassWord = dbAdminService.getPassWord(userId);
		return getPassWord;
	}
	

	@RequestMapping(value = "/updatePassWord")
	@ResponseBody
	public JSONArray updatePassWord(UserAccessEntity userAccessEntity) {
		JSONArray updatePassWord = dbAdminService.updatePassWord(userAccessEntity);
		return updatePassWord;
	}
}
