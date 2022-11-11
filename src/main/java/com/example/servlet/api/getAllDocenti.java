package com.example.servlet.api;


import java.io.*;
import java.util.ArrayList;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import Dao.*;
import com.google.gson.Gson;

@WebServlet(name = "getAllDocenti", value = "/api/getAllDocenti")
public class getAllDocenti extends HttpServlet {
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

            Gson gson = new Gson();
            ArrayList<Utente> docenti = dao.getAllProfessori();
            PrintWriter out = response.getWriter();
            out.println(gson.toJson(docenti));
            out.flush();


        }
    }
}






