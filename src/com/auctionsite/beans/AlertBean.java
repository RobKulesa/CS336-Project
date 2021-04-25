package com.auctionsite.beans;
import java.sql.*;


public class AlertBean {
    private int alertId;
    private int recipient;
    private java.sql.Timestamp sendDateTime;
    private String thread;

    public int getRecipient(){return this.recipient; }

    public void setRecipient(int recipientUID){this.recipient = recipientUID; }

    public Timestamp getSendDateTime(){return this.sendDateTime; }

    public void setSendDateTime(Timestamp sendDateTime){this.sendDateTime = sendDateTime; }

    public String getThread(){return this.thread;}

    public void setThread(String thread){this.thread = thread; }
}
