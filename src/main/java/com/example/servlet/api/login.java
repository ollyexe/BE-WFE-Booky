
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
@WebServlet(name = "login", value = "/api/login")
public class login extends HttpServlet {
    Dao dao;


    public void init(ServletConfig config) {

        dao = (Dao) config.getServletContext().getAttribute("dao");


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


        String userName = request.getParameter("mail");

        PrintWriter out = response.getWriter();
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

    }
}





