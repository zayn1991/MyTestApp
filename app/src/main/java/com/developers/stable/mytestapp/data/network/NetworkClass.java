package com.developers.stable.mytestapp.data.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.developers.stable.mytestapp.App;
import com.developers.stable.mytestapp.BuildConfig;
import com.developers.stable.mytestapp.utils.Constants;
import com.developers.stable.mytestapp.data.network.interceptors.CacheInterceptor;
import com.developers.stable.mytestapp.data.network.interceptors.OfflineCacheInterceptor;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClass {

    OkHttpClient getHttp() {
        return new OkHttpClient.Builder()
                .addInterceptor(offlineCacheInterceptor())
                .addInterceptor(loggingInterceptor())
                .addNetworkInterceptor(cacheInterceptor())
                .cache(getCache())
                .build();
    }

    OfflineCacheInterceptor offlineCacheInterceptor() {
        return new OfflineCacheInterceptor();
    }

    Cache getCache() {
        int cacheSize = 10 * 1024 * 1024; // 10 MB
        return new Cache(new File(App.getInstance().getCacheDir(), "http-cache"), cacheSize);
    }

    CacheInterceptor cacheInterceptor() {
        return new CacheInterceptor();
    }

    HttpLoggingInterceptor loggingInterceptor() {
        return new HttpLoggingInterceptor()
                .setLevel(BuildConfig.DEBUG
                        ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.BASIC);
    }

    Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Constants.URL_MAIN)
                .client(getHttp())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public ApiService getApiService() {
        return getRetrofit().create(ApiService.class);
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

}
