package com.cos.utils;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtil {

	public static final PropertiesUtil INSTANCE = new PropertiesUtil();

	public static PropertiesUtil getInstance() {
		return INSTANCE;
	}

	public String[] readOrganization() {
		InputStreamReader in = null;
		String[] split = null;
		try {
			Properties properties = new Properties();
			in = new InputStreamReader(this.getClass().getResourceAsStream("/cos.properties"), "UTF-8");
			properties.load(in);
			String property = properties.getProperty("cos.organization");
			split = property.split(",");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("∂¡»°≈‰÷√–≈œ¢ ß∞‹£°");
		} finally {
			if (in != null) {
				try {
					in.close();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return split;
	}

	public int readCoordinationCacheSize() {
		InputStreamReader in = null;
		int property = 0;
		try {
			Properties properties = new Properties();
			in = new InputStreamReader(this.getClass().getResourceAsStream("/cos.properties"), "UTF-8");
			properties.load(in);
			property = Integer.parseInt(properties.getProperty("cos.coordinationCache.messageQueue.size"));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("∂¡»°≈‰÷√–≈œ¢ ß∞‹£°");
		} finally {
			if (in != null) {
				try {
					in.close();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return property;
	}
	
	public Map<String, String> readSMS() {
		InputStreamReader in = null;
		Map<String,String> smsProperties=new HashMap<String,String>();
		try {
			Properties properties = new Properties();
			in = new InputStreamReader(this.getClass().getResourceAsStream("/cos.properties"), "UTF-8");
			properties.load(in);
			String uid = properties.getProperty("cos.sms.uid");
			smsProperties.put("uid", uid);
			String key = properties.getProperty("cos.sms.key");
			smsProperties.put("key", key);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("∂¡»°≈‰÷√–≈œ¢ ß∞‹£°");
		} finally {
			if (in != null) {
				try {
					in.close();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return smsProperties;
	}
}
