package com.auctionsite.dao;

import com.auctionsite.util.ApplicationDB;

import java.sql.*;

public class EarningsDao {

    public int getTotalEarnings() {
        try {
            ApplicationDB db = new ApplicationDB();
            Connection con = db.getConnection();

            Statement statement = con.createStatement();

            long millis = System.currentTimeMillis();
            Date currentDateTime = new Date(millis);

            String query = "SELECT SUM(b.amnt) FROM auctions a, bids b WHERE a.status='closed' AND b.bid=a.highest_bid";

            ResultSet queryResult = statement.executeQuery(query);

            if(queryResult.next()) {
                return Integer.parseInt(queryResult.getString(1));
            }
        }
        catch(SQLException e) {
            return 0;
        }
        return 0;
    }
}
