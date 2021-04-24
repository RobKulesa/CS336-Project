package com.auctionsite.controllers;

import com.auctionsite.beans.LoginBean;
import com.auctionsite.dao.LoginDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * A class meant to bridge the login-request process by collecting the data stored in the html,
 * storing it in a LoginBean Object, and then exposing that information via DAO to the database
 *
 * @author Robert "dig-me-a-hole-and-bury-me-PLEASE" Kulesa
 */
public class LoginServlet extends HttpServlet {
    /**
     * wtf does this do? I don't really know.
     */
    private static final long serialVersionUID = 1L;


    /**
     * Overrides the HttpServlet doPost method.
     * Retrieves login-request info from the html, stores it in a LoginBean
     * Then, uses the LoginDao to attempt authentication.
     * The method then navigates to the appropriate jsp file according the authentication results.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
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
            String uid = loginDao.getUID(loginBean);

            switch(userType) {
                case "admin":
                    session.setAttribute("admin", username);
                    request.setAttribute("username", username);
                    request.getRequestDispatcher("/JSP/Admin.jsp").forward(request, response);
                    break;
                case "customer rep":
                    session.setAttribute("customerrep", username);
                    request.setAttribute("username", username);
                    request.getRequestDispatcher("/JSP/CustomerRep.jsp").forward(request, response);
                    break;
                case "end user":
                    session.setAttribute("enduser", username);
                    session.setAttribute("uid", uid);
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