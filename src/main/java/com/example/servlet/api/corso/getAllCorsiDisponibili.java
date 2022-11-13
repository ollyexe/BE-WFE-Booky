//package com.example.servlet.api.corso;
//
//
//import java.io.*;
//import java.util.ArrayList;
//import javax.servlet.ServletConfig;
//import javax.servlet.ServletException;
//import javax.servlet.http.*;
//import javax.servlet.annotation.*;
//import Dao.*;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonParser;
//
//@WebServlet(name = "getAllCorsiDisponibili", value = "/api/getAllCorsiDisponibili")
//public class getAllCorsiDisponibili extends HttpServlet {
//    private Dao dao;
//
//
//    public void init(ServletConfig config) {
//
//        this.dao  = new Dao(config.getServletContext().getInitParameter("dbURL"),config.getServletContext().getInitParameter("dbUser"),config.getServletContext().getInitParameter("dbUserPass"));
//        System.out.println(dao==null);
//
//    }
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    private void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        response.setContentType("application/json");
//
//        HttpSession s = request.getSession();
//
//        if (this.dao == null) {//to fix
//
//            PrintWriter out = response.getWriter();
//            out.println("dao is null");
//        } else {
//
//            Gson gson = new GsonBuilder().setPrettyPrinting().create();
//
//            ArrayList<Corso> corsi = dao.getAllCorsiDisponibili();
//            String json = gson.toJson(corsi);
//            JsonElement je = JsonParser.parseString(json);
//            PrintWriter out = response.getWriter();
//            out.println("{"+"\"Corsi\" : "+gson.toJson(je)+ "}");
//            out.flush();
//
//
//        }
//    }
//}
//
//
//
//
//
//
