package com.example.zwitter.ui.main.post;

import android.util.Log;

import com.example.zwitter.data.AppDataManger;
import com.example.zwitter.data.models.Post;
import com.example.zwitter.data.models.User;
import com.example.zwitter.utils.Constants;
import com.example.zwitter.utils.InjectorUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.lifecycle.ViewModel;

public class PostViewModel extends ViewModel {
    private AppDataManger dataManger;
    private DatabaseReference database = null;

    public PostViewModel() {
        dataManger = InjectorUtils.provideRepository();
        database = FirebaseDatabase.getInstance().getReference();
    }

    void originalPostZweet(String message) {
        long time = System.currentTimeMillis();
        String postId = getUserId() + time;
        Post post = new Post( message, time, getUserId(), true);

        //Todo update user post list
        // add post
        savePostInDatabase(postId, post);

    }

    private void savePostInDatabase(String postId, Post post) {


        if (isSignedIn()) {

            database.child("posts")
                    .child(postId).setValue(post);


            try {
                database.child("users").child(getUserId()).child("noOfPosts").setValue(1);

            } catch (Exception e) {
                Log.e(Constants.MY_TAG, "update error ");
            }

        } else {
            Log.d(Constants.MY_TAG, "not signed in yet");

        }
    }

    private String getUserId() {
        return dataManger.getUserId();
    }

    private boolean isSignedIn() {
        return dataManger.isSignedIn();
    }

    public String getProfilePicture() {
        return dataManger.getProfileDp().toString();
    }

}
