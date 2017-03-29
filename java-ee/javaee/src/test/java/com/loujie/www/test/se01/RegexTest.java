package com.loujie.www.test.se01;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class RegexTest {

	@Test
	public void regex() {
		String str = "/openClass/1/pac420161029476/now_view.html";
		String reg = "^/openClass(/(\\d))?/([\\w]{10,20})/now_view\\.html$";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(str);
		boolean rs = matcher.matches();
		System.err.println(rs);
		MatchResult mr = matcher.toMatchResult();
		System.err.println(mr.groupCount());

		System.err.println(mr.group(0));
	}

}
