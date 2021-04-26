package com.auctionsite.beans;

/**
 * Class designed to model a user of the auction site
 * @author Robert Kulesa
 */
public class UserBean {
    /**
     * Stores the user's username
     */
    private String username;

    /**
     * Stores the user's password
     */
    private String password;

    /**
     * Determines if the user is an end-user, customer representative, or admin
     */
    private String userType;


    /**
     * UserBean constructor
     * @param username  String to be assigned to the UserBean username field
     * @param password  String to be assigned to the UserBean password field
     * @param userType  String to be assigned to the UserBean userType field
     */
    public UserBean(String username, String password, String userType) {
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    /**
     * Gets the UserBean's username
     * @return UserBean instance username field
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the UserBean's instance's username
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the UserBean's password
     * @return UserBean instance password field
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the UserBean's instance's password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the UserBean's username
     * @return UserBean instance userType field
     */
    public String getUserType() {
        return userType;
    }

    /**
     * Sets the UserBean's instance's userType
     * @param userType
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }
}
