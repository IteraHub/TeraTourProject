package com.itera.teratour.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Toast;

import com.itera.teratour.model.TargetInfoModel;

public class TargetInfoViewModel extends ViewModel {

    public static TargetInfoViewModel targetInfoInstance;

    private MutableLiveData<String> targetName;
    private MutableLiveData<String> targetDetail;

    private MutableLiveData<Drawable> targetImage;

    private MutableLiveData<TargetInfoModel> targetInfoLiveData = new MutableLiveData<>();

    public TargetInfoViewModel Instance(){
        if (targetInfoInstance == null){
            targetInfoInstance = this;
        }

        return targetInfoInstance;
    }

    public TargetInfoViewModel(){
        targetName = new MutableLiveData<>();
        targetDetail = new MutableLiveData<>();
        targetImage = new MutableLiveData<>();

        //targetName.setValue("Worked");

    }

    public LiveData<TargetInfoModel> TargetInfo(){

        if (targetInfoLiveData == null){
            targetInfoLiveData = new MutableLiveData<>();
        }

        return targetInfoLiveData;
    }

    public LiveData<Drawable> getTargetImage() {
        return targetImage;
    }

    public LiveData<String> getTargetDetail() {
        return targetDetail;
    }

    public LiveData<String> getTargetName() {
        if (targetName == null) {
            targetName = new MutableLiveData<>();
            targetName.setValue("No Title");
        }

        return targetName;
    }

    public void setTargetDetail(MutableLiveData<String> targetDetail) {
        this.targetDetail = targetDetail;
    }

    public void setTargetName(String var) {
        targetName.setValue(var);
    }

    public void setTargetImage(MutableLiveData<String> targetName) {
        this.targetName = targetName;
    }


    public void DoSomething(View view){
        targetName.setValue("Worked");

        Toast.makeText(view.getContext(), "Clicked", Toast.LENGTH_LONG).show();
    }
}
