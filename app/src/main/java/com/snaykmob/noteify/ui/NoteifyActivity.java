package com.snaykmob.noteify.ui;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public abstract class NoteifyActivity extends AppCompatActivity{
    public void showSnackbar(String message, int color) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(color);
        snackbar.show();
    }
}
