package com.teratour.plugin.teratourandroidlib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoPlayerActivity extends Activity {

    static VideoView videoView;
    static MediaController mediaController;

    private static VideoPlayerActivity instance;

    static Context context;


    public VideoPlayerActivity(){
        instance = this;


    }

    public static VideoPlayerActivity Instance(){
        if(instance == null)
            instance = new VideoPlayerActivity();

        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        videoView = findViewById(R.id.videoView);

        mediaController = new MediaController(this);

        Intent intent = getIntent();

        if(intent != null && intent.getExtras() != null){
            PlayVideo(intent.getStringExtra("videoPath"));
        }
    }



    private void PlayVideo(String path){


        Uri videoUri = Uri.parse(path);

        videoView.setVideoURI(videoUri);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);

        videoView.start();
    }

}
