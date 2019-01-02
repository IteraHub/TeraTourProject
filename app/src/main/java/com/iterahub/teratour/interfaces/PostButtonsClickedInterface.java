package com.iterahub.teratour.interfaces;

import android.view.View;

/**
 * Created by ACER on 4/12/2018.
 */

public interface PostButtonsClickedInterface {
    void onCommentsButtonClicked(int position);
    void onLikeButtonClicked(int position,View view);
    void onProfileImageClicked(int position);
    void onPostClicked(int position);
    void onShareClicked(int position);
    void onProfileNameClicked(int position);
    void onProfileUsernameClicked(int position);
}
