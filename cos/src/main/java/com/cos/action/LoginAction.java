package com.cos.action;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cos.cache.AccessMemory;
import com.cos.entity.UserAccessEntity;
import com.cos.service.InitService;

@Controller
@RequestMapping(value = "user")
public class LoginAction {

	@Autowired
	private InitService initService;

	@RequestMapping(value = "/login")
	public String login(UserAccessEntity userAccessEntity, RedirectAttributes model, HttpSession session) {
		initService.InitAccessControl();
		UserAccessEntity userAccessEntityDB = AccessMemory.getInstance().getUserAndAccess()
				.get(userAccessEntity.getUserId());
		session.setAttribute("userId", userAccessEntityDB.getUserId());
		session.setAttribute("userName", userAccessEntityDB.getUserName());
		Boolean flag = userAccessEntityDB != null
				&& userAccessEntity.getPassWord().equals(userAccessEntityDB.getPassWord());
		if (!flag) {
			return "redirect:/login.jsp";
		}
		int permission = userAccessEntityDB.getPermission();
		model.addFlashAttribute("user", userAccessEntityDB);
		if (flag && permission == 0) {
			return "redirect:../student/studentIndex";
		} else if (flag && permission == 1) {
			return "redirect:../admin/adminIndex";
		} else {
			return "redirect:../dbadmin/dbadminIndex";
		}
	}
}
