package com.example.gleatonhw2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/*
David Gleaton - C88379585 - hgleato@clemson.edu
This activity displays the title page layout file, and starts either the .GameActivity or the .HelpActivity if their respective buttons are clicked
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    //whooo boy this'll be fun! :D

    //Inspiration from Zybooks Lights Out game
    public void onHelpClick(View view){
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }

    public void onStartClick(View view){
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

}