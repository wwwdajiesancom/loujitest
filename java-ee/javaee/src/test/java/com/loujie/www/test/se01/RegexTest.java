package com.loujie.www.test.se01;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class RegexTest {

	@Test
	public void regex() {
		String str = "/center.html#!center/course/video/0/pac6820161029214/ab%e7%95%99%e5%ad%a6%e8%af%be%e7%a8%8bcc";
		String reg = "^/center.html#!center/course/video/(\\d+)/(\\w+)/.+$";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(str);
		boolean rs = matcher.matches();
		System.err.println(rs);
		
	}

}
