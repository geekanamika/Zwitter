package com.example.zwitter.ui.main.users;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zwitter.R;
import com.example.zwitter.data.models.User;
import com.example.zwitter.ui.chat.ChatActivity;
import com.example.zwitter.utils.Constants;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class UserListFragment extends Fragment {

    private RecyclerView userListView;

    private FirebaseRecyclerAdapter<User, UserListViewHolder> userListAdapter;

    public UserListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);

        userListView = view.findViewById(R.id.feed_list_view);
        userListView.setHasFixedSize(true);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set up Layout Manager, reverse layout
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        userListView.setLayoutManager(layoutManager);
        fetch();
        userListView.setAdapter(userListAdapter);
    }

    private void fetch() {
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("users");

        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(query, User.class)
                        .build();


        userListAdapter = new FirebaseRecyclerAdapter<User, UserListViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull UserListViewHolder userListViewHolder, int position,
                                            @NonNull final User userModel) {
                final DatabaseReference postRef = getRef(position);

                userListViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        /*
                         * add receiver id & start chat activity
                         */
                        Intent intent = new Intent(UserListFragment.this.getActivity(), ChatActivity.class);
                        intent.putExtra(Constants.USER_ID_TAG, userModel.getUserId());
                        intent.putExtra(Constants.USER_DP_TAG, userModel.getProfileDp());
                        intent.putExtra(Constants.INTENT_NAME_TAG, userModel.getUserName());
                        startActivity(intent);
                    }
                });

                /*
                 * set values on item_user fields
                 */
                userListViewHolder.userListFullName.setText(userModel.getUserName());
                userListViewHolder.userListBio.setText(userModel.getUserBio());
                Picasso.get().load(userModel.getProfileDp())
                        .into(userListViewHolder.userListAvatar);
            }

            @NonNull
            @Override
            public UserListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                return new UserListViewHolder(inflater.inflate(R.layout.item_user, parent, false));
            }
        };

    }

    @Override
    public void onStart() {
        Log.d(Constants.MY_TAG, "trying to listening");
        super.onStart();
        if (userListAdapter != null) {
            Log.d(Constants.MY_TAG, "listening");
            userListAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        Log.d(Constants.MY_TAG, "try to stop listening");
        super.onStop();
        if (userListAdapter != null) {
            Log.d(Constants.MY_TAG, "stop listening");
            userListAdapter.stopListening();
        }
    }
}
