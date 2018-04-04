package com.cos.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cos.cache.CoordinationCache;
import com.cos.cache.StudentMemory;
import com.cos.entity.StudentEntity;
import com.cos.utils.SwitchUtil;
import com.cos.websocket.Message;
import com.cos.websocket.MessageQueue;

import net.sf.json.JSONArray;

@Service
public class ChatLogService {

	public JSONArray ReadChatLog(int id1, int id2) throws Exception {

		Map<Integer, MessageQueue> coordinationCache = CoordinationCache.getInstance().getCoordinationCache();
		MessageQueue messageQueue = coordinationCache.get(id1);
		Message[] pop = new Message[messageQueue.size()];
		int i = 0;
		while (messageQueue.size() > 0) {
			pop[i++] = messageQueue.pop();
		}
		writeChatLog(id1, pop);

		String sid1 = id1 + "";
		String sid2 = id2 + "";

		String pathname = "D:\\" + sid1 + ".txt";
		File file = new File(pathname);
		InputStreamReader reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
		BufferedReader br = new BufferedReader(reader);
		String line = "";
		String start = sid1 + "-" + sid2;
		boolean flag = false;
		ArrayList<String> list = new ArrayList<String>();

		while ((line = br.readLine()) != null) {
			if (start.equals(line)) {
				line = br.readLine();
				flag = true;
			}
			if (flag) {
				if ("------------------------".equals(line)) {
					break;
				}
				int indexOf = line.indexOf(":");
				String id = line.substring(0, indexOf);
				String content;
				if (id.length() < 9) {
					content = "r&" + line.substring(indexOf + 1, line.length());
				} else {
					content = "s&" + line.substring(indexOf + 1, line.length());
				}
				list.add(content);
			}
		}

		Object[] array = list.toArray();
		SwitchUtil.reverseArray(array);
		JSONArray jsonArray = JSONArray.fromObject(array);
		return jsonArray;
	}

	public JSONArray GetChatLogByAd(int id) throws IOException {
		String sid = id + "";

		String pathname = "D:\\" + sid + ".txt";
		File file = new File(pathname);
		if (file.exists()) {
			InputStreamReader reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
			BufferedReader br = new BufferedReader(reader);
			String line = "";
			ArrayList<String> list = new ArrayList<String>();
			ArrayList<ArrayList<String>> list1 = new ArrayList<ArrayList<String>>();
			Map<Integer, StudentEntity> studentMap = StudentMemory.getInstance().getStudent();
			int lineNumber = 0;
			while ((line = br.readLine()) != null) {
				list.add(line);
				if ("------------------------".equals(line)) {
					String string = list.get(lineNumber - 1);
					int indexOf = string.indexOf(":");
					int id1 = Integer.parseInt(string.substring(0, indexOf));
					String content;
					if (studentMap.get(id1) != null) {
						ArrayList<String> list2 = new ArrayList<String>();
						list2.add(id1 + "");
						list2.add(studentMap.get(id1).getName());
						content = string.substring(indexOf + 1);
						list2.add(content);
						list1.add(list2);
					}
				}
				lineNumber++;
			}
			JSONArray jsonArray = JSONArray.fromObject(list1);
			return jsonArray;
		}else {
			return null;
		}
		
	}

	public void writeChatLog(int id, Message[] msg) throws IOException {
		String sid = id + "";
		File file = new File("D:\\" + id + ".txt");
		File tempFile = new File("D:\\temp.txt");

		FileInputStream fi = null;
		InputStreamReader reader = null;
		BufferedReader br = null;
		FileOutputStream fo = null;
		OutputStreamWriter writer = null;
		BufferedWriter bw = null;

		String line = "";
		for (int i = 0; i < msg.length; i++) {
			if (file.exists()) {
				fi = new FileInputStream(file);
				reader = new InputStreamReader(fi, "UTF-8");
				br = new BufferedReader(reader);
				fo = new FileOutputStream(tempFile, true);
				writer = new OutputStreamWriter(fo, "UTF-8");
				bw = new BufferedWriter(writer);

				String start1 = msg[i].getFrom() + "-" + msg[i].getTo();
				String start2 = msg[i].getTo() + "-" + msg[i].getFrom();
				boolean flag = false;
				boolean isExist = true;
				ArrayList<String> list = new ArrayList<String>();
				while ((line = br.readLine()) != null) {
					if (start1.equals(line) || start2.equals(line)) {
						flag = true;
						isExist = false;
					}
					if (flag) {
						if ("------------------------".equals(line)) {
							bw.write(msg[i].getFrom() + ":" + msg[i].getText() + "\r\n");
							bw.write(line + "\r\n");
							flag = false;
						} else {
							bw.write(line + "\r\n");
						}
						continue;
					}
					bw.write(line + "\r\n");
				}

				if (isExist) {
					Integer integer = new Integer(id);
					if (integer < 9) {
						bw.write(start2 + "\r\n");
					} else {
						bw.write(start1 + "\r\n");
					}
					bw.write(msg[i].getFrom() + ":" + msg[i].getText() + "\r\n");
					bw.write("------------------------" + "\r\n");
				}

				br.close();
				reader.close();
				fi.close();

				bw.close();
				writer.close();
				fo.flush();
				fo.close();
			}
			System.gc();
			boolean delete = file.delete();
			boolean renameTo = tempFile.renameTo(file);

		}
	}
}
