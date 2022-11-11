package com.example.servlet.api;


import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import Dao.*;
@WebServlet(name = "getLezioniByDocente", value = "/api/getLezioniByDocente")
public class getLezioniByDocente extends HttpServlet {
    private Dao dao;


    public void init(ServletConfig config) {

        this.dao  = new Dao(config.getServletContext().getInitParameter("dbURL"),config.getServletContext().getInitParameter("dbUser"),config.getServletContext().getInitParameter("dbUserPass"));
        System.out.println(dao==null);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");

        HttpSession s = request.getSession();

        if (this.dao == null) {
            PrintWriter out = response.getWriter();
            out.println("dao is null");
        } else {

            String docente = request.getParameter("docente");

            ArrayList<Lezione> lex = dao.getLezioniLibereByDocente(docente);
            PrintWriter out = response.getWriter();
            out.println("{");
            out.println("\"Nome Oggetto\"" + ":" + "\""+"Corsi per Docente "+ "\"" + ",");


            out.println("\"Docente\"" + ":" + "\""+docente+ "\"" + ",");
            out.println("\"numero_lezioni\"" + ":"+ "\"" + lex.size()+ "\"" + ",");
            out.println("\"lezioni\" : ");
            out.println("[");
            for (int i = 0; i < lex.size() ; i++) {
                Lezione l = lex.get(i);
                Corso c = dao.getCorsoByID(l.getCorso_ID());
                Utente doc = dao.getUtenteByID(l.getDocente_ID());



                out.println("     {");
                out.println("       \"data\" : " + "\"" +l.getData() + "\"" + " ,");
                out.println("       \"ora\" : " +  "\"" + l.getOra() + "\"" + " ,");
                //out.println("       \"stato\" : " +"\"" + l.getStato() +"\"" + " ,");
                out.println("       \"nome_corso\": " +"\"" + c.getNome() + "\"" +" ,");
                out.println("       \"nome_docente\": " +"\"" + doc.getNome() +"\"" + " ,");
                out.println("       \"cognome_docente\" : " +"\"" + doc.getCognome() +"\"" + " ,");

                out.print("}");
                if(i<lex.size()-1){
                    out.print(",");
                }

            }


            out.println("]");
            out.println("}");


        }
    }
}






