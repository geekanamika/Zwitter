package com.example.zwitter.utils;

import android.content.Context;

import com.example.zwitter.data.AppDataManger;
import com.example.zwitter.data.network.SignInManger;

public class InjectorUtils {
    public static AppDataManger provideRepository() {

        // remote
        SignInManger manger = SignInManger.getInstance();
                ;
        // pref
       // AppPrefHelper preferenceHelper = new AppPrefHelper(context, "pref");

        return AppDataManger.getInstance(manger);
    }
}
