package com.loujie.www.lucene3_5_0;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;

import com.loujie.www.entity.User;

/**
 * Lucene的基础操作
 * 
 * @author loujie
 *
 */
public class LuceneUtils {

	private static IndexReader indexReader = null;
	private static IndexSearcher indexSearcher = null;

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 创建索引
	 * 
	 * @param indexPath
	 * @throws IOException
	 */
	public static void createIndex(String indexPath) throws IOException {
		// 1.索引位置
		Directory directory = getDirectory(indexPath);
		// 2.利用IndexWriter写索引
		IndexWriter indexWriter = null;
		try {
			indexWriter = getIndexWriter(directory);
			// 3.创建数据信息
			List<Document> documents = createDocuments();
			// 4.用IndexWriter添加document，并保存
			indexWriter.addDocuments(documents);
			// 5.commit,在没有close的时候,用这个
			commit(indexWriter);
		} catch (Exception e) {
		} finally {
			closeWriter(indexWriter);
		}
	}

	/**
	 * 输出Index信息
	 * 
	 * @param indexPath
	 * @throws IOException
	 */
	public static void printIndex(String indexPath) throws IOException {
		// 1.索引目录
		Directory directory = getDirectory(indexPath);
		// 2.IndexReader
		indexReader = getIndexReader(directory);

		System.err.println("maxDoc:" + indexReader.maxDoc());
		System.err.println("numDocs:" + indexReader.numDocs());
		System.err.println("numDeleteDocs:" + indexReader.numDeletedDocs());
		// 3.关闭
		closeReader(indexReader);
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
		Directory directory = getDirectory(indexPath);
		try {
			indexReader = getIndexReader(directory);
			indexSearcher = new IndexSearcher(indexReader);
			// 2.声明Query对象
			Query query = null;
			// 2.也可以通过QueryParse来创建Query
			// query = queryParser.parse(queryString);
			Term t = new Term("name", queryString);
			query = new TermQuery(t);
			// 3.用IndexSearcher查询
			TopDocs topDocs = indexSearcher.search(query, 10);
			// 4.处理返回结果,
			System.err.println("搜索结果数量:" + topDocs.totalHits);
			for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
				System.err.println(scoreDoc.toString());
				Document document = indexSearcher.doc(scoreDoc.doc);
				System.err.println("结果:" + document.get("toString"));
			}
		} catch (Exception e) {
			e.printStackTrace();
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
		for (User user : getUsers()) {
			Document tempDoc = new Document();
			tempDoc.add(new NumericField("id", Store.YES, true).setIntValue(user.getId()));
			tempDoc.add(new Field("phone", user.getPhone(), Store.YES, Index.NOT_ANALYZED_NO_NORMS));
			tempDoc.add(new Field("name", user.getName(), Store.YES, Index.NOT_ANALYZED));
			tempDoc.add(new Field("password", user.getPassword(), Store.NO, Index.ANALYZED_NO_NORMS));
			tempDoc.add(new Field("idno", user.getIdno(), Store.YES, Index.NOT_ANALYZED_NO_NORMS));
			try {
				tempDoc.add(new NumericField("createTime", Store.YES, true).setLongValue(sdf.parse(user.getCreateTime()).getTime()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			tempDoc.add(new Field("toString", user.toString(), Store.YES, Index.NO));
			listDocument.add(tempDoc);
		}
		return listDocument;
	}

	/**
	 * 获取一个用户列表
	 * 
	 * @return
	 */
	public static List<User> getUsers() {
		List<User> listUsers = new ArrayList<>();
		listUsers.add(new User(2, "15801852667", "丛祺", "d3aedd3b6b5d1f4a7840e16770adadb8f87c56d9", "435432199010208765", "2016-08-19 13:52:29"));
		listUsers.add(new User(3, "13671588178", "李华", "34e52980c72763e4100305223430c218c505dfc3", "310115198806120925", "2015-08-19 13:52:29"));
		listUsers.add(new User(5, "13761168844", "张敏", "d3aedd3b6b5d1f4a7840e16770adadb8f87c56d9", "310115198703305214", "2014-08-19 14:04:04"));
		listUsers.add(new User(6, "13671812824", "徐晓琼", "d3aedd3b6b5d1f4a7840e16770adadb8f87c56d9", "310115198801282220", "2013-08-19 14:04:04"));
		listUsers.add(new User(7, "13917041493", "王寒", "d3aedd3b6b5d1f4a7840e16770adadb8f87c56d9", "31011519891210323x", "2012-08-19 14:04:04"));
		listUsers.add(new User(8, "13817770047", "罗楠", "d3aedd3b6b5d1f4a7840e16770adadb8f87c56d9", "430781199201215014", "2011-08-19 14:04:04"));
		listUsers.add(new User(9, "18016306785", "厉论", "d3aedd3b6b5d1f4a7840e16770adadb8f87c56d9", "430111198810310352", "2011-08-19 14:04:04"));
		listUsers.add(new User(10, "18017005450", "顾福祺", "d3aedd3b6b5d1f4a7840e16770adadb8f87c56d9", "310110198811041517", "2012-08-19 14:04:04"));
		listUsers.add(new User(11, "15800769360", "张荣", "d3aedd3b6b5d1f4a7840e16770adadb8f87c56d9", "310230198902044976", "2013-08-19 14:04:04"));
		return listUsers;
	}

	/**
	 * 获取索引位置Directory
	 * 
	 * @param indexPath
	 *            索引位置路径
	 * @return
	 * @throws IOException
	 */
	private static Directory getDirectory(String indexPath) throws IOException {
		return FSDirectory.open(new File(indexPath));
	}

	/**
	 * 获取分词解析器
	 * 
	 * @return
	 */
	private static Analyzer getAnalyzer() {
		return new StandardAnalyzer(Version.LUCENE_35);
	}

	/**
	 * 获取索引写入对象IndexWriter
	 * 
	 * @param directory
	 *            索引目录
	 * @return
	 * @throws CorruptIndexException
	 * @throws LockObtainFailedException
	 * @throws IOException
	 */
	private static IndexWriter getIndexWriter(Directory directory) throws CorruptIndexException, LockObtainFailedException, IOException {
		// 1.声明索引写入器的配置,定义了使用的解析器
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_35, getAnalyzer());
		// 2.每次都创建
		iwc.setOpenMode(OpenMode.CREATE);
		return new IndexWriter(directory, iwc);
	}

	/**
	 * 提交数据,但不关闭
	 * 
	 * @param indexWriter
	 */
	private static void commit(IndexWriter indexWriter) {
		if (indexWriter != null) {
			try {
				indexWriter.commit();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取IndexReader
	 * 
	 * @param directory
	 *            索引目录
	 * @return
	 * @throws CorruptIndexException
	 * @throws IOException
	 */
	private static IndexReader getIndexReader(Directory directory) throws CorruptIndexException, IOException {
		if (indexReader == null) {
			indexReader = IndexReader.open(directory);
		} else {
			IndexReader ir = IndexReader.openIfChanged(indexReader);
			if (ir != null) {
				indexReader = ir;
			}
		}
		return indexReader;
	}

	/**
	 * 关闭IndexWriter
	 * 
	 * @param indexWriter
	 */
	private static void closeWriter(IndexWriter indexWriter) {
		if (indexWriter != null) {
			try {
				indexWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 关闭IndexReader
	 * 
	 * @param indexRdader
	 */
	private static void closeReader(IndexReader indexReader) {
		if (indexReader != null) {
			try {
				indexReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
