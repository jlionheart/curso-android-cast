package com.curso_android_cast.cursoandroidcast.util;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {

    private NetworkUtil(){
        super();
    }

    private static NetworkInfo getActiveNetwork(){
        ConnectivityManager connectivityManager =  (ConnectivityManager)AppUtil.CONTEXT.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo();
    }

    public static boolean isAvailable(){
        NetworkInfo activeNetwork = getActiveNetwork();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static boolean isWifi(){
        NetworkInfo activeNetwork = getActiveNetwork();
        return activeNetwork != null && activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
    }

    public static boolean isMobile(){
        NetworkInfo activeNetwork = getActiveNetwork();
        return activeNetwork != null && activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE;
    }
}
