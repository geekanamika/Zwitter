package com.example.zwitter.ui.main.post;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zwitter.R;
import com.example.zwitter.ui.main.MainActivity;
import com.example.zwitter.utils.Constants;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class PostActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText postMessage;
    private Button zweetButton;
    private PostViewModel postViewModel;
    private Boolean originalOrReply = true;
    private String postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        init();

        setTextListener();
    }

    /**
     * checks how post activity was started
     */
    private void checkIfOriginOrReply () {
        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }
        if (intent != null) {
            Bundle data = intent.getExtras();
            if (data != null) {
                originalOrReply = data.getBoolean(Constants.INTENT_TAG);
                if(!originalOrReply)
                    postId = data.getString(Constants.POST_ID_TAG);
                Log.d(Constants.MY_TAG , postId + originalOrReply);
            }
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void init() {
        checkIfOriginOrReply();

        RoundedImageView userImage = findViewById(R.id.user_image);
        postMessage = findViewById(R.id.zweet_message);
        zweetButton = findViewById(R.id.action_post_message);
        Button cancelButton = findViewById(R.id.action_cancel_post);
        zweetButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        postViewModel = ViewModelProviders.of(this).get(PostViewModel.class);


        Picasso.get().load(postViewModel.getProfilePicture())
                .into(userImage);
    }

    /**
     * ensures that message of length 0 is not posted, post button is disabled
     */
    private void setTextListener() {
        postMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence zweet, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence zweet, int start, int before, int count) {
                if(zweet.toString().trim().length()==0){
                    zweetButton.setEnabled(false);
                } else {
                    zweetButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //Todo on back / cancel click, check via dialogue if text size > 0
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action_post_message : validateMessageAndPost();
                                            break;
            case R.id.action_cancel_post : startMainActivity();
                                            break;
        }
    }

    public void onBackPressed() {
       startMainActivity();
    }

    private void startMainActivity() {
        Intent intent = new Intent(PostActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void validateMessageAndPost() {

        if (TextUtils.isEmpty(zweetButton.getText())) {
            Toast.makeText(this, "Please add message!", Toast.LENGTH_SHORT).show();
            return;
        }
        String message = postMessage.getText().toString();
        Toast.makeText(this, getString(R.string.toast_posting), Toast.LENGTH_SHORT).show();
        if(originalOrReply)
            postViewModel.originalPostZweet(message);
        else
            postViewModel.postReplyToPostId(postId , message);

        startMainActivity();

    }
}
