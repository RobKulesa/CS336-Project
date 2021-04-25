package com.auctionsite.beans;

public class BidBean {
    private String part_number;
    private String brand;
    private String model;
    private int bid;
    private float amnt;

    public BidBean(int bid, String part_number, String brand, String model, float amnt) {
        this.bid = bid;
        this.part_number = part_number;
        this.brand = brand;
        this.model = model;
        this.amnt = amnt;
    }

    public String getPart_number() {
        return part_number;
    }

    public void setPart_number(String part_number) {
        this.part_number = part_number;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public float getAmnt() {
        return amnt;
    }

    public void setAmnt(float amnt) {
        this.amnt = amnt;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }
}
