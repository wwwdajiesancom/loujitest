package com.loujie.www.test.se01;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Test;

import com.loujie.www.util.SqliteUtil;

public class JoinCourseTest {

	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	@Test
	public void printCourseChapter() {

		// String insertSQL = "INSERT INTO course_chapter
		// (uniq_id,foreign_id,`name`,sort,`status`,created_at)VALUES();";

	}

	@Test
	public void printCourseware() {
		// 1.获取一些固定数据
		// 1.1课件名称
		List<String> coursewareNameList = this.getCoursewareNamelist();
		// 1.2课件唯一Id
		List<String> coursewareUniqIdList = this.getCoursewareUniqIdList(coursewareNameList.size(), "20170422");
		// 1.3获取课件时间长度
		List<Integer> totalLengthList = this.getTotalLengthList();
		// 1.4获取课件的视频
		List<String> videoNameList = this.getVideoNameList();
		// 2.生成相关SQL语句
		// 2.1生成课件插入SQL
		String insertCoursewareSQL = "INSERT INTO courseware(uniq_id,`name`,name_us,host_id,difficulty,view_type,price,source,is_trans,is_cdn_trans,is_check,total_length,`status`,created_at)VALUES (@coursewareUniqId,@courseName,'',0,3,2,0.00,'录播',1,1,1,@totalLength,1,NOW());";
		for (int i = 0; i < coursewareNameList.size(); i++) {
			System.err.println(insertCoursewareSQL.replace("@coursewareUniqId", "'" + coursewareUniqIdList.get(i) + "'")
					.replace("@courseName", "'" + coursewareNameList.get(i) + "'")
					.replace("@totalLength", "" + totalLengthList.get(i)));
		}
		System.err.println(
				"-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		// 2.2生成Video插入SQL
		String insertVideoFHDSQL = "INSERT INTO video(foreign_id,url,quality,`type`,mp4_url,m3u8_url,m3u8_url_us,created_at)VALUES(@coursewareUniqId,@URL_FHD,'FHD',0,@URL_FHD,@URL_FHD,@URL_FHD,NOW());";
		String insertVideoHDSQL = "INSERT INTO video(foreign_id,url,quality,`type`,mp4_url,m3u8_url,m3u8_url_us,created_at)VALUES(@coursewareUniqId,@URL_HD,'HD',0,@URL_HD,@URL_HD,@URL_HD,NOW());";
		String insertVideoSDSQL = "INSERT INTO video(foreign_id,url,quality,`type`,mp4_url,m3u8_url,m3u8_url_us,created_at)VALUES(@coursewareUniqId,@URL_SD,'SD',0,@URL_SD,@URL_SD,@URL_SD,NOW());";
		String insertVideoLDSQL = "INSERT INTO video(foreign_id,url,quality,`type`,mp4_url,m3u8_url,m3u8_url_us,created_at)VALUES(@coursewareUniqId,@URL_LD,'LD',0,@URL_LD,@URL_LD,@URL_LD,NOW());";
		for (int i = 0; i < coursewareNameList.size(); i++) {
			Map<String, Object> videoUrlMap = SqliteUtil.query(videoNameList.get(i));
			if (videoUrlMap == null || videoUrlMap.size() == 0) {
				System.err.println("获取视频错误---------------------------------");
				return;
			}
			System.err.println(insertVideoFHDSQL.replace("@coursewareUniqId", "'" + coursewareUniqIdList.get(i) + "'")
					.replaceAll("@URL_FHD", "'" + videoUrlMap.get("fhd").toString() + "'"));
			System.err.println(insertVideoHDSQL.replace("@coursewareUniqId", "'" + coursewareUniqIdList.get(i) + "'")
					.replaceAll("@URL_HD", "'" + videoUrlMap.get("hd").toString() + "'"));
			System.err.println(insertVideoSDSQL.replace("@coursewareUniqId", "'" + coursewareUniqIdList.get(i) + "'")
					.replaceAll("@URL_SD", "'" + videoUrlMap.get("sd").toString() + "'"));
			System.err.println(insertVideoLDSQL.replace("@coursewareUniqId", "'" + coursewareUniqIdList.get(i) + "'")
					.replaceAll("@URL_LD", "'" + videoUrlMap.get("ld").toString() + "'"));
		}
		System.err.println(
				"-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		// 2.3生成关系插入SQL
		String insertRelationSQL = "INSERT INTO course_courseware_r(course_id,courseware_id,sort,`status`,created_at)VALUES(@courseUniqId,@coursewareUniqId,@sort,1,NOW());";
		for (int i = 0; i < coursewareNameList.size(); i++) {
			System.err.println(insertRelationSQL.replace("@coursewareUniqId", "'" + coursewareUniqIdList.get(i) + "'")
					.replace("@sort", i + 1 + ""));
		}
	}

	/**
	 * 获取视频名称列表
	 * 
	 * @return
	 */
	public List<String> getVideoNameList() {
		List<String> list = new ArrayList<>();
		list.add("20170413-58eef2e6e8509.mp4");
		list.add("20170413-58eef2e6e9e9f.mp4");
		list.add("20170413-58eef2e6ea6d2.mp4");
		list.add("20170413-58eef2e6eb06e.mp4");
		list.add("20170413-58eef2e6eb792.mp4");
		list.add("20170413-58eef2e6ec6e3.mp4");
		list.add("20170413-58eef2e6ecc61.mp4");
		list.add("20170413-58eef2e6edb3f.mp4");
		list.add("20170413-58eef2e6ee271.mp4");
		list.add("20170413-58eef2e6e3a93.mp4");
		list.add("20170413-58eef2e6e3bb3.mp4");
		list.add("20170413-58eef2e6e4215.mp4");
		list.add("20170413-58eef2e6e4bb8.mp4");
		list.add("20170413-58eef2e6e5e78.mp4");
		list.add("20170413-58eef2e6e6550.mp4");
		list.add("20170413-58eef2e6e6a8a.mp4");
		list.add("20170413-58eef2e6e6fe6.mp4");
		list.add("20170413-58eef2e6e74f4.mp4");
		list.add("20170413-58eef2e6e7b1a.mp4");
		list.add("20170413-58eef2e6e8cc8.mp4");
		return list;
	}

	/**
	 * 获取视频的长度,需要用正则替换0[1-9]==$1
	 * 
	 * @return
	 */
	public List<Integer> getTotalLengthList() {
		List<Integer> list = new ArrayList<>();
		list.add(60 * 27 + 7);
		list.add(60 * 30 + 44);
		list.add(60 * 27 + 47);
		list.add(60 * 21 + 25);
		list.add(60 * 23 + 24);
		list.add(60 * 32 + 54);
		list.add(60 * 29 + 20);
		list.add(60 * 29 + 26);
		list.add(60 * 32 + 34);
		list.add(60 * 26 + 2);
		list.add(60 * 29 + 25);
		list.add(60 * 23 + 3);
		list.add(60 * 24 + 16);
		list.add(60 * 32 + 14);
		list.add(60 * 26 + 2);
		list.add(60 * 25 + 53);
		list.add(60 * 20 + 55);
		list.add(60 * 26 + 40);
		list.add(60 * 25 + 37);
		list.add(60 * 25 + 1);
		return list;
	}

	/**
	 * 获取课件唯一Id
	 * 
	 * @param length
	 *            获取多少个
	 * @param yyyyMMdd
	 *            中间拼接的部分字符
	 * @return
	 */
	public List<String> getCoursewareUniqIdList(int length, String yyyyMMdd) {
		List<String> idList = new ArrayList<>();
		for (int i = 0; i < length;) {
			String uniqId = "pacw" + getRandomStr(2) + yyyyMMdd + getRandomStr(2);
			if (!idList.contains(uniqId)) {
				idList.add(uniqId);
				i++;
			}
		}
		return idList;
	}

	/**
	 * 获取课件的名称
	 * 
	 * @return
	 */
	public List<String> getCoursewareNamelist() {
		List<String> list = new ArrayList<>();
		list.add("语音语调（1）");
		list.add("语音语调（2）");
		list.add("语音语调（3）");
		list.add("语音语调（4）");
		list.add("语音语调（5）");
		list.add("语音语调（6）");
		list.add("语音语调（7）");
		list.add("语音语调（8）");
		list.add("语音语调（9）");
		list.add("语音语调（10）");
		list.add("语音语调（11）");
		list.add("语音语调（12）");
		list.add("语音语调（13）");
		list.add("语音语调（14）");
		list.add("语音语调（15）");
		list.add("语音语调（16）");
		list.add("语音语调（17）");
		list.add("语音语调（18）");
		list.add("语音语调（19）");
		list.add("语音语调（20）");
		return list;
	}

	/**
	 * 获取随机字符串
	 * 
	 * @param randomLength
	 *            字符串长度
	 * @return
	 */
	public static String getRandomStr(int randomLength) {
		String str_ = "qazxswedcvfrtgbnhyujmkiolp0987654321";
		StringBuilder stringBuilder = new StringBuilder();
		int str_len = str_.length();
		Random random = new Random();
		for (int i = 0; i < randomLength; i++) {
			stringBuilder.append(str_.charAt(random.nextInt(str_len)));
		}
		return stringBuilder.toString();
	}

	/**
	 * 转换成Date
	 * 
	 * @param dateStr
	 * @param format
	 * @return
	 */
	public static Date parseDate(String dateStr, String format) {
		try {
			return new SimpleDateFormat(format).parse(dateStr);
		} catch (ParseException e) {
			dateStr = new SimpleDateFormat("yyyy-MM-dd").format(dateStr);
			try {
				return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
			} catch (ParseException e1) {
				return Calendar.getInstance().getTime();
			}
		}
	}
}
