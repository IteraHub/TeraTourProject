package com.iterahub.teratour.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.iterahub.teratour.R;
import com.iterahub.teratour.interfaces.PostButtonsClickedInterface;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by ACER on 10/21/2017.
 */

public class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    @BindView(R.id.post_profile_pic)
    public ImageView profile_image;
    @BindView(R.id.post)
    public ImageView media_image;
    @BindView(R.id.post_text)
    public TextView post_text_tv;
    @BindView(R.id.comment)
    public View comments_view;
    @BindView(R.id.like)
    public ImageButton like_btn;
    @BindView(R.id.share)
    public ImageButton share_btn;
    @BindView(R.id.profile_name)
    public TextView profile_name_tv;
    @BindView(R.id.like_count)
    public TextView like_count_tv;
    @BindView(R.id.profile_username)
    public TextView profile_username_tv;
    @BindView(R.id.owner_name)
    public TextView comment_owner_name_tv;
    @BindView(R.id.owner_pic)
    public ImageView comment_owner_image;
    @BindView(R.id.owner_username)
    public TextView comment_owner_username_tv;
    @BindView(R.id.owner_text)
    public TextView comment_text_tv;
    @BindView(R.id.shimmer_view_container_1)
    public ShimmerFrameLayout container;
    @BindView(R.id.progress_bar_1)
    public ProgressBar progressBar1;
    @BindView(R.id.progress_bar_2)
    public ProgressBar progressBar2;

    private PostButtonsClickedInterface postButtonsClickedInterface;

    public PostViewHolder(View itemView,PostButtonsClickedInterface postButtonsClickedInterface) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        like_btn.setOnClickListener(this);
        share_btn.setOnClickListener(this);
        comments_view.setOnClickListener(this);
        comment_text_tv.setOnClickListener(this);
        media_image.setOnClickListener(this);
        profile_image.setOnClickListener(this);
        profile_name_tv.setOnClickListener(this);
        profile_username_tv.setOnClickListener(this);
        this.postButtonsClickedInterface = postButtonsClickedInterface;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.comment:{
                postButtonsClickedInterface.onCommentsButtonClicked(this.getLayoutPosition());
                break;
            }
            case R.id.like:{
                postButtonsClickedInterface.onLikeButtonClicked(this.getLayoutPosition(),view);
                Log.e(this.getClass().getSimpleName(),"view drawable "+ ((ImageButton)view).getDrawable());
                break;
            }
            case R.id.post_profile_pic:{
                postButtonsClickedInterface.onProfileImageClicked(this.getLayoutPosition());
                break;
            }
            case R.id.post:{
                postButtonsClickedInterface.onPostClicked(this.getLayoutPosition());
                break;
            }
            case R.id.share:{
                postButtonsClickedInterface.onShareClicked(this.getLayoutPosition());
                break;
            }

            case R.id.profile_username:{
                postButtonsClickedInterface.onProfileUsernameClicked(this.getLayoutPosition());
                break;
            }
            case R.id.profile_name:{
                postButtonsClickedInterface.onProfileNameClicked(this.getLayoutPosition());
                break;
            }
            case R.id.owner_text:{
                postButtonsClickedInterface.onCommentsButtonClicked(this.getLayoutPosition());
                break;
            }

            default:break;
        }
    }
}
