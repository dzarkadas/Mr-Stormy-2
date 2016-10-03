package com.example.ezlen.mrstormy2.ui.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

public class NetDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context mycontext = getActivity();
        AlertDialog.Builder mybuilder = new AlertDialog.Builder(mycontext);

        mybuilder.setTitle("No network connection!")
                .setMessage("Please enable network access and try again.")
                .setPositiveButton("OK", null);

        return mybuilder.create();
    }
}