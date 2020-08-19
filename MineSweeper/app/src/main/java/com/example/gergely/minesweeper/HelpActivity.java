package com.example.gergely.minesweeper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        makeLinksWork();
        returnToMenu();

    }

    //allows links to work correctly
    public void makeLinksWork(){
        TextView linksTo = (TextView) findViewById(R.id.help_text);
        linksTo.setMovementMethod(LinkMovementMethod.getInstance());
    }

    //returns to main menu
    public void returnToMenu(){
        Button backButton = (Button) findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
