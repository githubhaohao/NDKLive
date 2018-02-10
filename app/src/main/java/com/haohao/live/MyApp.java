package com.haohao.live;

import android.app.Application;

/**
 * author: haohao
 * time: 2018/1/5
 * mail: haohaochang86@gmail.com
 * desc: description
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        System.loadLibrary("live");
    }
}
