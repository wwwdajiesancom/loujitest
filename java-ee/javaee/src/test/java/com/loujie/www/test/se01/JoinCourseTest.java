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
		List<String> coursewareUniqIdList = this.getCoursewareUniqIdList(coursewareNameList.size(), "20170507");
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
		list.add("20170424-58fdb6ecbb936.mp4");
		list.add("20170424-58fdb6ecbb9c9.mp4");
		list.add("20170424-58fdb6ecbbab0.mp4");
		list.add("20170427-5901584bf262a.mp4");
		return list;
	}

	/**
	 * 获取视频的长度,需要用正则替换0[1-9]==$1
	 * 
	 * @return
	 */
	public List<Integer> getTotalLengthList() {
		List<Integer> list = new ArrayList<>();
		list.add(60 * 9 + 31);
		list.add(60 * 9 + 16);
		list.add(60 * 9 + 20);
		list.add(60 * 8 + 26);
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
		list.add("第1集 巴拉望美景探秘-中美主持掐架地下河");
		list.add("第2集 巴拉望美食大战一触即发-毛蛋吓傻老美主持");
		list.add("第3集 巧克力山迷雾探险-看呆萌眼镜猴逍遥薄荷岛");
		list.add("第4集 马尼拉酒店惊魂记-市中市时光穿梭四百年");
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
