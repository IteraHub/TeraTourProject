package com.itera.teratour.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class UnityLaunch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toast.makeText(getApplicationContext(), "Called", Toast.LENGTH_LONG).show();

        startActivity(new Intent(getBaseContext(), UnityPlayerActivity.class));

        finish();
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
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

        finish();
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
