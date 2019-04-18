package com.example.zwitter.ui.chat;

import android.util.Log;

import com.example.zwitter.data.AppDataManger;
import com.example.zwitter.data.models.Message;
import com.example.zwitter.utils.Constants;
import com.example.zwitter.utils.InjectorUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.lifecycle.ViewModel;

/**
 * "chat_threads": {
 *     "user1user2" : {
 *         "messageId" : {
 *           "message" : "this is first ",
 *           "sender" : "user1",
 *           "timestamp", "timeInMillis"
 *         },
 *         "messageId2" : {
 *           "message" : "this is first ",
 *           "sender" : "user1"
 *         },
 *         "messageId3" : {
 *           "message" : "this is first ",
 *           "sender" : "user1"
 *         }
 *
 *     },
 *     "user1user3" : {
 *
 *     }
 *   }
 */

class ChatViewModel extends ViewModel {

    private final AppDataManger dataManger;
    private final DatabaseReference database;

    public ChatViewModel() {
        dataManger = InjectorUtils.provideRepository();
        database = FirebaseDatabase.getInstance().getReference();
    }

    void sendMessage(String receiverId, String message) {
        long time = System.currentTimeMillis();
        String messageKey = database.child("chatThreads").child(getUniqueThreadId(receiverId)).push().getKey();
        Message messageObj = new Message(message, String.valueOf(time), myUserId());
        if(isSignedIn() && messageKey != null) {
            database.child("chatThreads").child(getUniqueThreadId(receiverId)).child(messageKey).setValue(messageObj);
        } else {
            Log.e(Constants.MY_TAG, "not signed in or message key received is null");
        }

    }

    private boolean isSignedIn() {
        return dataManger.isSignedIn();
    }

    String myUserId() {
        return dataManger.getUserId();
    }

    String getUniqueThreadId(String receiverId) {
        return myUserId() + receiverId;
    }
}
