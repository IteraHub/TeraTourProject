package com.iterahub.teratour.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by arinzedroid on 8/7/2018.
 */

public class LikeModel {
    @SerializedName("status")
    @Expose
    private boolean status;

    @SerializedName("msg")
    @Expose
    private String msg;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }



}
