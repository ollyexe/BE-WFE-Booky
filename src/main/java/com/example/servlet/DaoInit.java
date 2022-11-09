package com.example.servlet;

import java.io.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import Dao.*;
@WebServlet(name = "DaoInit", value = "/DaoInit", loadOnStartup = 1, asyncSupported = true)
public class DaoInit extends HttpServlet {











    public void init(ServletConfig config) {

        Dao dao  = new Dao(config.getServletContext().getInitParameter("dbURL"),config.getServletContext().getInitParameter("dbUser"),config.getServletContext().getInitParameter("dbUserPass"));

        getServletContext().setAttribute("Dao",dao);

    }



    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

    }



}