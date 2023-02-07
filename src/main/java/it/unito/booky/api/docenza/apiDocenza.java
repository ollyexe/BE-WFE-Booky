
package it.unito.booky.api.docenza;

import Dao.Dao;
import Dao.Docenza;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "apiDocenza", value = "/apiDocenza")
public class apiDocenza extends HttpServlet {
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
            case "insertDocenza" : {
                if (dao == null) {
                    out.println("dao is null");
                } else {
                    int id_prof= Integer.parseInt(request.getParameter("prof"));
                    int id_cor  = Integer.parseInt(request.getParameter("corso"));
                    String dataStart  = request.getParameter("start");
                    String dataEnd  = request.getParameter("end");

                    try {
                        dao.insertDocenza(id_cor,id_prof,dataStart,dataEnd);


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
            case "getAllDocenza" : {
                if (dao == null) {
                    out.println("dao is null");
                } else {
                    int i = 0;
                    ArrayList<Docenza> docenze = dao.getAllDocenza();

                    out.println( "[");
                    for (Docenza d : docenze){
                        out.println( "{");
                        out.println("\"docente_id\"" + ":" + "\""+d.getDocente() + "\"" + ",");
                        out.println("\"corso_id\"" + ":" + "\""+d.getCorso() + "\"" + ",");

                        out.println("\"nome_docente\"" + ":" + "\""+Dao.getUtenteByID(d.getDocente()).getNome() + "\"" + ",");
                        out.println("\"cognome_docente\"" + ":" + "\""+Dao.getUtenteByID(d.getDocente()).getCognome()+ "\"" + ",");
                        out.println("\"corso\"" + ":" + "\""+Dao.getCorsoByID(d.getCorso()).getNome()+ "\"" + ",");
                        out.println("\"data_start\"" + ":" + "\""+d.getData_start()+ "\"" + ",");
                        out.println("\"Data_end\"" + ":" + "\""+d.getData_end()+ "\"" );
                        out.println("     }");
                        if(i<docenze.size()-1){
                            i++;
                            out.print(",");
                        }
                    }
                    out.println( "]");


                }
                break;
            }
            case "deleteDocenza" : {// to do
                if (dao == null) {
                    out.println("dao is null");
                } else {
                    int id_prof= Integer.parseInt(request.getParameter("prof"));
                    int id_cor  = Integer.parseInt(request.getParameter("corso"));
                    String dataStart  = request.getParameter("start");

                    String corso = request.getParameter("nome");
                    try {
                         if (dao.deleteDocenza(id_cor,id_prof,dataStart)){
                             out.print("{" +
                                     "\"delete_state\"" + ":" + "\"true\"" +" ,"+
                                     "\"state_description\"" + ":" + "\"User not exists\"" +

                                     "}");
                             out.flush();
                             break;
                         }
                         else {
                             out.print("{" +
                                     "\"delete_state\"" + ":" + "\"false\"" +" ,"+
                                     "\"state_description\"" + ":" + "\"User not exists\"" +

                                     "}");
                             out.flush();
                             break;
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





