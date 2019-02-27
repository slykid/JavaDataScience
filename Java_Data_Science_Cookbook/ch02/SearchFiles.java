package com.kilhyunkim.DS;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

public class SearchFiles {

	public static final String INDEX_DIRECTORY = "index";
	public static final String FIELD_CONTENTS = "contents";
	
	public static void main(String[] args) throws Exception 
	{
		IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(INDEX_DIRECTORY)));
		IndexSearcher indexSearcher = new IndexSearcher(reader);
		Analyzer analyzer = new StandardAnalyzer();
		QueryParser queryParser = new QueryParser(FIELD_CONTENTS, analyzer);
		
		String searchString = "over-full AND persuasion";
		//String searchString = "shakespeare";
		
		Query query = queryParser.parse(searchString);
				
		TopDocs results = indexSearcher.search(query, 5);
		
		ScoreDoc[] hits = results.scoreDocs;
		
		int numTotalHits = (int) results.totalHits;
		System.out.println(numTotalHits + " total matching documents");
		
		for(int i = 0; i < hits.length; i++)
		{
			int docId = hits[i].doc;
			Document d = indexSearcher.doc(docId);
			System.out.println( (i+1) + ". " + d.get("path") + " score=" + hits[i].score);
		}

	}

}
