package com.example;


import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ControllerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (isHaveParams(request)){
            response.sendRedirect( request.getContextPath() + "/Checker?xCoord=" + request.getParameter("xCoord") + "&yCoord=" +
                    request.getParameter("yCoord") + "&Radius=" + request.getParameter("Radius"));
//            RequestDispatcher dispatcher = request.getRequestDispatcher("/Checker");
//            dispatcher.forward(request, response);
            return;
        }
        if (request.getParameter("Points") != null){
            response.setContentType("text/html");
            response.setStatus(200);
            PrintWriter out = response.getWriter();
            HttpSession session = request.getSession();
            List<Attempt> list = (ArrayList<Attempt>) session.getAttribute("attempts");
            if (list == null){
                session.setAttribute("attempts", new ArrayList<String>());
                list = (ArrayList<Attempt>) session.getAttribute("attempts");
            }
            for(Attempt attempt : list){
                out.println(attempt.getX() + " " + attempt.getY());
            }
            response.flushBuffer();
            return;
        }
            response.sendRedirect(request.getContextPath());
    }

    private boolean isHaveParams(HttpServletRequest request){
            if (request.getParameter("xCoord") != null &&
            request.getParameter("yCoord") != null &&
            request.getParameter("Radius") != null){
                return true;
            }
            return false;
    }
}
