package com.iterahub.teratour;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;

import com.cloudinary.android.MediaManager;
import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.Stetho;
import com.iterahub.teratour.database.AppDatabase;
import com.iterahub.teratour.models.SlackMessage;
import com.iterahub.teratour.services.SendToSlackService;
import com.iterahub.teratour.utils.Constants;

import io.fabric.sdk.android.Fabric;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ACER on 2/4/2018.
 */

public class TeraTour extends Application {

    private static AppDatabase INSTANCE;
    private static Context context;
    private static TeraTour application;

    @Override
    public void onCreate(){
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        Stetho.initializeWithDefaults(this);

        TeraTour.context = getApplicationContext();

        //init cloudinary sdk
        initCloudinary();

        //this line of code handles app crash and forward info the slack
//        Thread.setDefaultUncaughtExceptionHandler(
//                this::handleUncaughtException);
    }

    private void initCloudinary(){
        Map<String,String> config = new HashMap<>();
        config.put("cloud_name","arinzedroid");
        config.put("api_key","265135529372356");
        config.put("api_secret","Q67XzFgifDNrhP5ODS-1EKnC4oE");
        MediaManager.init(this,config);
    }

    private void handleUncaughtException(Thread thread, Throwable e){
        String errorMsg = e.getMessage();
        String detailedMsg = e.getLocalizedMessage();

        SlackMessage slackMessage = new SlackMessage();
        SlackMessage.Attachment attachment0 = new SlackMessage.Attachment();
        SlackMessage.Attachment attachment1 = new SlackMessage.Attachment();
        SlackMessage.Attachment attachment2 = new SlackMessage.Attachment();

        attachment0.setTitle("Errors on Teratour App");
        attachment1.setTitle("Error Message");
        attachment1.setText(errorMsg);
        attachment2.setTitle("Detailed Error Message");
        attachment2.setText(detailedMsg);

        List<SlackMessage.Attachment> attachmentList = new ArrayList<>();
        attachmentList.add(attachment0);
        attachmentList.add(attachment1);
        attachmentList.add(attachment2);

       slackMessage.setAttachments(attachmentList);

       Intent intent = new Intent(this, SendToSlackService.class);
       intent.putExtra(Constants.SLACK_MESSAGE, Parcels.wrap(slackMessage));
       startService(intent);
    }

    @Override
    public void attachBaseContext(Context base){
        super.attachBaseContext(base);
        application = this;
    }

    public static TeraTour getApplication(){
        return application;
    }


    public static AppDatabase getAppDatabase(){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(TeraTour.context,AppDatabase.class,"TeraTourDB")
                    .build();
        }
        return INSTANCE;
    }

    public static void DestroyInstance(){
        INSTANCE = null;
    }
}
