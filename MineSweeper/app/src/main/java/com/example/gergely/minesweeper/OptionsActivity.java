package com.example.gergely.minesweeper;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.gergely.minesweeper.model.Singleton;

public class OptionsActivity extends AppCompatActivity {

    private Singleton theSingleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        theSingleton = Singleton.getInstance();
        createBoardSizeRadio();
        createNumberMinesRadio();
        createDoneOptions();

    }

    //creates the radio button menu for selecting the board size
    public void createBoardSizeRadio() {
        RadioGroup radio1 = (RadioGroup) findViewById(R.id.radio_group_board_size);
        int[] boardSizes = getResources().getIntArray(R.array.board_sizes);

        //create the buttons
        for (int i = 0; i < boardSizes.length; i++) {
            int boardSize = boardSizes[i];
            RadioButton button = new RadioButton(this);
            switch (i) {
                case 0:
                    button.setText(getString(R.string.six_columns, boardSize));
                    break;
                case 1:
                    button.setText(getString(R.string.ten_columns, boardSize));
                    break;
                case 2:
                    button.setText(getString(R.string.fifteen_columns, boardSize));
                    break;
                default:
                    break;
            }
            //add to radio group
            radio1.addView(button);
        }
    }

    //creates the radio button menu for selecting the board size
    public void createNumberMinesRadio() {
        RadioGroup radio2 = (RadioGroup) findViewById(R.id.radio_group_number_mines);
        int[] numberOfMines = getResources().getIntArray(R.array.number_mines);

        //create the buttons
        for (int i = 0; i < numberOfMines.length; i++) {
            int numberMines = numberOfMines[i];
            RadioButton button = new RadioButton(this);
            switch (i) {
                case 0:
                    button.setText(getString(R.string.six_mines, numberMines));
                    break;
                case 1:
                    button.setText(getString(R.string.ten_mines, numberMines));
                    break;
                case 2:
                    button.setText(getString(R.string.fifteen_mines, numberMines));
                    break;
                case 3:
                    button.setText(getString(R.string.twenty_mines, numberMines));
                    break;
                default:
                    break;
            }
            //add to radio group
            radio2.addView(button);
        }
    }

    /*method to implement the done button in order to save the selected options
    for the simpleton class as well as calls the method to save the values to prefs for next session use
    */
    public void createDoneOptions() {
        Button optionsButton = (Button) findViewById(R.id.done_options);

        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup radio1 = (RadioGroup) findViewById(R.id.radio_group_board_size);
                RadioGroup radio2 = (RadioGroup) findViewById(R.id.radio_group_number_mines);
                int idOfSelectedSize = radio1.getCheckedRadioButtonId();
                int idOfSelectedMines = radio2.getCheckedRadioButtonId();

                if (idOfSelectedSize != -1 && idOfSelectedMines != -1) {

                    RadioButton button1 = findViewById(idOfSelectedSize);
                    RadioButton button2 = findViewById(idOfSelectedMines);
                    String message1 = button1.getText().toString();
                    String message2 = button2.getText().toString();

                    switch (message1) {
                        case "4 rows by 6 columns":
                            theSingleton.setNumberOfRows(4);
                            theSingleton.setNumberOfColumns(6);
                            break;
                        case "5 rows by 10 columns":
                            theSingleton.setNumberOfRows(5);
                            theSingleton.setNumberOfColumns(10);
                            break;
                        case "6 rows by 15 columns":
                            theSingleton.setNumberOfRows(6);
                            theSingleton.setNumberOfColumns(15);
                            break;
                        default:
                            break;
                    }

                    switch (message2) {
                        case "6 mines":
                            theSingleton.setNumberOfMines(6);
                            break;
                        case "10 mines":
                            theSingleton.setNumberOfMines(10);
                            break;
                        case "15 mines":
                            theSingleton.setNumberOfMines(15);
                            break;
                        case "20 mines":
                            theSingleton.setNumberOfMines(20);
                            break;
                        default:
                            break;
                    }
                    saveOptionsData(theSingleton.getNumberOfRows(), theSingleton.getNumberOfColumns(), theSingleton.getNumberOfMines());
                    finish();
                } else {
                    Toast.makeText(OptionsActivity.this, "Please select both settings!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //saves current session fields (passed in singleton field values using getters) for future use
    private void saveOptionsData(int numberOfRow, int numberOfColumns, int numberOfMines) {
        SharedPreferences prefs = this.getSharedPreferences("AppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("number of rows", numberOfRow);
        editor.putInt("number of columns", numberOfColumns);
        editor.putInt("number of mines", numberOfMines);
        editor.apply();
    }
}
