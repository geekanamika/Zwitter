package com.example.zwitter.ui.profile.view;

import com.example.zwitter.data.AppDataManger;
import com.example.zwitter.utils.InjectorUtils;

import androidx.lifecycle.ViewModel;

public class ViewProfileViewModel extends ViewModel {

    private AppDataManger dataManger;
    public ViewProfileViewModel() {

        dataManger = InjectorUtils.provideRepository();
    }

    String getUserEmail() {
        return dataManger.getUserEmail();
    }


}
