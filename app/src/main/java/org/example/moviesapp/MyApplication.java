package org.example.moviesapp;

import android.app.Application;

import androidx.annotation.Nullable;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import org.example.moviesapp.utils.U;

import timber.log.Timber;


public class MyApplication extends Application {

    private static final boolean IS_LEAK_CANARY = true; // _up CANARY
    @Nullable private RefWatcher mRefWatcher;

    @Override public void onCreate() {
        super.onCreate();
        initLeakCanary();
        initTimber();
    }

    //TIMBER
    private void initTimber() {
        Timber.plant(new Timber.DebugTree());
    }

    //LEAK CANARY
    @SuppressWarnings("ConstantConditions")
    private boolean isDebugYLeakCanary() { return U.IS_DEBUG && IS_LEAK_CANARY; }

    public void initLeakCanary() {
        if(isDebugYLeakCanary()) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                return;
            }
            mRefWatcher = LeakCanary.install(this);
        }
    }

    public void mustDieLeakCanary(@Nullable Object object) {
        if(isDebugYLeakCanary()) {
            if (mRefWatcher != null) mRefWatcher.watch(object);
        }
    }
}