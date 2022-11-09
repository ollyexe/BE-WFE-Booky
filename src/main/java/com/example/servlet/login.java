/*
package com.example.servlet;

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
@WebServlet(name = "login", value = "/login")
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

        response.setContentType("text/html;charset=UTF-8");
        String azione = request.getParameter("action");
        HttpSession s = request.getSession();
        if (azione!=null && azione.equals("invalida")) {
            s.invalidate();
            RequestDispatcher rd=request.getRequestDispatcher("/index.html");
            rd.forward(request, response);
        }
        else if(azione!=null && azione.equals("refresh")){
            HttpSession s1 = request.getSession();
            PrintWriter out = response.getWriter();
            String url = response.encodeURL("login");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Login</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<p>Sei collegato come: " + s1.getAttribute("username") + "</p>");
            out.println("<p>Ruolo: " + s1.getAttribute("ruolo") + "</p>");
            out.print("<p>Stato della sessione: ");
            if (s1.isNew())
                out.println(" nuova sessione</p>");
            else out.println(" vecchia sessione</p>");
            out.println("<p>ID di sessione: " + s1.getId() + "</p>");


            out.println("<form name=\"loginForm\" method=\"post\" action=\"registration\">\n" +
                    "    Account: <input class = \"d\" type=\"text\" name=\"account\" required> <br>\n" +
                    "    Password: <input class=\"d\" type=\"password\" name=\"pass\" required> <br>\n" +
                    "    <input type=\"radio\" id=\"ruolo\" name=\"ruolo\" value=\"utente\" required>\n" +
                    "    <label for=\"ruolo\">Utente</label>\n" +
                    "    <input type=\"radio\" id=\"docente\" name=\"ruolo\" value=\"docente\" required>\n" +
                    "    <label for=\"docente\">Docente</label><br>\n" +
                    "    <input type=\"submit\" value=\"Sign In\" />\n" +
                    "\n" +
                    "\n" +
                    "</form>");


            out.println("<p>Invalida <a href=\"" + url + "?action=invalida\"> la sessione</a></p>");
            out.println("<p>Ricarica <a href=\"" + url + "?action=refresh\"> la pagina</a></p>");
            out.println("</body>");
            out.println("</html>");

        }

        String username = request.getParameter("account");
        String password = request.getParameter("pass");
        Utente u = Dao.getUtente(username);
        PrintWriter out = response.getWriter();
        if (Dao.checkMD5(u.getPass(),password) && u.getRuolo().equals("admin")) {
            response.setContentType("text/html");


            if (username != null && password != null)
                s.setAttribute("username", username);
            s.setAttribute("ruolo", u.getRuolo());
            String url = response.encodeURL("login");

            try {
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet Login</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<p>Sei collegato come: " + s.getAttribute("username") + "</p>");
                out.println("<p>Ruolo: " + s.getAttribute("ruolo") + "</p>");
                out.print("<p>Stato della sessione: ");
                if (s.isNew())
                    out.println(" nuova sessione</p>");
                else out.println(" vecchia sessione</p>");
                out.println("<p>ID di sessione: " + s.getId() + "</p>");


                out.println("<form name=\"loginForm\" method=\"post\" action=\"registration\">\n" +
                        "    Account: <input class = \"d\" type=\"text\" name=\"account\" required> <br>\n" +
                        "    Password: <input class=\"d\" type=\"password\" name=\"pass\" required> <br>\n" +
                        "    <input type=\"radio\" id=\"ruolo\" name=\"ruolo\" value=\"utente\" required>\n" +
                        "    <label for=\"ruolo\">Utente</label>\n" +
                        "    <input type=\"radio\" id=\"docente\" name=\"ruolo\" value=\"docente\" required>\n" +
                        "    <label for=\"docente\">Docente</label><br>\n" +
                        "    <input type=\"submit\" value=\"Sign Up\" />\n" +
                        "\n" +
                        "\n" +
                        "</form>");


                out.println("<p>Invalida <a href=\"" + url + "?action=invalida\"> la sessione</a></p>");
                out.println("<p>Ricarica <a href=\"" + url + "?action=refresh\"> la pagina</a></p>");
                out.println("</body>");
                out.println("</html>");
            }
            finally{
                out.close();

        }
        }
        else
        {

            RequestDispatcher rd = request.getRequestDispatcher("/index.html");
            rd.forward(request, response);
        }
    }

}

*/



