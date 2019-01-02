package com.iterahub.teratour.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by ACER on 2/4/2018.
 */

@Entity
@Parcel
public class DummyUser {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    @Ignore
    private ArrayList<DummyUserData> dummyUserData;
    @PrimaryKey(autoGenerate = true)
    private int primaryKey;

    public DummyUser(){

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<DummyUserData> getDummyUserData() {
        return dummyUserData;
    }

    public void setDummyUserData(ArrayList<DummyUserData> dummyUserData) {
        this.dummyUserData = dummyUserData;
    }

    public int getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
    }


    @Parcel
    @Entity
    public static class DummyUserData {

        @PrimaryKey(autoGenerate = true)
        private int primaryKey;
        @SerializedName("id")
        @Expose
        private String UserId;
        @SerializedName("username")
        @Expose
        private String UserName;
        @SerializedName("image_url")
        @Expose
        private String ImageUrl;
        @SerializedName("fullname")
        @Expose
        private String FullName;
        @SerializedName("background_url")
        @Expose
        private String BackgroundImageUrl;
//        @SerializedName("email")
//        @Expose
        private String email = "";
//        @SerializedName("first_name")
//        @Expose
        private String firstName;
//        @SerializedName("last_name")
//        @Expose
        private String lastName;
//        @SerializedName("birthday")
//        @Expose
        private String birthday;
//        @SerializedName("gender")
//        @Expose
        private String gender;
//        @SerializedName("location")
//        @Expose
        private String location;

        private String Platform;


        public DummyUserData(){

        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }


        public String getUserId() {
            return UserId;
        }

        public void setUserId(String userId) {
            UserId = userId;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String userName) {
            UserName = userName;
        }

        public String getImageUrl() {
            return ImageUrl;
        }

        public void setImageUrl(String imageUrl) {
            ImageUrl = imageUrl;
        }

        public String getPlatform() {
            return Platform;
        }

        public void setPlatform(String platform) {
            Platform = platform;
        }

        public int getPrimaryKey() {
            return primaryKey;
        }

        public void setPrimaryKey(int primaryKey) {
            this.primaryKey = primaryKey;
        }

        public String getFullName() {
            return FullName;
        }

        public void setFullName(String fullName) {
            FullName = fullName;
        }

        public String getBackgroundImageUrl() {
            return BackgroundImageUrl;
        }

        public void setBackgroundImageUrl(String backgroundImageUrl) {
            BackgroundImageUrl = backgroundImageUrl;
        }
    }


}
