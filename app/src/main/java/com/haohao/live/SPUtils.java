package com.haohao.live;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * author: haohao
 * time: 2018/1/5
 * mail: haohaochang86@gmail.com
 * desc: description
 */
public class SPUtils {
    private static final String TAG = "SPUtils";
    private static final String SP_NAME = "app_info";
    public static final String KEY_NGINX_SER_URI = "niginx_server_uri";
    private static volatile SPUtils sInstance;
    private static SharedPreferences sSharedPref;

    private SPUtils(Context context){
        sSharedPref = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public static SPUtils getInstance(Context context){
        if (sInstance == null) {
            synchronized (SPUtils.class){
                if (sInstance == null) {
                    sInstance = new SPUtils(context);
                }
            }
        }
        return sInstance;
    }

    public void putString(String key, String value){
        sSharedPref.edit().putString(key, value).apply();
    }

    public String getString(String key){
        return sSharedPref.getString(key, null);
    }

}
