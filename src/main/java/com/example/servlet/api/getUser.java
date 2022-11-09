package com.example.servlet.api;


import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import Dao.*;
import com.google.gson.Gson;

@WebServlet(name = "getUser", value = "/api/getUser")
public class getUser extends HttpServlet {
    Dao dao;
    private Gson gson = new Gson();


    public void init(ServletConfig config) {

        dao = (Dao) config.getServletContext().getAttribute("dao");


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("userName");
      /*  Utente u = Dao.getUtente(userName);
        String utenteJson = this.gson.toJson(u);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(utenteJson);
        out.flush();

       */
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }


}





