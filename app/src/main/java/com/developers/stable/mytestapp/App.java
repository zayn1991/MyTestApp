package com.developers.stable.mytestapp;

import android.app.Application;
import android.content.Context;

import com.developers.stable.mytestapp.data.network.NetworkClass;

public class App extends Application {

    static App sInstance;
    static NetworkClass networkClass;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        networkClass = new NetworkClass();
    }

    public static Context getInstance() {
        return sInstance;
    }

    public static NetworkClass getNetworkClass() {
        return networkClass;
    }
}
