package com.richzjc.rdownload;

import android.app.Application;
import androidx.multidex.MultiDex;

public class BApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
    }
}
