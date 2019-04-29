package com.itera.teratour.model;

public class ARAssetModel {

    private String assetName;

    private String assetURL;

    private int assetThumbnail;

    public ARAssetModel(String assetName, String assetURL, int assetThumbnail) {
        this.assetName = assetName;
        this.assetURL = assetURL;
        this.assetThumbnail = assetThumbnail;
    }

    public String GetAssetName() {
        return assetName;
    }

    public String GetAssetURL(){
        return assetURL;
    }

    public int GetAssetThumbnail() {
        return assetThumbnail;
    }


    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public void setAssetURL(String assetURL) {
        this.assetURL = assetURL;
    }

    public void setAssetThumbnail(int assetThumbnail) {
        this.assetThumbnail = assetThumbnail;
    }
}
