package com.cos.websocket;

public class Message {

	// ������
	public Integer from;
	// ������
	public Integer to;
	// ���͵��ı�
	public String text;

	public Integer getFrom() {
		return from;
	}

	public void setFrom(Integer from) {
		this.from = from;
	}

	public Integer getTo() {
		return to;
	}

	public void setTo(Integer to) {
		this.to = to;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
