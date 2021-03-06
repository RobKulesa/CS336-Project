package com.auctionsite.controllers;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
        HttpSession session = request.getSession(false);
        if(session!=null) {
            session.invalidate();
            request.setAttribute("errMessage", "You have logged out successfully");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/JSP/LandingPage.jsp");
            requestDispatcher.forward(request, response);
            System.out.println("Logged out");
        }
    }
}
