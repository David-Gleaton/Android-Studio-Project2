package com.example.gleatonhw2;


import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;


import android.content.Context;
import android.os.Bundle;


import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;

/*
Main Controller for the Game
David Gleaton - C88379585 - hgleato@clemson.edu
2/17/21
 */

//Implements 3 fragments
public class GameActivity extends AppCompatActivity
    implements TargetDialogFragment.OnTargetClickListener, WinLossFragment.OnWinLossClickListener, GetNameFragment.OnNameClickListener{

    //Private variables for game flow
    private PigDiceGame TheGame;
    private int mTargetValue;
    private int max = 7;
    private int min = 1;
    private boolean isPlayerTurn;
    //Difficulty Banker cut offs
    private int difficultly = 1;
    private double normal_diff = 0.05;
    private double cut_throat = 0.15;
    private String PlayerName;


    //pre: GameActivity has started and dialogue prompt completed
    //post: Sets PlayerName to name in the dialogue
    public void onNameValue(String value){
        PlayerName = value;
        TextView leaderboard = findViewById(R.id.LeaderBoard);
        String leaderboard_string = PlayerName + TheGame.getPlayerScore() + getString(R.string.Banker_Title) + TheGame.getBankerScore();
        leaderboard.setText(leaderboard_string);

    }

    //pre: Win Condition has been reached
    //post: Starts new game or finishes GameActivity
    public void onWinLoss(int which){
        if(which == 1){
            newGameStart();
            setTheGame();
        }else{
            finish();
        }
    }


    //pre: GameActivity has started and dialogue has been completed
    //Post: changes TargetValue to set number if one was entered
    @Override
    public void onTargetValue(int value){
        mTargetValue = value;
        TextView TargetScore = findViewById(R.id.TargetScore);
        String TargetValue = getString(R.string.Target_Score_Title) + String.valueOf(mTargetValue);
        TargetScore.setText(TargetValue);
    }

    //pre:
    //Post: Returns new game and sets programmatically the values to be passed to View
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //Create New Game Model Instances
        TheGame = new PigDiceGame();

        //Set New Game Values
        newGameStart();
        //Set Dafault Player name if none was entered
        PlayerName = getString(R.string.Player_Title);
        //Set layout values from Game Values
        setTheGame();


        //Create Spinner for difficulty setting
        //This code was heavily inspired from the Zybook chapter on Wigets
        Spinner spinner = findViewById(R.id.spinner_diff);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.diff_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                difficultly = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



    }


    //pre:
    //post: Sets the actions for a new game, including target score and username dialogues. Also clears all Model values and sets the player turn to true.
    private void newGameStart(){
        //Activate Dialogue Manager to grab Target Score
        FragmentManager manager = getSupportFragmentManager();
        TargetDialogFragment dialogue = new TargetDialogFragment();
        dialogue.show(manager, "TargetDialogFragment");
        //Activate Dialogue to Grab Player Name
        GetNameFragment dialogue_name = new GetNameFragment();
        dialogue_name.show(manager, "NameDialogFragment");
        //Create New Game
        TheGame.newGame();
        isPlayerTurn = true;
    }

    //pre: TheGame is instataited
    //post: Updates the view with new values
    private void setTheGame(){

        TextView leaderboard = findViewById(R.id.LeaderBoard);
        TextView Round = findViewById(R.id.Round);
        TextView RoundTotal = findViewById(R.id.RoundTotal);
        String leaderboard_string = PlayerName + TheGame.getPlayerScore() + getString(R.string.Banker_Title) + TheGame.getBankerScore();
        leaderboard.setText(leaderboard_string);

        String roundtext;
        if(isPlayerTurn) {
            roundtext = getString(R.string.Round_Title) + TheGame.getRound() + getString(R.string.Player_Turn);
        }else{
            roundtext = getString(R.string.Round_Title) + TheGame.getRound() + getString(R.string.Banker_Turn);
        }
        Round.setText(roundtext);

        String roundtotaltext = getString(R.string.RoundTotal_Title) + TheGame.getRoundTotal();
        RoundTotal.setText(roundtotaltext);
    }

    //pre: TheGame is instataited
    //Post: Updates Banker or Player score, resets RoundTotal, and increments Round #
    //      Displays a toast message indicating a Bank action took place
    public void onBankClick(View view){
        //Toast message to better give the player an understanding on what happened
        if(isPlayerTurn) {
            Toast.makeText(this, (PlayerName+getString(R.string.PlayerBanked)), Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, R.string.BankerBanked, Toast.LENGTH_SHORT).show();
        }
        //Activates TheGame method to increase total score
        TheGame.addBank(isPlayerTurn);
        //Flip the turn
        isPlayerTurn = !isPlayerTurn;
        //Set Round total to 0
        TheGame.setRoundTotal();
        //Increment the Round #
        TheGame.incrementRound();
        //Update the view
        setTheGame();
        //Check to see if either Banker or Player has won
        if(TheGame.getBankerScore() >= mTargetValue){
            //Bank wins
            playerWinorLoss(1);
        }else if(TheGame.getPlayerScore() >= mTargetValue){
            //Player wins
            playerWinorLoss(0);
        }else {
            //If it is now the banker's turn, activate the BankerTurn method.
            if (!isPlayerTurn) {
                onBankerTurn(view);
            }
        }
    }


    //pre: TheGame is intianted, Player || Banker has taken a roll action
    //Post: Displays the randomly rolled dice, handles a rolled 1 and its actions, and displays a toast if a 1 was rolled
    public void onRollClick(View view){

        //Timer Object inspired by the Zybooks chapter on Dice Roller App
        CountDownTimer mTimer = new CountDownTimer(2000, 100) {
            int roll = 0;
            //Context object to send a Toast to the GameActivity
            Context context = GameActivity.this;
            //Roll and display random dice to simulate dice rolling
            public void onTick(long milliUntilFinsihed) {
                ImageView Dice = findViewById(R.id.Dice);
                roll = (int)(Math.random() * (max - min) + min);
                switch (roll) {
                    case 1:
                        Dice.setImageResource(R.drawable.dice_1);
                        break;
                    case 2:
                        Dice.setImageResource(R.drawable.dice_2);
                        break;
                    case 3:
                        Dice.setImageResource(R.drawable.dice_3);
                        break;
                    case 4:
                        Dice.setImageResource(R.drawable.dice_4);
                        break;
                    case 5:
                        Dice.setImageResource(R.drawable.dice_5);
                        break;
                    case 6:
                        Dice.setImageResource(R.drawable.dice_6);
                        break;
                }

            }

            //Take final rolled dice and act upon the number
            public void onFinish() {
                if(roll > 1) {
                    //If the dice is not a one, add it to the Roundtotal
                    TheGame.rollManager(roll);
                }else{
                    //Display Toast to indicated a rolled 1
                    if(isPlayerTurn) {
                        Toast.makeText(context, (PlayerName+getString(R.string.Player_Rolled_1)), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context, R.string.Banker_Rolled_1, Toast.LENGTH_SHORT).show();
                    }
                    //Flip turn order, set Round total to 0 and increment the round #
                    isPlayerTurn = !isPlayerTurn;
                    TheGame.setRoundTotal();
                    TheGame.incrementRound();
                }
                //Update the board
                setTheGame();
                //If it is now the banker's turn, call BankerTurn method
                if(!isPlayerTurn) { onBankerTurn(view);}
            }
        }.start();

    }

    //pre: Player or Banker has reached the win condition
    //Post: Displays a dialogue prompting new game or quit
    private void playerWinorLoss(int win){
        //Activate Dialogue Manager to grab Target Score
        FragmentManager manager = getSupportFragmentManager();
        WinLossFragment dialogue = WinLossFragment.newInstance(win);
        dialogue.show(manager, "WinLossFragment");
    }

    //pre: isPlayerTurn == false
    //post: Conducts a simulated Banker turn based on difficulty
    private void onBankerTurn(View view){
        //1/2 second delay to better give the player an understanding
        SystemClock.sleep(500);
        //Switch case on difficutly: 0 is random, 1 is normal, and 2 is cut throat
        switch(difficultly){
            //Randomly roll and if even, the banker banks and if odd, the banker rolls
            case 0:
                int randomturn = (int)(Math.random() * (max - min) + min);
                //If even, bank, if odd, roll
                //Added a Toast to make it clear that the banker banked
                if(randomturn % 2 == 0 ){
                    onBankClick(view);
                }else{
                    onRollClick(view);
                }
                break;
            case 1:
                //If the Banker round total is >= the target value * normal_diff, bank, otherwise roll
                //Bank if at score
                if(TheGame.getRoundTotal() >= mTargetValue*normal_diff || (TheGame.getBankerScore() + TheGame.getRoundTotal()  >= mTargetValue)){
                    onBankClick(view);
                    //Else roll again
                }else{
                    onRollClick(view);
                }
                break;
            case 2:
                //If the Banker round total is >= the target value * cut_throat value, bank, otherwise roll
                //Bank if at score
                if(TheGame.getRoundTotal() >= mTargetValue*cut_throat || (TheGame.getBankerScore() + TheGame.getRoundTotal()  >= mTargetValue)){
                    onBankClick(view);
                    //Else roll again
                }else{
                    onRollClick(view);
                }
                break;
        }


    }


}