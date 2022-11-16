package com.example.servlet.api.lezioni;


import java.io.*;
import java.util.ArrayList;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import Dao.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@WebServlet(name = "apiLezione", value = "/apiLezione")
public class apiLezione extends HttpServlet {
    private Dao dao;


    public void init(ServletConfig config) {

        dao = new Dao("jdbc:mysql://localhost:3306/db_book", "dbhelper", "73C88AAFCFC9701657356F643382EBE40E2B8660C");
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

        HttpSession s = request.getSession();
        PrintWriter out = response.getWriter();
        System.out.println(request.getParameter("path"));
        response.setContentType("application/json");


        if(request.getParameter("path")!=null){
            switch(request.getParameter("path")){
                case "getLezioneByUtenteAndByDay" : {
                    if (this.dao == null) {

                        out.println("dao is null");
                    } else {
                        String date = request.getParameter("data");
                        String userName = request.getParameter("mail");
                        Utente u = dao.getUtente(userName);
                        ArrayList<Lezione> lex = dao.getLezioneByUtenteAndByDay(userName,date);

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
                            out.println("       \"cognome_studente\" : " +"\"" + stud.getCognome() +"\"" +" ,");
                            out.println("       \"valutazione\" : " +"\"" + l.getValutazione() +"\"" + " ,");
                            out.println("     }");
                            if(i<lex.size()-1){
                                out.print(",");
                            }

                        }


                        out.println("]");
                        out.println("}");


                    }
                        break;
                    }

                case "getLeasonsStory" : {
                    if (this.dao == null) {
                        out.println("dao is null");
                    }
                    else {

                        String userName = request.getParameter("mail");
                        Utente u = dao.getUtente(userName);
                        ArrayList<Lezione> lex = dao.getLeasonsStory(userName);
                        out.println("{");
                        out.println("\"Nome Oggetto\"" + ":" + "\""+"Leasons Story"+ "\"" + ",");
                        out.println("\"ID\"" + ":" + "\""+u.getID()+ "\"" + ",");

                        out.println("\"Email\"" + ":" + "\""+u.getEmail()+ "\"" + ",");
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
                            out.println("       \"cognome_studente\" : " +"\"" + stud.getCognome() +"\""+ " ," );
                            out.println("       \"valutazione\" : " +"\"" + l.getValutazione() +"\"" + " ,");
                            out.println("     }");
                            if(i<lex.size()-1){
                                out.print(",");
                            }

                        }


                        out.println("]");
                        out.println("}");


                    }

                }

                case "getLezioniLibereByCorso" :{
                    if (this.dao == null) {

                        out.println("dao is null");
                    }
                    else {

                        String corso = request.getParameter("corso");

                        ArrayList<Lezione> lex = dao.getLezioniLibereByCorso(corso);

                        out.println("{");
                        out.println("\"Nome Oggetto\"" + ":" + "\""+"Corsi per Materia "+ "\"" + ",");


                        out.println("\"Corso\"" + ":" + "\""+corso+ "\"" + ",");
                        out.println("\"numero_lezioni\"" + ":"+ "\"" + lex.size()+ "\"" + ",");
                        out.println("\"lezioni\" : ");
                        out.println("[");
                        for (int i = 0; i < lex.size() ; i++) {
                            Lezione l = lex.get(i);
                            Corso c = dao.getCorsoByID(l.getCorso_ID());
                            Utente doc = dao.getUtenteByID(l.getDocente_ID());
                            //Utente stud = dao.getUtenteByID(l.getUtente_ID());



                            out.println("     {");
                            out.println("       \"data\" : " + "\"" +l.getData() + "\"" + " ,");
                            out.println("       \"ora\" : " +  "\"" + l.getOra() + "\"" + " ,");
                            out.println("       \"stato\" : " +"\"" + l.getStato() +"\"" + " ,");
                            out.println("       \"nome_corso\": " +"\"" + c.getNome() + "\"" +" ,");
                            out.println("       \"nome_docente\": " +"\"" + doc.getNome() +"\"" + " ,");
                            out.println("       \"cognome_docente\" : " +"\"" + doc.getCognome() +"\"" + " ,");
                            //out.println("       \"nome_studente\" : " +"\"" + stud.getNome() +"\"" + " ,");
                            //out.println("       \"cognome_studente\" : " +"\"" + stud.getCognome() +"\""+ " ," );
                            //out.println("       \"valutazione\" : " +"\"" + l.getValutazione() +"\"" + " ,");
                            out.println("     }");
                            if(i<lex.size()-1){
                                out.print(",");
                            }

                        }


                        out.println("]");
                        out.println("}");


                    }
                }

                case "getLezioniLibereByDocente": {
                    if (this.dao == null) {

                        out.println("dao is null");
                    }
                    else {
                        String docente = request.getParameter("docente");
                        ArrayList<Lezione> lex = dao.getLezioniLibereByDocente(docente);
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

                case "getLezioniByUtente" : {
                    if (this.dao == null) {
                      out.println("dao is null");
                    }
                    else {

                        String userName = request.getParameter("mail");
                        Utente u = dao.getUtente(userName);
                        ArrayList<Lezione> lex = dao.getLezioneByUtente(userName);
                        out.println("{");
                        out.println("\"Nome Oggetto\"" + ":" + "\""+"Prenotazioni All"+ "\"" + ",");
                        out.println("\"ID\"" + ":" + "\""+u.getID()+ "\"" + ",");

                        out.println("\"Email\"" + ":" + "\""+u.getEmail()+ "\"" + ",");
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
                            out.println("       \"cognome_studente\" : " +"\"" + stud.getCognome() +"\""+ " ," );
                            out.println("       \"valutazione\" : " +"\"" + l.getValutazione() +"\"" + " ,");
                            out.println("     }");
                            if(i<lex.size()-1){
                                out.print(",");
                            }

                        }


                        out.println("]");
                        out.println("}");


                    }
                }

                case "valutaLezione" : {
                    if (dao == null) {
                        out.println("dao is null");
                    } else {
                        boolean flag= false;
                        String data = request.getParameter("data");
                        String ora = request.getParameter("ora");
                        int Docente_ID = Dao.getIDbyUtente(request.getParameter("docente"));
                        int stelle = Integer.parseInt(request.getParameter("stelle"));


                        try {
                            flag = dao.valutaLezione(data,ora,Docente_ID,stelle);
                            if(flag){
                                out.print("{" +
                                        "\"rate_state\"" + ":" + "\"succes\"" +" ,"+
                                        "\"state_description\"" + ":" + "\"lezione was rated\"" +
                                        "}");
                                out.flush();
                            }
                        }
                        catch (Error error){
                            out.print("{" +
                                    "\"rate_state\"" + ":" + "\"already\"" +" ,"+
                                    "\"state_description\"" + ":" + "\"this lezione  is already valuatata or doesnt exist or rate is 0(cant be)\"" +
                                    "}");
                            out.flush();
                        }





                    }
                    break;

                }




            }
        }
        else{
            out.println("Selected path doesnt exists ");
            out.flush();
        }
    }



}






