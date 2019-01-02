package com.iterahub.teratour.models;

import com.iterahub.teratour.utils.RandomString;

import java.util.Date;

public class CommentModel {

    String id = new RandomString().nextString();
    String user_id;
    String post_id;
    String comment_text;
    UserModel comment_owner;
    Date created_at;
    Date updated_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getComment_text() {
        return comment_text;
    }

    public void setComment_text(String comment_text) {
        this.comment_text = comment_text;
    }

    public UserModel getComment_owner() {
        return comment_owner;
    }

    public void setComment_owner(UserModel comment_owner) {
        this.comment_owner = comment_owner;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}
