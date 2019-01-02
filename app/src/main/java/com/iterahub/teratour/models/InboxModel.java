package com.iterahub.teratour.models;

public class InboxModel {

    private String dpUrl;
    private String nameText;
    private String chatText;
    private String chatTime;

    public InboxModel(){

    }

    public InboxModel(String dpUrl,String nameText, String chatText, String chatTime){
        this.dpUrl = dpUrl;
        this.nameText = nameText;
        this.chatText = chatText;
        this.chatTime = chatTime;
    }

    public String getDpUrl() {
        return dpUrl;
    }

    public void setDpUrl(String dpUrl) {
        this.dpUrl = dpUrl;
    }

    public String getNameText() {
        return nameText;
    }

    public void setNameText(String nameText) {
        this.nameText = nameText;
    }

    public String getChatText() {
        return chatText;
    }

    public void setChatText(String chatText) {
        this.chatText = chatText;
    }

    public String getChatTime() {
        return chatTime;
    }

    public void setChatTime(String chatTime) {
        this.chatTime = chatTime;
    }

}
