package com.auctionsite.dao;
import com.auctionsite.beans.AlertBean;
import com.auctionsite.util.ApplicationDB;
import java.util.ArrayList;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;



public class AlertDao {
    public ArrayList<AlertBean> getAllAlerts(int uid){
        ArrayList<AlertBean> allAlerts = new ArrayList<AlertBean>();
        try{
            ApplicationDB db = new ApplicationDB();
            Connection con = db.getConnection();

            Statement statement = con.createStatement();

            String alertQuery = "SELECT * FROM alerts WHERE recipient = " + Integer.toString(uid) + ";";
            ResultSet alertList = statement.executeQuery(alertQuery);

            while(alertList.next()){
                AlertBean alertBean = new AlertBean();
                alertBean.setRecipient(uid);
                alertBean.setSendDateTime(alertList.getTimestamp("send_datetime"));
                alertBean.setThread(alertList.getString("thread"));
                allAlerts.add(alertBean);
            }
            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        return allAlerts;
    }

    public  void sendAlert(AlertBean alertBean){
        //Retrieve necessary User data from AlertBean object
        String thread = alertBean.getThread();
        int recipient = alertBean.getRecipient();
        Timestamp sendDateTime = alertBean.getSendDateTime();

        try {
            //Attempts to set up connection to database
            ApplicationDB db = new ApplicationDB();
            Connection con = db.getConnection();

            //Formats SQL query
            String query = "INSERT INTO alerts (recipient, send_datetime, thread) VALUES (?, ?, ?);";

            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, recipient);
            preparedStatement.setString(2, sendDateTime.toString());
            preparedStatement.setString(3, thread);

            //Executes query
            preparedStatement.execute();
            con.close();
        }
        catch(SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

}
