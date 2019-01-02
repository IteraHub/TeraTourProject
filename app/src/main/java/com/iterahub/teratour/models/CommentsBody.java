package com.iterahub.teratour.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by arinzedroid on 8/7/2018.
 */

public class CommentsBody {
    @SerializedName("text")
    @Expose
    private String commentText;

    public CommentsBody(String commentText){
        this.commentText = commentText;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
}
