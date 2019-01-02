package com.iterahub.teratour.models;

import com.iterahub.teratour.utils.RandomString;

import java.util.Date;

public class PostModel {

    String id = new RandomString().nextString();
    String user_Id;
    String title;
    String post_text;
    int total_likes;
    boolean liked_by_user;
    UserModel postOwner;
    CommentModel latestComment;
    MediaModel mediaModel;
    Date created_at;
    Date updated_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(String user_Id) {
        this.user_Id = user_Id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPost_text() {
        return post_text;
    }

    public void setPost_text(String post_text) {
        this.post_text = post_text;
    }

    public int getTotal_likes() {
        return total_likes;
    }

    public void setTotal_likes(int total_likes) {
        this.total_likes = total_likes;
    }

    public boolean isLiked_by_user() {
        return liked_by_user;
    }

    public void setLiked_by_user(boolean liked_by_user) {
        this.liked_by_user = liked_by_user;
    }

    public UserModel getPostOwner() {
        return postOwner;
    }

    public void setPostOwner(UserModel postOwner) {
        this.postOwner = postOwner;
    }

    public CommentModel getLatestComment() {
        return latestComment;
    }

    public void setLatestComment(CommentModel latestComment) {
        this.latestComment = latestComment;
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

    public MediaModel getMediaModel() {
        return mediaModel;
    }

    public void setMediaModel(MediaModel mediaModel) {
        this.mediaModel = mediaModel;
    }
}
