package com.example.mychat;

import android.content.Context;
import android.view.View;
import android.graphics.Typeface;

import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

/**
 * @author gxb
 * ①snackbar for short messages or reminders
 * ②fontmanager
 */
public class Util {

    public static void showSnackBar(String type, View view, String content, Context context) {
        if (type.contentEquals("blue")) {
            Snackbar snackbar = Snackbar.make(view, content, Snackbar.LENGTH_SHORT);
            snackbar.getView().setBackgroundColor(ContextCompat.getColor(context, R.color.colorBlue));
            snackbar.show();
        } else if (type.contentEquals("red")) {
            Snackbar snackbar = Snackbar.make(view, content, Snackbar.LENGTH_SHORT);
            snackbar.getView().setBackgroundColor(ContextCompat.getColor(context, R.color.colorRed));
            snackbar.show();
        } else if (type.contentEquals("yellow")) {
            Snackbar snackbar = Snackbar.make(view, content, Snackbar.LENGTH_SHORT);
            snackbar.getView().setBackgroundColor(ContextCompat.getColor(context, R.color.colorGold));
            snackbar.show();
        }
    }
}