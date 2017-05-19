package com.loujie.www.lucene3_5_0;

import java.io.IOException;

import org.junit.Test;

public class LuceneHelloWord {

	private String indexPath = "D:\\lucene-3.5.0\\datasource\\index";
	/**
	 * 1.测试创建索引
	 */
	@Test
	public void createIndex1() {
		try {
			LuceneUtils.createIndex(indexPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.err.println("ok");
	}

	/**
	 * 查询索引
	 */
	@Test
	public void searchIndex2() {
		try {
			LuceneUtils.searchIndex(indexPath, "public");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deleteIndex3(){
		
	}
	
}
