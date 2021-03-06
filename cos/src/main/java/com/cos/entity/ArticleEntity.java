package com.cos.entity;
// Generated 2018-3-18 15:29:44 by Hibernate Tools 3.2.2.GA

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * Article generated by hbm2java
 */
@Entity
@Table(name = "article", catalog = "cos")
@DynamicInsert(true)
@DynamicUpdate(true)
public class ArticleEntity implements java.io.Serializable {

	private Integer id;
	private String title;
	private String content;
	private Integer adminId;
	private String adminName;
	private String department;
	private Integer attention;

	public ArticleEntity() {
	}

	public ArticleEntity(String title, String content, Integer adminId, String adminName, String department,
			Integer attention) {
		this.title = title;
		this.content = content;
		this.adminId = adminId;
		this.adminName = adminName;
		this.department = department;
		this.attention = attention;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "title", length = 20)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "content")
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "adminId")
	public Integer getAdminId() {
		return this.adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	@Column(name = "adminName", length = 20)
	public String getAdminName() {
		return this.adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	@Column(name = "department", length = 20)
	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@Column(name = "attention")
	public Integer getAttention() {
		return this.attention;
	}

	public void setAttention(Integer attention) {
		this.attention = attention;
	}

}
