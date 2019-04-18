package com.example.zwitter.utils;

import com.example.zwitter.data.AppDataManger;
import com.example.zwitter.data.network.SignInManger;

public class InjectorUtils {
    public static AppDataManger provideRepository() {

        // remote
        SignInManger manger = SignInManger.getInstance();

        return AppDataManger.getInstance(manger);
    }
}
