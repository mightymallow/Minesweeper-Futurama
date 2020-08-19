package com.example.gergely.minesweeper;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
//final submission
public class WelcomeScreenActivity extends AppCompatActivity {
    //test comment to see if branch is working correctly
    private boolean movedOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        fadeInText();
        playIntro();
        continueButton();
        continueAfterTime();
    }

    //displays animation for text to fade in
    public void fadeInText(){
        Animation myFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fadein);
        findViewById(R.id.words).startAnimation(myFadeInAnimation);
    }

    //plays intro sound
    public void playIntro(){
        MediaPlayer mPlayer = MediaPlayer.create(WelcomeScreenActivity.this, R.raw.intro);
        mPlayer.start();
    }

    //sets up continue button to go to menu screen
    public void continueButton(){
        Button continueClick = (Button) findViewById(R.id.continue_skip);

        continueClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movedOn = true;
                continueBlock();
                finish();
            }
        });
    }

    //continues after 15 seconds if user hasn't clicked continue
    public void continueAfterTime(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                if(movedOn == false) {
                    continueBlock();
                    finish();
                }
            }

        }, 15000L);
    }

    //method to continue to menu screen
    public void continueBlock(){
        MediaPlayer mPlayer = MediaPlayer.create(WelcomeScreenActivity.this, R.raw.fun);
        mPlayer.start();
        Intent goHomePage = new Intent(WelcomeScreenActivity.this,MenuActivity.class);
        startActivity(goHomePage);
    }
}
