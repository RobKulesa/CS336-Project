package com.auctionsite.dao;
import com.auctionsite.beans.BidBean;
import com.auctionsite.util.ApplicationDB;
import java.util.ArrayList;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;


public class CreateBidDao {
    public void uploadBid(BidBean bidBean){
        Float secretUpperLimit = bidBean.getSecretUpperLimit();
        Float desiredIncrement = bidBean.getDesiredIncrement();
        int aid = bidBean.getAid();
        int uid = bidBean.getUid();
        try {
            ApplicationDB db = new ApplicationDB();
            Connection con = db.getConnection();
            //Check if

            String query = "INSERT INTO bids (secret_upper_lim, aid, uid, desired_increment) VALUES (?, ?, ?, ?);";

            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setFloat(1, secretUpperLimit);
            preparedStatement.setInt(2, aid);
            preparedStatement.setInt(3, uid);
            preparedStatement.setFloat(4, desiredIncrement);

            preparedStatement.execute();
            con.close();
            recalculateBids(aid);
        }
        catch(SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public boolean isGoodAutoBid(BidBean bidBean){
        if(bidBean.getSecretUpperLimit() < 0.1) return false;
        if(bidBean.getDesiredIncrement() < 0.1) return false;

        try{
            ApplicationDB db = new ApplicationDB();
            Connection con = db.getConnection();

            //Create and format query
            Statement statement = con.createStatement();
            String aidQuery = "SELECT * FROM auctions WHERE aid = " + Integer.toString(bidBean.getAid()) + ";";
            ResultSet auction = statement.executeQuery(aidQuery);

            //Retrieve necessary data needed for comparison
            auction.next();
            float defaultIncr = auction.getFloat("bid_increment");
            float init_price = auction.getFloat("initial_price");
            int highestBid = auction.getInt("highest_bid");
            auction.close();
            float highestBidVal;
            String highestBidQuery = "SELECT * FROM bids WHERE bid = " + Integer.toString(highestBid) + ";";
            ResultSet highestBidSet = statement.executeQuery(highestBidQuery);
            if(highestBidSet.next()){
                highestBidVal = highestBidSet.getFloat("amnt");
            } else {
                highestBidVal = init_price;
            }

            //Start testing conditions
            if(bidBean.getSecretUpperLimit()!= 0 && bidBean.getSecretUpperLimit()<highestBidVal) return false;
            if(bidBean.getDesiredIncrement() < defaultIncr) return false;
            highestBidSet.close();
            con.close();

        }catch(Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * This is a relatively heavyweight method used to update the bid values after a new bid is entered into the database
     * 1. Get all bids associated with the given auction
     * 2. Find the bid with the highest possible value
     * 3. Compute the new values of all the automatic bids after the automatic bidding process is over
     * 4. Send alerts to all the losers
     * @param aid
     */
    public void recalculateBids(int aid){
        try {
            //Establish connection to database
            ApplicationDB db = new ApplicationDB();
            Connection con = db.getConnection();

            //Create and format query
            Statement statement = con.createStatement();

            String bidQuery = "SELECT * FROM bids WHERE aid =" + Integer.toString(aid)+";";
            ArrayList<BidBean> bids = new ArrayList<BidBean>();


            //Store all bids associated with the given auction
            ResultSet auctionSet = statement.executeQuery(bidQuery);
            while(auctionSet.next()){
                BidBean newBidBean = new BidBean();
                newBidBean.setSecretUpperLimit(auctionSet.getFloat(1));
                newBidBean.setUID(auctionSet.getInt(3));
                newBidBean.setAmnt(auctionSet.getFloat(4));
                newBidBean.setDesiredIncrement(auctionSet.getFloat(5));
                newBidBean.setBid(auctionSet.getInt(6));
                bids.add(newBidBean);
            }
            auctionSet.close();
            //If there is only one bid, and it is automatic, set its amnt to the initial price of the object
            String aucQuery = "SELECT * FROM  auctions WHERE aid = " + Integer.toString(aid) + ";";
            ResultSet auction = statement.executeQuery(aucQuery);
            auction.next();
            float defaultPrice = auction.getFloat("initial_price");
            auction.close();
            if(bids.size() == 1){
                if(bids.get(0).getSecretUpperLimit() != 0){
                    String newAmnt = "UPDATE bids SET amnt = " + Float.toString(defaultPrice) + "WHERE bid ="
                            + Integer.toString(bids.get(0).getBid()) + ";";
                    statement.executeUpdate(newAmnt);
                }

                return;
            } else { //If there are multiple bids associated with an auction

                //We retrieve the highest bid
                /*
                    This code may seem complicated, but there are a few things that need to be accounted for.
                    1. Not every bid is an automatic bid. We must check for that
                    2. There are cases where one automatic bid's upper limit may beat another automatic bid's uppper limit
                       However, because bids increment, a bid may never update to reach its upper limit
                            EX) Bid 1: Upperlimit of 100, incr 90, amnt 20; vs Bid 2: Upperlimit: 50, incr 5, amnt 10.
                                In this example, the second bid will reach a far greater max value than the first bid.
                    3. Thus, we calculate the max_value using this formula:
                        MaxValue = (numIncrementsPossible)*(IncrementAmt) + defaultValue.
                    4. Float comparison is often buggy in java, and thus we must use Float class methods for comparison
                 */
                BidBean highestBid = bids.get(0);
                BidBean scdHighest = null;
                for(BidBean b : bids){
                    float highestPossibleValCur;
                    float highestPossibleValPot;
                    //1
                    if(Math.abs(highestBid.getDesiredIncrement()- 0) < 0.00000001)
                        highestPossibleValCur = highestBid.getAmnt();
                    else{//2 and 3
                        int maxNumIncr = (int)((highestBid.getSecretUpperLimit() - defaultPrice)/highestBid.getDesiredIncrement());
                        highestPossibleValCur = defaultPrice + maxNumIncr*highestBid.getDesiredIncrement();
                    }
                    //1
                    if(Math.abs(b.getDesiredIncrement()- 0) < 0.00000001)
                        highestPossibleValPot = b.getAmnt();
                    else{//2 and 3
                        int maxNumIncr = (int)((b.getSecretUpperLimit() - defaultPrice)/b.getDesiredIncrement());
                        highestPossibleValPot = defaultPrice + maxNumIncr*b.getDesiredIncrement();
                    }
                    //final comparison
                    if(Float.compare(highestPossibleValCur, highestPossibleValPot) < 0)
                        highestBid = b;
                }

               /*
                    A couple things need to be done:
                        1. Update all amnt columns of all losing bids to their incremented form
                        2. Send alerts to all losing bids
                        3. Find the final value of the runner up, so we can use it to compute the amnt of the winner
                */
                float runnerUpAmnt = 0;
                for(BidBean b: bids){
                    // TODO SEND MESSAGE TO ALL LOSERS
                    if(!highestBid.equals(b)){
                        float highestPossibleVal;
                        if(Math.abs(b.getDesiredIncrement()- 0) > 0.00000001){
                            int maxNumIncr = (int)((b.getSecretUpperLimit() - defaultPrice)/b.getDesiredIncrement());
                            highestPossibleVal = defaultPrice + maxNumIncr * b.getDesiredIncrement();
                            if(highestPossibleVal > runnerUpAmnt){
                                runnerUpAmnt = highestPossibleVal;
                            }
                            String updateBidAmnt = "UPDATE bids SET amnt = " + Float.toString(highestPossibleVal)
                                    + "WHERE bid =" + Integer.toString(b.getBid()) + ";";
                            statement.executeUpdate(updateBidAmnt);
                        }
                    }
                }

                //Update the winning bid's final amnt post-computation
                int numWinnerIncr = (int)((runnerUpAmnt - defaultPrice)/highestBid.getDesiredIncrement());
                float newWinnerAmnt = defaultPrice + (numWinnerIncr + 1)*highestBid.getDesiredIncrement();
                String updateBidAmnt = "UPDATE bids SET amnt = " + Float.toString(newWinnerAmnt)
                        + "\n WHERE bid = " + Integer.toString(highestBid.getBid()) + ";";
                System.out.println(updateBidAmnt);
                statement.executeUpdate(updateBidAmnt);
                //Update auctions table with the bid of the winning bid.
                String updateAuctions= "UPDATE auctions SET highest_bid = " + Integer.toString(highestBid.getBid()) +
                         "\n WHERE aid =" + Integer.toString(aid) + ";";
                statement.executeUpdate(updateAuctions);
            }
            con.close();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

}
