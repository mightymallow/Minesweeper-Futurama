package com.example.gergely.minesweeper;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;

public class MessageFragment extends AppCompatDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //create the view to show
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.message_layout, null);

        //create a button listener
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
            }
        };

        //build the alert dialog
        return new AlertDialog.Builder(getActivity())
                .setTitle("Changing Message")
                .setView(v)
                .setPositiveButton(android.R.string.ok, listener)
                .create();
    }
}
