
package com.example.servlet.api.utenti;

import java.io.*;
import java.util.ArrayList;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.xml.xpath.XPath;

import Dao.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@WebServlet(name = "login", value = "/apiUtente")
public class login extends HttpServlet {
    Dao dao;


    public void init(ServletConfig config) {


         dao  = new Dao(config.getServletContext().getInitParameter("dbURL"),config.getServletContext().getInitParameter("dbUser"),config.getServletContext().getInitParameter("dbUserPass"));

        getServletContext().setAttribute("Dao",dao);
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
        String path = request.getParameter("path");




        response.setContentType("application/json");


      switch(path){
          case "login" : {
              String userName = request.getParameter("mail");
              String password = request.getParameter("pass");
              Utente u = Dao.getUtente(userName);
              if (u==null) {
                  out.print("{" +
                          "\"login_state\"" + ":" + "\"notExist\"" +
                          "}");
                  out.flush();
              }

              if (Dao.checkMD5(u.getPassword(), password)) {

                  out.print("{" +
                          "\"login_state\"" + ":" + "\"true\"" +
                          "}");
                  out.flush();


              }
              else {
                  out.print("{" +
                          "\"login_state\"" + ":" + "\"false\"" +
                          "}");
                  out.flush();
              }
            break;
          }
          case "getAllDocenti" : {
              if (dao == null) {
                  out.println("dao is null");
              } else {

                  ArrayList<Utente> docenti = dao.getAllProfessori();
                  Gson gson = new GsonBuilder().setPrettyPrinting().create();
                  String json = gson.toJson(docenti);
                  JsonElement je = JsonParser.parseString(json);
                  out.println("{"+"\"Docenti\" : "+gson.toJson(je)+ "}");
                  out.flush();


              }
              break;
          }
      }
    }
}





