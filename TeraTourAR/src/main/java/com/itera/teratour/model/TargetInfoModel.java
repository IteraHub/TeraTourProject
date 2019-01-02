package com.itera.teratour.model;
import android.graphics.drawable.Drawable;


/**
 * Created by root on 11/12/18.
 */

public class TargetInfoModel {

    private String targetName;

    private Drawable targetImage;

    private String targetInformation;


    public TargetInfoModel(String targetName, Drawable targetImage, String targetInformation) {
        this.targetName = targetName;
        this.targetImage = targetImage;
        this.targetInformation = targetInformation;
    }

    public String getTargetName() {
        if (targetName == null){
            targetName = "Title";
        }
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public Drawable getTargetImage() {
        return targetImage;
    }

    public void setTargetImage(Drawable targetImage) {
        this.targetImage = targetImage;
    }

    public String getTargetInformation() {
        return targetInformation;
    }

    public void setTargetInformation(String targetInformation) {
        this.targetInformation = targetInformation;
    }


}
