package com.example.zwitter.ui.profile.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zwitter.R;
import com.makeramen.roundedimageview.RoundedImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


class UserFeedHolder extends RecyclerView.ViewHolder {
    final TextView fullName;
    final TextView timeStamp;
    final TextView content;
    final TextView replyCount;
    final TextView likeCount;
    final RoundedImageView avatar;
    final ImageView likeButton;
    final ImageView replyButton;


    public UserFeedHolder(@NonNull View itemView) {
        super(itemView);

        fullName = itemView.findViewById(R.id.tweet_name);
        timeStamp = itemView.findViewById(R.id.min);
        content = itemView.findViewById(R.id.content);
        likeCount = itemView.findViewById(R.id.txtLike);
        replyCount = itemView.findViewById(R.id.txtComment);
        avatar = itemView.findViewById(R.id.edit_avatar);
        likeButton = itemView.findViewById(R.id.ic_like);
        replyButton = itemView.findViewById(R.id.btn_reply);
    }
}
