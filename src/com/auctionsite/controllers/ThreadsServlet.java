package com.auctionsite.controllers;

import com.auctionsite.beans.EarningsBean;
import com.auctionsite.beans.ThreadBean;
import com.auctionsite.dao.EarningsDao;
import com.auctionsite.dao.ThreadsDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

public class ThreadsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ThreadsDao threadsDao = new ThreadsDao();
        ArrayList<ThreadBean> threads = threadsDao.getThreads();
        request.setAttribute("threads", threads);
        request.getRequestDispatcher("/JSP/ThreadsView.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        update(req, resp);
    }

    protected void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ThreadsDao threadsDao = new ThreadsDao();
        ArrayList<ThreadBean> threads = threadsDao.getThreads();
        HttpSession session = req.getSession();
        String usertype = String.valueOf(session.getAttribute("usertype"));

        if(usertype.equals("customerrep")) {
            String username = String.valueOf(session.getAttribute("customerrep"));
            String reply = req.getParameter("reply");
            if(reply.isBlank()) {
                req.setAttribute("errMessage", "Fields cannot be blank!");
                req.setAttribute("threads", threads);
                req.getRequestDispatcher("/JSP/ThreadsView.jsp").forward(req, resp);
                return;
            }
            int ticket_num = Integer.parseInt(req.getParameter("ticketnum"));
            ThreadBean threadReplied = threads.get(ticket_num - 1);
            int msgNum = threadReplied.getNumMessages() + 1;
            threadsDao.addMessage(ticket_num, msgNum, username, reply);
            System.out.println("Inserting thread: " + ticket_num + ", " + msgNum + ", " + username + ", " + reply);
            threads = threadsDao.getThreads();
            req.setAttribute("threads", threads);
            req.getRequestDispatcher("/JSP/ThreadsView.jsp").forward(req, resp);
        } else { //enduser
            String reqtype = req.getParameter("reqtype");
            if(reqtype.equals("newpost")) {
                String username = String.valueOf(session.getAttribute("enduser"));
                String question = req.getParameter("question");
                if (question.isBlank()) {
                    req.setAttribute("errMessage", "Fields cannot be blank!");
                    req.setAttribute("threads", threads);
                    req.getRequestDispatcher("/JSP/ThreadsView.jsp").forward(req, resp);
                    return;
                }
                int ticket_num = threads.size() + 1;
                threadsDao.addMessage(ticket_num, 1, username, question);
                System.out.println("Inserting thread: " + ticket_num + ", " + username + ", " + question);
                threads = threadsDao.getThreads();
                req.setAttribute("threads", threads);
                req.getRequestDispatcher("/JSP/ThreadsView.jsp").forward(req, resp);
            } else if(reqtype.equals("search")) {
                String keyword = req.getParameter("keyword");
                if (keyword.isBlank()) {
                    req.setAttribute("errMessage", "Fields cannot be blank!");
                    req.setAttribute("threads", threads);
                    req.getRequestDispatcher("/JSP/ThreadsView.jsp").forward(req, resp);
                    return;
                }
                threads = threadsDao.searchByKeyword(keyword);
                req.setAttribute("threads", threads);
                req.getRequestDispatcher("/JSP/ThreadsView.jsp").forward(req, resp);
            } else if(reqtype.equals("clear")) {
                threads = threadsDao.getThreads();
                req.setAttribute("threads", threads);
                req.getRequestDispatcher("/JSP/ThreadsView.jsp").forward(req, resp);
            }
        }
    }
}
