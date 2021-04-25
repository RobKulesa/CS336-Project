package com.auctionsite.beans;

import java.util.ArrayList;

public class ThreadBean {
    private String msg;
    private ArrayList<String> replies;
    private String display_name;
    private int ticket_num;

    public ThreadBean(String display_name, int ticket_num, String msg) {
        this.msg = msg;
        replies = new ArrayList<String>();
        this.display_name = display_name;
        this.ticket_num = ticket_num;
    }

    public ArrayList<String> getReplies() {
        return replies;
    }

    public void setMsg(int index, String msg) {
        replies.set(index, msg);
    }

    public void addReply(String msg) {
        replies.add(msg);
    }

    public void setReplies(ArrayList<String> replies) {
        this.replies = replies;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public int getTicket_num() {
        return ticket_num;
    }

    public void setTicket_num(int ticket_num) {
        this.ticket_num = ticket_num;
    }

    public String getMessage(int idx) {
        return replies.get(idx);
    }

    public int getNumMessages() {
        return replies.size() + 1;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean containsMsg(String msg) {
        msg = msg.toLowerCase();
        if(this.msg.toLowerCase().contains(msg)) return true;
        for(String reply : this.replies) {
            if(reply.toLowerCase().contains(msg)) return true;
        }
        return false;
    }
}
