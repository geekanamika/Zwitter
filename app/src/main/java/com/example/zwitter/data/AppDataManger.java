package com.example.zwitter.data;

import android.net.Uri;

import com.example.zwitter.data.network.AppNetworkSource;
import com.example.zwitter.data.network.SignInManger;

public class AppDataManger implements AppNetworkSource {
    private final SignInManger signInManger;
    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static AppDataManger sInstance;
    private AppDataManger (SignInManger networkHelper) {
        this.signInManger = networkHelper;
    }

    public synchronized static AppDataManger getInstance(
            SignInManger networkDataSource) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new AppDataManger(networkDataSource);
            }
        }
        return sInstance;
    }

    @Override
    public boolean isSignedIn() {
        return signInManger.isSignedIn();
    }

    @Override
    public Uri getProfileDp() {
        return signInManger.getProfileDp();
    }

    @Override
    public String getProfileName() {
        return signInManger.getProfileName();
    }

    @Override
    public String getUserEmail() {
        return signInManger.getUserEmail();
    }

    @Override
    public String getUserId() {
        return signInManger.getUserId();
    }
}
