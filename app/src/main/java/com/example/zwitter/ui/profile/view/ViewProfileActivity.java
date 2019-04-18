package com.example.zwitter.ui.profile.view;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zwitter.R;
import com.example.zwitter.data.models.User;
import com.example.zwitter.ui.profile.edit.EditProfileActivity;
import com.example.zwitter.utils.Constants;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

public class ViewProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnEditFollowProfile;
    private String userId;
    private boolean isThisFollowButtton;
    private ViewProfileViewModel viewProfileViewModel;
    private TextView followersCount;
    private TextView followingsCount;
    private TextView postCount;
    private TextView userName;
    private ImageView userDp;



    private User user;

    private DatabaseReference database;
    private DatabaseReference mDatabaseReference;
    private ValueEventListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        viewProfileViewModel = ViewModelProviders.of(this).get(ViewProfileViewModel.class);

        followersCount = findViewById(R.id.followersCounterTextView);
        followingsCount = findViewById(R.id.followingsCounterTextView);
        userDp = findViewById(R.id.viewProfileDp);
        postCount = findViewById(R.id.postsCounterTextView);
        btnEditFollowProfile  = findViewById(R.id.followOrEditButton);
        userName = findViewById(R.id.view_user_name);

        btnEditFollowProfile.setOnClickListener(this);
        checkIfFollowOrEdit();

        attachFeedFragment();

    }

    private void attachFeedFragment() {
        UserFeedFragment fragment = new UserFeedFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.FRAGMENT_BUNDLE_TAG, userId);
        // set Arguments
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.user_feed_container, fragment, "adding");
        transaction.commit();
    }

    private void init() {
        database = FirebaseDatabase.getInstance().getReference().child("users").child(userId);

        listener = database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);

                updateUI(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(Constants.MY_TAG, "error loading while profile");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        init();



    }

    @Override
    protected void onStop() {
        super.onStop();
        database.removeEventListener(listener);

    }

    private void checkIfFollowOrEdit() {
        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }
        if (intent != null) {
            Bundle data = intent.getExtras();
            if (data != null) {
                userId = data.getString(Constants.INTENT_TAG);
                if (userId.equals(viewProfileViewModel.getUserId())) {
                    btnEditFollowProfile.setText(getString(R.string.label_edit_profile));
                    isThisFollowButtton = false;
                } else {
                    // Todo check if already following or not
                    isThisFollowButtton = true;
                    btnEditFollowProfile.setText(getString(R.string.label_follow));
                }
            }
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        CharSequence textWritten = btnEditFollowProfile.getText();
        switch (v.getId()) {
            case R.id.followOrEditButton : if (textWritten.equals(getString(R.string.label_edit_profile))) {
                startEditProfile();
            } else if (textWritten.equals(getString(R.string.label_follow))) {

                btnEditFollowProfile.setText(getString(R.string.label_following));
                viewProfileViewModel.doFollowUpdatesinDb(user.getUserId());
            } else {
                //Todo implement behaviour when already following, opposite behaviour of following
            }
            break;
        }
    }

    private void startEditProfile() {
        Intent intent = new Intent(this, EditProfileActivity.class);

        intent.putExtra(Constants.INTENT_NAME_TAG, user.getUserName());
        intent.putExtra(Constants.INTENT_BIO_TAG, user.getUserBio());
        startActivity(intent);
    }

    private void updateUI(User user) {
        Resources resources = getResources();
        followersCount.setText(resources.getString(R.string.count_followers,user.getNoOfFollower()));
        followingsCount.setText(resources.getString(R.string.count_followings, user.getNoOfFollowing()));
        postCount.setText(resources.getString(R.string.count_posts, user.getNoOfPosts()));
        userName.setText(user.getUserName());
        Picasso.get().load(user.getProfileDp())
                .into(userDp);
    }

    /**
     * According to firebase rules, there will be callback only if given path exists
     * Hence, query checks /followingList/myId/otherUserid
     * if callback occurs, it means that It's in my following list, hence update follow button text to "Following"
     */
//    void addListenerToFollowButton() {
////        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
////        Query query = mDatabaseReference.child("followingList").child(viewProfileViewModel.getUserId())
////                .child(user.getUserId());
////        if (isThisFollowButtton) {
////            query.addListenerForSingleValueEvent(new ValueEventListener() {
////                @Override
////                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                    btnEditFollowProfile.setText(getString(R.string.label_following));
////                }
////
////                @Override
////                public void onCancelled(@NonNull DatabaseError databaseError) {
////
////                }
////            });
////        }
//
//        if(isThisFollowButtton) {
//            viewProfileViewModel.doFollowUpdatesinDb(user.getUserId());
//        }
//
//    }
}
