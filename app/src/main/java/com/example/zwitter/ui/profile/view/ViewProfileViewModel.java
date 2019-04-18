package com.example.zwitter.ui.profile.view;

import android.util.Log;

import com.example.zwitter.data.AppDataManger;
import com.example.zwitter.utils.Constants;
import com.example.zwitter.utils.InjectorUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;

class ViewProfileViewModel extends ViewModel {

    private DatabaseReference database;
    private final AppDataManger dataManger;


    public ViewProfileViewModel() {
        dataManger = InjectorUtils.provideRepository();
        database = FirebaseDatabase.getInstance().getReference();
    }


    String getUserId() {
        return dataManger.getUserId();
    }


    /*
     * update getUserId() following list
     * update my following count
     * update other user's followers count
     */
    void doFollowUpdatesinDb(String otherUserId) {

        increaseMyFollowingCount();

        increaseUserFollowerCount(otherUserId);

        updateMyFollowingList(otherUserId);

    }

    private void updateMyFollowingList(String otherUserId) {
        database.child("followingList").child(getUserId()).child(otherUserId).setValue(true);
    }

    private void increaseUserFollowerCount(String userId) {

        database.child("users").child(userId).child("noOfFollower")
                .runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                        if (currentData.getValue() == null) {
                            currentData.setValue("0");
                        } else {

                            String stringValue = currentData.getValue().toString();
                            long val = Long.parseLong(stringValue);
                            long increasedIntValue = val + 1;
                            currentData.setValue(String.valueOf(increasedIntValue));
                        }
                        return Transaction.success(currentData);
                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, boolean b,
                                           @Nullable DataSnapshot dataSnapshot) {
                        if (databaseError != null) {
                            Log.e(Constants.MY_TAG, "Firebase counter increment failed while updating user follower count!");
                        }
                    }
                });

    }


    private void increaseMyFollowingCount() {
        database.child("users").child(getUserId()).child("noOfFollowing")
                .runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                        if (currentData.getValue() == null) {
                            currentData.setValue("0");
                        } else {

                            String stringValue = currentData.getValue().toString();
                            long val = Long.parseLong(stringValue);
                            long increasedIntValue = val + 1;
                            currentData.setValue(String.valueOf(increasedIntValue));
                        }
                        return Transaction.success(currentData);
                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, boolean b,
                                           @Nullable DataSnapshot dataSnapshot) {
                        if (databaseError != null) {
                            Log.e(Constants.MY_TAG, "Firebase counter increment failed while updating user following count!");
                        }
                    }
                });
    }
}
