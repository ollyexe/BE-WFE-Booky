
package it.unito.booky.api.utenti;

import java.io.*;
import java.util.ArrayList;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import Dao.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import org.apache.commons.lang3.EnumUtils;

@WebServlet(name = "apiUtente", value = "/apiUtente")
public class apiUtente extends HttpServlet {
    static Dao dao;


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

        PrintWriter out = response.getWriter();
        //System.out.println(request.getParameter("path"));
        response.setContentType("application/json");


    if(request.getParameter("path")!=null){
        switch(request.getParameter("path")){
            case "login" : {
                if (dao == null) {
                    out.println("dao is null");
                }else {
                    String userName = request.getParameter("mail");
                    String password = request.getParameter("pass");
                    Utente u = dao.getUtente(userName);

                    if(u==null){
                        out.print("{" +
                                "\"login_state\"" + ":" + "\"other\"" +" ,"+
                                "\"state_description\"" + ":" + "\"User not exists\"" +

                                "}");
                        out.flush();
                        break;
                    }


                     if (Dao.checkMD5(u.getPassword(), password)&&u!=null) {

                        out.print("{" +
                                "\"login_state\"" + ":" + "\"true\"" +" ,"+
                                "\"state_description\"" + ":" + "\"successful login\"" +

                                "}");
                        out.flush();
                         HttpSession s = request.getSession();
                         s.setAttribute("login_state",true);
                         s.setAttribute("email",u.getEmail());

                    }
                     else {


                             out.print("{" +
                                     "\"login_state\"" + ":" + "\"false\"" +" ,"+
                                     "\"state_description\"" + ":" + "\"Password does not match\"" +

                                     "}");
                             out.flush();


                    }
                    break;
                }
            }

            case "logout" : {
                if (dao == null) {
                    out.println("dao is null");
                }else {

                    HttpSession s = request.getSession();
                    s.invalidate();
                    break;

                }
            }
            case "registration" : {
                if (dao == null) {
                    out.println("dao is null");
                } else {
                    String email = request.getParameter("mail");
                    String password = request.getParameter("pass");
                    String nome = request.getParameter("nome");
                    String cognome = request.getParameter("cognome");
                    //ceck temporaneo --> succesivamente con la user session si fara il check se la mail esiste gia
                    //prima check   --> se true ritorno alla pagina login con i dati settati dalla sessione
                    //se check false si procede con la insertion

                    try {
                        if(dao.insertUtente(new Utente(0,email,password,nome,cognome,"utente"))){
                            out.print("{" +
                                    "\"registration_state\"" + ":" + "\"succesful\"" +" ,"+
                                    "\"state_description\"" + ":" + "\"user succesfuly registred\"" +
                                    "}");
                            out.flush();
                        }
                        else {
                            out.print("{" +
                                    "\"registration_state\"" + ":" + "\"fail\"" +" ,"+
                                    "\"state_description\"" + ":" + "\"failed to register\"" +
                                    "}");
                            out.flush();
                        }

                    }
                    catch (Error erro){
                        out.print("{" +
                                "\"registration_state\"" + ":" + "\"exists\"" +" ,"+
                                "\"state_description\"" + ":" + "\"user with this email already exists\"" +
                                "}");
                        out.flush();
                    }

                }
                break;
            }
            case "getAllDocenti" : {
                if (dao == null) {
                    out.println("dao is null");
                } else {
                    int i = 0;
                    ArrayList<Utente> docenti = dao.getAllProfessori();
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();

                    out.println( "[");
                    for (Utente u : docenti){
                        out.println( "{");
                        out.println("\"ID\"" + ":" + "\""+u.getID()+ "\"" + ",");
                        out.println("\"email\"" + ":" + "\""+u.getEmail()+ "\"" + ",");
                        out.println("\"password\"" + ":" + "\""+u.getPassword()+ "\"" + ",");
                        out.println("\"nome\"" + ":" + "\""+u.getNome()+ "\"" + ",");
                        out.println("\"cognome\"" + ":" + "\""+u.getCognome()+ "\"" + ",");
                        out.println("\"pf\"" + ":" + "\""+u.getPf()+ "\"" + ",");
                        out.println("\"stelle\"" + ":" + "\""+u.getStelle()+ "\"" + ",");

                        out.println( "\"corsi\"" + ":"+gson.toJson(JsonParser.parseString(gson.toJson(dao.getCorsiByDoc(u.getEmail())))) );
                        out.println("     }");
                        if(i<docenti.size()-1){
                            i++;
                            out.print(",");
                        }
                    }
                    out.println( "]");


                    out.flush();


                }
                break;
            }
            case "sessionLogin" : {

                HttpSession s = request.getSession();

                if (s.getAttribute("login_state")==null){
                    out.print("{" +
                            "\"login_state\"" + ":" + "\"false\"" +" ,"+
                            "\"email\"" + ":" + "\"\"" +

                            "}");
                    out.flush();
                }
                else {
                    out.print("{" +
                            "\"login_state\"" + ":" + "\"true\"" +" ,"+
                            "\"email\"" + ":" + "\""+s.getAttribute("email")+"\"" +

                            "}");
                    out.flush();
                }

                break;


            }
            case "getDocByCorso" : {
                if (dao == null) {
                    out.println("dao is null");
                } else {
                    String corso = request.getParameter("corso");
                    ArrayList<Utente> docenti = dao.getDocByCorso(corso);
                    int i = 0;
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();

                    out.println( "[");
                    for (Utente u : docenti){
                        out.println( "{");
                        out.println("\"ID\"" + ":" + "\""+u.getID()+ "\"" + ",");
                        out.println("\"email\"" + ":" + "\""+u.getEmail()+ "\"" + ",");
                        out.println("\"password\"" + ":" + "\""+u.getPassword()+ "\"" + ",");
                        out.println("\"nome\"" + ":" + "\""+u.getNome()+ "\"" + ",");
                        out.println("\"cognome\"" + ":" + "\""+u.getCognome()+ "\"" + ",");
                        out.println("\"pf\"" + ":" + "\""+u.getPf()+ "\"" + ",");
                        out.println("\"stelle\"" + ":" + "\""+u.getStelle()+ "\"" + ",");

                        out.println( "\"corsi\"" + ":"+gson.toJson(JsonParser.parseString(gson.toJson(dao.getCorsiByDoc(u.getEmail())))) );
                        out.println("     }");
                        if(i<docenti.size()-1){
                            i++;
                            out.print(",");
                        }
                    }
                    out.println( "]");


                    out.flush();



                }
                break;
            }
            case "deleteUtente" : {
                if (dao == null) {
                    out.println("dao is null");
                } else {
                    boolean flag= false;
                    String email = request.getParameter("mail");
                    try {
                        flag = dao.deleteUtente(email);
                        if(flag){
                            out.print("{" +
                                    "\"delete_state\"" + ":" + "\"succes\"" +" ,"+
                                    "\"state_description\"" + ":" + "\"user with this email was deleted\"" +
                                    "}");
                            out.flush();
                        }
                    }
                    catch (Error error){
                        out.print("{" +
                                "\"delete_state\"" + ":" + "\"already\"" +" ,"+
                                "\"state_description\"" + ":" + "\"user with this email is already deleted or doesnt exist\"" +
                                "}");
                        out.flush();
                    }





                }
                break;

            }
            case "changePassword" : {
                if (dao == null) {
                    out.println("dao is null");
                } else {
                    boolean flag= false;
                    String email = request.getParameter("mail");
                    String password = request.getParameter("pass");
                    try {
                        flag = dao.changePass(email,password);
                        if(flag){
                            out.print("{" +
                                    "\"change_state\"" + ":" + "\"true\"" +" ,"+
                                    "\"state_description\"" + ":" + "\"user with this email was deleted\"" +
                                    "}");
                            out.flush();
                        }
                        else {
                            out.print("{" +
                                    "\"change_state\"" + ":" + "\"fail\"" +" ,"+
                                    "\"state_description\"" + ":" + "\"user with this email is already deleted or doesnt exist\"" +
                                    "}");
                            out.flush();
                        }

                    }
                    catch (Error error){
                        out.print("{" +
                                "\"change_state\"" + ":" + "\"fail\"" +" ,"+
                                "\"state_description\"" + ":" + "\"user with this email is already deleted or doesnt exist\"" +
                                "}");
                        out.flush();
                    }





                }
                break;

            }
            case "getMiniUser" : {
                if (dao == null) {
                    out.println("dao is null");
                } else {
                    String userName = request.getParameter("mail");
                    Utente u = dao.getMiniUtente(userName);
                    out.println("{");
                    out.println("       \"nome\": " +"\"" + u.getNome() + "\"" +" ,");
                    out.println("       \"cognome\": " +"\"" + u.getCognome() +"\"" + " ,");
                    out.println("       \"ruolo\" : " +"\"" + u.getRuolo() +"\"" + " ,");
                    out.println("       \"pf\" : " +"\"" + u.getPf() +"\"" );
                    out.println("}");




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





