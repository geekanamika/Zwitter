package com.example.zwitter.data.network;

import android.net.Uri;

public interface AppNetworkSource {
    boolean isSignedIn();

    Uri getProfileDp();

    String getProfileName();

    String getUserEmail();

    String getUserId() ;
}
