package com.iterahub.teratour.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.iterahub.teratour.R;
import com.iterahub.teratour.interfaces.UserButtonsClickedInterface;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ACER on 4/13/2018.
 */

public class CommentsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    @BindView(R.id.owner_name)
    public TextView comment_owner_name_tv;
    @BindView(R.id.owner_pic)
    public ImageView comment_owner_image;
    @BindView(R.id.owner_username)
    public TextView comment_owner_username_tv;
    @BindView(R.id.owner_text)
    public TextView comment_text_tv;

    private UserButtonsClickedInterface userButtonsClickedInterface;

    public CommentsViewHolder(View itemView, UserButtonsClickedInterface userButtonsClickedInterface) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        comment_owner_image.setOnClickListener(this);
        comment_owner_name_tv.setOnClickListener(this);
        comment_owner_username_tv.setOnClickListener(this);
        this.userButtonsClickedInterface = userButtonsClickedInterface;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.owner_name:
            case R.id.owner_username:{
                userButtonsClickedInterface.onUsernameClick(this.getLayoutPosition());
                break;
            }
            case R.id.owner_pic:{
                userButtonsClickedInterface.onUserImageClick(this.getLayoutPosition());
                break;
            }
        }
    }
}
