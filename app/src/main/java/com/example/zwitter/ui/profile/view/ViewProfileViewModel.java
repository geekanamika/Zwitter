package com.example.zwitter.ui.profile.view;

import com.example.zwitter.data.AppDataManger;
import com.example.zwitter.utils.InjectorUtils;
import com.google.firebase.database.ValueEventListener;

import androidx.lifecycle.ViewModel;

class ViewProfileViewModel extends ViewModel {

    private final AppDataManger dataManger;


    public ViewProfileViewModel() {
        dataManger = InjectorUtils.provideRepository();
    }


    String getUserId() {
        return dataManger.getUserId();
    }


}
