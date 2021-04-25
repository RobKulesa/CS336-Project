package com.auctionsite.beans;

import java.util.ArrayList;

public class AccountInfoBean {
    private int uid;
    private String username;
    private String password;
    private ArrayList<AuctionBean> usersAuctions;
    private ArrayList<BidBean> usersBids;

    public AccountInfoBean(int uid, String username, String password) {
        this.usersAuctions = new ArrayList<AuctionBean>();
        this.usersBids = new ArrayList<BidBean>();
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

    public ArrayList<AuctionBean> getUsersAuctions() {
        return usersAuctions;
    }

    public void setUsersAuctions(ArrayList<AuctionBean> usersAuctions) {
        this.usersAuctions = usersAuctions;
    }

    public ArrayList<BidBean> getUsersBids() {
        return usersBids;
    }

    public void setUsersBids(ArrayList<BidBean> usersBids) {
        this.usersBids = usersBids;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void addBid(BidBean bid) {
        this.usersBids.add(bid);
    }

    public void addAuction(AuctionBean auction) {
        this.usersAuctions.add(auction);
    }
}

