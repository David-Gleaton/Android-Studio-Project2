package com.example.gleatonhw2;

/*
David Gleaton - C88379585 - hgleato@clemson.edu
Model file for the game
 */

public class PigDiceGame {
    //Private holding values
    private int BankerScore;
    private int PlayerScore;
    private int Round;
    private int RoundTotal;


    //pre:
    //post: All values are set to initial values
    //Construct a new Game method
    public void newGame(){
        BankerScore = 0;
        PlayerScore = 0;
        Round = 1;
        RoundTotal = 0;


    }


    //pre: isPlayer is passed in and is a boolean
    //post: Adds RoundTotal to either player or banker
    //AddBank
    public void addBank(boolean isPlayer){
        if(isPlayer){
            PlayerScore += RoundTotal;
        }else{
            BankerScore += RoundTotal;
        }
    }

    //pre: an int is passed in
    //post: Adds passed in int to RoundTotal
    //RollManager
    public void rollManager(int roll){
            RoundTotal += roll;
    }

    //Pre:
    //Post: Increments the Round value
    public void incrementRound() { Round += 1; }

    //pre:
    //post: Sets Round Total = 0
    //Setters
    public void setRoundTotal(){
        RoundTotal = 0;
    }



    //pre:
    //Post: returns BankerScore
    //Getters
    public int getBankerScore(){
        return BankerScore;
    }

    //pre:
    //Post: returns PlayerScore
    public int getPlayerScore(){
        return PlayerScore;
    }

    //pre:
    //Post: returns Round
    public int getRound(){
        return Round;
    }

    //pre:
    //Post: returns RoundTotal
    public int getRoundTotal(){
        return RoundTotal;
    }
}
