package com.auctionsite.beans;

/**
 * Class designed to model a log in request with appropriate fields and methods
 *
 * @author Rob Kulesa
 */
public class LoginBean {
    /**
     * Holds the inputted username of the current login request
     */
    private String username;

    /**
     * Holds the inputted password of the current login request
     */
    private String password;

    /**
     * Getter for the LoginBean username field
     * @return the username given in the attempted login request
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter for the LoginBean username field
     * @param username  String used to assign a value to the LoginBean username field
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter for the LoginBean username field
     * @return the username given in the attempted login request
     */
    public String getPassword() {
        return password;
    }

    /**
     * Getter for the LoginBean password field
     * @return the password given in the attempted login request
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
