package com.cos.cache;

import java.util.HashMap;
import java.util.Map;

import com.cos.entity.ArticleEntity;

public class ArticleMemory {

	private static class ArticleMemoryHolder {
		private static final ArticleMemory INSTANCE = new ArticleMemory();
	}

	private ArticleMemory() {

	}

	public static final ArticleMemory getInstance() {
		return ArticleMemoryHolder.INSTANCE;
	}

	private Map<String, ArticleEntity> articleMap = new HashMap<String, ArticleEntity>();

	public void setArticleMap(Map<String, ArticleEntity> articleMap) {
		this.articleMap = articleMap;
	}

	public Map<String, ArticleEntity> getArticleMap() {
		return articleMap;
	}

}
