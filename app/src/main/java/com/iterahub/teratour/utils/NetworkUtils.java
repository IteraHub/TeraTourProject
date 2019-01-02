package com.iterahub.teratour.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.iterahub.teratour.TeraTour;

public class NetworkUtils {

    public static boolean isNetworkAvailable(){
        ConnectivityManager cm =
                (ConnectivityManager) TeraTour.getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return (activeNetwork != null) &&
                activeNetwork.isConnectedOrConnecting();
    }
}
