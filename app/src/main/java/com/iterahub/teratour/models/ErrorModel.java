package com.iterahub.teratour.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ErrorModel {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("errors")
    @Expose
    private Errors errors;

    /**
     * No args constructor for use in serialization
     *
     */
    public ErrorModel() {
    }

    /**
     *
     * @param message
     * @param errors
     * @param status
     */
    public ErrorModel(Boolean status, String message, Errors errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

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

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    public class Errors {

        @SerializedName("email")
        @Expose
        private ArrayList<String> email = null;
        @SerializedName("username")
        @Expose
        private ArrayList<String> username = null;

        /**
         * No args constructor for use in serialization
         *
         */
        public Errors() {
        }

        /**
         *
         * @param username
         * @param email
         */
        public Errors(ArrayList<String> email, ArrayList<String> username) {
            super();
            this.email = email;
            this.username = username;
        }

        public ArrayList<String> getEmail() {
            return email;
        }

        public void setEmail(ArrayList<String> email) {
            this.email = email;
        }

        public ArrayList<String> getUsername() {
            return username;
        }

        public void setUsername(ArrayList<String> username) {
            this.username = username;
        }

    }

}