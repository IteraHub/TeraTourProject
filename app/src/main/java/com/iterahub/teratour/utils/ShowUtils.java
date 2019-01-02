package com.iterahub.teratour.utils;



import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.iterahub.teratour.R;

public class ShowUtils {

    public static void setImage(Context context, ImageView imageView, String url, int defaultImage){
        if(TextUtils.isEmpty(url))
            url = "http://res.cloudinary.com/arinzedroid/image/upload/v1523534359/Post_Images/post9.jpg";
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(defaultImage))
                .into(imageView);
    }

    public static void setImage(Context context, ImageView imageView, String url){
        if(TextUtils.isEmpty(url))
            url = "http://res.cloudinary.com/arinzedroid/image/upload/v1523534359/Post_Images/post9.jpg";
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.ic_rect))
                .into(imageView);
    }

    public static void setImage(Context context, ImageView imageView, String url, View view){
        if(TextUtils.isEmpty(url))
            url = "http://res.cloudinary.com/arinzedroid/image/upload/v1523534359/Post_Images/post9.jpg";
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.ic_rect))
                .into(imageView);
        view.setVisibility(View.GONE);
    }

    public static void loadFragment(FragmentActivity activity, Fragment fragment,int container,boolean addToBackStack){
        if(activity != null && activity.getSupportFragmentManager() != null){
            if(!addToBackStack){
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(container,fragment,fragment.getClass().getSimpleName())
                        .commit();
            }else{
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(container,fragment,fragment.getClass().getSimpleName())
                        .addToBackStack(null).commit();
            }

        }
    }

    public static void loadFragment(FragmentActivity activity, Fragment fragment, int container){
        if(activity != null && activity.getSupportFragmentManager() != null){
            activity.getSupportFragmentManager().beginTransaction()
                    .replace(container,fragment,fragment.getClass().getSimpleName())
                    .commit();
        }
    }

}
