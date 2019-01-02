package com.iterahub.teratour.repository;

import android.support.annotation.NonNull;

import com.iterahub.teratour.logics.AccountLogics;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkInterceptor implements Interceptor {
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();

        long t1 = System.nanoTime();
        AccountLogics.logMsg(this.getClass(),String.format(Locale.getDefault(),
                "Sending request %s on %s%n%s",request.url(), chain.connection(), request.headers()));

        Response response = chain.proceed(request);

        long t2 = System.nanoTime();
        AccountLogics.logMsg(this.getClass(),String.format(Locale.getDefault(),
                "Received response for %s in %.1fms%n%s",response.request().url(),
                (t2 - t1) / 1e6d, response.headers()));

        return response;
    }
}
