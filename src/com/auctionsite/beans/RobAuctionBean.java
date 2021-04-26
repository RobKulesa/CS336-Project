package com.auctionsite.beans;

public class RobAuctionBean {
    private int aid;
    private String part_number;
    private String brand;
    private String model;

    public RobAuctionBean(int aid, String part_number, String brand, String model) {
        this.aid = aid;
        this.part_number = part_number;
        this.brand = brand;
        this.model = model;
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

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }
}
