package com.auctionsite.dao;

import com.auctionsite.beans.ThreadBean;
import com.auctionsite.util.ApplicationDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.function.Predicate;

public class ThreadsDao {

    public ArrayList<ThreadBean> searchByKeyword(String keyword) {
        ArrayList<ThreadBean> threads = getThreads();
        threads.removeIf(threadBean -> {
            if(threadBean.containsMsg(keyword)) return false;
            else return true;
        });
        return threads;
    }

    public void addMessage(int ticket_num, int msg_num, String username, String msg) {
        try{
            ApplicationDB db = new ApplicationDB();
            Connection con = db.getConnection();
            String query = "INSERT into supporttickets (ticket_num, msg_num, display_name, msg) VALUES (?, ?, ?, ?);";

            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, ticket_num);
            statement.setInt(2, msg_num);
            statement.setString(3, username);
            statement.setString(4, msg);

            statement.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<ThreadBean> getThreads() {
        ArrayList<ThreadBean> threads = new ArrayList<ThreadBean>();
        try {
            ApplicationDB db = new ApplicationDB();
            Connection con = db.getConnection();

            Statement statement = con.createStatement();

            String query = "SELECT ticket_num, display_name, msg FROM supporttickets;";

            ResultSet queryResult = statement.executeQuery(query);

            while(queryResult.next()) {
                int ticket_num = queryResult.getInt("ticket_num");
                String display_name = queryResult.getString("display_name");
                String msg = queryResult.getString("msg");
                if(ticket_num <= 0 || display_name.isBlank()) continue;
                if(msg == null) msg = "";
                if(threads.isEmpty() || threads.size() < ticket_num) {
                    ThreadBean threadBean = new ThreadBean(display_name, ticket_num, msg);
                    threads.add(threadBean);
                } else {
                    ThreadBean threadBean = threads.get(ticket_num - 1);
                    threadBean.addReply(msg);
                }
            }
            queryResult.close();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return threads;
    }
}
