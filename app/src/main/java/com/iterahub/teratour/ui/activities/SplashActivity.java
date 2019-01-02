package com.iterahub.teratour.ui.activities;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.itera.teratour.View.UnityPlayerActivity;
import com.iterahub.teratour.R;
import com.iterahub.teratour.models.CommentModel;
import com.iterahub.teratour.models.DummyUser;
import com.iterahub.teratour.models.MediaModel;
import com.iterahub.teratour.models.PostModel;
import com.iterahub.teratour.models.UserModel;
import com.iterahub.teratour.utils.PrefUtils;
import com.iterahub.teratour.viewmodel.AppViewModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class SplashActivity extends AppCompatActivity {

    private final int m_permissionCode = 100; // unique permission request code
    PrefUtils prefUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //if not Android 6+ run the app
        prefUtils = new PrefUtils(this);
        if (Build.VERSION.SDK_INT < 23) {
            startApp();
        }
        else {
            checkPermissions(m_permissionCode);
        }

    }

    private void pushToServer(){
        AppViewModel appViewModel = ViewModelProviders.of(this).get(AppViewModel.class);
        String url = "http://res.cloudinary.com/arinzedroid/image/upload/v1523534359/Post_Images/post9.jpg";
        for(int i = 1; i <= 10; i++){
            PostModel postModel = new PostModel();
            postModel.setTitle(String.valueOf("title " + i));
            if(i % 2 == 0){
                postModel.setPost_text(getResources().getString(R.string.text_1));
            }else{
                postModel.setPost_text(getResources().getString(R.string.text_2));
            }
            UserModel userModel = new UserModel();
            userModel.setFirstname("First" + i);
            userModel.setLastname("Last" + i);
            userModel.setImage_url(url);
            userModel.setUsername("username" + i);

            postModel.setTotal_likes((i * 4)/2);
            postModel.setLiked_by_user(true);
            postModel.setCreated_at(new Date());
            postModel.setUpdated_at(new Date());
            postModel.setUser_Id(userModel.getId());

            MediaModel mediaModel = new MediaModel();
            mediaModel.setCreated_at(new Date());
            mediaModel.setPost_id(postModel.getId());
            mediaModel.setMedia_url(url);
            mediaModel.setUpdated_at(new Date());

            CommentModel commentModel = new CommentModel();
            commentModel.setCreated_at(new Date());
            commentModel.setPost_id(postModel.getId());
            commentModel.setUser_id(userModel.getId());
            commentModel.setUpdated_at(new Date());
            if(i % 2 == 0){
               commentModel.setComment_text(getResources().getString(R.string.text_1));
            }else{
                commentModel.setComment_text(getResources().getString(R.string.text_2));
            }

            postModel.setPostOwner(userModel);
            postModel.setLatestComment(commentModel);
            postModel.setMediaModel(mediaModel);

            appViewModel.addAllPosts(postModel).observe(this,success -> {
                if(success != null && success){
                    Log.e(this.getClass().getSimpleName(),"Post added successfully");
                }else{
                    Log.e(this.getClass().getSimpleName(),"They was a problem adding post");
                }
            });

        }
    }

    private String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("default_users.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void storeUsers(){
        Gson gson = new Gson();
        String JsonData = loadJSONFromAsset();
        Log.e(SplashActivity.class.getSimpleName(),JsonData);
        DummyUser dummyUser;
        dummyUser = gson.fromJson(JsonData,DummyUser.class);

        ArrayList<String> userId = new ArrayList<>();
        for (DummyUser.DummyUserData data : dummyUser.getDummyUserData()) {
            userId.add(data.getUserId());
        }
        //prefUtils.setUser(randomizeUsers(userId));
    }

    private String randomizeUsers(ArrayList<String> usersId){
        Random random = new Random();
        int i = random.nextInt(usersId.size());
        return usersId.get(i);
    }

    private void startApp(){

//        if(prefUtils.isFirstTime()){
//            startActivity(new Intent(this,SignUpActivity.class));
//        }else if(prefUtils.isLoggedIn()){
//            startActivity(new Intent(this,MainActivity.class));
//        }else{
//            startActivity(new Intent(this,LogInActivity.class));
//        }
//       finish();
    }

    private void checkPermissions(int code) {
        // require permission to access camera, read and write external storage
        String[] permissions_required = new String[] {
                Manifest.permission.CAMERA,
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE
        };

        // check if permissions have been granted
        List<String> permissions_not_granted_list = new ArrayList<String>();
        for (String permission : permissions_required) {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                permissions_not_granted_list.add(permission);
            }
        }
        // permissions not granted
        if (permissions_not_granted_list.size() > 0) {
            String[] permissions = new String[permissions_not_granted_list.size()];
            permissions_not_granted_list.toArray(permissions);
            ActivityCompat.requestPermissions(this, permissions, code);
        }
        else { // if all permissions have been granted
            startApp();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // this is the answer to our permission request (our permissioncode)
       if(requestCode == this.m_permissionCode){
           // check if all have been granted
           boolean ok = true;

           for (int grantResult : grantResults) {
               ok = ok && (grantResult == PackageManager.PERMISSION_GRANTED);
           }
           if (ok) {
               // if all have been granted, continue
               startApp();
           }
           else {
               // exit if not all required permissions have been granted
               Toast.makeText(this, "Error: required permissions not granted!", Toast.LENGTH_SHORT).show();
               finish();
           }
        }

    }
}
