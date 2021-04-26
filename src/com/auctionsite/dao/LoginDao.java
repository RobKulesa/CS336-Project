
package com.auctionsite.dao;

import com.auctionsite.beans.LoginBean;
import com.auctionsite.util.ApplicationDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginDao {

    public String authenticateUser(LoginBean loginBean) {
        String username = loginBean.getUsername();
        String password = loginBean.getPassword();

        try {
            ApplicationDB db = new ApplicationDB();
            Connection con = db.getConnection();

            Statement statement = con.createStatement();

            String loginQuery = "SELECT * FROM users WHERE display_name = '" + username + "' and pwd = '" + password + "';";

            ResultSet loginResult = statement.executeQuery(loginQuery);

            if(loginResult.next()) {
                String mystr = loginResult.getString("usertype");
                loginResult.close();
                return mystr;
            }
            loginResult.close();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return "invalid";
    }

    public String getUID(LoginBean loginBean){
        //Retrieves login request information
        String username = loginBean.getUsername();
        String password = loginBean.getPassword();

        try {
            //Establish connection to database
            ApplicationDB db = new ApplicationDB();
            Connection con = db.getConnection();

            //Create and format query
            Statement statement = con.createStatement();

            String loginQuery = "SELECT * FROM users WHERE display_name = '" + username + "' and pwd = '" + password + "';";

            //Returns a ResultSet that may contain a non-empty query
            ResultSet loginResult = statement.executeQuery(loginQuery);

            if(loginResult.next()) {
                return Integer.toString(loginResult.getInt("uid"));
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return "-1";
    }
}