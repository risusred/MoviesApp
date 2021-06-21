package org.example.moviesapp.ui;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.example.moviesapp.MyApplication;
import org.example.moviesapp.utils.InternetUtil;
import org.example.moviesapp.utils.U;

public abstract class BaseActivity extends AppCompatActivity {

    private void destroyLeekCanary() {
        if (U.IS_DEBUG) {
            MyApplication app = (MyApplication) getApplication();
            app.mustDieLeakCanary(this);
        }
    }

    @Override protected void onDestroy() {
        destroyLeekCanary(); // _up CANARY
        super.onDestroy();
    }

    // TOAST
    protected void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    // INTERNET
    protected void isBadInternetShowMessage() {
        InternetUtil.isBadInternetShowMessage(this, null);
    }

    // LOG
    protected void l(String msg) {
        U.l(getClass().getSimpleName(), msg);
    }
}