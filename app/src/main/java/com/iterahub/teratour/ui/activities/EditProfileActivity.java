package com.iterahub.teratour.ui.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.iterahub.teratour.R;
import com.iterahub.teratour.models.UserModel;
import com.iterahub.teratour.utils.PrefUtils;
import com.iterahub.teratour.utils.ShowUtils;
import com.iterahub.teratour.viewmodel.AppViewModel;

import java.util.Map;

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
    @BindView(R.id.firstname_et)
    EditText firstNameET;
    @BindView(R.id.lastname_et)
    EditText lastNameET;
    @BindView(R.id.password_et)
    EditText passwordET;
    @BindView(R.id.confirm_password_et)
    EditText confirmPasswordET;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.shadow_layout)
    View shadowLayout;



    boolean isBgUploading, isDpUploading;
    private static final int DP_IMAGE = 1;
    private static final int BG_IMAGE = 2;
    private String bgURL,dpURL;
    private PrefUtils prefUtils;
    private AppViewModel appViewModel;
    private UserModel userModel;
    private Uri dpImageUri;
    private Uri bgImageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);

        prefUtils = new PrefUtils(this);
        appViewModel = ViewModelProviders.of(this).get(AppViewModel.class);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_back_icon);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(view -> finish());
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("Edit Profile");

        initViews();

       uploadDpLayout.setOnClickListener(view -> selectDp());

       uploadBgLayout.setOnClickListener(view -> selectBg());

       okButton.setOnClickListener(view -> validateData());
    }

    private void initViews(){
        shadowLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        appViewModel.getUserById(prefUtils.getUserId()).observe(this, userModel -> {
            shadowLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            if(userModel != null){
                this.userModel = userModel;
                firstNameET.setText(userModel.getFirstname());
                lastNameET.setText(userModel.getLastname());
                dpURL = userModel.getImage_url();
                bgURL = userModel.getCover_photo_url();
                ShowUtils.setImage(this,dpImage,userModel.getImage_url());
                ShowUtils.setImage(this,bgImage,userModel.getCover_photo_url());
            }
        });
    }



    private void selectDp(){
        Intent pickIntent = new Intent();
        pickIntent.setType("image/*");
        pickIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(pickIntent,"Select Display Image"),DP_IMAGE);
//        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        galleryIntent.setType("image/*");
//        startActivityForResult(galleryIntent,DP_IMAGE);
    }

    private void selectBg(){
        Intent pickIntent = new Intent();
        pickIntent.setType("image/*");
        pickIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(pickIntent,"Select Background Image"),BG_IMAGE);
    }

    private void validateData(){
        String firstname = firstNameET.getText().toString(),
                lastname = lastNameET.getText().toString(),
                password = passwordET.getText().toString(),
                confirmPassword = confirmPasswordET.getText().toString();
        if(TextUtils.isEmpty(firstname)){
           firstNameET.setError("FirstName cannot be empty");
           return;
        }else if(firstname.length() < 2){
            firstNameET.setError("FirstName cannot be less than 2 characters");
            return;
        }
        if(TextUtils.isEmpty(lastname)){
            lastNameET.setError("LastName cannot be empty");
            return;
        }else if(lastname.length() < 2){
            lastNameET.setError("LastName cannot be less than 2 characters");
            return;
        }
        if(TextUtils.isEmpty(password)){
            if(userModel != null){
                password = userModel.getPassword();
                confirmPassword = password;
            }
            //TODO check else condition
        }else if(password.length() < 7 ){
            passwordET.setError("Password cannot be less than 7 characters");
            return;
        }
        if(!confirmPassword.equals(password)){
            confirmPasswordET.setText("");
            passwordET.setText("");
            passwordET.setError("Passwords do not match");
            //passwordET.requestFocus();
            return;
        }
        shadowLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        if(userModel != null){
            userModel.setFirstname(firstname);
            userModel.setLastname(lastname);
            userModel.setPassword(password);
            userModel.setImage_url(dpURL);
            userModel.setCover_photo_url(bgURL);
            processData(userModel);
        }else{
            Toast.makeText(this, "user's data is invalid", Toast.LENGTH_SHORT).show();
        }

    }

    private void processData(UserModel userModel){
        Log.e(this.toString(),userModel.toString());
        appViewModel.updateUser(userModel).observe(this, isSuccessful -> {
            if(isSuccessful != null && isSuccessful){
                Toast.makeText(this, "Users data updated successfully", Toast.LENGTH_SHORT).show();
                if(dpImageUri != null){
                    Intent intent = new Intent();
                    intent.putExtra("dpImageUri", dpImageUri.toString());
                    intent.putExtra("fullname",userModel.getFirstname() + " " +
                            userModel.getLastname());
                    setResult(RESULT_OK,intent);
                }
                finish();
            }else{
                Toast.makeText(this, "Data could not be updated", Toast.LENGTH_SHORT).show();
                shadowLayout.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
            }
        });
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
                    if(url != null){
                        dpURL = url.toString();
                        Log.e(EditProfileActivity.class.getSimpleName(),"url " + url.toString());
                    }
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
                    if(url != null){
                        bgURL = url.toString();
                        Log.e(EditProfileActivity.class.getSimpleName(),"url " + url.toString());
                    }
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
                           dpImageUri = data.getData();
                           handleDpUpload(dpImageUri);
                    }
                    break;
                }
                case BG_IMAGE:{
                    if(data != null){
                        if(data.getData() != null)
                            bgImageUri = data.getData();
                            handleBgUpload(data.getData());
                    }
                    break;
                }
                default: break;
            }
        }
    }
}
