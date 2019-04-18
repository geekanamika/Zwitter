package com.example.zwitter.ui.login;

import android.util.Log;

import com.example.zwitter.data.AppDataManger;
import com.example.zwitter.data.models.User;
import com.example.zwitter.utils.Constants;
import com.example.zwitter.utils.InjectorUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.lifecycle.ViewModel;

class LoginViewModel extends ViewModel {

    private final AppDataManger dataManger;

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
        return new User(getFullUserName(), getProfilePicture());
    }

    /**
     * creates a user object & sends data to firebase
     * @return true if user is signed else false
     */
    boolean logInSaveDatabase() {
        DatabaseReference database;

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
