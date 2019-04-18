package com.example.zwitter.ui.main.users;

import com.example.zwitter.data.AppDataManger;
import com.example.zwitter.utils.InjectorUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.lifecycle.ViewModel;

public class UserViewModel extends ViewModel {
    private final DatabaseReference mDatabase;
    private final AppDataManger dataManger;

    public UserViewModel() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        dataManger = InjectorUtils.provideRepository();
    }

    String getUid() {
        return dataManger.getUserId();
    }


}
