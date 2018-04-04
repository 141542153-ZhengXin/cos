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
 * Socket������
 */
@Component
public class MyWebSocketHandler implements WebSocketHandler {

	@Autowired
	private ChatLogService chatLogService;

	// ���ڱ���HttpSession��WebSocketSession��ӳ���ϵ
	public static final Map<Integer, WebSocketSession> userSocketSessionMap;
	public static final Map<Integer, MessageQueue> coordinationCache;

	private static int readCoordinationCacheSize = PropertiesUtil.getInstance().readCoordinationCacheSize();

	static {
		userSocketSessionMap = new ConcurrentHashMap<Integer, WebSocketSession>();
		coordinationCache = CoordinationCache.getInstance().getCoordinationCache();
		/* relation = new ConcurrentHashMap<Integer, ArrayList<Integer>>(); */
	}

	/**
	 * �������Ӻ�,�ѵ�¼�û���idд��WebSocketSession
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
	 * ��Ϣ�����ڿͻ���ͨ��Websocket API���͵���Ϣ�ᾭ�����Ȼ�������Ӧ�Ĵ���
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
	 * ��Ϣ���������
	 */
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		if (session.isOpen()) {
			session.close();
		}
		Iterator<Entry<Integer, WebSocketSession>> it = userSocketSessionMap.entrySet().iterator();
		// �Ƴ���ǰ�׳��쳣�û���Socket�Ự
		while (it.hasNext()) {
			Entry<Integer, WebSocketSession> entry = it.next();
			if (entry.getValue().getId().equals(session.getId())) {
				userSocketSessionMap.remove(entry.getKey());
				System.out.println("Socket�Ự�Ѿ��Ƴ�:�û�ID" + entry.getKey());
				break;
			}
		}
	}

	/**
	 * �ر����Ӻ�
	 */
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {

		System.out.println("Websocket:" + session.getId() + "�Ѿ��ر�");
		Iterator<Entry<Integer, WebSocketSession>> it = userSocketSessionMap.entrySet().iterator();
		// �Ƴ���ǰ�û���Socket�Ự
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
				System.out.println("Socket�Ự�Ѿ��Ƴ�:�û�ID" + entry.getKey());
				break;
			}
		}
	}

	public boolean supportsPartialMessages() {
		return false;
	}

	/**
	 * �����������û�������Ϣ
	 * 
	 * @param message
	 * @throws IOException
	 */
	public void broadcast(final TextMessage message) throws IOException {
		Iterator<Entry<Integer, WebSocketSession>> it = userSocketSessionMap.entrySet().iterator();
		// ���߳�Ⱥ��
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
	 * ��ĳ���û�������Ϣ
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
			System.out.println("�ļ��д��ڣ�");
		} else {
			file.createNewFile();// �����ļ�
			System.out.println("�ļ������ڣ������ļ��ɹ���");

		}
	}

}
