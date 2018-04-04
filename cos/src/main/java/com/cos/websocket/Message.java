package com.cos.websocket;

public class Message {

	// 发送者
	public Integer from;
	// 接收者
	public Integer to;
	// 发送的文本
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
