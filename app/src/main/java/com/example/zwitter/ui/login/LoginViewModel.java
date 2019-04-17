package com.example.zwitter.ui.login;

import android.util.Log;

import com.example.zwitter.data.AppDataManger;
import com.example.zwitter.data.models.User;
import com.example.zwitter.utils.Constants;
import com.example.zwitter.utils.InjectorUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {

    private AppDataManger dataManger;

    public LoginViewModel() {
        dataManger = InjectorUtils.provideRepository();
    }

    private String getFullUserName() {
        return dataManger.getProfileName();
    }


    private String getUserId() {
        return dataManger.getUserId();
    }

    private boolean isSignedIn() {
        return dataManger.isSignedIn();
    }

    private String getProfilePicture() {
        return dataManger.getProfileDp().toString();
    }

    private User getUser() {
        //Todo check if user already exists then don't set no of follower... to 0
        return new User(getUserId(), getFullUserName(), getProfilePicture());
    }

    boolean logInSaveDatabase() {
        DatabaseReference database = null;

        if (isSignedIn()) {
            User user = getUser();
            database = FirebaseDatabase.getInstance().getReference().child("users")
                    .child(getUserId());
            database.setValue(user);
        } else {
            Log.d(Constants.MY_TAG, "not signed in yet");
            return false;
        }
        return true;
    }
}
