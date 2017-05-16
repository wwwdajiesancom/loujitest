package com.loujie.www.test.se01;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.junit.Test;

public class RegexTest {

	private Logger logger = Logger.getLogger(this.getClass());

	@Test
	public void regex() {
		String str = "/center.html#!center/course/video/0/pac6820161029214/ab%e7%95%99%e5%ad%a6%e8%af%be%e7%a8%8bcc";
		String reg = "^/center.html#!center/course/video/(\\d+)/(\\w+)/.+$";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(str);
		boolean rs = matcher.matches();
		System.err.println(rs);
	}

	@Test
	public void ll() {
		for (int i = 0; i < 1000000; i++) {
			String lString = UUID.randomUUID().toString().toLowerCase().replace("-", "");
			String SQL = "INSERT INTO app_config(confKey,confValue,`status`,created_at) VALUES('" + lString
					+ "','on',1,NOW());";
			logger.info(SQL);
		}
	}

}
