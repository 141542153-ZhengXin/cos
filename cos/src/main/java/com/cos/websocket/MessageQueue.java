package com.cos.websocket;

public class MessageQueue {
	private Message[] messages;
	private int head;
	private int end;
	private int size;

	public MessageQueue(int size) {
		messages = new Message[size];
		this.head = 0;
		this.end = -1;
		this.size = 0;
	}

	public void push(Message msg) throws Exception {
		if (this.size > messages.length)
			throw new Exception("Queue is full!");
		if (end == messages.length - 1)
			end = -1;
		messages[++end] = msg;
		size++;
	}

	public Message pop() throws Exception {
		if (this.size == 0)
			throw new Exception("Queue is empty!");
		Message msg = messages[head++];
		if (head == messages.length)
			head = 0;
		size--;
		return msg;
	}

	public Message peek() throws Exception {
		if (this.size == 0)
			throw new Exception("Queue is empty!");
		return messages[head];
	}

	public int size() {
		return this.size;
	}

	public boolean isEmpty() {
		return (size == 0);
	}

	public boolean isFull() {
		return (size == messages.length);
	}
}