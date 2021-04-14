package com.auctionsite.dao;

import com.auctionsite.bean.UserBean;
import com.auctionsite.util.ApplicationDB;

import java.sql.*;

public class CreateUserDao {

    public void createUser(UserBean userBean) {
        String username = userBean.getUsername();
        String password = userBean.getPassword();
        String userType = userBean.getUserType();

        try {
            ApplicationDB db = new ApplicationDB();
            Connection con = db.getConnection();

            String query = "INSERT INTO users (display_name, pwd, usertype) VALUES (?, ?, ?);";

            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, userType);

            preparedStatement.execute();
            con.close();
        }
        catch(SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
