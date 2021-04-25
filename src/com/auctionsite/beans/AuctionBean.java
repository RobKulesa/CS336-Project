package com.auctionsite.beans;

import java.sql.Date;
import java.sql.Time;
public class AuctionBean {

    private int aid;
    private Time startTime;
    private Time closeTime;
    private Date closeDate;
    private int status;
    private float initPrice;
    private float secretMinPrice;
    private float bidIncr;
    private int uid;
    private int highestBidID;
    private String partNumber;

    public int getAid() {
        return this.aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public Time getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getCloseTime() {
        return this.closeTime;
    }

    public void setCloseTime(Time closeTime) {
        this.closeTime = closeTime;
    }

    public Date getCloseDate() {
        return this.closeDate;
    }

    public void setCloseDate(Date closeDate) {this.closeDate = closeDate; }

    public int getStatus(){return this.status;}

    public void setStatus(int status){this.status = status; }

    public float getInitPrice(){return this.initPrice; }

    public void setInitPrice(float initPrice){this.initPrice = initPrice; }

    public float getSecretMinPrice(){return this.secretMinPrice; }

    public void setSecretMinPrice(float secretMinPrice){ this.secretMinPrice = secretMinPrice; }

    public float getBidIncr(){ return this.bidIncr; }

    public void setBidIncr(float bidIncr){this.bidIncr = bidIncr; }

    public int getUid(){ return this.uid; }

    public void setUid(int uid){ this.uid = uid; }

    public int getHighestBidID(){return this.highestBidID;}

    public void setHighestBidID(int highestBidID){this.highestBidID = highestBidID; }

    public String getPartNumber(){return this.partNumber; }

    public void setPartNumber(String partNumber){ this.partNumber = partNumber; }
}