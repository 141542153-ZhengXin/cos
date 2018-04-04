package com.cos.dao;

import java.util.ArrayList;
import java.util.Map;

import com.cos.entity.ArticleEntity;

public interface ArticleDaoI {
	
	public abstract Map<String, ArticleEntity> getArticleList(int adminId);

	public abstract ArrayList<ArticleEntity> getArticleTop5(int adminId);
	
	public abstract void AddArticle(ArticleEntity articleEntity);

	public abstract void deleteArticle(int id);

	public abstract void UpdateArticle(ArticleEntity articleEntity);

	public abstract ArticleEntity UpdateData(int id);

	public abstract Map<String, ArticleEntity> getArticleList();

	public abstract ArrayList<ArticleEntity> getArticleTable();

	public abstract void addAttention(int id);

}
