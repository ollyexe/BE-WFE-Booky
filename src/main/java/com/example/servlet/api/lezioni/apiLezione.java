package com.example.servlet.api.lezioni;


import java.io.*;
import java.util.ArrayList;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import Dao.*;
@WebServlet(name = "", value = "/api/getLeasonsByUserAndByDay")
public class getLeasonsByUserAndByDay extends HttpServlet {
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
            String date = request.getParameter("data");
            String userName = request.getParameter("mail");
            Utente u = dao.getUtente(userName);
            ArrayList<Lezione> lex = dao.getLezioneByUtenteAndByDay(userName,date);
            PrintWriter out = response.getWriter();
            out.println("{");
            out.println("\"Nome Oggetto\"" + ":" + "\""+"Leasons By Utente And By Date"+ "\"" + ",");
            out.println("\"ID\"" + ":" + "\""+u.getID()+ "\"" + ",");
            out.println("\"Email\"" + ":" + "\""+u.getEmail()+ "\"" + ",");
            out.println("\"Data\"" + ":" + "\""+date+ "\"" + ",");
            out.println("\"numero_lezioni\"" + ":"+ "\"" + lex.size()+ "\"" + ",");
            out.println("\"lezioni\" : ");
            out.println("[");
            for (int i = 0; i < lex.size() ; i++) {
                Lezione l = lex.get(i);
                Corso c = dao.getCorsoByID(l.getCorso_ID());
                Utente doc = dao.getUtenteByID(l.getDocente_ID());
                Utente stud = dao.getUtenteByID(l.getUtente_ID());
                out.println("     {");
                out.println("       \"data\" : " + "\"" +l.getData() + "\"" + " ,");
                out.println("       \"ora\" : " +  "\"" + l.getOra() + "\"" + " ,");
                out.println("       \"stato\" : " +"\"" + l.getStato() +"\"" + " ,");
                out.println("       \"nome_corso\": " +"\"" + c.getNome() + "\"" +" ,");
                out.println("       \"nome_docente\": " +"\"" + doc.getNome() +"\"" + " ,");
                out.println("       \"cognome_docente\" : " +"\"" + doc.getCognome() +"\"" + " ,");
                out.println("       \"nome_studente\" : " +"\"" + stud.getNome() +"\"" + " ,");
                out.println("       \"cognome_studente\" : " +"\"" + stud.getCognome() +"\"" );
                out.println("     }");
                if(i<lex.size()-1){
                    out.print(",");
                }

            }


            out.println("]");
            out.println("}");


        }
    }
}






