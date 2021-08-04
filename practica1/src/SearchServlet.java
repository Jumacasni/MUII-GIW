/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.juanmanuelcastillonievas.search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

/**
 *
 * @author jumacasni
 */
public class SearchServlet extends HttpServlet {
    public static final String INDEX_DIRECTORY_PATH = "/resources/index/";
    public static final String DOCUMENTS_DIRECTORY_PATH = "/resources/documents/";
    public static final String FILE_STOP_WORDS = "/resources/stop_words/english_stopwords.txt";
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {
        
        String query = request.getParameter("searchQuery");
        
        Indexer indexer = new Indexer(this.getServletContext().getRealPath(".")+DOCUMENTS_DIRECTORY_PATH,
                this.getServletContext().getRealPath(".")+FILE_STOP_WORDS,
                this.getServletContext().getRealPath(".")+INDEX_DIRECTORY_PATH);
        
        indexer.indexFiles();

        Searcher searcher = new Searcher(this.getServletContext().getRealPath(".")+INDEX_DIRECTORY_PATH);
        TopDocs hits = searcher.search(query);

        System.out.println(hits.totalHits + " documents found.");
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />");
        out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />");
        out.println("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />");
        out.println("<meta name=\"author\" content=\"colorlib.com\">");
        out.println("<link href=\"https://fonts.googleapis.com/css?family=Poppins\" rel=\"stylesheet\" />");
        out.println("<link href=\"css/main.css\" rel=\"stylesheet\" />");
        out.println("<title>"+query+" - Search</title>");            
        out.println("</head>");
        out.println("<body>");
        out.println("<div class=\"s130 small\">");
        out.println("<form action=\"search\" method=\"post\">");
        out.println("<div class=\"inner-form\">");
        out.println("<div class=\"input-field first-wrap\">");
        out.println("<div class=\"svg-wrapper\">");
        out.println("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"24\" height=\"24\" viewBox=\"0 0 24 24\">");
        out.println("<path d=\"M15.5 14h-.79l-.28-.27C15.41 12.59 16 11.11 16 9.5 16 5.91 13.09 3 9.5 3S3 5.91 3 9.5 5.91 16 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z\"></path>");
        out.println("</svg>");
        out.println("</div>");
        out.println("<input id=\"search\" type=\"text\" name=\"searchQuery\" value=\""+query+"\" />");
        out.println("</div>");
        out.println("<div class=\"input-field second-wrap\">");
        out.println("<input class=\"btn-search\" type=\"submit\" value=\"SEARCH\"></input>");
        out.println("</div>");
        out.println("</div>");
        out.println("</form>");
        out.println("</div>");
        out.println("<script src=\"js/extention/choices.js\"></script>");
        out.println("<div class=\"links-container\">");
        out.println("<h2>Showing "+hits.totalHits.value+" results</h4>");
        
        Integer number = 1;
        
        try {
            for(ScoreDoc scoreDoc : hits.scoreDocs) {
                Document doc = searcher.getDocument(scoreDoc);

                BufferedReader brTest = new BufferedReader(new FileReader(this.getServletContext().getRealPath(".")+DOCUMENTS_DIRECTORY_PATH+doc.get("name")));
                String text = brTest.readLine();
                
                out.println("<a class=\"link\" href=\"http://localhost:8080/Search/resources/documents/"+doc.get("name")+"\" target=\"_blank\" rel=\"noopener noreferrer\"><span style=\"font-size: 30px\">"+Integer.toString(number)+".</span> "+text+"</a>");
                number += 1;
            }
            
        } finally {
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(SearchServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(SearchServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
