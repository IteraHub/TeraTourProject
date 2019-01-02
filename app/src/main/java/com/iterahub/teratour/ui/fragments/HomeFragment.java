package com.iterahub.teratour.ui.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.iterahub.teratour.R;
import com.iterahub.teratour.TeraTour;
import com.iterahub.teratour.adapters.PostsAdapter;
import com.iterahub.teratour.database.DatabaseInitializer;
import com.iterahub.teratour.interfaces.PostButtonsClickedInterface;
import com.iterahub.teratour.models.PostModel;
import com.iterahub.teratour.models.PostModels;
import com.iterahub.teratour.ui.activities.UserProfileActivity;
import com.iterahub.teratour.utils.Constants;
import com.iterahub.teratour.utils.PrefUtils;
import com.iterahub.teratour.utils.ShowUtils;
import com.iterahub.teratour.viewmodel.AppViewModel;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeFragment extends Fragment implements PostButtonsClickedInterface{


    @BindView(R.id.profile_recycler_view)
    RecyclerView profile_recycler_view;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.no_data_tv)
    TextView noDataTV;

    List<PostModel> postModelList;
    PostsAdapter adapters;
    AppViewModel appViewModel;
    PrefUtils prefUtils;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefUtils = new PrefUtils(getActivity());
        appViewModel = ViewModelProviders.of(getActivity()).get(AppViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,view);

        profile_recycler_view.setNestedScrollingEnabled(false);
        ((SimpleItemAnimator) profile_recycler_view.getItemAnimator()).setSupportsChangeAnimations(false);

        loadAllPosts(false);

        return view;
    }

    private void loadAllPosts(boolean refresh){
        appViewModel.getAllPosts(refresh).observe(this,postList -> {
            progressBar.setVisibility(View.GONE);
            if(postList != null && !postList.isEmpty()){
                postModelList = postList;
                adapters = new PostsAdapter(getActivity(),postList,this);
                profile_recycler_view.setAdapter(adapters);
                for(int i = 0; i < postList.size(); i++){
                    appViewModel.getMedia(postList.get(i).getId()).observe(this,mediaModel -> {
                        if(mediaModel != null){
                            adapters.addMedia(mediaModel);
                        }
                    });
                    appViewModel.getLatestComment(postList.get(i).getId()).observe(this, commentModel -> {
                        if(commentModel != null){
                            adapters.addComments(commentModel);
                            appViewModel.getUserById(commentModel.getUser_id()).observe(this, userModel -> {
                                if(userModel != null){
                                    adapters.addCommentOwner(userModel);
                                }
                            });
                        }
                    });
                    appViewModel.getUserById(postList.get(i).getUser_Id()).observe(this, userModel -> {
                        if(userModel != null){
                            adapters.addPostOwner(userModel);
                        }
                    });
                }
            }
        });
    }

    private boolean getImageDrawable(ImageButton button){
        Log.e(this.getClass().getSimpleName(),"Image drawable is "+ button.getDrawable().toString());
        Drawable drawable = ContextCompat.getDrawable(getActivity(),R.drawable.ic_outline_favorite_24px);
        Log.e(this.getClass().getSimpleName(),"drawable is "+ drawable.toString());
        Log.e(this.getClass().getSimpleName(),"raw drawable is "+ ContextCompat.getDrawable(getActivity(),R.drawable.ic_outline_favorite_24px).toString());
        if(button.getDrawable() == drawable)
            return true;
        else return false;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCommentsButtonClicked(int position) {
        Log.e(HomeFragment.class.getSimpleName(),"Comment Button Clicked " + position);
        if(position < postModelList.size()){
            ShowUtils.loadFragment(getActivity(),CommentsFragment.newInstance(postModelList.get(position).getId()),
                    R.id.frag_container,true);
        }
    }

    @Override
    public void onLikeButtonClicked(int position,View view) {
        Log.e(HomeFragment.class.getSimpleName(),"like_btn Button Clicked " + position);
        if(postModelList != null && position < postModelList.size()){
            //like button clicked
        }
    }

    private void onLikedPost(ImageView imageView, PostModels.PostData data, int position) {
        if(data.getLikedByUser()){
            imageView.setImageResource(R.drawable.ic_outline_favorite_24px);
            data.setLikedByUser(false);

            appViewModel.unLikePost(prefUtils.getAuth(),data.getId()).observe(this, unLiked -> {
                if(unLiked != null && unLiked.isStatus()){
                    Log.e(HomeFragment.class.getSimpleName(),"media_image unliked successfully");
                    DatabaseInitializer.updatePostLike(TeraTour.getAppDatabase(),data);
                }
            });
        }else{
            imageView.setImageResource(R.drawable.ic_outline_favourite_red);
            data.setLikedByUser(true);

            appViewModel.likePost(prefUtils.getAuth(),data.getId()).observe(this,liked ->{
                if(liked != null && liked.isStatus()){
                    Log.e(HomeFragment.class.getSimpleName(),"media_image liked successfully");
                    DatabaseInitializer.updatePostLike(TeraTour.getAppDatabase(),data);
                }
            });
        }
    }

    @Override
    public void onProfileImageClicked(int position) {
        if(postModelList != null && position < postModelList.size()){
            PostModel data = postModelList.get(position);
            if(data != null && data.getPostOwner() != null){
                Intent intent = new Intent(getActivity(), UserProfileActivity.class);
                intent.putExtra(Constants.USER_DATA, Parcels.wrap(data.getPostOwner()));
                startActivity(intent);
            }
        }
    }

    @Override
    public void onPostClicked(int position) {
        Log.e(HomeFragment.class.getSimpleName(),"media_image Button Clicked " + position);
        if(postModelList != null && position < postModelList.size()){
            PostModel data = postModelList.get(position);
            if(data != null && data.getMediaModel() != null && !TextUtils.isEmpty(data.getMediaModel().getMedia_url())){

                ShowUtils.loadFragment(getActivity(),PictureViewFragment.newInstance(data.getMediaModel().getMedia_url()),
                        R.id.frag_container,true);
            }
        }
    }

    @Override
    public void onShareClicked(int position) {

    }

    @Override
    public void onProfileNameClicked(int position) {
        if(postModelList != null && position < postModelList.size()){
            PostModel data = postModelList.get(position);
            if(data != null && data.getPostOwner() != null){
                Intent intent = new Intent(getActivity(), UserProfileActivity.class);
                intent.putExtra(Constants.USER_DATA, Parcels.wrap(data.getPostOwner()));
                startActivity(intent);
            }
        }
    }

    @Override
    public void onProfileUsernameClicked(int position) {
        if(postModelList != null && position < postModelList.size()){
            PostModel data = postModelList.get(position);
            if(data != null && data.getPostOwner() != null){
                Intent intent = new Intent(getActivity(), UserProfileActivity.class);
                intent.putExtra(Constants.USER_DATA, Parcels.wrap(data.getPostOwner()));
                startActivity(intent);
            }
        }
    }
}