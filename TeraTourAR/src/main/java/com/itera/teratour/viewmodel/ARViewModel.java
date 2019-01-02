package com.itera.teratour.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class ARViewModel extends ViewModel {

    public MutableLiveData<String> notify = new MutableLiveData<>();

}
