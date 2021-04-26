package com.auctionsite.controllers;
import com.auctionsite.beans.AlertBean;
import com.auctionsite.dao.AlertDao;

import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
public class ViewAlertsServlet extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        AlertDao alertDao = new AlertDao();
        ArrayList<AlertBean> alerts = alertDao.getAllAlerts(Integer.parseInt(request.getParameter("uid")));
        request.setAttribute("userAlerts", alerts);
        request.getRequestDispatcher("/JSP/viewMessages.jsp").forward(request, response);
    }

}
