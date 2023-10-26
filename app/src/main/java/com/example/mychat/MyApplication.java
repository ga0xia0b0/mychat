package com.example.mychat;

import android.app.Application;
import android.content.Context;

import com.example.mychat.object.User;

import cn.bmob.v3.Bmob;

/**
 * @author gxb
 * initialize Bmob SDK and stores global variable
 */

public class MyApplication extends Application {

    private static Context context;
    private static MyApplication instance;
    private static User user;
    private static String shareUrl = "";

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        instance = this;
        Bmob.initialize(this, "7fa9fa4432fd47969754479a09c30da6");
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        MyApplication.context = context;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        MyApplication.user = user;
    }

    public static String getShareUrl() {
        return shareUrl;
    }

    public static void setShareUrl(String shareUrl) {
        MyApplication.shareUrl = shareUrl;
    }
}
