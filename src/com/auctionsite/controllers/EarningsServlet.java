package com.auctionsite.controllers;

import com.auctionsite.beans.EarningsBean;
import com.auctionsite.dao.EarningsDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EarningsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String earningsType = request.getParameter("earningstype");
        EarningsDao earningsDao = new EarningsDao();
        EarningsBean earningsBean;
        int limit;
        switch(earningsType) {
            case "1":
                earningsBean = earningsDao.getTotalEarnings();
                break;
            case "2":
                earningsBean = earningsDao.getEarningsPerItem();
                break;
            case "3":
                earningsBean = earningsDao.getEarningsPerItemType();
                break;
            case "4":
                earningsBean = earningsDao.getEarningsPerUser();
                break;
            case "5":
                limit = Integer.parseInt(request.getParameter("limit5"));
                earningsBean = earningsDao.getEarningsBestSellingItems(limit);
                break;
            case "6":
                limit = Integer.parseInt(request.getParameter("limit6"));
                earningsBean = earningsDao.getEarningsBestUsers(limit);
                break;
            default:
                request.setAttribute("earningsMessage", "Report failed, please try again.");
                request.getRequestDispatcher("/JSP/Admin.jsp").forward(request, response);
                return;
        }
        System.out.println(earningsBean);
        request.setAttribute("earningsBean", earningsBean);
        request.getRequestDispatcher("/JSP/EarningsView.jsp").forward(request, response);
    }
}
