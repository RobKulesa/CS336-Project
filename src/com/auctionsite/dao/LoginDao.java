
package com.auctionsite.dao;

import com.auctionsite.beans.LoginBean;
import com.auctionsite.util.ApplicationDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Data Access Object class meant to expose LoginBean information the database.
 * Designed to process a login request by using the data to query the database's users table
 *
 * @author Robert "shawtyswiper" Kulesa
 */
public class LoginDao {

    /**
     * Takes in a login request in the form of a LoginBean object.
     * Queries the database and decides what kind of login request was made.
     * @param loginBean     The login request ot be processed
     * @return              The kind of login request made: invalid, end user, admin, or customer rep
     */
    public String authenticateUser(LoginBean loginBean) {
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
                return loginResult.getString("usertype");
            }
            con.close();
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