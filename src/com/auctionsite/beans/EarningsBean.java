package com.auctionsite.beans;

import java.util.ArrayList;

public class EarningsBean {
    private String type;
    private String[] columnNames;
    private ArrayList<String[]> data;

    public EarningsBean(String type, int numColumns) {
        this.type = type;
        this.columnNames = new String[numColumns];
        this.data = new ArrayList<String[]>();
    }

    public EarningsBean(String type, String[] columnNames) {
        this.type = type;
        this.columnNames = columnNames;
        this.data = new ArrayList<String[]>();
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    public ArrayList<String[]> getData() {
        return data;
    }

    public void setData(ArrayList<String[]> data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void addRow(String[] row) {
        this.data.add(row);
    }

    @Override
    public String toString() {
        String str = "";
        for(String[] row : data) {
            for(String entry : row) {
                str += entry + " ";
            }
            str += "\n";
        }
        return str;
    }
}
