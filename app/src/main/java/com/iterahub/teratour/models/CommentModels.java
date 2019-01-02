package com.iterahub.teratour.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Arinze on 11/7/2017.
 */

public class CommentModels {


    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private PostModels.PostData.LatestComment data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PostModels.PostData.LatestComment getData() {
        return data;
    }

    public void setData(PostModels.PostData.LatestComment data) {
        this.data = data;
    }


}
