package org.example.moviesapp.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class KeyboardUtil {

    public static void hideKeyboard(Activity a) {
        if (a!=null) {
            try {
                InputMethodManager imm =
                        (InputMethodManager) a.getSystemService(Context.INPUT_METHOD_SERVICE);
                if(imm!=null) {
                    View v = a.getCurrentFocus();
                    if (v!=null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}