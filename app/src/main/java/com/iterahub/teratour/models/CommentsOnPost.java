package com.iterahub.teratour.models;

import android.arch.persistence.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;
import java.util.ArrayList;

@Entity
@Parcel
public class CommentsOnPost {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private ArrayList<PostModels.PostData.LatestComment> commentData;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public ArrayList<PostModels.PostData.LatestComment> getCommentData() {
        return commentData;
    }

    public void setCommentData(ArrayList<PostModels.PostData.LatestComment> data) {
        this.commentData = data;
    }

}
