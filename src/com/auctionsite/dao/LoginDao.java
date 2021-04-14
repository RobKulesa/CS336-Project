package com.auctionsite.dao;

import com.auctionsite.bean.LoginBean;
import com.auctionsite.util.ApplicationDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginDao {

    public UserType authenticateUser(LoginBean loginBean) {
        String username = loginBean.getUsername();
        String password = loginBean.getPassword();

        try {
            ApplicationDB db = new ApplicationDB();
            Connection con = db.getConnection();

            Statement statement = con.createStatement();

            String loginQuery = "SELECT * FROM users WHERE display_name = '" + username + "' and pwd = '" + password + "';";

            ResultSet loginResult = statement.executeQuery(loginQuery);

            if(loginResult.next()) {
                switch(loginResult.getString("usertype")) {
                    case "admin":
                        return UserType.admin;
                    case "customer rep":
                        return UserType.customerrep;
                    case "end user":
                        return UserType.enduser;
                    default:
                        return UserType.invalid;
                }
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return UserType.invalid;
    }
}

