package com.iterahub.teratour.logics;

import android.util.Base64;
import android.util.Log;

public class AccountLogics {


    public static String generateAuth(String username, String password){
        String auth = username + ":" + password;
        String encodedText = Base64.encodeToString(auth.getBytes(), Base64.NO_WRAP);
        logMsg(AccountLogics.class,encodedText);
        return encodedText;
    }

    public static void logMsg(Class owner, String msg){
        Log.e(owner.getSimpleName(),msg != null ? msg:"Message is null");
    }
}
