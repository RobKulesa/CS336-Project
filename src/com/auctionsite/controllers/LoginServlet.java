package com.auctionsite.controllers;

import com.auctionsite.bean.LoginBean;
import com.auctionsite.dao.LoginDao;
import com.auctionsite.dao.UserType;

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
            UserType userType = loginDao.authenticateUser(loginBean);
            HttpSession session = request.getSession();
            switch(userType) {
                case admin:
                    session.setAttribute("admin", username);
                    request.setAttribute("username", username);
                    request.getRequestDispatcher("/JSP/Admin.jsp").forward(request, response);
                    break;
                case customerrep:
                    session.setAttribute("customerrep", username);
                    request.setAttribute("username", username);
                    request.getRequestDispatcher("/JSP/CustomerRep.jsp").forward(request, response);
                    break;
                case enduser:
                    session.setAttribute("enduser", username);
                    request.setAttribute("username", username);
                    request.getRequestDispatcher("/JSP/EndUser.jsp").forward(request, response);
                    break;
                case invalid:
                    request.setAttribute("errMessage", "Failed to login, please try again...");
                    request.getRequestDispatcher("/JSP/LandingPage.jsp").forward(request, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
