package com.auctionsite.controllers;

import com.auctionsite.beans.BidBean;
import com.auctionsite.dao.CreateBidDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CreateBidServlet extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        //Check for blank fields

        //upper limit CANNOT be blank
        String secretUpperLimit = request.getParameter("upper_limit");
        String origin = "/JSP/" + "viewAuc.jsp";
        if(secretUpperLimit.isBlank()) {
            request.setAttribute("/JSP/createBid.jsp", "Fields cannot be blank!");
            request.getRequestDispatcher(origin).forward(request, response);
            return;
        }

        //amntStr is permitted to be blank
        String amntStr = request.getParameter("desired_increment");
        if(amntStr.isBlank()){
            amntStr = request.getParameter("bid_increment");
        }

        //Check for formatting errors


        //Attempt user creation
        try{
            //Process Data before upload
            HttpSession session = request.getSession();
            int uid = Integer.parseInt((String)session.getAttribute("uid"));
            int aid = Integer.parseInt(request.getParameter("aid"));
            float secretUpperLimitFlt = Float.parseFloat(secretUpperLimit);
            float amnt = Float.parseFloat(amntStr);
            //Create BidBean model
            BidBean bidBean = new BidBean();
            bidBean.setDesiredIncrement(amnt);
            bidBean.setSecretUpperLimit(secretUpperLimitFlt);
            bidBean.setUID(uid);
            bidBean.setAID(aid);

            //Upload attempt
            CreateBidDao bidDao = new CreateBidDao();
            if(!bidDao.isGoodAutoBid(bidBean)){
                request.setAttribute("errMessage", "Invalid Parameters detected. Please try again");
                request.getRequestDispatcher("/JSP/createBid.jsp").forward(request, response);
                return;
            }
            bidDao.uploadBid(bidBean);
            request.setAttribute("createMessage", "Successfully created bid!");
            request.getRequestDispatcher(origin).forward(request, response);

        }catch(Exception e){
            request.setAttribute("errMessage", e.getMessage());
            e.printStackTrace();
            request.getRequestDispatcher("/JSP/createBid.jsp").forward(request, response);
        }
    }
}
