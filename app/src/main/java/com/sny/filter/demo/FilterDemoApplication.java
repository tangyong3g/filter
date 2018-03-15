package com.sny.filter.demo;

import android.app.Application;

import com.sny.filter.FilterManager;

/**
 * Created by Admin on 2018/2/6.
 */

public class FilterDemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FilterManager.getInstance(getApplicationContext());
    }
}
