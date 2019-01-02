package com.itera.teratour.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class UnityLaunch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startActivity(new Intent(getBaseContext(), UnityPlayerActivity.class));
    }

    @Override
    protected void onPause(){
        super.onPause();

    }

    @Override
    protected void onResume(){

        super.onResume();

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    // Trim Memory Unity
    @Override public void onTrimMemory(int level)
    {
        super.onTrimMemory(level);
    }

    // Low Memory Unity
    @Override public void onLowMemory()
    {
        super.onLowMemory();
    }
}
