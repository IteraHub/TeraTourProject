package com.iterahub.teratour.models;

import com.google.gson.annotations.SerializedName;
import com.iterahub.teratour.utils.RandomString;

import org.parceler.Parcel;

import java.util.Date;

@Parcel
public class UserModel {

    @SerializedName("id")
    String id = new RandomString().nextString();
    @SerializedName("fn")
    String firstname;
    @SerializedName("ln")
    String lastname;
    @SerializedName("em")
    String email;
    @SerializedName("un")
    String username;
    @SerializedName("pw")
    String password;
    @SerializedName("iu")
    String image_url;
    @SerializedName("dob")
    String dob;
    @SerializedName("abt")
    String about;
    @SerializedName("cpu")
    String cover_photo_url;
    @SerializedName("cl")
    String current_location;
    @SerializedName("ua")
    Date updated_at;
    @SerializedName("ca")
    Date created_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
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

    public String getCover_photo_url() {
        return cover_photo_url;
    }

    public void setCover_photo_url(String cover_photo_url) {
        this.cover_photo_url = cover_photo_url;
    }

    public String getCurrent_location() {
        return current_location;
    }

    public void setCurrent_location(String current_location) {
        this.current_location = current_location;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
