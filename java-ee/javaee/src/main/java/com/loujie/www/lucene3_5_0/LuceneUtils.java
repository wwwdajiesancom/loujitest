package com.loujie.www.lucene3_5_0;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * Lucene的基础操作
 * 
 * @author loujie
 *
 */
public class LuceneUtils {

	private static IndexSearcher indexSearcher;

	/**
	 * 创建索引
	 * 
	 * @param indexPath
	 * @throws IOException
	 */
	public static void createIndex(String indexPath) throws IOException {
		// 1.索引位置
		Directory directory = FSDirectory.open(new File(indexPath));
		// 2.利用IndexWriter写索引
		// 2.1创建解析器,给个标准的
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_35);
		// 2.2
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_35, analyzer);
		IndexWriter indexWriter = null;
		try {
			indexWriter = new IndexWriter(directory, iwc);
			// 3.创建数据信息
			List<Document> documents = createDocuments();
			// 4.用IndexWriter添加document，并保存
			indexWriter.addDocuments(documents);
		} catch (Exception e) {
		} finally {
			if (indexWriter != null) {
				// 必须关闭
				indexWriter.close();
			}
		}
	}

	/**
	 * 查询索引
	 * 
	 * @param indexPath
	 * @param queryString
	 * @return
	 * @throws IOException
	 */
	public static Object searchIndex(String indexPath, String queryString) throws IOException {
		// 1.用IndexReader读取已经存在的索引，并声明IndexSearch
		Directory directory = FSDirectory.open(new File(indexPath));
		IndexReader indexReader = null;
		try {
			indexReader = IndexReader.open(directory);
			indexSearcher = new IndexSearcher(indexReader);
			// 2.声明Query对象
			Query query = null;
			// Term t = new Term("content", queryString);
			// query = new TermQuery(t);
			// 2.也可以通过QueryParse来创建Query
			QueryParser queryParser = new QueryParser(Version.LUCENE_35, "content", new StandardAnalyzer(Version.LUCENE_35));
			query = queryParser.parse(queryString);
			// 3.用IndexSearcher查询
			TopDocs topDocs = indexSearcher.search(query, 10);
			// 4.处理返回结果,

			System.err.println("搜索结果数量:" + topDocs.totalHits);
			for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
				System.err.println(scoreDoc.score);
				System.err.println(scoreDoc.toString());
				Document document = indexSearcher.doc(scoreDoc.doc);
				System.err.println("结果:【name:" + document.get("name") + ",path:" + document.get("filepath") + "】");
			}
		} catch (Exception e) {
		} finally {
			if (indexReader != null) {
				indexReader.close();
			}
		}
		return null;
	}

	/**
	 * 删除索引根据条件
	 * 
	 * @param indexPath
	 * @throws IOException
	 */
	public static void deleteIndex(String indexPath) throws IOException {
		// 1.IndexWriter
		Directory directory = FSDirectory.open(new File(indexPath));
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35));
		IndexWriter indexWriter = new IndexWriter(directory, iwc);

		// indexWriter.deleteDocuments(query);

		// 删除,
		indexWriter.forceMergeDeletes();
		indexWriter.close();

	}

	public static void reDeleteIndex(String indexPath) throws CorruptIndexException, IOException {
		IndexReader indexReader = IndexReader.open(FSDirectory.open(new File(indexPath)), false);

		indexReader.undeleteAll();

		indexReader.close();
	}

	/**
	 * 生成文档
	 * 
	 * @return
	 * @throws FileNotFoundException
	 */
	private static List<Document> createDocuments() throws FileNotFoundException {
		List<Document> listDocument = new ArrayList<>();
		File file = new File("D:\\lucene-3.5.0\\datasource\\file");

		for (File ifile : file.listFiles()) {
			Document iDocument = new Document();
			iDocument.add(new Field("name", ifile.getName(), Store.YES, Index.NOT_ANALYZED));
			iDocument.add(new Field("content", new FileReader(ifile)));
			iDocument.add(new NumericField("modifytime").setLongValue(Calendar.getInstance().getTimeInMillis()));
			iDocument.add(new Field("filepath", ifile.getAbsolutePath(), Store.YES, Index.NO));
			listDocument.add(iDocument);
		}

		return listDocument;
	}

}
