package it.unito.booky.api.corso;


import java.io.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import Dao.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@WebServlet(name = "apiCorso", value = "/apiCorso")
public class apiCorso extends HttpServlet {
    private Dao dao;

    @Override
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
                case "getAllCorsi" : {
                    if (dao == null) {
                        out.println("dao is null");
                    } else {



                        out.println("{");
                        out.println("       \"corsi\": ");
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        String json = gson.toJson(dao.getCorsiByDoc("all"));
                        JsonElement je = JsonParser.parseString(json);
                        out.println( gson.toJson(je) );
                        out.println("}");
                        out.flush();



                    }
                    break;
                }
                case "getCorsiByDoc" : {
                    if (dao == null) {
                        out.println("dao is null");
                    } else {
                        out.println("{");
                        out.println("       \"corsi\": ");
                        String mail = request.getParameter("mail");
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        String json = gson.toJson(dao.getCorsiByDoc(mail));
                        JsonElement je = JsonParser.parseString(json);
                        out.println( gson.toJson(je) );
                        out.println("}");
                        out.flush();



                    }
                    break;
                }
                case "inserisciCorso" : {
                    if (dao == null) {
                        out.println("dao is null");
                    } else {
                        String cor = request.getParameter("nome");

                       try{
                           if (dao.insertCorso(cor)){
                               out.print("{" +
                                       "\"insert_state\"" + ":" + "\"true\"" +" ,"+
                                       "\"state_description\"" + ":" + "\"corso succesfuly registred\"" +
                                       "}");
                               out.flush();
                           }
                           else {
                               out.print("{" +
                                       "\"insert_state\"" + ":" + "\"false\"" +" ,"+
                                       "\"state_description\"" + ":" + "\"???\"" +
                                       "}");
                               out.flush();
                           }
                        }catch (Error e){
                           out.print("{" +
                                   "\"insert_state\"" + ":" + "\"exists\"" +" ,"+
                                   "\"state_description\"" + ":" + "\"corso already exists\"" +
                                   "}");
                           out.flush();
                       }




                    }
                    break;
                }
                case "getCorsiAttivi" : {
                    if (dao == null) {
                        out.println("dao is null");
                    } else {



                        out.println("{");
                        out.println("       \"corsi\": ");
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        String json = gson.toJson(dao.getAllCorsi());
                        JsonElement je = JsonParser.parseString(json);
                        out.println( gson.toJson(je) );
                        out.println("}");
                        out.flush();



                    }
                    break;
                }
                case "deleteCorso" : {
                    if (dao == null) {
                        out.println("dao is null");
                    } else {
                        boolean flag= false;
                        String corso = request.getParameter("nome");
                        try {
                            flag = dao.deleteCorso(corso);
                            if(flag){
                                out.print("{" +
                                        "\"delete_state\"" + ":" + "\"succes\"" +" ,"+
                                        "\"state_description\"" + ":" + "\"corso with this name was deleted\"" +
                                        "}");
                                out.flush();
                            }
                            else {
                                out.print("{" +
                                        "\"delete_state\"" + ":" + "\"false\"" +" ,"+
                                        "\"state_description\"" + ":" + "\"user with this email was not deleted\"" +
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






            }
        }
        else{
            out.println("Selected path doesnt exists ");
            out.flush();
        }
    }
}






