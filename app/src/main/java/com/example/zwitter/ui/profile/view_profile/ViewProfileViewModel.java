package com.example.zwitter.ui.profile.edit_profile.view_profile;

import android.app.Application;

import com.example.zwitter.data.AppDataManger;
import com.example.zwitter.utils.InjectorUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class ViewProfileViewModel extends AndroidViewModel {

    private AppDataManger dataManger;
    public ViewProfileViewModel(@NonNull Application application) {
        super(application);

        dataManger = InjectorUtils.provideRepository(application.getApplicationContext());
    }

    String getUserEmail() {
        return dataManger.getUserEmail();
    }


}
