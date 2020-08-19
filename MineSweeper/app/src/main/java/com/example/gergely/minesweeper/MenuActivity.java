package com.example.gergely.minesweeper;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import java.util.Random;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        startLogoSpin();
        goToHelp();
        goToOptions();
        goToGame();
    }

    //method to implement the planet express logo to do a spin animation indefinitely
    public void startLogoSpin() {

        RotateAnimation rotateAnimation1 = new RotateAnimation(0, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        rotateAnimation1.setInterpolator(new LinearInterpolator());
        rotateAnimation1.setDuration(8000);
        rotateAnimation1.setRepeatCount(Animation.INFINITE);
        findViewById(R.id.logo).startAnimation(rotateAnimation1);
    }

    //method to set up the help button to redirect to the help activity page
    public void goToHelp(){
        ImageView help = (ImageView) findViewById(R.id.help);

        help.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent goHelp = new Intent(MenuActivity.this,HelpActivity.class);
                startActivity(goHelp);
            }
        });
    }

    //method to set up the options button to redirect to the options activity page
    public void goToOptions(){
        ImageView options = (ImageView) findViewById(R.id.options);

        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goOptions = new Intent(MenuActivity.this,OptionsActivity.class);
                startActivity(goOptions);
            }
        });
    }

    //method to go to the game
    public void goToGame(){
        ImageView game = (ImageView) findViewById(R.id.start);

        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rand = new Random();
                int pick = rand.nextInt(2);
                switch(pick){
                    case 0:
                        MediaPlayer mPlayer = MediaPlayer.create(MenuActivity.this, R.raw.poor);
                        mPlayer.start();
                        break;
                    case 1:
                        MediaPlayer mPlayer2 = MediaPlayer.create(MenuActivity.this, R.raw.excited);
                        mPlayer2.start();
                        break;
                    default:
                        break;
                }
                Intent goGame = new Intent(MenuActivity.this,GameActivity.class);
                startActivity(goGame);
            }
        });
    }
}
