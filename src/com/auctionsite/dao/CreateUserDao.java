package com.auctionsite.dao;

import com.auctionsite.beans.UserBean;
import com.auctionsite.util.ApplicationDB;

import java.sql.*;

/**
 * Sometimes I wish Rob commented his code so I wouldn't struggle to understand how it all works.
 * Anyways, this class exists to expose UserBean objects to the MySQL database to create a new User in the database.
 *
 * @author Robert "ih8comments" Kulesa
 */
public class CreateUserDao {

    /**
     * Exposes the UserBean's fields and attempts to enter them into the database
     * @param userBean  holds the User information needed to create a new User in the database
     */
    public void createUser(UserBean userBean) {
        //Retrieve necessary User data from UserBean object
        String username = userBean.getUsername();
        String password = userBean.getPassword();
        String userType = userBean.getUserType();

        try {
            //Attempts to set up connection to database
            ApplicationDB db = new ApplicationDB();
            Connection con = db.getConnection();

            //Formats SQL query
            String query = "INSERT INTO users (display_name, pwd, usertype) VALUES (?, ?, ?);";

            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, userType);

            //Executes query
            preparedStatement.execute();
            con.close();
        }
        catch(SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}