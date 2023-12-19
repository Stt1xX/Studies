package com.example;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;

import static com.example.ServletUtils.getInnerUrl;

public class ControllerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (!isUrlValid(request)){
            response.setStatus(400);
            return;
        }

        if (isGetSecondPage(request)){
            RequestDispatcher dispatcher = request.getRequestDispatcher("/secondPage.jsp");
            dispatcher.forward(request, response);
            return;
        }
        if (getInnerUrl(request).startsWith("/pages/resultPage")){
            RequestDispatcher dispatcher = request.getRequestDispatcher("/Checker");
            dispatcher.forward(request, response);
            return;
        }
        if (isGetPoints(request)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/GetPoints");
            dispatcher.forward(request, response);
            return;
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");  // index.html
        dispatcher.forward(request, response);
    }

    private boolean isGetPoints(HttpServletRequest request){
        return request.getParameter("Points") != null;
    }
    private boolean isGetSecondPage(HttpServletRequest request){
        return request.getAttribute("IsHit") != null;
    }

    private boolean isUrlValid(HttpServletRequest request){
        String url = getInnerUrl(request);
        return url.equals("/pages/") || url.startsWith("/pages/resultPage");
    }
}
