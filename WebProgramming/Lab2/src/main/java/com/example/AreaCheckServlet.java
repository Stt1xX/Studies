package com.example;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class AreaCheckServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html");
        if (request.getParameter("xCoord") == null ||
                request.getParameter("yCoord") == null ||
                request.getParameter("Radius") == null){
            response.setStatus(400);
        }
        int x = Integer.parseInt(request.getParameter("xCoord"));
        double y = Double.parseDouble(request.getParameter("yCoord"));
        double radius = Double.parseDouble(request.getParameter("Radius"));
        String time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
        String isHit = isHit(x, y, radius);

        Attempt attempt = new Attempt(x, y, radius, isHit, time);

        if (!Attempt.attemptIsValid(attempt)){
            response.setStatus(400);
            return;
        }
        addAttempt(request, attempt);

        request.setAttribute("xCoord", x);
        request.setAttribute("yCoord", y);
        request.setAttribute("Radius", radius);
        request.setAttribute("IsHit", isHit);
        request.setAttribute("Time", time);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/");
        dispatcher.forward(request, response);
    }

    private String isHit(Integer x, Double y, Double r){
        if (x >= 0 && y >= 0 && y <= -0.5*x + 0.5*r){
            return "Yes";
        }
        if (x <= 0 && y <= 0 && x >= -r && y >= -0.5*r){
            return "Yes";
        }
        if (x > 0 && y < 0 && x*x + y*y <= 0.25*r*r){
            return "Yes";
        }
        return "No";
    }

    private void addAttempt(HttpServletRequest request, Attempt attempt){
        HttpSession session = request.getSession();

        List<Attempt> list = (ArrayList<Attempt>) session.getAttribute("attempts");
        if (list == null){
            session.setAttribute("attempts", new ArrayList<String>());
            list = (ArrayList<Attempt>) session.getAttribute("attempts");
        }

        list.add(attempt);
        session.setAttribute("attempts", list);
    }
}
