package com.iterahub.teratour.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.iterahub.teratour.api_service.SlackWebService;
import com.iterahub.teratour.models.SlackMessage;
import com.iterahub.teratour.utils.Constants;

import org.parceler.Parcels;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ACER on 4/14/2018.
 */

public class SendToSlackService extends IntentService {

    public SendToSlackService(){
        super("SendToSlackService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        SlackMessage slackMessage = Parcels.unwrap(intent.getParcelableExtra(Constants.SLACK_MESSAGE));
        if(slackMessage != null){
            sendMessageToSlack(slackMessage);
            Log.e(SendToSlackService.class.getSimpleName(),"slackMessage is not null");
        }
        else{
            Log.e(SendToSlackService.class.getSimpleName(),"slackMessage is null");
        }

    }

    private void sendMessageToSlack(SlackMessage slackMessage){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://hooks.slack.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SlackWebService slackWebService = retrofit.create(SlackWebService.class);

        try {
           slackWebService.sendMessageToSlack(slackMessage).execute();
            Log.e(SendToSlackService.class.getSimpleName(),"Request executed");
        }
        catch (Exception e){
            Log.e(SendToSlackService.class.getSimpleName(),"Error " + e.getMessage());
        }
    }
}
