package com.auctionsite.controllers;

import com.auctionsite.beans.LoginBean;
import com.auctionsite.dao.LoginDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        LoginBean loginBean = new LoginBean();

        loginBean.setUsername(username);
        loginBean.setPassword(password);
        LoginDao loginDao = new LoginDao();

        try {
            String userType = loginDao.authenticateUser(loginBean);
            HttpSession session = request.getSession();
            switch(userType) {
                case "admin":
                    session.setAttribute("admin", username);
                    session.setAttribute("usertype", "admin");
                    request.setAttribute("username", username);
                    request.getRequestDispatcher("/JSP/Admin.jsp").forward(request, response);
                    break;
                case "customer rep":
                    session.setAttribute("customerrep", username);
                    session.setAttribute("usertype", "customerrep");
                    request.setAttribute("username", username);
                    request.getRequestDispatcher("/JSP/CustomerRep.jsp").forward(request, response);
                    break;
                case "end user":
                    session.setAttribute("enduser", username);
                    session.setAttribute("usertype", "enduser");
                    request.setAttribute("username", username);
                    request.getRequestDispatcher("/JSP/EndUser.jsp").forward(request, response);
                    break;
                default:
                    request.setAttribute("errMessage", "Failed to login, please try again...");
                    request.getRequestDispatcher("/JSP/LandingPage.jsp").forward(request, response);
                    break;
            }
        } catch (Exception e) {
            request.setAttribute("errMessage", e.getMessage());
            request.getRequestDispatcher("/JSP/LandingPage.jsp").forward(request, response);
        }
    }
}