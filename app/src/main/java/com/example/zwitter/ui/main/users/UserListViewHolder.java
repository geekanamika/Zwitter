package com.example.zwitter.ui.main.users;

import android.view.View;
import android.widget.TextView;

import com.example.zwitter.R;
import com.makeramen.roundedimageview.RoundedImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


class UserListViewHolder extends RecyclerView.ViewHolder {
    final TextView userListFullName;
    final TextView userListBio;
    final RoundedImageView userListAvatar;

    public UserListViewHolder(@NonNull View itemView) {
        super(itemView);

        userListFullName = itemView.findViewById(R.id.list_user_name);
        userListBio = itemView.findViewById(R.id.user_list_bio);
        userListAvatar = itemView.findViewById(R.id.user_list_avatar);
    }
}
