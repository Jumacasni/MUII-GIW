/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.juanmanuelcastillonievas.search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author jumacasni
 */
public class Parser {
    private File currentFile;
    private ArrayList<String> fileText;
    
    public Parser(String filename){
        fileText = new ArrayList<String>();
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String strLine;
        ArrayList<String> lines = new ArrayList<String>();
        try {
            while ((strLine = reader.readLine()) != null) {
                processLine(strLine);
            }
            
            FileWriter myWriter = new FileWriter(currentFile);
            for(String text: fileText){
                myWriter.write(text);
                myWriter.write("\n");
            }
            myWriter.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void processLine(String line) throws IOException{
        if(line.startsWith(".I")){
            String[] parts = line.split(" ");
            String number = parts[1];
            
            if(!fileText.isEmpty()){
                FileWriter myWriter = new FileWriter(currentFile);
                for(String text: fileText){
                    myWriter.write(text);
                    myWriter.write("\n");
                }
                myWriter.close();
            }
            
            currentFile = new File("src/main/webapp/resources/documents/document"+number+".txt");
            if (currentFile.createNewFile()) {
                System.out.println("File created: " + currentFile.getName());
            } else {
                System.out.println("File already exists.");
            }
            
            fileText.clear();
        }
        
        else if(line.startsWith(".W")){
            ;
        }
        
        else{
            fileText.add(line);
        }
    }
    
    public static void main(String[] args) {
        Parser parser = new Parser("src/main/webapp/resources/MED.ALL");
    }
}
