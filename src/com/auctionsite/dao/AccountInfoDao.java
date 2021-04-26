package com.auctionsite.dao;

import com.auctionsite.beans.AccountInfoBean;
import com.auctionsite.beans.RobAuctionBean;
import com.auctionsite.beans.RobBidBean;
import com.auctionsite.util.ApplicationDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class AccountInfoDao {

    public ArrayList<AccountInfoBean> getAccountInfos() {
        ArrayList<AccountInfoBean> accountInfos = new ArrayList<AccountInfoBean>();
        try {
            ApplicationDB db = new ApplicationDB();
            Connection con = db.getConnection();

            Statement statement = con.createStatement();

            String query = "SELECT u.uid, u.display_name, u.pwd " +
                    "FROM users u " +
                    "WHERE u.usertype='end user';";

            ResultSet queryResult = statement.executeQuery(query);

            while(queryResult.next()) {
                int uid = queryResult.getInt("uid");
                String username = queryResult.getString("display_name");
                String password = queryResult.getString("pwd");

                AccountInfoBean user = new AccountInfoBean(uid, username, password);
                user.setUsersAuctions(getUsersAuctions(uid));
                user.setUsersBids(getUsersBids(uid));
                accountInfos.add(user);
            }
            queryResult.close();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return accountInfos;
    }

    public ArrayList<RobBidBean> getUsersBids(int uid) {
        ArrayList<RobBidBean> bids = new ArrayList<RobBidBean>();
        try {
            ApplicationDB db = new ApplicationDB();
            Connection con = db.getConnection();

            Statement statement = con.createStatement();

            String query = "SELECT a.part_number, i.brand, i.model, b.bid, b.amnt " +
                    "FROM bids b, auctions a, items i " +
                    "WHERE b.aid=a.aid " +
                    "AND a.part_number=i.part_number " +
                    "AND b.uid=" + uid + ";";

            ResultSet queryResult = statement.executeQuery(query);

            while(queryResult.next()) {
                int bid = queryResult.getInt("bid");
                String part_number = queryResult.getString("part_number");
                String brand = queryResult.getString("brand");
                String model = queryResult.getString("model");
                float amnt = queryResult.getFloat("amnt");

                bids.add(new RobBidBean(bid, part_number, brand, model, amnt));
            }
            queryResult.close();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return bids;
    }

    public ArrayList<RobAuctionBean> getUsersAuctions(int uid) {
        ArrayList<RobAuctionBean> auctions = new ArrayList<RobAuctionBean>();
        try {
            ApplicationDB db = new ApplicationDB();
            Connection con = db.getConnection();

            Statement statement = con.createStatement();

            String query = "SELECT a.aid, a.part_number, i.brand, i.model " +
                    "FROM auctions a, items i " +
                    "WHERE a.part_number=i.part_number " +
                    "AND a.uid=" + uid + ";";

            ResultSet queryResult = statement.executeQuery(query);

            while(queryResult.next()) {
                String part_number = queryResult.getString("part_number");
                String brand = queryResult.getString("brand");
                String model = queryResult.getString("model");
                int aid = queryResult.getInt("aid");

                auctions.add(new RobAuctionBean(aid, part_number, brand, model));
            }
            queryResult.close();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return auctions;
    }

    public void deleteBid(int bid) {
        try {
            ApplicationDB db = new ApplicationDB();
            Connection con = db.getConnection();

            String query = "DELETE FROM bids " +
                    "WHERE bid=" + bid + ";";

            PreparedStatement preparedStatement = con.prepareStatement(query);

            preparedStatement.execute();
            preparedStatement.close();
            con.close();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteAuction(int aid) {
        try {
            ApplicationDB db = new ApplicationDB();
            Connection con = db.getConnection();

            String query = "DELETE FROM auctions " +
                    "WHERE aid=" + aid + ";";

            PreparedStatement preparedStatement = con.prepareStatement(query);

            preparedStatement.execute();
            preparedStatement.close();
            con.close();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteUser(int uid) {
        try {
            ApplicationDB db = new ApplicationDB();
            Connection con = db.getConnection();

            String query = "DELETE FROM users " +
                    "WHERE uid=" + uid + ";";

            PreparedStatement preparedStatement = con.prepareStatement(query);

            preparedStatement.execute();
            preparedStatement.close();
            con.close();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateUser(int uid, String username, String pwd) {
        try {
            ApplicationDB db = new ApplicationDB();
            Connection con = db.getConnection();


            String query = "UPDATE users " +
                    "SET display_name='" + username +"', pwd='" + pwd + "' " +
                    "WHERE uid=" + uid + ";";

            PreparedStatement preparedStatement = con.prepareStatement(query);

            preparedStatement.execute();
            preparedStatement.close();
            con.close();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public int getUidFromUsername(String username) {
        int uid = 1;
        try {
            ApplicationDB db = new ApplicationDB();
            Connection con = db.getConnection();

            Statement statement = con.createStatement();

            String query = "SELECT uid FROM users WHERE display_name='" + username + "';";

            ResultSet queryResult = statement.executeQuery(query);

            if(queryResult.next()) {
                uid = queryResult.getInt("uid");
            }
            queryResult.close();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return uid;
    }

    public String getUsernameFromUid(int uid) {
        String username = "";
        try {
            ApplicationDB db = new ApplicationDB();
            Connection con = db.getConnection();

            Statement statement = con.createStatement();

            String query = "SELECT display_name FROM users WHERE uid=" + uid + ";";

            ResultSet queryResult = statement.executeQuery(query);

            if(queryResult.next()) {
                username = queryResult.getString("display_name");
            }
            queryResult.close();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return username;
    }

    public String getPwdFromUid(int uid) {
        String pwd = "";
        try {
            ApplicationDB db = new ApplicationDB();
            Connection con = db.getConnection();

            Statement statement = con.createStatement();

            String query = "SELECT pwd FROM users WHERE uid=" + uid + ";";

            ResultSet queryResult = statement.executeQuery(query);

            if(queryResult.next()) {
                pwd = queryResult.getString("pwd");
            }
            queryResult.close();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return pwd;
    }
}
