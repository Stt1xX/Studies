package com.example;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.PrintWriter;

public class PointsGetterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        response.setStatus(200);
        PrintWriter out = response.getWriter();

        List<Attempt> list = getAttemptsList(request);

        for(Attempt attempt : list){
            if (!Attempt.attemptIsValid(attempt)){
                response.setStatus(400);
                return;
            }
            out.println(attempt.getX() + " " + attempt.getY());
        }
        response.flushBuffer();
    }

    private ArrayList<Attempt> getAttemptsList(HttpServletRequest request){
        HttpSession session = request.getSession();
        ArrayList<Attempt> list = (ArrayList<Attempt>) session.getAttribute("attempts");
        if (list == null){
            session.setAttribute("attempts", new ArrayList<String>());
            list = (ArrayList<Attempt>) session.getAttribute("attempts");
        }
        return list;
    }

}
