package com.auctionsite.controllers;

import com.auctionsite.beans.AuctionBean;
import com.auctionsite.dao.AuctionDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RefreshAuctionsServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
        String origin = "/JSP/" + "viewAuc.jsp";
        AuctionDao aucDao = new AuctionDao();
        aucDao.refreshAuctions(aucDao.allAuctions());
        request.getRequestDispatcher(origin).forward(request, response);
    }

}
