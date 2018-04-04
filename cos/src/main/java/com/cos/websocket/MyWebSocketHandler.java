package com.cos.websocket;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.cos.cache.CoordinationCache;
import com.cos.service.ChatLogService;
import com.cos.utils.PropertiesUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Socket处理器
 */
@Component
public class MyWebSocketHandler implements WebSocketHandler {

	@Autowired
	private ChatLogService chatLogService;

	// 用于保存HttpSession与WebSocketSession的映射关系
	public static final Map<Integer, WebSocketSession> userSocketSessionMap;
	public static final Map<Integer, MessageQueue> coordinationCache;

	private static int readCoordinationCacheSize = PropertiesUtil.getInstance().readCoordinationCacheSize();

	static {
		userSocketSessionMap = new ConcurrentHashMap<Integer, WebSocketSession>();
		coordinationCache = CoordinationCache.getInstance().getCoordinationCache();
		/* relation = new ConcurrentHashMap<Integer, ArrayList<Integer>>(); */
	}

	/**
	 * 建立连接后,把登录用户的id写入WebSocketSession
	 */
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		Integer userId = (Integer) session.getAttributes().get("userId");
		craeteLogFile(userId);
		if (userSocketSessionMap.get(userId) == null) {
			userSocketSessionMap.put(userId, session);
			if (coordinationCache.get(userId) != null) {
				MessageQueue messageQueue = coordinationCache.get(userId);
				Message[] pop = new Message[messageQueue.size()];
				int i = 0;
				while (messageQueue.size() > 0) {
					pop[i++] = messageQueue.pop();
				}
				chatLogService.writeChatLog(userId, pop);
			} else {
				MessageQueue messageQueue = new MessageQueue(3);
				coordinationCache.put(userId, messageQueue);
			}
		}
	}

	/**
	 * 消息处理，在客户端通过Websocket API发送的消息会经过这里，然后进行相应的处理
	 */
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		if (message.getPayloadLength() == 0)
			return;
		Message msg = new Gson().fromJson(message.getPayload().toString(), Message.class);
		Message[] pop = null;
		if (coordinationCache.get(msg.getFrom()) != null) {
			MessageQueue messageQueue = coordinationCache.get(msg.getFrom());
			messageQueue.push(msg);
			if (messageQueue.size() >= readCoordinationCacheSize) {
				pop = new Message[messageQueue.size()];
				int i = 0;
				while (messageQueue.size() > 0) {
					pop[i++] = messageQueue.pop();
				}
				chatLogService.writeChatLog(msg.getFrom(), pop);
			}
		}
		if (coordinationCache.get(msg.getTo()) != null) {
			MessageQueue messageQueue = coordinationCache.get(msg.getTo());
			messageQueue.push(msg);
			if (messageQueue.size() >= readCoordinationCacheSize) {
				int i = 0;
				while (messageQueue.size() > 0) {
					pop[i++] = messageQueue.pop();
				}
				chatLogService.writeChatLog(msg.getTo(), pop);
			}
		}
		sendMessageToUser(msg.getTo(), new TextMessage(new GsonBuilder().serializeNulls().create().toJson(msg)));
	}

	/**
	 * 消息传输错误处理
	 */
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		if (session.isOpen()) {
			session.close();
		}
		Iterator<Entry<Integer, WebSocketSession>> it = userSocketSessionMap.entrySet().iterator();
		// 移除当前抛出异常用户的Socket会话
		while (it.hasNext()) {
			Entry<Integer, WebSocketSession> entry = it.next();
			if (entry.getValue().getId().equals(session.getId())) {
				userSocketSessionMap.remove(entry.getKey());
				System.out.println("Socket会话已经移除:用户ID" + entry.getKey());
				break;
			}
		}
	}

	/**
	 * 关闭连接后
	 */
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {

		System.out.println("Websocket:" + session.getId() + "已经关闭");
		Iterator<Entry<Integer, WebSocketSession>> it = userSocketSessionMap.entrySet().iterator();
		// 移除当前用户的Socket会话
		while (it.hasNext()) {
			Entry<Integer, WebSocketSession> entry = it.next();
			if (entry.getValue().getId().equals(session.getId())) {
				Integer id = entry.getKey();
				MessageQueue messageQueue = coordinationCache.get(id);
				Message[] pop = new Message[messageQueue.size()];
				int i = 0;
				while (messageQueue.size() > 0) {
					pop[i++] = messageQueue.pop();
				}
				chatLogService.writeChatLog(id, pop);
				userSocketSessionMap.remove(entry.getKey());
				System.out.println("Socket会话已经移除:用户ID" + entry.getKey());
				break;
			}
		}
	}

	public boolean supportsPartialMessages() {
		return false;
	}

	/**
	 * 给所有在线用户发送消息
	 * 
	 * @param message
	 * @throws IOException
	 */
	public void broadcast(final TextMessage message) throws IOException {
		Iterator<Entry<Integer, WebSocketSession>> it = userSocketSessionMap.entrySet().iterator();
		// 多线程群发
		while (it.hasNext()) {
			final Entry<Integer, WebSocketSession> entry = it.next();
			if (entry.getValue().isOpen()) {
				// entry.getValue().sendMessage(message);
				new Thread(new Runnable() {
					public void run() {
						try {
							if (entry.getValue().isOpen()) {
								entry.getValue().sendMessage(message);
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}).start();
			}
		}
	}

	/**
	 * 给某个用户发送消息
	 * 
	 * @param userName
	 * @param message
	 * @throws IOException
	 */
	public void sendMessageToUser(Integer userId, TextMessage message) throws IOException {
		WebSocketSession session = userSocketSessionMap.get(userId);
		if (session != null && session.isOpen()) {
			session.sendMessage(message);
		}
	}

	public void craeteLogFile(int id) throws IOException {
		String sid = id + "";
		String pathname = "D:\\" + sid + ".txt";

		File file = new File(pathname);
		if (file.exists()) {
			System.out.println("文件夹存在！");
		} else {
			file.createNewFile();// 创建文件
			System.out.println("文件不存在，创建文件成功！");

		}
	}

}
