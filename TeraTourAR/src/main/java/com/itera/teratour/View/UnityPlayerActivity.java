package com.itera.teratour.View;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.everyplay.Everyplay.Everyplay;
import com.everyplay.Everyplay.IEveryplayListener;
import com.itera.teratour.Model.TargetInfoModel;
import com.itera.teratour.R;
import com.itera.teratour.databinding.ArViewBinding;
import com.itera.teratour.databinding.TargetInfoLayoutBinding;
import com.itera.teratour.viewmodel.ARViewModel;
import com.itera.teratour.viewmodel.TargetInfoViewModel;
import com.teratour.plugin.teratourandroidlib.IUnityAREvents;
import com.teratour.plugin.teratourandroidlib.UnityAREvents;
import com.unity3d.player.UnityPlayer;

//import android.databinding.DataBindingUtil;

public class UnityPlayerActivity extends AppCompatActivity implements IEveryplayListener, IUnityAREvents {
    protected UnityPlayer mUnityPlayer; // don't change the name of this variable; referenced from native code

	private BottomSheetBehavior bottomSheetBehavior;

    FrameLayout unity_view;

     ArViewBinding arViewBinding;

     ARViewModel arViewModel;

     TargetInfoLayoutBinding targetInfoLayoutBinding;

     TargetInfoViewModel targetInfoViewModel;

    // Setup activity layout
    @Override protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        
        mUnityPlayer = new UnityPlayer(this);

        //setContentView(R.layout.ar_view);

        arViewBinding = DataBindingUtil.setContentView(this, R.layout.ar_view);

        targetInfoLayoutBinding = TargetInfoLayoutBinding.inflate(getLayoutInflater());

        arViewModel = ViewModelProviders.of(this).get(ARViewModel.class);

        targetInfoViewModel = ViewModelProviders.of(this).get(TargetInfoViewModel.class);

        targetInfoLayoutBinding.setLifecycleOwner(this);

        arViewBinding.setArViewModel(arViewModel);

        arViewBinding.setLifecycleOwner(this);

        arViewBinding.markerInfo.setTargetInfo(targetInfoViewModel);

        //targetInfoLayoutBinding.setTargetInfo(targetInfoViewModel);



        /*targetInfoViewModel.TargetInfo().observe(this, new Observer<TargetInfoModel>() {

            @Override
            public void onChanged(@Nullable TargetInfoModel targetInfoModel) {

                TextView targetName = findViewById(R.id.targetName);
                targetName.setText(targetInfoModel.getTargetName());
            }
        });*/



        unity_view = findViewById(R.id.unity_view);

        unity_view.addView(mUnityPlayer.getView());

        mUnityPlayer.requestFocus();

        unity_view.requestFocus();

        Everyplay.initEveryplay(this, this);
        UnityAREvents.Instance().InitializeUnityAREvents(this);


        initLayout();

    }

    @Override protected void onNewIntent(Intent intent)
    {
        // To support deep linking, we need to make sure that the client can get access to
        // the last sent intent. The clients access this through a JNI api that allows them
        // to get the intent set on launch. To update that after launch we have to manually
        // replace the intent with the one caught here.
        setIntent(intent);
    }

    // Quit Unity
    @Override protected void onDestroy ()
    {
        mUnityPlayer.quit();
        super.onDestroy();
    }

    // Pause Unity
    @Override protected void onPause()
    {
        super.onPause();
        mUnityPlayer.pause();
    }

    // Resume Unity
    @Override protected void onResume()
    {
        super.onResume();
        mUnityPlayer.resume();
    }

    @Override protected void onStart()
    {
        super.onStart();
        mUnityPlayer.start();
    }

    @Override protected void onStop()
    {
        super.onStop();
        mUnityPlayer.stop();
    }

    // Low Memory Unity
    @Override public void onLowMemory()
    {
        super.onLowMemory();
        mUnityPlayer.lowMemory();
    }

    // Trim Memory Unity
    @Override public void onTrimMemory(int level)
    {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_RUNNING_CRITICAL)
        {
            mUnityPlayer.lowMemory();
        }
    }

    // This ensures the layout will be correct.
    @Override public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        mUnityPlayer.configurationChanged(newConfig);
    }

    // Notify Unity of the focus change.
    @Override public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        mUnityPlayer.windowFocusChanged(hasFocus);
    }

    // For some reason the multiple keyevent type is not supported by the ndk.
    // Force event injection by overriding dispatchKeyEvent().
    @Override public boolean dispatchKeyEvent(KeyEvent event)
    {
        if (event.getAction() == KeyEvent.ACTION_MULTIPLE)
            return mUnityPlayer.injectEvent(event);
        return super.dispatchKeyEvent(event);
    }

    // Pass any events not handled by (unfocused) views straight to UnityPlayer
    @Override public boolean onKeyUp(int keyCode, KeyEvent event)     { return mUnityPlayer.injectEvent(event); }
    @Override public boolean onKeyDown(int keyCode, KeyEvent event)   { return mUnityPlayer.injectEvent(event); }
    @Override public boolean onTouchEvent(MotionEvent event)          { return mUnityPlayer.injectEvent(event); }
    /*API12*/ public boolean onGenericMotionEvent(MotionEvent event)  { return mUnityPlayer.injectEvent(event); }

@Override
    public void onEveryplayShown() {

    }

    @Override
    public void onEveryplayHidden() {

    }

    @Override
    public void onEveryplayReadyForRecording(int i) {
        Toast.makeText(getBaseContext(), "Recording Ready", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onEveryplayRecordingStarted() {
        Toast.makeText(getBaseContext(), "Recording Started...", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onEveryplayRecordingStopped() {
        Toast.makeText(getBaseContext(), "Recording Stopped...", Toast.LENGTH_LONG).show();

    }



    @Override
    public void onEveryplayThumbnailReadyAtTextureId(int i, int i1) {

    }

    @Override
    public void onFileReady(String s) {

    }




    private void initLayout()
    {

        /*CardView markerInfo = findViewById(R.id.marker_info);

        // animate markerInfo
        findViewById(R.id.marker_info_btn).setOnClickListener(view -> {
            if (markerInfo.getVisibility() == View.VISIBLE){
                markerInfo.setVisibility(View.INVISIBLE);
                markerInfo.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down));

            }else{

                markerInfo.setVisibility(View.VISIBLE);
                markerInfo.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up));
            }

        });
*/
        /*findViewById(R.id.marker_info_btn).setOnLongClickListener(view -> {
            if (!Everyplay.isRecording())
                Everyplay.startRecording();
            else
                Everyplay.stopRecording();
            return false;
        });*/

        View bottomSheet = findViewById(R.id.bottom_sheet);

        bottomSheet.bringToFront();

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            /**
             * Called when the bottom sheet changes its state.
             *
             * @param bottomSheet The bottom sheet view.
             * @param newState    The new state. This will be one of {@link #STATE_DRAGGING},
             *                    {@link #STATE_SETTLING}, {@link #STATE_EXPANDED},
             *                    {@link #STATE_COLLAPSED}, or {@link #STATE_HIDDEN}.
             */
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                switch (newState){
                    case BottomSheetBehavior.STATE_DRAGGING:
                        mUnityPlayer.pause();
                        break;

                    case BottomSheetBehavior.STATE_COLLAPSED:
                        mUnityPlayer.resume();
                        arViewModel.notify.setValue("Slide Up");
                        break;

                    case BottomSheetBehavior.STATE_HIDDEN:
                        mUnityPlayer.resume();
                        break;

                    case BottomSheetBehavior.STATE_EXPANDED:
                        arViewModel.notify.setValue("Slide Down");
                }
            }

            /**
             * Called when the bottom sheet is being dragged.
             *
             * @param bottomSheet The bottom sheet view.
             * @param slideOffset The new offset of this bottom sheet within [-1,1] range. Offset
             *                    increases as this bottom sheet is moving upward. From 0 to 1 the sheet
             *                    is between collapsed and expanded states and from -1 to 0 it is
             */
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                bottomSheet.bringToFront();
            }
        });

        mUnityPlayer.getView().setOnTouchListener(new View.OnTouchListener() {

            float x1 = 0, y1 = 0;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();

                switch (action){

                    case MotionEvent.ACTION_DOWN:

                        x1 = motionEvent.getX();
                        y1 = motionEvent.getY();

                        break;

                    case MotionEvent.ACTION_UP:
                        float x2 = motionEvent.getX();
                        float y2 = motionEvent.getY();

                        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED){
                            if (y1 > y2 && (y1 - y2) > 100) {   // SLIDE UP EVENT
                                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                            }else if (y2 > y1 && (y2 - y1) > 100){  // SLIDE DOWN EVENT
                                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                            }
                        }


                        break;


                }

                return false;
            }
        });


    }

    @Override
    public void OnRecordingStarted() {


    }

    @Override
    public void OnRecordingStopped() {

    }

    @Override
    public void OnShareVideo(String s) {

    }

    @Override
    public void OnARTargetFound(String s) {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        arViewModel.notify.setValue("Slide Up");
    }

    @Override
    public void OnARTargetLost(String s) {
        //targetInfoViewModel.targetName.setValue("Worked");
    }
}
