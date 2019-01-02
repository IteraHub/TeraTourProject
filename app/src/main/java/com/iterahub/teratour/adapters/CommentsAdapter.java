package com.iterahub.teratour.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iterahub.teratour.R;
import com.iterahub.teratour.interfaces.UserButtonsClickedInterface;
import com.iterahub.teratour.models.CommentModel;
import com.iterahub.teratour.models.UserModel;
import com.iterahub.teratour.utils.ShowUtils;
import com.iterahub.teratour.viewHolders.CommentsViewHolder;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsViewHolder> {

    private Context context;
    private List<CommentModel> commentModels;
    private UserButtonsClickedInterface userButtonsClickedInterface;

    public CommentsAdapter(Context context,List<CommentModel> commentModels,
                           UserButtonsClickedInterface userButtonsClickedInterface){
        this.context = context;
        this.commentModels = commentModels;
        this.userButtonsClickedInterface = userButtonsClickedInterface;
    }

    @NonNull
    @Override
    public CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_comment_layout,parent,false);
       return new CommentsViewHolder(v,userButtonsClickedInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsViewHolder holder, int position) {
        CommentModel commentModel = commentModels.get(position);
        if(commentModel.getComment_owner() != null){
            holder.comment_owner_username_tv.setText(commentModel.getComment_owner().getUsername());
            holder.comment_owner_name_tv.setText(String.valueOf(commentModel.getComment_owner()
                    .getFirstname() + " " + commentModel.getComment_owner().getLastname()));
            ShowUtils.setImage(context,holder.comment_owner_image,commentModel.getComment_owner().getImage_url());
        }
        holder.comment_text_tv.setText(commentModel.getComment_text());
    }

    public void addCommentOwner(UserModel userModel){
        for(int i = 0; i < commentModels.size(); i++ ){
            if(commentModels.get(i).getUser_id().equals(userModel.getId())){
                commentModels.get(i).setComment_owner(userModel);
                notifyItemChanged(i);
                //return;
            }
        }
    }

    @Override
    public int getItemCount() {
        return commentModels.size();
    }
}
