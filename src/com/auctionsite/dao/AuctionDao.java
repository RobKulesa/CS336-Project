package com.auctionsite.dao;
import com.auctionsite.beans.AuctionBean;
import com.auctionsite.util.ApplicationDB;
import com.auctionsite.beans.AlertBean;
import com.auctionsite.dao.AlertDao;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class AuctionDao {
    public AuctionBean getAuctionFromAid(int aid){
        AuctionBean newAucBean = new AuctionBean();
        try{
            ApplicationDB db = new ApplicationDB();
            Connection con = db.getConnection();

            Statement statement = con.createStatement();
            String aucQuery = "SELECT * FROM auctions WHERE aid =" + Integer.toString(aid) + ";";
            ResultSet auctions = statement.executeQuery(aucQuery);
            auctions.next();

            newAucBean.setAid(auctions.getInt(1));
            newAucBean.setCloseTime(auctions.getTime(2));
            newAucBean.setCloseDate(auctions.getDate(10));
            newAucBean.setStatus(auctions.getInt(3));
            newAucBean.setInitPrice(auctions.getFloat(4));
            newAucBean.setSecretMinPrice(auctions.getFloat(6));
            newAucBean.setBidIncr(auctions.getFloat(5));
            newAucBean.setUid(auctions.getInt(7));
            newAucBean.setHighestBidID(auctions.getInt(8));
            newAucBean.setPartNumber(auctions.getString(9));
            auctions.close();

        } catch(Exception e){
            e.printStackTrace();
        }
        return newAucBean;
    }

    public  ArrayList<AuctionBean> allAuctions(){
        ArrayList<AuctionBean> allAucs= new ArrayList<AuctionBean>();
        try{
            ApplicationDB db = new ApplicationDB();
            Connection con = db.getConnection();

            Statement statement = con.createStatement();

            String aucQuery = "SELECT * FROM auctions;";
            ResultSet auctions = statement.executeQuery(aucQuery);
            while(auctions.next()){
                AuctionBean newAucBean = new AuctionBean();

                newAucBean.setAid(auctions.getInt(1));
                newAucBean.setCloseTime(auctions.getTime(2));
                newAucBean.setCloseDate(auctions.getDate(10));
                newAucBean.setStatus(auctions.getInt(3));
                newAucBean.setInitPrice(auctions.getFloat(4));
                newAucBean.setSecretMinPrice(auctions.getFloat(6));
                newAucBean.setBidIncr(auctions.getFloat(5));
                newAucBean.setUid(auctions.getInt(7));
                newAucBean.setHighestBidID(auctions.getInt(8));
                newAucBean.setPartNumber(auctions.getString(9));
                allAucs.add(newAucBean);
            }
            con.close();
        } catch(Exception e){
            e.printStackTrace();
        }
        return allAucs;
    }

    public void refreshAuctions(ArrayList<AuctionBean> auctions){
        //Get Current Date and Time
        java.sql.Date curDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        java.sql.Time curTime = new java.sql.Time(Calendar.getInstance().getTime().getTime());

        //Get all auctions that have already closed
        ArrayList<AuctionBean> closeCandidates = new ArrayList<AuctionBean>();
        for(AuctionBean ab : auctions) {
            if(ab.getStatus() == 1 && ab.getCloseDate().compareTo(curDate) <= 0) {
                if(ab.getCloseDate().compareTo(curDate) == 0 && ab.getCloseTime().compareTo(curTime) < 0)
                    closeCandidates.add(ab);
            }
        }

        try{
            ApplicationDB db = new ApplicationDB();
            Connection con = db.getConnection();

            Statement statement = con.createStatement();

            //For each auction that has past its closeDateTime
            for(AuctionBean ab : closeCandidates){
                //Update its status
                String updateStat = "UPDATE auctions SET available = 0 WHERE aid = " + Integer.toString(ab.getAid()) + ";";
                statement.executeUpdate(updateStat);

                //Determine if a highest bid exists
                if(ab.getHighestBidID() != 0){
                    if(Math.abs(ab.getSecretMinPrice()) > 0.0000001){
                        //Get closetime, uid of the bid
                        String highestBidQuery = "SELECT * FROM bids WHERE bid = " + Integer.toString(ab.getHighestBidID()) + ";";
                        ResultSet bestBid = statement.executeQuery(highestBidQuery);
                        bestBid.next();
                        Timestamp closeTime = Timestamp.valueOf(ab.getCloseDate().toString()+ " " + ab.getCloseTime().toString());
                        int recipientUID = bestBid.getInt("uid");
                        bestBid.close();
                        AlertBean alertBean = new AlertBean();
                        alertBean.setRecipient(recipientUID);
                        alertBean.setSendDateTime(closeTime);
                        alertBean.setThread("You have won the auction with AuctionID of: " + ab.getAid());
                        AlertDao sendAlert = new AlertDao();
                        sendAlert.sendAlert(alertBean);



                    }
                    else{
                        String getHighestBidAmnt = "SELECT * FROM bids WHERE bid = " + Integer.toString(ab.getHighestBidID()) + ";";
                        ResultSet highestBidResult = statement.executeQuery(getHighestBidAmnt);
                        float highestBidValue = highestBidResult.getFloat("amnt");
                        //Determine if the highest bid is greater than the reserve
                        if(highestBidValue > ab.getSecretMinPrice()){
                            String highestBidQuery = "SELECT * FROM bids WHERE bid = " + Integer.toString(ab.getHighestBidID()) + ";";
                            ResultSet bestBid = statement.executeQuery(highestBidQuery);
                            bestBid.next();
                            Timestamp closeTime = Timestamp.valueOf(ab.getCloseDate().toString()+ " " + ab.getCloseTime().toString());
                            int recipientUID = bestBid.getInt("uid");
                            bestBid.close();
                            AlertBean alertBean = new AlertBean();
                            alertBean.setRecipient(recipientUID);
                            alertBean.setSendDateTime(closeTime);
                            alertBean.setThread("You have won the auction with AuctionID of:  " + ab.getAid());
                            AlertDao sendAlert = new AlertDao();
                            sendAlert.sendAlert(alertBean);
                        }
                    }

                }
            }

        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public void AuctionWinnerAlert(int winningBidID){

    }
}
