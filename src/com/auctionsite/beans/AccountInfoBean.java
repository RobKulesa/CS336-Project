package com.auctionsite.beans;

import java.util.ArrayList;

public class AccountInfoBean {
    private int uid;
    private String username;
    private String password;
    private ArrayList<RobAuctionBean> usersAuctions;
    private ArrayList<RobBidBean> usersBids;

    public AccountInfoBean(int uid, String username, String password) {
        this.usersAuctions = new ArrayList<RobAuctionBean>();
        this.usersBids = new ArrayList<RobBidBean>();
        this.uid = uid;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<RobAuctionBean> getUsersAuctions() {
        return usersAuctions;
    }

    public void setUsersAuctions(ArrayList<RobAuctionBean> usersAuctions) {
        this.usersAuctions = usersAuctions;
    }

    public ArrayList<RobBidBean> getUsersBids() {
        return usersBids;
    }

    public void setUsersBids(ArrayList<RobBidBean> usersBids) {
        this.usersBids = usersBids;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void addBid(RobBidBean bid) {
        this.usersBids.add(bid);
    }

    public void addAuction(RobAuctionBean auction) {
        this.usersAuctions.add(auction);
    }
}

