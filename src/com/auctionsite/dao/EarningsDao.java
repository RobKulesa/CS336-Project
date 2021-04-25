package com.auctionsite.dao;

import com.auctionsite.beans.EarningsBean;
import com.auctionsite.util.ApplicationDB;

import java.sql.*;

public class EarningsDao {
    public EarningsBean getTotalEarnings() {
        EarningsBean earnings = new EarningsBean("Total Auction Site Earnings", new String[]{"Total Earnings"});
        try {
            ApplicationDB db = new ApplicationDB();
            Connection con = db.getConnection();

            Statement statement = con.createStatement();

            String query = "SELECT SUM(b.amnt) FROM auctions a, bids b WHERE a.available='closed' AND b.bid=a.highest_bid;";

            ResultSet queryResult = statement.executeQuery(query);

            if(queryResult.next()) {
                earnings.addRow(new String[]{formatStringAsPrice(queryResult.getString(1))});
            }
        } catch(Exception e) {
            earnings.addRow(new String[]{"0"});
        }
        return earnings;
    }

    public EarningsBean getEarningsPerItem() {
        EarningsBean earnings = new EarningsBean("Earnings Per Item", new String[]{"Brand", "Model", "Part Number", "Earnings"});
        try {
            ApplicationDB db = new ApplicationDB();
            Connection con = db.getConnection();

            Statement statement = con.createStatement();

            String query = "SELECT i.brand, i.model, i.part_number, SUM(b.amnt) " +
                    "FROM auctions a, bids b, items i " +
                    "WHERE a.available='closed' " +
                    "AND b.bid=a.highest_bid " +
                    "AND a.part_number=i.part_number " +
                    "GROUP BY a.part_number " +
                    "ORDER BY SUM(b.amnt) DESC;";

            ResultSet queryResult = statement.executeQuery(query);

            while(queryResult.next()) {
                String[] row = new String[earnings.getColumnNames().length];
                for(int i=0; i < earnings.getColumnNames().length; ++i) {
                    if(i == earnings.getColumnNames().length - 1) row[i] = formatStringAsPrice(queryResult.getString(i+1));
                    else row[i] = queryResult.getString(i+1);
                }
                earnings.addRow(row);
            }
        } catch(Exception e) {
            earnings.addRow(new String[]{"none", "none", "none", "none", "none", "$0.00"});
        }
        return earnings;
    }

    public EarningsBean getEarningsPerItemType() {
        String[] itemTypes = {"keyboards", "mice", "monitors"};
        EarningsBean earnings = new EarningsBean("Earnings Per Item Type", new String[]{"Type", "Earnings"});
        for(String type : itemTypes) {
            try {
                ApplicationDB db = new ApplicationDB();
                Connection con = db.getConnection();

                Statement statement = con.createStatement();

                String query = "SELECT SUM(b.amnt) " +
                        "FROM auctions a, bids b, " + type +  " i " +
                        "WHERE a.available='closed' " +
                        "AND b.bid=a.highest_bid " +
                        "AND a.part_number=i.part_number " +
                        "ORDER BY SUM(b.amnt) DESC;";

                ResultSet queryResult = statement.executeQuery(query);

                if (queryResult.next()) {
                    String[] row = new String[earnings.getColumnNames().length];
                    row[0] = type;
                    row[1] = formatStringAsPrice(queryResult.getString(1));
                    earnings.addRow(row);
                }
            } catch (Exception e) {
                earnings.addRow(new String[]{type, "$0.00"});
            }
        }
        return earnings;
    }

    public EarningsBean getEarningsPerUser() {
        EarningsBean earnings = new EarningsBean("Earnings Per User", new String[]{"User", "Earnings"});
        try {
            ApplicationDB db = new ApplicationDB();
            Connection con = db.getConnection();

            Statement statement = con.createStatement();

            String query = "SELECT u.display_name, SUM(b.amnt) " +
                    "FROM auctions a, users u, bids b, items i " +
                    "WHERE a.available='closed' " +
                    "AND b.bid=a.highest_bid " +
                    "AND a.uid=u.uid " +
                    "GROUP BY a.uid " +
                    "ORDER BY SUM(b.amnt) DESC;";

            ResultSet queryResult = statement.executeQuery(query);

            while(queryResult.next()) {
                String[] row = new String[earnings.getColumnNames().length];
                for(int i=0; i < earnings.getColumnNames().length; ++i) {
                    if(i == earnings.getColumnNames().length - 1) row[i] = formatStringAsPrice(queryResult.getString(i+1));
                    else row[i] = queryResult.getString(i+1);
                }
                earnings.addRow(row);
            }
        } catch(Exception e) {
            earnings.addRow(new String[]{"none", "$0.00"});
        }
        return earnings;
    }

    public EarningsBean getEarningsBestSellingItems(int limit) {
        if(limit <= 0) limit = 10;
        EarningsBean earnings = new EarningsBean("Earnings Per " + limit + " Best Selling Items", new String[]{"Item", "Earnings"});
        try {
            ApplicationDB db = new ApplicationDB();
            Connection con = db.getConnection();

            Statement statement = con.createStatement();

            String query = "SELECT a.part_number, SUM(b.amnt) " +
                    "FROM auctions a, bids b " +
                    "INNER JOIN (SELECT a3.part_number " +
                    "FROM auctions a3 " +
                    "WHERE a3.available='closed' " +
                    "GROUP BY a3.part_number " +
                    "ORDER BY COUNT(a3.part_number) DESC " +
                    "LIMIT " + limit +") AS a2 ON a2.part_number=part_number " +
                    "WHERE a.highest_bid=b.bid " +
                    "GROUP BY a.part_number " +
                    "ORDER BY SUM(b.amnt) DESC;";

            ResultSet queryResult = statement.executeQuery(query);

            while(queryResult.next()) {
                String[] row = new String[earnings.getColumnNames().length];
                for(int i=0; i < earnings.getColumnNames().length; ++i) {
                    if(i == earnings.getColumnNames().length - 1) row[i] = formatStringAsPrice(queryResult.getString(i+1));
                    else row[i] = queryResult.getString(i+1);
                }
                earnings.addRow(row);
            }
        } catch(Exception e) {
            earnings.addRow(new String[]{"none", "$0.00"});
        }
        return earnings;
    }

    public EarningsBean getEarningsBestUsers(int limit) {
        if(limit <= 0) limit = 10;
        EarningsBean earnings = new EarningsBean("Earnings Per " + limit + " Best Users", new String[]{"User", "Earnings"});
        try {
            ApplicationDB db = new ApplicationDB();
            Connection con = db.getConnection();

            Statement statement = con.createStatement();

            String query = "SELECT u.display_name, SUM(b.amnt) FROM users u, bids b  WHERE b.uid=u.uid AND b.bid IN (SELECT a.highest_bid FROM auctions a WHERE a.available='closed') GROUP BY u.display_name ORDER BY SUM(b.amnt) DESC LIMIT " + limit +";";

            ResultSet queryResult = statement.executeQuery(query);

            while(queryResult.next()) {
                String[] row = new String[earnings.getColumnNames().length];
                for(int i=0; i < earnings.getColumnNames().length; ++i) {
                    if(i == earnings.getColumnNames().length - 1) row[i] = formatStringAsPrice(queryResult.getString(i+1));
                    else row[i] = queryResult.getString(i+1);
                }
                earnings.addRow(row);
            }
        } catch(Exception e) {
            earnings.addRow(new String[]{"none", "$0.00"});
        }
        return earnings;
    }

    private static String formatStringAsPrice(String str) {
        return "$" + String.format("%.2f", Float.parseFloat(str));
    }
}