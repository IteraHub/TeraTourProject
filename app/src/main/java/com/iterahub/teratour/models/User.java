package com.iterahub.teratour.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
@Entity
public class User {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    @Ignore
    private UserData data;

    @PrimaryKey(autoGenerate = true)
    private int primaryKey;

    /**
     * No args constructor for use in serialization
     *
     */
    public User() {
    }

    /**
     *
     * @param status
     * @param data
     */
    public User(Boolean status, UserData data) {
        super();
        this.status = status;
        this.data = data;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public UserData getData() {
        return data;
    }

    public void setData(UserData data) {
        this.data = data;
    }

    public int getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
    }

    @Entity
    @Parcel
    public static class UserData {

        @SerializedName("firstname")
        @Expose
        private String firstname;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("lastname")
        @Expose
        private String lastname;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("image_url")
        @Expose
        private String imageUrl;
        @SerializedName("dob")
        @Expose
        private String dob;
        @SerializedName("about")
        @Expose
        private String about;
        @SerializedName("coverphoto_url")
        @Expose
        private String coverphotoUrl;
        @SerializedName("location")
        @Expose
        private String location;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;
        @SerializedName("id")
        @Expose
        private Integer id;

        @PrimaryKey
        private int primaryKey;

        /**
         * No args constructor for use in serialization
         *
         */
        public UserData() {
        }

        /**
         *
         * @param id
         * @param updatedAt
         * @param username
         * @param location
         * @param imageUrl
         * @param email
         * @param createdAt
         * @param dob
         * @param about
         * @param lastname
         * @param firstname
         * @param coverphotoUrl
         */
        public UserData(String firstname, String email, String lastname, String username, String imageUrl, String dob, String about, String coverphotoUrl, String location, String updatedAt, String createdAt, Integer id) {
            super();
            this.firstname = firstname;
            this.email = email;
            this.lastname = lastname;
            this.username = username;
            this.imageUrl = imageUrl;
            this.dob = dob;
            this.about = about;
            this.coverphotoUrl = coverphotoUrl;
            this.location = location;
            this.updatedAt = updatedAt;
            this.createdAt = createdAt;
            this.id = id;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getAbout() {
            return about;
        }

        public void setAbout(String about) {
            this.about = about;
        }

        public String getCoverphotoUrl() {
            return coverphotoUrl;
        }

        public void setCoverphotoUrl(String coverphotoUrl) {
            this.coverphotoUrl = coverphotoUrl;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public int getPrimaryKey() {
            return primaryKey;
        }

        public void setPrimaryKey(int primaryKey) {
            this.primaryKey = primaryKey;
        }
    }

}