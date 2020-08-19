package com.example.gergely.minesweeper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.gergely.minesweeper.model.GameLogic;
import com.example.gergely.minesweeper.model.Singleton;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private Singleton theSingleton;
    private GameLogic logic;
    Button allTheButtons[][];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        theSingleton = Singleton.getInstance();
        setUpSingleton(getOptionsData(this));
        allTheButtons = new Button[theSingleton.getNumberOfRows()][theSingleton.getNumberOfColumns()];

        logic = new GameLogic();
        logic.createMinefield(theSingleton.getNumberOfRows(),theSingleton.getNumberOfColumns(),theSingleton.getNumberOfMines());

        updateTextViews();
        populateButtons();
    }

    //gets the saved options data from previous session and stores it in a temporary array
    static public int[] getOptionsData(Context context){
        SharedPreferences prefs = context.getSharedPreferences("AppPrefs", MODE_PRIVATE);
        int[] temporaryArray = new int[3];
        temporaryArray[0] = prefs.getInt("number of rows", 4);
        temporaryArray[1] = prefs.getInt("number of columns", 6);
        temporaryArray[2] = prefs.getInt("number of mines", 6);
        return temporaryArray;
    }

    //uses the temporary array created by getOptionsData method to set the Singleton classes values for row/column/mines
    private void setUpSingleton(int[] temporaryArray){
        theSingleton.setNumberOfRows(temporaryArray[0]);
        theSingleton.setNumberOfColumns(temporaryArray[1]);
        theSingleton.setNumberOfMines(temporaryArray[2]);
    }

    //places the rows and buttons on the screen
    private void populateButtons(){
        TableLayout table = (TableLayout) findViewById(R.id.tableForButtons);

        for (int rows = 0; rows < theSingleton.getNumberOfRows(); rows++){
            TableRow theTableRow = new TableRow(this);
            theTableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f));
            table.addView(theTableRow);
            for (int columns = 0; columns < theSingleton.getNumberOfColumns(); columns++){
                final int FINAL_ROW = rows;
                final int FINAL_COLUMN = columns;
                Button theButton = new Button(this);
                theButton.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));

                theButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gridButtonClicked(FINAL_ROW, FINAL_COLUMN);
                    }
                });

                allTheButtons[rows][columns] = theButton;
                theTableRow.addView(theButton);
            }
        }
    }

    //checks for different scenarios when a button is clicked
    private void gridButtonClicked(int rows, int columns){
        Button button = allTheButtons[rows][columns];
        lockButtonSizes();

        if(logic.checkHasBeenClicked(rows,columns) == false){

            //if it is a mine, play a random sound and then update
            if(logic.checkSpot(rows,columns) == true) {
                Random rand = new Random();
                int pick = rand.nextInt(6);
                switch(pick){
                    case 0:
                        MediaPlayer mPlayer = MediaPlayer.create(GameActivity.this, R.raw.money);
                        mPlayer.start();
                        break;
                    case 1:
                        MediaPlayer mPlayer2 = MediaPlayer.create(GameActivity.this, R.raw.beautiful);
                        mPlayer2.start();
                        break;
                    case 2:
                        MediaPlayer mPlayer3 = MediaPlayer.create(GameActivity.this, R.raw.whooo);
                        mPlayer3.start();
                        break;
                    case 3:
                        MediaPlayer mPlayer4 = MediaPlayer.create(GameActivity.this, R.raw.cash);
                        mPlayer4.start();
                        break;
                    case 4:
                        MediaPlayer mPlayer5 = MediaPlayer.create(GameActivity.this, R.raw.rich);
                        mPlayer5.start();
                        break;
                    case 5:
                        MediaPlayer mPlayer6 = MediaPlayer.create(GameActivity.this, R.raw.classy);
                        mPlayer6.start();
                        break;
                    default:
                        break;
                }

                updateTextViews();
                logic.setHasBeenClicked(rows,columns);
                updateButtonTexts(rows,columns);
                checkGameOver();

                //scale image to button, only works in JellyBean
                int newWidth = button.getWidth();
                int newHeight = button.getHeight();
                Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.treasure);
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
                Resources resource = getResources();
                button.setBackground(new BitmapDrawable(resource, scaledBitmap));

            } else {
                //if it is not a mine then scan and update
                int temp = logic.performScan(rows, columns, theSingleton.getNumberOfRows(), theSingleton.getNumberOfColumns());
                logic.setHasBeenClicked(rows,columns);
                logic.setHasBeenUsedToScan(rows,columns);
                String tempText = Integer.toString(temp);
                button.setText(tempText);
                updateTextViews();
            }
        } else if (logic.checkHasBeenUsedToScan(rows,columns) == false){
            //if it is a mine and has not done a scan
            int temp = logic.performScan(rows, columns, theSingleton.getNumberOfRows(), theSingleton.getNumberOfColumns());
            logic.setHasBeenUsedToScan(rows,columns);
            String tempText = Integer.toString(temp);
            button.setText(tempText);
            updateTextViews();

        }
    }

    //locks the size of the button images
    private void lockButtonSizes(){
        for (int row = 0; row < theSingleton.getNumberOfRows(); row++){
            for (int column = 0; column < theSingleton.getNumberOfColumns(); column++){
                Button button = allTheButtons[row][column];
                int width = button.getWidth();
                button.setMinimumWidth(width);
                button.setMaxWidth(width);
                int height = button.getHeight();
                button.setMinimumHeight(height);
                button.setMaxHeight(height);
            }
        }
    }

    //updates the text view fields of mines found and scans used
    private void updateTextViews(){
        TextView mineTracker = (TextView) findViewById(R.id.mine_status);
        mineTracker.setText("Found " + logic.getMinesFound() + " out of " + theSingleton.getNumberOfMines() + " mines");

        TextView scanTracker = (TextView) findViewById(R.id.scan_status);
        scanTracker.setText("# of scans used: " + logic.getScansUsed());


    }

    //updates all the text on the buttons of how many hidden mines are left
    private void updateButtonTexts(int row, int column){

            for (int i = 0; i < theSingleton.getNumberOfRows(); i++) {
                if (logic.checkHasBeenClicked(i, column) == true && logic.checkHasBeenUsedToScan(i, column) == true) {
                    String testing = allTheButtons[i][column].getText().toString();
                    int temp = Integer.parseInt(testing);
                    temp--;
                    String tempText = Integer.toString(temp);
                    allTheButtons[i][column].setText(tempText);
                }
            }

            for (int i = 0; i < theSingleton.getNumberOfColumns(); i++) {
                if (logic.checkHasBeenClicked(row, i) == true && logic.checkHasBeenUsedToScan(row, i) == true) {
                    int temp = Integer.parseInt(allTheButtons[row][i].getText().toString());
                    temp--;
                    String tempText = Integer.toString(temp);
                    allTheButtons[row][i].setText(tempText);
                }
            }
    }

    //checks if the game is over
    public void checkGameOver(){
     if(logic.getMinesFound() == theSingleton.getNumberOfMines()){
         Handler handler = new Handler();
         handler.postDelayed(new Runnable() {

             @Override
             public void run() {
                 Random rand = new Random();
                 int pick = rand.nextInt(2);
                 switch(pick){
                     case 0:
                         MediaPlayer mPlayer = MediaPlayer.create(GameActivity.this, R.raw.drunk);
                         mPlayer.start();
                         break;
                     case 1:
                         MediaPlayer mPlayer2 = MediaPlayer.create(GameActivity.this, R.raw.tired);
                         mPlayer2.start();
                         break;
                     default:
                         break;
                 }
             }

         }, 3000L);

         android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
         MessageFragment dialog =  new MessageFragment();
         dialog.show(manager, "Message dialog");
     }
    }
}
