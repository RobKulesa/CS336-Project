package com.auctionsite.controllers;

import com.auctionsite.beans.AccountInfoBean;
import com.auctionsite.beans.ThreadBean;
import com.auctionsite.dao.AccountInfoDao;
import com.auctionsite.dao.ThreadsDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

public class AccountInfoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AccountInfoDao accountInfoDao = new AccountInfoDao();
        ArrayList<AccountInfoBean> accounts = accountInfoDao.getAccountInfos();
        request.setAttribute("accounts", accounts);
        request.getRequestDispatcher("/JSP/AccountInfoView.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        update(req, resp);
    }

    protected void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AccountInfoDao accountInfoDao = new AccountInfoDao();
        ArrayList<AccountInfoBean> accounts = accountInfoDao.getAccountInfos();
        String reqtype = req.getParameter("reqtype");
        if(reqtype.equals("editinfo")) {
            int uid = Integer.valueOf(req.getParameter("uid"));
            String newusername = req.getParameter("newusername");
            String newpwd = req.getParameter("newpwd");
            if(newusername.isBlank() && newpwd.isBlank()) {
                req.setAttribute("errMessage", "Both fields cannot be blank!");
                req.setAttribute("accounts", accounts);
                req.getRequestDispatcher("/JSP/ThreadsView.jsp").forward(req, resp);
                return;
            }
            if(newusername.isBlank())
                newusername = accountInfoDao.getUsernameFromUid(uid);
            if(newpwd.isBlank())
                newpwd = accountInfoDao.getPwdFromUid(uid);

            accountInfoDao.updateUser(uid, newusername, newpwd);

            accounts = accountInfoDao.getAccountInfos();
            req.setAttribute("accounts", accounts);
            req.getRequestDispatcher("/JSP/AccountInfoView.jsp").forward(req, resp);
        } else if(reqtype.equals("deleteaccount")) {
            int uid = Integer.valueOf(req.getParameter("uid"));
            accountInfoDao.deleteUser(uid);

            accounts = accountInfoDao.getAccountInfos();
            req.setAttribute("accounts", accounts);
            req.getRequestDispatcher("/JSP/AccountInfoView.jsp").forward(req, resp);
        } else if(reqtype.equals("deletebid")) {
            int bid = Integer.valueOf(req.getParameter("bid"));
            accountInfoDao.deleteBid(bid);

            accounts = accountInfoDao.getAccountInfos();
            req.setAttribute("accounts", accounts);
            req.getRequestDispatcher("/JSP/AccountInfoView.jsp").forward(req, resp);
        } else if(reqtype.equals("deleteauction")) {
            int aid = Integer.valueOf(req.getParameter("aid"));
            accountInfoDao.deleteAuction(aid);

            accounts = accountInfoDao.getAccountInfos();
            req.setAttribute("accounts", accounts);
            req.getRequestDispatcher("/JSP/AccountInfoView.jsp").forward(req, resp);
        }
    }

}
