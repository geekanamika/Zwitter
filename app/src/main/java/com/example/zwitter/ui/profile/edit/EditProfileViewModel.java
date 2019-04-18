package com.example.zwitter.ui.profile.edit;

import com.example.zwitter.data.AppDataManger;
import com.example.zwitter.utils.InjectorUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.lifecycle.ViewModel;

class EditProfileViewModel extends ViewModel {
    private final AppDataManger dataManger;
    private DatabaseReference database;

    public EditProfileViewModel() {
        dataManger = InjectorUtils.provideRepository();
        database = FirebaseDatabase.getInstance().getReference();
    }

    String getUserEmail() {
        return dataManger.getUserEmail();
    }

    private String getUserId() {
        return dataManger.getUserId();
    }

    String getProfilePicture() {
        return dataManger.getProfileDp().toString();
    }

    void updateUserDetails(String name, String bio) {
        database.child("users")
                .child(getUserId()).child("userBio").setValue(bio);
        database.child("users")
                .child(getUserId()).child("userName").setValue(name);
    }
}
