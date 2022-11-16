package com.example.servlet.api.corso;


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

@WebServlet(name = "apiCorso", value = "/apiCorso")
public class apiCorso extends HttpServlet {
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
                case "getAllCorsiDisponibili" : {
                    if (this.dao == null) {//to fix
                        out.println("dao is null");
                    } else {

                        Gson gson = new GsonBuilder().setPrettyPrinting().create();

                        ArrayList<Corso> corsi = dao.getAllCorsiDisponibili();
                        String json = gson.toJson(corsi);
                        JsonElement je = JsonParser.parseString(json);
                        out.println("{"+"\"Corsi\" : "+gson.toJson(je)+ "}");
                        out.flush();


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






