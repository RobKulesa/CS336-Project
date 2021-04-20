package com.auctionsite.controllers;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class GoBackServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
        String origin = "/JSP/" + request.getParameter("origin");
        request.getRequestDispatcher(origin).forward(request, response);
    }
}