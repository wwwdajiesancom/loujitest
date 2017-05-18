package com.loujie.www.lucene;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

public class HelloWordDemo {

	final static String directoryPath = "D:\\logs";
	static Analyzer analyzer = new StandardAnalyzer();

	@Test
	public void createIndexTest() {
		try {
			createdIndex();

			System.err.println("创建索引成功.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void search() throws IOException, ParseException {
		String querySearchString = "shi";

		// 1.读取索引
		Directory directory = FSDirectory.getDirectory(directoryPath);
		// 2.查询
		IndexSearcher indexSearcher = new IndexSearcher(directory);

		QueryParser queryParser = new MultiFieldQueryParser(new String[]{"name", "desc"}, analyzer);
		Query query = queryParser.parse(querySearchString);

		TopDocs topDocs = indexSearcher.search(query, null, 10000);

		for (ScoreDoc scoreDocs : topDocs.scoreDocs) {
			Document itemD = indexSearcher.doc(scoreDocs.doc);
			System.err.println(itemD.toString());
		}

		System.err.println(topDocs.totalHits);

	}

	@Test
	public void analyzer() throws IOException {
		String sr = "大河向东流";
		Analyzer a1 = new StandardAnalyzer();
		TokenStream ts = a1.tokenStream("test", new StringReader(sr));
		for (Token token = new Token(); ((token = ts.next(token)) != null);) {
			System.err.println(token.toString());
		}
	}

	public static void createdIndex() throws IOException {
		// 1.定义所以目录
		Directory directory = FSDirectory.getDirectory(directoryPath);

		// 2.写索引
		IndexWriter indexWriter = new IndexWriter(directory, analyzer, MaxFieldLength.LIMITED);

		// 3.创建文档记录
		Document doc = createDoc();

		indexWriter.addDocument(doc);
		indexWriter.commit();
		// 优化
		indexWriter.optimize();
		indexWriter.close();

	}

	public static Document createDoc() {
		Document doc = new Document();
		doc.add(new Field("name", "loujie", Store.YES, Index.ANALYZED));
		doc.add(new Field("sex", "男", Store.YES, Index.NOT_ANALYZED));
		doc.add(new Field("birthday", "1989-02-15", Store.YES, Index.NOT_ANALYZED));
		doc.add(new Field("desc", "loujie shi yige hao ren ya，娄杰是一个好人，谢谢大家的支持", Store.YES, Index.ANALYZED));

		return doc;
	}

}
