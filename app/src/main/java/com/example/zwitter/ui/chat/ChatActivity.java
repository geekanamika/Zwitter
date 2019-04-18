package com.example.zwitter.ui.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zwitter.R;
import com.example.zwitter.data.models.Message;
import com.example.zwitter.utils.Constants;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener{

    String receiverId;
    String receiverName;
    String receiverDp;
    TextView messageEditText;
    TextView tvReceiverName;
    RoundedImageView imgReceiverAvatar;
    Button messageSendButton;
    ChatViewModel chatViewModel;

    RecyclerView chatRecyclerView;
    private FirebaseRecyclerAdapter<Message, ChatViewHolder> chatListAdapter;

    public ChatActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat);

        getReceiverDetails();

        init();
    }

    /**
     * instantiate variables and set values
     */
    private void init() {
        messageEditText = findViewById(R.id.messageEditText);
        messageSendButton = findViewById(R.id.btnMessageSend);
        messageSendButton.setOnClickListener(this);


        Toolbar toolbar = findViewById(R.id.chat_toolbar);
        setSupportActionBar(toolbar);

        tvReceiverName = findViewById(R.id.chat_receiver_name);
        imgReceiverAvatar = findViewById(R.id.chat_receiver_dp);
        tvReceiverName.setText(receiverName);
        Picasso.get().load(receiverDp).into(imgReceiverAvatar);

        setTextListener();

        chatViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);
        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        chatRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        chatRecyclerView.setLayoutManager(layoutManager);
        fetch();
        chatRecyclerView.setAdapter(chatListAdapter);


    }

    private void fetch() {
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("chatThreads")
                .child(chatViewModel.getUniqueThreadId(receiverId))
                ;

        FirebaseRecyclerOptions<Message> options =
                new FirebaseRecyclerOptions.Builder<Message>()
                        .setQuery(query, Message.class)
                        .build();

        chatListAdapter = new FirebaseRecyclerAdapter<Message, ChatViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ChatViewHolder chatViewHolder, int i, @NonNull Message message) {
                final DatabaseReference postRef = getRef(i);

                //Todo set background padding according to send detail
                if(!message.getSenderId().equals(chatViewModel.myUserId())) {
                    chatViewHolder.chatMessage.setBackground(getResources().getDrawable(R.drawable.bg_other_text));

                }

                chatViewHolder.chatMessage.setText(message.getMessage());
                Log.d(Constants.MY_TAG, message.getSenderId());
            }

            @NonNull
            @Override
            public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                return new ChatViewHolder(inflater.inflate(R.layout.item_my_message, parent, false));
            }
        };

    }

    /**
     * ensures that message of length 0 is not posted, post button is disabled
     */
    private void setTextListener() {
        messageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence zweet, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence message, int start, int before, int count) {
                if(message.toString().trim().length()==0){
                    messageSendButton.setEnabled(false);
                } else {
                    messageSendButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    /**
     * get receiver Id to start or continue chatting
     */
    private void getReceiverDetails() {
        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }
        if (intent != null) {
            Bundle data = intent.getExtras();
            if (data != null) {
                receiverId = data.getString(Constants.USER_ID_TAG);
                receiverDp = data.getString(Constants.USER_DP_TAG);
                receiverName = data.getString(Constants.INTENT_NAME_TAG);
            } else {
                closeOnError();
            }
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnMessageSend : validateAndSend();
                        break;
        }
    }

    private void validateAndSend() {
        if (TextUtils.isEmpty(messageEditText.getText())) {
            Toast.makeText(this, "Please add message!", Toast.LENGTH_SHORT).show();
            return;
        }
        String message = messageEditText.getText().toString();
        Log.d(Constants.MY_TAG, message);
        chatViewModel.sendMessage(receiverId, message);
        messageEditText.setText("");

    }

    @Override
    public void onStart() {
        super.onStart();
        if (chatListAdapter != null) {
            Log.d(Constants.MY_TAG, "listening");
            chatListAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (chatListAdapter != null) {
            Log.d(Constants.MY_TAG, "stop listening");
            chatListAdapter.stopListening();
        }
    }
}
