package com.example.zwitter.ui.chat;

import android.view.View;
import android.widget.TextView;

import com.example.zwitter.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChatViewHolder extends RecyclerView.ViewHolder {
    TextView chatMessage;
    public ChatViewHolder(@NonNull View itemView) {
        super(itemView);

        chatMessage = itemView.findViewById(R.id.chat_message_body);
    }
}
