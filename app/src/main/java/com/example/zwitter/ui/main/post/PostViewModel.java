package com.example.zwitter.ui.main.post;

import android.util.Log;

import com.example.zwitter.data.AppDataManger;
import com.example.zwitter.data.models.Post;
import com.example.zwitter.utils.Constants;
import com.example.zwitter.utils.InjectorUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;

class PostViewModel extends ViewModel {
    private final AppDataManger dataManger;
    private final DatabaseReference database;

    public PostViewModel() {
        dataManger = InjectorUtils.provideRepository();
        database = FirebaseDatabase.getInstance().getReference();
    }

    void originalPostZweet(String message) {
        long time = System.currentTimeMillis();
        String key = database.child("posts").push().getKey();
        Post post = new Post(dataManger.getUserId(), key, getProfilePicture(), message, time, getUserName(), true);

        // add post in "posts" node & update "user-posts" node with postId
        saveOriginalPosts(key, post);
    }


    void postReplyToPostId(String replyTo, String message ) {

        // get unique key of new reply message by push() to reference
        String replyPostId = database.child("posts").push().getKey();

        Log.d(Constants.MY_TAG, replyPostId + "\n"+replyTo +"\n"+message);
        Post post = new Post(dataManger.getUserId(), replyPostId, getProfilePicture(), message, System.currentTimeMillis(),
                getUserName(), false);

        // increase no of comments
        if (isSignedIn() && replyPostId != null) {
            // post reply message
            database.child("posts")
                    .child(replyPostId).setValue(post.toMap());

            // increase user's post count
            increaseUserPostCount();

            // increase reply count on replyTo post
            increaseReplyCountOnPostId(replyTo);

            // save reply postIds here
            database.child("postReplies")
                    .child(replyTo).child(replyPostId).setValue(true);

            database.child("userPosts").child(getUserId()).child(replyPostId).setValue(true);
        }
    }

    private void saveOriginalPosts(String postId, Post post) {
        if (isSignedIn()) {
            database.child("posts")
                    .child(postId).setValue(post.toMap());
            try {
                increaseUserPostCount();
                database.child("userPosts").child(getUserId()).child(postId).setValue(true);

            } catch (Exception e) {
                Log.e(Constants.MY_TAG, "update error ");
            }

        } else {
            Log.d(Constants.MY_TAG, "not signed in yet");

        }
    }

    /**
     * Increase reply count on replyTo postId
     * @param replyTo postId to which reply count is increased
     */
    private void increaseReplyCountOnPostId(String replyTo ) {
        database.child("posts").child(replyTo)
                .child("noOfComments")
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
                    public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {
                        if (databaseError != null) {
                            Log.e(Constants.MY_TAG,"Firebase counter increment failed while updating reply count!");
                        }
                    }
                });
    }

    /**
     * Increase user's post count on new post or reply
     */
    private void increaseUserPostCount() {
        database.child("users").child(getUserId()).child("noOfPosts")
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
                            Log.e(Constants.MY_TAG,"Firebase counter increment failed while updating user post count!");
                        }
                    }

                });
    }

    private String getUserId() {
        return dataManger.getUserId();
    }

    private String getUserName() {
        return dataManger.getProfileName();
    }

    private boolean isSignedIn() {
        return dataManger.isSignedIn();
    }

    String getProfilePicture() {
        return dataManger.getProfileDp().toString();
    }

}
