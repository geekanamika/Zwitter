package com.example.zwitter.ui.main.feed;

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

import androidx.lifecycle.ViewModel;

class FeedViewModel extends ViewModel {
    private final AppDataManger dataManger;

    public FeedViewModel() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        dataManger = InjectorUtils.provideRepository();
    }

    String getUid() {
        return dataManger.getUserId();
    }



    void onStarClicked(DatabaseReference reference) {
        reference.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Post p = mutableData.getValue(Post.class);
                if (p == null) {
                    return Transaction.success(mutableData);
                }

                if (p.stars.containsKey(getUid())) {
                    // Unstar the post and remove self from stars
                    long likeCount = Long.valueOf(p.getNoOfLikes()) - 1;
                    p.setNoOfLikes(""+likeCount);
                    p.stars.remove(getUid());
                } else {
                    // Star the post and add self to stars
                    long likeCount = Long.valueOf(p.getNoOfLikes()) +1;
                    p.setNoOfLikes(""+likeCount);
                    p.stars.put(getUid(), true);
                }

                // Set value and report transaction success
                mutableData.setValue(p);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                // Transaction completed
                Log.d(Constants.MY_TAG, "postTransaction:onComplete:" + databaseError);
            }
        });
    }
}
