package com.auctionsite.beans;

public class BidBean {
    private float secretUpperLimit;
    private float desiredIncrement;
    private float amnt;
    private int uid;
    private int aid;
    private int bid;

    public float getSecretUpperLimit(){ return this.secretUpperLimit; }

    public void setSecretUpperLimit(float secretUpperLimit){ this.secretUpperLimit = secretUpperLimit; }

    public float getDesiredIncrement(){return this.desiredIncrement; }

    public void setDesiredIncrement(float desiredIncrement){this.desiredIncrement = desiredIncrement; }

    public float getAmnt(){return this.amnt; }

    public void setAmnt(float amnt){this.amnt = amnt; }

    public int getUid(){return this.uid; }

    public void setUID(int uid){this.uid = uid; }

    public int getAid(){return this.aid; }

    public void setAID(int aid){this.aid = aid; }

    public int getBid(){return this.bid; }

    public void setBid(int bid){this.bid = bid; }


}
