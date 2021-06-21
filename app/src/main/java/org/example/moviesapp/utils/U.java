package org.example.moviesapp.utils;


import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

@SuppressLint("LogNotTimber")
public class U {

    //set to true to see the logs
    public static final boolean IS_DEBUG =  false;//false for unit test

    @NonNull private static final String DEBUG_FLAG = "¿¿";

    @NonNull private static String getMsgWithFlag(@Nullable String msg) {
        return DEBUG_FLAG + msg;
    }

    public static void l(@Nullable String tag, @Nullable String msg) {
        if (IS_DEBUG) {
            Log.d(tag, getMsgWithFlag(msg));
//            Timber.d(getMsgWithFlag(msg));
        }
    }

    public static void le(@Nullable String tag, @Nullable String msg) {
        if (IS_DEBUG) {
            Log.d(tag, getMsgWithFlag(msg));
            Log.e(tag, getMsgWithFlag(msg));
//            Timber.d(getMsgWithFlag(msg));
//            Timber.d(getMsgWithFlag(msg));
        }
    }
}
