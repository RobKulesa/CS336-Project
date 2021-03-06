package com.auctionsite.controllers;

import com.auctionsite.beans.UserBean;
import com.auctionsite.dao.CreateUserDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateUserServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("newusername");
        String password = request.getParameter("newpassword");
        String userType = request.getParameter("newusertype");
        String origin = "/JSP/" + request.getParameter("origin");

        System.out.println("UserType is : " + userType);

        if(username.isBlank() || password.isBlank()) {
            request.setAttribute("createMessage", "Fields cannot be blank!");
            request.getRequestDispatcher(origin).forward(request, response);
            return;
        }

        UserBean userBean = new UserBean(username, password, userType);

        CreateUserDao createUserDao = new CreateUserDao();

        try {
            createUserDao.createUser(userBean);
            request.setAttribute("createMessage", "Successfully created account!");
            request.getRequestDispatcher(origin).forward(request, response);
        } catch(Exception e) {
            request.setAttribute("createMessage", e.getMessage());
            request.getRequestDispatcher(origin).forward(request, response);
        }
    }
}