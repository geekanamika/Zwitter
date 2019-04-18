package com.example.zwitter.ui.main.feed;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zwitter.R;
import com.example.zwitter.data.models.Post;
import com.example.zwitter.ui.main.post.PostActivity;
import com.example.zwitter.ui.profile.view.ViewProfileActivity;
import com.example.zwitter.utils.Constants;
import com.example.zwitter.utils.TimeUtil;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FeedFragment extends Fragment {

    private RecyclerView feedListView;
    private FeedViewModel feedViewModel;

    private FirebaseRecyclerAdapter<Post, FeedViewHolder> feedAdapter;

    public FeedFragment() {
        // Required empty public constructor
    }

    public static FeedFragment newInstance() {
        return new FeedFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);

        feedViewModel = ViewModelProviders.of(this).get(FeedViewModel.class);
        feedListView = view.findViewById(R.id.feed_list_view);
        feedListView.setHasFixedSize(true);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set up Layout Manager, reverse layout
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        feedListView.setLayoutManager(layoutManager);
        fetch();
        feedListView.setAdapter(feedAdapter);
    }

    private void fetch() {
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("posts")
                .orderByChild("time")
                ;

        FirebaseRecyclerOptions<Post> options =
                new FirebaseRecyclerOptions.Builder<Post>()
                        .setQuery(query, Post.class)
                        .build();

        feedAdapter = new FirebaseRecyclerAdapter<Post, FeedViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FeedViewHolder feedViewHolder, int position,
                                            @NonNull final Post postModel) {
                final DatabaseReference postRef = getRef(position);

                // Set click listener for the whole post view

                attachListeners(feedViewHolder, postModel, postRef.getKey());

                // Determine if the current user has liked this post and set UI accordingly
                if (postModel.stars.containsKey(getUid())) {
                    feedViewHolder.likeButton.setImageResource(R.drawable.ic_like_selected);
                } else {
                    feedViewHolder.likeButton.setImageResource(R.drawable.ic_like);
                }

                feedViewHolder.fullName.setText(postModel.getUserName());
                TimeUtil time = new TimeUtil();
                feedViewHolder.timeStamp.setText(time.getTimeValue(System.currentTimeMillis(), postModel.getTime()));
                feedViewHolder.content.setText(postModel.getPostMessage());
                feedViewHolder.likeCount.setText(String.format("%s", "" + postModel.getNoOfLikes()));
                feedViewHolder.replyCount.setText(String.format("%s", "" + postModel.getNoOfComments()));

                Picasso.get().load(postModel.getProfileDp())
                        .into(feedViewHolder.avatar);

            }

            private void attachListeners(@NonNull FeedViewHolder feedViewHolder,
                                         @NonNull final Post postModel, final String key) {

                feedViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Todo open detail activity to display reply threads along with post
                        Log.d(Constants.MY_TAG, "post clicked " + postModel.getPostMessage());
                    }
                });

                feedViewHolder.likeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        feedViewModel.onStarClicked(FirebaseDatabase.getInstance().getReference()
                                .child("posts").child(key)
                        );
                    }
                });

                feedViewHolder.avatar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(FeedFragment.this.getActivity(), ViewProfileActivity.class);
                        intent.putExtra(Constants.INTENT_TAG, postModel.getPostAuthorId());
                        startActivity(intent);
                    }
                });

                feedViewHolder.replyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(FeedFragment.this.getActivity(), PostActivity.class);
                        intent.putExtra(Constants.INTENT_TAG, false);
                        intent.putExtra(Constants.POST_ID_TAG, postModel.getPostId());
                        Log.d(Constants.MY_TAG, postModel.getPostId());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                });
            }

            private String getUid() {
                return feedViewModel.getUid();
            }

            @NonNull
            @Override
            public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                return new FeedViewHolder(inflater.inflate(R.layout.item_tweet, parent, false));
            }
        };

    }

    @Override
    public void onStart() {
        Log.d(Constants.MY_TAG, "trying to listening");
        super.onStart();
        if (feedAdapter != null) {
            Log.d(Constants.MY_TAG, "listening");
            feedAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        Log.d(Constants.MY_TAG, "try to stop listening");
        super.onStop();
        if (feedAdapter != null) {
            Log.d(Constants.MY_TAG, "stop listening");
            feedAdapter.stopListening();
        }
    }
}
