package com.cos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cos.cache.AccessControl;
import com.cos.cache.ArticleControl;
import com.cos.cache.StudentControl;

@Service
public class InitService {
	
	@Autowired
	private AccessControl accessControl;
	
	@Autowired
	private StudentControl studentControl;
	
	@Autowired
	private ArticleControl articleControl;
	
	
	public void InitAccessControl() {
		accessControl.InitUserAndAccess();
	}
	
	public void InitStudentControl() {
		studentControl.InitStudent();
	}
	
	public void InitArticleControl(int adminId) {
		articleControl.InitArticleList(adminId);
	}
	
	public void InitArticleControl() {
		articleControl.InitArticleList();
	}

}
