package com.iterahub.teratour.models;

import com.iterahub.teratour.utils.DateTimeUtils;
import com.iterahub.teratour.utils.RandomString;

import org.parceler.Parcel;

import java.util.Date;

@Parcel
public class MessagesModel {
    String id = new RandomString().nextString();
    String userId;
    String chatId;
    String text;
    String createdAt = DateTimeUtils.formatDateTime(new Date());

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "MessagesModel{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", chatId='" + chatId + '\'' +
                ", text='" + text + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
