package com.juanmanuelcastillonievas.search;

import static com.juanmanuelcastillonievas.search.SearchServlet.DOCUMENTS_DIRECTORY_PATH;
import java.io.BufferedReader;
import java.io.FileReader;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class Searcher {
    private QueryParser queryParser;
    private IndexSearcher indexSearcher;

    public Searcher(String indexDirectoryPath) throws IOException {
        Directory indexDirectory = FSDirectory.open(Paths.get(indexDirectoryPath));
        IndexReader reader = DirectoryReader.open(indexDirectory);
        this.indexSearcher = new IndexSearcher(reader);
        this.queryParser = new QueryParser("content", new EnglishAnalyzer());
    }

    public TopDocs search(String searchQuery) throws IOException, ParseException {
        Query query = queryParser.parse(searchQuery);
        return indexSearcher.search(query, 2000);
    }
    
    public Document getDocument(ScoreDoc scoreDoc) throws CorruptIndexException, IOException {
        return indexSearcher.doc(scoreDoc.doc);
    }
    
    public static void main(String[] args) throws IOException, ParseException {
        if(args.length != 1){
            System.out.println("You need to pass 1 argument, the index directory path\n"
                    + "Example: /home/user/index");
            System.exit(-1);
        }
        
        Searcher searcher = new Searcher(args[0]);
        
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("What are you looking for?");

        String searchQuery = myObj.nextLine();  // Read user input
        
        TopDocs hits = searcher.search(searchQuery);

        System.out.println("\n"+hits.totalHits + " documents found:");
        
        Integer number = 0;
        
        for(ScoreDoc scoreDoc : hits.scoreDocs) {
            Document doc = searcher.getDocument(scoreDoc);
            
            System.out.println(Integer.toString(number)+": "+doc.get("name"));

            number += 1;
        }
        
        while(true){
            myObj = new Scanner(System.in);  // Create a Scanner object
            System.out.println("\nWhich document do you want to open? Introduce the number on the left (E.g: 50)");
            
            String documentNumber = myObj.nextLine();  // Read user input
            
            number = 0;

            for(ScoreDoc scoreDoc : hits.scoreDocs) {
                number += 1;
                
                if(number == Integer.parseInt(documentNumber)){
                    Document doc = searcher.getDocument(scoreDoc);
                    String line;
                    BufferedReader brTest = new BufferedReader(new FileReader(doc.get("filepath")));
                    while ((line = brTest.readLine()) != null) {
                        System.out.println(line);
                    }
                }
            }
        }
    }

    private Object getServletContext() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
