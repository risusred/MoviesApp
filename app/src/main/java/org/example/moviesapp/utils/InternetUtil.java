package org.example.moviesapp.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;

import org.example.moviesapp.R;

@SuppressWarnings("unused")
public class InternetUtil {

    @NonNull private static final String LOG_TAG = InternetUtil.class.getSimpleName();

    public static void isBadInternetShowMessage(@Nullable Activity activity,
                                                @Nullable View parentView) {
        if (!doIHaveInternet(activity)) {
            showSnackBar(activity, parentView);
        }
    }

    private static void showSnackBar(@Nullable Activity activity,
                                     @Nullable View parentView) {
        if(parentView == null){
            if(activity != null)parentView = activity.findViewById(android.R.id.content);
        }
        if(parentView != null) {
            Snackbar.make(parentView, R.string.error_no_internet_connection,
                    Snackbar.LENGTH_SHORT).show();
//                    .setAction(R.string.cancel, view -> {
//                    }).show();
        }
    }

    private static boolean doIHaveInternet(@Nullable Activity activity) {
        if(activity == null) return false;
        Context context =  activity.getApplicationContext();
        return isInternetOK(context);
    }

    public static boolean isInternetOK(@Nullable Context ctx) {
        if(ctx == null) return false;
        try {
            return getConnectivityAndCheck(ctx);
        } catch (Exception e) {
            U.le(LOG_TAG, "ko " + e);
            return false;
        }
    }

    private static boolean getConnectivityAndCheck(@NonNull Context ctx) {
        ConnectivityManager cm =
                (ConnectivityManager) ctx.getSystemService(
                        Context.CONNECTIVITY_SERVICE);
        if(cm==null) return false;
        NetworkInfo i = cm.getActiveNetworkInfo();
        if (i==null) return false;
        if (!i.isConnected()) return false;
        if (!i.isAvailable()) return false;
        // noinspection RedundantIfStatement
        if (!i.isConnectedOrConnecting()) return false;
        return true;
    }
}