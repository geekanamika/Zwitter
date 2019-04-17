package com.example.zwitter.ui.profile.edit;

import com.example.zwitter.data.AppDataManger;
import com.example.zwitter.utils.InjectorUtils;

import androidx.lifecycle.ViewModel;

public class EditProfileViewModel extends ViewModel {
    private AppDataManger dataManger;

    public EditProfileViewModel() {
        dataManger = InjectorUtils.provideRepository();
    }

    public String getFullUserName() {
        return dataManger.getProfileName();
    }

    String getUserEmail() {
        return dataManger.getUserEmail();
    }

    String getProfilePicture() {
        return dataManger.getProfileDp().toString();
    }
}
