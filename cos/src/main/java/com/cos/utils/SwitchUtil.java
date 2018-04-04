package com.cos.utils;

import java.util.ArrayList;

public class SwitchUtil {
	public static void reverseArray(Object[] Array) {
		ArrayList array_list = new ArrayList();
		for (int i = 0; i < Array.length; i++) {
			array_list.add(Array[Array.length - i - 1]);
		}
		Array = array_list.toArray(Array);
	}
}
