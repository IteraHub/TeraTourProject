package com.iterahub.teratour.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iterahub.teratour.R;
import com.iterahub.teratour.interfaces.PostButtonsClickedInterface;
import com.iterahub.teratour.models.CommentModel;
import com.iterahub.teratour.models.MediaModel;
import com.iterahub.teratour.models.PostModel;
import com.iterahub.teratour.models.UserModel;
import com.iterahub.teratour.utils.ShowUtils;
import com.iterahub.teratour.viewHolders.PostViewHolder;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostViewHolder> {

    private Context context;
    private List<PostModel> postModelList;
    private PostButtonsClickedInterface postButtonsClickedInterface;

    public PostsAdapter(Context context, List<PostModel> postModelsList, PostButtonsClickedInterface
            postButtonsClickedInterface){
        this.context = context;
        this.postModelList = postModelsList;
        this.postButtonsClickedInterface = postButtonsClickedInterface;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_profile,parent,false);
        return new PostViewHolder(v,postButtonsClickedInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        PostModel data = postModelList.get(position);
        if(data != null){
            if(!TextUtils.isEmpty(data.getPost_text())){
                holder.post_text_tv.setText(data.getPost_text());
            }
            if(!TextUtils.isEmpty(String.valueOf(data.getTotal_likes()))){
                holder.like_count_tv.setText(String.valueOf(data.getTotal_likes()));
            }
            if(data.getPostOwner() != null){
                holder.container.stopShimmerAnimation();
                holder.profile_name_tv.setText(String.valueOf(data.getPostOwner().getFirstname() +
                        " " + data.getPostOwner().getLastname()));
                holder.profile_username_tv.setText(data.getPostOwner().getUsername());
                ShowUtils.setImage(context,holder.profile_image,data.getPostOwner().getImage_url());
            }else{
                holder.container.startShimmerAnimation();
            }
            if(data.getMediaModel() != null){
                ShowUtils.setImage(context,holder.media_image,data.getMediaModel().getMedia_url());
                holder.progressBar1.setVisibility(View.GONE);
            }else{
                holder.progressBar1.setVisibility(View.VISIBLE);
            }
            if(data.getLatestComment() != null){
                holder.progressBar2.setVisibility(View.GONE);
                holder.comment_text_tv.setText(data.getLatestComment().getComment_text());
                if(data.getLatestComment().getComment_owner() != null){
                    holder.comment_owner_username_tv.setText(data.getLatestComment().getComment_owner().getUsername());
                    ShowUtils.setImage(context,holder.comment_owner_image,data.getLatestComment()
                            .getComment_owner().getImage_url());

                }
            }else{
                holder.progressBar2.setVisibility(View.VISIBLE);
            }
        }
    }

    public void addMedia(MediaModel mediaModel){
       for(int i = 0; i < postModelList.size(); i++){
           if(mediaModel.getPost_id().equals(postModelList.get(i).getId())){
               postModelList.get(i).setMediaModel(mediaModel);
               notifyItemChanged(i);
               return;
           }
       }
    }

    public void addComments(CommentModel commentModel){
        for(int i = 0; i < postModelList.size(); i++){
            if(commentModel.getPost_id().equals(postModelList.get(i).getId())){
                postModelList.get(i).setLatestComment(commentModel);
                notifyItemChanged(i);
                return;
            }
        }
    }

    public void addCommentOwner(UserModel userModel){
        for(int i = 0; i < postModelList.size(); i++){
            if(postModelList.get(i).getLatestComment() != null){
                if(postModelList.get(i).getLatestComment().getUser_id().equals(userModel.getId())){
                    postModelList.get(i).getLatestComment().setComment_owner(userModel);
                    notifyItemChanged(i);
                    return;
                }
            }
        }
    }

    public void addPostOwner(UserModel userModel){
        for(int i = 0; i < postModelList.size(); i++){
            if(postModelList.get(i).getUser_Id().equals(userModel.getId())){
                postModelList.get(i).setPostOwner(userModel);
                notifyItemChanged(i);
                return;
            }
        }
    }

    @Override
    public int getItemCount() {
        return postModelList.size();
    }
}
