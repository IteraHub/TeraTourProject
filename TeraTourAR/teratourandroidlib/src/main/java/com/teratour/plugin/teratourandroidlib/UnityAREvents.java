package com.teratour.plugin.teratourandroidlib;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by root on 11/12/18.
 */

public class UnityAREvents {

    public static String videoPath;

    private static UnityAREvents instance;

    static Context context;

    private static IUnityAREvents unityAREvents;


    public UnityAREvents(){
        instance = this;
    }

    public void InitializeUnityAREvents(IUnityAREvents unityAREvents){

        UnityAREvents.unityAREvents = unityAREvents;
    }

    public static UnityAREvents Instance(){
        if(instance == null)
            instance = new UnityAREvents();

        return instance;
    }

    public void SetContext(Context context){
        UnityAREvents.context = context;
    }

    public void ShowMessage(String msg){
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void ShareVideo(String videoSharePath) {

        if(unityAREvents != null) unityAREvents.OnShareVideo(videoSharePath);
    }


    private static void StartPlaybackActivity(Activity activity){
        Intent intent = new Intent(activity, VideoPlayerActivity.class);
        intent.putExtra("videoPath", videoPath);

        VideoPlayerActivity.context = context;
        activity.startActivity(intent);
    }


    public static void OnRecordingStopped(final Activity activity){

        if(unityAREvents != null) unityAREvents.OnRecordingStopped();

        new AlertDialog.Builder(context).setTitle("Message").setTitle("Recording Finished")
                .setPositiveButton("Preview", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Toast.makeText(context, "Playback Feature in Development", Toast.LENGTH_LONG).show();
                        try{

                            StartPlaybackActivity(activity);

                        }catch(Exception e) {

                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton("Share", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "Share Feature in Development", Toast.LENGTH_LONG).show();

                        ShareVideo(videoPath);
                    }
                }).setMessage("Recording Stopped").show();

    }

    public static void OnRecordingStarted(){

        if(unityAREvents != null) unityAREvents.OnRecordingStarted();
    }

    public static void OnARTargetFound(String targetID){

        if(unityAREvents != null) unityAREvents.OnARTargetFound(targetID);
    }

    public static void OnARTargetLost(String targetID){

        if(unityAREvents != null) unityAREvents.OnARTargetLost(targetID);

    }
}