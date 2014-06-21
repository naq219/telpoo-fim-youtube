package com.telpoo.frame.utils;

import java.util.Random;

public class VariablesSupport {
	public static String randomString(Integer length){
		if(length==null)length=10;
		char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
		    char c = chars[random.nextInt(chars.length)];
		    sb.append(c);
		}
		return sb.toString();
		
	}
}
