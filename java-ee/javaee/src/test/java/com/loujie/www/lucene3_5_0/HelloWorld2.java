package com.loujie.www.lucene3_5_0;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.junit.After;
import org.junit.Test;

public class HelloWorld2 {

	private String indexPath = "D:\\lucene-3.5.0\\datasource\\index2";

	@Test
	public void index1// 创建索引
	() {
		try {
			// 1.创建索引
			LuceneUtils2.createIndex(indexPath);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}
	}

	@Test
	public void indexInfo2// 输出索引信息
	() {
		try {
			// 输出索引信息
			LuceneUtils2.indexInfo(indexPath);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}
	}

	@Test
	public void indexPage3() {
		try {
			LuceneUtils2.indexPage(indexPath, 2, 3);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void analyzer() {
		LuceneUtils2.partcipleAnalyzer("大 家 好");
	}

	@After
	public void after() {
		LuceneUtils2.close();
		System.err.println("------------ok------------");
	}

}
