package com.iterahub.teratour.models;

import com.iterahub.teratour.utils.RandomString;

import org.parceler.Parcel;

import java.util.Date;

@Parcel
public class ChatsModel {

    String id = new RandomString().nextString();
    String myUserId;
    String otherUserId;
    String lastMessage;
    Date createdAt;
    UserModel chatOwner;

    public UserModel getChatOwner() {
        return chatOwner;
    }

    public void setChatOwner(UserModel chatOwner) {
        this.chatOwner = chatOwner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMyUserId() {
        return myUserId;
    }

    public void setMyUserId(String myUserId) {
        this.myUserId = myUserId;
    }

    public String getOtherUserId() {
        return otherUserId;
    }

    public void setOtherUserId(String otherUserId) {
        this.otherUserId = otherUserId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
