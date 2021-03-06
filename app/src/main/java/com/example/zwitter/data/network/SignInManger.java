package com.example.zwitter.data.network;

import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInManger {
    // Firebase instance variables
    private final FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static SignInManger sInstance;

    private SignInManger() {
        mFirebaseAuth = FirebaseAuth.getInstance();

    }

    public synchronized static SignInManger getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new SignInManger();
            }
        }
        return sInstance;
    }

    public boolean isSignedIn() {
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        return mFirebaseUser != null;

    }

    public Uri getProfileDp() {
        return mFirebaseUser.getPhotoUrl();
    }

    public String getProfileName() {
        return mFirebaseUser.getDisplayName();
    }

    public String getUserEmail() {
        return mFirebaseUser.getEmail();
    }

    public String getUserId() {
        return mFirebaseUser.getUid();
    }
}
