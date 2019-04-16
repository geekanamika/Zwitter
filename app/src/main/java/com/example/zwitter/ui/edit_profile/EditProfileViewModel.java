package com.example.zwitter.ui.edit_profile;

import android.app.Application;

import com.example.zwitter.data.AppDataManger;
import com.example.zwitter.utils.InjectorUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class EditProfileViewModel extends AndroidViewModel {
    private AppDataManger dataManger;

    public EditProfileViewModel(@NonNull Application application) {
        super(application);
        dataManger = InjectorUtils.provideRepository(application.getApplicationContext());
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
