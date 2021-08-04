package com.juanmanuelcastillonievas.search;

import java.io.BufferedReader;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Indexer {
    private IndexWriter iwriter;
    private String documentsDirectoryPath;
    private String fileStopWords;
    private String indexDirectoryPath;
    private CharArraySet stopWords;
    
    public Indexer(String documentsDirectoryPath, String fileStopWords, String indexDirectoryPath) throws IOException {
        this.documentsDirectoryPath = documentsDirectoryPath;
        this.fileStopWords = fileStopWords;
        this.indexDirectoryPath = indexDirectoryPath;

        Directory directory = FSDirectory.open(Paths.get(indexDirectoryPath));

        this.parseStopWords();

        EnglishAnalyzer analyzer = new EnglishAnalyzer(stopWords);
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        iwriter = new IndexWriter(directory, config);

        if(DirectoryReader.indexExists(directory)){
            iwriter.deleteAll();
        }
    }

    private CharArraySet parseStopWords() throws FileNotFoundException {
        Scanner sc = new Scanner(new File(fileStopWords));
        List<String> lines = new ArrayList<String>();
        while (sc.hasNextLine()) {
            lines.add(sc.nextLine());
        }

        this.stopWords = new CharArraySet(lines, true);
        return stopWords;
    }

    public void indexFiles() throws IOException {
        File[] files = new File(documentsDirectoryPath).listFiles();
        for(File file: files){
            
            Document document = new Document();
            
            TextField bodyField = new TextField("content", new FileReader(file));
            TextField fileNameField = new TextField("name", file.getName(), Field.Store.YES);
            TextField filePathField = new TextField("filepath", file.getCanonicalPath(), TextField.Store.YES);

            document.add(bodyField);
            document.add(fileNameField);
            document.add(filePathField);
            iwriter.addDocument(document);
        }
        iwriter.close();
    }
    
    public CharArraySet getStopWords(){
        return stopWords;
    }
    
    public static void main(String[] args) throws IOException {
        if(args.length != 3){
            System.out.println("You need to pass 3 arguments, the documents directory path, stop words file, and index directory path\n"
                    + "Example: /home/user/documents /home/user/stop_words.txt /home/user/index");
            System.exit(-1);
        }
        
        Indexer indexer = new Indexer(args[0], args[1], args[2]);
        
        indexer.indexFiles();
    }
}
