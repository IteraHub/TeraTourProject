package com.iterahub.teratour.ui.activities;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.iterahub.teratour.R;

import com.iterahub.teratour.adapters.PostsAdapter;
import com.iterahub.teratour.interfaces.PostButtonsClickedInterface;
import com.iterahub.teratour.models.PostModel;
import com.iterahub.teratour.models.UserModel;
import com.iterahub.teratour.ui.fragments.CommentsFragment;
import com.iterahub.teratour.ui.fragments.PictureViewFragment;
import com.iterahub.teratour.utils.Constants;
import com.iterahub.teratour.utils.PrefUtils;
import com.iterahub.teratour.utils.ShowUtils;
import com.iterahub.teratour.viewmodel.AppViewModel;

import org.parceler.Parcels;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserProfileActivity extends AppCompatActivity implements PostButtonsClickedInterface{

    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.profile_recycler_view)
    RecyclerView profileRv;
    @BindView(R.id.cover_pic)
    ImageView cover_image;
    @BindView(R.id.profile_pic)
    ImageView profileImage;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.progress_bar_1)
    ProgressBar progressBar1;
    @BindView(R.id.no_data_tv)
    TextView noDataTv;
    @BindView(R.id.username)
    TextView usernameTv;
    @BindView(R.id.name)
    TextView nameTv;

    PrefUtils prefUtils;
    UserModel userModel;
    AppViewModel appViewModel;
    PostsAdapter postsAdapter;
    List<PostModel> postModelList;


    private final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        prefUtils = new PrefUtils(this);
        appViewModel = ViewModelProviders.of(this).get(AppViewModel.class);

        getDataFromIntent(getIntent());

        profileRv.setNestedScrollingEnabled(false);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent));
    }

    private void getDataFromIntent(Intent intent){
        if(intent != null){
            userModel = Parcels.unwrap(intent.getParcelableExtra(Constants.USER_DATA));
            if(userModel != null){
                usernameTv.setText(userModel.getUsername());
                nameTv.setText(String.valueOf(userModel.getFirstname() + " " + userModel.getLastname()));
                ShowUtils.setImage(this,cover_image,userModel.getCover_photo_url(),progressBar1);
                ShowUtils.setImage(this,profileImage,userModel.getImage_url());
                collapsingToolbarLayout.setTitle(userModel.getFirstname() + " " + userModel.getLastname());
                loadPostsByUser(userModel.getId());
            }else{
                progressBar.setVisibility(View.VISIBLE);
                noDataTv.setVisibility(View.GONE);
                appViewModel.getUserById(prefUtils.getUserId()).observe(this, user -> {
                    if(user != null){
                        this.userModel = user;
                        usernameTv.setText(userModel.getUsername());
                        nameTv.setText(String.valueOf(userModel.getFirstname() + " " + userModel.getLastname()));
                        ShowUtils.setImage(this,cover_image,userModel.getCover_photo_url(),progressBar1);
                        ShowUtils.setImage(this,profileImage,userModel.getImage_url());
                        collapsingToolbarLayout.setTitle(userModel.getFirstname() + " " + userModel.getLastname());
                        loadPostsByUser(userModel.getId());
                        findViewById(R.id.inbox).setEnabled(false);
                    }
                });
            }
        }
    }

    private void loadPostsByUser(String userId){
        progressBar.setVisibility(View.VISIBLE);
        noDataTv.setVisibility(View.GONE);
        appViewModel.getPostsByUserId(userId,true,false).observe(this,postList -> {
            progressBar.setVisibility(View.GONE);
            if(postList != null && !postList.isEmpty()){
                this.postModelList = postList;
                postsAdapter = new PostsAdapter(this,postList,this);
                profileRv.setAdapter(postsAdapter);

                //loop through all posts and get their respective data from the server
                for(int i = 0; i < postList.size(); i++){
                    postList.get(i).setPostOwner(userModel);
                    appViewModel.getMedia(postList.get(i).getId()).observe(this,mediaModel -> {
                        if(mediaModel != null){
                            postsAdapter.addMedia(mediaModel);
                        }
                    });
                    appViewModel.getLatestComment(postList.get(i).getId()).observe(this, commentModel -> {
                        if(commentModel != null){
                            postsAdapter.addComments(commentModel);
                            appViewModel.getUserById(commentModel.getUser_id()).observe(this, userModel -> {
                                if(userModel != null){
                                    postsAdapter.addCommentOwner(userModel);
                                }
                            });
                        }
                    });
                    appViewModel.getUserById(postList.get(i).getUser_Id()).observe(this, userModel -> {
                        if(userModel != null){
                            postsAdapter.addPostOwner(userModel);
                        }
                    });
                }
            }else{
                noDataTv.setVisibility(View.VISIBLE);
            }
        });
    }

    @OnClick(R.id.inbox)
    public void onInboxClick(View v){
        Intent intent = new Intent(this,DirectMessageActivity.class);
        intent.putExtra(Constants.USER_DATA,Parcels.wrap(userModel));
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown.
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCommentsButtonClicked(int position) {
        if(position < postModelList.size()){
            ShowUtils.loadFragment(this,CommentsFragment.newInstance(postModelList.get(position).getId()),
                    R.id.frag_container1,true);
        }
    }

    @Override
    public void onLikeButtonClicked(int position,View view) {
        Log.e(TAG,"Nothing yet for like_btn");
    }

    @Override
    public void onProfileImageClicked(int position) {
        Log.e(TAG,"Nothing yet for profile image");

    }

    @Override
    public void onPostClicked(int position) {
        if(postModelList != null && position < postModelList.size()){
            PostModel data = postModelList.get(position);
            if(data != null && data.getMediaModel() != null && !TextUtils.isEmpty(data.getMediaModel().getMedia_url())){

                ShowUtils.loadFragment(this,PictureViewFragment.newInstance(data.getMediaModel().getMedia_url()),
                        R.id.frag_container1,true);
            }
        }

    }

    @Override
    public void onShareClicked(int position) {
        Log.e(TAG,"Nothing yet for share_btn");
    }

    @Override
    public void onProfileNameClicked(int position) {

    }

    @Override
    public void onProfileUsernameClicked(int position) {

    }

}