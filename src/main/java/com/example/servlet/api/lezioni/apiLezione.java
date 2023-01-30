package com.example.servlet.api.lezioni;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import Dao.*;


@WebServlet(name = "apiLezione", value = "/apiLezione")
public class apiLezione extends HttpServlet {
    private Dao dao;


    public void init(ServletConfig config) {

        dao  = (Dao) config.getServletContext().getAttribute("Dao");
       

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
        //System.out.println(request.getParameter("path"));
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
                       if(lex.size()==0){

                       }
                       else {
                           int j = 0;
                           out.println("[");
                           for (int i = 0; i < lex.size() ; i++) {
                               Lezione l = lex.get(i);
                               Corso c = dao.getCorsoByID(l.getCorso_ID());
                               Utente doc = dao.getUtenteByID(l.getDocente_ID());
                               out.println("     {");
                               out.println("       \"data\" : " + "\"" +l.getData() + "\"" + " ,");
                               out.println("       \"ora\" : " +  "\"" + l.getOra() + "\"" + " ,");
                               out.println("       \"nome_corso\": " +"\"" + c.getNome() + "\"" +" ,");
                               out.println("       \"nome_docente\": " +"\"" + doc.getNome() +"\"" + " ,");
                               out.println("       \"cognome_docente\" : " +"\"" + doc.getCognome() +"\"" + " ,");
                               out.println("       \"email\" : " +"\"" + doc.getEmail() +"\"" + " ,");
                               out.println("       \"valutazione\" : " +"\"" + l.getValutazione() +"\"" + " ,");

                               out.println("       \"pf\" : " +"\"" + doc.getPf() +"\"" + " ,");
                               out.println("       \"stelle\" : " +"\"" + doc.getStelle() +"\"" + " ,");
                               out.println("       \"prezzo\" : " +"\"" + l.getPrezzo() +"\"" );
                               out.println("     }");
                               if(j<lex.size()-1){
                                   j++;
                                   out.print(",");
                               }

                           }


                           out.println("]");
                       }


                    }
                        break;
                    }

                case "getLezioniFinite" : {
                    if (this.dao == null) {
                        out.println("dao is null");
                    }
                    else {

                        String userName = request.getParameter("mail");
                        ArrayList<Lezione> lex = dao.getLeasonStory(userName);
                        int j = 0;

                        out.println("[");
                        for (int i = 0; i < lex.size() ; i++) {
                            Lezione l = lex.get(i);
                            Corso c = dao.getCorsoByID(l.getCorso_ID());
                            Utente doc = dao.getUtenteByID(l.getDocente_ID());
                            out.println("     {");
                            out.println("       \"data\" : " + "\"" +l.getData() + "\"" + " ,");
                            out.println("       \"ora\" : " +  "\"" + l.getOra() + "\"" + " ,");
                            out.println("       \"nome_corso\": " +"\"" + c.getNome() + "\"" +" ,");
                            out.println("       \"nome_docente\": " +"\"" + doc.getNome() +"\"" + " ,");
                            out.println("       \"cognome_docente\" : " +"\"" + doc.getCognome() +"\"" + " ,");
                            out.println("       \"email\" : " +"\"" + doc.getEmail() +"\"" + " ,");
                            out.println("       \"valutazione\" : " +"\"" + l.getValutazione() +"\"" + " ,");

                            out.println("       \"pf\" : " +"\"" + doc.getPf() +"\"" + " ,");
                            out.println("       \"stelle\" : " +"\"" + doc.getStelle() +"\"" + " ,");
                            out.println("       \"prezzo\" : " +"\"" + l.getPrezzo() +"\"" );
                            out.println("     }");
                            if(j<lex.size()-1){
                                j++;
                                out.print(",");
                            }

                        }


                        out.println("]");

                        out.flush();

                    }
                    break;
                }

                case "getLezioniLibere" : {
                    if (this.dao == null) {
                        out.println("dao is null");
                    }
                    else {

                        ArrayList<Lezione> lex = dao.getLezioniLibere();
                        int j = 0;

                        out.println("[");
                        for (int i = 0; i < lex.size() ; i++) {
                            Lezione l = lex.get(i);
                            Corso c = dao.getCorsoByID(l.getCorso_ID());
                            Utente doc = dao.getUtenteByID(l.getDocente_ID());
                            out.println("     {");
                            out.println("       \"data\" : " + "\"" +l.getData() + "\"" + " ,");
                            out.println("       \"ora\" : " +  "\"" + l.getOra() + "\"" + " ,");
                            out.println("       \"nome_corso\": " +"\"" + c.getNome() + "\"" +" ,");
                            out.println("       \"nome_docente\": " +"\"" + doc.getNome() +"\"" + " ,");
                            out.println("       \"cognome_docente\" : " +"\"" + doc.getCognome() +"\"" + " ,");
                            out.println("       \"email\" : " +"\"" + doc.getEmail() +"\"" + " ,");
                            out.println("       \"valutazione\" : " +"\"" + l.getValutazione() +"\"" + " ,");
                            out.println("       \"pf\" : " +"\"" + doc.getPf() +"\"" + " ,");
                            out.println("       \"stelle\" : " +"\"" + doc.getStelle() +"\"" + " ,");
                            out.println("       \"prezzo\" : " +"\"" + l.getPrezzo() +"\"" );
                            out.println("     }");
                            if(j<lex.size()-1){
                                j++;
                                out.print(",");
                            }

                        }


                        out.println("]");

                        out.flush();

                    }
                    break;
                }
                case "getLezioniLibereByDocente" : {
                    if (this.dao == null) {
                        out.println("dao is null");
                    }
                    else {
                        int docente = Integer.parseInt(request.getParameter("ID"));
                        List<Lezione> lex = dao.getLezioniLibere();
                        lex=lex.stream().filter(lezione -> lezione.getDocente_ID()==docente).collect(Collectors.toList());
                        int j = 0;

                        out.println("[");
                        for (int i = 0; i < lex.size() ; i++) {
                            Lezione l = lex.get(i);
                            Corso c = dao.getCorsoByID(l.getCorso_ID());
                            Utente doc = dao.getUtenteByID(l.getDocente_ID());
                            out.println("     {");
                            out.println("       \"data\" : " + "\"" +l.getData() + "\"" + " ,");
                            out.println("       \"ora\" : " +  "\"" + l.getOra() + "\"" + " ,");
                            out.println("       \"nome_corso\": " +"\"" + c.getNome() + "\"" +" ,");
                            out.println("       \"nome_docente\": " +"\"" + doc.getNome() +"\"" + " ,");
                            out.println("       \"cognome_docente\" : " +"\"" + doc.getCognome() +"\"" + " ,");
                            out.println("       \"email\" : " +"\"" + doc.getEmail() +"\"" + " ,");
                            out.println("       \"valutazione\" : " +"\"" + l.getValutazione() +"\"" + " ,");
                            out.println("       \"pf\" : " +"\"" + doc.getPf() +"\"" + " ,");
                            out.println("       \"stelle\" : " +"\"" + doc.getStelle() +"\"" + " ,");
                            out.println("       \"prezzo\" : " +"\"" + l.getPrezzo() +"\"" );
                            out.println("     }");
                            if(j<lex.size()-1){
                                j++;
                                out.print(",");
                            }

                        }


                        out.println("]");

                        out.flush();

                    }
                    break;
                }

                case "getLezioniLibereByCorso" : {
                    if (this.dao == null) {
                        out.println("dao is null");
                    }
                    else {
                        String  corso =request.getParameter("corso");
                        List<Lezione> lex = dao.getLezioniLibere();
                        lex=lex.stream().filter(lezione -> lezione.getCorso_ID()==Dao.getCorsoByNome(corso).getID()).collect(Collectors.toList());
                        int j = 0;

                        out.println("[");
                        for (int i = 0; i < lex.size() ; i++) {
                            Lezione l = lex.get(i);
                            Corso c = dao.getCorsoByID(l.getCorso_ID());
                            Utente doc = dao.getUtenteByID(l.getDocente_ID());
                            out.println("     {");
                            out.println("       \"data\" : " + "\"" +l.getData() + "\"" + " ,");
                            out.println("       \"ora\" : " +  "\"" + l.getOra() + "\"" + " ,");
                            out.println("       \"nome_corso\": " +"\"" + c.getNome() + "\"" +" ,");
                            out.println("       \"nome_docente\": " +"\"" + doc.getNome() +"\"" + " ,");
                            out.println("       \"ID_docente\": " +"\"" + doc.getID() +"\"" + " ,");
                            out.println("       \"cognome_docente\" : " +"\"" + doc.getCognome() +"\"" + " ,");
                            out.println("       \"email\" : " +"\"" + doc.getEmail() +"\"" + " ,");
                            out.println("       \"valutazione\" : " +"\"" + l.getValutazione() +"\"" + " ,");
                            out.println("       \"pf\" : " +"\"" + doc.getPf() +"\"" + " ,");
                            out.println("       \"stelle\" : " +"\"" + doc.getStelle() +"\"" + " ,");
                            out.println("       \"prezzo\" : " +"\"" + l.getPrezzo() +"\"" );
                            out.println("     }");
                            if(j<lex.size()-1){
                                j++;
                                out.print(",");
                            }

                        }


                        out.println("]");

                        out.flush();

                    }
                    break;
                }






                case "getLezioniByDocenteAndCorso": {
                    if (this.dao == null) {

                        out.println("dao is null");
                    }
                    else {
                        String docente = request.getParameter("docente");
                        String corso = request.getParameter("corso");
                        ArrayList<Lezione> lex = dao.getLezioniLibereByDocenteAndCorso(docente,corso);
                        int j =0;
                        out.println("[");
                        for (int i = 0; i < lex.size() ; i++) {
                            Lezione l = lex.get(i);
                            Corso c = dao.getCorsoByID(l.getCorso_ID());
                            Utente doc = dao.getUtenteByID(l.getDocente_ID());
                            out.println("     {");
                            out.println("       \"data\" : " + "\"" +l.getData() + "\"" + " ,");
                            out.println("       \"ora\" : " +  "\"" + l.getOra() + "\"" + " ,");
                            out.println("       \"nome_corso\": " +"\"" + c.getNome() + "\"" +" ,");
                            out.println("       \"nome_docente\": " +"\"" + doc.getNome() +"\"" + " ,");
                            out.println("       \"cognome_docente\" : " +"\"" + doc.getCognome() +"\"" + " ,");
                            out.println("       \"email\" : " +"\"" + doc.getEmail() +"\"" + " ,");
                            out.println("       \"valutazione\" : " +"\"" + l.getValutazione() +"\"" + " ,");

                            out.println("       \"pf\" : " +"\"" + doc.getPf() +"\"" + " ,");
                            out.println("       \"stelle\" : " +"\"" + doc.getStelle() +"\"" + " ,");
                            out.println("       \"prezzo\" : " +"\"" + l.getPrezzo() +"\"" );
                            out.println("     }");
                            if(j<lex.size()-1){
                                j++;
                                out.print(",");
                            }
                        }


                        out.println("]");
                        out.flush();



                    }
                    break;
                }

                case "getNextLezioniPrenotate" : {
                    if (this.dao == null) {
                      out.println("dao is null");
                    }
                    else {

                        String userName = request.getParameter("mail");
                        ArrayList<Lezione> lex = dao.getNextLezioniPrenotate(userName);
                        int j = 0;

                        out.println("[");
                        for (int i = 0; i < lex.size() ; i++) {
                            Lezione l = lex.get(i);
                            Corso c = dao.getCorsoByID(l.getCorso_ID());
                            Utente doc = dao.getUtenteByID(l.getDocente_ID());
                            out.println("     {");
                            out.println("       \"data\" : " + "\"" +l.getData() + "\"" + " ,");
                            out.println("       \"ora\" : " +  "\"" + l.getOra() + "\"" + " ,");
                            out.println("       \"nome_corso\": " +"\"" + c.getNome() + "\"" +" ,");
                            out.println("       \"nome_docente\": " +"\"" + doc.getNome() +"\"" + " ,");
                            out.println("       \"cognome_docente\" : " +"\"" + doc.getCognome() +"\"" + " ,");
                            out.println("       \"email\" : " +"\"" + doc.getEmail() +"\"" + " ,");
                            out.println("       \"valutazione\" : " +"\"" + l.getValutazione() +"\"" + " ,");

                            out.println("       \"pf\" : " +"\"" + doc.getPf() +"\"" + " ,");
                            out.println("       \"stelle\" : " +"\"" + doc.getStelle() +"\"" + " ,");
                            out.println("       \"prezzo\" : " +"\"" + l.getPrezzo() +"\"" );
                            out.println("     }");
                            if(j<lex.size()-1){
                                j++;
                                out.print(",");
                            }

                        }


                        out.println("]");

                        out.flush();

                    }
                    break;
                }

                case "getLezioniDaConfermare" : {
                    if (this.dao == null) {
                        out.println("dao is null");
                    }
                    else {

                        String userName = request.getParameter("mail");
                        ArrayList<Lezione> lex = dao.getLezioniDaConfermare(userName);
                        int j = 0;

                        out.println("[");
                        for (int i = 0; i < lex.size() ; i++) {
                            Lezione l = lex.get(i);
                            Corso c = dao.getCorsoByID(l.getCorso_ID());
                            Utente doc = dao.getUtenteByID(l.getDocente_ID());
                            out.println("     {");
                            out.println("       \"data\" : " + "\"" +l.getData() + "\"" + " ,");
                            out.println("       \"ora\" : " +  "\"" + l.getOra() + "\"" + " ,");
                            out.println("       \"nome_corso\": " +"\"" + c.getNome() + "\"" +" ,");
                            out.println("       \"nome_docente\": " +"\"" + doc.getNome() +"\"" + " ,");
                            out.println("       \"cognome_docente\" : " +"\"" + doc.getCognome() +"\"" + " ,");
                            out.println("       \"email\" : " +"\"" + doc.getEmail() +"\"" + " ,");
                            out.println("       \"valutazione\" : " +"\"" + l.getValutazione() +"\"" + " ,");

                            out.println("       \"pf\" : " +"\"" + doc.getPf() +"\"" + " ,");
                            out.println("       \"stelle\" : " +"\"" + doc.getStelle() +"\"" + " ,");
                            out.println("       \"prezzo\" : " +"\"" + l.getPrezzo() +"\"" );
                            out.println("     }");
                            if(j<lex.size()-1){
                                j++;
                                out.print(",");
                            }

                        }


                        out.println("]");

                        out.flush();

                    }
                    break;
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
                                        "\"state_description\"" + ":" + "\"lezione  rated\"" +
                                        "}");
                                out.flush();
                            }
                        }
                        catch (Error error){
                            out.print("{" +
                                    "\"rate_state\"" + ":" + "\"already\"" +" ,"+
                                    "\"state_description\"" + ":" + "\"this lezione  is already rated or doesnt exist or rate is 0(cant be)\"" +
                                    "}");
                            out.flush();
                        }





                    }
                    break;

                }

                case "prenotaLezione" : {
                    if (dao == null) {
                        out.println("dao is null");
                    } else {
                        boolean flag= false;
                        String data = request.getParameter("data");
                        String ora = request.getParameter("ora");
                        String docente =  request.getParameter("docente");
                        String utente =  request.getParameter("utente");
                        int Docente_ID = Dao.getIDbyUtente(request.getParameter("docente"));
                        int Utente_ID = Dao.getIDbyUtente(request.getParameter("utente"));


                        try {
                            flag = dao.prenotaLezione(data,ora,Docente_ID,Utente_ID);
                            if(flag){
                                out.print("{" +
                                        "\"book_state\"" + ":" + "\"true\"" +" ,"+
                                        "\"state_description\"" + ":" + "\"lezione prenotata\"" +
                                        "}");
                                out.flush();
                            }
                        }
                        catch (Error error){
                            out.print("{" +
                                    "\"book_state\"" + ":" + "\"false\"" +" ,"+
                                    "\"state_description\"" + ":" + "\"this lezione  is already booked or doesnt exist \"" +
                                    "}");
                            out.flush();
                        }





                    }
                    break;

                }
                case "annullaLezione" : {
                    if (dao == null) {
                        out.println("dao is null");
                    } else {
                        boolean flag= false;
                        String data = request.getParameter("data");
                        String ora = request.getParameter("ora");
                        String docente =  request.getParameter("docente");

                        int Docente_ID = Dao.getIDbyUtente(request.getParameter("docente"));



                        try {
                            flag = dao.annullaLezione(data,ora,Docente_ID);
                            if(flag){
                                out.print("[" +
                                         "\"true\"" +
                                        "]");
                                out.flush();
                            }
                        }
                        catch (Error error){
                            out.print("[" +
                                    "\"false\"" +
                                    "]");
                            out.flush();
                        }





                    }
                    break;

                }
                case "concludiLezione" : {
                    if (dao == null) {
                        out.println("dao is null");
                    } else {
                        boolean flag= false;
                        String data = request.getParameter("data");
                        String ora = request.getParameter("ora");
                        String docente =  request.getParameter("docente");

                        int Docente_ID = Dao.getIDbyUtente(request.getParameter("docente"));



                        try {
                            flag = dao.concludiLezione(data,ora,Docente_ID);
                            if(flag){
                                out.print("{" +
                                        "\"cancell_state\"" + ":" + "\"true\"" +" ,"+
                                        "\"state_description\"" + ":" + "\"lezione finished\"" +
                                        "}");
                                out.flush();
                            }
                        }
                        catch (Error error){
                            out.print("{" +
                                    "\"cancell_state\"" + ":" + "\"false\"" +" ,"+
                                    "\"state_description\"" + ":" + "\"this lezione  is already finished or doesnt exist \"" +
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






