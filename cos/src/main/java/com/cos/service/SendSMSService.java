package com.cos.service;

import java.math.BigInteger;
import java.util.Map;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.stereotype.Service;

import com.cos.cache.StudentMemory;
import com.cos.entity.StudentEntity;
import com.cos.utils.PropertiesUtil;

@Service
public class SendSMSService {

	public String sendSMS(int sId, String msg) throws Exception {

		Map<String, String> readSMS = PropertiesUtil.getInstance().readSMS();
		Map<Integer, StudentEntity> student = StudentMemory.getInstance().getStudent();
		String telephone = student.get(sId).getTelephone() + "";

		String[] split = msg.split(" ");
		msg = "";
		for (String string : split) {
			msg += string;
		}

		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod("http://gbk.api.smschinese.cn");
		post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=gbk");// 在头文件中设置转码
		NameValuePair[] data = { new NameValuePair("Uid", readSMS.get("uid")),
				new NameValuePair("Key", readSMS.get("key")), new NameValuePair("smsMob", telephone),
				new NameValuePair("smsText", msg) };
		post.setRequestBody(data);
		client.executeMethod(post);
		Header[] headers = post.getResponseHeaders();
		int statusCode = post.getStatusCode();
		System.out.println("statusCode:" + statusCode);
		String result = new String(post.getResponseBodyAsString().getBytes("gbk"));
		System.out.println(result); // 打印返回消息状态

		post.releaseConnection();
		return result;
	}
}
