package com.cos.cache;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cos.dao.ArticleDaoI;
import com.cos.entity.ArticleEntity;

@Component
public class ArticleControl {

	@Autowired
	private ArticleDaoI articleDao;

	public void InitArticleList(int adminId) {
		Map<String, ArticleEntity> articleMap = articleDao.getArticleList(adminId);
		ArticleMemory.getInstance().setArticleMap(articleMap);
	}
	
	public void InitArticleList() {
		Map<String, ArticleEntity> articleMap = articleDao.getArticleList();
		ArticleMemory.getInstance().setArticleMap(articleMap);
	}

	public Collection<ArticleEntity> getArticleMap() {
		Map<String, ArticleEntity> articleMap = ArticleMemory.getInstance().getArticleMap();
		Iterator<ArticleEntity> iterator = articleMap.values().iterator();
		while (iterator.hasNext()) {
			ArticleEntity next = iterator.next();
			if (next.getContent() != null && next.getContent().contains("<br>")) {
				int length = next.getContent().length();
				String content = next.getContent();
				String contentBR = "";
				for (int i = 0; i < length - 1; i += 17) {
					if (i + 17 > length - 1) {
						break;
					}
					contentBR += (content.substring(i, i + 17) + "<br>");
				}
				next.setContent(contentBR);
				
			}
		}
		return articleMap.values();
	}
}
