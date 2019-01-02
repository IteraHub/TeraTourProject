package com.iterahub.teratour.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.iterahub.teratour.R;

import java.io.File;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditProfileActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.upload_dp_layout)
    View uploadDpLayout;
    @BindView(R.id.upload_bg_layout)
    View uploadBgLayout;
    @BindView(R.id.upload_progress_dp)
    ProgressBar dpUploadProgress;
    @BindView(R.id.upload_progress_bg)
    ProgressBar bgUploadProgress;
    @BindView(R.id.dp_image)
    ImageView dpImage;
    @BindView(R.id.bg_image)
    ImageView bgImage;
    @BindView(R.id.ok_button)
    Button okButton;

    boolean isBgUploading, isDpUploading;
    private static final int DP_IMAGE = 1;
    private static final int BG_IMAGE = 2;
    private String bgURL,dpURL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_back_icon);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(view -> finish());
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("Edit Profile");

       uploadDpLayout.setOnClickListener(view -> selectDp());

       uploadBgLayout.setOnClickListener(view -> selectBg());

       okButton.setOnClickListener(view -> validateData());
    }

    private void selectDp(){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,DP_IMAGE);
    }

    private void selectBg(){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, BG_IMAGE);
    }

    private void validateData(){

    }

    private void handleDpUpload(Uri fileUri){
        String requestId = MediaManager.get().upload(fileUri).callback(new UploadCallback() {
            @Override
            public void onStart(String requestId) {
                Log.e(EditProfileActivity.class.getSimpleName(),"Onstart DP entered");
                dpUploadProgress.setVisibility(View.VISIBLE);
                uploadDpLayout.setEnabled(false);
                isDpUploading = true;
            }

            @Override
            public void onProgress(String requestId, long bytes, long totalBytes) {
                //divide current byte by total byte then multiple result by 100 to get progress
                long progress = (bytes/totalBytes) * 100;
                dpUploadProgress.setProgress((int)progress);
                Log.e(EditProfileActivity.class.getSimpleName(),"OnProgress DP entered" + String.valueOf(progress));
                Log.e(EditProfileActivity.class.getSimpleName(),"bytes Dp " +
                        String.valueOf(bytes) + "total bytes DP " + String.valueOf(totalBytes));
            }

            @Override
            public void onSuccess(String requestId, Map resultData) {
                dpImage.setImageURI(fileUri);
                dpUploadProgress.setVisibility(View.GONE);
                uploadDpLayout.setEnabled(true);
                isDpUploading = false;
                if(resultData.containsKey("url")){
                    Object url = resultData.get("url");
                    dpURL = url.toString();
                    Log.e(EditProfileActivity.class.getSimpleName(),"url " + url.toString());
                }
                Log.e(EditProfileActivity.class.getSimpleName(),"OnSuccess DP entered");
            }

            @Override
            public void onError(String requestId, ErrorInfo error) {
                isDpUploading = false;
                dpUploadProgress.setVisibility(View.GONE);
                uploadDpLayout.setEnabled(true);
                Toast.makeText(EditProfileActivity.this, "Error: " + error.getDescription(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onReschedule(String requestId, ErrorInfo error) {
                isDpUploading = false;
                dpUploadProgress.setVisibility(View.GONE);
                uploadDpLayout.setEnabled(true);
                Toast.makeText(EditProfileActivity.this, "Error: " + error.getDescription(), Toast.LENGTH_SHORT).show();
            }
        }).dispatch();
    }

    private void handleBgUpload(Uri fileUri){
        String requestId = MediaManager.get().upload(fileUri).callback(new UploadCallback() {
            @Override
            public void onStart(String requestId) {
                bgUploadProgress.setVisibility(View.VISIBLE);
                uploadBgLayout.setEnabled(false);
                isBgUploading = true;
                Log.e(EditProfileActivity.class.getSimpleName(),"OnStart bg entered");
            }

            @Override
            public void onProgress(String requestId, long bytes, long totalBytes) {
                //divide current byte by total byte then multiple result by 100 to get progress
                long progress = (bytes/totalBytes) * 100;

                //parse progress to int and set to progress bar
                bgUploadProgress.setProgress((int)progress);
                Log.e(EditProfileActivity.class.getSimpleName(),"OnProgress BG entered" + String.valueOf(progress));
                Log.e(EditProfileActivity.class.getSimpleName(),"bytes Bg " +
                        String.valueOf(bytes) + "total bytes Bg " + String.valueOf(totalBytes));
            }

            @Override
            public void onSuccess(String requestId, Map resultData) {
               bgImage.setImageURI(fileUri);
                bgUploadProgress.setVisibility(View.GONE);
                uploadBgLayout.setEnabled(true);
                isBgUploading = false;
                if(resultData.containsKey("url")){
                    Object url = resultData.get("url");
                    bgURL = url.toString();
                    Log.e(EditProfileActivity.class.getSimpleName(),"url " + url.toString());
                }
                Log.e(EditProfileActivity.class.getSimpleName(),"OnSuccess BG entered");

            }

            @Override
            public void onError(String requestId, ErrorInfo error) {
                bgUploadProgress.setVisibility(View.GONE);
                uploadBgLayout.setEnabled(true);
                isBgUploading = false;
                Toast.makeText(EditProfileActivity.this, "Error: " + error.getDescription(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onReschedule(String requestId, ErrorInfo error) {
                bgUploadProgress.setVisibility(View.GONE);
                uploadBgLayout.setEnabled(true);
                isBgUploading = false;
                Toast.makeText(EditProfileActivity.this, "Error: " + error.getDescription(), Toast.LENGTH_SHORT).show();
            }
        }).dispatch();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case DP_IMAGE:{
                    if(data != null){
                       if(data.getData() != null)
                           handleDpUpload(data.getData());
                    }
                    break;
                }
                case BG_IMAGE:{
                    if(data != null){
                        if(data.getData() != null)
                            handleBgUpload(data.getData());
                    }
                    break;
                }
                default: break;
            }
        }
    }
}
